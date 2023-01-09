package ec.edu.espe.ajjacome2.minegocio.dto;

import ec.edu.espe.ajjacome2.minegocio.model.IdentificationType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserDto {
    private Long id;

    @NotNull
    private IdentificationType identificationType;

    @NotNull
    @NotEmpty
    private String identificationValue;

    @NotNull
    @NotEmpty
    private String names;

    @NotNull
    @NotEmpty
    @Email
    private String email;

    @NotNull
    @NotEmpty
    private String phone;
}
