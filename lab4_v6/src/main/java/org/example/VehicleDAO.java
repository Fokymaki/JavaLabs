package org.example;



import javax.persistence.*;
import java.util.List;

public class VehicleDAO {
    private EntityManager entityManager;

    public VehicleDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void create(Vehicle vehicle) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.persist(vehicle);
        transaction.commit();
    }

    public List<Vehicle> getAll() {
        TypedQuery<Vehicle> query = entityManager.createQuery("FROM Vehicle", Vehicle.class);
        return query.getResultList();
    }

    public void updateYearByBrand(String brand, int year) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        Query query = entityManager.createQuery("UPDATE Vehicle SET year = :year WHERE brand = :brand");
        query.setParameter("year", year);
        query.setParameter("brand", brand);
        query.executeUpdate();
        transaction.commit();
    }

    public void deleteByBrand(String brand) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        Query query = entityManager.createQuery("DELETE FROM Vehicle WHERE brand = :brand");
        query.setParameter("brand", brand);
        query.executeUpdate();
        transaction.commit();
    }

    public List<Vehicle> getByBrand(String brand) {
        TypedQuery<Vehicle> query = entityManager.createQuery(
                "SELECT v FROM Vehicle v, Plane p, Ship s, Airport a " +
                        "WHERE v.brand = :brand " +
                        "AND (p.brand = v.brand or p.brand=a.country) " +
                        "AND (p.airport = a OR s.class = a.class)",
                Vehicle.class);
        query.setParameter("brand", brand);
        return query.getResultList();
    }
}
