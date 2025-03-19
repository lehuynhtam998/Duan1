package com.poly.sms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.poly.sms.entity.SiteFeature;

@Repository
public interface SiteFeatureRepository extends JpaRepository<SiteFeature, Integer> {
}
