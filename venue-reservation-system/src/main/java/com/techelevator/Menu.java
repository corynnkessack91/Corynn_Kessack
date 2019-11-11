package com.techelevator;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

import com.techelevator.JDBCDAO.JDBCVenueDAO;
import com.techelevator.JDBCDAO.Reservation;
import com.techelevator.JDBCDAO.Space;
import com.techelevator.JDBCDAO.Venue;
import com.techelevator.JDBCDAO.VenueDAO;

public class Menu {

	private PrintWriter out;
	private Scanner in;
	private Venue venue;
	private JDBCVenueDAO dao;
	private VenueDAO venueDAO;
	private Reservation reservation;

	public Menu(InputStream input, OutputStream output) {
		this.out = new PrintWriter(output);
		this.in = new Scanner(input);
	}

	public String getUserInput() {
		return in.nextLine();
	}

	public LocalDate getUserStartDate() {
		DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("MM/dd/yyyy");
		LocalDate date;
		while (true) {
			System.out.println("When Do You Need The Space? (MM/DD/YYYY)\n");
			String input = in.nextLine();
			try {
				date = LocalDate.parse(input, dateFormat);
				break;
			} catch (DateTimeParseException e) {
				System.out.println("Please enter a valid date!\n");
			}
		}

		return date;
	}
	
	public String runDisplayMessage() {
		return "What would you like to do?";
	}
	
	public String handleVenuesDisplayMessage() {
		return "Which venue would you like to view?";
	}
	
	public String invalidResponse() {
		return "Invalid Response";
	}
	
	public String postDetailsMenuDisplayMessage() {
		return "What would you like to do next?";
	}
	
	public String handleSpacesDisplayMessage() {
		return "What Would You Like To Do Next?";
	}
	
	public String handleOpenSpacesDisplayMessage() {
		return "How Many People Will Be In Attendance? \\n";
	}
	
	public String thanksForStoppingBy() {
		return "Thanks For Stopping By\\n";
	}

	public LocalDate getUserEndDate() {
		DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("MM/dd/yyyy");
		LocalDate date;
		while (true) {
			System.out.println("How Many Days Will You Need The Space? \n");
			String input = in.nextLine();
			try {
				date = getUserStartDate().plusDays(1);
				break;
			} catch (DateTimeParseException e) {
				System.out.println("Please enter a valid date!\n");
			}

		}
		return date;
	}
	
	public int getChoice() {
		return Integer.parseInt(getUserInput());
	}

	public int getUserAttendees() {
		return in.nextInt();
	}

	public void displayAllVenues(List<Venue> venues) {
		for (int i = 0; i < venues.size(); i++) {
			Venue venue = venues.get(i);
			System.out.println(i + 1 + ") " + venue.getName());
		}
	}

	public void seeVenueDetails(Venue venue) {
		System.out.println();
		System.out.println("Name: " + venue.getName());
		System.out.println("Location: " + venue.getCityName() + ", " + venue.getCityAbbr());
		System.out.println("Category: " + venue.getCategory());
		System.out.println();
		System.out.println(venue.getDescription());
	}

	public void seeSpacesForVenue(List<Space> spaces) {
		System.out.println();
		System.out.printf("%-43s %-5s %-5s %-15s %-5s \n", "Name", "Open", "Close", "Daily Rate", "Max. Occupancy");
		for (int i = 0; i < spaces.size(); i++) {
			Space space = spaces.get(i);
			System.out.printf("#%s %-40s %-5s %-5s $%-15s %-5s\n", (i + 1), space.getName(), space.getOpen_from(),
					space.getOpen_to(), space.getDaily_rate(), space.getMax_occupancy());
		}

	}


	public String getChoiceFromUserInput(String[] options) {
		String choice = null;
		String userInput = in.nextLine();
		while (true) {
			try {
				int selectedOption = Integer.valueOf(userInput);
				if (selectedOption <= options.length) {
					choice = options[selectedOption - 1];
				}
			} catch (NumberFormatException e) {
			}

			if (choice == null) {
				out.println("\n" + userInput + " is not a valid option!\n");
			} else {
				return choice;
			}

		}
	}

	public String getChoiceFromOptions(String[] options) {
		String choice = null;
		while (choice == null) {
			displayMenuOptions(options);
			choice = getChoiceFromUserInput(options);
		}
		return choice;
	}

	private void displayMenuOptions(Object[] options) {
		out.println();
		for (int i = 0; i < options.length; i++) {
			int optionNum = i + 1;
			out.println(optionNum + ") " + options[i]);
		}
		out.print("\nPlease choose an option:\n");
		out.flush();
	}

	public void displayVenueDescription() {
		System.out.println(venue.getName());
	}

	private List<Venue> getAllVenues() {
		return venueDAO.getAllVenues();
	}

}
