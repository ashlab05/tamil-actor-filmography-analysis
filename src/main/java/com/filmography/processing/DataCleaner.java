package com.filmography.processing;

import com.filmography.model.Film;

import java.text.Normalizer;
import java.util.List;
import java.util.regex.Pattern;

public class DataCleaner {
    private static final Pattern FOOTNOTE = Pattern.compile("\\[[a-z]\\]");

    public void clean(List<Film> films) {
        for (Film film : films) {
            film.setFilm(cleanFilm(film.getFilm()));
            film.setRole(cleanRole(film.getRole()));
            film.setNotes(cleanNotes(film.getNotes()));
        }
    }

    private String cleanFilm(String film) {
        if (film == null) {
            return null;
        }
        String normalized = Normalizer.normalize(film, Normalizer.Form.NFD);
        normalized = normalized.replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
        normalized = normalized.replace("†", "");
        return normalized.trim();
    }

    private String cleanRole(String role) {
        if (role == null) {
            return null;
        }
        String cleaned = FOOTNOTE.matcher(role).replaceAll("");
        return cleaned.trim();
    }

    private String cleanNotes(String notes) {
        if (notes == null) {
            return null;
        }
        String cleaned = notes.trim();
        if (cleaned.isEmpty()) {
            return null;
        }
        cleaned = cleaned.replace("Child artist", "Child Actor");
        cleaned = cleaned.replace("Extended Cameo", "Cameo");
        return cleaned.trim();
    }
}
