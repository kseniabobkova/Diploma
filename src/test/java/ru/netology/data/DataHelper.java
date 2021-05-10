package ru.netology.data;

import com.github.javafaker.Faker;
import com.github.javafaker.service.FakeValuesService;
import com.github.javafaker.service.RandomService;

import java.time.LocalDate;
import java.util.Locale;

public class DataHelper {

    public static Faker faker = new Faker(new Locale("en"));

    public static String getApprovedNumber() {
        return "4444 4444 4444 4441";
    }

    public static String getDeclinedNumber() {
        return "4444 4444 4444 4442";
    }

    public static String getInvalidCardNumber() {
        return "4444 4444 4444 4443";
    }

    public static String getShortCardNumber() {
        return "4444 4444 4444 444";
    }


    public static String getCurrentMonth() {
        LocalDate localDate = LocalDate.now();
        return String.format("%02d", localDate.getMonthValue());
    }

    public static String getLastYear() {
        LocalDate localDate = LocalDate.now();
        return String.format("%ty", localDate.minusYears(1));
    }

    public static String getNextYear() {
        LocalDate localDate = LocalDate.now();
        return String.format("%ty", localDate.plusYears(1));
    }

    public static String getValidName() {
        Faker faker = new Faker(new Locale("en"));
        return faker.name().firstName() + " " + faker.name().lastName();
    }

    public static String getValidCvc() {
        FakeValuesService fakeValuesService = new FakeValuesService(new Locale("en"), new RandomService());
        return fakeValuesService.numerify("###");
    }


}

