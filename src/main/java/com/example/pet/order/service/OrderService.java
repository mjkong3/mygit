package com.example.pet.order.service;

import com.example.pet.cart.dto.CartDetailDto;
import com.example.pet.cart.service.CartService;
import com.example.pet.constant.OrderStatus;
import com.example.pet.customer.dto.CustomerDto;
import com.example.pet.customer.service.CustomerService;
import com.example.pet.exception.OutOfStockException;
import com.example.pet.order.dto.OrderDetailDto;
import com.example.pet.order.dto.OrderDto;
import com.example.pet.order.form.OrderForm;
import com.example.pet.item.dto.ItemDto;
import com.example.pet.item.service.ItemService;
import com.example.pet.order.mapper.OrderMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderMapper orderMapper;
    private final CustomerService customerService;
    private final CartService cartService;
    private final ItemService itemService;

    //order 테이블에 추가
    public OrderDto insertOrder(OrderForm orderForm, String id){

        OrderDto orderDto = new OrderDto();
        CustomerDto customerDto = customerService.loginId(id);
        orderDto.setCustNum(customerDto.getCustNum());

        if (orderForm.getOrderReq() == null || orderForm.getOrderReq().trim().isEmpty()) {
            orderDto.setOrderReq("조심히 천천히 와주세요");
        }else{
            orderDto.setOrderReq(orderForm.getOrderReq());
        }

        orderDto.setAddress(orderForm.getAddress());
        orderMapper.insertOrder(orderDto);
        return orderDto;

    }

    //order_detail 테이블에 추가
    public List<OrderDetailDto> insertDetail(OrderForm orderForm, OrderDto orderDto){
        List<OrderDetailDto> list = new ArrayList<>();

        for (Long cartItemNum : orderForm.getCartItemNum()) {
            CartDetailDto cartDetailDto = cartService.selectItem(cartItemNum);
            OrderDetailDto orderDetailDto = new OrderDetailDto();
            orderDetailDto.setOrderNum(orderDto.getOrderNum());
            orderDetailDto.setItemNum(cartDetailDto.getItemNum());
            orderDetailDto.setItemCnt(cartDetailDto.getCount());
            orderDetailDto.setOrderStatus(OrderStatus.ORDER);
            orderDetailDto.setPrice(cartDetailDto.getItemPrice() * cartDetailDto.getCount());

            // 주문하면 item 테이블에 재고가 감소해야 함
            ItemDto itemDto = itemService.selectItem(cartDetailDto.getItemNum());
            itemDto.setItemCnt(itemDto.getItemCnt() - cartDetailDto.getCount());
            int cnt = itemDto.getItemCnt();
            String itemName = itemDto.getItemName();

            Long custNum = orderDto.getCustNum();
            CustomerDto cust = customerService.selectCustomer(custNum);

            //customer 테이블 total_price 업데이트
            Map<String, Object> map = new HashMap<>();
            int totalPrice = orderDetailDto.getPrice();
            map.put("totalPrice", cust.getTotalPrice() + totalPrice);
            map.put("custNum", cust.getCustNum());

            if (cnt < 0) {
                throw new OutOfStockException("'" + itemName + "' 의 재고가 부족합니다 : " + -cnt + "개 부족");
            } else {
                orderMapper.insertDetail(orderDetailDto);
                customerService.updateTP(map);
                list.add(orderDetailDto);
            }
            itemService.updateCnt(itemDto);

            // 주문하면 장바구니에서 해당 주문한 제품 정보 삭제
            cartService.deleteCartItem(cartItemNum);

        }
        return list;
    }

    public List<OrderDetailDto> customerOrder(Long custNum){
        List<OrderDetailDto> list = orderMapper.customerOrder(custNum);
        return list;
    };

    public OrderDetailDto findOrderItem(Long orderDetailNim){
        return orderMapper.findOrderItem(orderDetailNim);
    };



}
