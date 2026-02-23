package com.haocp.clique.dto.request.user;

import com.haocp.clique.entity.enums.DrinkingHabit;
import com.haocp.clique.entity.enums.Gender;
import com.haocp.clique.entity.enums.SmokingHabit;
import com.haocp.clique.entity.enums.ZodiacSign;
import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateUserProfileRequest {

    @Size(max = 20, message = "Phone number must be less than 20 characters")
    String phoneNumber;

    @NotBlank(message = "First name is required")
    @Size(max = 50, message = "First name must be less than 50 characters")
    String firstName;

    @Size(max = 50, message = "Last name must be less than 50 characters")
    String lastName;

    @Size(max = 50, message = "Display name must be less than 50 characters")
    String displayName;

    @NotNull(message = "Birthday is required")
    @Past(message = "Birthday must be in the past")
    LocalDate birthday;

    @NotNull(message = "Gender is required")
    Gender gender;

    @Size(max = 1000, message = "Bio is too long")
    String bio;

    @Size(max = 100)
    String city;

    @Size(max = 100)
    String country;

    @Size(max = 30)
    String occupation;

    @Size(max = 100)
    String company;

    @Size(max = 100)
    String school;

    @Min(value = 50, message = "Height must be at least 50cm")
    @Max(value = 300, message = "Height must be less than 300cm")
    Integer heightCm;

    DrinkingHabit drinkingHabit;
    SmokingHabit smokingHabit;
    ZodiacSign zodiacSign;

    Gender interestedIn;

    @Min(18)
    @Max(50)
    @Builder.Default
    Integer minAgePreference = 18;

    @Min(18)
    @Max(50)
    @Builder.Default
    Integer maxAgePreference = 50;

}
