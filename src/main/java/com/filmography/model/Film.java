package com.filmography.model;

public class Film {
    private String actor;
    private int year;
    private String film;
    private String role;
    private String notes;

    private Integer debutYear;
    private Integer leadDebutYear;
    private Integer careerSpan;
    private String careerPhase;
    private Integer cumulativeMovies;
    private Integer moviesPerYear;
    private Integer releaseGap;
    private Boolean highProductivity;
    private Boolean isSpecial;
    private Integer ageAtFilm;
    private Integer ageAtDebut;
    private Integer currentAge;
    private Boolean isChildRole;
    private Boolean isUpcoming;

    public Film() {
    }

    public Film(String actor, int year, String film, String role, String notes) {
        this.actor = actor;
        this.year = year;
        this.film = film;
        this.role = role;
        this.notes = notes;
    }

    public String getActor() {
        return actor;
    }

    public void setActor(String actor) {
        this.actor = actor;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getFilm() {
        return film;
    }

    public void setFilm(String film) {
        this.film = film;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Integer getDebutYear() {
        return debutYear;
    }

    public void setDebutYear(Integer debutYear) {
        this.debutYear = debutYear;
    }

    public Integer getLeadDebutYear() {
        return leadDebutYear;
    }

    public void setLeadDebutYear(Integer leadDebutYear) {
        this.leadDebutYear = leadDebutYear;
    }

    public Integer getCareerSpan() {
        return careerSpan;
    }

    public void setCareerSpan(Integer careerSpan) {
        this.careerSpan = careerSpan;
    }

    public String getCareerPhase() {
        return careerPhase;
    }

    public void setCareerPhase(String careerPhase) {
        this.careerPhase = careerPhase;
    }

    public Integer getCumulativeMovies() {
        return cumulativeMovies;
    }

    public void setCumulativeMovies(Integer cumulativeMovies) {
        this.cumulativeMovies = cumulativeMovies;
    }

    public Integer getMoviesPerYear() {
        return moviesPerYear;
    }

    public void setMoviesPerYear(Integer moviesPerYear) {
        this.moviesPerYear = moviesPerYear;
    }

    public Integer getReleaseGap() {
        return releaseGap;
    }

    public void setReleaseGap(Integer releaseGap) {
        this.releaseGap = releaseGap;
    }

    public Boolean getHighProductivity() {
        return highProductivity;
    }

    public void setHighProductivity(Boolean highProductivity) {
        this.highProductivity = highProductivity;
    }

    public Boolean getIsSpecial() {
        return isSpecial;
    }

    public void setIsSpecial(Boolean isSpecial) {
        this.isSpecial = isSpecial;
    }

    public Integer getAgeAtFilm() {
        return ageAtFilm;
    }

    public void setAgeAtFilm(Integer ageAtFilm) {
        this.ageAtFilm = ageAtFilm;
    }

    public Integer getAgeAtDebut() {
        return ageAtDebut;
    }

    public void setAgeAtDebut(Integer ageAtDebut) {
        this.ageAtDebut = ageAtDebut;
    }

    public Integer getCurrentAge() {
        return currentAge;
    }

    public void setCurrentAge(Integer currentAge) {
        this.currentAge = currentAge;
    }

    public Boolean getIsChildRole() {
        return isChildRole;
    }

    public void setIsChildRole(Boolean isChildRole) {
        this.isChildRole = isChildRole;
    }

    public Boolean getIsUpcoming() {
        return isUpcoming;
    }

    public void setIsUpcoming(Boolean isUpcoming) {
        this.isUpcoming = isUpcoming;
    }
}
