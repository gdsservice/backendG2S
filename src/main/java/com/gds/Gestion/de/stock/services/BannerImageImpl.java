package com.gds.Gestion.de.stock.services;

import com.gds.Gestion.de.stock.entites.BannerImage;

import com.gds.Gestion.de.stock.repositories.BannerImageRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class BannerImageImpl implements InterfaceBannerImage {

    private BannerImageRepository bannerImageRepository;

    @Override
    public byte[] getMainImageForBanner(String idBanner) {
        return bannerImageRepository.findFirstByBannerIdBanner(idBanner).getData();
    }

    @Override
    public List<byte[]> getImagesForBanner(String idBanner) {
        return bannerImageRepository.findByBannerIdBanner(idBanner)
                .stream()
                .map(BannerImage::getData)
                .collect(Collectors.toList());
    }
}
