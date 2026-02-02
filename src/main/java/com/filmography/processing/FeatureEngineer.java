package com.filmography.processing;

import com.filmography.model.ActorMetadata;
import com.filmography.model.Film;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class FeatureEngineer {
    private static final Pattern SPECIAL = Pattern.compile("debut|cameo|hindi|playback|25th|50th|guest", Pattern.CASE_INSENSITIVE);

    public void engineer(List<Film> films) {
        Map<String, List<Film>> byActor = films.stream()
                .collect(Collectors.groupingBy(Film::getActor));

        for (Map.Entry<String, List<Film>> entry : byActor.entrySet()) {
            String actor = entry.getKey();
            List<Film> actorFilms = new ArrayList<>(entry.getValue());
            actorFilms.sort(Comparator.comparingInt(Film::getYear));

            int debutYear = actorFilms.stream().mapToInt(Film::getYear).min().orElse(0);
            int leadDebutYear = actorFilms.stream()
                    .filter(f -> f.getNotes() == null || !f.getNotes().toLowerCase().contains("child actor"))
                    .mapToInt(Film::getYear)
                    .min().orElse(debutYear);

            int birthYear = ActorMetadata.getBirthYear(actor);
            int currentAge = ActorMetadata.getCurrentAge(actor);

            Map<Integer, Long> moviesPerYear = actorFilms.stream()
                    .collect(Collectors.groupingBy(Film::getYear, Collectors.counting()));

            int cumulative = 0;
            Integer previousYear = null;

            for (Film film : actorFilms) {
                int year = film.getYear();
                cumulative += 1;

                film.setDebutYear(debutYear);
                film.setLeadDebutYear(leadDebutYear);
                film.setCareerSpan(ActorMetadata.CURRENT_YEAR - debutYear);
                film.setCareerPhase(careerPhase(year - debutYear));
                film.setCumulativeMovies(cumulative);
                film.setMoviesPerYear(moviesPerYear.getOrDefault(year, 0L).intValue());

                if (previousYear == null) {
                    film.setReleaseGap(0);
                } else {
                    film.setReleaseGap(year - previousYear);
                }
                previousYear = year;

                film.setHighProductivity(film.getMoviesPerYear() != null && film.getMoviesPerYear() > 1);
                film.setIsSpecial(isSpecial(film.getNotes()));
                film.setAgeAtFilm(birthYear == 0 ? null : year - birthYear);
                film.setAgeAtDebut(birthYear == 0 ? null : debutYear - birthYear);
                film.setCurrentAge(currentAge == 0 ? null : currentAge);
                film.setIsChildRole(isChildRole(film.getNotes()));
                film.setIsUpcoming(year >= 2025);
            }
        }
    }

    private String careerPhase(int careerAge) {
        if (careerAge <= 5) {
            return "Early";
        }
        if (careerAge <= 15) {
            return "Growth";
        }
        return "Peak";
    }

    private boolean isSpecial(String notes) {
        if (notes == null) {
            return false;
        }
        return SPECIAL.matcher(notes).find();
    }

    private boolean isChildRole(String notes) {
        if (notes == null) {
            return false;
        }
        return notes.toLowerCase().contains("child actor");
    }
}
