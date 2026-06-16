package MBean;

import jakarta.inject.Inject;
import org.example.PointResult;
import org.example.ResultsBean;
import javax.management.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

public class PointStatistics implements PointStatisticsMBean, NotificationBroadcaster {
    
    private ResultsBean resultsBean;
    private int totalPoints = 0;
    private int pointsInArea = 0;
    private int pointsOutside = 0;
    private final NotificationBroadcasterSupport broadcaster;
    private long notificationSequence = 0;

    public PointStatistics(ResultsBean resultsBean) {
        this.broadcaster = new NotificationBroadcasterSupport();
        this.resultsBean = resultsBean;
    }

    @Override
    public int getTotalPoints() {
        return totalPoints;
    }

    @Override
    public int getPointsInArea() {
        return pointsInArea;
    }

    @Override
    public int getPointsOutside() {
        return pointsOutside;
    }
    
    public void onPointUpdate(){
        List<PointResult> resList = resultsBean.getResults();
        int total = resList.size();
        int inArea = 0;
        int outside = 0;

        for (PointResult res : resList) {
            if (res.getHit()) {
                inArea++;
            } else {
                outside++;
            }
        }
        pointsInArea = inArea;
        pointsOutside = outside;
        totalPoints = total;
        if (totalPoints % 10 == 0) {
            String message = String.format("%d точек добавлено!", totalPoints);
            Notification notification = new Notification(
                    "point.milestone",
                    this,
                    ++notificationSequence,
                    System.currentTimeMillis(),
                    message
            );
            broadcaster.sendNotification(notification);
            System.out.println("Уведомление");
        }
    }

    private void sendNotification(String message) {
        Notification notification = new Notification(
                "point.statistics.event",
                this,
                ++notificationSequence,
                System.currentTimeMillis(),
                message
        );
        broadcaster.sendNotification(notification);
    }

    @Override
    public void addNotificationListener(
            NotificationListener listener,
            NotificationFilter filter,
            Object handback) {
        broadcaster.addNotificationListener(listener, filter, handback);
    }

    @Override
    public void removeNotificationListener(NotificationListener listener)
            throws ListenerNotFoundException {
        broadcaster.removeNotificationListener(listener);
    }

    @Override
    public MBeanNotificationInfo[] getNotificationInfo() {
        return new MBeanNotificationInfo[] {
                new MBeanNotificationInfo(
                        new String[]{"point.statistics.event"},
                        Notification.class.getName(),
                        "Уведомления о событиях в статистике точек"
                )
        };
    }
}