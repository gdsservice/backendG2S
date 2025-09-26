package com.gds.Gestion.de.stock.services;

import com.gds.Gestion.de.stock.DAOs.BannerDAO;
import com.gds.Gestion.de.stock.Input.BannerINPUT;
import com.gds.Gestion.de.stock.exceptions.EmptyException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface InterfaceBanner {

    void addBanner(BannerINPUT bannerINPUT, List<MultipartFile> images) throws EmptyException, IOException;

    List<BannerDAO> listBanner();
}
