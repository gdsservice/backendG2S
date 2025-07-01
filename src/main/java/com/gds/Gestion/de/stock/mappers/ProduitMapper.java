package com.gds.Gestion.de.stock.mappers;


import com.gds.Gestion.de.stock.DAOs.ProduitDAO;
import com.gds.Gestion.de.stock.DTOs.ProduitDTO;
import com.gds.Gestion.de.stock.Input.ProduitINPUT;
import com.gds.Gestion.de.stock.entites.Produit;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class ProduitMapper {

//    @Autowired
    private CategorieMapper categorieMapper;

    public ProduitDTO mapDeProdADto(Produit produit) {
        ProduitDTO produitDTO = new ProduitDTO();
        BeanUtils.copyProperties(produit, produitDTO);
        produitDTO.setCategorieStockProdDTO(categorieMapper.mapDeCategorieADto(produit.getCategorieStock()));
//        produitDTO.setUtilisateurProdDto(utilisateurMapper.mapDeUserADto(produit.getUtilisateurProd()));
        return produitDTO;
    }

    public Produit mapDeDtoAProd(ProduitDTO produitDTO) {
        Produit produit = new Produit();
        BeanUtils.copyProperties(produitDTO, produit);
        produit.setCategorieStock(categorieMapper.mapDeDtoACategorie(produitDTO.getCategorieStockProdDTO()));
//        produit.setUtilisateurProd(utilisateurMapper.mapDeDtoAUser(produitDTO.getUtilisateurProdDto()));
        return produit;
    }

    public Produit mapDeDAOAProd(ProduitDAO produitDAO) {
        Produit produit = new Produit();
        BeanUtils.copyProperties(produitDAO, produit);
        produit.setCategorieStock(categorieMapper.mapDeDtoACategorie(produitDAO.getCategorieStockProdDTO()));
        return produit;
    }

    public ProduitDAO mapDeProdADAO(Produit produit) {
        ProduitDAO produitDAO = new ProduitDAO();
        BeanUtils.copyProperties(produit, produitDAO);
        produitDAO.setCategorieStockProdDTO(categorieMapper.mapDeCategorieADto(produit.getCategorieStock()));
        return produitDAO;
    }

    public Produit mapDeINPUTAProd(ProduitINPUT produitINPUT) {
        Produit produit = new Produit();
        BeanUtils.copyProperties(produitINPUT, produit);
        produit.setCategorieStock(categorieMapper.mapDeDtoACategorie(produitINPUT.getCategorieStockProdDTO()));
        return produit;
    }

    public ProduitINPUT mapDeProdAINPUT(Produit produit) {
        ProduitINPUT produitINPUT = new ProduitINPUT();
        BeanUtils.copyProperties(produit, produitINPUT);
        produitINPUT.setCategorieStockProdDTO(categorieMapper.mapDeCategorieADto(produit.getCategorieStock()));
        return produitINPUT;
    }
}
