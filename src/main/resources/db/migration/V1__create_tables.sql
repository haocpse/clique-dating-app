CREATE DATABASE IF NOT EXISTS clique_dating;
USE clique_dating;

-- =============================================
-- 1. users
-- =============================================
CREATE TABLE users (
    id              BIGINT          NOT NULL AUTO_INCREMENT,
    email           VARCHAR(100)    NOT NULL,
    password        VARCHAR(255)    NOT NULL,
    phone_number    VARCHAR(20)     NULL,
    enabled         BOOLEAN         NOT NULL DEFAULT TRUE,
    email_verified  BOOLEAN         NOT NULL DEFAULT FALSE,
    auth_provider   VARCHAR(20)     DEFAULT 'LOCAL',
    provider_id     VARCHAR(255)    NULL,
    role            VARCHAR(20)     NOT NULL DEFAULT 'USER',
    last_login      DATETIME        NULL,
    created_at      DATETIME        NOT NULL,
    updated_at      DATETIME        NULL,
    PRIMARY KEY (id),
    UNIQUE KEY uk_users_email (email),
    UNIQUE KEY uk_users_phone (phone_number)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- =============================================
-- 2. user_profiles
-- =============================================
CREATE TABLE user_profiles (
    id                  BIGINT          NOT NULL AUTO_INCREMENT,
    user_id             BIGINT          NOT NULL,
    first_name          VARCHAR(50)     NOT NULL,
    last_name           VARCHAR(50)     NULL,
    display_name        VARCHAR(50)     NULL,
    birthday            DATE            NOT NULL,
    gender              VARCHAR(20)     NOT NULL,
    bio                 TEXT            NULL,
    city                VARCHAR(100)    NULL,
    country             VARCHAR(100)    NULL,
    occupation          VARCHAR(30)     NULL,
    company             VARCHAR(100)    NULL,
    school              VARCHAR(100)    NULL,
    height_cm           INT             NULL,
    drinking_habit      VARCHAR(20)     NULL,
    smoking_habit       VARCHAR(20)     NULL,
    zodiac_sign         VARCHAR(20)     NULL,
    interested_in       VARCHAR(20)     NULL,
    min_age_preference  INT             DEFAULT 18,
    max_age_preference  INT             DEFAULT 50,
    max_distance_km     INT             DEFAULT 50,
    created_at          DATETIME        NOT NULL,
    updated_at          DATETIME        NULL,
    PRIMARY KEY (id),
    UNIQUE KEY uk_user_profiles_user (user_id),
    CONSTRAINT fk_user_profiles_user FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- =============================================
-- 3. user_photos
-- =============================================
CREATE TABLE user_photos (
    id              BIGINT          NOT NULL AUTO_INCREMENT,
    user_id         BIGINT          NOT NULL,
    photo_url       VARCHAR(255)    NOT NULL,
    is_primary      BOOLEAN         NOT NULL DEFAULT FALSE,
    display_order   INT             NOT NULL DEFAULT 0,
    created_at      DATETIME        NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_user_photos_user FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- =============================================
-- 4. likes
-- =============================================
CREATE TABLE likes (
    id          BIGINT          NOT NULL AUTO_INCREMENT,
    liker_id    BIGINT          NOT NULL,
    liked_id    BIGINT          NOT NULL,
    like_type   VARCHAR(20)     NOT NULL DEFAULT 'LIKE',
    created_at  DATETIME        NOT NULL,
    PRIMARY KEY (id),
    UNIQUE KEY uk_likes_pair (liker_id, liked_id),
    CONSTRAINT fk_likes_liker FOREIGN KEY (liker_id) REFERENCES users (id) ON DELETE CASCADE,
    CONSTRAINT fk_likes_liked FOREIGN KEY (liked_id) REFERENCES users (id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- =============================================
-- 5. matches
-- =============================================
CREATE TABLE matches (
    id              BIGINT          NOT NULL AUTO_INCREMENT,
    user1_id        BIGINT          NOT NULL,
    user2_id        BIGINT          NOT NULL,
    status          VARCHAR(20)     NOT NULL DEFAULT 'ACTIVE',
    matched_at      DATETIME        NOT NULL,
    unmatched_at    DATETIME        NULL,
    PRIMARY KEY (id),
    UNIQUE KEY uk_matches_pair (user1_id, user2_id),
    CONSTRAINT fk_matches_user1 FOREIGN KEY (user1_id) REFERENCES users (id) ON DELETE CASCADE,
    CONSTRAINT fk_matches_user2 FOREIGN KEY (user2_id) REFERENCES users (id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- =============================================
-- 6. user_availabilities
-- =============================================
CREATE TABLE user_availabilities (
    id              BIGINT          NOT NULL AUTO_INCREMENT,
    user_id         BIGINT          NOT NULL,
    day_of_week     VARCHAR(15)     NOT NULL,
    specific_date   DATE            NULL,
    start_time      TIME            NOT NULL,
    end_time        TIME            NOT NULL,
    is_recurring    BOOLEAN         NOT NULL DEFAULT TRUE,
    is_active       BOOLEAN         NOT NULL DEFAULT TRUE,
    note            VARCHAR(255)    NULL,
    created_at      DATETIME        NOT NULL,
    updated_at      DATETIME        NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_user_availabilities_user FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- =============================================
-- 7. date_schedules
-- =============================================
CREATE TABLE date_schedules (
    id              BIGINT          NOT NULL AUTO_INCREMENT,
    requester_id    BIGINT          NOT NULL,
    receiver_id     BIGINT          NOT NULL,
    match_id        BIGINT          NOT NULL,
    scheduled_at    DATETIME        NOT NULL,
    location        VARCHAR(255)    NULL,
    message         TEXT            NULL,
    status          VARCHAR(20)     NOT NULL DEFAULT 'PENDING',
    cancelled_by_id BIGINT          NULL,
    cancel_reason   VARCHAR(255)    NULL,
    created_at      DATETIME        NOT NULL,
    updated_at      DATETIME        NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_date_schedules_requester FOREIGN KEY (requester_id) REFERENCES users (id) ON DELETE CASCADE,
    CONSTRAINT fk_date_schedules_receiver FOREIGN KEY (receiver_id) REFERENCES users (id) ON DELETE CASCADE,
    CONSTRAINT fk_date_schedules_match FOREIGN KEY (match_id) REFERENCES matches (id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
