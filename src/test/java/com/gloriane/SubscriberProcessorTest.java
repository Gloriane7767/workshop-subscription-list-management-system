package com.gloriane;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.function.Predicate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SubscriberProcessorTest {

    private SubscriberDao dao;
    private SubscriberProcessor processor;

    @BeforeEach
    void setUp() {
        dao = new SubscriberDao();
        processor = new SubscriberProcessor();
        
        dao.save(new Subscriber(1, "john@email.com", Plan.BASIC, true, 12));
        dao.save(new Subscriber(2, "jane@email.com", Plan.PRO, true, 6));
        dao.save(new Subscriber(3, "bob@email.com", Plan.FREE, false, 0));
        dao.save(new Subscriber(4, "alice@email.com", Plan.BASIC, true, 3));
        dao.save(new Subscriber(5, "charlie@email.com", Plan.PRO, true, 24));
        dao.save(new Subscriber(6, "david@email.com", Plan.FREE, false, 0));
    }

    @Test
    @DisplayName("Test finding ActiveSubscribers")
    void testFindActiveSubscribers() {
        List<Subscriber> result = SubscriberProcessor.findSubscribers(dao.findByAll(), Subscriber::isActive);
        assertEquals(4, result.size(), "There should be 4 active subscribers");
        assertEquals(true, result.stream().allMatch(Subscriber::isActive), "All subscribers should be active");
        
        result.forEach(System.out::println);
    }
}
