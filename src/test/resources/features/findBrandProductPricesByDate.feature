Feature: Fetching the price of a product of a brand for a given date
  Scenario Outline: Fetching the price of a product of a brand for a given date
    When The client asks for the price of a product <productId> of a brand <brandId> for a <implementationDate>
    Then the returned price contains the same brandId, productId, the date is in the correct window of time
    Then the expectedPriceList is <expectedPriceList>
    Then the expectedPrice is <expectedPrice>

    Examples:
    | brandId | productId | implementationDate | expectedPriceList | expectedPrice |
    | 1  | 35455 | 2020-06-14-10.00.00 | 1 | 35.50 |
    | 1  | 35455 | 2020-06-14-16.00.00 | 2 | 25.45 |
    | 1  | 35455 | 2020-06-14-21.00.00 | 1 | 35.50 |
    | 1  | 35455 | 2020-06-15-10.00.00 | 3 | 30.50 |
    | 1  | 35455 | 2020-06-16-21.00.00 | 4 | 38.95 |

  Scenario: Fetching the price for a non existing matching date
    When The client asks for the price of a non existing matching date
    Then the response contains a not found price message

  Scenario: Fetching the price for a non existing brand
    When The client asks for the price of a non existing brand
    Then the response contains an error message to inform about it

  Scenario: Fetching the price for a non existing product
    When The client asks for the price of a non existing product
    Then the response contains an error message to inform about it