package com.oopgroup7.quanlylophoc;

import org.junit.Test;

import static org.junit.Assert.*;

import java.nio.channels.Selector;

import java.util.ArrayList;
import java.util.List;

public class TestSequence {
     public static void test() {
        List<String> s = new ArrayList<>(10);
        for (int i = 0; i < 10; i++)
           s.add(Integer.toString(i));
        // Example iteration over the list
        for (String value : s) {
            System.out.println(value);
        }
     }
}
