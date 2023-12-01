package ru.otus.java.pro.generics;

import java.util.ArrayList;

public class Box<T extends Fruit> {
    private final ArrayList<T> fruits = new ArrayList<>();

    void putFruit(T fruit){
        fruits.add(fruit);
    }

    int weight(){
        int result = 0;
        for (T item: fruits){
            result += item.getFruitWeight();
        }
        return result;
    }

    boolean compare(Box<? extends Fruit> box) throws IllegalArgumentException{
        if (box == null){
            throw new IllegalArgumentException("box can't be null");
        }
        if (this == box){
            throw new IllegalArgumentException("Can't compare box with itself");
        }

        return (this.weight() == box.weight());
    }

    void putFruitsToBox(Box<? super T> box) throws IllegalArgumentException {
        if (box == null){
            throw new IllegalArgumentException("box can't be null");
        }
        if (this == box){
            throw new IllegalArgumentException("Can't put box to itself");
        }

        box.fruits.addAll(this.fruits);
        this.fruits.clear();
    }
}
