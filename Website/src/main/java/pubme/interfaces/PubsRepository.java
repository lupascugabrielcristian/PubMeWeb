package pubme.interfaces;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import pubme.model.Pub;

@Repository
public interface PubsRepository extends MongoRepository<Pub, String> {

    Pub findFirstById(String id);
    String deleteByName(String name);
}
