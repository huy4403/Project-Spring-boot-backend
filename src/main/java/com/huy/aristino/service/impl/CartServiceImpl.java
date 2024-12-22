package com.huy.aristino.service.impl;

import com.huy.aristino.model.Cart;
import com.huy.aristino.repository.CartRepository;
import com.huy.aristino.service.CartService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CartServiceImpl implements CartService {
    private CartRepository cartRepository;

    @Override
    public Cart createCart(Cart cart) {
        return cartRepository.save(cart);
    }

    @Override
    public Cart updateCart(Cart cart) {
        Cart existCart = cartRepository.findByUserId(cart.getUser().getId());
        existCart.setTotal(cart.getTotal());
        Cart updateCart = cartRepository.save(existCart);
        return updateCart;
    }
}
