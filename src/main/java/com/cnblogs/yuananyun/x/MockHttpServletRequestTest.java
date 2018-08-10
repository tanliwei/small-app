package com.cnblogs.yuananyun.x;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;

/**
 * Creating a mock HttpServletRequest out of a url string? -- https://stackoverflow.com/questions/6455359/creating-a-mock-httpservletrequest-out-of-a-url-string
 * @create 2018/7/9
 */
public class MockHttpServletRequestTest {
    @Test
    public void myTest(){
        // given
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setServerName("www.example.com");
        request.setRequestURI("/foo");
        request.setQueryString("param1=value1&param");

//        TestDispatcherServlet test = new TestDispatcherServlet();
// when
        String url = request.getRequestURL() .append( '?') + request.getQueryString(); // assuming there is always queryString.
        System.out.println(url);
        System.out.println("port:"+request.getLocalPort());
// then
        Assert.assertEquals(url, "http://www.example.com/foo?param1=value1&param");
    }
}
