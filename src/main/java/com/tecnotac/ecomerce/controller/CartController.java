package com.tecnotac.ecomerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tecnotac.ecomerce.global.GlobalData;
import com.tecnotac.ecomerce.model.Product;
import com.tecnotac.ecomerce.service.ProductService;

@Controller
public class CartController {

	@Autowired
	ProductService productService;

	@PostMapping("/addToCart")
	public String addToCart(@RequestParam("id") int id, @RequestParam("quantity") int quantity) {

		Product product = productService.getProductById(id).get();
		product.setQuantity(quantity);
		product.setSubtotal(quantity*product.getPrice());
		boolean located = false;
		double newsubtotal = 0;
		int position = 0;
		int newquantity = 1;

		for (int x = 0; x < GlobalData.cart.size(); x++) {
			Product p = GlobalData.cart.get(x);
			if (p.getId().equals(product.getId())) {
				located = true;
				position = x;
				break;
			}
		}

		if (located) {
			newquantity = GlobalData.cart.get(position).getQuantity()+quantity;
			GlobalData.cart.get(position).setQuantity(newquantity);
			newsubtotal = GlobalData.cart.get(position).getPrice()*newquantity;
			GlobalData.cart.get(position).setSubtotal(newsubtotal);
			
		} else {
			GlobalData.cart.add(productService.getProductById(id).get());
		}
		System.out.println("carrito: " + GlobalData.cart);
		return "redirect:/shop";
	}

	@GetMapping("/cart")
	public String cartGet(Model model) {
		model.addAttribute("cartCount", GlobalData.cart.size());
		model.addAttribute("total", GlobalData.cart.stream().mapToDouble(Product::getSubtotal).sum());
		model.addAttribute("cart", GlobalData.cart);
		return "/views/cart/cart";
	}

	@GetMapping("/cart/removeItem/{index}")
	public String cartItemRemove(@PathVariable int index) {
		GlobalData.cart.remove(index);
		return "redirect:/cart";
	}

	@GetMapping("/checkout")
	public String checkout(Model model) {
		model.addAttribute("cartCount", GlobalData.cart.size());
		model.addAttribute("total", GlobalData.cart.stream().mapToDouble(Product::getSubtotal).sum());
		model.addAttribute("cart", GlobalData.cart);
		return "/views/checkout/checkout";
	}

}
