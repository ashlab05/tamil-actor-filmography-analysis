# 🎬 Tamil Actor Filmography Analysis

A comprehensive Java-based data exploration, preprocessing, and feature engineering project analyzing the filmographies of **Ajith Kumar** and **Vijay** - two legendary Tamil cinema actors.

## 📁 Project Structure

```
filmography-analysis/
├── pom.xml                          # Maven build configuration
├── README.md                        # This file
│
├── data/
│   ├── raw/                         # Original CSV files
│   │   ├── ajith.csv               # Ajith Kumar's filmography (63 films)
│   │   └── vijay.csv               # Vijay's filmography (77 films)
│   └── processed/                   # Cleaned & engineered data
│       └── cleaned_filmography.csv  # Final dataset (140 films × 18 features)
│
├── docs/
│   ├── reports/                     # Analysis reports
│   │   ├── ASSIGNMENT_REPORT.md    # Complete assignment report with code explanations
│   │   ├── before_analysis.md      # Pre-processing analysis
│   │   └── after_analysis.md       # Post-processing analysis
│   └── charts/                      # Visualizations
│       ├── before/                  # Charts from raw data (6 charts)
│       └── after/                   # Charts from processed data (12 charts)
│
├── src/main/java/com/filmography/
│   ├── Main.java                    # Entry point
│   ├── model/
│   │   ├── Film.java               # Data model (18 fields)
│   │   └── ActorMetadata.java      # Actor birth dates & utilities
│   ├── io/
│   │   ├── CsvReader.java          # OpenCSV-based CSV parsing
│   │   ├── CsvWriter.java          # Export processed data
│   │   └── MarkdownWriter.java     # Report generation
│   ├── processing/
│   │   ├── DataCleaner.java        # Text normalization & cleaning
│   │   └── FeatureEngineer.java    # 13 derived features
│   ├── analysis/
│   │   ├── DataExplorer.java       # Statistics & summaries
│   │   └── InsightGenerator.java   # Chart-specific insights
│   └── visualization/
│       └── ChartGenerator.java     # JFreeChart visualizations (12 types)
│
└── target/                          # Maven build output (generated)
```

## 🚀 Quick Start

### Prerequisites
- Java 17+
- Maven 3.8+

### Build & Run

```bash
# Navigate to project directory
cd filmography-analysis

# Compile and run
mvn clean compile exec:java -Dexec.mainClass="com.filmography.Main"

# Output locations:
#   data/processed/cleaned_filmography.csv
#   docs/reports/*.md
#   docs/charts/before/*.png
#   docs/charts/after/*.png
```

## 📊 Features Engineered (13 Total)

| Feature | Type | Description |
|:---|:---:|:---|
| Debut_Year | Integer | Year of actor's first film |
| Career_Span | Integer | Years since debut |
| Career_Phase | String | "Early", "Growth", or "Peak" |
| Cumulative_Movies | Integer | Running film count |
| Movies_Per_Year | Integer | Films released that year |
| Release_Gap | Integer | Years since previous film |
| High_Productivity | Boolean | True if >1 film that year |
| Is_Special | Boolean | Milestone/cameo/debut film |
| Age_At_Film | Integer | Actor's age when film released |
| Age_At_Debut | Integer | Age at first film |
| Current_Age | Integer | Actor's age in 2026 |
| Is_Child_Role | Boolean | True if child actor role |
| Is_Upcoming | Boolean | True if Year ≥ 2025 |

## 📈 Visualizations

### Before Processing (6 Charts)
- Movies Per Year
- Career Timeline (Cumulative)
- Age vs Productivity
- Child vs Lead Roles
- 5-Year Period Productivity
- Decade-wise Output

### After Processing (12 Charts)
All above plus:
- Career Phase Distribution (Ajith)
- Career Phase Distribution (Vijay)
- Career Phase by Actor (Grouped)
- Release Gap Analysis
- Productivity Trend
- Age & Career Comparison

## 🛠️ Dependencies

```xml
<dependencies>
    <dependency>
        <groupId>com.opencsv</groupId>
        <artifactId>opencsv</artifactId>
        <version>5.9</version>
    </dependency>
    <dependency>
        <groupId>org.jfree</groupId>
        <artifactId>jfreechart</artifactId>
        <version>1.5.4</version>
    </dependency>
    <dependency>
        <groupId>org.apache.commons</groupId>
        <artifactId>commons-lang3</artifactId>
        <version>3.14.0</version>
    </dependency>
</dependencies>
```

## 📋 Key Findings

| Metric | Ajith Kumar | Vijay |
|:---|:---:|:---:|
| Total Films | 63 | 77 |
| Career Start | 1993 | 1984 |
| Debut Age | 22 | 10 (child actor) |
| Current Age | 55 | 52 |
| Career Span | 33 years | 42 years |
| Peak Years | Late 1990s | Late 1990s |

## 📄 Reports

- **[ASSIGNMENT_REPORT.md](docs/reports/ASSIGNMENT_REPORT.md)** - Complete report with code explanations
- **[before_analysis.md](docs/reports/before_analysis.md)** - Raw data exploration
- **[after_analysis.md](docs/reports/after_analysis.md)** - Processed data analysis

---

*Generated for BDBA Data Analytics Assignment - February 2026*
