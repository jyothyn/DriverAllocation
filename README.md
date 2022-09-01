# DriverAllocation
This takes a JSON string containing list of drivers and a list of shipment addresses. <br>
In viewmodel, a suitability score (ss) is calculated for each driver + shipment <br>
Then a driver is assigned a single shipment that has the highest ss score. <br>
The list of drivers is displayed in the view. <br>
Tapping on driver name, displays the shipment address assigned. <br>

Assumptions: The size of list of drivers = size of list of shipments <br>

Uses Hilt for Dependency Injection <br>
Uses viewmodel for including all the business logic <br>
Uses roomDB for creating records of driver-shipment, and for sorting to get the highest ss. <br>

To run the app: <br>
Download app, build the app, and run it on any Android device SDK version >=29 <br>
