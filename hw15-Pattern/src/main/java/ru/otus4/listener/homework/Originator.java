package ru.otus4.listener.homework;

import ru.otus4.model.Message;
import ru.otus4.model.ObjectForMessage;

import java.util.ArrayList;
import java.util.List;

public class Originator {
    private Message message;

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        ObjectForMessage objectForMessage = new ObjectForMessage();
        List<String> list = new ArrayList<>();
        if (message.getField13() != null) {
            list.addAll(message.getField13().getData());
        }
        objectForMessage.setData(list);
        this.message = new Message.Builder(message.getId())
                .field10(message.getField10())
                .field13(objectForMessage)
                .build();
    }

    public Memento save() {
        return new Memento(message);
    }

    public void load(Memento memento) {
        message = memento.getMessage();
    }
}
