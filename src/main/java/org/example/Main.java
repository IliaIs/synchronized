package org.example;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Main {
    final static String LETTERS = "RLRFR";
    final static int ROUTE_LENGTH = 100;
    final static int AMOUNT_OF_THREADS = 1000;
    public static final Map<Integer, Integer> sizeTofreaq = new HashMap<>();
    public static void main(String[] args) {
        for (int i = 0; i < AMOUNT_OF_THREADS; i++) {
            new Thread(() -> {
                String route = geterateRoute(LETTERS, ROUTE_LENGTH);
                int frequency =
                        (int) route.chars().filter(ch -> ch == 'R').count();
                synchronized (sizeTofreaq) {
                    if (sizeTofreaq.containsKey(frequency)) {
                        sizeTofreaq.put(frequency, sizeTofreaq.get(frequency) + 1);
                    } else {
                        sizeTofreaq.put(frequency, 1);
                    }
                }
            }).start();
        }

        Map.Entry<Integer, Integer> max = sizeTofreaq
                .entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .get();
        System.out.println(
                "Самое частое кол-во повторений " + max.getKey() +
                        " встретилось " + max.getValue() + " раз");
        sizeTofreaq
                .entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue())
                .forEach(e -> System.out.println(e.getKey() +
                        " (" +e.getValue() + " раз)"));
    }
    public static String geterateRoute(String letters, int length) {
        Random random = new Random();
        StringBuilder route = new StringBuilder();
        for (int i = 0; i < length; i++) {
            route.append(letters.charAt(random.nextInt(letters.length())));
        }
        return route.toString();
    }
}