package com.lippujarjestelma.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.lippujarjestelma.domain.Category;
import com.lippujarjestelma.domain.CategoryRepository;

@Controller
public class CategoryController {
	@Autowired
	private CategoryRepository categoryrepository;

	@GetMapping(value = "/categorylist")
	public String categorylist(Model model) {
		model.addAttribute("categories", categoryrepository.findAll());
		return "categorylist";
	}

	@GetMapping(value = "/addcategory")
	public String addCategory(Model model) {
		model.addAttribute("category", new Category());
		return "addcategory";
	}

	@PostMapping(value = "/savecategory")
	public String saveCategory(Category category) {
		categoryrepository.save(category);
		return "categorysaved";
	}
}
