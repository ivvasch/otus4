package ru.otus4.util;

import java.time.LocalDateTime;

public interface DateTimeProvider {
    LocalDateTime getTimeProcessor();

    LocalDateTime getTime();
}
