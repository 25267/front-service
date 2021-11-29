package com.example.moviemvcservice.controller;

import com.example.moviemvcservice.models.*;
import com.example.moviemvcservice.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping("/movies")
public class MovieController {

    @Autowired
    private MovieService movieService;

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("movies", movieService.index());
        User user = User.getUser();
        model.addAttribute("user", user);
        return  "movies/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") Long id, Model model) {


        if (User.getUser().getName() != null) {

            if (movieService.getRecommendedMovies(User.getUser().getId()) != null) {

                List<CatalogItem> recItems = new ArrayList<>();

                User user = User.getUser();
                user.setRecommendations(movieService.getRecommendedMovies(User.getUser().getId()).getRecommendations());

//                for (Recommendation rec : user.getRecommendations()) {
//
//                    recItems.add(movieService.getMovie(rec.getMovie_id()));
//                }

                for (Recommendation rec : User.getUser().getRecommendations()) {

                    if (rec.getMovie_id().equals(id)) {
                        model.addAttribute("rect", "yes");
                    }
                }
            }


            //
            if (movieService.getBookings(User.getUser().getId()) != null) {

                List<CatalogItem> items = new ArrayList<>();

                User user = User.getUser();
                user.setBookings(movieService.getBookings(User.getUser().getId()).getBookings());

//                for (Booking book : user.getBookings()) {
//
//                    items.add(movieService.getMovie(book.getMovie_id()));
//                }

                for (Booking rec : User.getUser().getBookings()) {

                    if (rec.getMovie_id().equals(id)) {
                        model.addAttribute("booked", "yes");
                    }
                }
            }

        } else  {
            model.addAttribute("booked", "no user");
            model.addAttribute("rect", "no user");
        }

        model.addAttribute("movie", movieService.show(id));
        return  "movies/show";
    }

    @GetMapping("/loginPage")
    public  String loginPage(@ModelAttribute("account") Account account) {

        return "movies/login";
    }

    @GetMapping("/recommendation")
    public String recommendation(Model model) {

        if (movieService.getRecommendedMovies(User.getUser().getId()) != null) {

            List<CatalogItem> items = new ArrayList<>();

            User user = User.getUser();
            user.setRecommendations(movieService.getRecommendedMovies(User.getUser().getId()).getRecommendations());

            for (Recommendation rec : user.getRecommendations()) {

                items.add(movieService.getMovie(rec.getMovie_id()));
            }

            model.addAttribute("movies", items);
        }

        model.addAttribute("user", User.getUser());

        return "movies/recommendation";

    }

    @GetMapping("/{id}/book")
    public String book( @PathVariable("id") Long id) {

        User user = User.getUser();

        movieService.bookMovie(user.getId(), id);

        return "redirect:/movies/";
    }

    @GetMapping("/{id}/rec")
    public String rec( @PathVariable("id") Long id) {

        User user = User.getUser();

        movieService.recMovie(user.getId(), id);

        return "redirect:/movies/";
    }

    @GetMapping("/booking")
    public String booking(Model model) {

        if (movieService.getBookings(User.getUser().getId()) != null) {

            List<CatalogItem> items = new ArrayList<>();

            User user = User.getUser();
            user.setBookings(movieService.getBookings(User.getUser().getId()).getBookings());

            for (Booking book : user.getBookings()) {

                items.add(movieService.getMovie(book.getMovie_id()));
            }

            model.addAttribute("movies", items);
        }

        model.addAttribute("user", User.getUser());

        return "movies/booking";

    }

    @GetMapping("/login")
    public String login(@ModelAttribute("account") Account account, Model model) {

        if (movieService.login(account.getEmail(), account.getNickname()) != null) {


            Account account1 = movieService.login(account.getEmail(), account.getNickname());

            System.out.println("idisequal: " + account1.getId());
            User user = User.getUser();
            user.setId(account1.getId());
            user.setEmail(account1.getEmail());
            user.setName(account1.getNickname());



            model.addAttribute("user", user);

        } else {
            return "redirect:/movies/loginPage";
        }

        model.addAttribute("movies", movieService.index());
        return  "movies/index";
    }

    @GetMapping("/signOut")
    public String signOut() {
        User user = User.getUser();
        user.setName(null);
        user.setEmail(null);

        return "redirect:/movies";
    }


}
