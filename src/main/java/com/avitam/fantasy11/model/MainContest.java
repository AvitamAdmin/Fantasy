package com.avitam.fantasy11.model;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@NoArgsConstructor
@Document("MainContest")
public class MainContest extends BaseEntity {
    private String mainContestName;
    private String mainContestId;
}