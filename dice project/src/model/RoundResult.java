package model;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class RoundResult {
    private final Map<Player, Integer> scoresByPlayer = new LinkedHashMap<>();
    private final Optional<Player> winner;
    private final int highestScore;

    public RoundResult(Map<Player, Integer> scoresByPlayer, Optional<Player> winner, int highestScore) {
        Objects.requireNonNull(scoresByPlayer, "scoresByPlayer");
        this.scoresByPlayer.putAll(scoresByPlayer);
        this.winner = winner;
        this.highestScore = highestScore;
    }

    public Map<Player, Integer> getScoresByPlayer() {
        return Collections.unmodifiableMap(scoresByPlayer);
    }

    public Optional<Player> getWinner() {
        return winner;
    }

    public int getHighestScore() {
        return highestScore;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Scores: ");
        boolean first = true;
        for (Map.Entry<Player,Integer> e : scoresByPlayer.entrySet()) {
            if (!first) sb.append(", ");
            first = false;
            sb.append(e.getKey().getName()).append("=").append(e.getValue());
        }
        sb.append(" | ");
        if (winner.isPresent()) {
            sb.append("Winner: ").append(winner.get().getName())
              .append(" (score=").append(highestScore).append(")");
        } else {
            sb.append("Tie at score ").append(highestScore);
        }
        return sb.toString();
    }
}
