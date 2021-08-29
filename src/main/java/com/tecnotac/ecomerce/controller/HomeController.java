package com.tecnotac.ecomerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.tecnotac.ecomerce.global.GlobalData;
import com.tecnotac.ecomerce.model.Product;
import com.tecnotac.ecomerce.service.CategoryService;
import com.tecnotac.ecomerce.service.CustomUserDetailService;
import com.tecnotac.ecomerce.service.ProductService;

@Controller
public class HomeController {
	
	@Autowired
	CategoryService categoryService;
	
	@Autowired
	ProductService productService;
	
	@Autowired
	CustomUserDetailService customUserDetailService;
	
	@GetMapping({"/","home","index"})
	public String home(Model model) {
		model.addAttribute("categories", categoryService.getAllCategory());
		model.addAttribute("products", productService.getAllProduct());
		model.addAttribute("cartCount",GlobalData.cart.size());
		model.addAttribute("total", GlobalData.cart.stream().mapToDouble(Product::getSubtotal).sum());
		model.addAttribute("cart",GlobalData.cart);
		return "/views/home/clientHome";
	}
	
	@GetMapping("/contact")
	public String contact(Model model) {
		model.addAttribute("cartCount",GlobalData.cart.size());
		model.addAttribute("total", GlobalData.cart.stream().mapToDouble(Product::getSubtotal).sum());
		model.addAttribute("cart",GlobalData.cart);
		return "/views/contact/contact";
	}
	
	@GetMapping("/shop")
	public String shop(Model model) {
		model.addAttribute("categories", categoryService.getAllCategory());
		model.addAttribute("products", productService.getAllProduct());
		model.addAttribute("cartCount",GlobalData.cart.size());
		model.addAttribute("total", GlobalData.cart.stream().mapToDouble(Product::getSubtotal).sum());
		model.addAttribute("cart",GlobalData.cart);
		return "/views/shop/shop";
	}
	
	@GetMapping("/shop/category/{id}")
	public String shopByCategory(Model model, @PathVariable int id) {
		model.addAttribute("categories", categoryService.getAllCategory());
		model.addAttribute("products", productService.getAllProductsByCategoryId(id));
		model.addAttribute("cartCount",GlobalData.cart.size());
		model.addAttribute("total", GlobalData.cart.stream().mapToDouble(Product::getSubtotal).sum());
		model.addAttribute("cart",GlobalData.cart);
		return "/views/shop/shop";
	}
	
	@GetMapping("/shop/viewproduct/{id}")
	public String viewProduct(Model model, @PathVariable int id) {
		model.addAttribute("product", productService.getProductById(id).get());
		model.addAttribute("products", productService.getAllProduct());
		model.addAttribute("cartCount",GlobalData.cart.size());
		model.addAttribute("total", GlobalData.cart.stream().mapToDouble(Product::getSubtotal).sum());
		model.addAttribute("cart",GlobalData.cart);
		return "/views/product/viewProduct";
	}
	
	@GetMapping("/myaccount")
	public String myAccount(Model model) {
		model.addAttribute("cartCount",GlobalData.cart.size());
		model.addAttribute("total", GlobalData.cart.stream().mapToDouble(Product::getSubtotal).sum());
		model.addAttribute("cart",GlobalData.cart);
		return "/views/account/myaccount";
	}
	
}
