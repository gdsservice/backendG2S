package com.gds.Gestion.de.stock.services;

import com.gds.Gestion.de.stock.DAOs.BannerDAO;
import com.gds.Gestion.de.stock.Input.BannerINPUT;
import com.gds.Gestion.de.stock.entites.*;
import com.gds.Gestion.de.stock.enums.SupprimerStatus;
import com.gds.Gestion.de.stock.exceptions.EmptyException;
import com.gds.Gestion.de.stock.exceptions.ProduitNotFoundException;
import com.gds.Gestion.de.stock.mappers.BannerMapper;
import com.gds.Gestion.de.stock.repositories.BannerImageRepository;
import com.gds.Gestion.de.stock.repositories.BannerRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
@Transactional
@AllArgsConstructor
public class BannerImpl implements InterfaceBanner{

    private BannerRepository bannerRepository;
    private BannerMapper bannerMapper;
    private BannerImageRepository bannerImageRepository;

    @Override
    public void addBanner(BannerINPUT bannerINPUT, List<MultipartFile> images) throws EmptyException, IOException {
        Banner banner = bannerMapper.mapBannerINPUTABanner(bannerINPUT);
        Banner byTitre = bannerRepository.findByTitre(banner.getTitre());
        if (byTitre != null){
            throw new EmptyException("Cet banner existe deja !");
        }
        Utilisateur principal = (Utilisateur) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        banner.setUtilisateurBanner(principal);
        banner.setIdBanner("GDS" + UUID.randomUUID());
        banner.setSupprimerStatus(SupprimerStatus.FALSE);
        Banner saveBanner = bannerRepository.save(banner);

        // Traitement des images
        List<BannerImage> bannerImage = new ArrayList<>();
        for (MultipartFile file : images) {
            BannerImage image = new BannerImage();
            image.setName(file.getOriginalFilename());
            image.setType(file.getContentType());
            image.setData(file.getBytes());
            image.setBanner(saveBanner);
            bannerImage.add(image);
            for (BannerImage BannerImage : bannerImage){
                bannerImageRepository.save(BannerImage);
            }
        }
    }

    @Override
    public List<BannerDAO> listBanner() {
        List<Banner> allBySupprimerStatusFalse = bannerRepository.findAllBySupprimerStatusFalse();
        return allBySupprimerStatusFalse.stream().map(banner -> bannerMapper.mapBannerADAO(banner))
                .collect(Collectors.toList());
    }

    @Override
    public BannerDAO afficherBanner(String idBanner) throws ProduitNotFoundException {
        return bannerMapper.mapBannerADAO(bannerRepository.findById(idBanner).orElseThrow(()->
                new ProduitNotFoundException(" Cet banner n'existe pas")));
    }

    @Override
    public void modifierBanner(BannerINPUT bannerINPUT, List<MultipartFile> images) throws ProduitNotFoundException, IOException {
        // Vérifier que le produit existe
        Banner bannerExist = bannerRepository.findById(bannerINPUT.getIdBanner())
                .orElseThrow(() -> new ProduitNotFoundException("Banner non trouvé avec ID: " + bannerINPUT.getIdBanner()));

        // Mise à jour des champs de base
        bannerExist.setTitre(bannerINPUT.getTitre());
        bannerExist.setUtilisateurBanner(bannerINPUT.getUtilisateurBanner());
        bannerExist.setSupprimerStatus(SupprimerStatus.FALSE);
        bannerExist.setIdBanner(bannerINPUT.getIdBanner());
        bannerExist.setPublier(bannerINPUT.isPublier());
        bannerExist.setBtn_link(bannerINPUT.getBtn_link());
        bannerExist.setBtn_text(bannerINPUT.getBtn_text());
        bannerExist.setSous_titre(bannerINPUT.getSous_titre());

        // Gestion des images
        if (images != null && !images.isEmpty()) {
            // Supprimer les anciennes images
            bannerImageRepository.deleteByBannerIdBanner(bannerExist.getIdBanner());
            // Ajouter les nouvelles images
            List<BannerImage> bannerImages = new ArrayList<>();
            for (MultipartFile file : images) {
                BannerImage image = new BannerImage();
                image.setName(file.getOriginalFilename());
                image.setType(file.getContentType());
                image.setData(file.getBytes());
                image.setBanner(bannerExist);
                bannerImages.add(image);
            }
            bannerImageRepository.saveAll(bannerImages);
        }
        bannerRepository.save(bannerExist);
    }

    @Override
    public void suppressionBanner(String idBanner) throws ProduitNotFoundException {
        Banner banner = bannerRepository.findById(idBanner)
                .orElseThrow(() -> new ProduitNotFoundException("Cet banner n'existe pas"));
        if (banner.getSupprimerStatus() == SupprimerStatus.TRUE)
            throw new ProduitNotFoundException("Ce banner est supprimer ! ");
        banner.setSupprimerStatus(SupprimerStatus.TRUE);
        bannerRepository.save(banner);
    }
}
