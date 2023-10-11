package com.rabin.airline.project.controller;


import com.rabin.airline.project.dto.AccountInfoDto;
import com.rabin.airline.project.dto.FlightBookingAcknowledgement;
import com.rabin.airline.project.dto.FlightBookingRequest;
import com.rabin.airline.project.service.AccountInfoService;
import com.rabin.airline.project.service.FlightBookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.AccountNotFoundException;
import java.util.List;

@RestController
@RequestMapping("/airlines")
public class AirlineController {
    @Autowired
    private AccountInfoService accountInfoService;

    @Autowired
    private FlightBookingService flightBookingService;



    @PostMapping("/deposits")
    public AccountInfoDto accountInformation(@RequestBody AccountInfoDto accountInfoDto){
      return  accountInfoService.saveAccountInforamtion(accountInfoDto);
    }

    @GetMapping
    public List<AccountInfoDto> getAllAccountInformationRecord(){
        return accountInfoService.getAllAccountInforamtion();
    }

    @PostMapping("/bookFlightTickets")
    public FlightBookingAcknowledgement bookFlightTicket(@RequestBody FlightBookingRequest flightBookingRequest) throws AccountNotFoundException {
        return flightBookingService.bookFlightTicket(flightBookingRequest);
    }
}
