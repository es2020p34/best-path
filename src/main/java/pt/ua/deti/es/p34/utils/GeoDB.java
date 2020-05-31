package pt.ua.deti.es.p34.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GeoDB {

	private static final Logger LOG = LoggerFactory.getLogger(GeoDB.class);

	private static final String SQL_INSERT = "INSERT INTO events(id, time, point, speed)"
			+ "VALUES(?,?, ST_SetSRID(ST_PointFromText(?),4326),?)";
	private static final String SQL_SELECT = "SELECT speed FROM events "
			+ "WHERE ST_DWithin(point, ST_MakePoint(?,?)::geography, ?) ORDER BY time DESC LIMIT ?";
	private static final String SQL_SELECT_LAST = "SELECT id, time, speed, "
			+ "ST_X(point::geometry) as longitude, ST_Y(point::geometry) as latitude "
			+ "FROM events ORDER BY time DESC LIMIT 1";

	private final String url;

	@Autowired
	public GeoDB(@Value("${geodb.addr}") String geoAddr, @Value("${geodb.port}") int geoPort,
			@Value("${geodb.name}") String geoName, @Value("${geodb.user}") String geoUser,
			@Value("${geodb.pass}") String geoPass) {
		url = "jdbc:postgresql://" + geoAddr + ":" + geoPort + "/" + geoName + "?user=" + geoUser + "&password="
				+ geoPass;
	}

	private Connection getConn() throws Exception {
		Connection conn = null;
		try {
			DriverManager.registerDriver(new org.postgresql.Driver());
			conn = DriverManager.getConnection(url);
			conn.setAutoCommit(true);
		} catch (Exception e) {
			LOG.error("getConn", e);
			throw (e);
		}
		return conn;
	}

	public void insert_event(Event event) {
		insert_row(event.id, event.timestamp, event.lat, event.lon, event.speed);
	}

	public void insert_row(final UUID id, final Timestamp timestamp, final double lat, final double lon,
			final float speed) {

		try (Connection conn = getConn(); PreparedStatement statement = conn.prepareStatement(SQL_INSERT);) {
			statement.setObject(1, id);
			statement.setTimestamp(2, timestamp);
			statement.setString(3, "POINT(" + lon + " " + lat + ")");
			statement.setFloat(4, speed);
			statement.executeUpdate();
		} catch (Exception e) {
			LOG.error("insert_row", e);
		}
	}

	public void insert_rows(final List<Event> events) {
		try (Connection conn = getConn(); PreparedStatement statement = conn.prepareStatement(SQL_INSERT);) {

			int count = 0;

			for (Event e : events) {
				statement.setObject(1, e.id);
				statement.setTimestamp(2, e.timestamp);
				statement.setString(3, "POINT(" + e.lon + " " + e.lat + ")");
				statement.setFloat(4, e.speed);

				statement.addBatch();
				count++;
				// execute every 100 rows or less
				if (count % 100 == 0 || count == events.size()) {
					statement.executeBatch();
				}
			}
		} catch (Exception e) {
			LOG.error("insert_rows", e);
		}
	}

	public double getAvgSpeed(final double lat, final double lon, final int radius, final int limit) {
		double speed = -1.0;

		try (Connection conn = getConn(); PreparedStatement statement = conn.prepareStatement(SQL_SELECT);) {
			statement.setDouble(1, lon);
			statement.setDouble(2, lat);
			statement.setInt(3, radius);
			statement.setInt(4, limit);

			ResultSet rs = statement.executeQuery();

			double tmp = 0.0, count = 0.0;

			while (rs.next()) {
				tmp += rs.getDouble(1);
				count++;
			}

			if (tmp > 0) {
				speed = tmp / count;
			}
		} catch (Exception e) {
			LOG.error("getAvgSpeed", e);
		}

		return speed;
	}

	public double getAvgSpeed(final double lat, final double lon) {
		return getAvgSpeed(lat, lon, 100, 10);
	}

	public Event getLastEvent() {
		Event rv = null;
		try (Connection conn = getConn(); PreparedStatement statement = conn.prepareStatement(SQL_SELECT_LAST);) {
			ResultSet rs = statement.executeQuery();
			if (rs.next()) {
				UUID id = Utils.cast(rs.getObject(1));
				Timestamp timestamp = rs.getTimestamp(2);
				float speed = rs.getFloat(3);
				double lon = rs.getDouble(4);
				double lat = rs.getDouble(5);

				rv = new Event(id, timestamp, lat, lon, speed);
			}
		} catch (Exception e) {
			LOG.error("getLastEvent", e);
		}
		return rv;
	}

	public static class Event {
		protected UUID id;
		protected Timestamp timestamp;
		protected double lat, lon;
		protected float speed;

		public Event() {
			id = null;
			timestamp = null;
			speed = 0;
			lat = 0.0;
			lon = 0.0;
		}

		public Event(final String id, final String timestamp, final double lat, final double lon, final float speed) {
			this.id = UUID.fromString(id);
			this.timestamp = Timestamp.valueOf(timestamp);
			this.lat = lat;
			this.lon = lon;
			this.speed = speed;
		}

		public Event(final UUID id, final Timestamp timestamp, final double lat, final double lon, final float speed) {
			this.id = id;
			this.timestamp = timestamp;
			this.lat = lat;
			this.lon = lon;
			this.speed = speed;
		}

		public void setTimestamp(String timestamp) {
			this.timestamp = Timestamp.valueOf(timestamp);
		}

		public void setTimestamp(Timestamp timestamp) {
			this.timestamp = timestamp;
		}

		public String getTimestamp() {
			return this.timestamp.toString();
		}

		public String getUUID() {
			return this.id.toString();
		}

		public void setUUID(UUID uuid) {
			this.id = uuid;
		}

		public void setUUID(String uuid) {
			this.id = UUID.fromString(uuid);
		}

		public double getLongitude() {
			return this.lon;
		}

		public void setLongitude(double lon) {
			this.lon = lon;
		}

		public double getLatitude() {
			return this.lat;
		}

		public void setLatitude(double lat) {
			this.lat = lat;
		}

		public double getSpeed() {
			return this.speed;
		}

		public void setSpeed(float speed) {
			this.speed = speed;
		}
	}
}