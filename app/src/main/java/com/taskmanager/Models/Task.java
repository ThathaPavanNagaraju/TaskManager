package com.taskmanager.Models;

import java.io.Serializable;

/**
 * Created by Pavan Nagaraju on 16-Jun-17.
 */

public class Task implements Serializable {
    private String name,description,modifiedDate,createdDate,id;

    public Task(String name, String description, String modifiedDate, String createdDate) {
        this.name = name;
        this.description = description;
        this.modifiedDate = modifiedDate;
        this.createdDate = createdDate;
    }

    public Task(String name, String description, String modifiedDate, String createdDate, String id) {
        this.name = name;
        this.description = description;
        this.modifiedDate = modifiedDate;
        this.createdDate = createdDate;
        this.id = id;
    }

    public Task(){

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(String modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }
}
