package org.example;

import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import org.example.PointResultDAO;
import org.example.PointResult;

import java.io.Serializable;
import java.util.List;

@Stateless
public class PointService implements Serializable {

    @EJB
    private PointResultDAO pointResultDAO;

    public void savePoint(PointResult point) {
        pointResultDAO.save(point);
    }

    public List<PointResult> getAllPoints() {
        return pointResultDAO.findAll();
    }

    public void clearHistory() {
        pointResultDAO.deleteAll();
    }
}