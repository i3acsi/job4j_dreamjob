package ru.job4j.dream.model;

public class Candidate extends Model {
    private int cityId;

    public Candidate(int id, String name, int cityId) {
        super(id, name);
        this.cityId = cityId;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }
}