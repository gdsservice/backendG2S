package com.gds.Gestion.de.stock.controllers;

import com.gds.Gestion.de.stock.DAOs.ProduitDAO;
import com.gds.Gestion.de.stock.services.InterfaceProduit;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@AllArgsConstructor
public class SitemapController {

    private final InterfaceProduit interfaceProduit;

    @GetMapping(value = "/sitemap.xml", produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<String> generateSitemap() {
        String baseUrl = "https://bamakogadget.com";
        List<ProduitDAO> produitDAO = interfaceProduit.ListerProd();

        StringBuilder sitemap = new StringBuilder();
        sitemap.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
        sitemap.append("<urlset xmlns=\"http://www.sitemaps.org/schemas/sitemap/0.9\">\n");

        // Page d’accueil
        sitemap.append("  <url>\n");
        sitemap.append("    <loc>").append(baseUrl).append("/</loc>\n");
        sitemap.append("    <lastmod>").append(LocalDate.now()).append("</lastmod>\n");
        sitemap.append("    <changefreq>daily</changefreq>\n");
        sitemap.append("    <priority>1.0</priority>\n");
        sitemap.append("  </url>\n");

        // Ajout des produits
        for (ProduitDAO produit : produitDAO) {
            sitemap.append("  <url>\n");
            sitemap.append("    <loc>").append(baseUrl)
                    .append("/produitDAO/").append(produit.getSlug()).append("</loc>\n");
            sitemap.append("    <lastmod>").append(LocalDate.now()).append("</lastmod>\n");
            sitemap.append("    <changefreq>weekly</changefreq>\n");
            sitemap.append("    <priority>0.8</priority>\n");
            sitemap.append("  </url>\n");
        }

        sitemap.append("</urlset>");

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_XML)
                .body(sitemap.toString());
    }
}
