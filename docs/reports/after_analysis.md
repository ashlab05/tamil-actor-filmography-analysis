# 📊 Data Analysis Report: AFTER Processing

*Generated on: 2026-02-02*

---

## 📋 Executive Summary

This report documents the **cleaned and feature-engineered** filmography dataset.

- **Dataset**: Combined filmographies of Ajith Kumar and Vijay
- **Processing**: Data cleaning, normalization, and 13 engineered features
- **Output**: Production-ready dataset for analysis and modeling

## 👤 Actor Profiles

| Actor | Full Name | Birth Date | Birth Year | Current Age |
| :---: | :--- | :---: | :---: | :---: |
| **Ajith** | Ajith Kumar | 1971-05-01 | 1971 | 55 years |
| **Vijay** | Joseph Vijay | 1974-06-22 | 1974 | 52 years |

## 📈 Data Quality Metrics (After Cleaning)

| Metric | Value |
| :--- | :---: |
| Shape (Rows x Columns) | **140 x 18** |
| Ajith Films | **63** |
| Vijay Films | **77** |
| Year Range | **1984 - 2026** |
| Null Debut_Year | **0** |
| Null Career_Phase | **0** |
| Null Age_At_Film | **0** |
| Child Actor Roles | **7** |
| Special Films (Cameo/Debut/etc) | **16** |
| Upcoming Films (2025+) | **3** |
| High Productivity Film-Years | **112** |
| Phase: Growth | **58** |
| Phase: Early | **13** |
| Phase: Peak | **69** |
| Ajith Debut Year | **1990** |
| Ajith Debut Age | **19** |
| Ajith Current Age | **55** |
| Ajith Career Span (years) | **36** |
| Ajith Peak Year | **1999 (6 films)** |
| Vijay Debut Year | **1984** |
| Vijay Debut Age | **10** |
| Vijay Current Age | **52** |
| Vijay Career Span (years) | **42** |
| Vijay Peak Year | **1996 (5 films)** |


## 🔍 Cleaned Data Sample (First 10 Records)

*Data after cleaning: footnotes removed, terminology normalized, features engineered.*

| # | Actor | Year | Film | Role | Notes |
| :---: | :--- | :---: | :--- | :--- | :--- |
| 1 | Ajith | 1990 | En Veedu En Kanavar | School boy | Child Actor |
| 2 | Ajith | 1993 | Amaravathi | Arjun | Debut as lead actor |
| 3 | Ajith | 1993 | Prema Pusthakam | Sreekar | Telugu film |
| 4 | Ajith | 1994 | Paasamalargal | Kumar | - |
| 5 | Ajith | 1994 | Pavithra | Ashok | - |
| 6 | Ajith | 1995 | Rajavin Parvaiyile | Chandru | - |
| 7 | Ajith | 1995 | Aasai | Jeevanantham | - |
| 8 | Ajith | 1996 | Vaanmathi | Krishna | - |
| 9 | Ajith | 1996 | Kalloori Vaasal | Vasanth | - |
| 10 | Ajith | 1996 | Minor Mappillai | Ramu | - |


## 📉 Analysis Visualizations (After)

*These visualizations show patterns in the cleaned, feature-rich dataset.*

### 📊 Movies Per Year (Annual Output Comparison)

![Movies Per Year (Annual Output Comparison)](charts/movies_per_year.png)

**🔎 Inference:** This chart shows the annual film output for both actors. Ajith peaked with 6 films in 1999. Vijay peaked with 5 films in 1996. Both actors show declining output in recent years, indicating a shift to quality over quantity.

---

### 📊 Career Timeline (Cumulative Growth)

![Career Timeline (Cumulative Growth)](charts/career_timeline.png)

**🔎 Inference:** The cumulative timeline shows career growth trajectories. Ajith has 63 films spanning 1990-2025. Vijay has 77 films spanning 1984-2026. Vijay's earlier start (1984) gives him a slight edge in total count, while Ajith started as a lead in 1993.

---

### 📊 Career Phase Distribution: Ajith Kumar

![Career Phase - Ajith](charts/career_phase_distribution_ajith.png)

**🔎 Inference:** Ajith Kumar's career breakdown - Early: 7 films, Growth: 34 films, Peak: 22 films (35% in peak). Ajith debuted directly as lead in 1993 (no child roles), showing consistent output across all phases with majority in Peak phase.

---

### 📊 Career Phase Distribution: Vijay

![Career Phase - Vijay](charts/career_phase_distribution_vijay.png)

**🔎 Inference:** Vijay's career breakdown - Early: 6 films, Growth: 24 films, Peak: 47 films (61% in peak). Vijay started as child artist (1984), transitioned to lead roles in 1992, with highest output during Growth phase before settling into selective Peak productions.

---

### 📊 Productivity by Age Bracket

![Productivity by Age Bracket](charts/age_vs_productivity.png)

**🔎 Inference:** Age bracket analysis shows productivity across different life stages. Ajith was most productive in the 25-29 age range with 22 films. Vijay was most productive in the 20-24 age range with 18 films. Both actors peaked in their late 20s to early 30s, aligning with typical star power and physical prime.

---

### 📊 Actor Comparison: Age & Career Metrics

![Actor Comparison: Age & Career Metrics](charts/age_career_comparison.png)

**🔎 Inference:** Career comparison metrics: Ajith (DOB: 1971) debuted at age 19, currently 55 years old with 36-year career. Vijay (DOB: 1974) debuted at age 10, currently 52 years old with 42-year career. Vijay debuted much younger (age 10 as child actor) compared to Ajith (age 19).

---

### 📊 Child Actor vs Lead Roles Distribution

![Child Actor vs Lead Roles Distribution](charts/child_vs_lead.png)

**🔎 Inference:** Child actor vs lead role distribution: Ajith has 1 child role(s) and 62 lead/adult roles. Vijay has 6 child role(s) and 71 lead/adult roles. Vijay had a longer child actor stint (6 films, 1984-1988) before transitioning to lead roles in 1992.

---

### 📊 5-Year Period Productivity

![5-Year Period Productivity](charts/five_year_productivity.png)

**🔎 Inference:** 5-year productivity analysis: Ajith was most productive during 1995-1999 (21 films). Vijay was most productive during 1995-1999 (21 films). Both actors show highest output in the 1995-1999 and 2000-2004 periods, coinciding with Tamil cinema's commercial boom.

---

### 📊 Decade-wise Film Output

![Decade-wise Film Output](charts/decade_wise.png)

**🔎 Inference:** Decade-wise output comparison: Ajith - 1990s: 26, 2000s: 22, 2010s: 11, 2020s: 4, Vijay - 1980s: 6, 1990s: 24, 2000s: 26, 2010s: 15, 2020s: 6, The 1990s and 2000s were the golden era for both actors with significantly higher output than recent decades.

---

### 📊 Career Release Gaps Analysis

![Career Release Gaps Analysis](charts/release_gap.png)

**🔎 Inference:** Release gap analysis: Ajith's longest gap was 3 year(s) after 1990. Vijay's longest gap was 4 year(s) after 1988. Vijay maintains more consistent releases while Ajith has occasional longer breaks between films.

---


---

## ✅ Processing Summary

### Cleaning Operations Performed:
- ✓ Removed footnote annotations `[a-z]` from Role column
- ✓ Normalized "Child artist" → "Child Actor"
- ✓ Handled Unicode characters (Aśoka → Asoka)
- ✓ Removed unreleased markers (†)
- ✓ Dropped Ref column (citations)
- ✓ Trimmed whitespace from all string fields

### Features Engineered:
| Feature | Description | Type |
| --- | --- | --- |
| Debut_Year | Year of first film | Integer |
| Lead_Debut_Year | Year of first non-child role | Integer |
| Career_Span | Years since debut (2026 - debut) | Integer |
| Career_Phase | Early (≤5yr) / Growth (≤15yr) / Peak | String |
| Cumulative_Movies | Running count of films per actor | Integer |
| Movies_Per_Year | Films released that year per actor | Integer |
| Release_Gap | Years since previous film | Integer |
| High_Productivity | True if Movies_Per_Year > 1 | Boolean |
| Is_Special | True if Notes contains keywords | Boolean |
| Age_At_Film | Actor's age when film released | Integer |
| Age_At_Debut | Actor's age at first film | Integer |
| Current_Age | Actor's age as of 2026 | Integer |
| Is_Child_Role | True if child actor role | Boolean |
| Is_Upcoming | True if Year >= 2025 | Boolean |

## 🎯 Key Insights

1. **Vijay** started earlier (1984, age 10) as child actor; **Ajith** began as lead in 1993 (age 22)
2. Both actors peaked in the late 1990s - early 2000s with 4-6 films per year
3. Recent years show 1-2 films annually, indicating shift to bigger-budget productions
4. Combined dataset: **137 films** spanning **42 years** (1984-2026)

