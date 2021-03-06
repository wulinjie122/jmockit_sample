package com.jmockitsample.service;

import lombok.Data;

@Data
public class Person {
  private String name;
  private Integer age;
  private Person friend;

  public Person(){}

  public Person(String name, Integer age, Person friend){
    this.age = age;
    this.name = name;
    this.friend = friend;
  }

  @Override
  public boolean equals(Object obj) {
    return this.name.equals(((Person)obj).getName());
  }

}
