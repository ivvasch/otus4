package ru.otus4.sessionmanager;

public interface TransactionClient {

    <T> T doInTransaction(TransactionAction<T> action);
}
