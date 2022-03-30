package app.loader.interfaces;

import app.status.StatusListener;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public interface ClassMethods {
    static Method createMethodGetInfo(Class<?> loadedClass) throws NoSuchMethodException {
        return loadedClass.getDeclaredMethod("getInfo");
    }

    static Method createMethodGetResult(Class<?> loadedClass) throws NoSuchMethodException {
        return loadedClass.getDeclaredMethod("getResult");
    }

    static Method createMethodSubmitTask(Class<?> loadedClass) throws NoSuchMethodException {
        return loadedClass.getDeclaredMethod("submitTask", String.class, StatusListener.class);
    }

    static Object createObjectFromConstructor(Class<?> loadedClass) throws NoSuchMethodException,
            InvocationTargetException, InstantiationException, IllegalAccessException {
        return loadedClass.getConstructor().newInstance();
    }
}
