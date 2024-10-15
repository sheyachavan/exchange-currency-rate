package com.exchange.rates.endpoints.api;

import com.exchange.rates.model.Currency;
import com.exchange.rates.model.ExchangeRate;
import com.exchange.rates.repository.ExchangeRateRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class CurrencyEndpoint {
    ExchangeRateRepository exchangeRateRepository;

    public CurrencyEndpoint(ExchangeRateRepository exchangeRateRepository) {
        this.exchangeRateRepository = exchangeRateRepository;
    }

    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("I am ok...");
    }

    @PostMapping("/getExchangeRate")
    public @ResponseBody Optional<ExchangeRate> getExchangeRate(@RequestParam("fromCurr") String fromCurr, @RequestParam("toCurr") String toCurr ){
        Optional<ExchangeRate> exchangeRate = exchangeRateRepository.getExchangeRate(fromCurr,toCurr);
        return exchangeRate;
    }

    @PutMapping("/setExchangeRate")
     String setExchangeRate(
        @RequestParam("fromCurr") String fromCurr,
        @RequestParam("toCurr") String toCurr,
        @RequestParam("rate") Double rate
    ) {
         exchangeRateRepository.updateExchangeRate(fromCurr,toCurr,rate);
         return "success";

    }

}