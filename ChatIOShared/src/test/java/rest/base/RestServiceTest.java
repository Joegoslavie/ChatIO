package rest.base;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class RestServiceTest {

    static RestService restService;

    @BeforeAll
    static void setup(){
        restService = new RestService();
    }

    @Test
    void postRequest() {
        HashMap<String, String> params = new HashMap<>();
        params.put("username", "123");
        params.put("password", "123");

        String output = restService.postRequest("https://httpbin.org/post", null);
        assertNotNull(output);
    }

    @Test
    void getRequest() {
        String output = restService.getRequest("https://httpbin.org/get");
        assertNotNull(output);
    }
}