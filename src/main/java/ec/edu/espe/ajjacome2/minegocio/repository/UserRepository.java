package ec.edu.espe.ajjacome2.minegocio.repository;

import ec.edu.espe.ajjacome2.minegocio.model.IdentificationType;
import ec.edu.espe.ajjacome2.minegocio.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {
    /**
     * Obtiene una lista de usuarios que no tengan un id especificado
     *
     * @param id id a excluir
     * @return lista de usuarios
     */
    @Query("SELECT u FROM User u WHERE u.id NOT IN ?1")
    List<User> findDistinctById(Long id);

    /**
     * Obtiene un usuario por su identificacion
     *
     * @param type tipo de identificacion
     * @param identificationValue valor de identificacion
     * @return usuario encontrado
     */
    @Query("SELECT u FROM User u WHERE u.identificationType = ?1 AND u.identificationValue = ?2")
    Optional<User> findByIdentification(IdentificationType type, String identificationValue);

    /**
     * Obtiene un usuario por su email
     *
     * @param email email del usuario
     * @return usuario encontrado
     */
    @Query("SELECT u FROM User u WHERE u.email = ?1")
    Optional<User> findByEmail(String email);

    /**
     * Obtiene un usuario por su identificacion siempre y cuando el id sea diferente al especificado
     *
     * @param id id a excluir
     * @param identificationType tipo de identificacion
     * @param identificationValue valor de la identificacion
     * @return usuario encontrado
     */
    @Query("SELECT u FROM User u WHERE u.id = ?1 AND u.identificationType = ?2 AND u.identificationValue = ?3")
    Optional<User> findDistinctIdByIdentification(
        Long id,
        IdentificationType identificationType,
        String identificationValue
    );

    /**
     * Obtiene un usuario por su email siempre y cuando el id sea diferente al especificado
     *
     * @param id id a excluir
     * @param email email del usuario
     * @return usuario encontrado
     */
    @Query("SELECT u FROM User u WHERE u.id = ?1 AND u.email = ?2")
    Optional<User> findDistinctIdByEmail(Long id, String email);
}
