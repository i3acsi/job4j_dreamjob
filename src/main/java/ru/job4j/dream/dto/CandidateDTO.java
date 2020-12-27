package ru.job4j.dream.dto;

import ru.job4j.dream.model.Candidate;
import ru.job4j.dream.model.City;

public class CandidateDTO {
    private int id;
    private String name;
    private int cityId;
    private String cityName;

    public CandidateDTO(int id, String name, int cityId, String cityName) {
        this.id = id;
        this.name = name;
        this.cityId = cityId;
        this.cityName = cityName;
    }

    public CandidateDTO() {
    }

    public CandidateDTO(Candidate candidate, City city) {
        this.id = candidate.getId();
        this.name = candidate.getName();
        this.cityId = candidate.getCityId();
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

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
}
