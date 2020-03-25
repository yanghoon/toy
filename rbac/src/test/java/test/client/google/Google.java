package test.client.google;

public class Google {
    private String tenant;

    public Google(String tenant) {
        this.tenant = tenant;
    }

    public String getGoogleAccount(){
        return "google:account:" + tenant;
    }
}