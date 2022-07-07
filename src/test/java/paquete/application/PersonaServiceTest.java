package paquete.application;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import paquete.domain.Persona;
import paquete.domain.PersonaRepositorio;
import paquete.infracstructure.PersonaDTO;
import static org.mockito.Mockito.verify;

class PersonaServiceTest {

    @Mock
    private PersonaRepositorio personaRepositorio;
    private AutoCloseable autoCloseable;
    @InjectMocks
    private PersonaService personaService;
    @InjectMocks
    private ModelMapper modelMapper;
    @BeforeEach
    void setUp()  throws Exception {
        autoCloseable = MockitoAnnotations.openMocks(this);
        personaService = new PersonaService(personaRepositorio, modelMapper);

    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void testAddPersona() throws Exception {
        PersonaDTO personaDTO = new PersonaDTO(
                1,
                "aa",
                "a",
                "el pepe",
                "aaaa",
                "aaaa@aaa.com",
                "eeee@eee.com",
                "o",
                true,
                null,
                "aaaaaaa.jpg",
                null);

        personaService.addPersona(personaDTO);


        ArgumentCaptor<Persona> personaArgumentCaptor = ArgumentCaptor.forClass(Persona.class);

        verify(personaRepositorio)
                .save(personaArgumentCaptor.capture());

        Persona capturedPersona = personaArgumentCaptor.getValue();
        System.out.println(capturedPersona);

        Assertions.assertThat(capturedPersona).isEqualTo(modelMapper.map(personaDTO, Persona.class));
    }

    @Test
    void findPersona() throws Exception {
        Assertions.assertThatThrownBy(() -> personaService.findPersona(1));
    }

    @Test
    void findUsuario() throws Exception {

        personaService.findUsuario("pepe");
        verify(personaRepositorio).findByUsuario("pepe");

    }

    @Test
    void findAll() {
        personaService.findAll();
        verify(personaRepositorio).findAll();
    }

    @Test
    void deletePersona() throws Exception {

        personaService.deletePersona(1);
        verify(personaRepositorio).deleteById(1);

    }

    @Test

    void updatePersona() throws Exception{

        PersonaDTO personaDTO = new PersonaDTO(
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

        personaService.updatePersona(1, personaDTO);

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


        Assertions.assertThat(modelMapper.map(persona, PersonaDTO.class)).isEqualTo(personaDTO);

        ArgumentCaptor<Persona> personaArgumentCaptor = ArgumentCaptor.forClass(Persona.class);

        verify(personaRepositorio)
                .save(personaArgumentCaptor.capture());

        Persona capturedPersona = personaArgumentCaptor.getValue();
        System.out.println(capturedPersona);

        Assertions.assertThat(capturedPersona).isEqualTo(modelMapper.map(personaDTO, Persona.class));

    }
}