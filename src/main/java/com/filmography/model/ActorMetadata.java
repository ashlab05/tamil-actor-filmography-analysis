package com.filmography.model;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public final class ActorMetadata {
    public static final int CURRENT_YEAR = 2026;

    private static final Map<String, LocalDate> BIRTH_DATES = new HashMap<>();

    static {
        BIRTH_DATES.put("Ajith", LocalDate.of(1971, 5, 1));
        BIRTH_DATES.put("Vijay", LocalDate.of(1974, 6, 22));
    }

    private ActorMetadata() {
    }

    public static LocalDate getBirthDate(String actor) {
        return BIRTH_DATES.get(actor);
    }

    public static int getBirthYear(String actor) {
        LocalDate date = getBirthDate(actor);
        return date == null ? 0 : date.getYear();
    }

    public static int getCurrentAge(String actor) {
        LocalDate date = getBirthDate(actor);
        if (date == null) {
            return 0;
        }
        return CURRENT_YEAR - date.getYear();
    }
}
