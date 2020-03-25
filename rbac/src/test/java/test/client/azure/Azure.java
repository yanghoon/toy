package test.client.azure;

public class Azure {
    private String tenant;

    public Azure(String tenant) {
        this.tenant = tenant;
    }
    
    public String getSubscription(){
        return "azure:subscription:" + tenant;
    }
}