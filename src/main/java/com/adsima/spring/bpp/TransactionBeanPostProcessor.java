package com.adsima.spring.bpp;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class TransactionBeanPostProcessor implements BeanPostProcessor
{
    private Map<String, Class<?>> transactionBeans = new HashMap<>();

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (bean.getClass().isAnnotationPresent(Transaction.class)) {
            transactionBeans.put(beanName, bean.getClass());
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Class<?> aClass = transactionBeans.get(beanName);
        if (aClass != null) {
            return Proxy.newProxyInstance(aClass.getClassLoader(), aClass.getInterfaces(), (proxy, method, args) -> {
                log.info("Open transaction");
                try {
                    log.info("Method invoke by bean {}", beanName);
                    return method.invoke(bean, args);
                } finally {
                    log.info("Close transaction");
                }
            });
        }
        return bean;
    }
}
