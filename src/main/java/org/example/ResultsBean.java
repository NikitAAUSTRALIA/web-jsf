package org.example;

import MBean.AreaCalculator;
import MBean.MBeanRegister;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.bean.ManagedBean;
import jakarta.annotation.PostConstruct;
import jakarta.inject.Named;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.ArrayList;

@Named("resultsBean")
@SessionScoped
public class ResultsBean implements Serializable {
    private List<PointResult> results;
    private BigDecimal currentX;
    private BigDecimal currentY;
    private BigDecimal currentR;
    private AreaCalculator areaCalculator;
    
    @PostConstruct
    public void init() {
        results = new ArrayList<>();
        currentX = null;
        currentY = null;
        currentR = null;
        try {
            areaCalculator = MBeanRegister.getAreaCalculator();
            if (areaCalculator != null) {
                System.out.println("MBean доступен в ResultsBean");
            } else {
                System.out.println("MBean еще не зарегистрирован");
            }
        } catch (Exception e) {
            System.err.println("Ошибка получения MBean: " + e.getMessage());
        }
    }
    
    public BigDecimal getCurrentX() {
        return currentX;
    }
    
    public void setCurrentX(BigDecimal currentX) {
        this.currentX = currentX;
    }
    
    public BigDecimal getCurrentY() {
        return currentY;
    }
    
    public void setCurrentY(BigDecimal currentY) {
        this.currentY = currentY;
    }
    
    public BigDecimal getCurrentR() {
        return currentR;
    }
    
    public void setCurrentR(String currentR) {
        if (currentR == null) {
            this.currentR = null;
            areaCalculator.setCurrentRadius(0.0);
            return;
        }
        this.currentR = new BigDecimal(currentR);
        areaCalculator.setCurrentRadius(this.currentR.doubleValue());
        System.out.println("Current R: " + this.currentR);
    }
    
    public List<PointResult> getResults() {
        return results;
    }

    public PointResult getCurrentPoint() {
        return new PointResult(currentX, currentY, currentR, null, null, null);
    }

    public void addResult(PointResult result) {
        results.add(result);
    }
    
    public void clearResults() {
        results.clear();
    }
}