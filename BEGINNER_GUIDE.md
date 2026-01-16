# BEGINNER'S GUIDE TO THE SUBSCRIPTION MANAGEMENT APP

## TABLE OF CONTENTS
1. What This App Does
2. Understanding Each Class (Line by Line)
3. How Classes Connect Together
4. Understanding Stream, Filter, and Collect
5. How to Build Similar Apps

---

## 1. WHAT THIS APP DOES

This app manages subscribers (like Netflix or Spotify subscribers). It can:
- Store subscriber information
- Find subscribers based on rules (active, expiring, by plan type)
- Perform actions on subscribers (extend subscription, deactivate)

Think of it like a mini-CRM system for managing subscriptions.

---

## 2. UNDERSTANDING EACH CLASS (LINE BY LINE)

### CLASS 1: Plan.java (The Simplest One!)

```java
package com.gloriane;

public enum Plan {
    FREE,
    BASIC,
    PRO,
}
```

**What it does:** Defines 3 subscription types.
- `enum` = a special class that holds fixed constants
- `FREE` = free plan (no payment)
- `BASIC` = basic paid plan
- `PRO` = premium paid plan

**Real-world analogy:** Like Spotify having Free, Premium Individual, and Premium Family plans.

---

### CLASS 2: Subscriber.java (The Data Container)

```java
package com.gloriane;

public class Subscriber {
    private final int id;              // Line 4: Unique number for each subscriber (cannot change)
    private String email;              // Line 5: Email address
    private Plan plan;                 // Line 6: Which plan they have (FREE, BASIC, or PRO)
    private boolean active;            // Line 7: Are they currently active? (true/false)
    private int monthsRemaining;       // Line 8: How many months left in subscription
```

**Lines 4-8:** These are the properties (data) each subscriber has.
- `private` = only this class can directly access these
- `final` on id = once set, it can never change

```java
    public Subscriber(int id, String email, Plan plan, boolean active, int monthsRemaining) {
        this.id = id;
        setEmail(email);
        setPlan(plan);
        setActive(active);
        setMonthsRemaining(monthsRemaining);
    }
```

**Lines 10-16:** Constructor - runs when you create a new Subscriber.
- `this.id = id` = set the object's id to the value passed in
- `setEmail(email)` = calls the setter method (which validates the email)

```java
    public int getId() {return id;}
    public String getEmail() {return email;}
    public Plan getPlan() {return plan;}
    public boolean isActive() {return active;}
```

**Lines 18-21:** Getter methods - allow other classes to READ the private data.
- `public` = anyone can call these
- `return id` = gives back the id value

```java
    public void setEmail(String email) {
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Invalid email address");
        }
        this.email = email;
    }
```

**Lines 23-28:** Setter for email with VALIDATION.
- Line 24: Checks if email is null or empty
- Line 25: If invalid, throw an error (stops the program)
- Line 27: If valid, set the email

```java
    public void setPlan(Plan plan) {
        if (plan == null) {
            throw new IllegalArgumentException("Plan cannot be null");
        }
        this.plan = plan;
    }
```

**Lines 30-35:** Setter for plan with validation (plan cannot be null).

```java
    public void setActive(boolean active) {
        this.active = active;
    }
```

**Lines 37-39:** Simple setter for active status (no validation needed for boolean).

```java
    public void setMonthsRemaining(int monthsRemaining) {
        if (monthsRemaining < 0) {
            throw new IllegalArgumentException("Months remaining cannot be negative");
        }
        this.monthsRemaining = monthsRemaining;
    }
```

**Lines 41-46:** Setter for months with validation (cannot be negative).

```java
    public int getMonthsRemaining() {
        return monthsRemaining;
    }
```

**Lines 48-50:** Getter for monthsRemaining.

```java
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
```

**Lines 52-61:** toString method - converts the object to readable text.
- When you print a Subscriber, this method runs automatically
- Example output: `Subscriber{id=1, email='alice@example.com', plan=FREE, active=true, monthsRemaining=0}`

---

### CLASS 3: SubscriberDao.java (The Fake Database)

```java
package com.gloriane;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
```

**Lines 3-5:** Import statements - bring in classes we need.
- `ArrayList` = a resizable list
- `List` = interface for lists
- `Optional` = a container that may or may not contain a value

```java
public class SubscriberDao {
    private List<Subscriber> subscribers = new ArrayList<>();
```

**Line 11:** Creates an empty list to store subscribers (acts like a database table).
- `List<Subscriber>` = a list that can only hold Subscriber objects
- `new ArrayList<>()` = creates an empty list

```java
   public void save(Subscriber subscriber) {
        subscribers.add(subscriber);
    }
```

**Lines 13-15:** Save method - adds a subscriber to the list.
- `void` = doesn't return anything
- `.add(subscriber)` = adds the subscriber to the end of the list

```java
   public List<Subscriber> findByAll() {
       return new ArrayList<>(subscribers);
    }
```

**Lines 18-20:** Returns ALL subscribers.
- `new ArrayList<>(subscribers)` = creates a COPY of the list (protects original)

```java
    public Optional<Subscriber> findById(int id) {
        return subscribers.stream()
                .filter(subscriber -> subscriber.getId() == id)
                .findFirst();
    }
```

**Lines 23-27:** Finds ONE subscriber by ID.
- Line 24: `.stream()` = converts list to a stream (explained later)
- Line 25: `.filter(...)` = keeps only subscribers where id matches
- Line 26: `.findFirst()` = gets the first match (returns Optional)
- `Optional` = might find it, might not (safer than returning null)

---

### CLASS 4: SubscriberFilter.java (The Rule Interface)

```java
package com.gloriane;

@FunctionalInterface
public interface SubscriberFilter {
    boolean matches(Subscriber subscriber);
```

**Lines 3-5:** Defines a contract for filtering.
- `@FunctionalInterface` = this interface has exactly ONE abstract method
- `boolean matches(...)` = returns true if subscriber matches the rule, false otherwise
- This is the CORE method that all filters must implement

```java
    default SubscriberFilter and(SubscriberFilter other) {
        return s -> matches(s) && other.matches(s);
    }
```

**Lines 7-9:** Combines TWO filters with AND logic.
- `default` = provides a default implementation
- `s -> matches(s) && other.matches(s)` = lambda expression
- Returns a NEW filter that checks BOTH conditions
- Example: ACTIVE AND EXPIRING

```java
    default SubscriberFilter or(SubscriberFilter other) {
        return s -> matches(s) || other.matches(s);
    }
```

**Lines 17-19:** Combines TWO filters with OR logic.
- `||` = OR operator
- Returns true if EITHER filter matches

```java
    default SubscriberFilter negate() {
        return s -> !matches(s);
    }
```

**Lines 25-27:** Reverses the filter.
- `!matches(s)` = NOT operator
- If filter was "active", negate makes it "not active"

---

### CLASS 5: SubscriberAction.java (The Action Interface)

```java
package com.gloriane;

@FunctionalInterface
public interface SubscriberAction {
    void run(Subscriber subscriber);
}
```

**Lines 3-6:** Defines a contract for actions.
- `void run(...)` = performs an action on a subscriber (doesn't return anything)
- Examples: deactivate, extend subscription, send email

---

### CLASS 6: BusinessRule.java (The Rules Library)

```java
package com.gloriane;

public class BusinessRule {
    public static final SubscriberFilter ACTIVE = subscriber -> subscriber.isActive();
```

**Line 5:** Creates a filter for ACTIVE subscribers.
- `static final` = constant that belongs to the class (not instances)
- `subscriber -> subscriber.isActive()` = lambda expression
- Reads as: "for each subscriber, check if they are active"

```java
    public static final SubscriberFilter EXPIRING = subscriber -> 
        subscriber.getMonthsRemaining() <= 1;
```

**Lines 8-9:** Filter for EXPIRING subscriptions.
- `<= 1` = 0 or 1 month remaining
- Returns true if months remaining is 0 or 1

```java
    public static final SubscriberFilter ACTIVE_AND_EXPIRING = subscriber -> 
        subscriber.isActive() && subscriber.getMonthsRemaining() <= 1;
```

**Lines 12-13:** Combines two conditions with AND.
- Must be BOTH active AND expiring

```java
    public static SubscriberFilter byPlan(Plan plan) {
        return subscriber -> subscriber.getPlan() == plan;
    }
```

**Lines 16-18:** Creates a filter for a SPECIFIC plan.
- This is a METHOD that returns a filter
- `byPlan(Plan.BASIC)` creates a filter for BASIC plan subscribers
- `==` checks if the subscriber's plan equals the given plan

```java
    public static final SubscriberFilter PAYING = subscriber -> 
        subscriber.getPlan() == Plan.BASIC || subscriber.getPlan() == Plan.PRO;
```

**Lines 21-22:** Filter for paying customers.
- `||` = OR operator
- True if plan is BASIC OR PRO (not FREE)

```java
    public static SubscriberAction extendSubscription(int months) {
        return subscriber -> subscriber.setMonthsRemaining(
            subscriber.getMonthsRemaining() + months);
    }
```

**Lines 25-28:** Action to extend subscription.
- Takes current months and adds the given months
- Example: if subscriber has 2 months, extend by 3 = 5 months total

```java
    public static final SubscriberAction DEACTIVATE = subscriber -> 
        subscriber.setActive(false);
```

**Lines 31-32:** Action to deactivate a subscriber.
- Sets active to false

---

### CLASS 7: SubscriberProcessor.java (The Engine)

```java
package com.gloriane;

import java.util.List;
import java.util.stream.Collectors;

public class SubscriberProcessor {
    public List<Subscriber> findSubscribers(List<Subscriber> subscribers, SubscriberFilter filter) {
        return subscribers.stream()
                .filter(filter::matches)
                .collect(Collectors.toList());
    }
```

**Lines 7-11:** Finds subscribers matching a filter.
- Line 8: `.stream()` = converts list to stream
- Line 9: `.filter(filter::matches)` = keeps only matching subscribers
- Line 10: `.collect(Collectors.toList())` = converts stream back to list
- Returns a NEW list with only matching subscribers

```java
    public List<Subscriber> applyToMatching(List<Subscriber> subscribers,
                                           SubscriberFilter filter,
                                           SubscriberAction action) {
        subscribers.stream()
                .filter(filter::matches)
                .forEach(action::run);
        return subscribers;
    }
```

**Lines 13-20:** Applies an action to matching subscribers.
- Line 16: `.stream()` = convert to stream
- Line 17: `.filter(filter::matches)` = find matching subscribers
- Line 18: `.forEach(action::run)` = run the action on each match
- Line 19: Returns the MODIFIED list

---

### CLASS 8: Main.java (The Program Entry Point)

```java
package com.gloriane;

import java.util.List;

public class Main {
    public static void main(String[] args) {
```

**Line 6:** The starting point of the program.
- `main` method is where Java starts execution

```java
        SubscriberDao dao = new SubscriberDao();
        SubscriberProcessor processor = new SubscriberProcessor();
```

**Lines 7-8:** Creates the two main objects we need.
- `dao` = handles data storage/retrieval
- `processor` = handles filtering and actions

```java
        dao.save(new Subscriber(1, "alice@example.com", Plan.FREE, true, 0));
        dao.save(new Subscriber(2, "bob@example.com", Plan.BASIC, true, 1));
        dao.save(new Subscriber(3, "charlie@example.com", Plan.PRO, true, 5));
        dao.save(new Subscriber(4, "diana@example.com", Plan.BASIC, true, 0));
        dao.save(new Subscriber(5, "eve@example.com", Plan.PRO, true, 2));
```

**Lines 10-14:** Creates and saves 5 test subscribers.
- Each `new Subscriber(...)` creates a subscriber object
- `dao.save(...)` adds it to the fake database

```java
        List<Subscriber> allSubscribers = dao.findByAll();
```

**Line 16:** Gets all subscribers from the database.

```java
        List<Subscriber> activeSubscribers = processor.findSubscribers(allSubscribers, BusinessRule.ACTIVE);
        System.out.println("Active Subscribers: " + activeSubscribers);
```

**Lines 22-23:** Finds and prints all ACTIVE subscribers.
- Passes the ACTIVE filter to findSubscribers
- All 5 subscribers are active, so all 5 will be returned

```java
        List<Subscriber> expiringSubscribers = processor.findSubscribers(allSubscribers, BusinessRule.EXPIRING);
        System.out.println("Expiring Subscribers: " + expiringSubscribers);
```

**Lines 27-28:** Finds subscribers with 0 or 1 month remaining.
- Alice (0 months), Bob (1 month), Diana (0 months)

```java
        List<Subscriber> aboutToExpire = processor.findSubscribers(allSubscribers, BusinessRule.ACTIVE_AND_EXPIRING);
        System.out.println("Active and Expiring Subscribers: " + aboutToExpire);
```

**Lines 32-33:** Finds subscribers who are BOTH active AND expiring.
- Same as above since all are active

```java
        List<Subscriber> basicSubscribers = processor.findSubscribers(allSubscribers, BusinessRule.byPlan(Plan.BASIC));
        System.out.println("Basic Subscribers: " + basicSubscribers);
```

**Lines 37-38:** Finds all BASIC plan subscribers.
- Bob and Diana

```java
        List<Subscriber> payingSubscribers = processor.findSubscribers(allSubscribers, BusinessRule.PAYING);
        System.out.println("Paying Subscribers: " + payingSubscribers);
```

**Lines 42-43:** Finds all paying subscribers (BASIC or PRO).
- Bob, Charlie, Diana, Eve (not Alice who is FREE)

```java
        processor.applyToMatching(allSubscribers, BusinessRule.EXPIRING, BusinessRule.extendSubscription(3));
        System.out.println("After extending expiring subscriptions: " + dao.findByAll());
```

**Lines 50-51:** Extends subscription by 3 months for expiring subscribers.
- Alice: 0 → 3 months
- Bob: 1 → 4 months
- Diana: 0 → 3 months

```java
        processor.applyToMatching(allSubscribers, BusinessRule.byPlan(Plan.FREE), BusinessRule.DEACTIVATE);
        System.out.println("After deactivating FREE subscribers: " + dao.findByAll());
```

**Lines 54-55:** Deactivates all FREE plan subscribers.
- Alice gets deactivated (active: true → false)

---

## 3. HOW CLASSES CONNECT TOGETHER

```
Main.java (The Boss)
    |
    |-- Creates --> SubscriberDao (The Database)
    |                   |
    |                   |-- Stores --> List<Subscriber> (The Data)
    |
    |-- Creates --> SubscriberProcessor (The Engine)
                        |
                        |-- Uses --> SubscriberFilter (The Rules)
                        |                |
                        |                |-- Implemented by --> BusinessRule
                        |
                        |-- Uses --> SubscriberAction (The Actions)
                                         |
                                         |-- Implemented by --> BusinessRule
```

**Flow Example: Finding Active Subscribers**

1. Main calls: `processor.findSubscribers(allSubscribers, BusinessRule.ACTIVE)`
2. SubscriberProcessor receives the list and the ACTIVE filter
3. It converts the list to a stream
4. For each subscriber, it calls `BusinessRule.ACTIVE.matches(subscriber)`
5. ACTIVE filter checks: `subscriber.isActive()`
6. If true, subscriber is kept; if false, it's filtered out
7. Stream is collected back into a list
8. List is returned to Main

**Flow Example: Extending Subscriptions**

1. Main calls: `processor.applyToMatching(allSubscribers, BusinessRule.EXPIRING, BusinessRule.extendSubscription(3))`
2. SubscriberProcessor receives list, filter, and action
3. It converts list to stream
4. Filters to find expiring subscribers
5. For each match, it calls `BusinessRule.extendSubscription(3).run(subscriber)`
6. The action adds 3 to the subscriber's monthsRemaining
7. The subscriber object is MODIFIED (not a copy)
8. Returns the modified list

---

## 4. UNDERSTANDING STREAM, FILTER, AND COLLECT

### What is a STREAM?

A stream is like a conveyor belt in a factory. Items (subscribers) move along the belt, and you can:
- Filter out items you don't want
- Transform items
- Collect items at the end

**Creating a Stream:**
```java
List<Subscriber> list = dao.findByAll();
Stream<Subscriber> stream = list.stream();
```

### What is FILTER?

Filter is like a quality control checkpoint. Only items that pass the test continue on the belt.

**Example:**
```java
subscribers.stream()
    .filter(subscriber -> subscriber.isActive())  // Only active subscribers pass through
```

**How it works:**
1. First subscriber enters filter
2. Check: `subscriber.isActive()` → true? Keep it. False? Remove it.
3. Next subscriber enters
4. Repeat for all subscribers

**Multiple Filters:**
```java
subscribers.stream()
    .filter(subscriber -> subscriber.isActive())           // First checkpoint
    .filter(subscriber -> subscriber.getMonthsRemaining() <= 1)  // Second checkpoint
```

### What is COLLECT?

Collect is like the packaging station at the end of the conveyor belt. It takes all the items that made it through and puts them in a container.

**Example:**
```java
List<Subscriber> result = subscribers.stream()
    .filter(subscriber -> subscriber.isActive())
    .collect(Collectors.toList());  // Put all survivors in a new list
```

### Complete Example with Explanation:

```java
List<Subscriber> payingSubscribers = subscribers.stream()  // Start the conveyor belt
    .filter(s -> s.getPlan() == Plan.BASIC || s.getPlan() == Plan.PRO)  // Quality check
    .collect(Collectors.toList());  // Package into a list
```

**Step-by-step for 5 subscribers:**

1. Alice (FREE) → Filter: Is plan BASIC or PRO? NO → Removed
2. Bob (BASIC) → Filter: Is plan BASIC or PRO? YES → Kept
3. Charlie (PRO) → Filter: Is plan BASIC or PRO? YES → Kept
4. Diana (BASIC) → Filter: Is plan BASIC or PRO? YES → Kept
5. Eve (PRO) → Filter: Is plan BASIC or PRO? YES → Kept
6. Collect: Put Bob, Charlie, Diana, Eve in a new list

### Method Reference (::)

`filter::matches` is shorthand for `subscriber -> filter.matches(subscriber)`

**Long form:**
```java
.filter(subscriber -> filter.matches(subscriber))
```

**Short form (method reference):**
```java
.filter(filter::matches)
```

Both do the same thing!

### forEach

forEach is like a worker at the end of the belt who performs an action on each item.

```java
subscribers.stream()
    .filter(s -> s.getPlan() == Plan.FREE)
    .forEach(s -> s.setActive(false));  // Deactivate each one
```

**Difference from collect:**
- `collect` → creates a NEW list
- `forEach` → performs action, returns nothing

---

## 5. HOW TO BUILD SIMILAR APPS

### Step 1: Identify Your Domain Objects

Ask: What am I managing?
- Subscribers? Students? Products? Orders?

Create a class with properties:
```java
public class Student {
    private int id;
    private String name;
    private int grade;
    private boolean enrolled;
    // Constructor, getters, setters, toString
}
```

### Step 2: Create Enums for Fixed Values

Ask: What categories or types exist?
```java
public enum Grade {
    FRESHMAN, SOPHOMORE, JUNIOR, SENIOR
}
```

### Step 3: Create a DAO (Data Access Object)

This manages storage:
```java
public class StudentDao {
    private List<Student> students = new ArrayList<>();
    
    public void save(Student student) {
        students.add(student);
    }
    
    public List<Student> findAll() {
        return new ArrayList<>(students);
    }
}
```

### Step 4: Create Filter Interface

Define how to filter your objects:
```java
@FunctionalInterface
public interface StudentFilter {
    boolean matches(Student student);
}
```

### Step 5: Create Action Interface

Define what actions you can perform:
```java
@FunctionalInterface
public interface StudentAction {
    void run(Student student);
}
```

### Step 6: Create Business Rules

Define your specific filters and actions:
```java
public class StudentRules {
    public static final StudentFilter ENROLLED = s -> s.isEnrolled();
    public static final StudentFilter PASSING = s -> s.getGrade() >= 60;
    
    public static StudentFilter byGradeLevel(Grade grade) {
        return s -> s.getGradeLevel() == grade;
    }
    
    public static final StudentAction GRADUATE = s -> s.setEnrolled(false);
}
```

### Step 7: Create Processor

The engine that uses streams:
```java
public class StudentProcessor {
    public List<Student> findStudents(List<Student> students, StudentFilter filter) {
        return students.stream()
            .filter(filter::matches)
            .collect(Collectors.toList());
    }
    
    public void applyToMatching(List<Student> students, 
                                StudentFilter filter, 
                                StudentAction action) {
        students.stream()
            .filter(filter::matches)
            .forEach(action::run);
    }
}
```

### Step 8: Create Main Class

Put it all together:
```java
public class Main {
    public static void main(String[] args) {
        StudentDao dao = new StudentDao();
        StudentProcessor processor = new StudentProcessor();
        
        // Add students
        dao.save(new Student(1, "Alice", 85, true));
        dao.save(new Student(2, "Bob", 55, true));
        
        // Find passing students
        List<Student> passing = processor.findStudents(
            dao.findAll(), 
            StudentRules.PASSING
        );
        
        // Graduate seniors
        processor.applyToMatching(
            dao.findAll(),
            StudentRules.byGradeLevel(Grade.SENIOR),
            StudentRules.GRADUATE
        );
    }
}
```

---

## KEY CONCEPTS SUMMARY

1. **Separation of Concerns:** Each class has ONE job
   - Subscriber = holds data
   - SubscriberDao = manages storage
   - SubscriberFilter = defines rules
   - SubscriberAction = defines actions
   - SubscriberProcessor = applies rules and actions
   - BusinessRule = library of specific rules
   - Main = orchestrates everything

2. **Functional Interfaces:** Interfaces with one method that can be implemented with lambdas
   - `SubscriberFilter` → `boolean matches(Subscriber s)`
   - `SubscriberAction` → `void run(Subscriber s)`

3. **Lambda Expressions:** Short way to write functions
   - `subscriber -> subscriber.isActive()` means "take a subscriber, return if they're active"

4. **Streams:** Functional way to process collections
   - `.stream()` = start processing
   - `.filter()` = keep only matching items
   - `.collect()` = gather results
   - `.forEach()` = perform action on each

5. **Method References:** Shorthand for lambdas
   - `filter::matches` = `s -> filter.matches(s)`

---

## PRACTICE EXERCISES

1. Add a new filter: `PREMIUM` (subscribers with PRO plan and more than 3 months remaining)
2. Add a new action: `UPGRADE_TO_PRO` (changes plan to PRO)
3. Create a method to find the subscriber with the most months remaining
4. Add a filter that combines ACTIVE and PAYING using the `and()` method

---

You now understand the complete architecture! The key is:
- Objects hold data
- Interfaces define contracts
- Lambdas implement contracts concisely
- Streams process collections functionally
- Everything connects through clear, single-purpose classes
