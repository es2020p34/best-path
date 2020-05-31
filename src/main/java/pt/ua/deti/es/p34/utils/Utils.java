package pt.ua.deti.es.p34.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.util.Properties;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Generic Utilities class.
 *
 * @author Catarina Silva
 * @version 1.1
 */
public class Utils {
  /**
   * Utility class, lets make the constructor private.
   */
  private Utils() {
  }

  @SuppressWarnings("unchecked")
  public static <T> T cast(final Object o) {
    return (T) o;
  }

  public static String stream2string(final InputStream is) {
    return new BufferedReader(new InputStreamReader(is)).lines().collect(Collectors.joining("\n"));
  }

  public static UUID asUuid(byte[] bytes) {
    ByteBuffer bb = ByteBuffer.wrap(bytes);
    long firstLong = bb.getLong();
    long secondLong = bb.getLong();
    return new UUID(firstLong, secondLong);
  }

  public static byte[] asBytes(UUID uuid) {
    ByteBuffer bb = ByteBuffer.wrap(new byte[16]);
    bb.putLong(uuid.getMostSignificantBits());
    bb.putLong(uuid.getLeastSignificantBits());
    return bb.array();
  }

  /**
     * Load the properties file with the kafka configuration
     * @param file the path to the configuration
     * @return {@link Properties} class
     */
    public static Properties loadProperties(final String file) {
      try {
          final Properties properties = new Properties();
          ClassLoader loader = Thread.currentThread().getContextClassLoader();
          final InputStream inputStream = loader.getResourceAsStream(file);
          properties.load(inputStream);
          return properties;
      } catch (final IOException e) {
          e.printStackTrace();
      }
      return null;
  }
}