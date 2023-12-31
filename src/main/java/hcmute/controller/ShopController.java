package hcmute.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import hcmute.dto.FilterForm;
import hcmute.model.Laptop;
import hcmute.model.Category;
import hcmute.service.ILaptopService;
import hcmute.service.ICategoryService;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/shop")
@Slf4j
public class ShopController {

	@Autowired
	private ILaptopService laptopService;

	@Autowired
	private ICategoryService categoryService;

	
	@GetMapping("/{categoryName}")
	public ModelAndView viewCartPage(@PathVariable(value = "categoryName") String category,
			@RequestParam(value = "page") int page) {
		List<Laptop> Laptops;
		if (category.compareTo("Tatca") == 0) {
			Laptops = laptopService.getAllLaptops();
		} else {
			Laptops = laptopService.getLaptopsByCategory(category);
		}
		List<Category> categories = categoryService.getAllCategories();
		Long numAllLaptops = laptopService.getNumAllLaptops();
		FilterForm filterForm = new FilterForm();
		filterForm.setMaxPrice(laptopService.getMaxPrice());
		filterForm.setMinPrice(0L);
		filterForm.setSort("none");
		filterForm.setTextSearch("");
		ModelAndView mav = new ModelAndView();
		if(page <= 0) page = 1;
		mav.addObject("laptops", Laptops);
		mav.addObject("categories", categories);
		mav.addObject("filterForm", filterForm);
		mav.addObject("numAllLaptops", numAllLaptops);
		mav.addObject("cur_category", category);
		mav.addObject("maxP", laptopService.getMaxPrice());
		mav.addObject("cur_page", page);
		mav.setViewName("client/shop");
		return mav;
	}

	@PostMapping("/{categoryName}")
	public ModelAndView viewCartPagePost(@PathVariable(value = "categoryName") String category,
			@RequestParam(value = "page") int page, @ModelAttribute("filterForm") FilterForm filterForm,
			BindingResult result) {
		List<Laptop> Laptops;
		if (category.compareTo("Tatca") == 0) {
			Laptops = laptopService.getAllLaptops();
		} else {
			Laptops = laptopService.getLaptopsByCategory(category);
		}
		Laptops = laptopService.filter(Laptops, filterForm);
		List<Category> categories = categoryService.getAllCategories();
		Long numAllLaptops = laptopService.getNumAllLaptops();
		ModelAndView mav = new ModelAndView();
		if(page <= 0) page = 1;
		mav.addObject("laptops", Laptops);
		mav.addObject("categories", categories);
		mav.addObject("filterForm", filterForm);
		mav.addObject("numAllLaptops", numAllLaptops);
		mav.addObject("cur_category", category);
		mav.addObject("cur_page", page);
		mav.addObject("maxP", laptopService.getMaxPrice());
		mav.setViewName("client/shop");
		return mav;
	}

	
}
