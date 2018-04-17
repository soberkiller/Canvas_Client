import java.io.*;
import java.util.*;

public class ReadConfig {
	public static void main(String[] args) {
		Properties p = new Properties();
		p.load("conf/canvasclient.conf");
		String token = p.getProperty("token");
		System.out.println(token);
		p.close();
}
