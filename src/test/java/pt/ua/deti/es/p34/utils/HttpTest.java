package pt.ua.deti.es.p34.utils;

import static org.junit.Assert.assertTrue;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Test the Http library.
 */
public class HttpTest {
    private static Map<String, Object> REPLY = null;

    @BeforeClass
    public static void setup() {
        REPLY = new HashMap<>();
        HashMap<String, Object> data = new HashMap<>();
        data.put("id", Integer.valueOf(1));
        data.put("email", "george.bluth@reqres.in");
        data.put("first_name", "George");
        data.put("last_name", "Bluth");
        data.put("avatar", "https://s3.amazonaws.com/uifaces/faces/twitter/calebogden/128.jpg");
        HashMap<String, Object> ad = new HashMap<>();
        ad.put("company", "StatusCode Weekly");
        ad.put("url", "http://statuscode.org/");
        ad.put("text",
                "A weekly newsletter focusing on software development, infrastructure, the server, performance, and the stack end of things.");
        REPLY.put("data", data);
        REPLY.put("ad", ad);
    }

    @Test
    public void GET() {
        Map<String, Object> reply = null;
        try {
            reply = Http.getJson("https://reqres.in/api/users/1");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        assertTrue(REPLY.equals(reply));
    }
}