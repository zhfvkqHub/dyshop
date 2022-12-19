package com.zhfvkq.dyshop.ask.controller;

import com.zhfvkq.dyshop.ask.dto.AskForm;
import com.zhfvkq.dyshop.common.CustomErrorResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/ask")
public class AskController {

    /**
     * 비회원 문의 페이지
     * @param id
     * @param askForm
     * @return
     * @throws Exception
     */
    @GetMapping(value = {"/{id}", ""})
    public ResponseEntity<AskForm> ask(@PathVariable(required = false) Integer id, AskForm askForm) throws Exception{

        askForm.setGateType("계정관련");

        HttpStatus status = HttpStatus.OK;

        if(invalid(id)){
            status = HttpStatus.BAD_REQUEST;
        }else if(id == 1){
            askForm.setDtlType("OTP 초기화 신청");
        }else if(id == 2){
            askForm.setDtlType("로그인 문의");
        }
        return ResponseEntity.status(status.value()).body(askForm);
    }
    private static boolean invalid(Integer id) {
        return id == null || (id != 1 && id != 2);
    }

    /**
     * 1:1 문의 저장
     * @param askForm
     * @param bindingResult
     * @return
     */
    @PostMapping
    public ResponseEntity<Object> askSave(@Valid @RequestBody AskForm askForm, BindingResult bindingResult) throws Exception{

        if(bindingResult.hasErrors()){

            HttpStatus status = HttpStatus.BAD_REQUEST;

            List<ObjectError> allErrors = bindingResult.getAllErrors();
            String errorMessage = allErrors.get(0).getDefaultMessage();

            CustomErrorResponse errors = new CustomErrorResponse(errorMessage,status.value());

            return ResponseEntity.status(status).body(errors);

        }else{
            // TODO 저장 로직
//            User saveUser = userService.save(requestDto);
//            return ResponseEntity.status(HttpStatus.OK).body(saveUser);
            return ResponseEntity.status(HttpStatus.OK).body("");
        }

    }
    private String getValue(String aa){
        return Objects.nonNull(aa) ? aa : null;
    }
}
