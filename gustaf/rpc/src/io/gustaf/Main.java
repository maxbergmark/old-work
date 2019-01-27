package io.gustaf;

public class Main {

    public static void main(String[] args) {
	    SquareRootComputer squareRootComputer = new RPCSquarRootComputer();
        doMath(squareRootComputer, 24);
    }

    static double doMath(SquareRootComputer squareRootComputer, double n) {
        double b = squareRootComputer.compute(n);
        return b*n;
    }
}
