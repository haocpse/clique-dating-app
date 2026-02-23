package com.haocp.clique.entity;

import com.haocp.clique.entity.enums.*;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "user_profiles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name", nullable = false, length = 50)
    private String firstName;

    @Column(name = "last_name", length = 50)
    private String lastName;

    @Column(name = "display_name", length = 50)
    private String displayName;

    @Column(nullable = false)
    private LocalDate birthday;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Gender gender;

    @Column(columnDefinition = "TEXT")
    private String bio;

    @Column(length = 100)
    private String city;

    @Column(length = 100)
    private String country;

    @Column(length = 30)
    private String occupation;

    @Column(length = 100)
    private String company;

    @Column(length = 100)
    private String school;

    @Column(name = "height_cm")
    private Integer heightCm;

    @Enumerated(EnumType.STRING)
    @Column(name = "drinking_habit", length = 20)
    private DrinkingHabit drinkingHabit;

    @Enumerated(EnumType.STRING)
    @Column(name = "smoking_habit", length = 20)
    private SmokingHabit smokingHabit;

    @Enumerated(EnumType.STRING)
    @Column(name = "zodiac_sign", length = 20)
    private ZodiacSign zodiacSign;

    @Enumerated(EnumType.STRING)
    @Column(name = "interested_in", length = 20)
    private Gender interestedIn;

    @Column(name = "min_age_preference")
    @Builder.Default
    private Integer minAgePreference = 18;

    @Column(name = "max_age_preference")
    @Builder.Default
    private Integer maxAgePreference = 50;

    @Column(name = "max_distance_km")
    @Builder.Default
    private Integer maxDistanceKm = 50;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
