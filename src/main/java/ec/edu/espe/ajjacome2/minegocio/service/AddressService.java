package ec.edu.espe.ajjacome2.minegocio.service;

import ec.edu.espe.ajjacome2.minegocio.model.Address;
import ec.edu.espe.ajjacome2.minegocio.repository.AddressRepository;
import ec.edu.espe.ajjacome2.minegocio.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressService implements IAddressService {
    private final AddressRepository repository;

    @Autowired
    public AddressService(AddressRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Address> findByUserId(Long userId) {
        return repository.findByUserId(userId);
    }

    @Override
    public Address save(Address address) {
        return repository.save(address);
    }
}
