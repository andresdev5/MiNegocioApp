package ec.edu.espe.ajjacome2.minegocio.controller;

import ec.edu.espe.ajjacome2.minegocio.dto.AddressDto;
import ec.edu.espe.ajjacome2.minegocio.dto.UserDto;
import ec.edu.espe.ajjacome2.minegocio.dto.UserWithAddressDto;
import ec.edu.espe.ajjacome2.minegocio.helpers.ResponseHandler;
import ec.edu.espe.ajjacome2.minegocio.model.*;
import ec.edu.espe.ajjacome2.minegocio.service.IAddressService;
import ec.edu.espe.ajjacome2.minegocio.service.ICityService;
import ec.edu.espe.ajjacome2.minegocio.service.IUserService;
import jakarta.validation.Valid;
import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.domain.LikeIgnoreCase;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Controlador para manejar las peticiones API REST de los usuarios
 *
 */
@RestController
@RequestMapping("/api/users")
public class UsersController {
    private final IUserService userService;
    private final IAddressService addressService;
    private final ICityService cityService;

    @Autowired
    public UsersController(
            IUserService userService,
            IAddressService addressService,
            ICityService cityService) {
        this.userService = userService;
        this.addressService = addressService;
        this.cityService = cityService;
    }

    /**
     * Obtiene todos los usuarios y opcionalmente puede recibir parametros para filtrar o buscar.
     *
     * @param specification
     * @param pageable
     *
     * @return lista de usuarios
     */
    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<User> getUsers(
        @And({
            @Spec(path = "identificationValue", params = "identificationValue", spec = Equal.class),
            @Spec(path = "names", params = "names", spec = LikeIgnoreCase.class),
        }) Specification<User> specification, Pageable pageable) {
        return userService.get(specification, Sort.by(Sort.Direction.ASC, "id"));
    }

    /**
     * Obtiene un usuario por su id.
     *
     * @param id id del usuario a obtener
     * @return usuario con el id especificado
     */
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public User getUser(@PathVariable Long id) {
        return userService.findById(id);
    }

    /**
     * Obtiene las direcciones de un usuario por su id
     *
     * @param id id del usuario
     * @return lista de direcciones del usuario especificado
     */
    @GetMapping(value = "/{id}/addresses", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Address> getUserAddresses(@PathVariable Long id) {
        return addressService.findByUserId(id);
    }

    /**
     * Inserta un nuevo usuario.
     *
     * @param userDto datos del usuario a insertar
     * @return usuario insertado
     */
    @PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> saveUser(@Valid @RequestBody UserWithAddressDto userDto) {
        if (userService.existsByIdentification(userDto.getIdentificationType(), userDto.getIdentificationValue())) {
            return ResponseHandler.response(
                "El número de identificación ya está en uso",
                HttpStatus.BAD_REQUEST
            );
        }

        if (userService.existsByEmail(userDto.getEmail())) {
            return ResponseHandler.response(
                "El correo electrónico ya está en uso",
                HttpStatus.BAD_REQUEST
            );
        }

        long cityId = userDto.getAddressCityId();
        long provinceId = userDto.getAddressProvinceId();
        String addressDescription = userDto.getAddressDescription();

        User savedUser = userService.save(userDto.toUser());
        City city = cityService.get(cityId);
        Province province = city.getProvince();

        if (province.getId() != provinceId) {
            return ResponseHandler.response(
                "La ciudad no pertenece a la provincia especificada, se esperaba el id: " + province.getId(),
                HttpStatus.BAD_REQUEST
            );
        }

        Address address = new Address();
        address.setUser(savedUser);
        address.setCity(city);
        address.setProvince(province);
        address.setDescription(addressDescription);
        address.setIsMain(true);

        savedUser.addAddress(address);
        addressService.save(address);

        return ResponseEntity.ok(savedUser);
    }

    /**
     * Edita un usuario por su id.
     *
     * @param id id del usuario a editar
     * @param user datos del usuario a editar
     * @return usuario editado
     */
    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateUser(@PathVariable Long id, @Valid @RequestBody UserDto user) {
        User target = userService.findById(id);

        if (userService.existsByEmailExceptId(id, user.getEmail())) {
            return ResponseHandler.response(
                "El correo electrónico ya está en uso",
                HttpStatus.BAD_REQUEST
            );
        }

        boolean identificationExists = userService.existsByIdentificationExceptId(
            id,
            user.getIdentificationType(),
            user.getIdentificationValue()
        );

        if (identificationExists) {
            return ResponseHandler.response(
                "El número de identificación ya está en uso",
                HttpStatus.BAD_REQUEST
            );
        }

        target.setIdentificationType(user.getIdentificationType());
        target.setIdentificationValue(user.getIdentificationValue());
        target.setNames(user.getNames());
        target.setEmail(user.getEmail());
        target.setPhone(user.getPhone());

        User updatedUser = userService.save(target);

        return ResponseHandler.response(
            "Usuario actualizado correctamente",
            HttpStatus.OK,
            updatedUser
        );
    }

    /**
     * Elimina un usuario por id
     *
     * @param id id del usuario a eliminar
     * @return respuesta de la operación
     */
    @DeleteMapping("/{id}")
    ResponseEntity<Object> deleteEmployee(@PathVariable Long id) {
        userService.deleteById(id);
        return ResponseHandler.response("Usuario eliminado correctamente", HttpStatus.OK);
    }

    /**
     * Guarda una direccion para un usuario definiendo su id
     *
     * @param id id del usuario
     * @param addressDto datos de la direccion
     * @return direccion guardada
     */
    @PostMapping(value = "/{id}/addresses", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> saveAddress(@PathVariable Long id, @Valid @RequestBody AddressDto addressDto) {
        User user = userService.findById(id);
        City city = cityService.get(addressDto.getCityId());
        Province province = city.getProvince();

        if (!province.getId().equals(addressDto.getProvinceId())) {
            return ResponseHandler.response(
                "La ciudad no pertenece a la provincia especificada, se esperaba el id: " + province.getId(),
                HttpStatus.BAD_REQUEST
            );
        }

        Address address = new Address();
        address.setUser(user);
        address.setCity(city);
        address.setProvince(province);
        address.setDescription(addressDto.getDescription());
        address.setIsMain(false);

        Address savedAddress = addressService.save(address);

        return ResponseHandler.response(
            "Dirección agregada correctamente",
            HttpStatus.OK,
            savedAddress
        );
    }
}
