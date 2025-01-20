package com.ayuda.transactionservice.utils.helpers;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class ReferenceGenerator {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

    private static String generateRefrence() {
        String timestamp = LocalDateTime.now().format(DATE_FORMATTER);
        String randomString = RandomStringUtils.randomAlphanumeric(8).toUpperCase();
        return timestamp + "-" + randomString;
    }

    public String generateTransactionReference() {
        String timestamp = LocalDateTime.now().format(DATE_FORMATTER);
        String randomString = RandomStringUtils.randomAlphanumeric(8).toUpperCase();
        return "REF-" + ReferenceGenerator.generateRefrence();
    }
}
