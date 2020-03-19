package Cars;

import model.Car;
import util.Database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class CarsDaoImpl implements CarsDao {
    Database db = new Database();

    @Override
    public Car getCar(String carId) {
        ResultSet rs = db.executeQuery("SELECT * FROM Cars where Id=" + carId);
        try {
            rs.first();
            Car car = new Car();
            car.setAvailable(rs.getBoolean("isAvailable"));
            car.setCompany(rs.getString("Company"));
            car.setModel(rs.getString("Model"));
            car.setRent(rs.getFloat("Rent"));
            car.setId(UUID.fromString(rs.getString("Id")));
            return car;
        } catch (SQLException e) {
            System.err.println(e);
        }
        return null;
    }

    @Override
    public boolean addCar(Car car) {
        return db.insertQuery("INSERT INTO Customers (Id, Company, Model, Rent, IsAvailable) VALUES ("
                + "'" + UUID.randomUUID() + "'" + ","
                + "'" + car.getCompany() + "'" + ","
                + "'" + car.getModel() + "'" + ","
                + "'" + car.getRent() + "'" + ","
                + car.isAvailable() + ")");
    }

    @Override
    public Car getCarByDistance(double lat, double lng) {
        ResultSet rs = db.executeQuery("SELECT\n" +
                "    Id, (\n" +
                "      6371 * acos (\n" +
                "      cos ( radians("+lat+") )\n" +
                "      * cos( radians( lat ) )\n" +
                "      * cos( radians( lng ) - radians("+lng+") )\n" +
                "      + sin ( radians("+lat+") )\n" +
                "      * sin( radians( lat ) )\n" +
                "    )\n" +
                ") AS distance\n" +
                "FROM table\n" +
                "HAVING distance < 1\n" +
                "ORDER BY distance\n" +
                "LIMIT 1;");
        try {
            return getCar(rs.getString("Id"));
        } catch (SQLException e) {
            return null;
        }
    }

    @Override
    public void UpdateAvailability(UUID carId, boolean avail) {
        db.executeQuery("UPDATE Cars SET isAvailable=" + avail + "WHERE Id=" + carId);
    }
}
