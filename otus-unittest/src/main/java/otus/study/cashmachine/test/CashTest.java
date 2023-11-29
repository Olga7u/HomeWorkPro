package otus.study.cashmachine.test;

import otus.study.cashmachine.bank.dao.CardsDao;
import otus.study.cashmachine.bank.service.AccountService;
import otus.study.cashmachine.bank.service.CardService;
import otus.study.cashmachine.bank.service.impl.AccountServiceImpl;
import otus.study.cashmachine.bank.service.impl.CardServiceImpl;
import otus.study.cashmachine.machine.data.CashMachine;
import otus.study.cashmachine.machine.data.MoneyBox;
import otus.study.cashmachine.machine.service.CashMachineService;
import otus.study.cashmachine.machine.service.MoneyBoxService;
import otus.study.cashmachine.machine.service.impl.CashMachineServiceImpl;
import otus.study.cashmachine.machine.service.impl.MoneyBoxServiceImpl;

import java.math.BigDecimal;

public class CashTest {
    public static void main(String[] args){
        BigDecimal accountBalance = new BigDecimal(10000);
        String cardNumber = "123456";
        String cardPin = "1111";

        AccountService accountService = new AccountServiceImpl();
        CardsDao cardsDao = new CardsDao();

        CardService cardService = new CardServiceImpl(accountService,cardsDao);
        MoneyBoxService moneyBoxService = new MoneyBoxServiceImpl();

        cardService.createCard(cardNumber,accountService.createAccount(accountBalance).getId(),cardPin);

        CashMachineService cashMachineService = new CashMachineServiceImpl(cardService,accountService,moneyBoxService);

        MoneyBox moneyBox = new MoneyBox(100,100,100,100);
        CashMachine cashMachine = new CashMachine(moneyBox);

        System.out.println(cashMachineService.checkBalance(cashMachine,cardNumber,cardPin));
        System.out.println(cardService.getBalance(cardNumber,cardPin));
    }
}
