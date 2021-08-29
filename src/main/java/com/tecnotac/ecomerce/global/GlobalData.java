package com.tecnotac.ecomerce.global;

import java.util.ArrayList;
import java.util.List;

import com.tecnotac.ecomerce.model.Product;

public class GlobalData {
	public static List<Product> cart;
	static {
		cart = new ArrayList<Product>();
	}
}
