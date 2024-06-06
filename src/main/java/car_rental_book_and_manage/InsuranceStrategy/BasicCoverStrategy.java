package car_rental_book_and_manage.InsuranceStrategy;

/** Represents the basic cover insurance strategy for a car rental. */
public class BasicCoverStrategy implements InsuranceStrategy {

  /**
   * Gets the name of the insurance type.
   *
   * @return the user-friendly name of the insurance type
   */
  @Override
  public String getInsuranceTypeName() {
    return "Basic Cover";
  }

  /**
   * Gets the description of the insurance coverage.
   *
   * @return a detailed description of the insurance coverage
   */
  @Override
  public String getDescription() {
    return "$500 EXCESS, $500 BOND\n\n"
               + "Windscreen & Tyre: No\n"
               + "Premium 24/7 Roadside Assistance: No\n"
               + "Lost Key Replacement: No";
  }

  /**
   * Gets the daily cost of the insurance.
   *
   * @return the daily cost of the insurance
   */
  @Override
  public double getDailyCost() {
    return 24.0;
  }

  /**
   * Gets the deposit required for the insurance.
   *
   * @return the deposit required for the insurance
   */
  @Override
  public double getDeposit() {
    return 500.0;
  }

  /**
   * Gets the excess amount for the insurance.
   *
   * @return the excess amount for the insurance
   */
  @Override
  public double getExcess() {
    return 500.0;
  }
}
