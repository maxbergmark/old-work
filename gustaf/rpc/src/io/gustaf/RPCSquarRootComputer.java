package io.gustaf;

public class RPCSquarRootComputer implements SquareRootComputer {
    @Override
    public double compute(double n) {
        send(n);
        return waitForResponse();
    }

    void send(double n);

    double waitForResponse();
}
