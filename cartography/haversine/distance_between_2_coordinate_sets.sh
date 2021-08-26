#!/bin/bash

deg2rad () {
    # echo $1
    pi=`bc -l <<< "scale=1001; 4*a(1)"`
    bc -l <<< "$1 * ($pi/180)"
}

earthRadius=6371
lat_1="$1"
lon_1="$2"
lat_2="$3"
lon_2="$4"
lat_1_rad="`deg2rad $lat_1`"
lat_2_rad="`deg2rad $lat_2`"
delta_lat=`bc <<<"$lat_2 - $lat_1"`
delta_lon=`bc <<<"$lon_2 - $lon_1"`
delta_lat="`deg2rad $delta_lat`"
delta_lon="`deg2rad $delta_lon`"

distance=`bc -l <<< "s($delta_lat/2) * s($delta_lat/2) + c($lat_1_rad) * c($lat_2_rad) * s($delta_lon/2) * s($delta_lon/2)"`
distance=`bc -l <<< "2 * a(sqrt($distance) / sqrt(1-$distance))"`
distance=`bc -l <<< "($earthRadius * $distance) * 1000"`
distance=`bc <<<"scale=0; $distance / 1"`
echo $distance"m"
