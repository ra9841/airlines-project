package com.rabin.airline.project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountInfoDto {
    private long accId;
    private String accountNo;
    private double accAmount;
}
