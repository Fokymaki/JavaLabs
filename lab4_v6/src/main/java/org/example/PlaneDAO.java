package org.example;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.util.List;

public class PlaneDAO {

    private final EntityManagerFactory entityManagerFactory;

    public PlaneDAO(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    public void save(Plane plane) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.persist(plane);
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

    public List<Plane> findAll() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            TypedQuery<Plane> query = entityManager.createQuery("SELECT p FROM Plane p", Plane.class);
            return query.getResultList();
        } finally {
            entityManager.close();
        }
    }

    public void persist(Plane plane) {
        save(plane);
    }
}
