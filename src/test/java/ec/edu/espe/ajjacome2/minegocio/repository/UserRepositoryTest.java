package ec.edu.espe.ajjacome2.minegocio.repository;

import ec.edu.espe.ajjacome2.minegocio.model.IdentificationType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;

@TestPropertySource(locations = "classpath:application-test.properties")
@Sql(scripts = "classpath:test-postgres.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    public void testGetUsers() {
        assertThat(userRepository.findAll()).isNotEmpty();
    }

    @Test
    public void testFindUserByIdentification() {
        assertThat(userRepository.findByIdentification(IdentificationType.CI, "1726744293")
                .orElse(null)).isNotNull();
    }

    @Test
    public void testFindUserByEmail() {
        assertThat(userRepository.findByEmail("johndoe@unexustech.com")
                .orElse(null)).isNotNull();
    }
}
