package ec.edu.espe.ajjacome2.minegocio.dto;

import ec.edu.espe.ajjacome2.minegocio.model.User;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserWithAddressDto extends UserDto {
    @NotNull
    @Min(message = "El id de la provincia no puede ser menor a 1", value = 1)
    private Long addressProvinceId;

    @NotNull
    @Min(message = "El id de la ciudad no puede ser menor a 1", value = 1)
    private Long addressCityId;

    @NotNull
    @NotEmpty
    private String addressDescription;

    public User toUser() {
        User user = new User();
        user.setId(this.getId());
        user.setIdentificationType(this.getIdentificationType());
        user.setIdentificationValue(this.getIdentificationValue());
        user.setNames(this.getNames());
        user.setEmail(this.getEmail());
        user.setPhone(this.getPhone());
        return user;
    }
}
