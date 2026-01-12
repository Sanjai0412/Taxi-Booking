<html>
    <body>
    <h2>Ongoing Ride</h2>

        <p>Pickup: ${ongoingRide.pickupLocationName}</p>
        <p>Drop: ${ongoingRide.dropLocationName}</p>
        <p>Distance: ${ongoingRide.estimatedDistance}</p>
        <p>Fare: ${ongoingRide.estimatedFare}</p>

        <form method="post" action="/api/driver/ride/complete">
            <input type="hidden" name="rideId" value="${ongoingRide.rideRequestId}">
            <button type="submit">Complete Ride</button>
        </form>

    </body>
</html>