package com.gloriane;

public class BusinessRule {
    // Active Subscriber
    public static final SubscriberFilter ACTIVE = subscriber -> subscriber.isActive();
    
    // Expiring Subscription (0 or 1 month remaining)
    public static final SubscriberFilter EXPIRING = subscriber -> 
        subscriber.getMonthsRemaining() <= 1;
    
    // Active and Expiring Subscriber
    public static final SubscriberFilter ACTIVE_AND_EXPIRING = subscriber -> 
        subscriber.isActive() && subscriber.getMonthsRemaining() <= 1;
    
    // Subscriber by Plan
    public static SubscriberFilter byPlan(Plan plan) {
        return subscriber -> subscriber.getPlan() == plan;
    }
    
    // Paying Subscriber (BASIC or PRO)
    public static final SubscriberFilter PAYING = subscriber -> 
        subscriber.getPlan() == Plan.BASIC || subscriber.getPlan() == Plan.PRO;
    
    // Extend Subscription
    public static SubscriberAction extendSubscription(int months) {
        return subscriber -> subscriber.setMonthsRemaining(
            subscriber.getMonthsRemaining() + months);
    }
    
    // Deactivate Subscriber
    public static final SubscriberAction DEACTIVATE = subscriber -> 
        subscriber.setActive(false);
}