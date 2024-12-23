package com.huy.aristino.controller;

import com.huy.aristino.model.Cart;
import com.huy.aristino.model.CartItem;
import com.huy.aristino.service.CartItemService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@CrossOrigin
@RestController
@RequestMapping("api/cartItem")
public class CartItemController {
    private CartItemService cartItemService;

    @PostMapping
    public ResponseEntity<CartItem> addCartItem(@RequestBody CartItem cartItem) {
        CartItem cartItemResult = cartItemService.addCartItem(cartItem);
        return new ResponseEntity<>(cartItemResult, HttpStatus.OK);
    }

    @PutMapping("update")
    public ResponseEntity<CartItem> updateCartItem(@RequestBody CartItem cartItem) {
        CartItem cartItemResult = cartItemService.updateCartItem(cartItem);
        return new ResponseEntity<>(cartItemResult, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteCartItem(@PathVariable("id") int id) {
        cartItemService.deleteCartItem(id);
        return new ResponseEntity<>("Xóa thành công sản phẩm khỏi giỏ hàng",HttpStatus.OK);
    }

    @GetMapping("get-all")
    public ResponseEntity<?> getCartItems(@RequestParam("cartId") int cartId) {
        List<CartItem> cartItems = cartItemService.getCartItemsByCartId(cartId);
        if(cartItems.isEmpty()) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", "Giỏ hàng đang trống");
            return new ResponseEntity<>(errorResponse,HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(cartItems, HttpStatus.OK);
    }


}
