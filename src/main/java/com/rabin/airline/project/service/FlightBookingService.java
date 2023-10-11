package com.rabin.airline.project.service;

import com.rabin.airline.project.dto.FlightBookingAcknowledgement;
import com.rabin.airline.project.dto.FlightBookingRequest;

import javax.security.auth.login.AccountNotFoundException;

public interface FlightBookingService {
    FlightBookingAcknowledgement bookFlightTicket(FlightBookingRequest flightBookingRequest) throws AccountNotFoundException;
}
