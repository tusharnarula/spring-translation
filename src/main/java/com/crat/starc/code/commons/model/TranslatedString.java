package com.crat.starc.code.commons.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashMap;
import java.util.Map;

@Document(collection = "Translation")
public class TranslatedString {

    @Id
    private String id;

    private String resourceType;

    private String resourceId;

    private Map<String, String> translation;

    public TranslatedString() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getResourceType() {
        return resourceType;
    }

    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
    }

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public Map<String, String> getTranslation() {
        return translation;
    }

    public void setTranslation(Map<String, String> translation) {
        this.translation = translation;
    }

    public void addTranslation(String lang, String translation) {
        if(this.getTranslation()==null){
            this.setTranslation(new HashMap<>());
        }
        this.getTranslation().put(lang, translation);
    }
}
