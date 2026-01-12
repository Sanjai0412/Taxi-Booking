<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
    <body>
        <h2>Driver interface</h2>
        <br>
        <p>Welcome, Driver ID: ${userId}</p>
        <br>
        <h2>Available Ride Requests</h2>
        <ul style="list-style: none;">
            <c:if test="${not empty nearbyRequests}">
                <c:forEach var="req" items="${nearbyRequests}">
                    <li>
                       <form method="post" action="/api/driver/ride-requests/accept">
                            
                            <p>Pickup Location: ${req.pickupLocationName}</p>
                            <p>Drop Location: ${req.dropLocationName}</p>
                            <p>Estimated Distance: ${req.estimatedDistance}</p>
                            <p>Estimated Fare: ${req.estimatedFare}</p>
                            <input type="hidden" name="rideRequestId" value="${req.rideRequestId}">
                            <button type="submit" name="acceptRide">Accept Ride</button>
                          </form>
                    </li>
                </c:forEach>
            </c:if>
            <c:if test="${empty nearbyRequests}">
                <li>No nearby ride requests.</li>
            </c:if>
        </ul>
    </body>
</html>