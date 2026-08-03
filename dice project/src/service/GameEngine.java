package service;

import config.GameConfig;
import model.Dice;
import model.Player;
import model.RoundResult;

import java.util.*;

public class GameEngine {

    public RoundResult playRound(List<Player> players, GameConfig config) {
        Objects.requireNonNull(players, "players");
        if (players.size() < 2) throw new IllegalArgumentException("At least two players required");
        Objects.requireNonNull(config, "config");

        Map<Player, Integer> scores = new LinkedHashMap<>();
        Dice dice = new Dice(config.getDiceSides());

        int highest = 0;
        Player winner = null;
        boolean tie = false;

        for (Player p : players) {
            int total = 0;
            for (int i = 0; i < config.getDiceCount(); i++) {
                total += dice.roll();
            }
            scores.put(p, total);
            p.addScore(total);
            if (total > highest) {
                highest = total;
                winner = p;
                tie = false;
            } else if (total == highest) {
                tie = true;
            }
        }

        if (tie) {
            return new RoundResult(scores, Optional.empty(), highest);
        } else {
            if (winner != null) {
                winner.addWin();
                for (Player p : players) {
                    if (p != winner) p.addLoss();
                }
            }
            return new RoundResult(scores, Optional.ofNullable(winner), highest);
        }
    }

    public List<RoundResult> playMatch(List<Player> players, GameConfig config, int rounds) {
        List<RoundResult> results = new ArrayList<>();
        for (int i = 0; i < rounds; i++) {
            results.add(playRound(players, config));
        }
        return results;
    }
}
