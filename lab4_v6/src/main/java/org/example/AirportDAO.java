package org.example;

import javax.persistence.*;
import java.util.List;

public class AirportDAO {

    private final EntityManager entityManager;

    public AirportDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void persist(Airport airport) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.persist(airport);
        transaction.commit();
    }

    public Airport getByIata(String iata) {
        TypedQuery<Airport> query = entityManager.createQuery(
                "SELECT a FROM Airport a WHERE a.iata = :iata", Airport.class);
        query.setParameter("iata", iata);
        List<Airport> airports = query.getResultList();
        if (airports.isEmpty()) {
            return null;
        }
        return airports.get(0);
    }

    public List<Airport> getAll() {
        TypedQuery<Airport> query = entityManager.createQuery(
                "SELECT a FROM Airport a",
                Airport.class);
        return query.getResultList();
    }

    public void update(Airport airport) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.merge(airport);
        transaction.commit();
    }

    public void delete(Airport airport) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.remove(airport);
        transaction.commit();
    }
}
