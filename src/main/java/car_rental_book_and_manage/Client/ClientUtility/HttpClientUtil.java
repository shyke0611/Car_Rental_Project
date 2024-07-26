package car_rental_book_and_manage.Client.ClientUtility;

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
    HttpURLConnection conn = setupConnection(method, endpoint, payload);

    int responseCode = conn.getResponseCode();
    String responseMessage = conn.getResponseMessage();
    System.out.println("Response Code: " + responseCode);
    System.out.println("Response Message: " + responseMessage);

    if (responseCode >= HttpURLConnection.HTTP_OK
        && responseCode < HttpURLConnection.HTTP_MULT_CHOICE) {
      return readResponse(conn);
    } else {
      String errorResponse = readErrorResponse(conn);
      System.err.println("Error Response: " + errorResponse);
      conn.disconnect();
      throw new RuntimeException(
          "Failed : HTTP error code : " + responseCode + " - " + errorResponse);
    }
  }

  /**
   * Sets up an HTTP connection with the given method, endpoint, and payload.
   *
   * @param method the HTTP method
   * @param endpoint the URL endpoint
   * @param payload the JSON payload
   * @return the configured HttpURLConnection
   * @throws Exception if an error occurs
   */
  private static HttpURLConnection setupConnection(String method, String endpoint, Object payload)
      throws Exception {
    URL url = new URL(endpoint);
    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
    conn.setRequestMethod(method);

    if ("POST".equalsIgnoreCase(method)
        || "PUT".equalsIgnoreCase(method)
        || "DELETE".equalsIgnoreCase(method)) {
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

    if ("GET".equalsIgnoreCase(method)) {
      conn.setRequestProperty("Accept", "application/json");
    }

    return conn;
  }

  /**
   * Reads the response from the HttpURLConnection.
   *
   * @param conn the HttpURLConnection
   * @return the response as a String
   * @throws Exception if an error occurs
   */
  private static String readResponse(HttpURLConnection conn) throws Exception {
    try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
      StringBuilder response = new StringBuilder();
      String line;
      while ((line = br.readLine()) != null) {
        response.append(line);
      }
      return response.toString();
    } finally {
      conn.disconnect();
    }
  }

  /**
   * Reads the error response from the HttpURLConnection.
   *
   * @param conn the HttpURLConnection
   * @return the error response as a String
   * @throws Exception if an error occurs
   */
  private static String readErrorResponse(HttpURLConnection conn) throws Exception {
    try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getErrorStream()))) {
      StringBuilder response = new StringBuilder();
      String line;
      while ((line = br.readLine()) != null) {
        response.append(line);
      }
      return response.toString();
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

  public static String sendDeleteRequest(String endpoint, Object payload) throws Exception {
    return sendHttpRequest("DELETE", endpoint, payload);
  }
}
