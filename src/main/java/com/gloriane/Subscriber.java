package com.gloriane;

public class Subscriber {
    private  final int id;
    private String email;
    private Plan plan;
    private boolean active;
    private int monthsRemaining;

    public Subscriber(int id, String email, Plan plan, boolean active, int monthsRemaining) {
        this.id = id;
        setEmail(email);
        setPlan(plan);
        setActive(active);
        setMonthsRemaining(monthsRemaining);
    }

    public int getId() {return id;}
    public String getEmail() {return email;}
    public Plan getPlan() {return plan;}
    public boolean isActive() {return active;}

    public void setEmail(String email) {
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Invalid email address");
        }
        this.email = email;
    }

    public void setPlan(Plan plan) {
        if (plan == null) {
            throw new IllegalArgumentException("Plan cannot be null");
        }
        this.plan = plan;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void setMonthsRemaining(int monthsRemaining) {
        if (monthsRemaining < 0) {
            throw new IllegalArgumentException("Months remaining cannot be negative");
        }
        this.monthsRemaining = monthsRemaining;
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

