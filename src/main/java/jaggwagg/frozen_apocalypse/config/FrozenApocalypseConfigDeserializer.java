package jaggwagg.frozen_apocalypse.config;

import com.google.gson.*;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class FrozenApocalypseConfigDeserializer implements JsonDeserializer<FrozenApocalypseConfig> {
    @Override
    public FrozenApocalypseConfig deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        FrozenApocalypseConfig config = new FrozenApocalypseConfig();
        Field[] fields = FrozenApocalypseConfig.class.getDeclaredFields();
        Set<Field> requiredFields = new HashSet<>();

        checkFields(jsonObject, config, fields, requiredFields, jsonDeserializationContext);
        checkMissingOrUnknownFields(jsonObject, requiredFields);

        return config;
    }

    private void checkFields(JsonObject jsonObject, Object targetType, Field[] targetTypeFields, Set<Field> requiredFields, JsonDeserializationContext jsonDeserializationContext) {
        for (Field field : targetTypeFields) {
            if (Modifier.isStatic(field.getModifiers()) || Modifier.isTransient(field.getModifiers())) {
                continue;
            }

            requiredFields.add(field);

            if (!jsonObject.has(field.getName())) {
                continue;
            }

            JsonElement fieldValue = jsonObject.get(field.getName());

            try {
                if (fieldValue.isJsonNull()) {
                    throw new JsonParseException("Value is null: " + field.getName());
                }

                if (fieldValue.isJsonPrimitive()) {
                    JsonPrimitive jsonPrimitive = getJsonPrimitive(field, fieldValue);

                    if (field.getType().equals(String.class) && !jsonPrimitive.isString() || jsonPrimitive.getAsString().isEmpty()) {
                        throw new JsonParseException("Invalid string value for field: " + field.getName());
                    }
                }

                if (fieldValue instanceof JsonArray jsonArray) {
                    ParameterizedType listParameterizedType = (ParameterizedType) field.getGenericType();
                    List<?> listValue = jsonDeserializationContext.deserialize(fieldValue, listParameterizedType);

                    if (listValue.isEmpty()) {
                        throw new JsonParseException("List has no values: " + field.getName());
                    }

                    for (int i = 0; i < jsonArray.size(); i++) {
                        Object listType = listValue.get(i);
                        Field[] listFields = listType.getClass().getDeclaredFields();
                        Set<Field> listRequiredFields = new HashSet<>();
                        JsonElement element = jsonArray.get(i);
                        JsonObject elementObject;

                        if (element instanceof JsonPrimitive) {
                            continue;
                        } else {
                            elementObject = element.getAsJsonObject();
                        }

                        checkFields(elementObject, listType, listFields, listRequiredFields, jsonDeserializationContext);
                        checkMissingOrUnknownFields(elementObject, listRequiredFields);
                    }

                    setFieldAccessibleAndSetValue(field, targetType, listValue);
                } else {
                    Object value = jsonDeserializationContext.deserialize(fieldValue, field.getType());
                    setFieldAccessibleAndSetValue(field, targetType, value);
                }
            } catch (JsonParseException | NumberFormatException | IllegalAccessException e) {
                throw new JsonParseException("Error setting field: " + field.getName() + ": " + e.getMessage(), e);
            }
        }
    }

    private static JsonPrimitive getJsonPrimitive(Field field, JsonElement fieldValue) {
        JsonPrimitive jsonPrimitive = fieldValue.getAsJsonPrimitive();

        if (field.getType().equals(boolean.class) && !jsonPrimitive.isBoolean()) {
            throw new JsonParseException("Invalid boolean value for field: " + field.getName());
        }

        if (field.getType().equals(int.class) && !jsonPrimitive.isNumber()) {
            throw new JsonParseException("Invalid number value for field: " + field.getName());
        }

        return jsonPrimitive;
    }

    private void setFieldAccessibleAndSetValue(Field field, Object target, Object value) throws IllegalAccessException {
        field.setAccessible(true);
        field.set(target, value);
    }

    private void checkMissingOrUnknownFields(JsonObject jsonObject, Set<Field> requiredFields) {
        Set<String> jsonObjectKeyNames = new HashSet<>(jsonObject.keySet());
        Set<String> requiredFieldNames = requiredFields.stream().map(Field::getName).collect(Collectors.toSet());
        Set<String> missingFieldNames = new HashSet<>(requiredFieldNames);
        Set<String> unknownFieldNames = new HashSet<>(jsonObjectKeyNames);
        Set<String> missingOrUnknownFieldNames = new HashSet<>();

        missingFieldNames.removeAll(jsonObjectKeyNames);
        unknownFieldNames.removeAll(requiredFieldNames);
        missingOrUnknownFieldNames.addAll(missingFieldNames);
        missingOrUnknownFieldNames.addAll(unknownFieldNames);

        if (!missingOrUnknownFieldNames.isEmpty()) {
            throw new JsonParseException("Missing or unknown field(s): " + missingOrUnknownFieldNames);
        }
    }
}
