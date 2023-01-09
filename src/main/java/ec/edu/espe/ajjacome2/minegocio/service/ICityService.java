package ec.edu.espe.ajjacome2.minegocio.service;

import ec.edu.espe.ajjacome2.minegocio.model.City;

public interface ICityService {
    City get(Long id);
    City save(City city);
}
