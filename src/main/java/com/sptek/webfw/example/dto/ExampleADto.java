package com.sptek.webfw.example.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExampleADto {
    private String aDtoFirstName;
    private String aDtoLastName;
}
