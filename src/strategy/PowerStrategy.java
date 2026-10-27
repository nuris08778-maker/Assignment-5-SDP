package strategy;

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
