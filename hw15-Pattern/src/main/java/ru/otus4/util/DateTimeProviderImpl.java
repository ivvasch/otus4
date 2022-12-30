package ru.otus4.util;

import java.time.LocalDateTime;

public class DateTimeProviderImpl implements DateTimeProvider {
    private final LocalDateTime time;

    public DateTimeProviderImpl() {
          this.time = getTimeProcessor();
    }

    @Override
    public LocalDateTime getTimeProcessor() {
        return LocalDateTime.now();
    }

    public LocalDateTime getTime() {
        return time;
    }
}
