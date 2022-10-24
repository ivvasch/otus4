package ru.otus4.tests;

import ru.otus4.annotation.After;
import ru.otus4.annotation.Before;
import ru.otus4.annotation.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class TestRunner {
    private static int statisticFalls = 0;
    private static int statisticSuccess = 0;
    private static int statisticAll = 0;
    private static boolean check = false;
    private static final List<String> name = new ArrayList<>();
    private static Class clazz;

    public static void start(TestClassTest testClassTest) throws ClassNotFoundException, InstantiationException, IllegalAccessException {

        Method[] declaredMethods = testClassTest.getClass().getDeclaredMethods();
        clazz = Class.forName(testClassTest.getClass().getName());
        testMethodTest(declaredMethods, clazz.newInstance());
        System.out.println("===========================================");
        System.out.println("Всего тестов " + statisticAll);
        System.out.println("Всего тестов пройдено успешно " + statisticSuccess);
        System.out.println("Всего тестов не пройдено " + statisticFalls);
    }

    private static void beforeMethodTest(Method[] declaredMethods, Object clazz) {
        for (Method method : declaredMethods) {
            try {
                if (method.isAnnotationPresent(Before.class)) {
                    method.setAccessible(true);
                    method.invoke(clazz);
                }
            } catch (Exception e) {
                check = true;
                statisticFalls++;
            }
        }
    }

    private static void testMethodTest(Method[] declaredMethods, Object clazz) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        for (Method next : declaredMethods) {
            if (next.isAnnotationPresent(Test.class) && !name.contains(next.getName())) {
                try {
                    statisticAll++;
                    name.add(next.getName());
                    next.setAccessible(true);
                    beforeMethodTest(declaredMethods, clazz);
                    if (!check) {
                        next.invoke(clazz);
                        statisticSuccess++;
                    }
                    afterMethodClass(declaredMethods, clazz);
                    clazz = clazz.getClass().newInstance();
                } catch (IllegalAccessException | InvocationTargetException | InstantiationException ex) {
                    statisticFalls++;
                    afterMethodClass(declaredMethods, clazz);
                    clazz = clazz.getClass().newInstance();
                    testMethodTest(declaredMethods, clazz);
                }
            }
        }
    }

    private static void afterMethodClass(Method[] declaredMethods, Object clazz) {
        for (Method method : declaredMethods) {
            try {
                if (method.isAnnotationPresent(After.class)) {
                    method.invoke(clazz);
                }
            } catch (Exception ignored) {
            }
        }
    }
}
