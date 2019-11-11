package com.techelevator;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;

import com.techelevator.JDBCDAO.JDBCReservationDAO;
import com.techelevator.JDBCDAO.JDBCSpaceDAO;
import com.techelevator.JDBCDAO.JDBCVenueDAO;
import com.techelevator.JDBCDAO.Reservation;
import com.techelevator.JDBCDAO.ReservationDAO;
import com.techelevator.JDBCDAO.Space;
import com.techelevator.JDBCDAO.SpaceDAO;
import com.techelevator.JDBCDAO.Venue;
import com.techelevator.JDBCDAO.VenueDAO;

public class ExcelsiorCLI {

	private static final String MAIN_MENU_OPTION_VENUES = " List Venues";
	private static final String MAIN_MENU_OPTION_QUIT = " Quit";
	private static final String[] MAIN_MENU_MENU_OPTIONS = new String[] { MAIN_MENU_OPTION_VENUES,
			MAIN_MENU_OPTION_QUIT };

	private static final String VENUE_DETAILS_VIEW_SPACE = " View Spaces";
	private static final String VENUE_DETAILS_SEARCH_RESERVATION = " Search For Reservation";
	private static final String VENUE_DETAILS_RETURN_PREV_SCREEN = " Return To Previous Screen";
	private static final String[] VENUE_DETAIL_MENU = new String[] { VENUE_DETAILS_VIEW_SPACE,
			VENUE_DETAILS_SEARCH_RESERVATION, VENUE_DETAILS_RETURN_PREV_SCREEN };
	
	private static final String SPACE_RESERVE_SPACE = "Reserve A Space";
	private static final String SPACE_RETURN_PREV_SCREEN = "Return To Previous Screen";
	private static final String[] VENUE_SPACE_RESERVE_SPACE = new String[] {SPACE_RESERVE_SPACE, SPACE_RETURN_PREV_SCREEN};

	private ReservationDAO reservationDAO;
	private SpaceDAO spaceDAO;
	private VenueDAO venueDAO;
	private Menu menu;

	
	public static void main(String[] args) {
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setUrl("jdbc:postgresql://localhost:5432/excelsior-venues");
		dataSource.setUsername("postgres");
		dataSource.setPassword("postgres1");

		ExcelsiorCLI application = new ExcelsiorCLI(dataSource);
		application.run();
	}

	public ExcelsiorCLI(DataSource datasource) {
		this.menu = new Menu(System.in, System.out);
		reservationDAO = new JDBCReservationDAO(datasource);
		spaceDAO = new JDBCSpaceDAO(datasource);
		venueDAO = new JDBCVenueDAO(datasource);
	}

	public void run() {
		menu.runDisplayMessage();
		String choice = (String) menu.getChoiceFromOptions(MAIN_MENU_MENU_OPTIONS);
		if (choice == MAIN_MENU_OPTION_VENUES) {
			handleVenues();
		} else if (choice == MAIN_MENU_OPTION_QUIT) {
			menu.thanksForStoppingBy();

		}

	}

	private void handleVenues() {
		menu.handleVenuesDisplayMessage();

		List<Venue> allVenues = getAllVenues();
		menu.displayAllVenues(allVenues);

		int choice = menu.getChoice();
		
		Venue selectedVenues = venueDAO.getSpecificVenue((long) choice);
		
		if(choice == menu.getChoice()) {
				
			menu.seeVenueDetails(selectedVenues);

			postDetailsMenu(selectedVenues);
		}else {
			menu.invalidResponse();
		}
		
	}
	
	private void postDetailsMenu(Venue venue) {
		System.out.println();
		menu.postDetailsMenuDisplayMessage();
		String choice = (String) menu.getChoiceFromOptions(VENUE_DETAIL_MENU);
		if (choice == VENUE_DETAILS_VIEW_SPACE) {
			handleSpaces();
		} else if (choice == VENUE_DETAILS_SEARCH_RESERVATION) {
			handleOpenSpaces();
		}
	}

	private void handleSpaces() {
		
		List<Space> selectedVenueSpaces = spaceDAO.getAllSpaces((long) menu.getChoice());
		menu.seeSpacesForVenue(selectedVenueSpaces);
		menu.handleSpacesDisplayMessage();
		String choice = (String) menu.getChoiceFromOptions(VENUE_SPACE_RESERVE_SPACE);
		if(choice == SPACE_RESERVE_SPACE) {
			handleOpenSpaces();
		}

	}
	
	private void handleOpenSpaces() {
		Long choiceInput = Long.parseLong(menu.getUserInput());
		
		LocalDate choiceDate =  menu.getUserStartDate();
		
		LocalDate choiceEndDate = menu.getUserEndDate();
		
		menu.handleOpenSpacesDisplayMessage();
		int choiceAttend = Integer.parseInt(menu.getUserInput());
		
		spaceDAO.getOpenSpaces(choiceInput, choiceDate, choiceEndDate, choiceAttend);
	}

	private List<Venue> getAllVenues() {
		return venueDAO.getAllVenues();
	}


}
