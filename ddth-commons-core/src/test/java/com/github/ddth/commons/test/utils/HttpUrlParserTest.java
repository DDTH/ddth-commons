package com.github.ddth.commons.test.utils;

import org.junit.Assert;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import okhttp3.HttpUrl;

public class HttpUrlParserTest extends TestCase {

    public HttpUrlParserTest(String testName) {
        super(testName);
    }

    public static Test suite() {
        return new TestSuite(HttpUrlParserTest.class);
    }

    private static HttpUrl parse(String url) {
        return HttpUrl.parse(url.replaceAll("[\\?&\\s]+$", ""));
    }

    @org.junit.Test
    public void test1() {
        String url = "http://localhost:9000/";
        String encodedUrl = "http://localhost:9000/";
        String parsedUrl = parse(url).toString();
        Assert.assertEquals(encodedUrl, parsedUrl);
    }

    @org.junit.Test
    public void test2() {
        String url = "http://user:password@localhost:9000/";
        String encodedUrl = "http://user:password@localhost:9000/";
        String parsedUrl = parse(url).toString();
        Assert.assertEquals(encodedUrl, parsedUrl);
    }

    @org.junit.Test
    public void test3() {
        String url = "http://user:password@localhost:9000/?a=1&b=2";
        String encodedUrl = "http://user:password@localhost:9000/?a=1&b=2";
        String parsedUrl = parse(url).toString();
        Assert.assertEquals(encodedUrl, parsedUrl);
    }

    @org.junit.Test
    public void test4() {
        String url = "http://user:password@localhost:9000/?a=1&b=2";
        String encodedUrl = "http://user:password@localhost:9000/?a=1&b=2&c=3";
        String parsedUrl = parse(url).newBuilder().addQueryParameter("c", "3").build().toString();
        Assert.assertEquals(encodedUrl, parsedUrl);
    }

    @org.junit.Test
    public void test5() {
        String url = "http://user:password@localhost:9000/";
        String encodedUrl = "http://user:password@localhost:9000/?a=1&b=2&c=3";
        String parsedUrl = parse(url).newBuilder().addQueryParameter("a", "1")
                .addQueryParameter("b", "2").addQueryParameter("c", "3").build().toString();
        Assert.assertEquals(encodedUrl, parsedUrl);
    }

    @org.junit.Test
    public void test6() {
        String url = "http://user:password@localhost:9000/?";
        String encodedUrl = "http://user:password@localhost:9000/?a=1&b=2&c=3";
        String parsedUrl = parse(url).newBuilder().addQueryParameter("a", "1")
                .addQueryParameter("b", "2").addQueryParameter("c", "3").build().toString();
        Assert.assertEquals(encodedUrl, parsedUrl);
    }

    @org.junit.Test
    public void test7() {
        String url = "http://user:password@localhost:9000/?a=1&b=2&";
        String encodedUrl = "http://user:password@localhost:9000/?a=1&b=2&c=3";
        String parsedUrl = parse(url).newBuilder().addQueryParameter("c", "3").build().toString();
        Assert.assertEquals(encodedUrl, parsedUrl);
    }

    @org.junit.Test
    public void test8() {
        String url = "http://user:password@localhost:9000/?";
        String encodedUrl = "http://user:password@localhost:9000/?a=1%202&b=2%203&c=3%204";
        String parsedUrl = parse(url).newBuilder().addQueryParameter("a", "1 2")
                .addQueryParameter("b", "2 3").addQueryParameter("c", "3 4").build().toString();
        Assert.assertEquals(encodedUrl, parsedUrl);
    }

    @org.junit.Test
    public void test9() {
        String url = "http://user:password@localhost:9000/?a=1+2";
        String encodedUrl = "http://user:password@localhost:9000/?a=1+2&b=2%203";
        String parsedUrl = parse(url).newBuilder().addQueryParameter("b", "2 3").build().toString();
        Assert.assertEquals(encodedUrl, parsedUrl);
    }

}
