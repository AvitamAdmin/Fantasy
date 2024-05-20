package com.avitam.fantasy11.form;

import com.avitam.fantasy11.model.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Getter
@Setter
public class TournamentForm extends BaseForm{

    private String name;
    @DateTimeFormat(pattern = "dd-MM-yyyy hh:mm")
    private Date dateAndTime;
    private long sportId;
}
