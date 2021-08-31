package com.ksp.utils;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

@Service
public class BeanUtils implements ApplicationContextAware {
	private ApplicationContext context;

	@Override
	public void setApplicationContext(ApplicationContext context) {
		this.context = context;
	}

	public <T> T getBean(Class<T> beanClass) {
		return context.getBean(beanClass);
	}

}
