package ru.otus4.service.impl;

import ru.otus4.annotation.Log;
import ru.otus4.service.TestLoggingInterface;

public class TestLoggingInterfaceImpl implements TestLoggingInterface {

    @Override
    public int calculaton(int param) {

        return param * 2;
    }
    @Log
    @Override
    public int calculaton(int param, int param2) {

        return param + param2;
    }
    @Log
    @Override
    public int calculaton(int param, int param2, int param3) {

        return (param + param2) * param3;
    }
}
