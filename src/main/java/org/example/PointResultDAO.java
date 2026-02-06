package org.example;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import java.util.List;
import org.example.PointResult;

@Stateless
public class PointResultDAO {

    @PersistenceContext(unitName = "webappPU")
    private EntityManager entityManager;

    public void save(PointResult point) {
        entityManager.persist(point);
    }

    public List<PointResult> findAll() {
        TypedQuery<PointResult> query = entityManager.createNamedQuery(
                "PointResult.findAll",
                PointResult.class
        );
        return query.getResultList();
    }

    public void deleteAll() {
        entityManager.createQuery("DELETE FROM PointResult").executeUpdate();
    }
}