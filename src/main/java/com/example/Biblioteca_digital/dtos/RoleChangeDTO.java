package com.example.Biblioteca_digital.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class RoleChangeDTO {

    @NotNull(message = "O ID não pode ser nulo.")
    private Long id;

    @NotBlank(message = "A nova Role não pode estar em branco.")
    private String role;
}
