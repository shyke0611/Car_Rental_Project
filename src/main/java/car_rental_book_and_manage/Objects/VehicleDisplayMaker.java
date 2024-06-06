package car_rental_book_and_manage.Objects;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.util.HashMap;
import java.util.Map;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

/** Factory class for creating vehicle image panes with details and actions. */
public class VehicleDisplayMaker {

  private final int paneWidth = 300;
  private final int paneHeight = 400;
  private final int imageWidth = 250;
  private final int imageHeight = 190;
  private final Map<String, Image> imageCache = new HashMap<>();

  /**
   * Creates a pane for a vehicle with details and a "Book Now" button.
   *
   * @param vehicle the vehicle to display
   * @param bookNowHandler the event handler for the "Book Now" button
   * @return the pane containing the vehicle details and actions
   */
  public Pane createVehiclePane(Vehicle vehicle, EventHandler<MouseEvent> bookNowHandler) {
    VBox pane = new VBox();
    pane.setPrefSize(paneWidth, paneHeight);
    pane.getStylesheets().add(getClass().getResource("/css/vehicles.css").toExternalForm());
    pane.getStyleClass().add("vehicle-pane");

    VBox brandModelYearBox = createBrandModelYearBox(vehicle);
    ImageView imageView = createImageView(vehicle);
    HBox priceBox = createPriceBox(vehicle);
    VBox separator = createSeparator();
    VBox colorFuelTypeBox = createVehicleSpecsBox(vehicle);
    Button bookNowButton = createBookNowButton(bookNowHandler);

    VBox.setMargin(imageView, new Insets(10, 0, 0, 0));

    Region spacer = new Region();
    VBox.setVgrow(spacer, Priority.ALWAYS);

    pane.getChildren()
        .addAll(
            brandModelYearBox,
            imageView,
            spacer,
            priceBox,
            separator,
            colorFuelTypeBox,
            bookNowButton);
    pane.setAlignment(Pos.CENTER);
    VBox.setVgrow(bookNowButton, Priority.NEVER);

    return pane;
  }

  /**
   * Creates an ImageView for the vehicle image.
   *
   * @param vehicle the vehicle to display
   * @return the ImageView containing the vehicle image
   */
  private ImageView createImageView(Vehicle vehicle) {
    ImageView vehicleImageView = new ImageView();
    vehicleImageView.getStyleClass().add("vehicle-image");
    vehicleImageView.setFitWidth(220);
    vehicleImageView.setFitHeight(270);
    vehicleImageView.setPreserveRatio(true);
    vehicleImageView.setSmooth(true);

    try {
      String imagePath = "/images and attribution/" + vehicle.getImage();
      Image image = imageCache.computeIfAbsent(imagePath, path -> loadImage(path));
      vehicleImageView.setImage(image);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return vehicleImageView;
  }

  /**
   * Loads and resizes the image from the specified path.
   *
   * @param path the path to the image file
   * @return the loaded and resized image
   */
  private Image loadImage(String path) {
    try {
      return new Image(getClass().getResourceAsStream(path), imageWidth, imageHeight, true, true);
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  /**
   * Creates a VBox containing the brand, model, and year of the vehicle.
   *
   * @param vehicle the vehicle to display
   * @return the VBox containing the brand, model, and year
   */
  private VBox createBrandModelYearBox(Vehicle vehicle) {
    Label brandModelLabel = new Label(vehicle.getBrand() + " " + vehicle.getModel());
    brandModelLabel.getStyleClass().add("vehicle-brand-model-label");

    Label yearLabel = new Label(String.valueOf(vehicle.getMakeYear()));
    yearLabel.getStyleClass().add("vehicle-year-label");

    VBox brandModelYearBox = new VBox(brandModelLabel, yearLabel);
    brandModelYearBox.setAlignment(Pos.TOP_LEFT);
    return brandModelYearBox;
  }

  /**
   * Creates an HBox containing the price per day of the vehicle.
   *
   * @param vehicle the vehicle to display
   * @return the HBox containing the price per day
   */
  private HBox createPriceBox(Vehicle vehicle) {
    String formattedPrice = String.format("$ %.2f", vehicle.getPricePerDay());
    Label priceLabel = new Label(formattedPrice);
    priceLabel.getStyleClass().add("vehicle-price-label");
    Label priceText = new Label("/ DAY");
    priceText.getStyleClass().add("vehicle-price-lab");
    HBox priceBox = new HBox(10, priceLabel, priceText);
    priceBox.setAlignment(Pos.CENTER);
    return priceBox;
  }

  /**
   * Creates a separator VBox.
   *
   * @return the VBox separator
   */
  private VBox createSeparator() {
    VBox separator = new VBox();
    separator.getStyleClass().add("separator");
    return separator;
  }

  /**
   * Creates a VBox containing the vehicle specifications (color, fuel type, speed).
   *
   * @param vehicle the vehicle to display
   * @return the VBox containing the vehicle specifications
   */
  private VBox createVehicleSpecsBox(Vehicle vehicle) {
    HBox colorBox = createSpecBox(FontAwesomeIcon.ADJUST, vehicle.getColour());
    HBox fuelBox = createSpecBox(FontAwesomeIcon.THERMOMETER_FULL, vehicle.getFuelType());
    HBox colorFuelTypeBox = new HBox(15, colorBox, fuelBox);
    colorFuelTypeBox.setAlignment(Pos.CENTER);

    HBox.setHgrow(colorBox, Priority.ALWAYS);
    HBox.setHgrow(fuelBox, Priority.ALWAYS);
    HBox.setHgrow(colorFuelTypeBox, Priority.ALWAYS);

    HBox speedBox = createSpeedSpecBox(vehicle);
    speedBox.setAlignment(Pos.CENTER);
    HBox.setHgrow(speedBox, Priority.ALWAYS);

    VBox specsBox = new VBox(5, colorFuelTypeBox, speedBox);
    specsBox.setAlignment(Pos.CENTER);
    VBox.setVgrow(colorFuelTypeBox, Priority.ALWAYS);
    VBox.setVgrow(speedBox, Priority.ALWAYS);

    VBox.setMargin(specsBox, new Insets(0, 0, 0, 30));

    return specsBox;
  }

  /**
   * Creates an HBox for a specific vehicle specification.
   *
   * @param icon the FontAwesomeIcon to use
   * @param text the text to display
   * @return the HBox containing the icon and text
   */
  private HBox createSpecBox(FontAwesomeIcon icon, String text) {
    FontAwesomeIconView iconView = new FontAwesomeIconView(icon);
    iconView.setSize("15");

    Label label = new Label(text);
    label.getStyleClass().add("vehicle-color-fueltype-label");
    label.setWrapText(false);
    label.setMaxWidth(Double.MAX_VALUE);

    HBox specBox = new HBox(5, iconView, label);
    specBox.setSpacing(5);
    specBox.setAlignment(Pos.CENTER);
    HBox.setHgrow(label, Priority.ALWAYS);
    return specBox;
  }

  /**
   * Creates an HBox for the speed specification.
   *
   * @param vehicle the vehicle to display
   * @return the HBox containing the speed icon and label
   */
  private HBox createSpeedSpecBox(Vehicle vehicle) {
    FontAwesomeIconView speedIcon = new FontAwesomeIconView(FontAwesomeIcon.DASHBOARD);
    speedIcon.setSize("15");

    Label speedLabel = new Label(vehicle.getEconomy() + " L/100kms");
    speedLabel.getStyleClass().add("vehicle-color-fueltype-label");
    speedLabel.setWrapText(false);
    speedLabel.setMaxWidth(Double.MAX_VALUE);

    HBox speedBox = new HBox(5, speedIcon, speedLabel);
    speedBox.setSpacing(5);
    speedBox.setAlignment(Pos.CENTER);
    HBox.setHgrow(speedLabel, Priority.ALWAYS);
    return speedBox;
  }

  /**
   * Creates a "Book Now" button with the specified event handler.
   *
   * @param bookNowHandler the event handler for the "Book Now" button
   * @return the "Book Now" button
   */
  private Button createBookNowButton(EventHandler<MouseEvent> bookNowHandler) {
    Button bookNowButton = new Button("Book Now");
    bookNowButton.getStyleClass().add("book-now-button");
    bookNowButton.setPrefWidth(200);
    bookNowButton.setOnMouseClicked(bookNowHandler);
    return bookNowButton;
  }
}
