package com.zagori.cathouse.models;

public class Breed {
    private String id;
    private String name;
    private String temperament;
    private String origin;
    private String description;
    private String life_span;
    private String wikipedia_url;
    private Weight weight;

    public Breed() {
    }

    public Breed(String id, String name, String temperament, String origin, String description, String life_span, String wikipedia_url, Weight weight) {
        this.id = id;
        this.name = name;
        this.temperament = temperament;
        this.origin = origin;
        this.description = description;
        this.life_span = life_span;
        this.wikipedia_url = wikipedia_url;
        this.weight = weight;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTemperament() {
        return temperament;
    }

    public void setTemperament(String temperament) {
        this.temperament = temperament;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLife_span() {
        return life_span;
    }

    public void setLife_span(String life_span) {
        this.life_span = life_span;
    }

    public String getWikipedia_url() {
        return wikipedia_url;
    }

    public void setWikipedia_url(String wikipedia_url) {
        this.wikipedia_url = wikipedia_url;
    }

    public Weight getWeight() {
        return weight;
    }

    public void setWeight(Weight weight) {
        this.weight = weight;
    }
}
