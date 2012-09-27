package com.thinkingtop.kaas.services.util;

import java.lang.reflect.*;
import java.net.*;

public class ClassUtil2 {
	/**
	 * @param strClassName
	 * @param argsType
	 * @param args
	 * @return Object
	 * @throws java.lang.NoSuchMethodException
	 * @throws java.lang.SecurityException
	 * @throws java.lang.ClassNotFoundException
	 * @throws java.lang.InstantiationException
	 * @throws java.lang.IllegalAccessException
	 * @throws java.lang.IllegalArgumentException
	 * @throws java.lang.reflect.InvocationTargetException
	 */
	public static Object loadClass(String strClassName, Class[] argsType,
			Object[] args) throws NoSuchMethodException, SecurityException,
			ClassNotFoundException, InstantiationException,
			IllegalAccessException, IllegalArgumentException,
			InvocationTargetException {
		Object returnObj = null;
		Class className = null;
		Constructor constructor = null;
		className = Class.forName(strClassName);
		constructor = className.getConstructor(argsType);
		returnObj = constructor.newInstance(args);
		return returnObj;
	}

	/**
	 * @param strClassName
	 * @return Object
	 * @throws java.lang.NoSuchMethodException
	 * @throws java.lang.SecurityException
	 * @throws java.lang.ClassNotFoundException
	 * @throws java.lang.InstantiationException
	 * @throws java.lang.IllegalAccessException
	 * @throws java.lang.IllegalArgumentException
	 * @throws java.lang.reflect.InvocationTargetException
	 */
	public static Object loadClass(String strClassName)
			throws NoSuchMethodException, SecurityException,
			ClassNotFoundException, InstantiationException,
			IllegalAccessException, IllegalArgumentException,
			InvocationTargetException {
		return loadClass(strClassName, null, null);
	}

	public static Object loadClass(String strClassName, Object defObj) {
		try {
			return loadClass(strClassName, null, null);
		} catch (Throwable ex) {
			// LogUtil.logException( ex );
		}
		return defObj;
	}

	/**
	 * @param classObject
	 * @param strMethodName
	 * @param argsType
	 * @param args
	 * @return Object
	 * @throws java.lang.NoSuchMethodException
	 * @throws java.lang.SecurityException
	 * @throws java.lang.IllegalAccessException
	 * @throws java.lang.IllegalArgumentException
	 * @throws java.lang.reflect.InvocationTargetException
	 */
	public static Object invokeMothod(Object classObject, String strMethodName,
			Class[] argsType, Object[] args) throws NoSuchMethodException,
			SecurityException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {
		Method concatMethod = classObject.getClass().getMethod(strMethodName,
				argsType);
		return concatMethod.invoke(classObject, args);
	}

	/**
	 * @param classObject
	 * @param strMethodName
	 * @return Object
	 * @throws java.lang.NoSuchMethodException
	 * @throws java.lang.SecurityException
	 * @throws java.lang.IllegalAccessException
	 * @throws java.lang.IllegalArgumentException
	 * @throws java.lang.reflect.InvocationTargetException
	 */
	public static Object invokeMothod(Object classObject, String strMethodName)
			throws NoSuchMethodException, SecurityException,
			IllegalAccessException, IllegalArgumentException,
			InvocationTargetException {
		return invokeMothod(classObject, strMethodName, null, null);
	}

	/**
	 * @param classObject
	 * @param strFieldName
	 * @return Object
	 * @throws java.lang.NoSuchFieldException
	 * @throws java.lang.SecurityException
	 * @throws java.lang.IllegalArgumentException
	 * @throws java.lang.IllegalAccessException
	 */
	public static Object getFieldValue(Object classObject, String strFieldName)
			throws NoSuchFieldException, SecurityException,
			IllegalArgumentException, IllegalAccessException {
		Object retObj = null;
		Field field;
		field = classObject.getClass().getDeclaredField(strFieldName);
		retObj = field.get(classObject);
		return retObj;
	}

	/**
	 * @param className
	 * @return String
	 */
	public static String whereClass(String className) {
		String retStr = null;
		if (!className.startsWith("/")) {
			className = "/" + className;
		}
		className = className.replace(".", "/");
		className = className + ".class";
		java.net.URL classUrl = getResource(className);
		if (classUrl != null) {
			String str = classUrl.getFile();
			retStr = str;
		}
		return retStr;
	}

	/**
	 * @param name
	 * @return java.net.URL
	 */
	public static URL getResource(String name) {
		return ClassUtil2.class.getResource(name);
	}

	public static void compiler(String targetDir, String filename,
			String classpath) throws Exception {
		// javac -d E:/test -classpath C:\work\WEB-INF\lib\bss.jar
		// e:/test/TestVector.java
		String str = "javac -d " + targetDir + " -classpath " + classpath + " "
				+ filename;
		// LogUtil.logInfo( str );
		Runtime runtime = Runtime.getRuntime();
		Process p = runtime.exec(str);
	}

	public static void compiler(String targetDir, String filename)
			throws Exception {
		// javac -d E:/test -classpath C:\work\WEB-INF\lib\bss.jar
		// e:/test/TestVector.java
		Runtime runtime = Runtime.getRuntime();
		Process p = runtime.exec("javac -d " + targetDir + " " + filename);
	}
}
