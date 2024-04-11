package utils.reflectionutils;

import javax.persistence.EntityManager;
import javax.persistence.Id;
import java.lang.reflect.Field;
import java.util.Arrays;

public class ReflectionUtils {
    public static <T> T updateReflection(T object, T temp, EntityManager entityManager) {
            Field[] objFields = object.getClass().getDeclaredFields();
        Arrays.stream(objFields)
                .filter(field -> field.isAnnotationPresent(Id.class))
                .peek(q -> q.setAccessible(true))
                .forEach(field -> {
                    try {
                        if(field.get(object)!=null){
                            field.set(object, field.get(temp));
                        }
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                });

        return entityManager.merge(object);
    }
}