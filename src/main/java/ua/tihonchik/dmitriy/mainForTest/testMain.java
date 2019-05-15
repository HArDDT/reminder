package ua.tihonchik.dmitriy.mainForTest;

import ua.tihonchik.dmitriy.additional.NotificationConverterImpl;

import java.time.LocalDateTime;

public class testMain {
    public static void main(String[] args) {
        NotificationConverterImpl nc =
                new NotificationConverterImpl();
        System.out.println(nc.getNextValidDate("13, year", LocalDateTime.of(2019, 5, 14, 14, 40, 0)));
    }
}
