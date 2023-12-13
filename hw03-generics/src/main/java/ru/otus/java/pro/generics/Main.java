package ru.otus.java.pro.generics;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Main {
    public static void main(String[] args) {

        Box<Fruit> fruitBox = new Box<>();
        Box<Fruit> fruitBox2 = new Box<>();

        Box<Apple> appleBox = new Box<>();
        Box<Apple> appleBox2 = new Box<>();

        Box<Orange> orangeBox = new Box<>();

        fruitBox.putFruit(new Apple(1));
        fruitBox.putFruit(new Orange(2));

        appleBox.putFruit(new Apple(3));
        appleBox.putFruit(new Apple(4));

        appleBox2.putFruit(new Apple(5));

        orangeBox.putFruit(new Orange(7));

        try {
            log.info("fruitBox.weight=" + fruitBox.weight());
            log.info("fruitBox2.weight=" + fruitBox2.weight());
            log.info("appleBox.weight=" + appleBox.weight());
            log.info("appleBox2.weight=" + appleBox2.weight());
            log.info("orangeBox.weight=" + orangeBox.weight());

            log.info("appleBox compare fruitBox: " + appleBox.compare(fruitBox));
            log.info("appleBox compare orangeBox: " + appleBox.compare(orangeBox));

            appleBox.putFruitsToBox(appleBox2);
            log.info("appleBox->appleBox2");
            log.info("appleBox.weight=" + appleBox.weight());
            log.info("appleBox2.weight=" + appleBox2.weight());

            orangeBox.putFruitsToBox(fruitBox);
            log.info("orangeBox->fruitBox");
            log.info("orangeBox.weight=" + orangeBox.weight());
            log.info("fruitBox.weight=" + fruitBox.weight());

            fruitBox.putFruitsToBox(fruitBox2);
            log.info("fruitBox->fruitBox2");
            log.info("fruitBox.weight=" + fruitBox.weight());
            log.info("fruitBox2.weight=" + fruitBox2.weight());
        } catch (Exception ex) {
            log.error(ex.getMessage());
        }
    }
}