# Assignment #5 - Strategy Pattern

**Student:** Nursultan Nurislam, SE - 2538
**Course:** ShP-2216 - Software Design Patterns (OP 6B06102)
**Topic:** Option B - Calculator

## 1. Introduction

For this assignment I chose Option B: Calculator.

The domain is a simple calculator that does one arithmetic operation on two numbers. The family of algorithms is the operations themselves: addition, subtraction, multiplication, division and power. All of them take two numbers and return one number, so they can be used in the same way.

The usual way to write such a calculator is one method with a chain of `if` or `switch` statements that checks the operation name. This code is hard to extend, because for every new operation we must open the calculator and change it. The Strategy pattern solves this problem. Each operation is a separate class that implements the `OperationStrategy` interface. The `Calculator` only holds a strategy and calls it, so the operation can be changed at runtime with `setStrategy`.

In my project:

- **Strategy interface:** `OperationStrategy`
- **Concrete strategies:** `AddStrategy`, `SubtractStrategy`, `MultiplyStrategy`, `DivideStrategy`, `PowerStrategy`
- **Context:** `Calculator`
- **Client:** `Main`

## 2. UML Class Diagram

![UML class diagram](uml.png)

`Calculator` (the context) has a field of the type `OperationStrategy`, so it depends only on the interface. The five concrete strategies implement the interface. The client `Main` creates a strategy and passes it to the calculator, and `Calculator` does not know which concrete class it works with.

## 3. Clean Code

### 3.1 Elimination of conditional logic in favor of polymorphism

Before (the calculator checks the operation by itself):

```java
public double calculate(String operation, double a, double b) {
    if (operation.equals("+")) {
        return a + b;
    } else if (operation.equals("-")) {
        return a - b;
    } else if (operation.equals("*")) {
        return a * b;
    } else if (operation.equals("/")) {
        return a / b;
    }
    throw new IllegalArgumentException("Unknown operation");
}
```

After (one line, the right algorithm is chosen by polymorphism):

```java
public double calculate(double a, double b) {
    return strategy.execute(a, b);
}
```

There is no `if` or `switch` on the operation type in `Calculator`. Which code runs depends on the object that was passed to `setStrategy`.

### 3.2 Meaningful, intention-revealing names

Each strategy is named after what it does, and the method names say the same for all of them.

Before:

```java
class Op1 implements Op {
    public double run(double x, double y) {
        return x + y;
    }
}
```

After:

```java
public class AddStrategy implements OperationStrategy {

    @Override
    public double execute(double a, double b) {
        return a + b;
    }

    @Override
    public String getSymbol() {
        return "+";
    }
}
```

From the names `OperationStrategy`, `AddStrategy` and `execute` it is clear what the code does without reading the body.

### 3.3 Single Responsibility: each strategy does exactly one algorithm

Every strategy class contains one algorithm and nothing else. `DivideStrategy` is the only place that knows about division and the check for zero:

```java
public class DivideStrategy implements OperationStrategy {

    @Override
    public double execute(double a, double b) {
        if (b == 0) {
            throw new ArithmeticException("Cannot divide by zero");
        }
        return a / b;
    }

    @Override
    public String getSymbol() {
        return "/";
    }
}
```

Before, this check was one more `if` branch inside a big method together with all other operations. Now, if the rule for division changes, only `DivideStrategy` is changed.

### 3.4 Context remains algorithm-agnostic

`Calculator` imports only the interface, never a concrete strategy:

```java
import strategy.OperationStrategy;

public class Calculator {
    private OperationStrategy strategy;

    public Calculator(OperationStrategy strategy) {
        this.strategy = strategy;
    }

    public void setStrategy(OperationStrategy strategy) {
        this.strategy = strategy;
    }

    public double calculate(double a, double b) {
        return strategy.execute(a, b);
    }
}
```

The calculator does not know how any operation works. It does not even know how many operations exist. The choice of the strategy is made by the client:

```java
calculator.setStrategy(new MultiplyStrategy());
```

### 3.5 Easy extensibility: new strategy without touching existing classes

After the first four strategies were ready, I added `PowerStrategy` in a separate commit. I only created one new file:

```java
public class PowerStrategy implements OperationStrategy {

    @Override
    public double execute(double a, double b) {
        return Math.pow(a, b);
    }

    @Override
    public String getSymbol() {
        return "^";
    }
}
```

`Calculator`, the interface and the other four strategies were not changed. The only change in the client was the line that uses the new strategy. In the version with `if/else` the new operation would require editing the calculator method and could break the old operations. This is the Open/Closed principle: the code is open for extension and closed for modification.

## 4. Strategy vs. State

Both patterns have the same structure: a context holds a reference to an interface and delegates the work to it. The difference is in the purpose and in who changes the object. In my calculator the operation is chosen from outside by the client (`calculator.setStrategy(new AddStrategy())`), and the strategies do not know about each other and never switch themselves. The calculator also has no life cycle: the result of one calculation does not depend on the previous one. This is the Strategy pattern, a choice of one of several interchangeable algorithms. In the State pattern the object changes its behavior because its own internal state changes, and the states usually decide by themselves which state comes next (for example, an order that goes from "new" to "paid" to "shipped"). Since there are no states and no transitions in the calculator, only different ways to do the same job, Strategy is the correct choice.

## 5. Conclusion

With the Strategy pattern the calculator became more flexible. A new operation is added by creating one class, the operation can be changed at runtime, and the `Calculator` class never changes and has no `if` or `switch`. Each algorithm can be tested and read separately.

The price is that the number of classes grew: instead of one class with one method there are now one interface, five strategies and a context, seven types in total. For a calculator with only four operations this is more than needed, and a simple `switch` would be shorter. But when operations are added often or are complex, the extra classes are worth it, because the existing code stays safe from changes.

## 6. GitHub Repository

Link: **<INSERT YOUR GITHUB REPOSITORY LINK HERE>**
