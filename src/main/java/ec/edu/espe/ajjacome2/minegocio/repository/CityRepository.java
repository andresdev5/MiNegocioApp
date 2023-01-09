package ec.edu.espe.ajjacome2.minegocio.repository;

import ec.edu.espe.ajjacome2.minegocio.model.Address;
import ec.edu.espe.ajjacome2.minegocio.model.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface CityRepository extends JpaRepository<City, Long>, JpaSpecificationExecutor<City> {

}
