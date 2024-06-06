package car_rental_book_and_manage.InsuranceStrategy;

/** Interface for defining insurance strategies for car rentals. */
public interface InsuranceStrategy {

  /**
   * Gets the name of the insurance type.
   *
   * @return the name of the insurance type
   */
  String getInsuranceTypeName();

  /**
   * Gets the description of the insurance coverage.
   *
   * @return a detailed description of the insurance coverage
   */
  String getDescription();

  /**
   * Gets the daily cost of the insurance.
   *
   * @return the daily cost of the insurance
   */
  double getDailyCost();

  /**
   * Gets the deposit required for the insurance.
   *
   * @return the deposit required for the insurance
   */
  double getDeposit();

  /**
   * Gets the excess amount for the insurance.
   *
   * @return the excess amount for the insurance
   */
  double getExcess();
}
