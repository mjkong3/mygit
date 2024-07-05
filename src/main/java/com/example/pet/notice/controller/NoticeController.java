package com.example.pet.notice.controller;

import com.example.pet.category.dto.CategoryDto;
import com.example.pet.category.service.CategoryService;
import com.example.pet.config.PageHandler;
import com.example.pet.notice.dto.NoticeDto;
import com.example.pet.notice.form.NoticeForm;
import com.example.pet.notice.service.NoticeService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class NoticeController {

    private final NoticeService noticeService;
    private final CategoryService categoryService;


    @GetMapping("/notice/new")
    public String noticeForm(Model model, HttpServletRequest request, Principal principal){

        List<CategoryDto> categorys = categoryService.cateList();
        model.addAttribute("categories", categorys);

        if (principal == null) {
            model.addAttribute("loginErrorMsg","로그인 후 사용가능합니다.");
            return "/login";
        }

        model.addAttribute("noticeForm",new NoticeForm());

        return "/notice/noticeForm.html";
    }

    // 공지 등록
    @PostMapping("/notice/new")
    public String noticeNew(@Valid NoticeForm noticeForm,
                            BindingResult bindingResult,
                            Model model,
                            RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("errorMessage",
                                "공지사항 등록중 오류발생");
            return "/notice/noticeForm.html";
        }
        try {
            NoticeDto noticeDto = new NoticeDto();
            noticeDto.setNoticeTitle(noticeForm.getTitle());
            noticeDto.setNoticeContent(noticeForm.getContent());
            noticeService.addNT(noticeForm);

            redirectAttributes.addFlashAttribute("completeMessage",
                                                "공지사항 등록 완료");
        } catch (IllegalStateException e) {
            e.printStackTrace();
            model.addAttribute("errorMessage",
                                "공지사항 등록중 오류발생");
        }

        return "redirect:/notice";
    }



    //공지 상세보기
    @GetMapping("/notice/{noticeNum}")
    public String noticeDtl(@PathVariable int noticeNum, Model model, Principal principal) {
        if (principal == null) {
            model.addAttribute("loginErrorMsg","로그인 후 사용가능합니다.");
            return "/login";
        }
        List<CategoryDto> categorys = categoryService.cateList();
        model.addAttribute("categories", categorys);

        try {
            // 클릭된 공지의 번호를 통해 조회수 증가
            noticeService.increaseHit(noticeNum);
            NoticeDto noticeDto = noticeService.selectNT(noticeNum);
            model.addAttribute("notice", noticeDto);
        } catch (NullPointerException e) {
            model.addAttribute("errorMessage", "존재하지 않는 공지입니다");
            return "redirect:/notice";
        }
        return "/notice/noticeDtl.html";
    }



    //공지 수정
    @PostMapping("/notice/{noticeNum}")
    public String noticeUpdate(@PathVariable int noticeNum,
                               NoticeForm noticeForm,
                               Model model, RedirectAttributes redirectAttributes){

        Map<String, Object> map = new HashMap<>();
        map.put("noticeTitle", noticeForm.getTitle());
        map.put("noticeContent", noticeForm.getContent());
        map.put("noticeNum", noticeNum);

        try{
            noticeService.updateNT(map);
            redirectAttributes.addFlashAttribute("completeMessage",
                                                 "수정 완료");
        }catch (NullPointerException e){
            redirectAttributes.addFlashAttribute("errorMessage",
                                                 "공지 수정중 오류발생");
            return "redirect:/notice";
        }
        return "redirect:/notice";
    }



    // 공지 삭제
    @GetMapping("/notice/delete/{noticeNum}")
    public String noticeDelete(@PathVariable int noticeNum,
                               Model model,
                               RedirectAttributes redirectAttributes) {
        try {
            noticeService.deleteNT(noticeNum);
            redirectAttributes.addFlashAttribute("successMessage", "공지가 삭제되었습니다.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "공지 삭제 중 오류가 발생했습니다.");
        }
        return "redirect:/notice";
    }



    //공지 목록
    @GetMapping("/notice")
    public String noticeList(Model model,
                             @RequestParam(defaultValue = "1") int page,
                             @RequestParam(defaultValue = "10") int pageSize){

        List<CategoryDto> categorys = categoryService.cateList();
        model.addAttribute("categories", categorys);

        int totalCnt = noticeService.listNTAllCount();
        int offset = (page - 1) * pageSize;
        List<NoticeDto> noticeDtoList = noticeService.NTList(pageSize, offset);

        PageHandler pageHandler = new PageHandler(totalCnt, pageSize, page);
        model.addAttribute("pageHandler", pageHandler);
        model.addAttribute("notices", noticeDtoList);

        return "/notice/notice";
    }



    // 공지 조회수 증가
    @PostMapping("/notice/increaseHit")
    @ResponseBody
    public String increaseHit(@RequestParam("noticeNum") int noticeNum) {
        noticeService.increaseHit(noticeNum);
        return "성공";
    }


}
