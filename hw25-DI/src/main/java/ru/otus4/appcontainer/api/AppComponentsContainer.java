package ru.otus4.appcontainer.api;

public interface AppComponentsContainer {
    <C> C getAppComponent(Class<C> componentClass) throws Exception;
    <C> C getAppComponent(String componentName);
}
