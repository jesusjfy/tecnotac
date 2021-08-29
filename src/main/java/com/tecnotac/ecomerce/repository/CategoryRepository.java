package com.tecnotac.ecomerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.tecnotac.ecomerce.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer>{

}