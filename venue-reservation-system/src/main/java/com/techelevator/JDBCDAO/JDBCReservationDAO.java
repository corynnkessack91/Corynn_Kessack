package com.techelevator.JDBCDAO;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

public class JDBCReservationDAO implements ReservationDAO {

	private JdbcTemplate jdbcTemplate;

	public JDBCReservationDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);

	}
	
	@Override
	public Reservation makeReservation(Reservation reservation) {
		String insertSql = "INSERT INTO reservation (id, space_id, name, start_date, end_date) VALUES (DEFAULT, ?, ?, ?, ?) RETURNING id";
		SqlRowSet rows = jdbcTemplate.queryForRowSet(insertSql, reservation.getSpaceId(), reservation.getStartDate(), reservation.getEndDate());
		rows.next();
		long reservation_id = rows.getLong("id");
		
		reservation.setReservationId(reservation_id);
		return reservation;
	}
	
	
	private Space mapRowToSpace(SqlRowSet row) {
		Space spaces = new Space();
		spaces.setId(row.getLong("space_id"));
		spaces.setMax_occupancy(row.getInt("max_occupancy"));
		spaces.setIs_accessible(row.getBoolean("is_accessible"));
		spaces.setDaily_rate(row.getBigDecimal("daily_rate"));
		spaces.setOpen_to(row.getInt("open_to"));
		spaces.setOpen_from(row.getInt("open_from"));
		spaces.setName(row.getString("name"));
		return spaces;
	}
}