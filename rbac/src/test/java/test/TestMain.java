package test;

import java.util.List;

import com.namics.commons.random.RandomData;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import test.client.azure.Azure;
import test.client.azure.AzureProvider;
import test.client.google.GoogleProvider;
import test.model.Tool;
import test.service.AzureService;
import test.service.CloudService;
import test.service.GoogleService;

// @SpringBootTest
public class TestMain {
    private static Logger log = LoggerFactory.getLogger(TestMain.class);

    private List<CloudService> services = Lists.newArrayList(new AzureService(), new GoogleService());

    @Test
    public void main() {
        // services.forEach(s -> s.doSomething("tenant1"));
    }

    @BeforeAll
    public static void before(){
        ClientCache.setProviders(new AzureProvider(), new GoogleProvider());

        tools().forEach(t -> {
            Azure client = ClientCache.get(Azure.class, "xxx");
            log.info("{}", t);
            log.info("{}", client);
        });
    }

    public static List<Tool> tools() {
        // Tool google = new Tool();
        // Tool azure = new Tool();
        // Tool azure2 = new Tool();
        Tool t1 = RandomData.random(Tool.class);
        t1.setKind("azure");
        t1.getAttributes().clear();
        return Lists.newArrayList(t1);
    }
}