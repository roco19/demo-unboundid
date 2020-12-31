package com.example.demo.dao;

import java.util.List;
import com.example.demo.model.Person;

public interface PersonDao {

  public int insertPerson(Person person);

  public List<Person> listPersons();

  public Person getPerson(String dn);

}
