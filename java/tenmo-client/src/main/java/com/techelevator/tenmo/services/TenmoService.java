package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.AuthenticatedUser;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

public class TenmoService {

    private RestTemplate restTemplate = new RestTemplate();
    private String BASE_URL;
    private AuthenticatedUser currentUser;
    public static String AUTH_TOKEN = "";


    public TenmoService(String url, AuthenticatedUser currentUser) {
    BASE_URL = url;
    this.currentUser = currentUser;
    }

    public BigDecimal getBalance() {
        BigDecimal balance = new BigDecimal(0);

        try {
            balance = restTemplate.exchange(BASE_URL + "balance/" + currentUser.getUser().getId(), HttpMethod.GET, makeAuthEntity(), BigDecimal.class).getBody();
            System.out.println("Your current account balance is: $" + balance);
        } catch (RestClientException e) {
            System.out.println("Error getting user balance.");
        }
        return balance;
    }

    private HttpEntity makeAuthEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(currentUser.getToken());
        HttpEntity entity = new HttpEntity<>(headers);
        return entity;
    }
}
