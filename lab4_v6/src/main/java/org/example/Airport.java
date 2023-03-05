package org.example;

import javax.persistence.*;

@Entity
@Table(name = "airport")
public class Airport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String city;
    private String country;
    private String iata;

    public Airport() {}

    public Airport(String name, String city, String country, String iata) {
        this.name = name;
        this.city = city;
        this.country = country;
        this.iata = iata;
    }

    // getters and setters

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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getIata() {
        return iata;
    }

    public void setIata(String iata) {
        this.iata = iata;
    }
}
