package com.example.demo.api;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.model.Person;
import com.example.demo.service.PersonService;

@RestController
@RequestMapping("/api/person")
public class PersonController {
  @Autowired
  private PersonService personService;

  @PostMapping("/insert")
  public void insertPerson(@RequestBody Person person) {
    personService.insertPerson(person);
  }

  @PostMapping("/list")
  public List<Person> listPersons() {
    return personService.listPersons();
  }

  @PostMapping("/get")
  public Person getPerson(@RequestBody String dn) {
    return personService.getPerson(dn);
  }

}
