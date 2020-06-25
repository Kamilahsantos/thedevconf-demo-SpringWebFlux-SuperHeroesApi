package br.com.tdc.floripa.superheroes.exception;

public class HeroNotFoundException extends RuntimeException{

  public HeroNotFoundException(String heroId) {
    super("Hero not found with id " + heroId);
  }

}
