package com.killer.connecting;

public class connectCanvas {

    public static void main(String[] args) {

        double version = 0.1;
        String url = "https://canvas.instructure.com/api/v1";

        String[] fields = {
                "courses",

        };


        ConnectionPool connection = new ConnectionPool(fields, url, version);
//        System.out.println(connection.getEndpoints());
        String response =  connection.buildConnection();
        System.out.println(response);

    }

}

