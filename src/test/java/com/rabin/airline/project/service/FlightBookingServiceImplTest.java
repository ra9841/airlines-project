package com.rabin.airline.project.service;

import com.rabin.airline.project.dto.FlightBookingAcknowledgement;
import com.rabin.airline.project.dto.FlightBookingRequest;
import com.rabin.airline.project.entity.PassengerInfo;
import com.rabin.airline.project.entity.PaymentInfo;
import com.rabin.airline.project.repository.PassengerInfoRepository;
import com.rabin.airline.project.repository.PaymentInfoRepository;
import com.rabin.airline.project.utils.AccountUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.security.auth.login.AccountNotFoundException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FlightBookingServiceImplTest {

    @Mock
    private PassengerInfoRepository mockPassengerInfoRepository;
    @Mock
    private PaymentInfoRepository mockPaymentInfoRepository;
    @Mock
    private AccountUtils mockAccountUtils;

    @InjectMocks
    private FlightBookingServiceImpl flightBookingServiceImplUnderTest;

    @Test
    void testBookFlightTicket() throws Exception {
        // Setup
        final FlightBookingRequest flightBookingRequest = new FlightBookingRequest();
        final PassengerInfo passengerInfo = new PassengerInfo();
        passengerInfo.setPId(1L);
        passengerInfo.setFare(5000.0);
        flightBookingRequest.setPassengerInfo(passengerInfo);
        final PaymentInfo paymentInfo = new PaymentInfo();
        paymentInfo.setAccountNo("accountNo");
        paymentInfo.setAmount(3000.0);
        paymentInfo.setPassengerId(2L);
        flightBookingRequest.setPaymentInfo(paymentInfo);

        final FlightBookingAcknowledgement expectedResult = new FlightBookingAcknowledgement();
        expectedResult.setStatus("SUCESS");
        expectedResult.setTotalFare(1000.0);
        expectedResult.setPnrNo("pnrNo");
        final PassengerInfo passengerInfo1 = new PassengerInfo();
        passengerInfo1.setPId(2L);
        passengerInfo1.setFare(300.0);
        expectedResult.setPassengerInfo(passengerInfo1);

        // Configure PassengerInfoRepository.save(...).
        final PassengerInfo passengerInfo2 = new PassengerInfo();
        passengerInfo2.setPId(3L);
        passengerInfo2.setName("name");
        passengerInfo2.setEmail("email");
        passengerInfo2.setSource("source");
        passengerInfo2.setFare(500.0);
        final PassengerInfo entity = new PassengerInfo();
        entity.setPId(4L);
        entity.setName("name");
        entity.setEmail("email");
        entity.setSource("source");
        entity.setFare(600.0);
        when(mockPassengerInfoRepository.save(entity)).thenReturn(passengerInfo2);

        // Run the test
        final FlightBookingAcknowledgement result = flightBookingServiceImplUnderTest.bookFlightTicket(
                flightBookingRequest);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
        verify(mockAccountUtils).validateCreditLimit("accountNo", 500.0);
        verify(mockPaymentInfoRepository).save(new PaymentInfo(1L, "accountNo", 50000.0, "cardType", 2L));
    }

    @Test
    void testBookFlightTicket_AccountUtilsThrowsAccountNotFoundException() throws Exception {
        // Setup
        final FlightBookingRequest flightBookingRequest = new FlightBookingRequest();
        final PassengerInfo passengerInfo = new PassengerInfo();
        passengerInfo.setPId(0L);
        passengerInfo.setFare(0.0);
        flightBookingRequest.setPassengerInfo(passengerInfo);
        final PaymentInfo paymentInfo = new PaymentInfo();
        paymentInfo.setAccountNo("accountNo");
        paymentInfo.setAmount(0.0);
        paymentInfo.setPassengerId(0L);
        flightBookingRequest.setPaymentInfo(paymentInfo);

        // Configure PassengerInfoRepository.save(...).
        final PassengerInfo passengerInfo1 = new PassengerInfo();
        passengerInfo1.setPId(0L);
        passengerInfo1.setName("name");
        passengerInfo1.setEmail("email");
        passengerInfo1.setSource("source");
        passengerInfo1.setFare(0.0);
        final PassengerInfo entity = new PassengerInfo();
        entity.setPId(0L);
        entity.setName("name");
        entity.setEmail("email");
        entity.setSource("source");
        entity.setFare(0.0);
        when(mockPassengerInfoRepository.save(entity)).thenReturn(passengerInfo1);

        when(mockAccountUtils.validateCreditLimit("accountNo", 0.0)).thenThrow(AccountNotFoundException.class);

        // Run the test
        assertThatThrownBy(() -> flightBookingServiceImplUnderTest.bookFlightTicket(flightBookingRequest))
                .isInstanceOf(AccountNotFoundException.class);
    }
}
