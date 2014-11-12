package cz.kobzol.bulanci.model;

/**
 * Represents time cooldown
 */
public class Cooldown implements IUpdatable {
    private final long interval;
    private long value;

    public Cooldown(long interval) {
        this.interval = interval;
    }

    public boolean isReady() {
        return this.value > this.interval;
    }
    public void reset() {
        this.value = 0;
    }

    @Override
    public void update() {
        this.value++;
    }
}
