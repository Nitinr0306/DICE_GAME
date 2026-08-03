package app;

import config.GameConfig;
import enums.SortOption;
import exception.InvalidInputException;
import model.Player;
import reports.ReportService;
import repository.FilePlayerRepository;
import repository.Repository;
import service.GameEngine;
import service.PlayerService;
import util.ConsoleUtils;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

public class Main {
    private final ConsoleUtils console = new ConsoleUtils();
    private final Repository<Player, String> repository = new FilePlayerRepository("players.dat");
    private final PlayerService playerService = new PlayerService(repository);
    private final GameEngine gameEngine = new GameEngine();
    private final ReportService reportService = new ReportService();

    public static void main(String[] args) {
        new Main().run();
    }

    private void run() {
        console.println("========================================");
        console.println("DICE TOURNAMENT");
        console.println("========================================");
        boolean exit = false;
        while (!exit) {
            try {
                showMenu();
                int choice = console.readInt("Enter your choice: ", 1, 9);
                switch (choice) {
                    case 1 -> addPlayer();
                    case 2 -> listPlayers();
                    case 3 -> searchPlayer();
                    case 4 -> deletePlayer();
                    case 5 -> playSingleRound();
                    case 6 -> playMatch();
                    case 7 -> showLeaderboard();
                    case 8 -> showReports();
                    case 9 -> exit = true;
                    default -> console.println("Invalid choice.");
                }
            } catch (InvalidInputException ex) {
                console.error(ex.getMessage());
            } catch (Exception ex) {
                console.error("Unexpected error: " + ex.getMessage());
            }
            console.println("");
        }
        console.println("Goodbye!");
    }

    private void showMenu() {
        console.println("\n1. Add Player");
        console.println("2. View Players");
        console.println("3. Search Player by Name");
        console.println("4. Delete Player");
        console.println("5. Play Single Round");
        console.println("6. Play Match (multiple rounds)");
        console.println("7. Leaderboard");
        console.println("8. Reports");
        console.println("9. Exit");
    }

    private void addPlayer() {
        String name = console.readNonEmptyString("Enter player name: ");
        Player p = playerService.addPlayer(name);
        console.success("Player added with ID: " + p.getId());
    }

    private void listPlayers() {
        List<Player> players = playerService.findAll();
        if (players.isEmpty()) {
            console.println("No players found.");
            return;
        }
        console.println("\nPlayers:");
        players.forEach(p -> console.println(p.toDisplayString()));
    }

    private void searchPlayer() {
        String term = console.readNonEmptyString("Enter name (full or partial): ");
        List<Player> players = playerService.searchByName(term);
        if (players.isEmpty()) {
            console.println("No matching players.");
            return;
        }
        players.forEach(p -> console.println(p.toDisplayString()));
    }

    private void deletePlayer() {
        String id = console.readNonEmptyString("Enter player ID to delete: ");
        boolean removed = playerService.deleteById(id);
        if (removed) console.success("Player deleted."); else console.println("Player not found.");
    }

    private GameConfig readConfig() {
        int diceSides = console.readInt("Number of sides per die (e.g., 6): ", 2, 100);
        int diceCount = console.readInt("Number of dice per player (1-5): ", 1, 5);
        return new GameConfig.Builder()
                .withDiceSides(diceSides)
                .withDiceCount(diceCount)
                .build();
    }

    private void playSingleRound() {
        List<Player> players = ensureAtLeastTwoPlayers();
        if (players == null) return;
        GameConfig config = readConfig();
        var result = gameEngine.playRound(players, config);
        console.println(result.toString());
        playerService.saveAll();
    }

    private void playMatch() {
        List<Player> players = ensureAtLeastTwoPlayers();
        if (players == null) return;
        GameConfig config = readConfig();
        int rounds = console.readInt("How many rounds? ", 1, 1000);
        var results = gameEngine.playMatch(players, config, rounds);
        console.println("Match finished at " + LocalDateTime.now(ZoneId.systemDefault()));
        console.println("Round results:");
        results.forEach(r -> console.println(r.toString()));
        playerService.saveAll();
    }

    private void showLeaderboard() {
        console.println("Sort by: 1) WINS DESC  2) NAME ASC");
        int pick = console.readInt("Choose option: ", 1, 2);
        SortOption option = (pick == 1) ? SortOption.WINS_DESC : SortOption.NAME_ASC;
        List<Player> sorted = playerService.getLeaderboard(option);
        console.println("Leaderboard:");
        int rank = 1;
        for (Player p : sorted) {
            console.println(rank + ". " + p.leaderboardString());
            rank++;
        }
    }

    private void showReports() {
        var stats = reportService.computeStats(playerService.findAll());
        console.println(stats);
    }

    private List<Player> ensureAtLeastTwoPlayers() {
        List<Player> players = playerService.findAll();
        if (players.size() < 2) {
            console.error("At least two players are required. Add more players first.");
            return null;
        }
        return players;
    }
}
