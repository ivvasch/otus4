package ru.otus4.tests;

import ru.otus4.annotation.After;
import ru.otus4.annotation.Before;
import ru.otus4.annotation.Test;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class TestRunner {
    private static int statisticFalls = 0;
    private static int statisticSuccess = 0;
    private static int statisticAll = 0;
    private static boolean check = false;
    private static final List<String> name = new ArrayList<>();

    public static void start(Class<?> clazz) throws Exception {
        testMethodTest(clazz);
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

    private static void testMethodTest(Class<?> clazz) throws Exception {
        Method[] declaredMethods = clazz.getDeclaredMethods();
        Object newClazz;
        for (Method next : declaredMethods) {
            if (next.isAnnotationPresent(Test.class) && !name.contains(next.getName())) {
                newClazz = clazz.getDeclaredConstructor().newInstance();
                try {
                    statisticAll++;
                    name.add(next.getName());
                    next.setAccessible(true);
                    beforeMethodTest(declaredMethods, newClazz);
                    if (!check) {
                        next.invoke(newClazz);
                        statisticSuccess++;
                    }
                    afterMethodClass(declaredMethods, newClazz);
                } catch (Exception ex) {
                    statisticFalls++;
                    afterMethodClass(declaredMethods, newClazz);
                    testMethodTest(Class.forName(clazz.getName()));
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
