package ru.otus.listener.homework;

import ru.otus.listener.Listener;
import ru.otus.model.Message;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

public class HistoryListener implements Listener, HistoryReader {
    Map<Long, Message> history = new LinkedHashMap<>();

    @Override
    public void onUpdated(Message msg) {
        Message msgCopy = msg.copy();
        history.put(msgCopy.getId(), msgCopy);
    }

    @Override
    public Optional<Message> findMessageById(long id) {
        return Optional.of(history.get(id));
    }
}
