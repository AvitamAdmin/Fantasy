package com.avitam.fantasy11.api.service.impl;

import com.avitam.fantasy11.api.dto.UserTeamsDto;
import com.avitam.fantasy11.api.dto.UserTeamsWsDto;
import com.avitam.fantasy11.api.service.BaseService;
import com.avitam.fantasy11.api.service.UserTeamsService;
import com.avitam.fantasy11.model.*;
import com.avitam.fantasy11.repository.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class UserTeamsServiceImpl implements UserTeamsService {

    public static final String ADMIN_USERTEAM = "/admin/userTeam";
    @Autowired
    private UserTeamsRepository userTeamsRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private BaseService baseService;
    @Autowired
    private PlayerRepository playerRepository;
    @Autowired
    private PlayerRoleRepository playerRoleRepository;
    @Autowired
    private MatchesRepository matchesRepository;
    @Autowired
    private TeamRepository teamRepository;

    @Override
    public UserTeams findByRecordId(String recordId) {
        return userTeamsRepository.findByRecordId(recordId);
    }

    @Override
    public void deleteByRecordId(String recordId) {
        userTeamsRepository.deleteByRecordId(recordId);
    }

    @Override
    public void updateByRecordId(String recordId) {
        UserTeams userTeams = userTeamsRepository.findByRecordId(recordId);
        if (userTeams != null) {
            userTeamsRepository.save(userTeams);
        }
    }

    @Override
    public UserTeamsWsDto handleEdit(UserTeamsWsDto request) {
        List<UserTeamsDto> userTeamDtos = request.getUserTeamsDtoList();
        List<UserTeams> userTeamsList = new ArrayList<>();
        UserTeams userTeams = null;

        for (UserTeamsDto userTeamsDto : userTeamDtos) {
            if (userTeamsDto.getRecordId() != null) {
                userTeams = userTeamsRepository.findByRecordId(userTeamsDto.getRecordId());
                modelMapper.map(userTeamsDto, userTeams);
                userTeams.setLastModified(new Date());
                userTeamsRepository.save(userTeams);
                request.setMessage("Data updated Successfully");
            } else {
//                if (baseService.validateIdentifier(EntityConstants.USER_TEAMS, userTeamsDto.getIdentifier()) != null) {
//                    request.setSuccess(false);
//                    request.setMessage("Identifier already present");
//                    return request;
//                }
                userTeams = modelMapper.map(userTeamsDto, UserTeams.class);

                //baseService.populateCommonData(userTeams);
                userTeams.setStatus(true);
                userTeamsRepository.save(userTeams);
                if (userTeams.getRecordId() == null) {
                    userTeams.setRecordId(String.valueOf(userTeams.getId().getTimestamp()));
                }
                userTeamsRepository.save(userTeams);
                request.setMessage("Data added Successfully");
            }
            userTeamsList.add(userTeams);
            request.setBaseUrl(ADMIN_USERTEAM);

        }
        request.setUserTeamsDtoList(modelMapper.map(userTeamsList, List.class));
        return request;
    }

    @Override
    public UserTeamsWsDto getUserTeamsDetails(UserTeamsWsDto request) {
        int batsMan = 0, bowler = 0, wicketKeeper = 0, allRounder = 0;
        UserTeamsDto userTeamsDto = new UserTeamsDto();
        UserTeams userTeams = userTeamsRepository.findByRecordId(request.getUserTeamsDtoList().get(0).getRecordId());

        for (UserTeam userTeam : userTeams.getPlayers()) {
            Player player = playerRepository.findByRecordId(userTeam.getPlayerId());
            if (playerRoleRepository.findByRecordId(player.getPlayerRoleId()).getIdentifier().equalsIgnoreCase("Batsman")) {
                batsMan++;
            } else if (playerRoleRepository.findByRecordId(player.getPlayerRoleId()).getIdentifier().equalsIgnoreCase("WicketKeeper")) {
                wicketKeeper++;
            } else if (playerRoleRepository.findByRecordId(player.getPlayerRoleId()).getIdentifier().equalsIgnoreCase("Bowler")) {
                bowler++;
            } else if (playerRoleRepository.findByRecordId(player.getPlayerRoleId()).getIdentifier().equalsIgnoreCase("AllRounder")) {
                allRounder++;
            }
            userTeamsDto.setBatsManCount(batsMan);
            userTeamsDto.setWicketKeeperCount(wicketKeeper);
            userTeamsDto.setBowlerCount(bowler);
            userTeamsDto.setAllRounderCount(allRounder);
        }

        int team1 = 0, team2 = 0;
        for (UserTeam userTeam : userTeams.getPlayers()) {
            Matches match = matchesRepository.findByRecordId(userTeams.getMatchId());
            Player player = playerRepository.findByRecordId(userTeam.getPlayerId());
            if (match.getTeam1Id().equals(player.getTeamId())) {
                team1++;
                userTeamsDto.setTeam1Count(team1);
            } else {
                match.getTeam2Id().equals(player.getTeamId());
                team2++;
                userTeamsDto.setTeam2Count(team2);
            }
            Team teamName1 = teamRepository.findByRecordId(match.getTeam1Id());
            userTeamsDto.setTeam1Name(teamName1.getShortName());

            Team teamName2 = teamRepository.findByRecordId(match.getTeam2Id());
            userTeamsDto.setTeam2Name(teamName2.getShortName());
        }


        request.setUserTeamsDtoList(List.of(userTeamsDto));
        return request;
    }
}
