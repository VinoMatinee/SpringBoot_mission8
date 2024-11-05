package com.ucamp.jpa.dto.req;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerReqForm {
    private Long id;
    
    @NotEmpty(message = "name은 필수 입력 항목입니다.")
    private String name;

    @NotEmpty(message = "email은 필수 입력 항목입니다.")
    private String email;

    @NotNull(message="나이를 입력해 주세요.")
    @Min(value=10, message="나이는 최소한 10세이상으로 입력하세요.")
    private int age;
}
