package ua.palchevskyi.rest_app.Repos;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.palchevskyi.rest_app.Entity.User;

public interface UserDetailsRepository extends JpaRepository<User, String>{
}
