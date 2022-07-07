package paquete.infracstructure;
import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.Date;

@Data
//@AllArgsConstructor
public class PersonaDTO {

    private Integer id;

    private String usuario;

    private String password;

    private String name;

    private String surname;

    private String company_email;

    private String personal_email;

    private String city;

    private Boolean active;

    private Date created_date;

    private String image_url;

    private Date termination_date;

    public PersonaDTO(Integer id, String usuario, String password, String name, String surname, String company_email, String personal_email, String city, Boolean active, Date created_date, String image_url, Date termination_date) {
        this.id=id;
        this.usuario = usuario;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.company_email = company_email;
        this.personal_email = personal_email;
        this.city = city;
        this.active = active;
        this.created_date = created_date;
        this.image_url = image_url;
        this.termination_date = termination_date;
    }

    public PersonaDTO () {}
}