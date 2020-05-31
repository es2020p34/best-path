// Global Variables
var loc0 = [-8.6296, 41.1557];
var i = 0;

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

// Create shortest path to destination
geocoder.on('result', function(result) {
    var lat1 = result.result.center[1];
    var lon1 = result.result.center[0];
    var url = 'api/directions?lat0='+loc0[1]+'&lon0='+loc0[0]+'&lat1='+lat1+'&lon1='+lon1;
    fetch(url).then(res => res.json()).then((out) => {
        var coords = out.features[0].geometry;
        addRoute(coords, "path", "dest");

        //
        var coordinates = coords.coordinates;
        var bounds = coordinates.reduce(function(bounds, coord) {
            return bounds.extend(coord);
        }, new mapboxgl.LngLatBounds(coordinates[0], coordinates[0]));
        map.fitBounds(bounds, { padding: 50 });

    }).catch(err => { throw err });
})

// Remove path
geocoder.on('clear', function(e) {
    clean("dest");
});

// Add traffic layer to the map
var traffic = 'low';
var coords = [[-8.6489,41.1612],[-8.6336,41.1586],[-8.6272,41.1553],[-8.6236,41.1480],[-8.6109,41.1476]];
map.on('load', function(){
    i = i +1;
    updateRoute(coords, traffic, i);
    traffic = 'high';
    coords = [[-8.6076,41.1426],[-8.6122,41.1442],[-8.6145,41.1412],[-8.6346,41.1478],[-8.6701,41.1486],[-8.6893,41.1675]];
    i = i + 1;
    updateRoute(coords, traffic, i);
});

// Use the coordinates to make the Map Matching API request
function updateRoute(coords, type, i) {
    var coordinates = coords.join(';')
    var radius = [];
    coords.forEach(element => { radius.push(25); });
    var radiuses = radius.join(';')

    var url = 'api/pathdesign?coords='+coordinates+'&radius='+radiuses;
    fetch(url).then(res => res.json()).then((out) => {
        var coords = out.matchings[0].geometry;
        addRoute(coords, type, i);
    }).catch(err => { throw err });
}

// Draw the Map Matching route as a new layer on the map
function addRoute(coords, type, name) {
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
            colour = "#0000FF";
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
                "properties": {},
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