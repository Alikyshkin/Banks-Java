package model;

public class InterestPair {
    private final double interest;
    private final double sum;

    public InterestPair(double sum, double interest) {
        this.sum = sum;
        this.interest = interest;
    }

    public double getInterest() {
        return interest;
    }

    public double getSum() {
        return sum;
    }
}
