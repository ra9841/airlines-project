package com.rabin.airline.project.utils;

import com.rabin.airline.project.dto.AccountInfoDto;
import com.rabin.airline.project.service.AccountInfoServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.security.auth.login.AccountNotFoundException;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AccountUtilsTest {

    @Mock
    private AccountInfoServiceImpl mockAccountInfoService;

    private AccountUtils accountUtilsUnderTest;

    @BeforeEach
    void setUp() {
        accountUtilsUnderTest = new AccountUtils();
        accountUtilsUnderTest.accountInfoService = mockAccountInfoService;
    }

    @Test
    void testValidateCreditLimit() throws Exception {
        // Setup
        // Configure AccountInfoServiceImpl.getAllAccountInforamtion(...).
        final List<AccountInfoDto> accountInfoDtos = List.of(new AccountInfoDto(0L, "accountNo", 0.0));
        when(mockAccountInfoService.getAllAccountInforamtion()).thenReturn(accountInfoDtos);

        // Run the test
        final boolean result = accountUtilsUnderTest.validateCreditLimit("accountNo", 0.0);

        // Verify the results
        assertThat(result).isFalse();
    }

    @Test
    void testValidateCreditLimit_AccountInfoServiceImplReturnsNoItems() {
        // Setup
        when(mockAccountInfoService.getAllAccountInforamtion()).thenReturn(Collections.emptyList());

        // Run the test
        assertThatThrownBy(() -> accountUtilsUnderTest.validateCreditLimit("accountNo", 0.0))
                .isInstanceOf(AccountNotFoundException.class);
    }
}
