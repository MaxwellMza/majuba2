
package com.majuba.majuba.services;

import com.majuba.majuba.entities.Cart;
import com.majuba.majuba.entities.Category;
import com.majuba.majuba.entities.Food;
import com.majuba.majuba.repositories.CategoryRepository;
import com.majuba.majuba.repositories.FoodRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FoodService {
    
        
    @Autowired
    private FoodRepository foodRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    @Transactional
    public void create(byte[] image, String title, String description, Double price, Long category_id) {
        Food food = new Food();

       food.setCategory(categoryRepository.findById(category_id).orElse(null));
       food.setDescription(description);
       food.setImage(image);
      food.setPrice(price);
       food.setTitle(title);                                            
        foodRepository.save(food);

    }

    @Transactional(readOnly = true)
    public List<Food> fidAll() {
        return foodRepository.findAll();
    }
/*
   // @Transactional
   // public List<Cart> buscarPorNombre(String nombre) {
     //   return autorrepositorio.buscarPorNombre(nombre);
    //}
@Transactional
    public void modify(Integer cart_id,Cart cart){
        CartRepository.modify(cart_id,cart);
    }*/
    @Transactional
    public void delete(Long food_id) {
       foodRepository.deleteById(food_id);
    }

    
    @Transactional(readOnly = true)
    public Food searchById(Long food_id) {
        Optional<Food> foodOptional = foodRepository.findById(food_id);
        return foodOptional.orElse(null);
    }

    @Transactional
    public void edit(Long food_id,byte[] image, String title, String description, Double price, Long category_id){
        Category category = categoryRepository.findById(category_id).orElse(null);
        foodRepository.edit(food_id,image,title,description,price,category);
    }
    
    
}
