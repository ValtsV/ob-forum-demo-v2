package com.valts.obforumdemov2.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class UserDTO {
    private Long userId;
    private String avatar;
    private String username;
}
