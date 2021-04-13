package com.example.restservice.inrostructure.utils;

import java.io.PrintStream;

public class Dumper {
    public static void dump(Object object, PrintStream out) {
        out.println("==============================");
        out.println("==============================");
        out.println("========ACTUAL=PRICE==========");
        out.println("==============================");
        out.println("==============================");
        out.println(object);
        out.println("==============================");
        out.println("==============================");
        out.println("==============================");
    }
}
