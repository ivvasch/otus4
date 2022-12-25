package ru.otus4.listener.homework;


import ru.otus4.listener.Listener;
import ru.otus4.model.Message;
import ru.otus4.model.ObjectForMessage;

import java.util.*;

public class HistoryListener implements Listener, HistoryReader {
    private final Map<Long, Message> mapMessage = new HashMap<>();

    @Override
    public void onUpdated(Message msg) {
        Message message;
        ObjectForMessage objectForMessage = new ObjectForMessage();
        List<String> list = new ArrayList<>();
        if (msg.getField13() != null) {
            list.addAll(msg.getField13().getData());
        }
        objectForMessage.setData(list);
        try {
            message = (Message) msg.clone();
            message = message.toBuilder().field13(objectForMessage).build();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
        mapMessage.put(msg.getId(), message);
    }

    @Override
    public Optional<Message> findMessageById(long id) {
        return Optional.ofNullable(mapMessage.get(id));
    }
}
