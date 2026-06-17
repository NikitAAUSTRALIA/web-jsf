package MBean;

import org.example.ResultsBean;

import javax.management.*;
import java.lang.management.ManagementFactory;

public class MBeanRegister {

    private static PointStatistics pointStats;
    private static AreaCalculator areaCalc;

    public static void registerAll(ResultsBean resultsBean) throws Exception {
        MBeanServer server = ManagementFactory.getPlatformMBeanServer();
        pointStats = new PointStatistics(resultsBean);
        if (server.isRegistered(new ObjectName("MBean:type=PointStatistics"))) {
            server.unregisterMBean(new ObjectName("MBean:type=PointStatistics"));
        }
        server.registerMBean(pointStats, new ObjectName("MBean:type=PointStatistics"));
        areaCalc = new AreaCalculator();
        if (server.isRegistered(new ObjectName("MBean:type=AreaCalculator"))) {
            server.unregisterMBean(new ObjectName("MBean:type=AreaCalculator"));
        }
        server.registerMBean(areaCalc, new ObjectName("MBean:type=AreaCalculator"));
        System.out.println("MBean зарегистрированы");
    }

    public static PointStatistics getPointStatistics() {
        return pointStats;
    }
    
    public static AreaCalculator getAreaCalculator() {
        return areaCalc;
    }
}