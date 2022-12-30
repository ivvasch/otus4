package ru.otus4.listener.homework;

import ru.otus4.model.Message;

public class Memento {
    private Message message;

    public Message getMessage() {
        return message;
    }

    public Memento(Message message) {
        this.message = message;
    }
}
