package paquete.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import paquete.application.PersonaService;


@DataJpaTest
class PersonaRepositorioTest {

    @Autowired
    private PersonaRepositorio personaRepositorio;

    @Test
    void checkFindByUsuario() throws Exception{
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

        Assertions.assertThat(personaRepositorio.findByUsuario("pepe").get(0)).isEqualTo(persona);

    }
}