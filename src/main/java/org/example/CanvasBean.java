package org.example;

import MBean.MBeanRegister;
import MBean.PointStatistics;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.primefaces.PrimeFaces;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

@Named
@SessionScoped
public class CanvasBean implements Serializable {

    @Inject
    private ResultsBean resultsBean;
    @Inject 
    private PointService pointService;
    
    private BigDecimal x;
    private BigDecimal y;
    private BigDecimal r;
    private boolean hit;
    private PointStatistics pointStats;

    @PostConstruct
    public void init() {
        try {
            pointStats = MBeanRegister.getPointStatistics();
            if (pointStats != null) {
                System.out.println("MBean доступен в CanvasBean");
            } else {
                System.out.println("MBean еще не зарегистрирован");
            }
        } catch (Exception e) {
            System.err.println("Ошибка получения MBean: " + e.getMessage());
        }
    }
    
    public void processCanvasClick() {
        long startTime = System.nanoTime();
        FacesContext context = FacesContext.getCurrentInstance();
        Map<String, String> params = context.getExternalContext().getRequestParameterMap();
        try {
            x = new BigDecimal(params.get("canvasX"));
            y = new BigDecimal(params.get("canvasY"));
            r = new BigDecimal(params.get("canvasR"));
            hit = checkArea(x, y, r);
            PointResult point = new PointResult(x, y, r, hit, new Date(), System.nanoTime() - startTime);
            pointService.savePoint(point);
            resultsBean.addResult(point);

            PrimeFaces.current().ajax().addCallbackParam("success", true);
            PrimeFaces.current().ajax().addCallbackParam("x", x);
            PrimeFaces.current().ajax().addCallbackParam("y", y);
            PrimeFaces.current().ajax().addCallbackParam("r", r);
            PrimeFaces.current().ajax().addCallbackParam("hit", hit);
            pointStats.onPointUpdate();
        } catch (Exception e) {
            PrimeFaces.current().ajax().addCallbackParam("success", false);
            PrimeFaces.current().ajax().addCallbackParam("error", e.getMessage());
        }
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