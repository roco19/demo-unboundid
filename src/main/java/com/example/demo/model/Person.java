package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Person {
  private final String dn;
  private final String givenName;
  private final String surname;

  public Person(
      @JsonProperty("dn") String dn,
      @JsonProperty("givenName") String givenName,
      @JsonProperty("surname") String surname) {
    this.dn = dn;
    this.givenName = givenName;
    this.surname = surname;
  }

  public String getDn() {
    return dn;
  }

  public String getGivenName() {
    return givenName;
  }

  public String getSurname() {
    return surname;
  }

  public String getFullName() {
    return givenName + " " + surname;
  }

}
