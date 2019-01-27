package io.gustaf;

public class LocalNewtonSquareRootComputer implements SquareRootComputer {
    @Override
    public double compute(double n) {
        return Math.sqrt(n);
    }
}
