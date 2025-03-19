package com.poly.sms.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Entity
@Table(name = "site_features")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SiteFeature {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "feature_name", nullable = false, length = 50)
    private String featureName;

    @Column(name = "feature_description", nullable = false, length = 50)
    private String featureDescription;

    @Column(name = "icon_html", columnDefinition = "text")
    private String iconHtml;
}
