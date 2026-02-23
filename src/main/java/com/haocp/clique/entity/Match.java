package com.haocp.clique.entity;

import com.haocp.clique.entity.enums.MatchStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "matches", uniqueConstraints = {
        @UniqueConstraint(columnNames = { "user1_id", "user2_id" })
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Match {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    @Builder.Default
    private MatchStatus status = MatchStatus.ACTIVE;

    @Column(name = "matched_at", nullable = false, updatable = false)
    private LocalDateTime matchedAt;

    @Column(name = "unmatched_at")
    private LocalDateTime unmatchedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user1_id", nullable = false)
    private User user1;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user2_id", nullable = false)
    private User user2;

    @PrePersist
    protected void onCreate() {
        matchedAt = LocalDateTime.now();
    }
}
