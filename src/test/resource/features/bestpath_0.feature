Feature: Find the best path to your destination
    Everybody wants to reach to the destination quickly

    Scenario: Find the fastest path to Mercado do Bolhão
        Given I am at Mercado do Bolhão
        When I search for the fastest path to Foz do Douro
        Then I find the road with less congestion

    Scenario: Simple and intuitive Visualization
        Given I need instant information about road congestion
        When I look to the Porto map
        Then I regard the principal information to avoid some roads
