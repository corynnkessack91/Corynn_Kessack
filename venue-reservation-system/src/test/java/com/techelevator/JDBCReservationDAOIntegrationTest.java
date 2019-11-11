package com.techelevator;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import javax.sql.DataSource;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.techelevator.JDBCDAO.JDBCReservationDAO;
import com.techelevator.JDBCDAO.JDBCVenueDAO;
import com.techelevator.JDBCDAO.Reservation;
import com.techelevator.JDBCDAO.ReservationDAO;
import com.techelevator.JDBCDAO.Venue;
import com.techelevator.JDBCDAO.VenueDAO;

public class JDBCReservationDAOIntegrationTest {

	private static SingleConnectionDataSource dataSource;

	JdbcTemplate jdbcTemplate;
	private ReservationDAO dao;
	
	@BeforeClass
	public static void setupDataSource() {
		dataSource = new SingleConnectionDataSource();
		dataSource.setUrl("jdbc:postgresql://localhost:5432/excelsior-venues");
		dataSource.setUsername("postgres");
		dataSource.setPassword("postgres1");
		dataSource.setAutoCommit(false);
	}

	@Before
	public void setup() {
		jdbcTemplate = new JdbcTemplate(dataSource);
		dao = new JDBCReservationDAO(dataSource);

	}
	
	@Test
	public void create_Reservation_test() {
		Reservation reservation = testToReservation();
		String insertSql = "INSERT INTO reservation ( space_id, name, start_date, end_date) VALUES ( ?, ?, ?, ?) RETURNING id";
		SqlRowSet rows = jdbcTemplate.queryForRowSet(insertSql, reservation.getSpaceId(), reservation.getStartDate(), reservation.getEndDate());
		rows.next();
		reservation.setReservationId(rows.getLong("id"));
		
		List<Reservation> returnedReservation = (List<Reservation>) dao.makeReservation(reservation);
	}
	
	@AfterClass
	public static void closeDataSource() throws SQLException {
		dataSource.destroy();
	}

	@After
	public void rollback() throws SQLException {
		dataSource.getConnection().rollback();
	}

	protected DataSource getDataSource() {
		return dataSource;
	}
	
	private Reservation testToReservation() {
		Reservation reservation = new Reservation();
		reservation.setSpaceId((long) 1);
		reservation.setStartDate(LocalDate.now());
		reservation.setEndDate(LocalDate.now());
		
		return reservation;
	}
}
