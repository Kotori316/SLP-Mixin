package com.kotori316.scala_lib.test;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

import net.minecraftforge.common.util.LazyOptional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class OptionalTest1 {
    @Test
    void test1() {
        LazyOptional<String> lazyOptional = LazyOptional.of(() -> "TNT");
        assertEquals("TNT", lazyOptional.orElse("NULL"));

        lazyOptional.invalidate();
        assertEquals("NULL", lazyOptional.orElse("NULL"));
    }

    @Test
    void test2() {
        AtomicReference<String> reference = new AtomicReference<>("First");
        LazyOptional<String> lazyOptional = LazyOptional.of(reference::get);
        reference.set("Second");
        assertEquals("Second", lazyOptional.orElse("NULL"));
        reference.set("Third");
        assertEquals("Second", lazyOptional.orElse("NULL"));
        lazyOptional.invalidate();
        assertEquals("NULL", lazyOptional.orElse("NULL"));
    }

    @Test
    @DisplayName("Check laziness 1")
    void test3() {
        AtomicReference<String> reference = new AtomicReference<>("First");
        LazyOptional<String> lazyOptional = LazyOptional.of(reference::get).lazyMap(s -> s + s);
        Optional<String> normalOptional = LazyOptional.of(reference::get).map(s -> s + s);
        reference.set("Second");
        assertEquals("SecondSecond", lazyOptional.orElse("NULL"));
        assertEquals("FirstFirst", normalOptional.orElse("NULL"));

        lazyOptional.invalidate();
        assertEquals("NULL", lazyOptional.orElse("NULL"));
    }

    @Test
    @DisplayName("Check laziness 2")
    void test4() {
        AtomicReference<String> reference = new AtomicReference<>("First");
        LazyOptional<String> lazyOptional = LazyOptional.of(reference::get);
        lazyOptional.invalidate();

        Optional<String> normalOptional = lazyOptional.map(s -> s + s);
        assertFalse(normalOptional.isPresent());
    }
}
