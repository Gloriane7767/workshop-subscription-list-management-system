package com.gloriane;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class SubscriberProcessor {
    public static void applyToMatching(
            findSubscribers(List<Subscriber> list, Predicate<Subscriber> rule) {
        List<Subscriber> result = new ArrayList<>();
        for (Subscriber subscriber : list) {
            if (rule.test(subscriber)) {
                result.add(subscriber);

            }
        }
    }

    applyToMatching(List<Subscriber> list, Predicate<Subscriber> rule, SubscriberAction action) {
        for (Subscriber subscriber : list) {
            if (rule.test(subscriber)) {
                action.perform(subscriber);
            }
        }
    }
}
