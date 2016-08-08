package pubme.interfaces;

import org.springframework.data.mongodb.repository.MongoRepository;
import pubme.model.User;

public interface UserRepository extends MongoRepository<User, String> {
    User findOneById(String id);
}
