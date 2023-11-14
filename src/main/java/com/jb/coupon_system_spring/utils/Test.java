package com.jb.coupon_system_spring.utils;

public class Test {
    private static int count = 1;
    private static String[] client = {"Admin", "Company", "Customer"};

    public static void test(int clientType, String title) {
        System.out.printf(">>>>>>>>>>> Test %02d - %s Service - %s\n", count++, client[clientType - 1], title);
    }

    public static void printTitle(int clientType, String title) {
        System.out.println("\n------------------------------- " + client[clientType - 1] + " Service Test - " + title + " -------------------------------\n");
    }
}
