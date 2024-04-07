package app.spring.security.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import app.spring.security.model.MyUser;

public interface MyUserRepo extends MongoRepository<MyUser, String>{

}
