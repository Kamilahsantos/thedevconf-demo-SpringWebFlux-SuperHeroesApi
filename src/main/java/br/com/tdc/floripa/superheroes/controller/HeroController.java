package br.com.tdc.floripa.superheroes.controller;

import br.com.tdc.floripa.superheroes.model.Hero;
import br.com.tdc.floripa.superheroes.repository.HeroRepository;
import br.com.tdc.floripa.superheroes.exception.HeroNotFoundException;
import br.com.tdc.floripa.superheroes.response.ErrorResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController
public class HeroController {

  @Autowired
  private HeroRepository heroRepository;

  @GetMapping("/heroes")
  public Flux<Hero> getAllHeroes() {
    return heroRepository.findAll();
  }

  @PostMapping("/heroes")
  public Mono<Hero> createHero(@Valid @RequestBody Hero hero) {
    return heroRepository.save(hero);
  }

  @GetMapping("/heroes/{id}")
  public Mono<ResponseEntity<Hero>> getHeroById(@PathVariable(value = "id") String heroId) {
    return heroRepository.findById(heroId)
      .map(savedHero -> ResponseEntity.ok(savedHero))
      .defaultIfEmpty(ResponseEntity.notFound().build());
  }

  @PutMapping("/heroes/{id}")
  public Mono<ResponseEntity<Hero>> updateHero(@PathVariable(value = "id") String heroId,
                                               @Valid @RequestBody Hero hero) {
    return heroRepository.findById(heroId)
      .flatMap(existingHero -> {
        existingHero.setName(hero.getName());
        return heroRepository.save(existingHero);
      })
      .map(updateHero -> new ResponseEntity<>(updateHero, HttpStatus.OK))
      .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }

  @DeleteMapping("/heroes/{id}")
  public Mono<ResponseEntity<Void>> deleteHero(@PathVariable(value = "id") String heroId) {

    return heroRepository.findById(heroId)
      .flatMap(existingHero ->
        heroRepository.delete(existingHero)
          .then(Mono.just(new ResponseEntity<Void>(HttpStatus.OK)))
      )
      .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }

  @GetMapping(value = "/stream/heroes", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
  public Flux<Hero> streamAllHeroes() {
    return heroRepository.findAll();
  }


  @ExceptionHandler(DuplicateKeyException.class)
  public ResponseEntity handleDuplicateKeyException(DuplicateKeyException ex) {
    return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorResponse("A Hero with the same text already exists"));
  }

  @ExceptionHandler(HeroNotFoundException.class)
  public ResponseEntity handleHeroNotFoundException(HeroNotFoundException ex) {
    return ResponseEntity.notFound().build();
  }
}
