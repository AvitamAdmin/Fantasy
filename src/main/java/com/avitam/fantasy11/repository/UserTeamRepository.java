package com.avitam.fantasy11.repository;

import com.avitam.fantasy11.model.Team;
import com.avitam.fantasy11.model.UserTeam;
import com.avitam.fantasy11.repository.generic.GenericImportRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("UserTeamRepository")
public interface UserTeamRepository extends GenericImportRepository<UserTeam> {
    Optional<UserTeam> findById(String id);

    @Query("{matchId:?0)}")
    List<Team>getTeamsByMatchId(String matchId);

    UserTeam findByRecordId(String recordId);

    void deleteByRecordId(String recordId);
    List<UserTeam> findByStatusOrderByIdentifier(Boolean b);
}