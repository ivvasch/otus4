package ru.otus4.listener.homework;

import ru.otus4.model.Message;

import java.util.Optional;

public interface HistoryReader {

    Optional<Message> findMessageById(long id);
}
