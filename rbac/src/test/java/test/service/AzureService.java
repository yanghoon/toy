package test.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import test.ClientCache;
import test.client.azure.Azure;

public class AzureService implements CloudService {
    private Logger log = LoggerFactory.getLogger(AzureService.class);

    public void doSomething(String tenant) {
        Azure azure = ClientCache.get(Azure.class, tenant);
        String subscription = azure.getSubscription();
        log.info("subscription: {}", subscription);
    }
}