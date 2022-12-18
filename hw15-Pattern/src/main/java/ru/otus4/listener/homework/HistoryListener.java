package ru.otus4.listener.homework;


import ru.otus4.listener.Listener;
import ru.otus4.model.Message;

import java.util.Optional;

public class HistoryListener implements Listener, HistoryReader {

    Originator originator = new Originator();
    CareTaker careTaker = new CareTaker();
    @Override
    public void onUpdated(Message msg) {
        originator.setMessage(msg);
        careTaker.add(originator.save());
    }

    @Override
    public Optional<Message> findMessageById(long id) {
        originator.load(careTaker.get(id));
        return Optional.ofNullable(originator.getMessage());
    }
}
