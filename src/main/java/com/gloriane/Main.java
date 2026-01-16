package com.gloriane;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        SubscriberDao dao = new SubscriberDao();
        SubscriberProcessor processor = new SubscriberProcessor();

        dao.save(new Subscriber(1, "alice@example.com", Plan.FREE, true, 0));
        dao.save(new Subscriber(2, "bob@example.com", Plan.BASIC, true, 1));
        dao.save(new Subscriber(3, "charlie@example.com", Plan.PRO, true, 5));
        dao.save(new Subscriber(4, "diana@example.com", Plan.BASIC, true, 0));
        dao.save(new Subscriber(5, "eve@example.com", Plan.PRO, true, 2));

        List<Subscriber> allSubscribers = dao.findByAll();

        System.out.println("=== INITIAL SUBSCRIBER ANALYSIS ===");
        System.out.println();

        // Active Subscribers
        List<Subscriber> activeSubscribers = processor.findSubscribers(allSubscribers, BusinessRule.ACTIVE);
        System.out.println("Active Subscribers: " + activeSubscribers);
        System.out.println();

        // Expiring Subscribers
        List<Subscriber> expiringSubscribers = processor.findSubscribers(allSubscribers, BusinessRule.EXPIRING);
        System.out.println("Expiring Subscribers: " + expiringSubscribers);
        System.out.println();

        // Active and Expiring Subscribers
        List<Subscriber> aboutToExpire = processor.findSubscribers(allSubscribers, BusinessRule.ACTIVE_AND_EXPIRING);
        System.out.println("Active and Expiring Subscribers: " + aboutToExpire);
        System.out.println();

        // Subscribers by Plan
        List<Subscriber> basicSubscribers = processor.findSubscribers(allSubscribers, BusinessRule.byPlan(Plan.BASIC));
        System.out.println("Basic Subscribers: " + basicSubscribers);
        System.out.println();

        // Paying Subscribers
        List<Subscriber> payingSubscribers = processor.findSubscribers(allSubscribers, BusinessRule.PAYING);
        System.out.println("Paying Subscribers: " + payingSubscribers);
        System.out.println();

        System.out.println("=== APPLYING BUSINESS ACTIONS ===");
        System.out.println();

        // Extend subscription for expiring subscribers
        processor.applyToMatching(allSubscribers, BusinessRule.EXPIRING, BusinessRule.extendSubscription(3));
        System.out.println("After extending expiring subscriptions: " + dao.findByAll());
        System.out.println();

        // Deactivate FREE plan subscribers
        processor.applyToMatching(allSubscribers, BusinessRule.byPlan(Plan.FREE), BusinessRule.DEACTIVATE);
        System.out.println("After deactivating FREE subscribers: " + dao.findByAll());
    }
}

