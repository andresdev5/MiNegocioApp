package ec.edu.espe.ajjacome2.minegocio.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AddressDto {
    @NotNull
    @Min(message = "El id de la provincia no puede ser menor a 1", value = 1)
    private Long provinceId;

    @NotNull
    @Min(message = "El id de la ciudad no puede ser menor a 1", value = 1)
    private Long cityId;

    @NotNull
    @NotEmpty
    private String description;
}
