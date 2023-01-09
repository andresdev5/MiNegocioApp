package ec.edu.espe.ajjacome2.minegocio.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import static  org.assertj.core.api.Assertions.*;

@TestPropertySource(locations = "classpath:application-test.properties")
@Sql(scripts = "classpath:test-postgres.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CityRepositoryTest {
    @Autowired
    private CityRepository cityRepository;

    @Test
    public void testGetCities() {
        assertThat(cityRepository.findAll()).isNotEmpty();
    }
}
