package car_rental_book_and_manage.Server.RestControllers;

import car_rental_book_and_manage.Server.InsuranceStrategy.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * InsuranceController handles insurance-related HTTP requests. It provides endpoints for retrieving
 * insurance details.
 */
@Path("/insurance")
public class InsuranceController {
  private ObjectMapper objectMapper = new ObjectMapper();
  private InsuranceManager insuranceManager = new InsuranceManager();

  /**
   * Handles HTTP GET requests for retrieving insurance details.
   *
   * @param insuranceType the type of insurance
   * @return Response containing the insurance details in JSON format.
   * @throws Exception if an error occurs during processing.
   */
  @GET
  @Path("/{insuranceType}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response getInsuranceDetails(@PathParam("insuranceType") String insuranceType)
      throws Exception {
    // Decode the insuranceType to handle spaces and special characters
    String decodedInsuranceType = URLDecoder.decode(insuranceType, StandardCharsets.UTF_8.name());
    try {
      InsuranceStrategy strategy = insuranceManager.getStrategyByType(decodedInsuranceType);
      Map<String, Object> insuranceDetails = new HashMap<>();
      insuranceDetails.put("insuranceTypeName", strategy.getInsuranceTypeName());
      insuranceDetails.put("description", strategy.getDescription());
      insuranceDetails.put("dailyCost", strategy.getDailyCost());
      insuranceDetails.put("deposit", strategy.getDeposit());
      insuranceDetails.put("excess", strategy.getExcess());
      return Response.ok(objectMapper.writeValueAsString(insuranceDetails)).build();
    } catch (IllegalArgumentException e) {
      return Response.status(Response.Status.BAD_REQUEST)
          .entity("Unknown insurance type: " + decodedInsuranceType)
          .build();
    }
  }
}
