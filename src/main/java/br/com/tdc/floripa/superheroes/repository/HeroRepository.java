package br.com.tdc.floripa.superheroes.repository;

import br.com.tdc.floripa.superheroes.model.Hero;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HeroRepository extends ReactiveMongoRepository <Hero, String> {



}
