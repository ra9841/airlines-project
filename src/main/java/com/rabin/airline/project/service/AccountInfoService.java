package com.rabin.airline.project.service;

import com.rabin.airline.project.dto.AccountInfoDto;

import java.util.List;

public interface AccountInfoService {
    AccountInfoDto saveAccountInforamtion(AccountInfoDto accountInfoDto);

    List<AccountInfoDto> getAllAccountInforamtion();
}
