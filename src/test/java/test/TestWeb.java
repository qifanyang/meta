package test;

import com.meta.MetaApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MetaApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT )
public class TestWeb {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    public void testDemo() {
        String url = "http://localhost:" + port + "/addModel";
        String result = testRestTemplate.getForObject(url, String.class);
        System.out.println(result);

    }
}
