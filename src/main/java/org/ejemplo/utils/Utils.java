package org.ejemplo.utils;

import java.util.List;
import java.util.function.Predicate;

public class Utils {
    public static Boolean validateStringNotEmptyAndNotNull(String string){
        return string == null || string.isBlank();
    }
    public static <T> boolean exists(List<T> items, Predicate<T> predicate) {
        return items.stream().anyMatch(predicate);
    }
}
