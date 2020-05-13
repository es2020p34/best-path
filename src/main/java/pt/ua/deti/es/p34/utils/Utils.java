package pt.ua.deti.es.p34.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
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
}