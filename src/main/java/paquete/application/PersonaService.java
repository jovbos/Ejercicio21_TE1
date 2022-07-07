package paquete.application;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import paquete.domain.Persona;
import paquete.domain.PersonaRepositorio;
import paquete.infracstructure.PersonaDTO;
import java.util.ArrayList;
import java.util.List;

@Service
public class PersonaService {

    private final ModelMapper modelMapper;

    private final PersonaRepositorio personaRepositorio;

    public PersonaService(PersonaRepositorio personaRepositorio, ModelMapper modelMapper) {
        this.personaRepositorio = personaRepositorio;
        this.modelMapper = modelMapper;
    }

    public PersonaDTO addPersona(PersonaDTO personaDTO) throws Exception {
        if (personaDTO.getUsuario() == null || personaDTO.getPassword() == null || personaDTO.getName() == null || personaDTO.getCompany_email() == null
                ||personaDTO.getPersonal_email() == null ||personaDTO.getCity() == null ||personaDTO.getActive() == null){throw new Exception("Faltan campos imprescindibles");}
        if (personaDTO.getUsuario().length() > 10){throw new Exception("El campo usuario no puede contener mas de 10 digitos");}

        Persona persona = modelMapper.map(personaDTO, Persona.class);
        personaRepositorio.save(persona);
        PersonaDTO output = modelMapper.map(persona,PersonaDTO.class);
        output.setId(persona.getId());
        return output;
    }

    public PersonaDTO findPersona(Integer id) throws Exception {

        Persona persona = personaRepositorio.findById(id).orElseThrow(() -> new Exception("Not found"));
        return modelMapper.map(persona, PersonaDTO.class);
    }

    public List<PersonaDTO> findUsuario(String usuario){

        List<Persona> personaList = personaRepositorio.findByUsuario(usuario);
        List<PersonaDTO> dtoList = new ArrayList<>();
        personaList.forEach(p -> {
            dtoList.add(modelMapper.map(p, PersonaDTO.class));
        });
        return dtoList;
    }

    public List<PersonaDTO> findAll() {
        Iterable<Persona> personaList = personaRepositorio.findAll();
        List<PersonaDTO> dtoList = new ArrayList<>();
        personaList.forEach(p -> {
            dtoList.add(modelMapper.map(p, PersonaDTO.class));
        });
        return dtoList;
    }
    public void deletePersona(Integer id) throws Exception {
        personaRepositorio.deleteById(id);
    }
    public void updatePersona(Integer id, PersonaDTO personaDTO) throws Exception {
        Persona persona = modelMapper.map(personaDTO, Persona.class);
        persona.setId(id);
        personaRepositorio.save(persona);

        return;
    }
}
