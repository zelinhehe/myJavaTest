package DesignPatternBeauty.ApiAuthenticator;

public class Test {
    public static void main(String[] args) {
        DefaultApiAuthenticator authenticator = new DefaultApiAuthenticator();

        String baseUrl = "/api/uri";
        String appId = "1001";
        String password = "PASS-1001";
//        String password = "PASS-1002";
        long timestamp = System.currentTimeMillis();

        AuthToken authToken = AuthToken.generate(baseUrl, appId, password, timestamp);
        ApiRequest req1 = new ApiRequest(baseUrl, appId, authToken.getToken(), timestamp);

        authenticator.auth(req1);
    }
}
