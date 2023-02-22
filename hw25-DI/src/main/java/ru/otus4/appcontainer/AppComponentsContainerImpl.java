package ru.otus4.appcontainer;

import lombok.extern.slf4j.Slf4j;
import ru.otus4.appcontainer.api.AppComponent;
import ru.otus4.appcontainer.api.AppComponentsContainer;
import ru.otus4.appcontainer.api.AppComponentsContainerConfig;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

@Slf4j
public class AppComponentsContainerImpl implements AppComponentsContainer {

    private final List<Object> appComponents = new ArrayList<>();
    private final Map<String, Object> appComponentsByName = new HashMap<>();
    private final List<AppComponent> list = new ArrayList<>();

    public AppComponentsContainerImpl(Class<?> initialConfigClass) {
        processConfig(initialConfigClass);
    }

    private void processConfig(Class<?> configClass) {
        Object configClazz;
        checkConfigClass(configClass);
        try {
            configClazz = configClass.getConstructor().newInstance();
            Method[] methods = configClass.getMethods();
            for (Method method : methods) {
                if (list.contains(method.getAnnotation(AppComponent.class))) {
                    throw new Exception();
                }
                if (method.isAnnotationPresent(AppComponent.class)) {
                    list.add(method.getAnnotation(AppComponent.class));
                }
            }
            list.sort(Comparator.comparing(AppComponent::order));
            for (AppComponent next : list) {
                for (Method method : methods) {
                    if (method.getName().equals(next.name())) {
                        Object invoke;
                        Class<?>[] parameterTypes = method.getParameterTypes();
                        if (parameterTypes.length == 0) {
                            invoke = method.invoke(configClazz);
                        } else {
                            for (Class<?> parameterType : parameterTypes) {
                                appComponents.add(appComponentsByName.get(parameterType.getSimpleName().toLowerCase()));
                            }
                            Object[] objects = appComponents.toArray();
                            invoke = method.invoke(configClazz, objects);
                        }
                        appComponents.clear();
                        appComponentsByName.put(method.getName().toLowerCase(), invoke);
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void checkConfigClass(Class<?> configClass) {
        if (!configClass.isAnnotationPresent(AppComponentsContainerConfig.class)) {
            throw new IllegalArgumentException(String.format("Given class is not config %s", configClass.getName()));
        }
    }

    @Override
    public <C> C getAppComponent(Class<C> componentClass) throws Exception {
        String simpleName = componentClass.getSimpleName();
        Object appComponent = getAppComponent(simpleName);
        if (appComponent == null) {
            Optional<Object> first = appComponentsByName.values().stream()
                    .filter(v -> v.getClass().isAssignableFrom(componentClass))
                    .findFirst();
            if (first.isPresent()) {
                appComponent = first.get();
            }
            if (appComponent == null) {
                throw new Exception("No valid Component");
            }
        }
        return (C) appComponent;
    }

    @Override
    public <C> C getAppComponent(String componentName) {
        Object component = appComponentsByName.get(componentName.toLowerCase());
        return (C) component;
    }
}
