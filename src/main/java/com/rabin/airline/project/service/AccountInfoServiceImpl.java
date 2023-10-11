package com.rabin.airline.project.service;

import com.rabin.airline.project.dto.AccountInfoDto;
import com.rabin.airline.project.entity.AccountInfo;
import com.rabin.airline.project.repository.AccountInfoRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountInfoServiceImpl implements AccountInfoService {

    @Autowired
    private AccountInfoRepository accountInfoRepository;


    @Override
    public AccountInfoDto saveAccountInforamtion(AccountInfoDto accountInfoDto) {
        AccountInfo accountInfo = new AccountInfo();
        BeanUtils.copyProperties(accountInfoDto, accountInfo);
        accountInfoRepository.save(accountInfo);
        AccountInfoDto accountInfoDto1 = new AccountInfoDto();
        BeanUtils.copyProperties(accountInfo, accountInfoDto1);
        return accountInfoDto1;
    }

    @Override
    public List<AccountInfoDto> getAllAccountInforamtion() {
        return accountInfoRepository.findAll().stream().map(accountInfo -> {
            AccountInfoDto accountInfoDto=new AccountInfoDto();
            BeanUtils.copyProperties(accountInfo,accountInfoDto);
            return accountInfoDto;
        }).collect(Collectors.toList());
    }
}
