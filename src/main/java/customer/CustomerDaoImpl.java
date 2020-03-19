package customer;

import model.Customer;
import model.Location;
import util.Database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CustomerDaoImpl implements CustomerDao {
    Database db = new Database();

    @Override
    public List<Customer> getAllCostumers() {
        List<Customer> customers = new ArrayList<Customer>();
        ResultSet rs = db.executeQuery("SELECT * FROM Customers");
        while(true) {
            try {
                if (!rs.next()) break;
                Customer cust = new Customer();
                cust.setId(UUID.fromString(rs.getString("Id")));
                cust.setCurrentBalance(rs.getFloat("CurrentBalance"));
                cust.setEmail(rs.getString("Email"));
                cust.setFirstName(rs.getString("FirstName"));
                cust.setLastName(rs.getString("LastName"));
                customers.add(cust);
            } catch (SQLException e) {
                System.err.println(e);
            }
        }
        return customers;
    }

    @Override
    public Customer getCustomer(String customerId) {
        ResultSet rs = db.executeQuery("SELECT * FROM Customers where Id="+customerId);
        try {
            rs.first();
            Customer cust = new Customer();
            cust.setCurrentBalance(rs.getFloat("CurrentBalance"));
            cust.setEmail(rs.getString("Email"));
            cust.setFirstName(rs.getString("FirstName"));
            cust.setLastName(rs.getString("LastName"));
            cust.setCurrentLocation((Location) rs.getObject("currentLocation"));
            return cust;
        } catch (SQLException e) {
            System.err.println(e);
        }
        return null;
    }

    @Override
    public float getCurrentBalance(String customerId) {
        ResultSet rs = db.executeQuery("SELECT CurrentBalance FROM Customers where Id="+customerId);
        try {
            return rs.getFloat("CurrentBalance");
        } catch (SQLException e) {
            System.err.println(e);
        }
        return 0;
    }

    @Override
    public boolean registerCustomer(Customer customer) {
       return db.insertQuery("INSERT INTO Customers (Id, FirstName, LastName, Email) VALUES ("
                +"'"+UUID.randomUUID()+"'"+","
                +"'"+customer.getFirstName()+"'"+","
                +"'"+customer.getLastName()+"'"+","
                +"'"+customer.getEmail()+"'"+")");

    }

    @Override
    public void addBalance(String customerId, float balance) {
        db.executeQuery("UPDATE Customers SET CurrentBalance="+balance+"WHERE Id="+customerId);
    }
}
