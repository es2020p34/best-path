// Global Variables
var loc0 = [-8.6296, 41.1557];
var i = 0;
var avg_S = 0
var dist = 0;
var transit = 0;

var dts = document.getElementById('dts');
var dur = document.getElementById('dur');
var dis = document.getElementById('dis');
var tra = document.getElementById('tra');

// Create the map
mapboxgl.accessToken = 'pk.eyJ1IjoiZGlhc2R1YXJ0ZSIsImEiOiJjazZ2YjZmaTYwMDJjM3JzNHZvajJhdTlyIn0.CQS2LCyFIVKZqDuzW_qQmA';
var map = new mapboxgl.Map({
    container: 'map',
    style: 'mapbox://styles/mapbox/streets-v11',
    center: [-8.629002, 41.157903],
    zoom: 13
});
// Add zoom and rotation controls to the map.
map.addControl(new mapboxgl.NavigationControl(), 'bottom-right');

// Add static location marker
let newLatLng = new mapboxgl.LngLat(loc0[0], loc0[1]);
let oneMarker = new mapboxgl.Marker();
oneMarker.setLngLat(newLatLng);
oneMarker.addTo(map);


// Get user location
if (navigator.geolocation) {
    navigator.geolocation.getCurrentPosition(getPosition);
}
function getPosition(position) {
    loc0 = [position.coords.longitude, position.coords.latitude];
    // Replace static marker with current location marker
    oneMarker.remove();
    newLatLng = new mapboxgl.LngLat(loc0[0], loc0[1]);
    oneMarker = new mapboxgl.Marker();
    oneMarker.setLngLat(newLatLng);
    oneMarker.addTo(map);
}

// Create Geocoder box
var geocoder = new MapboxGeocoder({
    accessToken: mapboxgl.accessToken,
    mapboxgl: mapboxgl,
    placeholder: 'Where to?'
});
map.addControl(geocoder);

// Create a popup, but don't add it to the map yet.
var popup = new mapboxgl.Popup({
    closeButton: false
});

// Popup message
map.on('mousemove', function() {
    for (var y=0; y<i; y++) {
        map.on('mousemove', y.toString(), function (e) {
            // Change the cursor style as a UI indicator.
            map.getCanvas().style.cursor = 'pointer';
            // Populate the popup and set its coordinates based on the feature.
            var feature = e.features[0];
            popup
                .setLngLat(feature.geometry.coordinates[0])
                .setText(
                    feature.properties.avg + ' km/h'
                )
                .addTo(map);
        });
    }
});

map.on('mousemove', function() {
    for (var y=0; y<i; y++) {
        map.on('mouseleave', y.toString(), function () {
            map.getCanvas().style.cursor = '';
            popup.remove();
        });
    }
});

// Create shortest path to destination
geocoder.on('result', function(result) {
    dts.style.display = "block";
    var lat1 = result.result.center[1];
    var lon1 = result.result.center[0];
    var url = 'api/directions?lat0='+loc0[1]+'&lon0='+loc0[0]+'&lat1='+lat1+'&lon1='+lon1;
    fetch(url).then(res => res.json()).then((out) => {
        // Set values
        dur.innerText = "";
        dis.innerText = "";
        tra.innerText = "";
        // Calculate time
        //var date0 = new Date(0);
        //date0.setSeconds(out.features[0].properties['summary'].duration);
        //console.log(date0.toISOString().substr(11, 8));
        // Calculate Distance
        var distance = out.features[0].properties['summary'].distance;
        distance = parseFloat(distance)/1000;
        dist = distance;
        dis.innerHTML = distance.toFixed(1) + " km"
        // Add Route to map
        var coords = out.features[0].geometry;
        addRoute(coords, "path", "dest");

        // Ajust View
        var coordinates = coords.coordinates;
        var bounds = coordinates.reduce(function(bounds, coord) {
            return bounds.extend(coord);
        }, new mapboxgl.LngLatBounds(coordinates[0], coordinates[0]));
        map.fitBounds(bounds, { padding: 50 });

        addTraffic(coords.coordinates);

    }).catch(err => { throw err });
})

// Add Traffic to path
function addTraffic(coords) {
    var final = [];
    var tmpList = [];
    var t = 0;
    while (coords.length != 0) {
        if (t != 10) {
            tmpList.push(coords.pop());
            t = t+1;
        } else {
            tmpList.push(coords[coords.length - 1]);
            final.push(tmpList);
            tmpList = [];
            t=0;
        }
    }
    final.push(tmpList)
    i = final.length;

    for (let y=0; y<final.length; y++){
        var avg = 0;
        var lmt = 50;
        var url = 'api/avgspeed?lat='+final[y][0][1]+'&lon='+final[y][0][0];
        fetch(url).then(res => res.json()).then((out) => {
            avg  = out["avg_speed"];
            avg_S = avg_S + avg;
            var url2 = 'api/speedlimit?lat='+final[y][0][1]+'&lon='+final[y][0][0];
            fetch(url2).then(res => res.json()).then((out2) => {
                lmt  = out2["speed_limit"];
            }).catch();

            if (avg <= (1/4)*lmt) {
                type = "high";
                transit = transit + 2;
            } else if (avg <= (1/2)*lmt) {
                type = "moderate";
                transit = transit + 1;
            } else {
                type = "low";
            }

            var coordsFin = JSON.parse('{"coordinates":'+JSON.stringify(final[y])+',"type":"LineString"}');

            addRoute(coordsFin, type, y.toString(), avg);

            if(y==(final.length-1)) {
                var date = new Date(0);
                date.setSeconds((dist/(avg_S/i))*60*60);
                dur.innerText = date.toISOString().substr(11, 8);
                if (transit/i < 2/3) {
                    tra.innerText = "Low";
                } else if (transit/i < 4/3) {
                    tra.innerText = "Medium";
                } else {
                    tra.innerText = "High";
                }
            }
        }).catch(err => { throw err });
    }
}

// Remove path
geocoder.on('clear', function(e) {
    clean("dest");
    for (let o=0; o<i; o++) {
        clean(o.toString());
    }
    i = 0;
    avg_S = 0;
    dist = 0;
    transit = 0;
    dts.style.display = "none";
});

// Draw the Map Matching route as a new layer on the map
function addRoute(coords, type, name, avg) {
    switch(type) {
        case 'low':
            colour = "#03AA46";
            break;
        case 'moderate':
            colour = "#FFA500";
            break;
        case 'high':
            colour = "#FF0000";
            break;
        case 'path':
            colour = "#808080";
            break;
        default:
            colour = "#03AA46";
    }

    map.addLayer({
        "id": String(name),
        "type": "line",
        "source": {
            "type": "geojson",
            "data": {
                "type": "Feature",
                "properties": { "avg": avg },
                "geometry": coords
            }
        },
        "layout": {
            "line-join": "round",
            "line-cap": "round"
        },
        "paint": {
            "line-color": colour,
            "line-width": 6,
            "line-opacity": 0.8
        }
    });
}

// Clean layer on the map
function clean(id) {
    if(typeof map.getLayer(id) !== 'undefined') {
        map.removeLayer(String(id));
        map.removeSource(String(id));
    }
}