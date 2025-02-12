package com.avitam.fantasy11.repository;

import com.avitam.fantasy11.model.Team;
import com.avitam.fantasy11.model.UserTeams;
import com.avitam.fantasy11.repository.generic.GenericImportRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("UserTeamsRepository")
public interface UserTeamsRepository extends GenericImportRepository<UserTeams> {
    Optional<UserTeams> findById(String id);

    @Query("{matchId:?0)}")
    List<Team> getTeamsByMatchId(String matchId);

    UserTeams findByRecordId(String recordId);

    void deleteByRecordId(String recordId);

    List<UserTeams> findByStatusOrderByIdentifier(Boolean b);
}