package com.example.moviemvcservice.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountRecommendation {

    private List<Recommendation> recommendations;
}
