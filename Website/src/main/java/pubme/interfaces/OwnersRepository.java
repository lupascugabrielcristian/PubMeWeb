package pubme.interfaces;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import pubme.model.Owner;

@Repository
public interface OwnersRepository extends MongoRepository<Owner, String> {
    Owner findOneByMail(String mail);
    Owner findOneByFullName(String fullName);
    Owner findOneById(String id);
}
