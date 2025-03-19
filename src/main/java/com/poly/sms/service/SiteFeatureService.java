package com.poly.sms.service;

import java.util.List;
import java.util.Optional;

import com.poly.sms.entity.SiteFeature;

public interface SiteFeatureService {
    List<SiteFeature> getAllSiteFeatures();

    Optional<SiteFeature> getSiteFeatureById(Integer id);

    SiteFeature saveSiteFeature(SiteFeature siteFeature);

    void deleteSiteFeature(Integer id);
}
