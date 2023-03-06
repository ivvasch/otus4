package ru.otus4.server;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class NumbersServer {

    private static final int port = 8090;

    private static final Logger logger = LoggerFactory.getLogger(NumbersServer.class);
    public static void main(String[] args) throws IOException, InterruptedException {
        logger.info("Start server");

        Server server = ServerBuilder.forPort(port)
                .addService(new NumbersServiceImpl())
                .build();

        server.start();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            logger.info("Received shutDownRequest");
            server.shutdown();
            logger.info("Server stopped");
        }));

        logger.info("Server is waiting for client, port: " + port);
        server.awaitTermination();
        
    }

}
