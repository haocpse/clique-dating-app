package com.haocp.clique.entity;

import com.haocp.clique.entity.enums.LikeType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "likes", uniqueConstraints = {
        @UniqueConstraint(columnNames = { "liker_id", "liked_id" })
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Like {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "like_type", nullable = false, length = 20)
    @Builder.Default
    private LikeType likeType = LikeType.LIKE;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "liker_id", nullable = false)
    private User liker;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "liked_id", nullable = false)
    private User liked;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
