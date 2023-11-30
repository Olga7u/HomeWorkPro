package ru.otus.java.pro.generics;

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
            System.out.println("fruitBox.weight="+fruitBox.weight());
            System.out.println("fruitBox2.weight="+fruitBox2.weight());

            System.out.println("appleBox.weight="+appleBox.weight());
            System.out.println("appleBox2.weight="+appleBox2.weight());

            System.out.println("orangeBox.weight="+orangeBox.weight());

            System.out.println();
            System.out.print("appleBox compare fruitBox: ");
            System.out.println(appleBox.compare(fruitBox));

            System.out.print("appleBox compare orangeBox: ");
            System.out.println(appleBox.compare(orangeBox));

            System.out.println();
            appleBox.putFruitsToBox(appleBox2);
            System.out.println("appleBox->appleBox2");
            System.out.println("appleBox.weight="+appleBox.weight());
            System.out.println("appleBox2.weight="+appleBox2.weight());

            System.out.println();
            orangeBox.putFruitsToBox(fruitBox);
            System.out.println("orangeBox->fruitBox");
            System.out.println("orangeBox.weight="+orangeBox.weight());
            System.out.println("fruitBox.weight="+fruitBox.weight());

            System.out.println();
            fruitBox.putFruitsToBox(fruitBox2);
            System.out.println("fruitBox->fruitBox2");
            System.out.println("fruitBox.weight="+fruitBox.weight());
            System.out.println("fruitBox2.weight="+fruitBox2.weight());
        }catch (Exception ex){
            System.err.println(ex.getMessage());
        }
    }
}