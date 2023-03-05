package org.example;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.util.List;

public class ShipDAO {

    private final EntityManagerFactory entityManagerFactory;

    public ShipDAO(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    public void save(Ship ship) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.persist(ship);
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        } finally {
            entityManager.close();
        }
    }

    public List<Ship> findAll() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            TypedQuery<Ship> query = entityManager.createQuery("SELECT s FROM Ship s", Ship.class);
            return query.getResultList();
        } finally {
            entityManager.close();
        }
    }

    public void persist(Ship ship) {
        save(ship);
    }
}