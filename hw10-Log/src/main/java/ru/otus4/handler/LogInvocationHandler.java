package ru.otus4.handler;

import ru.otus4.annotation.Log;
import ru.otus4.service.TestLoggingInterface;
import ru.otus4.service.impl.TestLoggingInterfaceImpl;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class LogInvocationHandler implements InvocationHandler {
    private final TestLoggingInterface testLoggingInterface;
    private final Set<Method> methodsWithAnnotation;
    public LogInvocationHandler(TestLoggingInterfaceImpl testLoggingInterface) {
        this.testLoggingInterface = testLoggingInterface;
        Method[] declaredMethods = testLoggingInterface.getClass().getDeclaredMethods();
        this.methodsWithAnnotation = Arrays.stream(declaredMethods)
                .filter(declaredMethod -> declaredMethod.isAnnotationPresent(Log.class))
                .collect(Collectors.toSet());
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        String params = Arrays.stream(args)
                .map(param -> " param: " + param + ",")
                .reduce("", String::concat);
        if (checkEquals(method)) {
            System.out.println("executed method: " + method.getName() + params);
        }
        return method.invoke(testLoggingInterface, args);
    }

    private boolean checkEquals(Method method) {
        for (Method methodAnnot : methodsWithAnnotation) {
            boolean equalsName = methodAnnot.getName().equals(method.getName());
            if (equalsName) {
                String[] methodAnnotTypes = Arrays.stream(methodAnnot.getParameterTypes()).map(Class::getName).toArray(String[]::new);
                String[] methodTypes = Arrays.stream(method.getParameterTypes()).map(Class::getName).toArray(String[]::new);
                boolean equalsParam = Arrays.equals(methodTypes, methodAnnotTypes);
                if (equalsParam) {
                    return true;
                }
            }
        }
        return false;
    }
}
