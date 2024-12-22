package com.huy.aristino.service.impl;

import com.huy.aristino.model.Cart;
import com.huy.aristino.model.CartItem;
import com.huy.aristino.repository.CartItemRepository;
import com.huy.aristino.service.CartItemService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CartItemServiceImpl implements CartItemService {
    private CartItemRepository cartItemRepository;

    @Override
    public CartItem addCartItem(CartItem cartItem) {
        CartItem addCartItem = cartItemRepository.save(cartItem);
        return addCartItem;
    }

    @Override
    public CartItem updateCartItem(CartItem cartItem) {
        CartItem existCartItem = findCartItemById(cartItem.getId());
        existCartItem.setQuantityBuy(cartItem.getQuantityBuy());
        CartItem updateCartItem = cartItemRepository.save(existCartItem);
        return updateCartItem;
    }

    @Override
    public void deleteCartItem(int id) {
        cartItemRepository.deleteById(id);
    }

    @Override
    public CartItem findCartItemById(int id){
        Optional<CartItem> cartItem = cartItemRepository.findById(id);
        return cartItem.get();
    }

    @Override
    public List<CartItem> getCartItemsByCartId(int cartId) {
        List<CartItem> cartItems = cartItemRepository.findByCartId(cartId);
        return cartItems;
    }
}
