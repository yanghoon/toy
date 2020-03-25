package test.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import test.ClientCache;
import test.client.google.Google;

public class GoogleService implements CloudService {
    private Logger log = LoggerFactory.getLogger(GoogleService.class);

    public void doSomething(String tenant) {
        Google google = ClientCache.get(Google.class, tenant);
        String account = google.getGoogleAccount();
        log.info("account: {}", account);
    }
}