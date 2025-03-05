package br.com.avancard;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class ApplicationContextLoad implements ApplicationContextAware {

    //ATRIBUTOS
    private static ApplicationContext applicationContext;


    //MÉTODOS
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }


    //MÉTODOS ESPECIAIS
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }
}
