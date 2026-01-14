package com.gloriane;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class Main {
    public static void main(String[] args) {
        SubscriberDao dao = new SubscriberDao();
        dao.save(new Subscriber(1, "alice@example.com", Plan.FREE, true, 0));
        dao.save(new Subscriber(2, "bob@example.com", Plan.BASIC, true, 1));
        dao.save(new Subscriber(3, "charlie@example.com", Plan.PRO, true, 5));
        dao.save(new Subscriber(4, "diana@example.com", Plan.BASIC, true, 0));
        dao.save(new Subscriber(5, "eve@example.com", Plan.PRO, true, 2));
        System.out.println(dao.findByAll());
    }
// Rules using lambda expressions for filtering
}
