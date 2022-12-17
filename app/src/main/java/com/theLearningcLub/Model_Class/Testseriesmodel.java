package com.theLearningcLub.Model_Class;


public class Testseriesmodel {
    private String title, genre, year;
    public String Question_id;

    public Testseriesmodel() {
    }

    public Testseriesmodel(String title, String genre, String year,String Question_id) {
        this.title = title;
        this.genre = genre;
        this.year = year;
        this.Question_id = Question_id;
    }

    public String getQuestion_id() {
        return Question_id;
    }

    public void setQuestion_id(String question_id) {
        Question_id = question_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String name) {
        this.title = name;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }
}