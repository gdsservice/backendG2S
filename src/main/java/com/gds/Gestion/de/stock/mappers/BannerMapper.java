package com.gds.Gestion.de.stock.mappers;

import com.gds.Gestion.de.stock.DAOs.BannerDAO;
import com.gds.Gestion.de.stock.Input.BannerINPUT;
import com.gds.Gestion.de.stock.entites.Banner;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class BannerMapper {

    public Banner mapBannerINPUTABanner(BannerINPUT bannerINPUT){
        Banner banner = new Banner();
        BeanUtils.copyProperties(bannerINPUT, banner);
        return banner;
    }

    public BannerDAO mapBannerADAO(Banner banner) {
        BannerDAO bannerDAO  = new BannerDAO();
        BeanUtils.copyProperties(banner, bannerDAO);
        return bannerDAO;
    }
}
