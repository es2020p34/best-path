Feature: All of the system need a data source
  To provide the information is necessary a data source

  Scenario: Consuming data from buses velocities information
    Given several buses on Porto
    When is necessary the information about traffic to process the data
    Then use the velocity of these buses to know if the traffic is slow, fast or normal.

  Scenario: Consuming data about maximum velocity to a given road
    Given a road
    When we know if that road is congestion or not
    Then we compare the values of limits and the median velocity