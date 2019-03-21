package frc.robot.filter;

import java.util.function.Supplier;

public abstract class Filter implements Supplier<Double> {
    private Supplier<Double> input;

    public Filter(Supplier<Double> input) {
        this.input = input;
    }

    @Override
    public Double get() {
        return get(input.get());
    }

    public abstract Double get(double input);
}