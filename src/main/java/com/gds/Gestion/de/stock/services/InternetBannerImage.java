package com.gds.Gestion.de.stock.services;

import java.util.List;

public interface InternetBannerImage {

    byte[] getMainImageForBanner(String idBanner);
    List<byte[]> getImagesForBanner(String idBanner);
}
