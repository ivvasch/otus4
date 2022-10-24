package ru.otus4;

import ru.otus4.tests.TestClassTest;
import ru.otus4.tests.TestRunner;

public class Main {
    public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        TestClassTest testClassTest = new TestClassTest();
        TestRunner.start(testClassTest);
    }
}
