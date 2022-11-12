package ru.otus4;

import ru.otus4.service.InvProxy;
import ru.otus4.service.TestLoggingInterface;

public class Main {
    public static void main(String[] args) {

        TestLoggingInterface testLoggingIn = InvProxy.createTestLoggingInterfaceImpl();
        testLoggingIn.calculaton(10);
        testLoggingIn.calculaton(15, 17);
        testLoggingIn.calculaton(25, 27, 2);
    }
}
