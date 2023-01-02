package ru.otus4.processor;

import ru.otus4.model.Message;
import ru.otus4.util.DateTimeProvider;

public class ProcessorException implements Processor {
    private final DateTimeProvider provider;

    public ProcessorException(DateTimeProvider provider) {
        this.provider = provider;
    }

    @Override
    public Message process(Message message) {
            long time = provider.getTimeProcessor().getSecond();
        try {
            if (time % 2 == 0) {
                throw new RuntimeException("Test2 exception");
            }
        } catch (RuntimeException exception) {
            System.out.println(exception);
        }
        return null;
    }
}
