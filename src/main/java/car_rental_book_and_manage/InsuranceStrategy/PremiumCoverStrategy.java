package car_rental_book_and_manage.InsuranceStrategy;

/** Represents the premium cover insurance strategy for a car rental. */
public class PremiumCoverStrategy implements InsuranceStrategy {

  /**
   * Gets the name of the insurance type.
   *
   * @return the user-friendly name of the insurance type
   */
  @Override
  public String getInsuranceTypeName() {
    return "Premium Cover";
  }

  /**
   * Gets the description of the insurance coverage.
   *
   * @return a detailed description of the insurance coverage
   */
  @Override
  public String getDescription() {
    return "$0 EXCESS, $0 BOND\n\n"
               + "Windscreen & Tyre: Yes\n"
               + "Premium 24/7 Roadside Assistance: Yes\n"
               + "Lost Key Replacement: Yes";
  }

  /**
   * Gets the daily cost of the insurance.
   *
   * @return the daily cost of the insurance
   */
  @Override
  public double getDailyCost() {
    return 35.0;
  }

  /**
   * Gets the deposit required for the insurance.
   *
   * @return the deposit required for the insurance
   */
  @Override
  public double getDeposit() {
    return 0.0;
  }

  /**
   * Gets the excess amount for the insurance.
   *
   * @return the excess amount for the insurance
   */
  @Override
  public double getExcess() {
    return 0.0;
  }
}
