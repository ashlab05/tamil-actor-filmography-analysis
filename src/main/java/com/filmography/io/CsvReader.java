package com.filmography.io;

import com.filmography.model.Film;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CsvReader {

    public List<Film> read(String path, String actor) throws IOException {
        List<Film> films = new ArrayList<>();
        try (CSVReader reader = new CSVReader(new FileReader(path))) {
            String[] header;
            try {
                header = reader.readNext();
            } catch (CsvValidationException ex) {
                return films;
            }
            if (header == null) {
                return films;
            }
            Map<String, Integer> headerIndex = mapHeader(header);
            String[] row;
            while (true) {
                try {
                    row = reader.readNext();
                } catch (CsvValidationException ex) {
                    continue;
                }
                if (row == null) {
                    break;
                }
                String yearStr = getCell(row, headerIndex, "Year");
                String film = getCell(row, headerIndex, "Film");
                String role = getCell(row, headerIndex, "Role");
                String notes = getCell(row, headerIndex, "Notes");

                if (yearStr == null || yearStr.isEmpty()) {
                    continue;
                }

                int year;
                try {
                    year = Integer.parseInt(yearStr.trim());
                } catch (NumberFormatException ex) {
                    continue;
                }

                Film filmRow = new Film(actor, year, film, role, notes);
                films.add(filmRow);
            }
        }
        return films;
    }

    private Map<String, Integer> mapHeader(String[] header) {
        Map<String, Integer> map = new HashMap<>();
        for (int i = 0; i < header.length; i++) {
            map.put(header[i].trim(), i);
        }
        return map;
    }

    private String getCell(String[] row, Map<String, Integer> headerIndex, String column) {
        Integer index = headerIndex.get(column);
        if (index == null || index >= row.length) {
            return null;
        }
        return row[index];
    }
}
