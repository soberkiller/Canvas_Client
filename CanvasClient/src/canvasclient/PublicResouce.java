package canvasclient;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public abstract class PublicResouce {
    // for connection to Canvas
    static List<String> fields = new ArrayList<String>();
    public static final String GET = "GET";
    public static final String PUT = "PUT";
    public static final String POST = "POST";

    // for Token
    public final Base64.Decoder decoder = Base64.getDecoder();
    public static final String FILENAME = "token.dat";
}
