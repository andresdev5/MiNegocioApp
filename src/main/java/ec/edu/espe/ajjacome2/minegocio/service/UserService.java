package ec.edu.espe.ajjacome2.minegocio.service;

import ec.edu.espe.ajjacome2.minegocio.model.IdentificationType;
import ec.edu.espe.ajjacome2.minegocio.model.User;
import ec.edu.espe.ajjacome2.minegocio.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements IUserService {
    private final UserRepository repository;

    @Autowired
    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<User> findAll() {
        return repository.findAll();
    }

    @Override
    public List<User> get(Specification<User> specification, Sort sort) {
        return repository.findAll(specification, sort);
    }

    @Override
    public User findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public boolean existsByIdentificationExceptId(Long id, IdentificationType type, String value) {
        return repository.findDistinctIdByIdentification(id, type, value).isPresent();
    }

    @Override
    public boolean existsByEmailExceptId(Long id, String email) {
        return repository.findDistinctIdByEmail(id, email).isPresent();
    }

    @Override
    public boolean existsByEmail(String email) {
        return repository.findByEmail(email).isPresent();
    }

    @Override
    public boolean existsByIdentification(IdentificationType type, String value) {
        return repository.findByIdentification(type, value).isPresent();
    }

    @Override
    public User save(User user) {
        return repository.save(user);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
