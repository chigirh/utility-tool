package chigirh.app.utility.boot;

import org.springframework.context.ApplicationContext;

/**
 * ApplicationContextSupport.
 */
public class ApplicationContextSupport {

	private static ApplicationContext applicationContext;

	static void set(ApplicationContext applicationContext) {
		ApplicationContextSupport.applicationContext = applicationContext;
	}

	public static ApplicationContext getContext() {
		return applicationContext;
	}


	public static <T> T getBean(Class<T> requiredType) {
		return (T)applicationContext.getBean(requiredType);

	}

	public static <T> T getBean(String name) {
		return (T)applicationContext.getBean(name);

	}

	public static String getProperty(String property) {
		return applicationContext.getEnvironment().getProperty("${"+property+"}");

	}

}
