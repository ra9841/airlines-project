package com.rabin.airline.project.utils;

import com.rabin.airline.project.dto.AccountInfoDto;


import com.rabin.airline.project.exception.InsufficientAmountException;
import com.rabin.airline.project.service.AccountInfoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.security.auth.login.AccountNotFoundException;
import java.util.List;

@Component
public class AccountUtils {

    @Autowired
    AccountInfoServiceImpl accountInfoService;

//    public  boolean validateCreditLimit(String accountNo, double fare) {
//        List<AccountInfoDto> accountInfos = accountInfoService.getAllAccountInforamtion();
//
//        boolean sufficientFunds =false;  // Initialize to false, assuming insufficient funds by default
//
//        for (AccountInfoDto accountInfoDto : accountInfos) {
//            if (fare <= accountInfoDto.getAccAmount()) {
//                sufficientFunds=true;
//                break;
//
//            }
//        }
//        if(!sufficientFunds){
//            throw new InsufficientAmountException("Insufficient fund.......!");
//        }
//        return true;  // If we reach here, there are sufficient funds in at least one account
//    }


//    public boolean validateCreditLimit(String accountNo, double fare) throws AccountNotFoundException {
//        List<AccountInfoDto> accountInfos = accountInfoService.getAllAccountInforamtion();
//
//        for (AccountInfoDto accountInfoDto : accountInfos) {
//            if (accountNo.equals(accountInfoDto.getAccountNo())) {
//                if (fare <= accountInfoDto.getAccAmount()) {
//                    return true;  // Return true if fare is less than or equal to any account amount
//                } else {
//                    throw new InsufficientAmountException("Insufficient fund......!"); // Throw exception if fare is greater than all account amounts
//                }
//            }
//
//        }
//
//        // If the account number is not found in the list, you can handle it as per your requirements.
//        throw new AccountNotFoundException("Account not found: ");
//    }


    //using java 8
    public boolean validateCreditLimit(String accountNo, double fare) throws AccountNotFoundException {
        List<AccountInfoDto> accountInfos = accountInfoService.getAllAccountInforamtion();

        // Use a stream to find the matching account info by account number
        AccountInfoDto accountInfoDto = accountInfos.stream()
                .filter(info -> accountNo.equals(info.getAccountNo()))
                .findFirst()
                .orElseThrow(() -> new AccountNotFoundException("Account not found "));

        if (fare <= accountInfoDto.getAccAmount()) {
            return true;     // Return true if fare is within the credit limit
        } else {
            throw new InsufficientAmountException("Insufficient fund.....!");    // Throw exception if fare exceeds the credit limit
        }
    }
}


