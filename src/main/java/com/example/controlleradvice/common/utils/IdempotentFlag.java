package com.example.controlleradvice.common.utils;

public class IdempotentFlag {
    private static ThreadLocal<String> idempotent = new ThreadLocal<String>();
    public static void doing() {
        idempotent.set("1");
    }
    public static boolean isDoing() {
        if ("1".equals(idempotent.get())) {
            return true;
        }
        return false;
    }
    public static void done() {
        idempotent.remove();
    }
}
