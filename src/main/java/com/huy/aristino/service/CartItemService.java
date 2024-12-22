package com.huy.aristino.service;

import com.huy.aristino.model.Cart;
import com.huy.aristino.model.CartItem;

import java.util.List;

public interface CartItemService {

    CartItem addCartItem(CartItem cartItem);

    CartItem updateCartItem(CartItem cartItem);

    void deleteCartItem(int id);

    List<CartItem> getCartItemsByCartId(int cartId);

    CartItem findCartItemById(int id);
}
