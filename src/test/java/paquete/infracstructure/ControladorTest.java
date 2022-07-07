package paquete.infracstructure;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import paquete.domain.Persona;
import paquete.domain.PersonaRepositorio;
import org.assertj.core.api.Assertions;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import static org.springframework.http.HttpStatus.OK;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Slf4j
class ControladorTest {
    @Autowired
    private PersonaRepositorio personaRepositorio;
    @LocalServerPort
    int puerto;
    @Autowired
    private TestRestTemplate restTemplate;
    @BeforeEach
    void setUp()  throws Exception {

        Persona persona = new Persona(
                1,
                "pepe",
                "123",
                "el pepe",
                "aaaa",
                "aaaa@aaa.com",
                "eeee@eee.com",
                "o",
                true,
                null,
                "aaaaaaa.jpg",
                null);

        personaRepositorio.save(persona);

    }


    @Test
    void addPersona() {

        PersonaDTO personaDTO = new PersonaDTO(
                2,
                "johnny",
                "123",
                "el johnny",
                "aaaa",
                "aaaa@aaa.com",
                "eeee@eee.com",
                "o",
                true,
                null,
                "aaaaaaa.jpg",
                null);

        ResponseEntity<PersonaDTO> responseEntity = new RestTemplate().postForEntity("http://localhost:"+puerto+"/addPersona",personaDTO, PersonaDTO.class);

        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(OK);

    }

    @Test
    void updatePersona() throws URISyntaxException {

        PersonaDTO personaDTO = new PersonaDTO(
                1,
                "johnny",
                "123",
                "el johnny",
                "aaaa",
                "aaaa@aaa.com",
                "eeee@eee.com",
                "o",
                true,
                null,
                "aaaaaaa.jpg",
                null);

        personaDTO.setId(null);

        RequestEntity<PersonaDTO> request = RequestEntity.put("http://localhost:"+puerto+"/updatePersona/"+1).body(personaDTO);

        ParameterizedTypeReference<String> mensaje =
                new ParameterizedTypeReference<String>() {};
        ResponseEntity<String> responseEntity = restTemplate.exchange(request, mensaje);

        System.out.println(responseEntity);

        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(OK);
        Assertions.assertThat(responseEntity.getBody()).isEqualTo("Persona con id: "+1+" modificada.");


    }

    @Test
    void deletePersona() {

        RequestEntity<Void> request = RequestEntity.delete("http://localhost:"+puerto+"/deletePersona/"+1)
                .accept(MediaType.ALL).build();

        ParameterizedTypeReference<String> mensaje =
                new ParameterizedTypeReference<String>() {};
        ResponseEntity<String> responseEntity = restTemplate.exchange(request, mensaje);

        System.out.println(responseEntity);

        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(OK);
        Assertions.assertThat(responseEntity.getBody()).isEqualTo("Persona borrada");



    }

    @Test
    void getPersonaId() {

        ResponseEntity<PersonaDTO> responseEntity = new RestTemplate().getForEntity("http://localhost:"+puerto+"/getPersona/findId/" + 1 , PersonaDTO.class);

        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(OK);

    }

    @Test
    void getPersonaUsuario() throws URISyntaxException {

        RequestEntity<Void> request = RequestEntity.get(new URI("http://localhost:"+puerto+"/getPersona/findUser/pepe"))
                .accept(MediaType.APPLICATION_JSON).build();
        ParameterizedTypeReference<List<PersonaDTO>> dtoList =
                new ParameterizedTypeReference<List<PersonaDTO>>() {};
        ResponseEntity<List<PersonaDTO>> responseEntity = restTemplate.exchange(request, dtoList);

        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(OK);
        Assertions.assertThat(responseEntity.getBody().size()).isEqualTo(1);

    }
    @Test
    void getAll() throws URISyntaxException {

        RequestEntity<Void> request = RequestEntity.get(new URI("http://localhost:"+puerto+"/getPersona/findAll"))
                .accept(MediaType.APPLICATION_JSON).build();
        ParameterizedTypeReference<List<PersonaDTO>> dtoList =
                new ParameterizedTypeReference<List<PersonaDTO>>() {};
        ResponseEntity<List<PersonaDTO>> responseEntity = restTemplate.exchange(request, dtoList);

        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(OK);
        Assertions.assertThat(responseEntity.getBody().size()).isEqualTo(1);

    }
}