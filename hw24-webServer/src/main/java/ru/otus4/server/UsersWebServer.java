package ru.otus4.server;

public interface UsersWebServer {
    void start() throws Exception;

    void join() throws Exception;

    void stop() throws Exception;
}
