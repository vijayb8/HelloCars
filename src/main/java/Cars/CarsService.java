package Cars;

import com.linecorp.armeria.common.HttpData;
import com.linecorp.armeria.common.HttpResponse;
import com.linecorp.armeria.common.HttpStatus;
import com.linecorp.armeria.common.MediaType;
import com.linecorp.armeria.server.annotation.*;
import lombok.NonNull;
import model.Car;

public class CarsService {
    CarsDao cars = new CarsDaoImpl();

    @Get("/cars/:carId")
    @ProducesJson
    public HttpResponse GetCustomer(@Param("carId") String Id) {
        return HttpResponse.of(HttpStatus.OK, MediaType.JSON_UTF_8, (HttpData) cars.getCar(Id));
    }

    @Post("/cars/add")
    @ConsumesJson
    @ProducesJson
    public HttpResponse AddCar(
            @NonNull @Description("Add Customer to the database") Car body
    ) {
        if (cars.addCar(body)) {
            return HttpResponse.of(HttpStatus.OK);
        }
        return HttpResponse.of(HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
