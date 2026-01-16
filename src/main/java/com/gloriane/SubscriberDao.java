package com.gloriane;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// Implementation to save subscriber to database
public class SubscriberDao {

    // This list acts like a fake database
    private List<Subscriber> subscribers = new ArrayList<>();

   public void save(Subscriber subscriber) {
        subscribers.add(subscriber);
    }

    // Implementation to find all subscribers from database
   public List<Subscriber> findByAll() {
       return new ArrayList<>(subscribers);
    }

    // Implementation to find subscriber by ID from database
    public Optional<Subscriber> findById(int id) {
        return subscribers.stream()
                .filter(subscriber -> subscriber.getId() == id)
                .findFirst();
    }
}

