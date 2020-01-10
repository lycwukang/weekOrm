package com.wuk.fastorm.testing;

import org.junit.Test;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Test0 {

    @Test
    public void test() {
        Map<String, T0> map = new ConcurrentHashMap<>(12);
        map.putIfAbsent("T", new T0());
        map.putIfAbsent("T", new T0());
    }

    public static class T0 {

        public T0() {
            System.out.println("create T0!");
        }
    }
}
