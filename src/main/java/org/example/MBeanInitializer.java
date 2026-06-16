package org.example;
import MBean.MBeanRegister;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import jakarta.inject.Inject;

@Singleton
@Startup
public class MBeanInitializer {

    @Inject
    private ResultsBean resultsBean;

    @PostConstruct
    public void init() {
        try {
            System.out.println("Регистрация MBean");
            MBeanRegister.registerAll(resultsBean);
            System.out.println("MBean зарегистрированы!");
        } catch (Exception e) {
            System.err.println("Ошибка: " + e.getMessage());
            e.printStackTrace();
        }
    }
}