import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class RestaurantService {
    private static final List<Restaurant> restaurants = new ArrayList<>();

    public Restaurant findRestaurantByName(String restaurantName){
        for (Restaurant restaurant : restaurants) {
            if (restaurant.getName().equals(restaurantName)) {
                return restaurant;
            }
        }
        return null;
    }

    public Restaurant addRestaurant(String name, String location, LocalTime openingTime, LocalTime closingTime) {
        Restaurant newRestaurant = new Restaurant(name, openingTime, closingTime);
        restaurants.add(newRestaurant);
        return newRestaurant;
    }
    public void removeRestaurant(String restaurantName) throws RestaurantNotFoundException {
        Restaurant restaurantToBeRemoved = findRestaurantByName(restaurantName);
        if (restaurantToBeRemoved == null)
            throw new RestaurantNotFoundException(restaurantName);
        restaurants.remove(restaurantToBeRemoved);
    }
    public List<Restaurant> getRestaurants() {

        return restaurants;

    }
}

class RestaurantNotFoundException extends Exception {
    public RestaurantNotFoundException(String restaurantName) {
        super(restaurantName);
    }
}
