package com.example.pet.cart.service;

import com.example.pet.cart.dto.CartDetailDto;
import com.example.pet.cart.dto.CartDto;
import com.example.pet.cart.dto.CartItemDto;
import com.example.pet.cart.form.CartForm;
import com.example.pet.cart.mapper.CartMapper;
import com.example.pet.customer.dto.CustomerDto;
import com.example.pet.item.dto.ItemDto;
import com.example.pet.customer.mapper.CustomerMapper;
import com.example.pet.item.mapper.ItemMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartMapper cartMapper;
    private final CustomerMapper customerMapper;
    private final ItemMapper itemMapper;

    //장바구니 추가
    public Long addCart(CartForm cartForm, String id){
        //로그인한 cust_id 검색
        CustomerDto customerDto = customerMapper.loginCustomer(id);
        Long custNum = customerDto.getCustNum();

        if(custNum == null){
            throw new IllegalArgumentException("id를 찾을 수 없습니다.");
        }

        CartDto cart = createCartExists(custNum);

        return addItemToCart(cart, cartForm);
    }

    //장바구니있는지 확인
    public CartDto createCartExists(Long custNum){
        CartDto cart = cartMapper.findCartNum(custNum);

        if (cart == null){
            CartDto cartDto = new CartDto();
            cartDto.setCustNum(custNum);
            cartMapper.insertCart(custNum);
            cart = cartMapper.findCartNum(custNum);
        }

        return cart;
    }

    //장바구니에 상품 추가
    private Long addItemToCart(CartDto cart, CartForm cartForm){
        ItemDto itemDto = itemMapper.selectItem(cartForm.getItemNum());

        Map<String, Object> map = new HashMap<>();
        map.put("cartNum",cart.getCartNum());
        map.put("itemNum",itemDto.getItemNum());

        //장바구니 목록 조회
        CartItemDto cartItemDto = cartMapper.cartList(map);

        if(cartItemDto == null){
            cartItemDto = new CartItemDto();
            cartItemDto.setCartNum(cart.getCartNum());
            cartItemDto.setItemNum(itemDto.getItemNum());
            cartItemDto.setCount(cartForm.getCount());

            cartMapper.insertItem(cartItemDto);
        }else{
            cartItemDto.setCount(cartItemDto.getCount() + cartForm.getCount());
            map.put("count", cartItemDto.getCount());
            map.put("cartItemNum", cartItemDto.getCartItemNum());
            cartMapper.updateCount(map);
        }

        // 다시 장바구니 목록 조회하여 cartItemNum이 null인지 확인
        cartItemDto = cartMapper.cartList(map);

        // cartItemNum이 null이라면 다시 시도
        if (cartItemDto == null) {
            return addItemToCart(cart, cartForm);
        }

        return cartItemDto.getCartItemNum();
    }

    //장바구니 목록 확인
    public List<CartDetailDto> cartItemList(String id){
        CustomerDto customerDto = customerMapper.loginCustomer(id);
        Long custNum = customerDto.getCustNum();

        List<CartDetailDto> list = new ArrayList<>();

        CartDto cart = cartMapper.findCartNum(custNum);

        if (cart == null){
            return list;
        }

        list = cartMapper.listAll(cart.getCartNum());

        return list;
    }

    public CartDetailDto selectItem(Long cartItemNum){
        return cartMapper.selectItem(cartItemNum);
    }

    public void updateCartItemCount(Long cartItemNum, int count){

        CartItemDto cartItemDto = cartMapper.findCartItem(cartItemNum);

        if(cartItemDto == null){
            throw new IllegalArgumentException("상품이 존재하지 않습니다!");
        }

        Map<String, Object> map = new HashMap();
        map.put("count", count);
        map.put("cartItemNum", cartItemNum);

        cartMapper.updateCount(map);
    }

    public void deleteCartItem(Long cartItemNum){

        CartItemDto cartItemDto = cartMapper.findCartItem(cartItemNum);

        if(cartItemDto == null){
            throw new IllegalArgumentException("상품이 존재하지 않습니다.");
        }

        cartMapper.deleteItem(cartItemNum);
    }
}
