package config;

/**
 * Immutable configuration for a game or match.
 * Built with Builder pattern to clearly demonstrate configuration options.
 */
public final class GameConfig {
    private final int diceSides;
    private final int diceCount;

    private GameConfig(Builder builder) {
        this.diceSides = builder.diceSides;
        this.diceCount = builder.diceCount;
    }

    public int getDiceSides() {
        return diceSides;
    }

    public int getDiceCount() {
        return diceCount;
    }

    @Override
    public String toString() {
        return "GameConfig{" +
                "diceSides=" + diceSides +
                ", diceCount=" + diceCount +
                '}';
    }

    public static class Builder {
        private int diceSides = 6;
        private int diceCount = 1;

        public Builder withDiceSides(int diceSides) {
            if (diceSides < 2) throw new IllegalArgumentException("diceSides must be >= 2");
            this.diceSides = diceSides;
            return this;
            
        }

        public Builder withDiceCount(int diceCount) {
            if (diceCount < 1) throw new IllegalArgumentException("diceCount must be >= 1");
            this.diceCount = diceCount;
            return this;
        }

        public GameConfig build() {
            return new GameConfig(this);
        }
    }
}
