package ec.edu.espe.ajjacome2.minegocio.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import ec.edu.espe.ajjacome2.minegocio.Application;
import ec.edu.espe.ajjacome2.minegocio.model.IdentificationType;
import ec.edu.espe.ajjacome2.minegocio.model.User;
import ec.edu.espe.ajjacome2.minegocio.service.AddressService;
import ec.edu.espe.ajjacome2.minegocio.service.CityService;
import ec.edu.espe.ajjacome2.minegocio.service.IAddressService;
import ec.edu.espe.ajjacome2.minegocio.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static  org.assertj.core.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = Application.class)
@AutoConfigureMockMvc
@AutoConfigureWebTestClient
@TestPropertySource(locations = "classpath:application-test.properties")
@Sql(scripts = "classpath:test-postgres.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class UsersControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @TestConfiguration
    static class TestConfigurationApp {
        @Bean
        ObjectMapper objectMapperPrettyPrinting() {
            return JsonMapper.builder()
                .enable(SerializationFeature.INDENT_OUTPUT)
                .addModule(new JavaTimeModule())
                .build();
        }
    }

    @Test
    @DisplayName("Test GET /api/users")
    public void testGetUsers() throws Exception {
        List<User> mockUsers = new ArrayList<>();
        User mockUser = new User();

        mockUser.setId(1L);
        mockUser.setIdentificationType(IdentificationType.CI);
        mockUser.setIdentificationValue("1726744293");
        mockUser.setNames("John Doe");
        mockUser.setEmail("johndoe@unexustech.com");
        mockUser.setPhone("0987654321");
        mockUsers.add(mockUser);

        Mockito.when(userService.get(Mockito.any(), Mockito.any())).thenReturn(mockUsers);

        mockMvc.perform(
                get("/api/users")
                .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$[0].id").value(1))
            .andExpect(jsonPath("$[0].identificationValue").value("1726744293"));
    }

    @Test
    @DisplayName("Test GET /api/users/{id}")
    public void testGetUser() throws Exception {
        User mockUser = new User();

        mockUser.setId(1L);
        mockUser.setIdentificationType(IdentificationType.CI);
        mockUser.setIdentificationValue("1726744293");
        mockUser.setNames("John Doe");
        mockUser.setEmail("johndoe@gmail.com");
        mockUser.setPhone("0987654321");

        Mockito.when(userService.findById(Mockito.anyLong())).thenReturn(mockUser);

        mockMvc.perform(
                get("/api/users/1")
                .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.identificationValue").value("1726744293"));
    }

    @Test
    @DisplayName("Test DELETE /api/users/{id}")
    public void testDeleteUser() throws Exception {
        mockMvc.perform(delete("/api/users/2"))
            .andExpect(status().isOk())
            .andDo(print());

        assertThat(userService.findById(2L)).isNull();
    }
}
