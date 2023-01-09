package ec.edu.espe.ajjacome2.minegocio.service;

import ec.edu.espe.ajjacome2.minegocio.model.City;
import ec.edu.espe.ajjacome2.minegocio.repository.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CityService implements ICityService {
    private final CityRepository repository;

    @Autowired
    public CityService(CityRepository repository) {
        this.repository = repository;
    }

    @Override
    public City get(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public City save(City city) {
        return repository.save(city);
    }
}
