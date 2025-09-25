package com.gds.Gestion.de.stock.controllers;

import com.gds.Gestion.de.stock.DAOs.ProduitDAO;
import com.gds.Gestion.de.stock.services.InterfaceProduit;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@AllArgsConstructor
public class SitemapController {

    private final InterfaceProduit interfaceProduit;

    @GetMapping(value = "/sitemap.xml", produces = MediaType.APPLICATION_XML_VALUE)
//    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN')")
    public String generateSitemap() {
        String baseUrl = "https://bamakogadget.com";
        List<ProduitDAO> produits = interfaceProduit.ListerProd();

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
        for (ProduitDAO produit : produits) {
            sitemap.append("  <url>\n");
            sitemap.append("    <loc>").append(baseUrl)
                    .append("/produits/").append(produit.getSlug()).append("</loc>\n");
            sitemap.append("    <lastmod>").append(LocalDate.now()).append("</lastmod>\n");
            sitemap.append("    <changefreq>weekly</changefreq>\n");
            sitemap.append("    <priority>0.8</priority>\n");
            sitemap.append("  </url>\n");
        }

        sitemap.append("</urlset>");
        System.out.println("===========================");
        System.out.println(sitemap.toString());
        System.out.println("===========================");
        return sitemap.toString();
    }
}
