package ru.otus4.cachehw;


import java.util.LinkedList;
import java.util.List;
import java.util.WeakHashMap;

public class MyCache<K, V> implements HwCache<K, V> {
    //Надо реализовать эти методы
    WeakHashMap<K, V> cache = new WeakHashMap<>();

    List<HwListener<K, V>> listeners = new LinkedList<>();

    @Override
    public void put(K key, V value) {
        for (HwListener<K, V> listener : listeners) {
            listener.notify(key, value, "PUT");
        }
        cache.put(key, value);
    }

    @Override
    public void remove(K key) {
        for (HwListener<K, V> listener : listeners) {
            listener.notify(key, cache.get(key), "REMOVE");
        }
        cache.remove(key);
    }

    @Override
    public V get(K key) {
        if (cache != null) {
            return cache.get(key);
        }
        return null;
    }


    @Override
    public void addListener(HwListener<K, V> listener) {
        listeners.add(listener);
    }

    @Override
    public void removeListener(HwListener<K, V> listener) {
        listeners.remove(listener);

    }
}
