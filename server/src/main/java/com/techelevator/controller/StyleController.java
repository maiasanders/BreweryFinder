package com.techelevator.controller;

import com.techelevator.dao.StyleDao;
import com.techelevator.model.Style;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RestController
public class StyleController {
    private StyleDao dao;

    public StyleController(StyleDao dao) {
        this.dao = dao;
    }

    @GetMapping(path = "/styles")
    public List<Style> get() {
        return dao.getStyles();
    }
}
