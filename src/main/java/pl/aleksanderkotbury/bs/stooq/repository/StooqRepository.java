package pl.aleksanderkotbury.bs.stooq.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(exported = false)
public interface StooqRepository extends MongoRepository<StooqMongoDto, String> {
}
