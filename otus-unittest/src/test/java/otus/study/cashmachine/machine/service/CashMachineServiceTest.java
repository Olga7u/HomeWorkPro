package otus.study.cashmachine.machine.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import otus.study.cashmachine.bank.dao.CardsDao;
import otus.study.cashmachine.bank.data.Account;
import otus.study.cashmachine.bank.data.Card;
import otus.study.cashmachine.bank.service.AccountService;
import otus.study.cashmachine.bank.service.impl.CardServiceImpl;
import otus.study.cashmachine.machine.data.CashMachine;
import otus.study.cashmachine.machine.data.MoneyBox;
import otus.study.cashmachine.machine.service.impl.CashMachineServiceImpl;
import otus.study.cashmachine.machine.service.impl.MoneyBoxServiceImpl;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CashMachineServiceTest {

    @Spy
    @InjectMocks
    private CardServiceImpl cardService;

    @Mock
    private CardsDao cardsDao;

    @Mock
    private AccountService accountService;

    @Mock
    private MoneyBoxService moneyBoxService;

    private CashMachineServiceImpl cashMachineService;

    private final CashMachine cashMachine = new CashMachine(new MoneyBox());

    private final BigDecimal accountBalance = new BigDecimal(10000);
    private final Account account = new Account(11L,accountBalance);

    @BeforeEach
    void init() {
        cashMachineService = new CashMachineServiceImpl(cardService, accountService, moneyBoxService);
    }

    @Test
    void getMoney() {
// @TODO create get money test using spy as mock
        Card card = new Card(account.getId(),"1234",account.getId(),"1111");
        lenient().when(cardsDao.getCardByNumber("1234")).thenReturn(card);

        BigDecimal sum = new BigDecimal(1600);
        List<Integer> expectedNotes = List.of(0,1,1,1);

        AccountService accountServiceSpy = spy(accountService);

        lenient().when(accountServiceSpy.getMoney(account.getId(),sum)).thenReturn(account.getAmount().subtract(sum));
        MoneyBoxService moneyBoxService = new MoneyBoxServiceImpl();

        cashMachineService = new CashMachineServiceImpl(cardService, accountServiceSpy, moneyBoxService);

        List<Integer> notes = cashMachineService.getMoney(cashMachine,"1234","1111",sum);

        assertEquals(expectedNotes, notes);
        verify(accountServiceSpy,times(1)).getMoney(account.getId(),sum);

        Exception thrownNoCard = assertThrows(IllegalArgumentException.class, () ->
                cashMachineService.getMoney(cashMachine,"4321","1111",sum));
        assertEquals("No card found", thrownNoCard.getMessage());

        Exception thrownPin = assertThrows(IllegalArgumentException.class, () ->
                cashMachineService.getMoney(cashMachine,"1234","1100",sum));
        assertEquals("Pincode is incorrect", thrownPin.getMessage());
    }

    @Test
    void putMoney() {
        Card card = new Card(account.getId(),"1234",account.getId(),"1111");
        lenient().when(cardsDao.getCardByNumber("1234")).thenReturn(card);

        BigDecimal sum = new BigDecimal(1600);
        BigDecimal expectedAmount = new BigDecimal(11600);
        List<Integer> notes = List.of(0,1,1,1);

        lenient().when(accountService.checkBalance(account.getId())).thenReturn(account.getAmount());
        lenient().when(accountService.putMoney(account.getId(),sum)).thenReturn(account.getAmount().add(sum));

        BigDecimal amount = cashMachineService.putMoney(cashMachine,"1234","1111",notes);
        assertEquals(expectedAmount,amount);

        Exception thrownNoCard = assertThrows(IllegalArgumentException.class, () ->
                cashMachineService.putMoney(cashMachine,"4321","1111",notes));
        assertEquals("No card found", thrownNoCard.getMessage());

        Exception thrownPin = assertThrows(IllegalArgumentException.class, () ->
                cashMachineService.putMoney(cashMachine,"1234","1100",notes));
        assertEquals("Pincode is incorrect", thrownPin.getMessage());
    }


    @Test
    void checkBalance() {
        Card card = new Card(account.getId(),"1234",1L,"1111");
        lenient().when(cardsDao.getCardByNumber("1234")).thenReturn(card);

        lenient().when(accountService.checkBalance(account.getId())).thenReturn(account.getAmount());

        BigDecimal balance = cardService.getBalance("1234","1111");
        assertEquals(accountBalance, balance);

        Exception thrownNoCard = assertThrows(IllegalArgumentException.class, () -> {
            cardService.getBalance("4321", "1111");
        });
        assertEquals("No card found",thrownNoCard.getMessage());

        Exception thrownPin = assertThrows(IllegalArgumentException.class, () -> {
            cardService.getBalance("1234", "1100");
        });
        assertEquals("Pincode is incorrect",thrownPin.getMessage());
    }

    @Mock
    Card card;

    @Test
    void changePin() {
// @TODO create change pin test using spy as implementation and ArgumentCaptor and thenReturn
        lenient().when(cardsDao.getCardByNumber(anyString())).thenReturn(card);
        lenient().when(card.getPinCode()).thenReturn("1111");

        ArgumentCaptor<String> newPinCaptor = ArgumentCaptor.forClass(String.class);

        boolean result = cardService.cnangePin("1234","1111","2222");
        assertTrue(result);
        verify(card, times(1)).setPinCode(newPinCaptor.capture());
    }

    @Test
    void changePinWithAnswer() {
// @TODO create change pin test using spy as implementation and mock an thenAnswer

        when(cardsDao.getCardByNumber(anyString())).thenAnswer(invocation -> {
            String cardNum = invocation.getArgument(0);

            if (cardNum.equals("1234")) {
                when(card.getPinCode()).thenReturn("1111");
                return card;
            }
            else {
                return null;
            }
        });

        boolean result = cardService.cnangePin("1234", "1111", "2222");
        assertTrue(result);
        verify(card, times(1)).setPinCode("2222");

        result = cardService.cnangePin("1234", "2200", "3300");
        assertFalse(result);
        verify(card, never()).setPinCode("3300");

        Exception thrown = assertThrows(IllegalArgumentException.class, () -> cardService.cnangePin("4321", "1111", "2222"));
        assertEquals("No card found",thrown.getMessage());
    }
}