package car_rental_book_and_manage.Objects;

import java.time.LocalDate;

/** Represents a reservation in the car rental system. */
public class Reservation {

  private int reservationId;
  private int clientId;
  private int vehicleId;
  private double totalRate;
  private String licensePlate;
  private String licenseNo;
  private LocalDate startDate;
  private LocalDate returnDate;
  private String insuranceType;

  /** Default constructor. */
  public Reservation() {}

  /**
   * Constructs a reservation with the specified details.
   *
   * @param clientId the ID of the client
   * @param vehicleId the ID of the vehicle
   * @param totalRate the total rate for the reservation
   * @param licensePlate the license plate of the vehicle
   * @param licenseNo the license number of the client
   * @param startDate the start date of the reservation
   * @param returnDate the return date of the reservation
   * @param insuranceType the type of insurance for the reservation
   */
  public Reservation(
      int clientId,
      int vehicleId,
      double totalRate,
      String licensePlate,
      String licenseNo,
      LocalDate startDate,
      LocalDate returnDate,
      String insuranceType) {
    this.clientId = clientId;
    this.vehicleId = vehicleId;
    this.totalRate = totalRate;
    this.licensePlate = licensePlate;
    this.licenseNo = licenseNo;
    this.startDate = startDate;
    this.returnDate = returnDate;
    this.insuranceType = insuranceType;
  }

  /**
   * Gets the reservation ID.
   *
   * @return the reservation ID
   */
  public int getReservationId() {
    return reservationId;
  }

  /**
   * Sets the reservation ID.
   *
   * @param reservationId the reservation ID to set
   */
  public void setReservationId(int reservationId) {
    this.reservationId = reservationId;
  }

  /**
   * Gets the client ID.
   *
   * @return the client ID
   */
  public int getClientId() {
    return clientId;
  }

  /**
   * Sets the client ID.
   *
   * @param clientId the client ID to set
   */
  public void setClientId(int clientId) {
    this.clientId = clientId;
  }

  /**
   * Gets the vehicle ID.
   *
   * @return the vehicle ID
   */
  public int getVehicleId() {
    return vehicleId;
  }

  /**
   * Sets the vehicle ID.
   *
   * @param vehicleId the vehicle ID to set
   */
  public void setVehicleId(int vehicleId) {
    this.vehicleId = vehicleId;
  }

  /**
   * Gets the total rate for the reservation.
   *
   * @return the total rate for the reservation
   */
  public double getTotalRate() {
    return totalRate;
  }

  /**
   * Sets the total rate for the reservation.
   *
   * @param totalRate the total rate to set
   */
  public void setTotalRate(double totalRate) {
    this.totalRate = totalRate;
  }

  /**
   * Gets the license plate of the vehicle.
   *
   * @return the license plate of the vehicle
   */
  public String getLicensePlate() {
    return licensePlate;
  }

  /**
   * Sets the license plate of the vehicle.
   *
   * @param licensePlate the license plate to set
   */
  public void setLicensePlate(String licensePlate) {
    this.licensePlate = licensePlate;
  }

  /**
   * Gets the license number of the client.
   *
   * @return the license number of the client
   */
  public String getLicenseNo() {
    return licenseNo;
  }

  /**
   * Sets the license number of the client.
   *
   * @param licenseNo the license number to set
   */
  public void setLicenseNo(String licenseNo) {
    this.licenseNo = licenseNo;
  }

  /**
   * Gets the start date of the reservation.
   *
   * @return the start date of the reservation
   */
  public LocalDate getStartDate() {
    return startDate;
  }

  /**
   * Sets the start date of the reservation.
   *
   * @param startDate the start date to set
   */
  public void setStartDate(LocalDate startDate) {
    this.startDate = startDate;
  }

  /**
   * Gets the return date of the reservation.
   *
   * @return the return date of the reservation
   */
  public LocalDate getReturnDate() {
    return returnDate;
  }

  /**
   * Sets the return date of the reservation.
   *
   * @param returnDate the return date to set
   */
  public void setReturnDate(LocalDate returnDate) {
    this.returnDate = returnDate;
  }

  /**
   * Gets the type of insurance for the reservation.
   *
   * @return the type of insurance for the reservation
   */
  public String getInsuranceType() {
    return insuranceType;
  }

  /**
   * Sets the type of insurance for the reservation.
   *
   * @param insuranceType the insurance type to set
   */
  public void setInsuranceType(String insuranceType) {
    this.insuranceType = insuranceType;
  }
}
