package MBean;

public interface AreaCalculatorMBean {

    double getCurrentArea();

    double getCurrentRadius();
    
    void setCurrentRadius(double radius);
    
    double calculateArea(double radius);
}