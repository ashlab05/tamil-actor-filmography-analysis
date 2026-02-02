# 📊 Data Exploration Report: BEFORE Processing

*Generated on: 2026-02-02*

---

## 📋 Executive Summary

This report documents the **raw state** of the filmography dataset before any cleaning or feature engineering.

- **Dataset**: Combined filmographies of Ajith Kumar and Vijay
- **Purpose**: Data exploration, quality assessment, and preprocessing preparation

## 👤 Actor Profiles

| Actor | Full Name | Birth Date | Birth Year | Current Age |
| :---: | :--- | :---: | :---: | :---: |
| **Ajith** | Ajith Kumar | 1971-05-01 | 1971 | 55 years |
| **Vijay** | Joseph Vijay | 1974-06-22 | 1974 | 52 years |

## 📈 Data Quality Metrics (Before Cleaning)

| Metric | Value |
| :--- | :---: |
| Shape (Rows x Columns) | **140 x 5** |
| Ajith Films | **63** |
| Vijay Films | **77** |
| Year Range | **1984 - 2026** |
| Null/Empty in Film | **0** |
| Null/Empty in Role | **0** |
| Null/Empty in Notes | **114** |
| Total Null Values | **114** |
| Footnote Annotations [a-z] | **26** |
| Child Actor Roles | **7** |
| Duplicate Rows | **0** |


## 🔍 Raw Data Sample (First 10 Records)

*Note: This shows the original data with footnote annotations, inconsistent formats, and null values.*

| # | Actor | Year | Film | Role | Notes |
| :---: | :--- | :---: | :--- | :--- | :--- |
| 1 | Ajith | 1990 | En Veedu En Kanavar | School boy | Child artist |
| 2 | Ajith | 1993 | Amaravathi | Arjun | Debut as lead actor |
| 3 | Ajith | 1993 | Prema Pusthakam | Sreekar | Telugu film |
| 4 | Ajith | 1994 | Paasamalargal | Kumar | - |
| 5 | Ajith | 1994 | Pavithra | Ashok | - |
| 6 | Ajith | 1995 | Rajavin Parvaiyile | Chandru | - |
| 7 | Ajith | 1995 | Aasai | Jeevanantham | - |
| 8 | Ajith | 1996 | Vaanmathi | Krishna | - |
| 9 | Ajith | 1996 | Kalloori Vaasal | Vasanth | - |
| 10 | Ajith | 1996 | Minor Mappillai | Ramu | - |


## 📉 Exploratory Visualizations (Before)

*These visualizations help identify patterns and issues in the raw data.*

### 📊 Movies Per Year (Annual Output Comparison)

![Movies Per Year (Annual Output Comparison)](charts/movies_per_year.png)

**🔎 Inference:** This chart shows the annual film output for both actors. Ajith peaked with 6 films in 1999. Vijay peaked with 5 films in 1996. Both actors show declining output in recent years, indicating a shift to quality over quantity.

---

### 📊 Career Timeline (Cumulative Growth)

![Career Timeline (Cumulative Growth)](charts/career_timeline.png)

**🔎 Inference:** The cumulative timeline shows career growth trajectories. Ajith has 63 films spanning 1990-2025. Vijay has 77 films spanning 1984-2026. Vijay's earlier start (1984) gives him a slight edge in total count, while Ajith started as a lead in 1993.

---

### 📊 Productivity by Age Bracket

![Productivity by Age Bracket](charts/age_vs_productivity.png)

**🔎 Inference:** Age bracket analysis shows productivity across different life stages. Both actors peaked in their late 20s to early 30s, aligning with typical star power and physical prime.

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


---

## 🔧 Issues Identified for Cleaning

1. **Footnote annotations** in Role column (e.g., `[a]`, `[b]`) need removal
2. **Null/Empty Notes** - many records have missing notes
3. **Unicode characters** - some film names have special characters (e.g., Aśoka)
4. **Inconsistent terminology** - "Child artist" vs "Child Actor"
5. **Reference citations** in Ajith's data (Ref column) to be dropped

