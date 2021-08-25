import math
import sys

def getDistanceFromLatLonInM(lat1, lon1, lat2, lon2):
    earthRadius = 6371
    degLat = deg2rad(lat2-lat1)
    degLon = deg2rad(lon2-lon1)

    distanceBetweenPoints = math.sin(degLat/2) * math.sin(degLat/2) + math.cos(deg2rad(lat1)) * math.cos(deg2rad(lat2)) * math.sin(degLon/2) * math.sin(degLon/2)

    angle = 2 * math.atan2(math.sqrt(distanceBetweenPoints), math.sqrt(1-distanceBetweenPoints))

    distanceBetweenPoints = (earthRadius * angle) * 1000

    print(str(math.floor(distanceBetweenPoints)) + "m")

def deg2rad(deg):
    return deg * (math.pi/108)

if __name__ == "__main__":
    a = float(sys.argv[1])
    b = float(sys.argv[2])
    c = float(sys.argv[3])
    d = float(sys.argv[4])
    getDistanceFromLatLonInM(a, b, c, d)
