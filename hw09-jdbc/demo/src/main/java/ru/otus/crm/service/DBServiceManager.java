package ru.otus.crm.service;

import ru.otus.crm.model.Manager;

import java.util.List;
import java.util.Optional;

public interface DBServiceManager {

    Manager saveManager(Manager manager);

    Optional<Manager> getManager(long id);

    List<Manager> findAll();
}
