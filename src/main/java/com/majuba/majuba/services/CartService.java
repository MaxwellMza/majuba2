package com.majuba.majuba.services;

import com.majuba.majuba.entities.*;
import com.majuba.majuba.repositories.CartRepository;
import com.majuba.majuba.repositories.FoodRepository;
import com.majuba.majuba.repositories.OrderRepository;
import com.majuba.majuba.repositories.TableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CartService {

    @Autowired
    private FoodRepository foodRepository;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private TableRepository tableRepository;
    @Autowired
    private OrderRepository orderRepository;

    @Transactional
    public void transformDTO(Long table_id, List<FoodDTO> foodDTOS) throws Exception {
        Order order = new Order();
        Optional<Table> table = tableRepository.findById(table_id);
        order.setTable(table.orElse(null));
        order.setClient(null);
        order = orderRepository.save(order);
        Double tot = 0.0;
        Cart cart = null;
        for (FoodDTO foodDTO : foodDTOS) {
            cart = new Cart();
            Optional<Food> foodOptional = foodRepository.findById(foodDTO.getFood_id());
            Food food = foodOptional.orElseThrow(Exception::new);
            cart.setFood(food);
            cart.setAmount(foodDTO.getAmount());
            Double sub = food.getPrice() * foodDTO.getAmount();
            tot = tot + sub;
            cart.setSubtotal(sub);
            cart.setOrder(order);
            cartRepository.save(cart);
        }
        cart.getOrder().setTotal(tot);
    }
}