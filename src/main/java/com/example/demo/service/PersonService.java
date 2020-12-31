package com.example.demo.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import com.example.demo.dao.PersonDao;
import com.example.demo.model.Person;

@Service
public class PersonService {
  @Autowired
  @Qualifier("ldapPersonDao")
  private PersonDao personDao;

  public int insertPerson(Person person) {
    return personDao.insertPerson(person);
  }

  public List<Person> listPersons() {
    return personDao.listPersons();
  }

  public Person getPerson(final String dn) {
    return personDao.getPerson(dn);
  }

}
