package com.poly.sms.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poly.sms.entity.SiteFeature;
import com.poly.sms.repository.SiteFeatureRepository;
import com.poly.sms.service.SiteFeatureService;

import java.util.List;
import java.util.Optional;

@Service
public class SiteFeatureServiceImpl implements SiteFeatureService {

    @Autowired
    private SiteFeatureRepository siteFeatureRepository;

    @Override
    public List<SiteFeature> getAllSiteFeatures() {
        return siteFeatureRepository.findAll();
    }

    @Override
    public Optional<SiteFeature> getSiteFeatureById(Integer id) {
        return siteFeatureRepository.findById(id);
    }

    @Override
    public SiteFeature saveSiteFeature(SiteFeature siteFeature) {
        return siteFeatureRepository.save(siteFeature);
    }

    @Override
    public void deleteSiteFeature(Integer id) {
        siteFeatureRepository.deleteById(id);
    }
}
