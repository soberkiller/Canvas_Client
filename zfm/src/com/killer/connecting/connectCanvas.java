package com.killer.connecting;

public class connectCanvas {

    public static void main(String[] args) {

        double version = 0.1;
        String url = "https://canvas.instructure.com/api/v1";

        String[] fields = {
                "courses",
                "10300000000000133",
                "users"

        };

        String[] rawResp;
        ConnectionPool connection = new ConnectionPool(fields, url, version);
//        System.out.println(connection.getEndpoints());
        String response =  connection.buildConnection();
        rawResp = response.split(",");
        for (String s : rawResp)
            System.out.println(s);

    }

}

