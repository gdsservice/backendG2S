package com.gds.Gestion.de.stock.services;

import java.util.List;

public interface InterfaceProduitImage {

    byte[] getMainImageForProduit(String idProd);
    List<byte[]> getImagesForProduit(String idProd);
}
