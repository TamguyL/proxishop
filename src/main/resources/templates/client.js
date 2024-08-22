//Slider
const slide = ["img/image1.jpg", "img/image2.jpg", "img/image3.jpg",];
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
    let map = L.map('map').setView([48.41374, -4.50125], 13);
    L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
      attribution: '&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a>'
    }).addTo(map);
    let geocoder = L.Control.Geocoder.nominatim();
    let marker;
  function addMarkerToAddress(address) {
      geocoder.geocode(address, function(results) {
          if (results.length > 0) {
              let latlng = results[0].center;
          L.marker(latlng).addTo(map)
              .bindPopup('Voici mon commerce au : ' + address)
              .openPopup();
          map.setView(latlng, 13);
      } else {
      alert('Adresse non trouvée.');
      }
      });
  }
  addMarkerToAddress('4 rue Duliscouët');
({
      position: 'topright',
      placeholder: 'Rechercher une adresse...',
      defaultMarkGeocode: false
    }).on('markgeocode', function(e) {
      let bbox = e.geocode.bbox;
      let poly = L.polygon([
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