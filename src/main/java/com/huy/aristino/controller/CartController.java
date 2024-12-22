package com.huy.aristino.controller;

import com.huy.aristino.model.Cart;
import com.huy.aristino.service.CartService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@CrossOrigin
@RestController
@RequestMapping("api/cart")
public class CartController {
    private CartService cartService;
    @PostMapping
    public ResponseEntity<Cart> createCart(@RequestBody Cart cart) {
        Cart newCart = cartService.createCart(cart);
        return new ResponseEntity<>(newCart, HttpStatus.CREATED);
    }

    @PutMapping("update/{id}")
    public ResponseEntity<Cart> updateCart(@PathVariable("id") int id, @RequestBody Cart cart) {
        cart.setId(id);
        Cart updateCart = cartService.updateCart(cart);
        return new ResponseEntity<>(updateCart, HttpStatus.OK);
    }
}
