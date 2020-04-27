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
var traffic = 'low';
var coords = [[-8.6489,41.1612],[-8.6336,41.1586],[-8.6272,41.1553],[-8.6236,41.1480],[-8.6109,41.1476]];
map.on('load', function(){
    updateRoute(coords, traffic, 0);
    traffic = 'high';
    coords = [[-8.6076,41.1426],[-8.6122,41.1442],[-8.6145,41.1412],[-8.6346,41.1478],[-8.6701,41.1486],[-8.6893,41.1675]];
    updateRoute(coords, traffic, 1);
});

// Use the coordinates to make the Map Matching API request
function updateRoute(coords, traffic, i) {
    // Set the profile
    var profile = "driving";
    var newCoords = coords.join(';')
    // Set the radius for each coordinate pair to 25 meters
    var radius = [];
    coords.forEach(element => {
        radius.push(25);
    });
    getMatch(newCoords, radius, profile, traffic, i);
}

// Make a Map Matching request
function getMatch(coordinates, radius, profile, traffic, i) {
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
        addRoute(coords, traffic, i);
    });
}

// Draw the Map Matching route as a new layer on the map
function addRoute(coords, traffic, i) {
    var colour = "#03AA46";
    if(traffic == 'low') {
        colour = "#03AA46"
    }else if(traffic == 'moderate') {
        colour = "#FFA500";
    }else if(traffic == 'high') {
        colour = "#FF0000";
    }
    map.addLayer({
        "id": "r"+i,
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