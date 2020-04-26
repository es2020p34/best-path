Feature: Find the best path to your destination
    Everybody wants to reach to the destination quickly

    Scenario: Find the fastest path to the Ribeira
        Given I am at Estação de São Bento
        When I search for the fastest path to Ribeira
        Then I find the road with less congestion

    Scenario: Simple and intuitive Visualization
        Given I need instant information about road congestion
        When I look to the Porto map
        Then I regard the principal information to avoid some roads
