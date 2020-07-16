package br.com.tdc.floripa.superheroes.controller;

import br.com.tdc.floripa.superheroes.model.Hero;
import br.com.tdc.floripa.superheroes.repository.HeroRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.Collections;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class HeroControllerTest {

  @Autowired
  private WebTestClient webTestClient;

  @Autowired
    HeroRepository  heroRepository;


  @Test
  public void shouldGetAllHeroes() {
    webTestClient.get().uri("/heroes")
      .accept(MediaType.APPLICATION_JSON)
      .exchange()
      .expectStatus().isOk()
      .expectHeader().contentType(MediaType.APPLICATION_JSON)
      .expectBodyList(Hero.class);
  }


}
