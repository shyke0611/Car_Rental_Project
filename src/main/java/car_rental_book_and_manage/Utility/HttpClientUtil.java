package car_rental_book_and_manage.Utility;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/** Utility class for making HTTP requests. */
public class HttpClientUtil {

  private static final ObjectMapper objectMapper =
      new ObjectMapper().registerModule(new JavaTimeModule());

  /**
   * Makes an HTTP request and returns the response as a String.
   *
   * @param method the HTTP method (GET, POST, PUT, DELETE)
   * @param endpoint the URL endpoint
   * @param payload the JSON payload (for POST and PUT requests)
   * @return the response as a String
   * @throws Exception if an error occurs
   */
  public static String sendHttpRequest(String method, String endpoint, Object payload)
      throws Exception {
    URL url = new URL(endpoint);
    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
    conn.setRequestMethod(method);

    if ("POST".equals(method) || "PUT".equals(method)) {
      conn.setDoOutput(true);
      conn.setRequestProperty("Content-Type", "application/json");
      if (payload != null) {
        String jsonInputString = objectMapper.writeValueAsString(payload);
        try (OutputStream os = conn.getOutputStream()) {
          byte[] input = jsonInputString.getBytes("utf-8");
          os.write(input, 0, input.length);
        }
      }
    }

    if ("GET".equals(method) || "DELETE".equals(method)) {
      conn.setRequestProperty("Accept", "application/json");
    }

    int responseCode = conn.getResponseCode();
    if (responseCode >= 200 && responseCode < 300) {
      BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
      StringBuilder sb = new StringBuilder();
      String output;
      while ((output = br.readLine()) != null) {
        sb.append(output);
      }
      conn.disconnect();
      return sb.toString();
    } else {
      conn.disconnect();
      throw new RuntimeException("Failed : HTTP error code : " + responseCode);
    }
  }

  /**
   * Makes an HTTP GET request.
   *
   * @param endpoint the URL endpoint
   * @return the response as a String
   * @throws Exception if an error occurs
   */
  public static String sendGetRequest(String endpoint) throws Exception {
    return sendHttpRequest("GET", endpoint, null);
  }

  /**
   * Makes an HTTP POST request with a JSON payload.
   *
   * @param endpoint the URL endpoint
   * @param payload the JSON payload
   * @return the response as a String
   * @throws Exception if an error occurs
   */
  public static String sendPostRequest(String endpoint, Object payload) throws Exception {
    return sendHttpRequest("POST", endpoint, payload);
  }

  /**
   * Makes an HTTP PUT request with a JSON payload.
   *
   * @param endpoint the URL endpoint
   * @param payload the JSON payload
   * @return the response as a String
   * @throws Exception if an error occurs
   */
  public static String sendPutRequest(String endpoint, Object payload) throws Exception {
    return sendHttpRequest("PUT", endpoint, payload);
  }

  /**
   * Makes an HTTP DELETE request.
   *
   * @param endpoint the URL endpoint
   * @return the response as a String
   * @throws Exception if an error occurs
   */
  public static String sendDeleteRequest(String endpoint) throws Exception {
    return sendHttpRequest("DELETE", endpoint, null);
  }
}
