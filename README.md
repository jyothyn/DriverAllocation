# DriverAllocation
This takes a JSON string containing list of drivers and a list of shipment addresses. 
In viewmodel, a suitability score (ss) is calculated for each driver + shipment
Then a driver is assigned a single shipment that has the highest ss score.
The list of drivers is displayed in the view.
Tapping on driver name, displays the shipment address assigned.

Assumptions: The size of list of drivers = size of list of shipments

Uses Hilt for Dependency Injection
Uses viewmodel for including all the business logic
Uses roomDB for creating records of driver-shipment, and for sorting to get the highest ss.

To run the app:
Download app, build the app, and run it on any Android device SDK version >=29
