package otus.study.cashmachine.bank.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import otus.study.cashmachine.bank.data.Account;
import otus.study.cashmachine.bank.service.impl.AccountServiceImpl;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class AccountServiceTest {
    AccountService accountService;
    Account testAccount;

    @BeforeEach
    void init() {
        accountService = new AccountServiceImpl();
        testAccount = accountService.createAccount(new BigDecimal(1000));
    }

    @Test
    void testCreateAccount() {
        Account newAccount = accountService.createAccount(new BigDecimal(1000));
        assertEquals(0, new BigDecimal(1000).compareTo(newAccount.getAmount()));
    }

    @Test
    void testAddSum() {
        BigDecimal sum = testAccount.getAmount();
        accountService.putMoney(testAccount.getId(), new BigDecimal(100));
        BigDecimal expectedSum = sum.add(new BigDecimal(100));
        assertEquals(0, expectedSum.compareTo(accountService.checkBalance(testAccount.getId())));
    }

    @Test
    void testGetSum() {
        BigDecimal sum = testAccount.getAmount();
        accountService.getMoney(testAccount.getId(), new BigDecimal(100));
        BigDecimal expectedSum = sum.subtract(new BigDecimal(100));
        assertEquals(0, expectedSum.compareTo(accountService.checkBalance(testAccount.getId())));
    }

    @Test
    void testGetAccount() {
        assertEquals(testAccount, accountService.getAccount(testAccount.getId()));
    }

    @Test
    void testCheckBalance() {
        assertEquals(0, testAccount.getAmount()
                .compareTo(accountService.checkBalance(testAccount.getId())));
    }
}
