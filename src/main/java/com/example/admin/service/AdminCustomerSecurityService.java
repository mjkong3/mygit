//package com.example.admin.service;
//
//import com.example.pet.dto.CustomerDto;
//import com.example.pet.mapper.CustomerMapper;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//@Service
//@RequiredArgsConstructor
//public class AdminCustomerSecurityService implements UserDetailsService {
//
//    private final CustomerMapper customerMapper;
//
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//
//        CustomerDto dto = customerMapper.loginCustomer(username);
//
//        if(dto == null){
//            throw new UsernameNotFoundException("사용자를 찾을 수 없습니다");
//        }
//
//        System.out.println(dto.getCustPw());
//
//        return User.builder()
//                .username(dto.getCustId())
//                .password(dto.getCustPw())
//                .roles(dto.getRole().toString())
//                .build();
//    }
//}
