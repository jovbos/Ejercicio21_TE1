package paquete.infracstructure;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import paquete.application.PersonaService;
import paquete.domain.Persona;

import java.util.List;

@RestController
public class Controlador {


    @Autowired
    private PersonaService personaService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping("/addPersona")
    public PersonaDTO addPersona(@RequestBody PersonaDTO personaDTO) throws Exception {
        return personaService.addPersona(personaDTO);
    }

    @PutMapping("/updatePersona/{id}")
    public String updatePersona(@PathVariable("id") Integer id, @RequestBody PersonaDTO personaDTO) throws Exception {
        personaService.updatePersona(id, personaDTO);
        return "Persona con id: "+id+" modificada.";
    }

    @DeleteMapping("/deletePersona/{id}")
    public String deletePersona(@PathVariable("id") Integer id) throws Exception{
        personaService.deletePersona(id);
        return "Persona borrada";
    }

    @GetMapping("/getPersona/findId/{id}")
    public PersonaDTO getPersonaId(@PathVariable("id") Integer id) throws Exception{
        PersonaDTO personaDTO = modelMapper.map(personaService.findPersona(id), PersonaDTO.class);
        return personaDTO;
    }

    @GetMapping("/getPersona/findUser/{usuario}")
    public List<PersonaDTO> getPersonaId(@PathVariable("usuario") String usuario) throws Exception{
        return personaService.findUsuario(usuario);
    }

    @GetMapping("/getPersona/findAll")
    public List<PersonaDTO> getAll() {
        return personaService.findAll();
    }
}

