package com.valts.obforumdemov2.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class UserAuthDTO {
    private String email;
    private String password;

}
