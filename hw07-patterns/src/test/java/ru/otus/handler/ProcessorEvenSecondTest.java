package ru.otus.handler;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.model.Message;
import ru.otus.processor.Processor;
import ru.otus.processor.homework.ProcessorEvenSecond;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProcessorEvenSecondTest {

    @Spy
    private ProcessorEvenSecond processorSpy;

    @Test
    void EvenSecondTest(){
        when(processorSpy.now()).thenAnswer(invocation -> LocalDateTime.of(2024,1,1,0,0,2));

        Message message = new Message.Builder(1L).build();
        List<Processor> processors = List.of(processorSpy);

        ComplexProcessor complexProcessor = new ComplexProcessor(processors, (ex) -> {
            throw new TestException(ex.getMessage());
        });

        assertThatExceptionOfType(TestException.class).isThrownBy(() -> complexProcessor.handle(message));
    }

    @Test
    void OddSecondTest(){
        when(processorSpy.now()).thenAnswer(invocation -> LocalDateTime.of(2024,1,1,0,0,1));

        Message message = new Message.Builder(1L).build();
        List<Processor> processors = List.of(processorSpy);

        ComplexProcessor complexProcessor = new ComplexProcessor(processors, (ex) -> {
            throw new TestException(ex.getMessage());
        });

        assertEquals(message, complexProcessor.handle(message));
    }

    private static class TestException extends RuntimeException {
        public TestException(String message) {
            super(message);
        }
    }

}
