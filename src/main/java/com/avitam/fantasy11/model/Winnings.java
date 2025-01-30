package com.avitam.fantasy11.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@Document("Winnings")
public class Winnings extends CommonFields {

   private Map<String,String> rankWinnings;
   private String contestDetailsId;

}