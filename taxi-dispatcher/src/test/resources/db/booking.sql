INSERT INTO bookings(id, status, start_latitude, start_longitude, destination_latitude, destination_longitude, taxi_id)
OVERRIDING SYSTEM VALUE
VALUES 
	(1, 2, 0.0, 0.0, 0.2, 0.1, 1),
	(2, 1, 0.3, 0.1, -0.1, 0.0, 2),
	(3, 0, 20.1, 20.0, 19.8, 22.0, null);