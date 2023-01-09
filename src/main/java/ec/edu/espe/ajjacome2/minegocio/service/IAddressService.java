package ec.edu.espe.ajjacome2.minegocio.service;

import ec.edu.espe.ajjacome2.minegocio.model.Address;

import java.util.List;

public interface IAddressService {
    List<Address> findByUserId(Long userId);
    Address save(Address address);
}
