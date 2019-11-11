package com.techelevator.JDBCDAO;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

public class JDBCSpaceDAO implements SpaceDAO {

	private static final String SELECT_SPACE_SQL = "Select name, open_from, open_to, CAST(daily_rate AS decimal), max_occupancy, venue_id From space";

	private JdbcTemplate jdbcTemplate;
	private Venue venue;
	private Space space;
	private Reservation reservation;

	public JDBCSpaceDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);

	}

	@Override
	public List<Space> getAllSpaces(Long id) {
		List<Space> spaces = new ArrayList<Space>();

		String allSpaces = SELECT_SPACE_SQL + " WHERE venue_id = ? ";

		SqlRowSet rows = jdbcTemplate.queryForRowSet(allSpaces, id);

		while (rows.next()) {
			Space space = testToSpace(rows);
			spaces.add(space);
		}

		return spaces;
	}

	@Override
	public List<Space> getOpenSpaces(Long id, LocalDate startDate, LocalDate endDate, int maxOccup) {
		List<Space> spaces = new ArrayList<Space>();

		String sqlClose = "SELECT space_id, max_occupancy, is_accessible, open_from, open_to, daily_rate, name FROM space JOIN venue ON space.venue_id = venue.id WHERE venue_id NOT IN (SELECT space_id FROM reservation WHERE (?, ?) overlaps (start_date, end_date) AND max_occupancy = ? GROUP BY space_id) LIMIT 5";

		if (inSeason(id, startDate) == true) {
			if (space.getMax_occupancy() >= maxOccup) {

				SqlRowSet rows = jdbcTemplate.queryForRowSet(sqlClose, startDate, endDate, maxOccup);
				while (rows.next()) {
					Space space = testToSpace(rows);
					spaces.add(space);
				}

			}

		}

		return spaces;
	}

	@Override
	public Boolean inSeason(Long id, LocalDate startDate) {
		List<Space> spaces = new ArrayList<Space>();

		String sqlSeason = "SELECT space.id, space.max_occupancy, space.is_accessible, space.open_from, space.open_to, space.daily_rate, space.name FROM space JOIN venue ON space.venue_id = venue.id WHERE space.venue_id = ? AND ? BETWEEN open_from AND open_to";
		SqlRowSet rows = jdbcTemplate.queryForRowSet(sqlSeason, id, startDate);

		if (startDate.getMonthValue() >= space.getOpen_from() || space.getOpen_from() == 0) {
			if (startDate.getMonthValue() <= space.getOpen_to() || space.getOpen_to() == 0) {
				return true;
			}
		}
		return false;
	}

	private Space testToSpace(SqlRowSet row) {
		Space space = new Space();
		space.setId(row.getLong("venue_id"));
		space.setName(row.getString("name"));
		space.setOpen_from(row.getInt("open_from"));
		space.setOpen_to(row.getInt("open_to"));
		space.setDaily_rate(row.getBigDecimal("daily_rate"));
		space.setMax_occupancy(row.getInt("max_occupancy"));
		space.setVenue_id(row.getLong("venue_id"));
		return space;
	}

}