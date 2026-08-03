package reports;

import model.Player;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

public class ReportService {

    public String computeStats(List<Player> players) {
        StringBuilder sb = new StringBuilder();
        sb.append("=== REPORTS (as of ")
          .append(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))
          .append(") ===\n");

        sb.append("Total players: ").append(players.size()).append('\n');
        int totalWins = players.stream().mapToInt(Player::getWins).sum();
        int totalLosses = players.stream().mapToInt(Player::getLosses).sum();
        long totalScore = players.stream().mapToLong(Player::getTotalScore).sum();
        sb.append("Total wins: ").append(totalWins).append(" | Total losses: ").append(totalLosses).append('\n');
        sb.append("Total cumulative score: ").append(totalScore).append('\n');

        double avgWins = players.stream().mapToInt(Player::getWins).average().orElse(0.0);
        double avgScore = players.stream().mapToLong(Player::getTotalScore).average().orElse(0.0);
        sb.append(String.format(Locale.ROOT, "Average wins/player: %.2f | Average score/player: %.2f\n", avgWins, avgScore));

        players.stream()
                .max(Comparator.comparingInt(Player::getWins))
                .ifPresent(p -> sb.append("Top by wins: ").append(p.getName()).append(" ("+p.getWins()+")\n"));

        players.stream()
                .max(Comparator.comparingLong(Player::getTotalScore))
                .ifPresent(p -> sb.append("Top by total score: ").append(p.getName()).append(" ("+p.getTotalScore()+")\n"));

        Map<Integer, Long> distribution = players.stream()
                .collect(Collectors.groupingBy(Player::getWins, Collectors.counting()));
        sb.append("Win distribution (wins -> count): ").append(distribution).append('\n');

        return sb.toString();
    }
}
