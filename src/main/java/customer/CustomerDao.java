package customer;

import model.Customer;

import java.util.List;

public interface CustomerDao {
    public List<Customer> getAllCostumers();
    public float getCurrentBalance(String customerId);
    public Customer getCustomer(String customerId);
    public boolean registerCustomer(Customer customer);
    public void addBalance(String customerId, float balance);
}
