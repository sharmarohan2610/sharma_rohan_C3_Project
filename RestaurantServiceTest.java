import org.junit.jupiter.api.*;
import java.time.LocalTime;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;

class RestaurantServiceTest {
    RestaurantService service = new RestaurantService();
    Restaurant restaurant;

    @BeforeEach
    public void setupRestaurant() {
        LocalTime openingTime = LocalTime.parse("10:30:00");
        LocalTime closingTime = LocalTime.parse("22:00:00");
        restaurant = service.addRestaurant("Amelie's cafe", "Chennai", openingTime, closingTime);
        restaurant.addToMenu("Sweet corn soup", 119);
        restaurant.addToMenu("Vegetable lasagne", 269);
    }

    @Test
        public void searching_for_existing_restaurant_should_return_expected_restaurant_object() {
        String restaurantName = "Amelie's cafe";
        Restaurant foundRestaurant = service.findRestaurantByName(restaurantName);
        assertNotNull(foundRestaurant);
        assertEquals(restaurantName, foundRestaurant.getName());
    }
    @Test
        public void searching_for_non_existing_restaurant_should_throw_exception() {
        String restaurantName = "Pantry d'or";
        assertThrows(RestaurantNotFoundException.class, () -> service.removeRestaurant(restaurantName));
    }

    @Test
    @Timeout(5) // Set a timeout of 5 seconds for this test
    public void remove_restaurant_should_reduce_list_of_restaurants_size_by_1() throws RestaurantNotFoundException {
        int initialNumberOfRestaurants = service.getRestaurants().size();
        String restaurantName = "Amelie's cafe";
        service.removeRestaurant(restaurantName);
        assertEquals(initialNumberOfRestaurants - 1, service.getRestaurants().size());
        assertThrows(RestaurantNotFoundException.class, () -> service.findRestaurantByName(restaurantName));
    }

    @Test
    public void removing_restaurant_that_does_not_exist_should_throw_exception() {
        String nonExistingRestaurantName = "Pantry d'or";
        assertThrows(RestaurantNotFoundException.class, () -> service.removeRestaurant(nonExistingRestaurantName));
    }

    @Test
    public void add_restaurant_should_increase_list_of_restaurants_size_by_1() {
        int initialNumberOfRestaurants = service.getRestaurants().size();
        service.addRestaurant("Pumpkin Tales", "Chennai", LocalTime.parse("12:00:00"), LocalTime.parse("23:00:00"));
        assertEquals(initialNumberOfRestaurants + 1, service.getRestaurants().size());
    }

    @Test
    public void calculate_order_value_should_return_correct_value_for_selected_items() {
        List<String> itemNames = new ArrayList<>();
        itemNames.add("Sweet corn soup");
        itemNames.add("Vegetable lasagne");

        int orderValue = restaurant.calculateOrderValue(itemNames);
        assertEquals(388, orderValue);
    }

    @Test
    public void calculate_order_value_should_ignore_invalid_item_names() {
        List<String> itemNames = new ArrayList<>();
        itemNames.add("Sweet corn soup");
        itemNames.add("Invalid item");

        int orderValue = restaurant.calculateOrderValue(itemNames);
        assertEquals(119, orderValue);
    }

    @Test
    public void calculate_order_value_should_return_0_for_empty_item_list() {
        List<String> itemNames = new ArrayList<>();

        int orderValue = restaurant.calculateOrderValue(itemNames);
        assertEquals(0, orderValue);
    }
}