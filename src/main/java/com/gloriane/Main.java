package com.gloriane;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class Main {
    public static void main(String[] args) {
        List<Subscriber> subscribers = new ArrayList<>();
        subscribers.add(new Subscriber(1, "alice@example.com", Plan.FREE));
        subscribers.add(new Subscriber(2, "bob@example.com", Plan.BASIC));
        subscribers.add(new Subscriber(3, "charlie@example.com", Plan.PRO));
        subscribers.add(new Subscriber(4, "diana@example.com", Plan.BASIC));
        subscribers.add(new Subscriber(5, "eve@example.com", Plan.PRO));
        System.out.println(subscribers);
    }

}
