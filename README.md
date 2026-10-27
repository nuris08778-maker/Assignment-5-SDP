# Calculator with Strategy Pattern

**Student:** Nursultan Nurislam, SE - 2538
**Course:** ShP-2216 - Software Design Patterns
**Assignment #5:** Strategy Pattern (Option B - Calculator)

## About

This project shows the Strategy design pattern in Java. The `Calculator` does not know how to add, subtract or multiply. It only holds an `OperationStrategy` and calls it. Each operation is a separate class, and the strategy can be changed while the program is running. There is no `if` or `switch` on the operation type anywhere in the `Calculator`.

## Project structure

```
strategy-calculator
├── README.md
├── .gitignore
└── src
    ├── Main.java
    ├── context
    │   └── Calculator.java
    └── strategy
        ├── OperationStrategy.java
        ├── AddStrategy.java
        ├── SubtractStrategy.java
        ├── MultiplyStrategy.java
        ├── DivideStrategy.java
        └── PowerStrategy.java
```

## Classes

| Class | Role |
|-------|------|
| `OperationStrategy` | Strategy interface with `execute(a, b)` and `getSymbol()` |
| `AddStrategy` | Concrete strategy, a + b |
| `SubtractStrategy` | Concrete strategy, a - b |
| `MultiplyStrategy` | Concrete strategy, a * b |
| `DivideStrategy` | Concrete strategy, a / b (error if b is 0) |
| `PowerStrategy` | Concrete strategy, a ^ b (added later to show extensibility) |
| `Calculator` | Context, holds a strategy and delegates the calculation to it |
| `Main` | Client, chooses the strategy and switches it at runtime |

## How to run

Requirements: JDK 17.

From the project root folder:

```
mkdir out
javac -d out $(find src -name "*.java")
java -cp out Main
```

On Windows PowerShell:

```
mkdir out
javac -d out (Get-ChildItem -Recurse -Filter *.java src | ForEach-Object { $_.FullName })
java -cp out Main
```

You can also open the folder in IntelliJ IDEA, mark `src` as Sources Root and run `Main`.

## Example output

```
=== Switching strategies at runtime ===
10.0 + 5.0 = 15.0
10.0 - 5.0 = 5.0
10.0 * 5.0 = 50.0
10.0 / 5.0 = 2.0

=== New strategy added without changing existing classes ===
2.0 ^ 10.0 = 1024.0

=== Error case ===
Error: Cannot divide by zero
```

## How to add a new strategy

1. Create a new class that implements `OperationStrategy`.
2. Pass it to the calculator with `calculator.setStrategy(new YourStrategy())`.

`Calculator` and the other strategies do not need any changes.
