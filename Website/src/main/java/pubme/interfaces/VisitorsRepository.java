package pubme.interfaces;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import pubme.model.VisitorsDistribution;

@Repository
public interface VisitorsRepository extends MongoRepository<VisitorsDistribution, Long>{
    VisitorsDistribution findOneByPubId(String pubId);
}
