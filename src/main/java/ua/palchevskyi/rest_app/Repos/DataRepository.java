package ua.palchevskyi.rest_app.Repos;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.palchevskyi.rest_app.Entity.Message;

public interface DataRepository extends JpaRepository<Message, Long>{

}
