package com.techelevator.dao;

import com.techelevator.model.Category;

import java.util.List;

public interface CategoryDao {
    Category getCategoryByName(String categoryName, boolean wild);

    List<Category> getCategoriesByEventId(int id);

    List<Category> getCategories();
    Category addCategory(Category category);
    Category getCategoryById(int id);

}
