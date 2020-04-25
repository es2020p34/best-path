mapboxgl.accessToken = 'pk.eyJ1IjoiZGlhc2R1YXJ0ZSIsImEiOiJjazZ2YjZmaTYwMDJjM3JzNHZvajJhdTlyIn0.CQS2LCyFIVKZqDuzW_qQmA';
var map = new mapboxgl.Map({
    container: 'map',
    style: 'mapbox://styles/mapbox/streets-v11',
    center: [-8.629002, 41.157903],
    zoom: 13
});
// Add zoom and rotation controls to the map.
map.addControl(new mapboxgl.NavigationControl(), 'bottom-right');
// Add traffic layer to the map
map.on('load', updateRoute);

// Use the coordinates to make the Map Matching API request
function updateRoute() {
    // Set the profile
    var profile = "driving";
    // Get the coordinates
    var coords = [[-8.6489,41.1612],[-8.6336,41.1586],[-8.6272,41.1553],[-8.6236,41.1480],[-8.6109,41.1476]];
    var newCoords = coords.join(';')
    // Set the radius for each coordinate pair to 25 meters
    var radius = [];
    coords.forEach(element => {
        radius.push(25);
    });
    getMatch(newCoords, radius, profile);
}

// Make a Map Matching request
function getMatch(coordinates, radius, profile) {
    // Separate the radiuses with semicolons
    var radiuses = radius.join(';')
    // Create the query
    var query = 'https://api.mapbox.com/matching/v5/mapbox/' + profile + '/' + coordinates + '?geometries=geojson&radiuses=' + radiuses + '&steps=true&access_token=' + mapboxgl.accessToken;
    console.log(query)
    $.ajax({
        method: 'GET',
        url: query
    }).done(function(data) {
        // Get the coordinates from the response
        var coords = data.matchings[0].geometry;
        // Draw the route on the map
        addRoute(coords);
    });
}

// Draw the Map Matching route as a new layer on the map
function addRoute(coords) {
    map.addLayer({
        "id": "route",
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
            "line-color": "#03AA46",
            "line-width": 8,
            "line-opacity": 0.8
        }
    });
}