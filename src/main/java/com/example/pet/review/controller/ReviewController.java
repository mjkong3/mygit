package com.example.pet.review.controller;


import com.example.pet.FileService;
import com.example.pet.category.dto.CategoryDto;
import com.example.pet.category.service.CategoryService;
import com.example.pet.config.PageHandler;
import com.example.pet.customer.dto.CustomerDto;
import com.example.pet.customer.service.CustomerService;
import com.example.pet.order.dto.OrderDetailDto;
import com.example.pet.item.service.ItemService;
import com.example.pet.order.service.OrderService;
import com.example.pet.review.dto.ReviewDto;
import com.example.pet.review.form.ReviewForm;
import com.example.pet.review.service.ReviewService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ReviewController {

    @Value("${reviewImgLocation}")
    private String reviewImgLocation;

    private final ReviewService reviewService;
    private final FileService fileService;
    private final CustomerService customerService;
    private final ItemService itemService;
    private final OrderService orderService;
    private final CategoryService categoryService;

    @GetMapping("/review/new/{orderDetailNum}")
    public String reviewForm(Model model,
                             @PathVariable Long orderDetailNum,
                             RedirectAttributes redirectAttributes,
                             HttpServletRequest request,
                             Principal principal){

        if (principal == null) {
            model.addAttribute("loginErrorMsg",
                                "로그인 후 사용가능합니다.");
            return "login";
        }
        List<CategoryDto> categories = categoryService.cateList();
        model.addAttribute("categories", categories);

        ReviewDto reviewDto = reviewService.findRV(orderDetailNum);

        //이미 리뷰가 작성된 주문
        if(reviewDto != null){
            CustomerDto customerDto = customerService.loginId(principal.getName());
            model.addAttribute("customer", customerDto);

            ReviewDto reviewDto1 = reviewService.selectRV(reviewDto.getReviewNum());
            model.addAttribute("errorMessage",
                                "이미 작성된 리뷰");
            model.addAttribute("review", reviewDto1);
            return "review/reviewDtl";
        }
        //작성되지 않은 주문
        model.addAttribute("reviewForm", new ReviewForm());
        model.addAttribute("orderDetailNum", orderDetailNum);
        return "/review/reviewForm";
    }

    // 리뷰 등록
    @PostMapping("/review/new/{orderDetailNum}")
    public String reviewNew(@ModelAttribute ReviewForm reviewForm,
                            @PathVariable("orderDetailNum") Long orderDetailNum,
                            BindingResult bindingResult,
                            Model model,
                            RedirectAttributes redirectAttributes,
                            Principal principal) {

        System.out.println("reviewController (reviewNew 실행)");
        List<CategoryDto> categories = categoryService.cateList();
        model.addAttribute("categories", categories);



        System.out.println(orderDetailNum);
        if (bindingResult.hasErrors()) {
            model.addAttribute("errorMessage", "리뷰 등록중 오류발생");
            System.out.println("에러발생");
            return "review/reviewForm.html";
        }

        try {
            ReviewDto reviewDto = new ReviewDto();
            reviewDto.setOrderDetailNum(orderDetailNum);
            reviewDto.setReviewTitle(reviewForm.getTitle());
            reviewDto.setReviewContent(reviewForm.getContent());

            OrderDetailDto orderDetailDto = orderService.findOrderItem(orderDetailNum);
            reviewDto.setItemNum(orderDetailDto.getItemNum());

            CustomerDto customerDto = customerService.loginId(principal.getName());
            reviewDto.setCustNum(customerDto.getCustNum());

            MultipartFile reviewImgFile = reviewForm.getImg();
            if (!reviewImgFile.isEmpty()) {
                String originalFilename = reviewImgFile.getOriginalFilename();
                String savedFilename = fileService.uploadFile(reviewImgLocation, originalFilename, reviewImgFile.getBytes());
                reviewDto.setReviewImg("/images/review/" + savedFilename);
            }

            reviewService.addRV(reviewDto);
            System.out.println(reviewForm.toString());
            redirectAttributes.addFlashAttribute("newReview", "공지사항 등록 완료");

        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("errorMessage", "공지사항 등록중 오류발생");
        }

        return "redirect:/review";

    }

    // 리뷰 상세보기
    @GetMapping("/review/{reviewNum}")
    public String reviewDtl(@PathVariable int reviewNum, Model model, Principal principal) {

        List<CategoryDto> categories = categoryService.cateList();
        model.addAttribute("categories", categories);

        try {
            // 클릭된 공지의 번호를 통해 조회수 증가
            //reviewService.increaseRVHit(reviewNum);
            ReviewDto reviewDto = reviewService.selectRV(reviewNum);
            model.addAttribute("review", reviewDto);

            if (principal != null) { // 로그인한 사용자가 있는 경우
                CustomerDto customerDto = customerService.loginId(principal.getName());
                model.addAttribute("customer", customerDto);
                System.out.println(customerDto);
            } else{
                model.addAttribute("customer", null);

            }

            System.out.println(reviewDto);
        } catch (NullPointerException e) {
            model.addAttribute("errorMessage", "존재하지 않는 공지입니다");
            return "redirect:/review";
        }
        return "review/reviewDtl.html";
    }



    // 리뷰 삭제
    @GetMapping("/review/delete/{reviewNum}")
    public String reviewDelete(@PathVariable("reviewNum") int reviewNum,
                               Model model,
                               RedirectAttributes redirectAttributes) {

        try {
            reviewService.deleteRV(reviewNum);
            redirectAttributes.addFlashAttribute("successMessage", "공지가 삭제되었습니다.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "공지 삭제 중 오류가 발생했습니다.");
        }
        return "redirect:/review";
    }

    // 리뷰 목록
    @GetMapping("/review")
    public String reviewList(Model model,
                             @RequestParam(defaultValue = "1") int page,
                             @RequestParam(defaultValue = "10") int pageSize){

        List<CategoryDto> categories = categoryService.cateList();
        model.addAttribute("categories", categories);


        int totalCnt = reviewService.RVListCount();
        int offset = (page - 1) * pageSize;

        List<ReviewDto> reviewDtoList = reviewService.RVList(pageSize, offset);

        PageHandler pageHandler = new PageHandler(totalCnt, pageSize, page);
        model.addAttribute("pageHandler", pageHandler);
        model.addAttribute("reviews", reviewDtoList);
        return "/review/review";
    }




/*    @Autowired
    public void setReviewService(ReviewService reviewService) {
        this.reviewService = reviewService;
    }*/

/*    // 리뷰 조회수 증가
    @PostMapping("/review/increaseRVHit")
    @ResponseBody
    public String increaseRVHit(@RequestParam("reviewNum") int reviewNum) {
        reviewService.increaseRVHit(reviewNum);
        return "성공";
    }*/
}
