package hcmute.controller;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import hcmute.model.Laptop;
import hcmute.model.Category;
import hcmute.service.ILaptopService;
import hcmute.service.ICategoryService;
import hcmute.utils.AppConstant;
import hcmute.utils.FileUploadUtils;

@Controller
@RequestMapping("/")
public class HomeController {

	@Autowired
	ICategoryService categoryService;
	
	@Autowired
	ILaptopService laptopService;
	

	@GetMapping
	public ModelAndView home(ModelAndView mav) {
		List<Category> categories = categoryService.getAllCategories();
		List<Laptop> topFeatured = laptopService.getTopFeatured();
		List<Laptop> bestSeller = laptopService.getBestSeller();
		mav.addObject("categories", categories);
		mav.addObject("topFeatured", topFeatured);
		mav.addObject("bestSeller", bestSeller);
		mav.setViewName("client/index.html");
		return mav;
	}

	
	// Test request for upload file
	@GetMapping("/upload")
	public ModelAndView testUploadImage(ModelAndView mav) {
		mav.setViewName("client/uploadImage.html");
		return mav;
	}

	@PostMapping("/upload")
	public ModelAndView getUploadImage(ModelAndView mav, @RequestParam("image") MultipartFile file) throws IOException {
		FileUploadUtils.saveFile(AppConstant.UPLOAD_DIRECTORY,file);
		StringBuilder fileName = new StringBuilder();
		fileName.append(file.getOriginalFilename());
		mav.addObject("imagePath",fileName.toString());
		mav.setViewName("client/uploadImage.html");
		return mav;
	}
}
