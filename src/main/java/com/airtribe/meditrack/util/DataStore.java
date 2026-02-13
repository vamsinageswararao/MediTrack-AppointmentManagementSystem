package com.airtribe.meditrack.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Generic DataStore for managing entities
 * @param <T> the type of entity to store
 */
public class DataStore<T> {
    private Map<String, T> dataMap;

    public DataStore() {
        this.dataMap = new HashMap<>();
    }

    public void add(T entity) {
        String id = extractId(entity);
        dataMap.put(id, entity);
    }

    public T getById(String id) {
        return dataMap.get(id);
    }

    public List<T> getAll() {
        return new ArrayList<>(dataMap.values());
    }

    public void update(T entity) {
        String id = extractId(entity);
        if (dataMap.containsKey(id)) {
            dataMap.put(id, entity);
        }
    }

    public void delete(String id) {
        dataMap.remove(id);
    }

    public boolean exists(String id) {
        return dataMap.containsKey(id);
    }

    public int size() {
        return dataMap.size();
    }

    private String extractId(T entity) {
        try {
            return (String) entity.getClass().getMethod("getId").invoke(entity);
        } catch (Exception e) {
            // Fallback for entities without getId method
            try {
                return (String) entity.getClass().getMethod("getAppointmentId").invoke(entity);
            } catch (Exception ex) {
                try {
                    return (String) entity.getClass().getMethod("getBillId").invoke(entity);
                } catch (Exception exc) {
                    throw new RuntimeException("Unable to extract ID from entity", exc);
                }
            }
        }
    }
}
