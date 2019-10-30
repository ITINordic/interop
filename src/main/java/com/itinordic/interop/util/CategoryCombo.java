package com.itinordic.interop.util;

import java.util.List;

/**
 *
 * @author developer
 */
public class CategoryCombo {

    private String id;
    private List<Category> categories;
    private List<CategoryOptionCombo> categoryOptionCombos;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public List<CategoryOptionCombo> getCategoryOptionCombos() {
        return categoryOptionCombos;
    }

    public void setCategoryOptionCombos(List<CategoryOptionCombo> categoryOptionCombos) {
        this.categoryOptionCombos = categoryOptionCombos;
    }

    public String getCategoriesAsString() {
        String string = "";
        if (categories != null && !categories.isEmpty()) {
            for (Category category : categories) {
                if (!string.isEmpty()) {
                    string += ", ";
                }
                string += category.getName();
            }
        }
        return string;
    }

    @Override
    public String toString() {
        return "CategoryCombo{" + "id=" + id + ", categories=" + categories + ", categoryOptionCombos=" + categoryOptionCombos + '}';
    }

}
