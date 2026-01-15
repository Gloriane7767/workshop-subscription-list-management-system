package com.gloriane;

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

        // Rules using lambda expressions for filtering
        Predicate<Subscriber> activeSubscribers = subscriber -> subscriber.isActive();
        Predicate<Subscriber> isExpiring = subscriber -> subscriber.getMonthsRemaining() <= 1;
        Predicate<Subscriber> activeAndExpiring = subscriber -> activeSubscribers.test(subscriber) && isExpiring.test(subscriber);
        Predicate<Subscriber> paySubscriptionPlan = subscriber -> subscriber.getPlan() == Plan.BASIC || subscriber.getPlan() == Plan.PRO;
        Predicate<Subscriber> proOnly = subscriber -> subscriber.getPlan() == Plan.PRO;
        Consumer<Subscriber> extendBy3Months = subscriber -> subscriber.extendSubscription(3);
        Consumer<Subscriber> deactivate = subscriber -> subscriber.setActive(false);

        // Use findSubscribers to filter and display results
        System.out.println("\nActive subscribers: " + SubscriberProcessor.findSubscribers(dao.findByAll(), activeSubscribers));
        System.out.println("Expiring subscribers: " + SubscriberProcessor.findSubscribers(dao.findByAll(), isExpiring));
        System.out.println("Active and expiring: " + SubscriberProcessor.findSubscribers(dao.findByAll(), activeAndExpiring));
        System.out.println("Paid plans: " + SubscriberProcessor.findSubscribers(dao.findByAll(), paySubscriptionPlan));
        System.out.println("PRO only: " + SubscriberProcessor.findSubscribers(dao.findByAll(), proOnly));

        // Apply rules and print results after each action
        System.out.println("\n--- Applying Actions ---");
        System.out.println("Extending subscriptions for active and expiring subscribers...");
        SubscriberProcessor.applyToMatching(dao.findByAll(), activeAndExpiring, extendBy3Months);
        System.out.println("After extending: " + dao.findByAll());

        System.out.println("\nDeactivating expired FREE plan subscribers...");
        SubscriberProcessor.applyToMatching(dao.findByAll(),
                subscriber -> subscriber.getPlan() == Plan.FREE && subscriber.getMonthsRemaining() <= 0,
                deactivate);
        System.out.println("After deactivating: " + dao.findByAll());
    }
}
