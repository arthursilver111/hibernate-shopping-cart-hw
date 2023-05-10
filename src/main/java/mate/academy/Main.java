package mate.academy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import mate.academy.dao.ShoppingCartDao;
import mate.academy.dao.TicketDao;
import mate.academy.dao.UserDao;
import mate.academy.dao.impl.ShoppingCartDaoImpl;
import mate.academy.dao.impl.TicketDaoImpl;
import mate.academy.dao.impl.UserDaoImpl;
import mate.academy.lib.Injector;
import mate.academy.model.*;
import mate.academy.service.*;

public class Main {
    private static final Injector injector = Injector.getInstance("mate");
    private static MovieService movieService = (MovieService) injector
            .getInstance(MovieService.class);
    private static CinemaHallService cinemaHallService = (CinemaHallService) injector
            .getInstance(CinemaHallService.class);
    private static MovieSessionService movieSessionService = (MovieSessionService) injector
            .getInstance(MovieSessionService.class);

    private static ShoppingCartService shoppingCartService = (ShoppingCartService) injector
            .getInstance(ShoppingCartService.class);
    private static UserService userService = (UserService) injector
            .getInstance(UserService.class);

    public static void main(String[] args) {
        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);
        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);

        CinemaHall firstCinemaHall = new CinemaHall();
        firstCinemaHall.setCapacity(100);
        firstCinemaHall.setDescription("first hall with capacity 100");

        CinemaHall secondCinemaHall = new CinemaHall();
        secondCinemaHall.setCapacity(200);
        secondCinemaHall.setDescription("second hall with capacity 200");

        cinemaHallService.add(firstCinemaHall);
        cinemaHallService.add(secondCinemaHall);

        System.out.println(cinemaHallService.getAll());
        System.out.println(cinemaHallService.get(firstCinemaHall.getId()));

        MovieSession tomorrowMovieSession = new MovieSession();
        tomorrowMovieSession.setCinemaHall(firstCinemaHall);
        tomorrowMovieSession.setMovie(fastAndFurious);
        tomorrowMovieSession.setShowTime(LocalDateTime.now().plusDays(1L));

        MovieSession yesterdayMovieSession = new MovieSession();
        yesterdayMovieSession.setCinemaHall(firstCinemaHall);
        yesterdayMovieSession.setMovie(fastAndFurious);
        yesterdayMovieSession.setShowTime(LocalDateTime.now().minusDays(1L));

        movieSessionService.add(tomorrowMovieSession);
        movieSessionService.add(yesterdayMovieSession);

        System.out.println(movieSessionService.get(yesterdayMovieSession.getId()));
        System.out.println(movieSessionService.findAvailableSessions(
                fastAndFurious.getId(), LocalDate.now()));

        User userBob = new User("art@gmail.com", "1234567");
        userService.add(userBob);

        shoppingCartService.registerNewShoppingCart(userService.findByEmail("art@gmail.com").get());
        System.out.println(shoppingCartService.getByUser(userService.findByEmail("art@gmail.com").get()));
    }
}
