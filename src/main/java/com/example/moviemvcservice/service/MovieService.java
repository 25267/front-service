package com.example.moviemvcservice.service;

import com.example.moviemvcservice.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

@Service
public class MovieService {

    @Autowired
    private RestTemplate restTemplate;

    public List<CatalogItem> index() {



        ResponseEntity<List<CatalogItem>> rateResponse =
                restTemplate.exchange("http://localhost:8087/movieCatalog/",
                        HttpMethod.GET, null, new ParameterizedTypeReference<List<CatalogItem>>() {
                        });
        List<CatalogItem> movies = rateResponse.getBody();

        return movies;
    }


    public Account login(String email, String nicname) {

        return  restTemplate.getForObject("http://localhost:8087/movieCatalog/login?email=" + email + "&nickname=" + nicname, Account.class);

    }

    public AccountRecommendation getRecommendedMovies(Long accountId) {

       return restTemplate.getForObject("http://localhost:8087/movieCatalog/recommendation?accountId=" + accountId, AccountRecommendation.class);
    }

    public AccountBookings getBookings(Long accountId) {

        return restTemplate.getForObject("http://localhost:8087/movieCatalog/booking?accountId=" + accountId, AccountBookings.class);
    }

    public void  bookMovie(Long userId, Long movieId) {
        restTemplate.getForObject("http://localhost:8087/movieCatalog/bookMovie?userId=" + userId + "&movieId=" + movieId, String.class);
    }

    public void  recMovie(Long userId, Long movieId) {
        restTemplate.getForObject("http://localhost:8087/movieCatalog/recommendMovie?userId=" + userId + "&movieId=" + movieId, String.class);
    }

    public CatalogItem getMovie(Long movieId) {

        return restTemplate.getForObject("http://localhost:8087/movieCatalog/" + movieId, CatalogItem.class);
    }


    public CatalogItem show(Long id) {
        return  restTemplate.getForObject("http://localhost:8087/movieCatalog/" + id , CatalogItem.class);
    }
}
