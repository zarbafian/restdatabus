package com.restdatabus;

import java.util.Arrays;

public class ArrayTest {

    public static void main(String[] args) {

        String s = "[definitions, toto, fields, io]";

        String a[] = s.substring(1, s.length() - 1).split(", ");

        System.out.println(a);
    }
}
