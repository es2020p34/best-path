Feature: Statistics page
  A statistics page is available for everyone

  Scenario: Regard more information about general road congestion
    Given I want to know more information about road congestion
    When I see the statistics page on BestPath system
    Then I obtain detailed information about general road congestion

  Scenario: Make studies about road traffic at Porto
    Given a person with interesting to study the road congestion
    When using the information available on the system
    Then the person can to make a dataset.

  Scenario: city official people make management based on existing data
    Given data information about all the roads congestion
    When official people want to improve the quality of traffic
    Then they use the system to visualize the behaviour on last times.