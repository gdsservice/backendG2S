package com.gds.Gestion.de.stock.services;

import java.util.List;

public interface InterfaceBannerImage {

    byte[] getMainImageForBanner(String idBanner);
    List<byte[]> getImagesForBanner(String idBanner);
}
