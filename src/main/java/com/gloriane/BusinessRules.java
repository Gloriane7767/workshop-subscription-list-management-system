package com.gloriane;

public class BusinessRules {

    // Filters
    public static final SubscriberFilter ACTIVE_SUBSCRIBER =
            subscriber -> subscriber.isActive();

    public static final SubscriberFilter EXPIRING_SUBSCRIPTION =
            subscriber -> subscriber.getMonthsRemaining() <= 1;

    public static final SubscriberFilter ACTIVE_AND_EXPIRING =
            ACTIVE_SUBSCRIBER.and(EXPIRING_SUBSCRIPTION);

    public static SubscriberFilter subscriberByPlan(Plan plan) {
        return subscriber -> subscriber.getPlan() == plan;
    }

    public static final SubscriberFilter PAYING_SUBSCRIBER =
            subscriber -> subscriber.getPlan() != Plan.FREE;

    // Actions
    public static SubscriberAction extendSubscription(int months) {
        return subscriber -> subscriber.setMonthsRemaining(
                subscriber.getMonthsRemaining() + months);
    }

    public static final SubscriberAction DEACTIVATE_SUBSCRIBER =
            subscriber -> subscriber.setActive(false);
}

