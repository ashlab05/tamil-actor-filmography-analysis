package com.filmography.analysis;

import com.filmography.model.ActorMetadata;
import com.filmography.model.Film;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class DataExplorer {
    private static final Pattern FOOTNOTE = Pattern.compile("\\[[a-z]\\]");

    public Map<String, Object> summarizeBefore(List<Film> films) {
        Map<String, Object> stats = new LinkedHashMap<>();
        int total = films.size();

        stats.put("Shape (Rows x Columns)", total + " x 5");

        Map<String, Long> actorCounts = films.stream()
                .collect(Collectors.groupingBy(Film::getActor, Collectors.counting()));
        for (Map.Entry<String, Long> e : actorCounts.entrySet()) {
            stats.put(e.getKey() + " Films", e.getValue());
        }

        int minYear = films.stream().mapToInt(Film::getYear).min().orElse(0);
        int maxYear = films.stream().mapToInt(Film::getYear).max().orElse(0);
        stats.put("Year Range", minYear + " - " + maxYear);

        int nullFilm = 0, nullRole = 0, nullNotes = 0;
        int footnotes = 0;
        int childRoles = 0;

        for (Film film : films) {
            if (film.getFilm() == null || film.getFilm().isEmpty()) nullFilm++;
            if (film.getRole() == null || film.getRole().isEmpty()) nullRole++;
            if (film.getNotes() == null || film.getNotes().isEmpty()) nullNotes++;
            if (film.getRole() != null && FOOTNOTE.matcher(film.getRole()).find()) footnotes++;
            if (film.getNotes() != null && film.getNotes().toLowerCase().contains("child")) childRoles++;
        }

        stats.put("Null/Empty in Film", nullFilm);
        stats.put("Null/Empty in Role", nullRole);
        stats.put("Null/Empty in Notes", nullNotes);
        stats.put("Total Null Values", nullFilm + nullRole + nullNotes);
        stats.put("Footnote Annotations [a-z]", footnotes);
        stats.put("Child Actor Roles", childRoles);

        long duplicates = total - films.stream().map(f -> f.getActor() + "|" + f.getYear() + "|" + f.getFilm()).distinct().count();
        stats.put("Duplicate Rows", duplicates);

        return stats;
    }

    public Map<String, Object> summarizeAfter(List<Film> films) {
        Map<String, Object> stats = new LinkedHashMap<>();
        int total = films.size();

        stats.put("Shape (Rows x Columns)", total + " x 18");

        Map<String, Long> actorCounts = films.stream()
                .collect(Collectors.groupingBy(Film::getActor, Collectors.counting()));
        for (Map.Entry<String, Long> e : actorCounts.entrySet()) {
            stats.put(e.getKey() + " Films", e.getValue());
        }

        int minYear = films.stream().mapToInt(Film::getYear).min().orElse(0);
        int maxYear = films.stream().mapToInt(Film::getYear).max().orElse(0);
        stats.put("Year Range", minYear + " - " + maxYear);

        int nullDebutYear = 0, nullCareerPhase = 0, nullAgeAtFilm = 0;
        for (Film film : films) {
            if (film.getDebutYear() == null) nullDebutYear++;
            if (film.getCareerPhase() == null) nullCareerPhase++;
            if (film.getAgeAtFilm() == null) nullAgeAtFilm++;
        }
        stats.put("Null Debut_Year", nullDebutYear);
        stats.put("Null Career_Phase", nullCareerPhase);
        stats.put("Null Age_At_Film", nullAgeAtFilm);

        long childRoles = films.stream().filter(f -> Boolean.TRUE.equals(f.getIsChildRole())).count();
        stats.put("Child Actor Roles", childRoles);

        long specialFilms = films.stream().filter(f -> Boolean.TRUE.equals(f.getIsSpecial())).count();
        stats.put("Special Films (Cameo/Debut/etc)", specialFilms);

        long upcoming = films.stream().filter(f -> Boolean.TRUE.equals(f.getIsUpcoming())).count();
        stats.put("Upcoming Films (2025+)", upcoming);

        long highProd = films.stream().filter(f -> Boolean.TRUE.equals(f.getHighProductivity())).count();
        stats.put("High Productivity Film-Years", highProd);

        Map<String, Long> phases = films.stream()
                .filter(f -> f.getCareerPhase() != null)
                .collect(Collectors.groupingBy(Film::getCareerPhase, Collectors.counting()));
        for (Map.Entry<String, Long> e : phases.entrySet()) {
            stats.put("Phase: " + e.getKey(), e.getValue());
        }

        for (String actor : actorCounts.keySet()) {
            int birthYear = ActorMetadata.getBirthYear(actor);
            int debutYear = films.stream()
                    .filter(f -> f.getActor().equals(actor))
                    .mapToInt(Film::getYear)
                    .min().orElse(0);
            stats.put(actor + " Debut Year", debutYear);
            stats.put(actor + " Debut Age", debutYear - birthYear);
            stats.put(actor + " Current Age", ActorMetadata.getCurrentAge(actor));
            stats.put(actor + " Career Span (years)", ActorMetadata.CURRENT_YEAR - debutYear);

            Map<Integer, Long> byYear = films.stream()
                    .filter(f -> f.getActor().equals(actor))
                    .collect(Collectors.groupingBy(Film::getYear, Collectors.counting()));
            int peakYear = byYear.entrySet().stream()
                    .max(java.util.Map.Entry.comparingByValue())
                    .map(java.util.Map.Entry::getKey).orElse(0);
            long peakCount = byYear.getOrDefault(peakYear, 0L);
            stats.put(actor + " Peak Year", peakYear + " (" + peakCount + " films)");
        }

        return stats;
    }

    public Map<String, Object> summarize(List<Film> films) {
        return summarizeBefore(films);
    }
}
