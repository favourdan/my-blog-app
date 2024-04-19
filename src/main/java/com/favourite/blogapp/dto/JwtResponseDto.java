package com.favourite.blogapp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class JwtResponseDto {
    private String accessToken;
    private String tokenType = "Bearer";

}
