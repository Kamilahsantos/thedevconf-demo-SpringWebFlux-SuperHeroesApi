package br.com.tdc.floripa.superheroes.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;


@Document(collection = "heroes")
public class Hero {

  @Id
  private String id;

  @NotBlank
  @Size(max=100)
  private String name;


  @NotNull
  private Date createdAt = new Date();

  public Hero(String this_is_a_test_hero) {
  }


  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Date getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(Date createdAt) {
    this.createdAt = createdAt;
  }



  public Hero(String id, String name) {
    this.id = id;
    this.name = name;
  }

  public Hero() {
  }
}
