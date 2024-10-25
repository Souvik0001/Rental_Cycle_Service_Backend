INSERT INTO cycle (rating, available, cycle_id, current_location)
VALUES
    (4.5, true, 1001, ST_GeomFromText('POINT(40.7128 -74.0060)', 4326)),
    (3.8, false, 1002, ST_GeomFromText('POINT(34.0522 -118.2437)', 4326)),
    (4.2, true, 1003, ST_GeomFromText('POINT(51.5074 -0.1278)', 4326)),
    (2.9, true, 1004, ST_GeomFromText('POINT(48.8566 2.3522)', 4326)),
    (4.7, true, 1005, ST_GeomFromText('POINT(35.6895 139.6917)', 4326)),
    (3.5, false, 1006, ST_GeomFromText('POINT(55.7558 37.6173)', 4326)),
    (4.0, true, 1007, ST_GeomFromText('POINT(37.7749 -122.4194)', 4326)),
    (3.9, false, 1008, ST_GeomFromText('POINT(52.5200 13.4050)', 4326)),
    (4.6, true, 1009, ST_GeomFromText('POINT(40.4168 -3.7038)', 4326)),
    (4.3, true, 1010, ST_GeomFromText('POINT(41.9028 12.4964)', 4326));
