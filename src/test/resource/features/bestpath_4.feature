Feature: Feedback
  Is required feedback at the end of use.

  Scenario: I report the fails on road congestion predicts
    Given I detect a fail on system or wrong road congestion predict
    When I reach the destination
    Then I report that on feedback that is required