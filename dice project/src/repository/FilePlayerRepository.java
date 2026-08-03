package repository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import model.Player;

public class FilePlayerRepository implements Repository<Player, String> {
    private final File file;

    public FilePlayerRepository(String filePath) {
        this.file = new File(filePath);
    }

    @SuppressWarnings("unchecked")
    private List<Player> readAllInternal() {
        if (!file.exists() || file.length() == 0) {
            return new ArrayList<>();
        }
        try (ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream(file)))) {
            Object obj = ois.readObject();
            if (obj instanceof List<?>) {
                return (List<Player>) obj;
            }
        } catch (Exception e) {
            // Corruption or incompatible version: fallback to empty list
        }
        return new ArrayList<>();
    }

    private void writeAllInternal(List<Player> players) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(file)))) {
            oos.writeObject(players);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    @Override
    public List<Player> findAll() {
        return new ArrayList<>(readAllInternal());
    }

    @Override
    public Optional<Player> findById(String id) {
        return readAllInternal().stream().filter(p -> p.getId().equals(id)).findFirst();
    }

    @Override
    public Player save(Player entity) {
        List<Player> all = readAllInternal();
        for (int i = 0; i < all.size(); i++) {
            if (all.get(i).getId().equals(entity.getId())) {
                all.set(i, entity);
                writeAllInternal(all);
                return entity;
            }
        }
        all.add(entity);
        writeAllInternal(all);
        return entity;
    }

    @Override
    public List<Player> saveAll(List<Player> entities) {
        writeAllInternal(new ArrayList<>(entities));
        return entities;
    }

    @Override
    public boolean deleteById(String id) {
        List<Player> all = readAllInternal();
        boolean removed = all.removeIf(p -> p.getId().equals(id));
        if (removed) writeAllInternal(all);
        return removed;
    }
}
