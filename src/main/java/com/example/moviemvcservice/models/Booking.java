package com.example.moviemvcservice.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@NoArgsConstructor
@AllArgsConstructor


public class Booking {


    private Long id;

    private Long account_id;
    private Long movie_id;


}
