package frc.robot.filter;

import java.util.function.Supplier;

public class RangeFilter extends Filter {
    private double max, min;

    public RangeFilter(Supplier<Double> input, double min, double max) {
        super(input);
        this.min = min;
        this.max = max;
    }

    @Override
    public Double get(double input) {
        return Math.min(Math.max(input, min), max);
    }
}
