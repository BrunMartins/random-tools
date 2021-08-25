/**
 * Get Distance between two coodrinate sets in meters
 *
 * @param {Float} lat1 Latitude of the first set
 * @param {Float} lon1 Longitude of the first set
 * @param {Float} lat2 Latitude of the second set
 * @param {Float} lon2 Longitude of the second set
 *
 * @link http://en.wikipedia.org/wiki/Haversine_formula
 *
 * @returns {Integer} Distance between the two coordinate sets in meters
 */
function getDistanceFromLatLonInM(lat1, lon1, lat2, lon2) {
    const earthRadius = 6371; // Radius of the earth in km
    const degLat = deg2rad(lat2-lat1);
    const degLon = deg2rad(lon2-lon1);

    let distance =
        Math.sin(degLat/2) * Math.sin(degLat/2) +
        Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) *
        Math.sin(degLon/2) * Math.sin(degLon/2);

    distance = 2 * Math.atan2(Math.sqrt(distance), Math.sqrt(1-distance));

    distance = (earthRadius * distance) * 1000; // Distance in meters

    return Math.floor(distance) + "m";
}

/**
 * Convert degrees to radians
 *
 * @param {Float} deg - Value to convert to radians. Represented in degrees
 *
 * @returns {Float} Value in radians
 */
function deg2rad(deg) {
    return deg * (Math.PI/180)
}

console.log(getDistanceFromLatLonInM(38.719799034265954, -9.17914790724783, 38.71951003445213, -9.180880364919282));