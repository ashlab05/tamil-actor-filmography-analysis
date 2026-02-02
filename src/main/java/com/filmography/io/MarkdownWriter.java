package com.filmography.io;

import com.filmography.model.ActorMetadata;
import com.filmography.model.Film;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class MarkdownWriter {

    public void writeBefore(String path, Map<String, Object> stats, List<Film> sample, List<String> charts,
                            Map<String, String> inferences) throws IOException {
        try (FileWriter writer = new FileWriter(path)) {
            writer.write("# 📊 Data Exploration Report: BEFORE Processing\n\n");
            writer.write("*Generated on: " + LocalDate.now() + "*\n\n");
            writer.write("---\n\n");

            writer.write("## 📋 Executive Summary\n\n");
            writer.write("This report documents the **raw state** of the filmography dataset before any cleaning or feature engineering.\n\n");
            writer.write("- **Dataset**: Combined filmographies of Ajith Kumar and Vijay\n");
            writer.write("- **Purpose**: Data exploration, quality assessment, and preprocessing preparation\n\n");

            writeBio(writer);

            writer.write("## 📈 Data Quality Metrics (Before Cleaning)\n\n");
            writeStats(writer, stats);

            writer.write("\n## 🔍 Raw Data Sample (First 10 Records)\n\n");
            writer.write("*Note: This shows the original data with footnote annotations, inconsistent formats, and null values.*\n\n");
            writeSample(writer, sample);

            writer.write("\n## 📉 Exploratory Visualizations (Before)\n\n");
            writer.write("*These visualizations help identify patterns and issues in the raw data.*\n\n");
            writeChartLinks(writer, charts, inferences);

            writer.write("\n---\n\n");
            writer.write("## 🔧 Issues Identified for Cleaning\n\n");
            writer.write("1. **Footnote annotations** in Role column (e.g., `[a]`, `[b]`) need removal\n");
            writer.write("2. **Null/Empty Notes** - many records have missing notes\n");
            writer.write("3. **Unicode characters** - some film names have special characters (e.g., Aśoka)\n");
            writer.write("4. **Inconsistent terminology** - \"Child artist\" vs \"Child Actor\"\n");
            writer.write("5. **Reference citations** in Ajith's data (Ref column) to be dropped\n\n");
        }
    }

    public void writeAfter(String path, Map<String, Object> stats, List<Film> sample, List<String> charts,
                           Map<String, String> inferences) throws IOException {
        try (FileWriter writer = new FileWriter(path)) {
            writer.write("# 📊 Data Analysis Report: AFTER Processing\n\n");
            writer.write("*Generated on: " + LocalDate.now() + "*\n\n");
            writer.write("---\n\n");

            writer.write("## 📋 Executive Summary\n\n");
            writer.write("This report documents the **cleaned and feature-engineered** filmography dataset.\n\n");
            writer.write("- **Dataset**: Combined filmographies of Ajith Kumar and Vijay\n");
            writer.write("- **Processing**: Data cleaning, normalization, and 13 engineered features\n");
            writer.write("- **Output**: Production-ready dataset for analysis and modeling\n\n");

            writeBio(writer);

            writer.write("## 📈 Data Quality Metrics (After Cleaning)\n\n");
            writeStats(writer, stats);

            writer.write("\n## 🔍 Cleaned Data Sample (First 10 Records)\n\n");
            writer.write("*Data after cleaning: footnotes removed, terminology normalized, features engineered.*\n\n");
            writeSample(writer, sample);

            writer.write("\n## 📉 Analysis Visualizations (After)\n\n");
            writer.write("*These visualizations show patterns in the cleaned, feature-rich dataset.*\n\n");
            writeChartLinks(writer, charts, inferences);

            writer.write("\n---\n\n");
            writer.write("## ✅ Processing Summary\n\n");
            writer.write("### Cleaning Operations Performed:\n");
            writer.write("- ✓ Removed footnote annotations `[a-z]` from Role column\n");
            writer.write("- ✓ Normalized \"Child artist\" → \"Child Actor\"\n");
            writer.write("- ✓ Handled Unicode characters (Aśoka → Asoka)\n");
            writer.write("- ✓ Removed unreleased markers (†)\n");
            writer.write("- ✓ Dropped Ref column (citations)\n");
            writer.write("- ✓ Trimmed whitespace from all string fields\n\n");

            writer.write("### Features Engineered:\n");
            writer.write("| Feature | Description | Type |\n");
            writer.write("| --- | --- | --- |\n");
            writer.write("| Debut_Year | Year of first film | Integer |\n");
            writer.write("| Lead_Debut_Year | Year of first non-child role | Integer |\n");
            writer.write("| Career_Span | Years since debut (2026 - debut) | Integer |\n");
            writer.write("| Career_Phase | Early (≤5yr) / Growth (≤15yr) / Peak | String |\n");
            writer.write("| Cumulative_Movies | Running count of films per actor | Integer |\n");
            writer.write("| Movies_Per_Year | Films released that year per actor | Integer |\n");
            writer.write("| Release_Gap | Years since previous film | Integer |\n");
            writer.write("| High_Productivity | True if Movies_Per_Year > 1 | Boolean |\n");
            writer.write("| Is_Special | True if Notes contains keywords | Boolean |\n");
            writer.write("| Age_At_Film | Actor's age when film released | Integer |\n");
            writer.write("| Age_At_Debut | Actor's age at first film | Integer |\n");
            writer.write("| Current_Age | Actor's age as of 2026 | Integer |\n");
            writer.write("| Is_Child_Role | True if child actor role | Boolean |\n");
            writer.write("| Is_Upcoming | True if Year >= 2025 | Boolean |\n\n");

            writer.write("## 🎯 Key Insights\n\n");
            writer.write("1. **Vijay** started earlier (1984, age 10) as child actor; **Ajith** began as lead in 1993 (age 22)\n");
            writer.write("2. Both actors peaked in the late 1990s - early 2000s with 4-6 films per year\n");
            writer.write("3. Recent years show 1-2 films annually, indicating shift to bigger-budget productions\n");
            writer.write("4. Combined dataset: **137 films** spanning **42 years** (1984-2026)\n\n");
        }
    }

    private void writeBio(FileWriter writer) throws IOException {
        writer.write("## 👤 Actor Profiles\n\n");
        writer.write("| Actor | Full Name | Birth Date | Birth Year | Current Age |\n");
        writer.write("| :---: | :--- | :---: | :---: | :---: |\n");
        writer.write("| **Ajith** | Ajith Kumar | 1971-05-01 | 1971 | " + ActorMetadata.getCurrentAge("Ajith") + " years |\n");
        writer.write("| **Vijay** | Joseph Vijay | 1974-06-22 | 1974 | " + ActorMetadata.getCurrentAge("Vijay") + " years |\n\n");
    }

    private void writeStats(FileWriter writer, Map<String, Object> stats) throws IOException {
        writer.write("| Metric | Value |\n");
        writer.write("| :--- | :---: |\n");
        for (Map.Entry<String, Object> entry : stats.entrySet()) {
            writer.write("| " + entry.getKey() + " | **" + entry.getValue() + "** |\n");
        }
        writer.write("\n");
    }

    private void writeSample(FileWriter writer, List<Film> sample) throws IOException {
        writer.write("| # | Actor | Year | Film | Role | Notes |\n");
        writer.write("| :---: | :--- | :---: | :--- | :--- | :--- |\n");
        int i = 1;
        for (Film film : sample) {
            writer.write("| " + i++ + " | " + safe(film.getActor()) + " | " + film.getYear() + " | " + safe(film.getFilm()) + " | " + safe(film.getRole()) + " | " + safe(film.getNotes()) + " |\n");
        }
        writer.write("\n");
    }

    private void writeChartLinks(FileWriter writer, List<String> charts, Map<String, String> inferences) throws IOException {
        for (String chart : charts) {
            // Handle career_phase_distribution which now generates two files
            if (chart.equals("career_phase_distribution.png")) {
                // Write Ajith's chart
                writer.write("### 📊 Career Phase Distribution: Ajith Kumar\n\n");
                writer.write("![Career Phase - Ajith](charts/career_phase_distribution_ajith.png)\n\n");
                String ajithInference = inferences == null ? null : inferences.get("career_phase_ajith.png");
                if (ajithInference != null && !ajithInference.isEmpty()) {
                    writer.write("**🔎 Inference:** " + ajithInference + "\n\n");
                }
                writer.write("---\n\n");
                
                // Write Vijay's chart
                writer.write("### 📊 Career Phase Distribution: Vijay\n\n");
                writer.write("![Career Phase - Vijay](charts/career_phase_distribution_vijay.png)\n\n");
                String vijayInference = inferences == null ? null : inferences.get("career_phase_vijay.png");
                if (vijayInference != null && !vijayInference.isEmpty()) {
                    writer.write("**🔎 Inference:** " + vijayInference + "\n\n");
                }
                writer.write("---\n\n");
            } else {
                writer.write("### 📊 " + chartTitle(chart) + "\n\n");
                writer.write("![" + chartTitle(chart) + "](charts/" + chart + ")\n\n");
                String inference = inferences == null ? null : inferences.get(chart);
                if (inference != null && !inference.isEmpty()) {
                    writer.write("**🔎 Inference:** " + inference + "\n\n");
                }
                writer.write("---\n\n");
            }
        }
    }

    private String chartTitle(String chart) {
        switch (chart) {
            case "movies_per_year.png":
                return "Movies Per Year (Annual Output Comparison)";
            case "career_timeline.png":
                return "Career Timeline (Cumulative Growth)";
            case "career_phase_distribution.png":
                return "Career Phase Distribution";
            case "career_phase_distribution_ajith.png":
                return "Career Phase Distribution: Ajith Kumar";
            case "career_phase_distribution_vijay.png":
                return "Career Phase Distribution: Vijay";
            case "age_vs_productivity.png":
                return "Productivity by Age Bracket";
            case "age_career_comparison.png":
                return "Actor Comparison: Age & Career Metrics";
            case "child_vs_lead.png":
                return "Child Actor vs Lead Roles Distribution";
            case "five_year_productivity.png":
                return "5-Year Period Productivity";
            case "decade_wise.png":
                return "Decade-wise Film Output";
            case "release_gap.png":
                return "Career Release Gaps Analysis";
            case "career_phase_by_actor.png":
                return "Career Phase Comparison by Actor";
            case "productivity_trend.png":
                return "Productivity Trend Across Age (Line Chart)";
            default:
                return chart.replace(".png", "").replace("_", " ");
        }
    }

    private String safe(String value) {
        if (value == null || value.isEmpty()) {
            return "-";
        }
        return value.replace("|", "\\|");
    }
}
