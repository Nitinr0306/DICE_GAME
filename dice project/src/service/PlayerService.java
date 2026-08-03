package service;

import enums.SortOption;
import model.Player;
import repository.Repository;

import java.util.*;
import java.util.stream.Collectors;

public class PlayerService {
    private final Repository<Player, String> repository;
    private final Map<String, Player> index = new HashMap<>();

    public PlayerService(Repository<Player, String> repository) {
        this.repository = repository;
        loadIndex();
    }

    private void loadIndex() {
        index.clear();
        for (Player p : repository.findAll()) {
            index.put(p.getId(), p);
        }
    }

    public Player addPlayer(String name) {
        Objects.requireNonNull(name);
        String trimmed = name.trim();
        if (trimmed.isEmpty()) throw new IllegalArgumentException("Name cannot be empty");
        Player p = new Player(trimmed);
        index.put(p.getId(), p);
        repository.save(p);
        return p;
    }

    public List<Player> findAll() {
        return new ArrayList<>(index.values());
    }

    public Optional<Player> findById(String id) {
        return Optional.ofNullable(index.get(id));
    }

    public boolean deleteById(String id) {
        boolean removed = index.remove(id) != null;
        if (removed) repository.deleteById(id);
        return removed;
    }

    public List<Player> searchByName(String term) {
        String lc = term.toLowerCase(Locale.ROOT);
        return index.values().stream()
                .filter(p -> p.getName().toLowerCase(Locale.ROOT).contains(lc))
                .sorted(Comparator.comparing(Player::getName))
                .collect(Collectors.toList());
    }

    public List<Player> getLeaderboard(SortOption option) {
        Comparator<Player> byWinsDesc = Comparator.comparingInt(Player::getWins).reversed()
                .thenComparing(Player::getName);
        Comparator<Player> byNameAsc = Comparator.comparing(Player::getName);
        Comparator<Player> comp = switch (option) {
            case WINS_DESC -> byWinsDesc;
            case NAME_ASC -> byNameAsc;
        };
        return index.values().stream().sorted(comp).collect(Collectors.toList());
    }

    public void saveAll() {
        repository.saveAll(new ArrayList<>(index.values()));
    }
}
