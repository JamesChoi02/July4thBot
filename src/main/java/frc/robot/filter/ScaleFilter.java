package frc.robot.filter;

import java.util.function.Supplier;

public class ScaleFilter extends Filter {
    private double scalar;

    public ScaleFilter(Supplier<Double> input, double scalar) {
        super(input);
        this.scalar = scalar;
    }

    @Override
    public Double get(double input) {
        return input * scalar;
    }
}
