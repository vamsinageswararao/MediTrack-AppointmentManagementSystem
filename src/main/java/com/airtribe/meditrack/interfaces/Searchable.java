package com.airtribe.meditrack.interfaces;

public interface Searchable {
    /**
     * Checks if the entity matches the given keyword
     */
    boolean matches(String keyword);
}
