package com.smart.sso.demo.entity.catalogue;

/**
 * @Author xhx
 * @Date 2022/1/25 16:22
 */
public class Catalogue {
    int catalogueId;
    String description;

    public Catalogue(int catalogueId, String description) {
        this.catalogueId = catalogueId;
        this.description = description;
    }

    public Catalogue() {
    }

    public int getCatalogueId() {
        return catalogueId;
    }

    public void setCatalogueId(int catalogueId) {
        this.catalogueId = catalogueId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
