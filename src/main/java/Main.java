import Cars.CarsService;
import com.linecorp.armeria.common.HttpResponse;
import com.linecorp.armeria.common.HttpStatus;
import com.linecorp.armeria.server.Server;
import com.linecorp.armeria.server.ServerBuilder;
import customer.CustomerService;
import util.Database;

import java.util.concurrent.CompletableFuture;

public class Main {
    public static void main(String[] args) {
        // Start the server
        ServerBuilder sb = Server.builder();
        sb.http(8080);
        // add the services
        // Health check if server is up
        sb.service("/", (ctx, res) -> HttpResponse.of("Hello World!", HttpStatus.OK));
        sb.annotatedService(new CustomerService());
        sb.annotatedService(new CarsService());
        Server server = sb.build();
        CompletableFuture<Void> future = server.start();
        future.join();
    }
}