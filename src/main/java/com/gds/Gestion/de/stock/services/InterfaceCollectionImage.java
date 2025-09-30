package com.gds.Gestion.de.stock.services;

import java.util.List;

public interface InterfaceCollectionImage {
    List<byte[]> getImagesForCollection(String idCollection);

    byte[] getMainImageForCollection(String idCollection);
}
