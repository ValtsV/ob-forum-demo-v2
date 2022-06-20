package com.valts.obforumdemov2.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class LoginResponse {
    private Long id;
    private String avatar;
    private String username;
    private List<String> roles;
}
