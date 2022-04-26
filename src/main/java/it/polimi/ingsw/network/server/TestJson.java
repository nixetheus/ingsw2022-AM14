package it.polimi.ingsw.network.server;

public class TestJson {
  private final String name;
  private final String email;
  private final int age;
  private final boolean isMarried;

  public TestJson(String name, String email, int age, boolean isMarried) {
    this.name = name;
    this.email = email;
    this.age = age;
    this.isMarried = isMarried;
  }

  public String getName() {
    return name;
  }

  @Override
  public String toString() {
    return "TestJson{" +
        "name='" + name + '\'' +
        ", email='" + email + '\'' +
        ", age=" + age +
        ", isMarried=" + isMarried +
        '}';
  }
}
/*
  JSON object;
  {
    "name": "Mario",
    "email": "prova@gmail.com",
    "age": 28,
    "isMarried": false
   }
   {"name":"Mario","email":"prova@gmail.com","age":28,"isMarried":false}
 */