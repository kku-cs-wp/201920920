package kr.ac.kku.cs.wp.JW_P1.tools.json;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

public class ReflectionUtil {

    public static <T> T createObjectFromJson(Class<T> clazz, JSONObject jsonObject) throws Exception {
        // Get the declared constructor (even if it's private) and make it accessible
        Constructor<T> constructor = clazz.getDeclaredConstructor();
        constructor.setAccessible(true); // Allows us to access private constructors if necessary
        T obj = constructor.newInstance();

        // Get all fields of the class
        Field[] fields = clazz.getDeclaredFields();

        // Iterate over each field
        for (Field field : fields) {
            field.setAccessible(true); // Make the private fields accessible

            // Get the field name and check if the JSON object has a matching key
            String fieldName = field.getName();
            if (!jsonObject.has(fieldName)) {
                continue;
            }

            // Handle lists
            if (List.class.isAssignableFrom(field.getType())) {
                ParameterizedType listType = (ParameterizedType) field.getGenericType();
                Class<?> listClass = (Class<?>) listType.getActualTypeArguments()[0];

                JSONArray jsonArray = jsonObject.getJSONArray(fieldName);
                List<Object> list = new ArrayList<>();

                // Convert each element in the JSONArray to the appropriate type
                for (int i = 0; i < jsonArray.length(); i++) {
                    Object item;
                    if (listClass.isPrimitive() || listClass == String.class || Number.class.isAssignableFrom(listClass)) {
                        item = jsonArray.get(i); // Direct assignment for primitives and strings
                    } else {
                        item = createObjectFromJson(listClass, jsonArray.getJSONObject(i)); // Recursive call for complex objects
                    }
                    list.add(item);
                }
                field.set(obj, list);
            } else {
                // Handle primitive types and other objects
                Object value = jsonObject.get(fieldName);

                if (field.getType() == int.class || field.getType() == Integer.class) {
                    field.set(obj, jsonObject.getInt(fieldName));
                } else if (field.getType() == boolean.class || field.getType() == Boolean.class) {
                    field.set(obj, jsonObject.getBoolean(fieldName));
                } else if (field.getType() == double.class || field.getType() == Double.class) {
                    field.set(obj, jsonObject.getDouble(fieldName));
                } else if (field.getType() == long.class || field.getType() == Long.class) {
                    field.set(obj, jsonObject.getLong(fieldName));
                } else if (field.getType() == String.class) {
                    field.set(obj, jsonObject.getString(fieldName));
                } else {
                    // For other objects, directly set the value
                    field.set(obj, value);
                }
            }
        }

        return obj;
    }
}
