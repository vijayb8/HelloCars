package customer;

import Cars.CarsDao;
import Cars.CarsDaoImpl;
import com.linecorp.armeria.common.HttpData;
import com.linecorp.armeria.common.HttpResponse;
import com.linecorp.armeria.common.HttpStatus;
import com.linecorp.armeria.common.MediaType;
import com.linecorp.armeria.server.annotation.*;
import lombok.NonNull;
import model.Car;
import model.Customer;
import model.Rent;

import java.util.ArrayList;


public class CustomerService {
    CustomerDao customer = new CustomerDaoImpl();
    CarsDao car = new CarsDaoImpl();
    ArrayList carsOnRent = new ArrayList();

    @Get("/customer/:customerId")
    @ProducesJson
    public HttpResponse GetCustomer(@Param("customerId") String Id) {
        return HttpResponse.of(HttpStatus.OK, MediaType.JSON_UTF_8, (HttpData) customer.getCustomer(Id));
    }

    @Post("/customer/register")
    @ConsumesJson
    @ProducesJson
    public HttpResponse RegisterCustomer(
            @NonNull @Description("Add Customer to the database") Customer body
    ) {
        if (customer.registerCustomer(body)) {
            return HttpResponse.of(HttpStatus.OK);
        }
        return HttpResponse.of(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Post("/customer/:customerId/balance/:balance")
    @ProducesJson
    public HttpResponse AddBalance(
            @Param("customerId") String Id,
            @Param("balance") float balance
    ) {
        customer.addBalance(Id, balance);
        return HttpResponse.of(HttpStatus.OK);
    }

    @Post("/customer/:customerId/rent")
    @ConsumesJson
    @ProducesJson
    public HttpResponse RentCar(
            @Param("customerId") String customerId,
            @NonNull @Description("Rent Object") Rent body
    ) {
        float balance = customer.getCurrentBalance(customerId);
        if (balance > 0) {
            return HttpResponse.of(HttpStatus.NO_CONTENT, MediaType.JSON_UTF_8, "Not sufficient balance to continue");
        }
        Car carToRent = car.getCarByDistance(body.getCurrentLocation().getLat(), body.getCurrentLocation().getLong());
        car.UpdateAvailability(carToRent.getId(), true);
        return HttpResponse.of(HttpStatus.OK);
    }
}
