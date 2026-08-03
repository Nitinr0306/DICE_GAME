package model;

import java.util.Random;

public class Dice {
    private final int sides;
    private final Random random = new Random();

    public Dice(int sides) {
        if (sides < 2) throw new IllegalArgumentException("Dice must have at least 2 sides");
        this.sides = sides;
    }

    public int getSides() {
        return sides;
    }

    public int roll() {
        return random.nextInt(sides) + 1;
    }
}
