package ru.otus4.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.numbers.NumberRequest;
import ru.numbers.NumberResponse;
import ru.numbers.NumbersServiceGrpc;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

public class NumbersServiceImpl extends NumbersServiceGrpc.NumbersServiceImplBase {

    private final Logger logger = LoggerFactory.getLogger(NumbersServiceImpl.class);

    @Override
    public void number(NumberRequest request, io.grpc.stub.StreamObserver<NumberResponse> responseObserver) {
        logger.info("Request for the new sequence");
        var currentValue = new AtomicLong(request.getStart());
        var executor = Executors.newScheduledThreadPool(1);
        Runnable task = () -> {
            var value = currentValue.incrementAndGet();
            var response = NumberResponse.newBuilder().setNumber(value).build();
            responseObserver.onNext(response);
            if (value == request.getEnd()) {
                executor.shutdown();
                responseObserver.onCompleted();
                logger.info("finish");
            }
        };
        executor.scheduleAtFixedRate(task, 0, 2, TimeUnit.SECONDS);
    }
}
