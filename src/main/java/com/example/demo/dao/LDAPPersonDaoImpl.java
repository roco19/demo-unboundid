package com.example.demo.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.example.demo.model.Person;
import com.unboundid.ldap.sdk.AddRequest;
import com.unboundid.ldap.sdk.Attribute;
import com.unboundid.ldap.sdk.DN;
import com.unboundid.ldap.sdk.Entry;
import com.unboundid.ldap.sdk.Filter;
import com.unboundid.ldap.sdk.LDAPConnection;
import com.unboundid.ldap.sdk.LDAPException;
import com.unboundid.ldap.sdk.RDN;
import com.unboundid.ldap.sdk.ResultCode;
import com.unboundid.ldap.sdk.SearchRequest;
import com.unboundid.ldap.sdk.SearchResult;
import com.unboundid.ldap.sdk.SearchResultEntry;
import com.unboundid.ldap.sdk.SearchScope;

@Repository("ldapPersonDao")
public class LDAPPersonDaoImpl implements PersonDao {
  private static final String ATTRIBUTE_CN = "cn";
  private static final String ATTRIBUTE_OBJECT_CLASS = "objectClass";
  private static final String ATTRIBUTE_GIVEN_NAME = "givenName";
  private static final String ATTRIBUTE_SURNAME = "sn";

  private static final Filter FILTER_BY_PERSON =
      Filter.createEqualityFilter(ATTRIBUTE_OBJECT_CLASS, "inetOrgPerson");

  private static DN BASE_DN;
  private static DN DN_MUSICIANS;
  static {
    try {
      BASE_DN = new DN("dc=example,dc=com");
      DN_MUSICIANS = new DN("ou=musicians,dc=example,dc=com");
    } catch (LDAPException e) {
      e.printStackTrace();
    }
  }

  @Autowired
  private LDAPConnection ldapConnection;

  @Override
  public int insertPerson(Person person) {
    RDN personRdn = new RDN(ATTRIBUTE_CN, person.getFullName());
    DN personDn = new DN(personRdn, DN_MUSICIANS);

    List<Attribute> personAttributes = new ArrayList<>(3);
    personAttributes.add(new Attribute(ATTRIBUTE_OBJECT_CLASS, "inetOrgPerson"));
    personAttributes.add(new Attribute(ATTRIBUTE_GIVEN_NAME, person.getGivenName()));
    personAttributes.add(new Attribute(ATTRIBUTE_SURNAME, person.getSurname()));

    Entry addEntry = new Entry(personDn, personAttributes);
    AddRequest addRequest = new AddRequest(addEntry);

    try {
      ldapConnection.add(addRequest);
      return 0;
    } catch (LDAPException e) {
      e.printStackTrace();
      return e.getResultCode().intValue();
    }
  }

  @Override
  public List<Person> listPersons() {
    try {
      SearchRequest searchRequest = new SearchRequest(
          BASE_DN.toNormalizedString(),
          SearchScope.SUB,
          FILTER_BY_PERSON,
          ATTRIBUTE_GIVEN_NAME,
          ATTRIBUTE_SURNAME);

      SearchResult searchResult = ldapConnection.search(searchRequest);
      return searchResult
          .getSearchEntries()
          .stream()
          .map(
              entry -> new Person(
                  entry.getDN(),
                  entry.getAttributeValue(ATTRIBUTE_GIVEN_NAME),
                  entry.getAttributeValue(ATTRIBUTE_SURNAME)))
          .collect(Collectors.toList());
    } catch (LDAPException e) {
      e.printStackTrace();
      return null;
    }
  }

  @Override
  public Person getPerson(String dn) {
    try {
      SearchResultEntry entry =
          ldapConnection.getEntry(dn, ATTRIBUTE_GIVEN_NAME, ATTRIBUTE_SURNAME);
      if (entry == null) {
        throw new LDAPException(ResultCode.OTHER);
      }

      return new Person(
          dn,
          entry.getAttributeValue(ATTRIBUTE_GIVEN_NAME),
          entry.getAttributeValue(ATTRIBUTE_SURNAME));
    } catch (LDAPException e) {
      e.printStackTrace();
      return null;
    }
  }

}
