package com.agenda.AgendaContatoShared.dto.usuario;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioCriadoDTO {
    @NotNull(message = "Campo username não pode ser null!")
    @NotBlank(message = "Campo username é obrigatório!")
    private String username;

    @NotNull(message = "Campo email não pode ser null!")
    @NotBlank(message = "Campo email é obrigatório!")
    private String email;

    @NotNull(message = "Campo telefone não pode ser null!")
    @NotBlank(message = "Campo telefone é obrigatório!")
    private String telefone;

    @NotNull(message = "Campo senha não pode ser null!")
    @NotBlank(message = "Campo senha é obrigatório!")
    @Size(min = 10, message = "A senha deve ter no mínimo 10 caracteres")
    private String senha;
}
