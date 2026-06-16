package MBean;

public class AreaCalculator implements AreaCalculatorMBean {
    private double currentRadius = 0.0;

    @Override
    public double getCurrentArea() {
        return calculateArea(currentRadius);
    }

    @Override
    public double getCurrentRadius() {
        return currentRadius;
    }
    
    @Override
    public void setCurrentRadius(double radius) {
        if (radius <= 0) {
            throw new IllegalArgumentException("Радиус должен быть > 0!");
        }
        this.currentRadius = radius;
        System.out.println("Радиус изменен: " + radius);
    }

    @Override
    public double calculateArea(double radius) {
        if (radius <= 0) {
            throw new IllegalArgumentException("Радиус должен быть > 0!");
        }
        return (radius * radius * 0.5) + (radius * radius * 0.25) + (Math.PI * radius * radius * 0.25 * 0.25);
    }
}