package ru.otus4.handler;

import ru.otus4.listener.Listener;
import ru.otus4.model.Message;

public interface Handler {
    Message handle(Message msg);

    void addListener(Listener listener);
    void removeListener(Listener listener);
}
