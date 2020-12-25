package ru.job4j.dream.dto;

import ru.job4j.dream.model.Candidate;
import ru.job4j.dream.model.City;

public class CandidateDTO {
    private int id;
    private String name;
    private String cityName;

    public CandidateDTO(int id, String name, String cityName) {
        this.id = id;
        this.name = name;
        this.cityName = cityName;
    }

    public CandidateDTO() {
    }

    public CandidateDTO(Candidate candidate, City city) {
        this.id = candidate.getId();
        this.name = candidate.getName();
        this.cityName = city.getName();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
}
