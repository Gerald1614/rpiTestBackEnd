package com.rpi.test1.stock;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.time.ZoneId;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.google.gson.Gson;
import com.rpi.test1.gpio.GpioController;

@Component
public class CfltStock {
    static LocalDate currentdate = LocalDate.now(ZoneId.systemDefault()).minusDays(1);
  
    private static final String Authorization_Bearer =  "6JcSQdggEgVyMlZOzZQVfSE7pVHa_MI9";


    
	@Autowired
	GpioController gpioController;
	
    Gson gson = new Gson();   


	public StockInfo cfltStock(String stockName, String stockDate) throws IOException, InterruptedException {
		
		String CfltStockAddress = "https://api.polygon.io/v1/open-close/" + stockName +"/"+ stockDate +"?adjusted=true";
		var client = HttpClient.newHttpClient();


		var request = HttpRequest.newBuilder(
		       URI.create(CfltStockAddress))
		   .header("accept", "application/json")
		   .header("Authorization", "Bearer " + Authorization_Bearer)
		   .build();

		var response = client.send(request, HttpResponse.BodyHandlers.ofString());
		
	    StockInfo stockInfo = gson.fromJson(response.body(), StockInfo.class);  
	    System.out.println(CfltStockAddress);
		System.out.println(stockInfo.toString());
	    return stockInfo;


		
	};
	
}
