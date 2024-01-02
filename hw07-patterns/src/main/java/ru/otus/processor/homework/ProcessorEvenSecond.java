package ru.otus.processor.homework;

import ru.otus.model.Message;
import ru.otus.processor.Processor;

import java.time.LocalDateTime;

public class ProcessorEvenSecond implements Processor {
    @Override
    public Message process(Message message) {
        if (now().getSecond() % 2 == 0){
            throw new RuntimeException("Odd second");
        }
        return message.toBuilder().build();
    }

    public LocalDateTime now(){
        return LocalDateTime.now();
    }

}
