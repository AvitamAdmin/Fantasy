package com.avitam.fantasy11.api.dto;

import com.avitam.fantasy11.model.Address;
import com.avitam.fantasy11.model.WithdrawalMethods;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class WithdrawalMethodsDto extends CommonDto{

    private WithdrawalMethods withdrawalMethods;
    private List<WithdrawalMethods> WithdrawalMethodsList;
    private MultipartFile logo;
}
