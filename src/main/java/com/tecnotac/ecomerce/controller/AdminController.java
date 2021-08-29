package com.tecnotac.ecomerce.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.tecnotac.ecomerce.dto.ProductDTO;
import com.tecnotac.ecomerce.model.Category;
import com.tecnotac.ecomerce.model.Product;
import com.tecnotac.ecomerce.service.CategoryService;
import com.tecnotac.ecomerce.service.ProductService;

@Controller
public class AdminController{
	
	//public static String uploadDir = System.getProperty("user.dir") + "/src/main/resources/static/productImages";
	//public static String uploadDir = System.getProperty("user.dir") + "\\productimgs\\";
	
	@Autowired
	CategoryService categoryService;
	
	@Autowired
	ProductService productService;
	
	@GetMapping("/admin")
	public String adminHome() {
		return "/views/home/adminHome";
	}
	
	@GetMapping("/admin/categories")
	public String getCategories(Model model) {
		model.addAttribute("categories", categoryService.getAllCategory());
		return "/views/category/categories";
	}
	
	@GetMapping("/admin/categories/add")
	public String getCategoriesAdd(Model model) {
		model.addAttribute("category", new Category());
		model.addAttribute("title", "Nueva categoría");
		return "/views/category/categoriesAdd";
	}
	
	@PostMapping("/admin/categories/add")
	public String postCategoriesAdd(@ModelAttribute("category") Category category) {
		categoryService.addCategory(category);
		return "redirect:/admin/categories";
	}
	
	@GetMapping("/admin/categories/delete/{id}")
	public String deleteCategory(@PathVariable int id) {
		categoryService.removeCategoryById(id);
		return "redirect:/admin/categories";
	}
	
	@GetMapping("/admin/categories/update/{id}")
	public String updateCategory(@PathVariable int id, Model model) {
		Optional<Category> category = categoryService.getCategoryById(id);
		if (category.isPresent()) {
			model.addAttribute("category", category.get());
			model.addAttribute("title", "Editar categoría");
			return "/views/category/categoriesAdd";
		} else {
			return "/error/404";
		}
	}
	
	//Product Section
	@GetMapping("/admin/products")
	public String getProducts(Model model) {
		model.addAttribute("products", productService.getAllProduct());
		return "/views/product/products";
	}
	
	@GetMapping("/admin/products/add")
	public String getProductsAdd(Model model) {
		model.addAttribute("productDTO", new ProductDTO());
		model.addAttribute("categories", categoryService.getAllCategory());
		model.addAttribute("title", "Nuevo producto");
		return "/views/product/productsAdd";
	}
	
	@PostMapping("/admin/products/add")
	public String postProductsAdd(@ModelAttribute("productDTO") ProductDTO productDTO, 
			@RequestParam("productImage") MultipartFile file,
			@RequestParam("imgName") String imgName, HttpServletRequest request) throws IOException{
		
		String uploadDir = request.getServletContext().getRealPath("/productimgs/");
		//String uploadDir = servletContext.getRealPath("/productimgs/");
		
		
		Product product = new Product();
		product.setId(productDTO.getId());
		product.setName(productDTO.getName());
		product.setCategory(categoryService.getCategoryById(productDTO.getCategoryId()).get());
		product.setPrice(productDTO.getPrice());
		product.setWeight(productDTO.getWeight());
		product.setDescription(productDTO.getDescription());
		String imageUUID;
		if (!file.isEmpty()) {
			imageUUID = file.getOriginalFilename();
			Path fileNanmePath = Paths.get(uploadDir, imageUUID);
			Files.write(fileNanmePath, file.getBytes());
		} else {
			imageUUID = imgName;
		}
		
		product.setImageName(imageUUID);
		productService.addProduct(product);
		return "redirect:/admin/products";
	}
	
	@GetMapping("/admin/product/delete/{id}")
	public String deleteProduct(@PathVariable long id) {
		productService.removeProductById(id);
		return "redirect:/admin/products";
	}
	
	@GetMapping("/admin/product/update/{id}")
	public String updateProduct(@PathVariable long id, Model model) {
		Product product = productService.getProductById(id).get();
		ProductDTO productDTO = new ProductDTO();
		productDTO.setId(product.getId());
		productDTO.setName(product.getName());
		productDTO.setCategoryId(product.getCategory().getId());
		productDTO.setPrice(product.getPrice());
		productDTO.setWeight(product.getWeight());
		productDTO.setDescription(product.getDescription());
		productDTO.setImageName(product.getImageName());
		
		model.addAttribute("categories", categoryService.getAllCategory());
		model.addAttribute("productDTO", productDTO);
		model.addAttribute("title", "Editar producto");
		
		return "/views/product/productsAdd";
	}

}
