package com.github.ddth.commons.utils;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;

/**
 * Spring-related utilities.
 * 
 * @author Thanh Ba Nguyen <bnguyen2k@gmail.com>
 * @since 0.1.1
 */
public class SpringUtils {

	/**
	 * Gets a bean by its id.
	 * 
	 * @param appContext
	 * @param name
	 * @return
	 */
	public static Object getSpringBean(ApplicationContext appContext, String id) {
		try {
			Object bean = appContext.getBean(id);
			return bean;
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * Gets a bean by its class.
	 * 
	 * @param <T>
	 * @param appContext
	 * @param clazz
	 * @return
	 */
	public static <T> T getBean(ApplicationContext appContext, Class<T> clazz) {
		try {
			return appContext.getBean(clazz);
		} catch (BeansException e) {
			return null;
		}
	}

	/**
	 * Gets a bean by its id and class.
	 * 
	 * @param appContext
	 * @param id
	 * @param clazz
	 * @return
	 */
	public static <T> T getBean(ApplicationContext appContext, String id,
			Class<T> clazz) {
		try {
			return appContext.getBean(id, clazz);
		} catch (BeansException e) {
			return null;
		}
	}

	/**
	 * Gets all beans of a given type.
	 * 
	 * @param appContext
	 * @param clazz
	 * @return a Map with the matching beans, containing the bean names as keys
	 *         and the corresponding bean instances as values
	 */
	public static <T> Map<String, T> getBeansOfType(
			ApplicationContext appContext, Class<T> clazz) {
		try {
			return appContext.getBeansOfType(clazz);
		} catch (BeansException e) {
			return new HashMap<String, T>();
		}
	}

	/**
	 * Gets all beans whose Class has the supplied Annotation type.
	 * 
	 * @param appContext
	 * @param annotationType
	 * @return a Map with the matching beans, containing the bean names as keys
	 *         and the corresponding bean instances as values
	 */
	public static Map<String, Object> getBeansWithAnnotation(
			ApplicationContext appContext,
			Class<? extends Annotation> annotationType) {
		try {
			return appContext.getBeansWithAnnotation(annotationType);
		} catch (BeansException e) {
			return new HashMap<String, Object>();
		}
	}
}
