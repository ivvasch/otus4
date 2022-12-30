package ru.otus4.processor;

import ru.otus4.model.Message;

public class ProcessorChangeField implements Processor {
    @Override
    public Message process(Message message) {
        String tempField;
        tempField = message.getField11();
        Message.Builder msgBuilder = new Message.Builder(message.getId());
        msgBuilder.field11(message.getField12());
        msgBuilder.field12(tempField);
        return msgBuilder.build();
    }
}
