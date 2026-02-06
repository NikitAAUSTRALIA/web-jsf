package org.example;

import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.bean.ManagedBean;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.primefaces.PrimeFaces;

import java.math.BigDecimal;
import java.util.Date;

@Named("requestBean")
@RequestScoped
public class RequestBean {

    @Inject
    private ResultsBean resultsBean;

    @Inject
    private PointService pointService;

    public void clearHistory(){
        resultsBean.setCurrentR(null);
        resultsBean.clearResults();
        pointService.clearHistory();
    }
    
    public void checkPoint() {
        long startTime = System.nanoTime();
        PointResult current = resultsBean.getCurrentPoint();
        if (current.getR() == null) {
            return;
        }
        boolean hit = checkArea(current.getX(), current.getY(), current.getR());
        current.setHit(hit);
        current.setTimestamp(new Date());
        current.setExecTime(System.nanoTime() - startTime);
        pointService.savePoint(current);
        resultsBean.addResult(current);
        PrimeFaces.current().ajax().addCallbackParam("success", true);
        PrimeFaces.current().ajax().addCallbackParam("x", current.getX());
        PrimeFaces.current().ajax().addCallbackParam("y", current.getY());
        PrimeFaces.current().ajax().addCallbackParam("r", current.getR());
        PrimeFaces.current().ajax().addCallbackParam("hit", hit);
        resultsBean.setCurrentX(null);
        resultsBean.setCurrentY(null);
    }

    private boolean checkArea(BigDecimal x, BigDecimal y, BigDecimal r) {
        if (x.compareTo(BigDecimal.ZERO) <= 0 && y.compareTo(BigDecimal.ZERO) >= 0) {
            return (x.pow(2).add(y.pow(2))).compareTo(r.divide(new BigDecimal(2)).pow(2)) <= 0;
        }
        else if (x.compareTo(BigDecimal.ZERO) >= 0 && y.compareTo(BigDecimal.ZERO) >= 0) {
            return y.compareTo(r.divide(new BigDecimal(2)).subtract(x.divide(new BigDecimal(2)))) <= 0;
        }
        else if (x.compareTo(BigDecimal.ZERO) <= 0 && y.compareTo(BigDecimal.ZERO) <= 0) {
            return x.negate().compareTo(r.divide(new BigDecimal(2))) <= 0 && y.negate().compareTo(r) <= 0;
        }
        return false;
    }
}