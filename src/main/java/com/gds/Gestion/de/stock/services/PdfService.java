package com.gds.Gestion.de.stock.services;

import com.gds.Gestion.de.stock.DAO.VenteDAO;
import com.gds.Gestion.de.stock.entites.Produit;
import com.gds.Gestion.de.stock.entites.VenteProduit;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.pdf.*;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.DashedBorder;
import com.itextpdf.layout.borders.FixedDashedBorder;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.property.BorderRadius;
import com.itextpdf.layout.property.TextAlignment;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Cell;

@Service
public class PdfService {

    public byte[] generateVentePdf(VenteDAO venteDAO) {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            PdfWriter writer = new PdfWriter(out);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            //Couleur et format
            DeviceRgb roseFonce = new DeviceRgb(255, 105, 180); // Rose foncé

            // En-tête de l'entreprise
            Paragraph header = new Paragraph()
                    .add("BAMAKO GADGETS")
                    .setBold()
                    .setFontSize(20)
                    .setFontColor(roseFonce)
                    .setMarginBottom(10)
                    .setTextAlignment(TextAlignment.CENTER);

            document.add(header);

            document.add(new Paragraph("Votre fournisseur de confiance en Appareils Informatiques, Électroniques, \n" +
                    ("Électroménagers, Quincailleries, Accessoires de Voiture et Divers. \n" +
                            "Bamako Coura, non loin de l'Hôtel de Nuima Beleza,\n " +
                            "vers la rue Mamadou Konaté, rue 352, porte 121, Résidence DEBE. \n " +
                            " Téléphone : +223 84 00 89 69 / 83 52 28 56 / 77 21 20 47 \n " +
                            "Email : g2sservices1@gmail.com | Site web : www.bamakogadgets.com"
                    ))
                    .setTextAlignment(TextAlignment.CENTER)
                    .setMarginBottom(30)
                    .setFontSize(10));

            // 🏠 Informations du Client
            // Détails de la Vente
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy", Locale.FRENCH);
            String dateVenteFormat = venteDAO.getVente().getDateVente().format(formatter);
            document.add(new Paragraph("Doit : " + venteDAO.getVente().getClientsVente().getPrenom() + " " + venteDAO.getVente().getClientsVente().getNom()
                    + "\nTéléphone : " + venteDAO.getVente().getClientsVente().getTelephone()
                    + "\nDate de vente : " + dateVenteFormat)
                            .setBold()
                    .setFontSize(10));

            // Ajout d'un espace après l'en-tête
            document.add(new Paragraph("\n"));


            // Création du tableau avec 4 colonnes
            float[] columnWidths = {200f, 200f, 200f, 200f};
            Table table = new Table(columnWidths);

            table.addCell(new Cell().add(new Paragraph("Désignation").setFontSize(10).setBold()));
            table.addCell(new Cell().add(new Paragraph("Quantité").setFontSize(10).setBold()));
            table.addCell(new Cell().add(new Paragraph("Prix Unitaire (FCFA)").setFontSize(10).setBold()));
            table.addCell(new Cell().add(new Paragraph("Montant (FCFA)").setFontSize(10).setBold()));

            // Ajout des produits au tableau
            for (VenteProduit venteProduit : venteDAO.getVenteProduitList()) {
                table.addCell(new Cell().add(new Paragraph(venteProduit.getProduit().getDesignation()).setFontSize(10)));
                table.addCell(new Cell().add(new Paragraph(String.valueOf(venteProduit.getQuantite())).setFontSize(10)));
                table.addCell(new Cell().add(new Paragraph(String.valueOf(venteProduit.getProduit().getPrixUnitaire())).setFontSize(10)));
                table.addCell(new Cell().add(new Paragraph(String.valueOf(venteProduit.getMontant()))).setFontSize(10));
            }

            // Ajout du tableau au document
            document.add(table);

            // Définition de la largeur des colonnes
            float[] clientVenteColumnWidths = {257f};
            Table clientVenteTable = new Table(clientVenteColumnWidths)
                    .setTextAlignment(TextAlignment.LEFT)
                    .setHorizontalAlignment(com.itextpdf.layout.property.HorizontalAlignment.RIGHT);

            // Réduction
            clientVenteTable.addCell(new Cell()
                    .add(new Paragraph("Réduction :     " + venteDAO.getVente().getReduction() + "  FCFA").setBold().setFontSize(10).setMargin(5))
                    .setBorderBottom(new SolidBorder(1)));

            // 🏠 Montant à payer
            clientVenteTable.addCell(new Cell()
                    .add(new Paragraph("Montant à payer :     " + venteDAO.getVente().getMontant() + "  FCFA").setBold().setFontSize(10).setMargin(5)));

            // Ajout du tableau au document
            document.add(clientVenteTable);



            document.close();
            return out.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
