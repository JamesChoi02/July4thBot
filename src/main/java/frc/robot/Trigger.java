package frc.robot;

import java.util.function.Supplier;

public class Trigger extends edu.wpi.first.wpilibj.buttons.Trigger {
    private Supplier<Boolean> condition;

    public Trigger(Supplier<Boolean> condition) {
        this.condition = condition;
    }

    @Override
    public boolean get() {
        return condition.get();
    }
}
