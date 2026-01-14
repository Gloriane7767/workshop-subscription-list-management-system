package com.gloriane;

public class Subscriber {
    private int id;
    private String email;
    private Plan plan;
    private boolean active;
    private int monthsRemaining;

    public Subscriber(int id, String email, Plan plan, boolean active, int monthsRemaining) {
        this.id = id;
        this.email = email;
        this.plan = plan;
        this.active = active;
        this.monthsRemaining = monthsRemaining;
    }

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public Plan getPlan() {
        return plan;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void extendSubscription(int months) {
        this.monthsRemaining += months;
    }

    @Override
    public String toString() {
        return "Subscriber{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", plan=" + plan +
                ", active=" + active +
                ", monthsRemaining=" + monthsRemaining +
                '}';
    }
}

