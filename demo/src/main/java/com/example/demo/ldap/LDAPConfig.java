package com.example.demo.ldap;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.unboundid.ldap.sdk.LDAPConnection;
import com.unboundid.ldap.sdk.LDAPException;

// TODO Review the scope of the beans, are they singleton?
@Configuration
public class LDAPConfig {
  private static final int PORT = 10389;
  private static final String HOST = "localhost";

  @Bean
  public LDAPConnection getLdapConnection() throws LDAPException {
    return new LDAPConnection(HOST, PORT);
  }

}
