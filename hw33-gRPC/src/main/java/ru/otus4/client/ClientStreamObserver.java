package ru.otus4.client;

import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.numbers.NumberResponse;

public class ClientStreamObserver implements StreamObserver<NumberResponse> {
    private static final Logger logger = LoggerFactory.getLogger(ClientStreamObserver.class);

    boolean isTaskEnd = false;
    private long lastValue = 0;

    public boolean isTaskEnd() {
        return isTaskEnd;
    }

    public void setTaskEnd(boolean taskEnd) {
        isTaskEnd = taskEnd;
    }

    @Override
    public void onNext(NumberResponse response) {
        long number = response.getNumber();
        logger.info("Number from server >>> {}", number);
        setLastValue(number);
    }

    @Override
    public void onError(Throwable t) {
        logger.error("Error", t);
    }

    @Override
    public void onCompleted() {
        logger.info("Task completed");
        setTaskEnd(true);
    }

    private synchronized void setLastValue(long value) {
        this.lastValue = value;
    }

    public synchronized long getLastValueAndReset() {
        var lastValuePrev = this.lastValue;
        this.lastValue = 0;
        return lastValuePrev;
    }

}
