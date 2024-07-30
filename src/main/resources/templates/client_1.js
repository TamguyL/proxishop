//Slider
const slide = ["image1.jpg", "image2.jpg", "image3.jpg",];
let numero = 0;
function changement(sens) {
numero = numero + sens;
if (numero < 0)
    numero = slide.length - 1;
if (numero > slide.length - 1)
    numero = 0;
document.querySelector("#slide").src = slide[numero];
}
setInterval("changement(1)", 3000);

//Map
document.addEventListener("DOMContentLoaded", function() {
    var map = L.map('map').setView([48.41374, -4.50125], 13);
    L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
      attribution: '&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a>'
    }).addTo(map);
    var geocoder = L.Control.Geocoder.nominatim();
    var marker;
  function addMarkerToAddress(address) {
      geocoder.geocode(address, function(results) {
          if (results.length > 0) {
              var latlng = results[0].center;
          L.marker(latlng).addTo(map)
              .bindPopup('Voici mon commerce au : ' + address)
              .openPopup();
          map.setView(latlng, 13);
      } else {
      alert('Adresse non trouvée.');
      }
      });
  }
  addMarkerToAddress('cité pablo picasso');
    var searchControl = L.Control.geocoder({
      position: 'topright',
      placeholder: 'Rechercher une adresse...',
      defaultMarkGeocode: false
    }).on('markgeocode', function(e) {
      var bbox = e.geocode.bbox;
      var poly = L.polygon([
        bbox.getSouthEast(),
        bbox.getNorthEast(),
        bbox.getNorthWest(),
        bbox.getSouthWest()
      ]).addTo(map);
      map.fitBounds(poly.getBounds());
      if (marker) {
        map.removeLayer(marker);
      }
      marker = L.marker(e.geocode.center).addTo(map);
    }).addTo(map);
  });