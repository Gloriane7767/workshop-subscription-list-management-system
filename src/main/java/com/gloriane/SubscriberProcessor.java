package com.gloriane;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
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
    applyToMatching(List<Subscriber> list, Predicate<Subscriber> rule, Consumer<Subscriber> action) {
        List<Subscriber> result = new ArrayList<>();
        for (Subscriber subscriber : list) {
            if (rule.test(subscriber)) {
                action.accept(subscriber);
                result.add(subscriber);
            }
        }
        return result;
    }
}
