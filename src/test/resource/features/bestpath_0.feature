Feature: Find the best path to your destination
    Everybody wants to reach to the destination quickly

    Scenario: Find the fastest path to Mercado do Bolhão
        Given I am at Mercado do Bolhão
        When I search for the fastest path to Foz do Douro
        Then I find the road with less congestion

