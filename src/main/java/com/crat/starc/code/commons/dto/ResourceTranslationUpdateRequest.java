package com.crat.starc.code.commons.dto;

import java.lang.reflect.Field;

public class ResourceTranslationUpdateRequest {

    private Object resource;
    private String language;
    private String translation;

    private Field field;

    public ResourceTranslationUpdateRequest() {
    }

    public Object getResource() {
        return resource;
    }

    public void setResource(Object resource) {
        this.resource = resource;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getTranslation() {
        return translation;
    }

    public void setTranslation(String translation) {
        this.translation = translation;
    }

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }

}
