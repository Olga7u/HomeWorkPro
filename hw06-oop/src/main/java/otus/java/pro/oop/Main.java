package otus.java.pro.oop;

import lombok.extern.slf4j.Slf4j;
import otus.java.pro.oop.atm.Atm;
import otus.java.pro.oop.atm.Banknote;
import otus.java.pro.oop.atm.Cash;
import otus.java.pro.oop.atm.impl.AtmImpl;

@Slf4j
public class Main {

    public static void main(String[] args) {

        Atm atm = new AtmImpl();

        Cash cash = new Cash(100);
        cash.load(new Banknote(100), 100);
        cash.load(new Banknote(500), 1);
        cash.load(new Banknote(1000), 4);
        cash.load(new Banknote(5000), 100);

        log.info("Rest=" + atm.getRest());
        try {
            atm.putMoney(cash);
        } catch (Exception ex) {
            log.error(ex.getMessage());
        }
        log.info("Rest=" + atm.getRest());

        cash = atm.getMoney(51700);

        log.info("Rest=" + atm.getRest());
        log.info(cash.report());

    }
}