package ua.palchevskyi.rest_app.Entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="users")
@Data
public class User{
    @Id
    private String id;
    private String name;
    private String userpicture;
    private String email;
    private String gender;
    private String locale;
    private LocalDateTime lastVisit;
}
