package com.adobe.aem.demo.assesment.core.models;

import org.apache.sling.api.resource.Resource;

import java.util.List;

public class ChildPagesBeanModel {

    private String name;

    private String path;

    private List<ChildPagesBeanModel> childPages;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public List<ChildPagesBeanModel> getChildPages() {
        return childPages;
    }

    public void setChildPages(List<ChildPagesBeanModel> childPages) {
        this.childPages = childPages;
    }
}
