package com.gloriane;

@FunctionalInterface
public interface SubscriberFilter {
    boolean matches(Subscriber subscriber);

    default SubscriberFilter and(SubscriberFilter other) {
        return s -> matches(s) && other.matches(s);
    }

    /**
     * Combines this filter with another using logical OR.
     * @param other The other filter to combine with.
     * @return A new filter that matches if either filter matches.
     */

    default SubscriberFilter or(SubscriberFilter other) {
        return s -> matches(s) || other.matches(s);
    }

    /**
     * Returns a filter that represents the logical negation of this filter.
     * @return A new filter that matches if this filter does not match.
     */
    default SubscriberFilter negate() {
        return s -> !matches(s);
    }
}

