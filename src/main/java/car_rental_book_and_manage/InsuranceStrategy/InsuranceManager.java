package car_rental_book_and_manage.InsuranceStrategy;

/** Manages the insurance strategy for a car rental. */
public class InsuranceManager {
  private InsuranceStrategy strategy;

  /**
   * Sets the insurance strategy.
   *
   * @param strategy the insurance strategy to be set
   */
  public void setStrategy(InsuranceStrategy strategy) {
    this.strategy = strategy;
  }

  /**
   * Gets the current insurance strategy.
   *
   * @return the current insurance strategy
   */
  public InsuranceStrategy getStrategy() {
    return strategy;
  }

  /**
   * Gets the description of the current insurance strategy.
   *
   * @return the description of the current insurance strategy
   */
  public String getDescription() {
    if (strategy == null) {
      return "No strategy selected";
    } else {
      return strategy.getDescription();
    }
  }

  /**
   * Gets the daily cost of the current insurance strategy.
   *
   * @return the daily cost of the current insurance strategy
   */
  public double getDailyCost() {
    return strategy.getDailyCost();
  }

  /**
   * Gets the deposit required for the current insurance strategy.
   *
   * @return the deposit required for the current insurance strategy
   */
  public double getDeposit() {
    return strategy.getDeposit();
  }

  /**
   * Gets the excess amount for the current insurance strategy.
   *
   * @return the excess amount for the current insurance strategy
   */
  public double getExcess() {
    return strategy.getExcess();
  }

  /**
   * Gets the insurance strategy based on the type.
   *
   * @param type the type of insurance strategy
   * @return the insurance strategy corresponding to the type
   */
  public InsuranceStrategy getStrategyByType(String type) {
    switch (type) {
      case "Basic Cover":
        return new BasicCoverStrategy();
      case "Limited Cover":
        return new LimitedCoverStrategy();
      case "Premium Cover":
        return new PremiumCoverStrategy();
      default:
        throw new IllegalArgumentException("Unknown insurance type: " + type);
    }
  }
}
