package pt.ua.deti.es.p34.http;

import com.fasterxml.jackson.databind.ObjectMapper;

import pt.ua.deti.es.p34.utils.Utils;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.zip.GZIPInputStream;
import java.util.zip.Inflater;
import java.util.zip.InflaterInputStream;

/**
 * HTTP library.
 * <p>
 * Implements the REST GET method.
 *
 * @author Catarina Silva
 * @version 1.0
 */
public class HTTP {
    /**
     * Utility class, lets make the constructor private.
     */
    private HTTP() {
    }

    private static InputStream inputStream(HttpURLConnection con) throws IOException {
        InputStream rv;
        String encoding = con.getContentEncoding();
        if (encoding != null && encoding.equalsIgnoreCase("gzip")) {
            rv = new GZIPInputStream(con.getInputStream());
        } else if (encoding != null && encoding.equalsIgnoreCase("deflate")) {
            rv = new InflaterInputStream(con.getInputStream(), new Inflater(true));
        } else {
            rv = con.getInputStream();
        }
        return rv;
    }

    private static InputStream errorStream(HttpURLConnection con) throws IOException {
        InputStream rv;
        String encoding = con.getContentEncoding();
        if (encoding != null && encoding.equalsIgnoreCase("gzip")) {
            rv = new GZIPInputStream(con.getErrorStream());
        } else if (encoding != null && encoding.equalsIgnoreCase("deflate")) {
            rv = new InflaterInputStream(con.getErrorStream(), new Inflater(true));
        } else {
            rv = con.getErrorStream();
        }
        return rv;
    }

    public static Map<String, Object> getJson(final String urlPath) throws IOException {
        URL url = new URL(urlPath);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("Content-Type", "application/json");
        con.setRequestProperty("Accept-Encoding", "gzip, deflate");
        con.setRequestProperty("User-Agent", "");

        con.setAllowUserInteraction(true);
        con.setConnectTimeout(5000);
        con.setReadTimeout(5000);

        boolean isError = (con.getResponseCode() >= 400);
        InputStream is = isError ? errorStream(con) : inputStream(con);

        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> jsonMap = Utils.cast(mapper.readValue(is, Map.class));

        con.disconnect();

        return jsonMap;
    }
}
