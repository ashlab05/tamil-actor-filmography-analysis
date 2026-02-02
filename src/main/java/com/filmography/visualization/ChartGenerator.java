package com.filmography.visualization;

import com.filmography.model.ActorMetadata;
import com.filmography.model.Film;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.renderer.category.StackedBarRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.ui.RectangleInsets;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class ChartGenerator {

    private static final Color AJITH_COLOR = new Color(220, 53, 69);
    private static final Color VIJAY_COLOR = new Color(0, 123, 255);
    private static final Color BG_COLOR = new Color(250, 250, 252);
    private static final Color GRID_COLOR = new Color(230, 230, 230);
    private static final Font TITLE_FONT = new Font("SansSerif", Font.BOLD, 18);
    private static final Font LABEL_FONT = new Font("SansSerif", Font.BOLD, 13);
    private static final Font TICK_FONT = new Font("SansSerif", Font.PLAIN, 11);

    public void generateBefore(List<Film> films, String outputDir) throws IOException {
        new File(outputDir).mkdirs();
        moviesPerYearChart(films, outputDir + "/movies_per_year.png");
        careerTimelineChart(films, outputDir + "/career_timeline.png");
        ageVsProductivityBefore(films, outputDir + "/age_vs_productivity.png");
        childVsLeadChart(films, outputDir + "/child_vs_lead.png");
        fiveYearProductivity(films, outputDir + "/five_year_productivity.png");
        decadeWiseChart(films, outputDir + "/decade_wise.png");
        careerPhaseByActorChart(films, outputDir + "/career_phase_by_actor.png");
    }

    public void generateAfter(List<Film> films, String outputDir) throws IOException {
        new File(outputDir).mkdirs();
        moviesPerYearChart(films, outputDir + "/movies_per_year.png");
        careerTimelineChart(films, outputDir + "/career_timeline.png");
        careerPhaseChart(films, outputDir + "/career_phase_distribution.png");
        ageVsProductivity(films, outputDir + "/age_vs_productivity.png");
        ageCareerComparison(films, outputDir + "/age_career_comparison.png");
        childVsLeadChart(films, outputDir + "/child_vs_lead.png");
        fiveYearProductivity(films, outputDir + "/five_year_productivity.png");
        decadeWiseChart(films, outputDir + "/decade_wise.png");
        releaseGapChart(films, outputDir + "/release_gap.png");
        careerPhaseByActorChart(films, outputDir + "/career_phase_by_actor.png");
        productivityTrendChart(films, outputDir + "/productivity_trend.png");
    }

    private void moviesPerYearChart(List<Film> films, String path) throws IOException {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        Map<String, Map<Integer, Long>> counts = films.stream()
                .collect(Collectors.groupingBy(Film::getActor,
                        Collectors.groupingBy(Film::getYear, TreeMap::new, Collectors.counting())));

        Set<Integer> allYears = new TreeSet<>();
        counts.values().forEach(m -> allYears.addAll(m.keySet()));

        for (Integer year : allYears) {
            for (String actor : counts.keySet()) {
                long count = counts.get(actor).getOrDefault(year, 0L);
                dataset.addValue(count, actor, String.valueOf(year));
            }
        }

        JFreeChart chart = ChartFactory.createBarChart(
                "Movies Per Year (1984-2026)", "Year", "Number of Movies", dataset,
                PlotOrientation.VERTICAL, true, true, false);
        styleCategoryChart(chart, true);
        CategoryPlot plot = chart.getCategoryPlot();
        BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setSeriesPaint(0, AJITH_COLOR);
        renderer.setSeriesPaint(1, VIJAY_COLOR);
        ChartUtils.saveChartAsPNG(new File(path), chart, 1400, 500);
    }

    private void careerTimelineChart(List<Film> films, String path) throws IOException {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        Map<String, List<Film>> byActor = films.stream().collect(Collectors.groupingBy(Film::getActor));

        Set<Integer> allYears = new TreeSet<>();
        films.forEach(f -> allYears.add(f.getYear()));

        for (String actor : byActor.keySet()) {
            List<Film> actorFilms = byActor.get(actor).stream()
                    .sorted((a, b) -> Integer.compare(a.getYear(), b.getYear()))
                    .collect(Collectors.toList());
            int cumulative = 0;
            int filmIndex = 0;
            for (Integer year : allYears) {
                while (filmIndex < actorFilms.size() && actorFilms.get(filmIndex).getYear() <= year) {
                    cumulative++;
                    filmIndex++;
                }
                dataset.addValue(cumulative, actor, String.valueOf(year));
            }
        }

        JFreeChart chart = ChartFactory.createLineChart(
                "Career Timeline: Cumulative Movies Over Time", "Year", "Total Movies", dataset,
                PlotOrientation.VERTICAL, true, true, false);
        styleLineChart(chart);
        ChartUtils.saveChartAsPNG(new File(path), chart, 1400, 500);
    }

    private void careerPhaseChart(List<Film> films, String path) throws IOException {
        // Generate separate pie charts for each actor
        Map<String, List<Film>> byActor = films.stream()
                .filter(f -> f.getCareerPhase() != null)
                .collect(Collectors.groupingBy(Film::getActor));
        
        for (Map.Entry<String, List<Film>> entry : byActor.entrySet()) {
            String actor = entry.getKey();
            List<Film> actorFilms = entry.getValue();
            
            DefaultPieDataset<String> dataset = new DefaultPieDataset<>();
            Map<String, Long> counts = actorFilms.stream()
                    .collect(Collectors.groupingBy(Film::getCareerPhase, Collectors.counting()));
            
            dataset.setValue("Early (0-5 yrs)", counts.getOrDefault("Early", 0L));
            dataset.setValue("Growth (6-15 yrs)", counts.getOrDefault("Growth", 0L));
            dataset.setValue("Peak (16+ yrs)", counts.getOrDefault("Peak", 0L));
            
            String actorFileName = actor.toLowerCase().replace(" ", "_");
            String filePath = path.replace(".png", "_" + actorFileName + ".png");
            
            JFreeChart chart = ChartFactory.createPieChart(
                    "Career Phase Distribution: " + actor, dataset, true, true, false);
            stylePieChart(chart);
            ChartUtils.saveChartAsPNG(new File(filePath), chart, 600, 450);
        }
    }

    private void ageVsProductivity(List<Film> films, String path) throws IOException {
        // Create age bracket productivity chart instead of scatter
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        Map<String, List<Film>> byActor = films.stream().collect(Collectors.groupingBy(Film::getActor));

        String[] brackets = {"10-19", "20-24", "25-29", "30-34", "35-39", "40-44", "45-49", "50+"};

        for (String actor : byActor.keySet()) {
            int birthYear = ActorMetadata.getBirthYear(actor);
            Map<String, Long> bracketCounts = new TreeMap<>();
            for (String b : brackets) bracketCounts.put(b, 0L);

            for (Film film : byActor.get(actor)) {
                int age = film.getYear() - birthYear;
                String bracket = getAgeBracket(age);
                bracketCounts.merge(bracket, 1L, Long::sum);
            }

            for (String bracket : brackets) {
                dataset.addValue(bracketCounts.get(bracket), actor, bracket);
            }
        }

        JFreeChart chart = ChartFactory.createBarChart(
                "Films by Age Bracket: When Were They Most Productive?",
                "Age Range", "Number of Films", dataset,
                PlotOrientation.VERTICAL, true, true, false);
        styleCategoryChart(chart, false);
        CategoryPlot plot = chart.getCategoryPlot();
        BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setSeriesPaint(0, AJITH_COLOR);
        renderer.setSeriesPaint(1, VIJAY_COLOR);
        renderer.setDefaultItemLabelsVisible(true);
        renderer.setDefaultItemLabelGenerator(new StandardCategoryItemLabelGenerator());
        ChartUtils.saveChartAsPNG(new File(path), chart, 900, 500);
    }

    private String getAgeBracket(int age) {
        if (age < 20) return "10-19";
        if (age < 25) return "20-24";
        if (age < 30) return "25-29";
        if (age < 35) return "30-34";
        if (age < 40) return "35-39";
        if (age < 45) return "40-44";
        if (age < 50) return "45-49";
        return "50+";
    }

    private void ageVsProductivityBefore(List<Film> films, String path) throws IOException {
        // Create age bracket productivity chart
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        Map<String, List<Film>> byActor = films.stream().collect(Collectors.groupingBy(Film::getActor));

        String[] brackets = {"10-19", "20-24", "25-29", "30-34", "35-39", "40-44", "45-49", "50+"};

        for (String actor : byActor.keySet()) {
            int birthYear = ActorMetadata.getBirthYear(actor);
            Map<String, Long> bracketCounts = new TreeMap<>();
            for (String b : brackets) bracketCounts.put(b, 0L);

            for (Film film : byActor.get(actor)) {
                int age = film.getYear() - birthYear;
                String bracket = getAgeBracket(age);
                bracketCounts.merge(bracket, 1L, Long::sum);
            }

            for (String bracket : brackets) {
                dataset.addValue(bracketCounts.get(bracket), actor, bracket);
            }
        }

        JFreeChart chart = ChartFactory.createBarChart(
                "Films by Age Bracket: When Were They Most Productive?",
                "Age Range", "Number of Films", dataset,
                PlotOrientation.VERTICAL, true, true, false);
        styleCategoryChart(chart, false);
        CategoryPlot plot = chart.getCategoryPlot();
        BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setSeriesPaint(0, AJITH_COLOR);
        renderer.setSeriesPaint(1, VIJAY_COLOR);
        renderer.setDefaultItemLabelsVisible(true);
        renderer.setDefaultItemLabelGenerator(new StandardCategoryItemLabelGenerator());
        ChartUtils.saveChartAsPNG(new File(path), chart, 900, 500);
    }

    private void ageCareerComparison(List<Film> films, String path) throws IOException {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        Map<String, List<Film>> byActor = films.stream().collect(Collectors.groupingBy(Film::getActor));

        for (Map.Entry<String, List<Film>> entry : byActor.entrySet()) {
            String actor = entry.getKey();
            Film any = entry.getValue().get(0);
            if (any.getAgeAtDebut() != null) {
                dataset.addValue(any.getAgeAtDebut(), "Debut Age", actor);
            }
            if (any.getCurrentAge() != null) {
                dataset.addValue(any.getCurrentAge(), "Current Age (2026)", actor);
            }
            if (any.getCareerSpan() != null) {
                dataset.addValue(any.getCareerSpan(), "Career Span", actor);
            }
        }

        JFreeChart chart = ChartFactory.createBarChart(
                "Actor Comparison: Age & Career Metrics", "Actor", "Years", dataset,
                PlotOrientation.VERTICAL, true, true, false);
        styleCategoryChart(chart, false);
        CategoryPlot plot = chart.getCategoryPlot();
        BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setSeriesPaint(0, new Color(40, 167, 69));
        renderer.setSeriesPaint(1, new Color(255, 193, 7));
        renderer.setSeriesPaint(2, new Color(23, 162, 184));
        renderer.setDefaultItemLabelsVisible(true);
        renderer.setDefaultItemLabelGenerator(new StandardCategoryItemLabelGenerator());
        ChartUtils.saveChartAsPNG(new File(path), chart, 700, 500);
    }

    private void childVsLeadChart(List<Film> films, String path) throws IOException {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        Map<String, List<Film>> byActor = films.stream().collect(Collectors.groupingBy(Film::getActor));

        for (Map.Entry<String, List<Film>> entry : byActor.entrySet()) {
            long child = entry.getValue().stream()
                    .filter(f -> f.getNotes() != null && f.getNotes().toLowerCase().contains("child"))
                    .count();
            long lead = entry.getValue().size() - child;
            dataset.addValue(child, "Child Roles", entry.getKey());
            dataset.addValue(lead, "Lead/Adult Roles", entry.getKey());
        }

        JFreeChart chart = ChartFactory.createStackedBarChart(
                "Child Actor vs Lead Roles Distribution", "Actor", "Number of Films", dataset,
                PlotOrientation.VERTICAL, true, true, false);
        styleCategoryChart(chart, false);
        CategoryPlot plot = chart.getCategoryPlot();
        StackedBarRenderer renderer = (StackedBarRenderer) plot.getRenderer();
        renderer.setSeriesPaint(0, new Color(255, 99, 132));
        renderer.setSeriesPaint(1, new Color(54, 162, 235));
        renderer.setDefaultItemLabelsVisible(true);
        renderer.setDefaultItemLabelGenerator(new StandardCategoryItemLabelGenerator());
        ChartUtils.saveChartAsPNG(new File(path), chart, 600, 450);
    }

    private void fiveYearProductivity(List<Film> films, String path) throws IOException {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        Map<String, Map<String, Long>> byActorPeriod = films.stream()
                .collect(Collectors.groupingBy(Film::getActor,
                        Collectors.groupingBy(f -> getPeriod(f.getYear()), Collectors.counting())));

        Set<String> periods = new TreeSet<>();
        byActorPeriod.values().forEach(m -> periods.addAll(m.keySet()));

        for (String period : periods) {
            for (String actor : byActorPeriod.keySet()) {
                long count = byActorPeriod.get(actor).getOrDefault(period, 0L);
                dataset.addValue(count, actor, period);
            }
        }

        JFreeChart chart = ChartFactory.createBarChart(
                "5-Year Period Productivity Comparison", "Period", "Number of Films", dataset,
                PlotOrientation.VERTICAL, true, true, false);
        styleCategoryChart(chart, true);
        CategoryPlot plot = chart.getCategoryPlot();
        BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setSeriesPaint(0, AJITH_COLOR);
        renderer.setSeriesPaint(1, VIJAY_COLOR);
        ChartUtils.saveChartAsPNG(new File(path), chart, 900, 500);
    }

    private void decadeWiseChart(List<Film> films, String path) throws IOException {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        Map<String, Map<String, Long>> byActorDecade = films.stream()
                .collect(Collectors.groupingBy(Film::getActor,
                        Collectors.groupingBy(f -> getDecade(f.getYear()), Collectors.counting())));

        Set<String> decades = new TreeSet<>();
        byActorDecade.values().forEach(m -> decades.addAll(m.keySet()));

        for (String decade : decades) {
            for (String actor : byActorDecade.keySet()) {
                long count = byActorDecade.get(actor).getOrDefault(decade, 0L);
                dataset.addValue(count, actor, decade);
            }
        }

        JFreeChart chart = ChartFactory.createBarChart(
                "Decade-wise Film Output", "Decade", "Number of Films", dataset,
                PlotOrientation.VERTICAL, true, true, false);
        styleCategoryChart(chart, false);
        CategoryPlot plot = chart.getCategoryPlot();
        BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setSeriesPaint(0, AJITH_COLOR);
        renderer.setSeriesPaint(1, VIJAY_COLOR);
        renderer.setDefaultItemLabelsVisible(true);
        renderer.setDefaultItemLabelGenerator(new StandardCategoryItemLabelGenerator());
        ChartUtils.saveChartAsPNG(new File(path), chart, 800, 500);
    }

    private void releaseGapChart(List<Film> films, String path) throws IOException {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        Map<String, List<Film>> byActor = films.stream().collect(Collectors.groupingBy(Film::getActor));

        for (Map.Entry<String, List<Film>> entry : byActor.entrySet()) {
            String actor = entry.getKey();
            List<Film> sorted = entry.getValue().stream()
                    .sorted((a, b) -> Integer.compare(a.getYear(), b.getYear()))
                    .collect(Collectors.toList());

            int maxGap = 0;
            double avgGap = 0;
            int gapCount = 0;
            for (int i = 1; i < sorted.size(); i++) {
                int gap = sorted.get(i).getYear() - sorted.get(i - 1).getYear();
                if (gap > maxGap) maxGap = gap;
                if (gap > 0) {
                    avgGap += gap;
                    gapCount++;
                }
            }
            avgGap = gapCount > 0 ? avgGap / gapCount : 0;

            dataset.addValue(maxGap, "Max Gap (years)", actor);
            dataset.addValue(avgGap, "Avg Gap (years)", actor);
        }

        JFreeChart chart = ChartFactory.createBarChart(
                "Career Release Gaps Analysis", "Actor", "Years", dataset,
                PlotOrientation.VERTICAL, true, true, false);
        styleCategoryChart(chart, false);
        CategoryPlot plot = chart.getCategoryPlot();
        BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setSeriesPaint(0, new Color(255, 87, 34));
        renderer.setSeriesPaint(1, new Color(76, 175, 80));
        renderer.setDefaultItemLabelsVisible(true);
        renderer.setDefaultItemLabelGenerator(new StandardCategoryItemLabelGenerator("{2}", new DecimalFormat("0.0")));
        ChartUtils.saveChartAsPNG(new File(path), chart, 600, 450);
    }

    private String getPeriod(int year) {
        int start = (year / 5) * 5;
        return start + "-" + (start + 4);
    }

    private String getDecade(int year) {
        int start = (year / 10) * 10;
        return start + "s";
    }

    private void styleChartBase(JFreeChart chart) {
        chart.setBackgroundPaint(Color.WHITE);
        chart.setPadding(new RectangleInsets(15, 15, 15, 15));
        if (chart.getLegend() != null) {
            chart.getLegend().setItemFont(new Font("SansSerif", Font.PLAIN, 12));
            chart.getLegend().setBackgroundPaint(Color.WHITE);
        }
        chart.getTitle().setFont(TITLE_FONT);
        chart.getTitle().setPaint(new Color(50, 50, 50));
    }

    private void styleCategoryChart(JFreeChart chart, boolean rotateLabels) {
        styleChartBase(chart);
        CategoryPlot plot = chart.getCategoryPlot();
        plot.setBackgroundPaint(BG_COLOR);
        plot.setDomainGridlinePaint(GRID_COLOR);
        plot.setRangeGridlinePaint(GRID_COLOR);
        plot.setOutlineVisible(false);

        if (plot.getRenderer() instanceof BarRenderer) {
            BarRenderer renderer = (BarRenderer) plot.getRenderer();
            renderer.setDrawBarOutline(false);
            renderer.setItemMargin(0.02);
            renderer.setShadowVisible(false);
        }

        CategoryAxis domainAxis = plot.getDomainAxis();
        domainAxis.setTickLabelFont(TICK_FONT);
        domainAxis.setLabelFont(LABEL_FONT);
        if (rotateLabels) {
            domainAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45);
        }

        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setLabelFont(LABEL_FONT);
        rangeAxis.setTickLabelFont(TICK_FONT);
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
    }

    private void styleLineChart(JFreeChart chart) {
        styleChartBase(chart);
        CategoryPlot plot = chart.getCategoryPlot();
        plot.setBackgroundPaint(BG_COLOR);
        plot.setDomainGridlinePaint(GRID_COLOR);
        plot.setRangeGridlinePaint(GRID_COLOR);
        plot.setOutlineVisible(false);

        LineAndShapeRenderer renderer = new LineAndShapeRenderer(true, true);
        renderer.setSeriesPaint(0, AJITH_COLOR);
        renderer.setSeriesPaint(1, VIJAY_COLOR);
        renderer.setSeriesStroke(0, new BasicStroke(2.5f));
        renderer.setSeriesStroke(1, new BasicStroke(2.5f));
        plot.setRenderer(renderer);

        CategoryAxis domainAxis = plot.getDomainAxis();
        domainAxis.setTickLabelFont(TICK_FONT);
        domainAxis.setLabelFont(LABEL_FONT);
        domainAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45);

        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setLabelFont(LABEL_FONT);
        rangeAxis.setTickLabelFont(TICK_FONT);
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
    }

    private void stylePieChart(JFreeChart chart) {
        styleChartBase(chart);
        PiePlot<?> plot = (PiePlot<?>) chart.getPlot();
        plot.setBackgroundPaint(Color.WHITE);
        plot.setOutlineVisible(false);
        plot.setShadowPaint(null);
        plot.setLabelFont(new Font("SansSerif", Font.PLAIN, 12));
        plot.setLabelGenerator(new StandardPieSectionLabelGenerator("{0}: {1} ({2})"));
        plot.setSectionPaint("Early (0-5 yrs)", new Color(255, 193, 7));
        plot.setSectionPaint("Growth (6-15 yrs)", new Color(40, 167, 69));
        plot.setSectionPaint("Peak (16+ yrs)", new Color(0, 123, 255));
    }

    private void styleXYScatter(JFreeChart chart) {
        styleChartBase(chart);
        XYPlot plot = chart.getXYPlot();
        plot.setBackgroundPaint(BG_COLOR);
        plot.setDomainGridlinePaint(GRID_COLOR);
        plot.setRangeGridlinePaint(GRID_COLOR);
        plot.setOutlineVisible(false);

        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer(false, true);
        renderer.setSeriesPaint(0, AJITH_COLOR);
        renderer.setSeriesPaint(1, VIJAY_COLOR);
        renderer.setSeriesShape(0, new java.awt.geom.Ellipse2D.Double(-5, -5, 10, 10));
        renderer.setSeriesShape(1, new java.awt.geom.Ellipse2D.Double(-5, -5, 10, 10));
        plot.setRenderer(renderer);

        NumberAxis domainAxis = (NumberAxis) plot.getDomainAxis();
        domainAxis.setLabelFont(LABEL_FONT);
        domainAxis.setTickLabelFont(TICK_FONT);
        domainAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setLabelFont(LABEL_FONT);
        rangeAxis.setTickLabelFont(TICK_FONT);
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
    }

    private void careerPhaseByActorChart(List<Film> films, String path) throws IOException {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        
        Map<String, Map<String, Long>> byActorPhase = films.stream()
                .filter(f -> f.getCareerPhase() != null)
                .collect(Collectors.groupingBy(Film::getActor,
                        Collectors.groupingBy(Film::getCareerPhase, Collectors.counting())));
        
        String[] phases = {"Early", "Growth", "Peak"};
        Set<String> actors = byActorPhase.keySet();
        
        for (String phase : phases) {
            for (String actor : actors) {
                long count = byActorPhase.get(actor).getOrDefault(phase, 0L);
                dataset.addValue(count, actor, phase);
            }
        }

        JFreeChart chart = ChartFactory.createBarChart(
                "Career Phase Distribution by Actor", "Career Phase", "Number of Films", dataset,
                PlotOrientation.VERTICAL, true, true, false);
        styleCategoryChart(chart, false);
        CategoryPlot plot = chart.getCategoryPlot();
        BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setSeriesPaint(0, AJITH_COLOR);
        renderer.setSeriesPaint(1, VIJAY_COLOR);
        renderer.setDefaultItemLabelsVisible(true);
        renderer.setDefaultItemLabelGenerator(new StandardCategoryItemLabelGenerator());
        renderer.setDefaultItemLabelFont(new Font("SansSerif", Font.BOLD, 12));
        ChartUtils.saveChartAsPNG(new File(path), chart, 700, 500);
    }

    private void productivityTrendChart(List<Film> films, String path) throws IOException {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        
        // Group by actor and 5-year age bracket using birth year calculation
        Map<String, Map<String, Integer>> ageFilmCount = new HashMap<>();
        
        for (Film film : films) {
            String actor = film.getActor();
            int birthYear = ActorMetadata.getBirthYear(actor);
            if (birthYear == 0) continue;
            
            int age = film.getYear() - birthYear;
            if (age <= 0) continue;
            
            String ageGroup = getAgeBracket5Year(age);
            ageFilmCount.computeIfAbsent(actor, k -> new HashMap<>())
                    .merge(ageGroup, 1, Integer::sum);
        }
        
        String[] ageGroups = {"15-19", "20-24", "25-29", "30-34", "35-39", "40-44", "45-49", "50-54"};
        
        // Get actual actor names from the data
        Set<String> actors = ageFilmCount.keySet();
        
        for (String ageGroup : ageGroups) {
            for (String actor : actors) {
                int count = ageFilmCount.get(actor).getOrDefault(ageGroup, 0);
                double avgPerYear = count / 5.0; // 5-year bracket
                dataset.addValue(avgPerYear, actor, ageGroup);
            }
        }

        JFreeChart chart = ChartFactory.createLineChart(
                "Average Annual Productivity by Age", "Age Bracket", "Films per Year (Avg)", dataset,
                PlotOrientation.VERTICAL, true, true, false);
        styleLineChart(chart);
        
        CategoryPlot plot = chart.getCategoryPlot();
        LineAndShapeRenderer renderer = (LineAndShapeRenderer) plot.getRenderer();
        renderer.setSeriesPaint(0, AJITH_COLOR);
        renderer.setSeriesPaint(1, VIJAY_COLOR);
        renderer.setSeriesStroke(0, new BasicStroke(3.0f));
        renderer.setSeriesStroke(1, new BasicStroke(3.0f));
        renderer.setSeriesShape(0, new java.awt.geom.Ellipse2D.Double(-4, -4, 8, 8));
        renderer.setSeriesShape(1, new java.awt.geom.Ellipse2D.Double(-4, -4, 8, 8));
        renderer.setDefaultShapesVisible(true);
        
        ChartUtils.saveChartAsPNG(new File(path), chart, 900, 500);
    }
    
    private String getAgeBracket5Year(int age) {
        if (age < 15) return "10-14";
        if (age < 20) return "15-19";
        if (age < 25) return "20-24";
        if (age < 30) return "25-29";
        if (age < 35) return "30-34";
        if (age < 40) return "35-39";
        if (age < 45) return "40-44";
        if (age < 50) return "45-49";
        return "50-54";
    }
}
