import context.Calculator;
import strategy.AddStrategy;
import strategy.DivideStrategy;
import strategy.MultiplyStrategy;
import strategy.OperationStrategy;
import strategy.PowerStrategy;
import strategy.SubtractStrategy;

public class Main {

    public static void main(String[] args) {
        Calculator calculator = new Calculator(new AddStrategy());

        System.out.println("=== Switching strategies at runtime ===");
        calculate(calculator, new AddStrategy(), 10, 5);
        calculate(calculator, new SubtractStrategy(), 10, 5);
        calculate(calculator, new MultiplyStrategy(), 10, 5);
        calculate(calculator, new DivideStrategy(), 10, 5);

        System.out.println();
        System.out.println("=== New strategy added without changing existing classes ===");
        calculate(calculator, new PowerStrategy(), 2, 10);

        System.out.println();
        System.out.println("=== Error case ===");
        try {
            calculate(calculator, new DivideStrategy(), 10, 0);
        } catch (ArithmeticException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void calculate(Calculator calculator, OperationStrategy strategy, double a, double b) {
        calculator.setStrategy(strategy);
        double result = calculator.calculate(a, b);
        System.out.println(a + " " + strategy.getSymbol() + " " + b + " = " + result);
    }
}
