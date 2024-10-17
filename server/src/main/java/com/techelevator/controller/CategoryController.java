package com.techelevator.controller;

import com.techelevator.dao.CategoryDao;
import com.techelevator.model.Category;
import com.techelevator.model.dto.CategoryPostDto;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(path = "/categories")
public class CategoryController {
    private CategoryDao dao;

    public CategoryController(CategoryDao dao) {
        this.dao = dao;
    }

    @GetMapping
    public List<Category> list() {
        return dao.getCategories();
    }

    @PostMapping
    public Category add(@RequestBody CategoryPostDto dto) {
        Category category = new Category(dto.getCategoryName());
        return dao.addCategory(category);
    }
}
