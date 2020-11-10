package com.jmockitsample.service;


import mockit.Expectations;
import mockit.Mocked;
import mockit.Tested;
import mockit.Verifications;
import org.junit.Assert;
import org.junit.Test;

import java.util.Locale;

public class MockedClassTest {

  //加上了JMockit的API @Mocked,JMockit会帮我们实例化这个对象，不用担心它为null
  @Mocked
  Locale locale;

  @Test
  public void testMockedClass() {
    //静态方法不起作用了，返回了null
    Assert.assertTrue(locale.getDefault() == null);

    //非静态方法（返回类型为string）也不起作用了，返回了null
    Assert.assertTrue(locale.getCountry() == null);

    //自己new一个，也同样如此，方法都被mock了
    Locale chinaLocale = new Locale("zh", "CN");
    Assert.assertTrue(chinaLocale.getCountry() == null);
  }

  @Test
  public void mockProcessTest(final @Mocked PersonService target) {
    //录制预期行为
    new Expectations() {
      {
        target.showName(anyString);
        result = "test1";
        target.showAge(anyInt);
        result = -1;
      }
    };

    //测试代码
    Assert.assertTrue("test1".equals(target.showName("test2")));
    Assert.assertTrue(-1 == target.showAge(12));
    Assert.assertTrue(-1 == target.showAge(12));

    //验证
    new Verifications() {
      {
        target.showName("test1");
        times = 0; //执行了0次。参数一致的才会计数
        target.showAge(12);
        times = 2; //执行了2次
      }
    };
  }

}
