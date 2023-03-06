package ru.otus4.client;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.numbers.NumberRequest;
import ru.numbers.NumbersServiceGrpc;

public class NumbersClient {
    private static final Logger logger = LoggerFactory.getLogger(NumbersClient.class);
    private static final int LOOP = 50;
    private long value = 0;
    private static final int PORT = 8090;
    private static final String HOST = "localhost";

    public static void main(String[] args) {
        logger.info("Start client main");

        ManagedChannel channel = ManagedChannelBuilder.forAddress(HOST, PORT).usePlaintext().build();

        var asyncClient = NumbersServiceGrpc.newStub(channel);

        new NumbersClient().clientAction(asyncClient);

        channel.shutdown();
    }

    private void clientAction(NumbersServiceGrpc.NumbersServiceStub asyncClient) {
        var numberRequest = getNumberRequest();
        var clientStreamObserver = new ClientStreamObserver();
        asyncClient.number(numberRequest, clientStreamObserver);

        for (int i = 0; i < LOOP; i++) {
            getNextValue(clientStreamObserver);
            if (clientStreamObserver.isTaskEnd()) {
                break;
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

    }

    private long getNextValue(ClientStreamObserver clientStreamObserver) {
        logger.info(">>> currentValue {}", value);
        value = value + clientStreamObserver.getLastValueAndReset() + 1;
        return value;
    }

    private NumberRequest getNumberRequest() {
        return NumberRequest.newBuilder().setStart(0).setEnd(30).build();
    }

}
