package otus.java.pro.oop.atm;

public interface Atm {
    int getRest();

    void putMoney(Cash addCash) throws IllegalArgumentException;

    Cash getMoney(int sum) throws IllegalArgumentException;
}
