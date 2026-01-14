package com.gloriane;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class SubscriberProcessor {
    public static List<Subscriber>
    findSubscribers(List<Subscriber> list, Predicate<Subscriber> rule) {
        List<Subscriber> result = new ArrayList<>();
        for (Subscriber subscriber : list) {
            if (rule.test(subscriber)) {
                result.add(subscriber);
            }
        }
        return result;
    }
   public static List<Subscriber>
    applyToMatching(List<Subscriber> list, Predicate<Subscriber> rule, SubscriberAction action) {
        List<Subscriber> result = new ArrayList<>();
        for (Subscriber subscriber : list) {
            if (rule.test(subscriber)) {
                action.run(subscriber);
                result.add(subscriber);
            }
        }
        return result;
    }
}
