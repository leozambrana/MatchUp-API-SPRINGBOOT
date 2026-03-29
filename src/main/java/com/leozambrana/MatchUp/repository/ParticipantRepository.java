package com.leozambrana.MatchUp.repository;

import com.leozambrana.MatchUp.entity.Participant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ParticipantRepository extends JpaRepository<Participant, UUID> {
    List<Participant> findByGameId(UUID gameId);
    Optional<Participant> findByGameIdAndUserId(UUID gameId, UUID userId);
    boolean existsByGameIdAndUserId(UUID gameId, UUID userId);
}