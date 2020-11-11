package com.jmockitsample.service;


import mockit.Invocation;
import mockit.Mock;
import mockit.MockUp;
import org.testng.Assert;
import org.testng.annotations.Test;

import javax.security.auth.Subject;
import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;

public class MockLoginContextThatFailsAuthenticationTest {

  @Test(groups = {"checkin"}, expectedExceptions = LoginException.class)
  public void applyingAnotherMockClass() throws Exception {
    MockLoginContextThatFailsAuthentication instance = new MockLoginContextThatFailsAuthentication();

    // Inside an application class:
    // new LoginContext("test").login();
    instance.login();
  }

  @Test(groups = {"checkin"})
  public void accessingTheMockedInstanceInMockMethods() throws Exception {
    final Subject testSubject = new Subject();

    new MockUp<LoginContext>() {
      @Mock
      void $init(Invocation invocation, String name, Subject subject) {
        Assert.assertNotNull(name);
        Assert.assertSame(testSubject, subject);

        // Gets the invoked instance.
        LoginContext loginContext = invocation.getInvokedInstance();

        // Verifies that this is the first invocation.
        assertEquals(1, invocation.getInvocationCount());

        // Forces setting of private Subject field, since no setter is available.
        Deencapsulation.setField(loginContext, subject);
      }

      @Mock
      void login(Invocation invocation) {
        // Gets the invoked instance.
        LoginContext loginContext = invocation.getInvokedInstance();

        // getSubject() returns null until the subject is authenticated.
        assertNull(loginContext.getSubject());

        // Private field set to true when login succeeds.
        Deencapsulation.setField(loginContext, "loginSucceeded", true);
      }

      @Mock
      void logout(Invocation invocation) {
        // Gets the invoked instance.
        LoginContext loginContext = invocation.getInvokedInstance();

        assertSame(testSubject, loginContext.getSubject());
      }

    }

  }
