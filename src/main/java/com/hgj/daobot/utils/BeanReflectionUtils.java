package com.hgj.daobot.utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

import org.apache.commons.lang3.StringUtils;

public final class BeanReflectionUtils {

    private BeanReflectionUtils() {
    }

    public static final String GETTER = "get";
    public static final String SETTER = "set";
    private static final Integer NUMBER_ZERO = 0;
    private static final Integer NUMBER_ONE = 1;
    private static final Integer NUMBER_TWO = 2;

    /**
     * Method to get the name of get method from the field
     * 
     * @param fieldName
     *            Name of the field
     * @return Name of get method
     */
    public static String getSetter(String fieldName) {

        return new StringBuilder().append(SETTER)
                .append(StringUtils.capitalize(fieldName)).toString();

    }

    /**
     * Method to get the name of set method from the field
     * 
     * @param fieldName
     *            Name of the field
     * @return Name of set method
     */
    public static String getGetter(String fieldName) {

        return new StringBuilder().append(GETTER)
                .append(StringUtils.capitalize(fieldName)).toString();

    }

    /**
     * Checks the instance for nulls given the field names
     * 
     * @param instance
     *            the root instance
     * @param fieldNames
     *            the field names to be checked
     * @return true If any of fields is null
     */
    public static boolean hasNulls(Object instance, String... fieldNames) {

        if (instance == null) {
            throw new IllegalArgumentException("Instance cannot be null!");
        }

        boolean hasNulls = false;

        for (String fieldName : fieldNames) {

            if (get(instance, fieldName) == null) {
                hasNulls = true;
                break;
            }

        }

        return hasNulls;
    }

    /**
     * Gets the value of an object by calling the getter method given a field
     * Path
     * 
     * Example: For instance.getA() use BeanReflectionUtils.get(instance,"a");
     * 
     * For instance.getA().getB() use BeanReflectionUtils.get(instance,"a.b");
     * 
     * @param instance
     *            The root object instance
     * @param fieldPath
     *            The field path: i.e. For: instance.getA().getB() the field
     *            path would be "a.b"
     * @return the object returned from the Getter method
     */
    public static Object get(Object instance, String fieldPath) {

        Object result = null;

        if (instance == null) {
            throw new IllegalArgumentException("instance cannot be null");
        }

        if (fieldPath == null) {
        	throw new IllegalArgumentException("fieldPath cannot be null!");
        }

        String[] fields = fieldPath.split("\\.", NUMBER_TWO);

        String primaryField = fields[NUMBER_ZERO];
        String primaryGetter = getGetter(primaryField);

        Object primaryObject;
        try {
            primaryObject = instance.getClass()
                    .getDeclaredMethod(primaryGetter, new Class[NUMBER_ZERO])
                    .invoke(instance, new Object[NUMBER_ZERO]);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e);
        } catch (SecurityException e) {
            throw new IllegalArgumentException(e);
        } catch (IllegalAccessException e) {
            throw new IllegalArgumentException(e);
        } catch (InvocationTargetException e) {
            throw new IllegalArgumentException(e);
        } catch (NoSuchMethodException e) {
            throw new IllegalArgumentException(e);
        }

        if (fields.length > NUMBER_ONE) {
            String secondaryField = fields[NUMBER_ONE];
            result = get(primaryObject, secondaryField);
        } else {
            result = primaryObject;
        }

        return result;
    }

    /**
     * Sets the value of an object by calling the setter method given a field
     * Path
     * 
     * Example: For instance.setA() use
     * BeanReflectionUtils.set(instance,"a","test");
     * 
     * For instance.getA().setB() use
     * BeanReflectionUtils.get(instance,"a.b","test");
     * 
     * @param instance
     *            The root object instance
     * @param fieldPath
     *            The field path: i.e. For: instance.getA().setB() the field
     *            path would be "a.b"
     * @param value
     *            Value to set the field
     */
    public static void set(Object instance, String fieldPath, Object value) {

        if (instance == null) {
            throw new IllegalArgumentException(
                    "Either the instance or one of the elements in the field path is null!");
        }

        if (fieldPath == null) {
            throw new IllegalArgumentException("fieldPath cannot be null!");
        }

        String[] fields = fieldPath.split("\\.", NUMBER_TWO);

        String primaryFieldName = fields[NUMBER_ZERO];
        String primarySetter = getSetter(primaryFieldName);

        Field primaryField = null;

        try {
            primaryField = instance.getClass().getDeclaredField(
                    primaryFieldName);
        } catch (SecurityException e) {
            throw new IllegalArgumentException(e);
        } catch (NoSuchFieldException e) {
            throw new IllegalArgumentException(e);
        }

        if (fields.length > NUMBER_ONE) {

            setSecondaryField(instance, value, primaryField, fields[NUMBER_ONE]);

        } else {

            setField(instance, value, primarySetter, primaryField);

        }
    }

    /**
     * Sets the value of an object to the field using setter method
     * 
     * @param instance
     *            The root object instance
     * @param value
     *            Value to set the field
     * @param setter
     *            Name of setter's field
     * @param field
     *            Field to set the value
     */
    private static void setField(Object instance, Object value, String setter,
            Field field) {
        try {
            instance.getClass()
                    .getDeclaredMethod(setter, new Class[] { field.getType() })
                    .invoke(instance, value);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e);
        } catch (SecurityException e) {
            throw new IllegalArgumentException(e);
        } catch (IllegalAccessException e) {
            throw new IllegalArgumentException(e);
        } catch (InvocationTargetException e) {
            throw new IllegalArgumentException(e);
        } catch (NoSuchMethodException e) {
            throw new IllegalArgumentException(e);
        }
    }

    /**
     * Set the value of secondary field, for example instance.getA().setB()
     * 
     * @param instance
     *            The root object instance
     * @param value
     *            Value to set the field
     * @param primaryField
     *            Primary Field which belongs the secondary field
     * @param secondaryFieldName
     *            Name of secondary Field
     */
    private static void setSecondaryField(Object instance, Object value,
            Field primaryField, String secondaryFieldName) {

        String primaryFieldName = primaryField.getName();

        Object primaryObject = get(instance, primaryFieldName);

        if (primaryObject == null) {
            try {
                primaryObject = primaryField.getType().newInstance();
                set(instance, primaryFieldName, primaryObject);
            } catch (InstantiationException e) {
                throw new IllegalArgumentException(e);
            } catch (IllegalAccessException e) {
                throw new IllegalArgumentException(e);
            }
        }

        set(primaryObject, secondaryFieldName, value);
    }
}
