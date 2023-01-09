package ec.edu.espe.ajjacome2.minegocio.repository;

import ec.edu.espe.ajjacome2.minegocio.model.Address;
import ec.edu.espe.ajjacome2.minegocio.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long>, JpaSpecificationExecutor<Address> {
    @Query("SELECT a FROM Address a WHERE a.user.id = ?1 ORDER BY a.id ASC")
    List<Address> findByUserId(Long id);
}
