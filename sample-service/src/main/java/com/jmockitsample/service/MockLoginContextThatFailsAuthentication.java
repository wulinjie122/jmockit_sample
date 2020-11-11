package com.jmockitsample.service;

import mockit.Mock;
import mockit.MockUp;

import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;

public class MockLoginContextThatFailsAuthentication extends MockUp<LoginContext> {

  @Mock
  public void $init(String name) {

  }

  @Mock
  public void login() throws LoginException {
    throw new LoginException();
  }

}
