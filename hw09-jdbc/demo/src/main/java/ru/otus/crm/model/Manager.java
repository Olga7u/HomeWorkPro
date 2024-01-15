package ru.otus.crm.model;

import ru.otus.annotations.Id;

public class Manager {
    @Id
    private Long id;
    private String name;
    private boolean isFree;

    public Manager() {
    }

    public Manager(String name) {
        this.id = null;
        this.name = name;
        this.isFree = true;
    }

    public Manager(String name, boolean isFree) {
        this.id = null;
        this.name = name;
        this.isFree = isFree;
    }

    public Manager(Long id, String name, boolean isFree) {
        this.id = id;
        this.name = name;
        this.isFree = isFree;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean getIsFree() {
        return isFree;
    }

    public void setIsFree(boolean isFree) {
        this.isFree = isFree;
    }

    @Override
    public String toString() {
        return "Manager{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", isFree=" + isFree +
                '}';
    }
}
