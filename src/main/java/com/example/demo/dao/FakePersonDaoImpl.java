package com.example.demo.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.example.demo.model.Person;

@Repository("fakePersonDao")
public class FakePersonDaoImpl implements PersonDao {
  private static final Map<String, Person> database = new HashMap<>(); // dn, person

  @Override
  public int insertPerson(Person person) {
    database.put(person.getDn(), person);
    return 1;
  }

  @Override
  public List<Person> listPersons() {
    return new ArrayList<>(database.values());
  }

  @Override
  public Person getPerson(String dn) {
    return database.get(dn);
  }

}
