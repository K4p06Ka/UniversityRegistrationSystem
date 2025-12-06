package repository;

import java.util.*;

//All our id's has String type, so we only do generic type for classes
public class InMemoryRepository<T> {
    private Map<String, T> storage = new HashMap<>();

    public InMemoryRepository() {

    }

    public Optional<T> findById(String id) {
        return Optional.ofNullable(storage.get(id));
    }

    public List<T> findAll() {
        return new ArrayList<>(storage.values());
    }

    public T save(T entity, String id) {
        storage.put(id, entity);
        return entity;
    }

    public void deleteById(String id) {
        storage.remove(id);
    }
}