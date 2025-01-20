package com.ayuda.walletservice.utils.helpers;

import java.util.Random;

public class AccountNumberGenerator {

    private static final Random RANDOM = new Random();

    public static String generateAccountNumber() {
        StringBuilder accountNumber = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            accountNumber.append(RANDOM.nextInt(10)); // Append random digit (0-9)
        }
        return accountNumber.toString();
    }
}
