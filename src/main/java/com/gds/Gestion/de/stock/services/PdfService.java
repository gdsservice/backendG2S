package com.gds.Gestion.de.stock.services;

import com.gds.Gestion.de.stock.DAOs.VenteDAO;
import com.gds.Gestion.de.stock.entites.VenteProduit;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.*;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.property.TextAlignment;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Cell;

@Service
public class PdfService {

    public byte[] generateVentePdf(VenteDAO venteDAO) {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            PdfWriter writer = new PdfWriter(out);

            // Format A8 : 52mm x 74mm ≈ 147.4pt x 209.8pt
            PdfDocument pdf = new PdfDocument(writer);
            pdf.setDefaultPageSize(new PageSize(147.4f, 209.8f)); // format A8

            Document document = new Document(pdf);
            DeviceRgb roseFonce = new DeviceRgb(255, 105, 180); // Rose foncé

            Paragraph header = new Paragraph()
                    .add("BAMAKO GADGETS")
                    .setBold()
                    .setFontSize(8)
                    .setFontColor(roseFonce)
                    .setMarginBottom(4)
                    .setTextAlignment(TextAlignment.CENTER);

            document.add(header);

            document.add(new Paragraph("Appareils, Électroniques,\nÉlectroménagers, Quincaillerie, Voiture")
                    .setTextAlignment(TextAlignment.CENTER)
                    .setFontSize(5)
                    .setMarginBottom(4));

            document.add(new Paragraph("Adresse : Bamako Coura, Hôtel Nuima Beleza\nTel : +22384008969\nEmail : g2sservices1@gmail.com")
                    .setTextAlignment(TextAlignment.CENTER)
                    .setFontSize(5)
                    .setMarginBottom(6));

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy", Locale.FRENCH);
            String dateVenteFormat = venteDAO.getVente().getDateVente().format(formatter);

            document.add(new Paragraph("Client : " + venteDAO.getVente().getClientsVente().getPrenom() + " " + venteDAO.getVente().getClientsVente().getNom()
                    + "\nTel : " + venteDAO.getVente().getClientsVente().getTelephone()
                    + "\nDate : " + dateVenteFormat)
                    .setFontSize(5)
                    .setBold()
                    .setMarginBottom(5));

            float[] columnWidths = {50f, 20f, 30f, 40f};
            Table table = new Table(columnWidths).setFontSize(5);

            table.addCell(new Cell().add(new Paragraph("Désignation").setBold()));
            table.addCell(new Cell().add(new Paragraph("Qté").setBold()));
            table.addCell(new Cell().add(new Paragraph("P.U").setBold()));
            table.addCell(new Cell().add(new Paragraph("Montant").setBold()));

            for (VenteProduit venteProduit : venteDAO.getVenteProduitList()) {
                table.addCell(new Cell().add(new Paragraph(venteProduit.getProduit().getDesignation())));
                table.addCell(new Cell().add(new Paragraph(String.valueOf(venteProduit.getQuantite()))));
                table.addCell(new Cell().add(new Paragraph(String.valueOf(venteProduit.getProduit().getPrixUnitaire()))));
                table.addCell(new Cell().add(new Paragraph(String.valueOf(venteProduit.getMontant()))));
            }

            document.add(table.setMarginBottom(4));

            float[] totalColumnWidths = {120f};
            Table totalTable = new Table(totalColumnWidths)
                    .setFontSize(5)
                    .setTextAlignment(TextAlignment.RIGHT);

            totalTable.addCell(new Cell()
                    .add(new Paragraph("Réduction : " + venteDAO.getVente().getReduction() + " FCFA").setBold())
                    .setBorderBottom(new SolidBorder(0.3f)));

            totalTable.addCell(new Cell()
                    .add(new Paragraph("Total à payer : " + venteDAO.getVente().getMontant() + " FCFA").setBold()));

            document.add(totalTable);
            document.close();

            return out.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

//    public String generateTicketTexte(VenteDAO venteDAO) {
//        StringBuilder ticket = new StringBuilder();
//
//        ticket.append(center("BAMAKO GADGETS")).append("\n");
//        ticket.append(center("Appareils, Électroniques")).append("\n");
//        ticket.append(center("Électroménagers, Quincaillerie")).append("\n");
//        ticket.append(center("Tel: +22384008969")).append("\n");
//        ticket.append("--------------------------------\n");
//
//        ticket.append("Client : ").append(venteDAO.getVente().getClientsVente().getPrenom())
//                .append(" ").append(venteDAO.getVente().getClientsVente().getNom()).append("\n");
//        ticket.append("Tel    : ").append(venteDAO.getVente().getClientsVente().getTelephone()).append("\n");
//        ticket.append("Date   : ").append(venteDAO.getVente().getDateVente().toString()).append("\n");
//        ticket.append("--------------------------------\n");
//
//        ticket.append(String.format("%-12s %3s %5s %7s\n", "Produit", "Qté", "P.U", "Total"));
//        ticket.append("--------------------------------\n");
//
//        for (VenteProduit vp : venteDAO.getVenteProduitList()) {
//            String designation = vp.getProduit().getDesignation();
//            int qte = vp.getQuantite();
//            double pu = vp.getProduit().getPrixUnitaire();
//            double montant = vp.getMontant();
//
//            ticket.append(String.format("%-12s %3d %5.0f %7.0f\n", shorten(designation, 12), qte, pu, montant));
//        }
//
//        ticket.append("--------------------------------\n");
//        ticket.append("Réduction : ").append(venteDAO.getVente().getReduction()).append(" FCFA\n");
//        ticket.append("Total     : ").append(venteDAO.getVente().getMontant()).append(" FCFA\n");
//        ticket.append("--------------------------------\n");
//        ticket.append(center("Merci pour votre achat !")).append("\n");
//
//        return ticket.toString();
//    }
//
//    private String center(String text) {
//        int width = 32;
//        int padding = (width - text.length()) / 2;
//        return " ".repeat(Math.max(padding, 0)) + text;
//    }
//
//    private String shorten(String text, int maxLength) {
//        return text.length() > maxLength ? text.substring(0, maxLength - 1) + "…" : text;
//    }



}
