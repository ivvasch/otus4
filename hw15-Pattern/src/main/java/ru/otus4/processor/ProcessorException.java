package ru.otus4.processor;

import ru.otus4.model.Message;

import java.util.Date;

public class ProcessorException implements Processor {

    private long state;

    public long getState() {
        return state;
    }

    public void setState(long state) {
        this.state = state;
    }

    @Override
    public Message process(Message message) {
            long time = new Date().getTime() / 1000;
        try {
            if (time % 2 == 0) {
                setState(time);
                message.toBuilder().field1(String.valueOf(time)).build();
                throw new RuntimeException("Test2 exception");
            }
        } catch (RuntimeException exception) {
            System.out.println(exception);
        }
        return null;
    }
}
