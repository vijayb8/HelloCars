package Cars;

import model.Car;

import java.util.ArrayList;
import java.util.UUID;

public interface CarsDao {
    public Car getCar(String carId);
    public boolean addCar(Car car);
    public Car getCarByDistance(double lat, double lng);
    public void UpdateAvailability(UUID carId, boolean avail);
}
