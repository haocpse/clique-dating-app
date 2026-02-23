package com.haocp.clique.dto.request.user;

import com.haocp.clique.entity.enums.DrinkingHabit;
import com.haocp.clique.entity.enums.Gender;
import com.haocp.clique.entity.enums.SmokingHabit;
import com.haocp.clique.entity.enums.ZodiacSign;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateUserProfileRequest {

    String phoneNumber;
    String firstName;
    String lastName;
    String displayName;
    LocalDate birthday;
    Gender gender;
    String bio;
    String city;
    String country;
    String occupation;
    String company;
    String school;
    Integer heightCm;
    DrinkingHabit drinkingHabit;
    SmokingHabit smokingHabit;
    ZodiacSign zodiacSign;
    Gender interestedIn;
    Integer minAgePreference;
    Integer maxAgePreference;
    Integer maxDistanceKm;

}
