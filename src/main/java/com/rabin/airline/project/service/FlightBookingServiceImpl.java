package com.rabin.airline.project.service;

import com.rabin.airline.project.dto.FlightBookingAcknowledgement;
import com.rabin.airline.project.dto.FlightBookingRequest;

import com.rabin.airline.project.entity.PassengerInfo;
import com.rabin.airline.project.entity.PaymentInfo;
import com.rabin.airline.project.repository.PassengerInfoRepository;
import com.rabin.airline.project.repository.PaymentInfoRepository;
import com.rabin.airline.project.utils.AccountUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.security.auth.login.AccountNotFoundException;
import javax.transaction.Transactional;
import java.util.UUID;

@Service
public class FlightBookingServiceImpl implements FlightBookingService {

    @Autowired
    private PassengerInfoRepository passengerInfoRepository;

    @Autowired
    private PaymentInfoRepository paymentInfoRepository;

    @Autowired
    private AccountUtils accountUtils;

    @Transactional
    @Override
    public FlightBookingAcknowledgement bookFlightTicket(FlightBookingRequest flightBookingRequest) throws AccountNotFoundException {

        PassengerInfo passengerInfo=passengerInfoRepository.save(flightBookingRequest.getPassengerInfo());

        PaymentInfo paymentInfo = flightBookingRequest.getPaymentInfo();


        accountUtils.validateCreditLimit(paymentInfo.getAccountNo(),passengerInfo.getFare());


        paymentInfo.setPassengerId(passengerInfo.getPId());
        paymentInfo.setAmount(passengerInfo.getFare());
        paymentInfoRepository.save(paymentInfo);

        return new FlightBookingAcknowledgement("SUCESS",passengerInfo.getFare(), UUID.randomUUID().toString().split("-")[0],passengerInfo);
    }
}

