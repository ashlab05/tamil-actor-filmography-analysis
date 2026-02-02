package com.filmography;

import com.filmography.analysis.DataExplorer;
import com.filmography.analysis.InsightGenerator;
import com.filmography.io.CsvReader;
import com.filmography.io.CsvWriter;
import com.filmography.io.MarkdownWriter;
import com.filmography.model.Film;
import com.filmography.processing.DataCleaner;
import com.filmography.processing.FeatureEngineer;
import com.filmography.visualization.ChartGenerator;

import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws Exception {
        // Data directories
        String rawDataDir = new File("data/raw").getAbsolutePath();
        String processedDataDir = new File("data/processed").getAbsolutePath();
        
        // Documentation directories
        String reportsDir = new File("docs/reports").getAbsolutePath();
        String chartsBeforeDir = new File("docs/charts/before").getAbsolutePath();
        String chartsAfterDir = new File("docs/charts/after").getAbsolutePath();
        
        // Create directories if they don't exist
        new File(rawDataDir).mkdirs();
        new File(processedDataDir).mkdirs();
        new File(reportsDir).mkdirs();
        new File(chartsBeforeDir).mkdirs();
        new File(chartsAfterDir).mkdirs();
        
        // Input files
        String ajithPath = rawDataDir + "/ajith.csv";
        String vijayPath = rawDataDir + "/vijay.csv";

        CsvReader reader = new CsvReader();
        List<Film> films = new ArrayList<>();
        films.addAll(reader.read(ajithPath, "Ajith"));
        films.addAll(reader.read(vijayPath, "Vijay"));
        films.sort(Comparator.comparing(Film::getActor).thenComparing(Film::getYear));

        List<Film> rawFilms = cloneFilms(films);

        DataExplorer explorer = new DataExplorer();
        Map<String, Object> beforeStats = explorer.summarizeBefore(rawFilms);

        ChartGenerator chartGenerator = new ChartGenerator();
        chartGenerator.generateBefore(rawFilms, chartsBeforeDir);

        InsightGenerator insightGenerator = new InsightGenerator();
        Map<String, String> beforeInsights = insightGenerator.generate(rawFilms);

        MarkdownWriter markdownWriter = new MarkdownWriter();
        markdownWriter.writeBefore(
            reportsDir + "/before_analysis.md",
            beforeStats,
            rawFilms.subList(0, Math.min(10, rawFilms.size())),
            List.of(
                "movies_per_year.png",
                "career_timeline.png",
                "age_vs_productivity.png",
                "child_vs_lead.png",
                "five_year_productivity.png",
                "decade_wise.png"
            ),
            beforeInsights
        );

        DataCleaner cleaner = new DataCleaner();
        cleaner.clean(films);

        FeatureEngineer engineer = new FeatureEngineer();
        engineer.engineer(films);

        Map<String, Object> afterStats = explorer.summarizeAfter(films);
        chartGenerator.generateAfter(films, chartsAfterDir);
        Map<String, String> afterInsights = insightGenerator.generate(films);

        markdownWriter.writeAfter(
            reportsDir + "/after_analysis.md",
            afterStats,
            films.subList(0, Math.min(10, films.size())),
            List.of(
                "movies_per_year.png",
                "career_timeline.png",
                "career_phase_distribution.png",
                "age_vs_productivity.png",
                "age_career_comparison.png",
                "child_vs_lead.png",
                "five_year_productivity.png",
                "decade_wise.png",
                "release_gap.png"
            ),
            afterInsights
        );

        CsvWriter writer = new CsvWriter();
        writer.writeCleaned(processedDataDir + "/cleaned_filmography.csv", films);

        System.out.println("✓ Analysis complete!");
        System.out.println("  Data:    data/raw/ (input), data/processed/ (output)");
        System.out.println("  Reports: docs/reports/");
        System.out.println("  Charts:  docs/charts/before/, docs/charts/after/");
    }

    private static List<Film> cloneFilms(List<Film> films) {
        List<Film> copy = new ArrayList<>();
        for (Film film : films) {
            copy.add(new Film(film.getActor(), film.getYear(), film.getFilm(), film.getRole(), film.getNotes()));
        }
        return copy;
    }
}
