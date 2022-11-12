package ru.otus4.service;

import ru.otus4.handler.LogInvocationHandler;
import ru.otus4.service.impl.TestLoggingInterfaceImpl;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

public class InvProxy {

    private InvProxy() {}

    public static TestLoggingInterface createTestLoggingInterfaceImpl() {
        InvocationHandler handler = new LogInvocationHandler(new TestLoggingInterfaceImpl());
        return (TestLoggingInterface) Proxy.newProxyInstance(InvProxy.class.getClassLoader(),
                new Class[]{TestLoggingInterface.class}, handler);
    }
}
