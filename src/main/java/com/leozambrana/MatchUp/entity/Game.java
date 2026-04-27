package com.leozambrana.MatchUp.entity;

import com.leozambrana.MatchUp.enums.*;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "games")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String title;
    private String description;
    private String location;

    @Column(name = "date_time")
    private LocalDateTime dateTime;

    @Enumerated(EnumType.STRING)
    @Column(name = "court_type")
    private CourtType courtType;

    @Enumerated(EnumType.STRING)
    @Column(name = "match_format")
    private MatchFormat matchFormat;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    private MatchGender gender;

    @Column(name = "slots")
    private Integer slots;

    @Enumerated(EnumType.STRING)
    @Column(name = "duration")
    private MatchDuration duration;

    @Enumerated(EnumType.STRING)
    @Column(name = "recurrence")
    private RecurrenceType recurrence;

    @Column(name = "invite_code", unique = true)
    private String inviteCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by")
    private User createdBy;

    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "game", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Participant> participants;

    @OneToMany(mappedBy = "game", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Expense> expenses;
}