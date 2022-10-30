package com.zhfvkq.dyshop.member.api;

import com.zhfvkq.dyshop.member.dto.MemberJoinForm;
import com.zhfvkq.dyshop.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberApiController {

    private final MemberService memberService;

    /**
     * 회원가입 api (글로벌 오류 전달 방법을 아직 못 찾아서 일단 안씀)
     * @param memberJoinForm
     * @param bindingResult
     * @return
     */
    @PostMapping("/api/signup")
    public ResponseEntity<BasicResponse> signup(@Valid @RequestBody MemberJoinForm memberJoinForm, BindingResult bindingResult){

        log.info("member api = {}", memberJoinForm.getUserId());

        // @RequestBody 로는 글로벌 오류 출력이 어려운걸로 보임..
        if(memberJoinForm.getUserId() != memberJoinForm.getConfirmId()){ // 중복 확인 아이디와 입력 아이디가 다름
            bindingResult.reject("notConfirm");
        }
        if(memberJoinForm.getPassword() != memberJoinForm.getRePassword()){
            bindingResult.reject("notPassword");
        }

        if(bindingResult.hasErrors()) {
            log.info("bindingResult = {} " , bindingResult);
            return ResponseEntity.status(HttpStatus.MULTIPLE_CHOICES).body(new BasicResponse(memberJoinForm));
        }

        // 회원가입
        memberService.memberJoin(memberJoinForm);
        return ResponseEntity.status(HttpStatus.OK).body(new BasicResponse(memberJoinForm));
    }

    /**
     * 아이디 중복 체크 api
     */
    @GetMapping("/api/{userChkId}/exists")
    public ResponseEntity<Boolean> checkUserIdDuplicate(@PathVariable String userChkId){
        log.info("userChkId == {}", userChkId);
        return ResponseEntity.ok(memberService.checkUserIdDuplicate(userChkId));
    }
}
