package ru.otus4;

import ru.otus4.tests.TestClassTest;
import ru.otus4.tests.TestRunner;

public class Main {
    public static void main(String[] args) throws Exception {
        TestRunner.start(TestClassTest.class);
    }
}
