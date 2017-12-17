package com.shildon.learn.exception;

import java.io.IOException;

public class ExceptionTest {

    public static void main(String[] args) {
        // 证明checked exception和runtime exception catch后都能执行后面的语句
        new ExceptionTest().testRunTimeException();
        new ExceptionTest().testCheckedException();
    }

    private void testRunTimeException() {
        try {
            throw new RuntimeException();
        } catch (Exception e) {
            System.out.println("catch runtime exception!");
        }
        System.out.println("after catch runtime exception!");
    }

    private void testCheckedException() {
        try {
            throw new IOException();
        } catch (Exception e) {
            System.out.println("catch checked exception!");
        }
        System.out.println("after catch checked exception!");
    }

}
