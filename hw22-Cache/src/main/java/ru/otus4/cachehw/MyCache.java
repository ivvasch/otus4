package ru.otus4.cachehw;


import java.util.WeakHashMap;

public class MyCache<K, V> implements HwCache<K, V> {
    //Надо реализовать эти методы
    WeakHashMap<K, V> cache = new WeakHashMap<>();

    @Override
    public void put(K key, V value) {
        cache.put(key, value);
    }

    @Override
    public void remove(K key) {
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

    }

    @Override
    public void removeListener(HwListener<K, V> listener) {

    }
}
