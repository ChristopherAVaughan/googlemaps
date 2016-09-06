package com.directionswithgooglemaps;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Scanner;

/**
 * Sample class that makes simple request to Google Maps to get directions.
 *
 * @author Chris Vaughan
 * @since 2016-09-06
 */
public class RestRequest {

	/**
	 * The URL of the API we want to connect to.
	 */
	protected static String endpoint = "https://maps.googleapis.com/maps/api/directions/";

	/**
	 * The character set to use when encoding URL parameters.
	 */
	protected static String charset = "UTF-8";

	/**
	 * API key used to make requests of the API.
	 */
	protected static String key = "AIzaSyDGP5PNMHqUms__GLT_Org_lRAPxe-qIx8";

	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		
		boolean repeatQuestions = true;
		
		do {
			
			System.out.println("Enter in Yes to get directions or No to exit the application:");	
			String userInput = sc.nextLine();
			
			switch (userInput.toLowerCase()) {
			case "no":
				System.out.println("Thank you, come again!");
				repeatQuestions = false;
				break;
			case "yes":
				System.out.println("Ok, please proceed!");
				repeatQuestions = false;
				questions(sc);
				break;
			default:
					System.out.println("WHAT!");
					break;
			}

			
		} while (repeatQuestions);
		


	}

	private static void questions(Scanner sc) {
		try {

			
			//Request user to identify their starting point
			System.out.println("Please enter in your starting point?");
			String origin = sc.nextLine();

			//Request user to identify their end point for directions
			System.out.println("Please enter in your destintion?");
			String destination = sc.nextLine();

			//Request the user to specify the language they want the directions in
			System.out.println("What language do you want the directions in?");
			String language = sc.nextLine();

			// The return type of the response xml|json
			String returnType = "json";

			// creates the url parameters as a string encoding them with the defined charset
			String queryString = String.format("origin=%s&destination=%s&key=%s&language=%s",
					URLEncoder.encode(origin, charset),
					URLEncoder.encode(destination, charset), 
					URLEncoder.encode(key, charset),
					URLEncoder.encode(language, charset));

			// creates a new URL out of the endpoint, returnType, and
			// querystring
			URL googleDirections = new URL(endpoint + returnType + "?" + queryString);
			HttpURLConnection connection = (HttpURLConnection) googleDirections.openConnection();
			connection.setRequestMethod("GET");

			// if we did not get a 200 (success) throw an exception
			if (connection.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + connection.getResponseCode());
			}

			// read response into buffer
			BufferedReader br = new BufferedReader(new InputStreamReader((connection.getInputStream())));

			// loop of buffer line by the line until it return null, meaning
			// there are no more lines
			while (br.readLine() != null) {
				// print out each line to the screen
				System.out.println(br.readLine());
			}
			
			//close connection to API
			connection.disconnect();

		} catch (MalformedURLException e) {

			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();
		}
	}

}
