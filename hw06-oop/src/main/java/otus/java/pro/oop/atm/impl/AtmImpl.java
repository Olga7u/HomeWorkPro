package otus.java.pro.oop.atm.impl;

import otus.java.pro.oop.atm.Atm;
import otus.java.pro.oop.atm.Cash;

public class AtmImpl implements Atm {
    private final Cash cash;

    public AtmImpl() {

        cash = new Cash(100);
    }

    @Override
    public int getRest() {
        return cash.rest();
    }

    @Override
    public void putMoney(Cash addCash) throws IllegalArgumentException {
        cash.loadCash(addCash);
    }

    @Override
    public Cash getMoney(int sum) throws IllegalArgumentException {
        return cash.unloadSum(sum);
    }
}
