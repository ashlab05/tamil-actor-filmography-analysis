package com.filmography.io;

import com.filmography.model.Film;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class CsvWriter {

    public void writeCleaned(String path, List<Film> films) throws IOException {
        try (FileWriter writer = new FileWriter(path)) {
            writer.write("Actor,Year,Film,Role,Debut_Year,Lead_Debut_Year,Career_Span,Career_Phase,Cumulative_Movies,Movies_Per_Year,Release_Gap,High_Productivity,Is_Special,Age_At_Film,Age_At_Debut,Current_Age,Is_Child_Role,Is_Upcoming\n");
            for (Film film : films) {
                writer.write(escape(film.getActor()) + "," +
                        film.getYear() + "," +
                        escape(film.getFilm()) + "," +
                        escape(film.getRole()) + "," +
                        value(film.getDebutYear()) + "," +
                        value(film.getLeadDebutYear()) + "," +
                        value(film.getCareerSpan()) + "," +
                        escape(film.getCareerPhase()) + "," +
                        value(film.getCumulativeMovies()) + "," +
                        value(film.getMoviesPerYear()) + "," +
                        value(film.getReleaseGap()) + "," +
                        value(film.getHighProductivity()) + "," +
                        value(film.getIsSpecial()) + "," +
                        value(film.getAgeAtFilm()) + "," +
                        value(film.getAgeAtDebut()) + "," +
                        value(film.getCurrentAge()) + "," +
                        value(film.getIsChildRole()) + "," +
                        value(film.getIsUpcoming()) +
                        "\n");
            }
        }
    }

    private String value(Object value) {
        return value == null ? "" : String.valueOf(value);
    }

    private String escape(String value) {
        if (value == null) {
            return "";
        }
        String escaped = value.replace("\"", "\"\"");
        if (escaped.contains(",") || escaped.contains("\"") || escaped.contains("\n")) {
            return "\"" + escaped + "\"";
        }
        return escaped;
    }
}
