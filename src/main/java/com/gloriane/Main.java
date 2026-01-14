package com.gloriane;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
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
SubscriberProcessor processor = new SubscriberProcessor();
    Predicate<Subscriber> activeSubscribers = subscriber -> subscriber.setActive();
    Predicate<Subscriber> isExpiring = subscriber -> subscriber.getmonthsRemaining() <= 1;
    Predicate<Subscriber> activeAndExpiring = subscriber -> activeSubscribers.test(subscriber) && isExpiring.test(subscriber);
    Predicate<Subscriber> paySubscriptionPlan = subscriber -> subscriber.getPlan() == Plan.BASIC || subscriber.getPlan() == Plan.PRO;
    Predicate<Subscriber> proOnly = subscriber -> subscriber.getPlan() == Plan.PRO;
    Consumer<Subscriber> extendBy3Months = subscriber -> subscriber.extendSubscription(3);
    Consumer<Subscriber> deactivate = subscriber -> subscriber.setActive(false);

// Rules using predefined method for actions



}

















/*
Step 1: What Main is responsible for
Your Main will now do 4 things:
Create subscribers
Store them in DAO
Define business rules (lambdas)
Apply rules using SubscriberProcessor
 */