package model;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

public class Player implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private final String id;
    private String name;
    private int wins;
    private int losses;
    private long totalScore; // cumulative points across rounds

    public Player(String name) {
        this(UUID.randomUUID().toString(), name);
    }

    public Player(String id, String name) {
        this.id = Objects.requireNonNull(id, "id");
        this.name = Objects.requireNonNull(name, "name").trim();
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = Objects.requireNonNull(name).trim(); }

    public int getWins() { return wins; }
    public int getLosses() { return losses; }
    public long getTotalScore() { return totalScore; }

    public void addScore(int score) { this.totalScore += score; }
    public void addWin() { this.wins++; }
    public void addLoss() { this.losses++; }

    public String toDisplayString() {
        return String.format("%s | %s | wins=%d, losses=%d, total=%d", id, name, wins, losses, totalScore);
    }

    public String leaderboardString() {
        return String.format("%s (wins=%d, losses=%d, total=%d)", name, wins, losses, totalScore);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return id.equals(player.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
