package com.haocp.clique.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "partner_images")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PartnerImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "partner_id")
    private Partner partner;

    @Column(nullable = false)
    private String imageUrl;

}
