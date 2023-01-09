package ec.edu.espe.ajjacome2.minegocio.service;

import ec.edu.espe.ajjacome2.minegocio.model.IdentificationType;
import ec.edu.espe.ajjacome2.minegocio.model.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface IUserService {
    List<User> findAll();
    List<User> get(Specification<User> specification, Sort sort);
    User save(User user);
    void deleteById(Long id);
    User findById(Long id);
    boolean existsByIdentification(IdentificationType type, String value);
    boolean existsByEmail(String email);
    boolean existsByIdentificationExceptId(Long id, IdentificationType type, String value);
    boolean existsByEmailExceptId(Long id, String email);
}
