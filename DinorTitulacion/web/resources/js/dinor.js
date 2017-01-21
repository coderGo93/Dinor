/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

//document.getElementById('mapa').style.position = "fixed";
//$("#mapa").css({position:'fixed'});

var map;
var latitud;
var longitud;
var markers = [];
var locations;
var latitudes = [];
var longitudes = [];
var infowindow;
var marker;
var activoMapaUpdate = 0;
$('.message a').click(function () {
    $('form').animate({height: "toggle", opacity: "toggle"}, "slow");
});
$('.bCreate').click(function () {
    $('.create-page').animate({height: "toggle", opacity: "toggle"}, "slow");
    $('.bCreate').hide();
    $('.bCreate_cancel').show();
    map = new google.maps.Map(document.getElementById('mapa'), {
        center: {lat: 20.693382, lng: -103.421126},
        zoom: 15
    });

    marker = new google.maps.Marker({
        map: map,
        draggable: true,
        animation: google.maps.Animation.DROP,
        position: {lat: 20.693051, lng: -103.418873}
    });
    var geocoder = new google.maps.Geocoder;
    google.maps.event.addListener(map, 'click', function (event) {
        marker.setMap(null)
        // alert(event.latLng.lat() + ", " + event.latLng.lng())
        marker = new google.maps.Marker({
            map: map,
            draggable: true,
            animation: google.maps.Animation.DROP,
            position: {lat: event.latLng.lat(), lng: event.latLng.lng()}
        });
        google.maps.event.addListener(marker, 'dragend', function (event) {
            document.getElementById("latitude").value = this.getPosition().lat();
            document.getElementById("longitude").value = this.getPosition().lng();
        });
        google.maps.event.addListener(marker, 'dragend', function (event) {
            geocodeLatLng(geocoder, map, this.getPosition().lat(), this.getPosition().lng());
        });
        google.maps.event.addListener(marker, 'click', function (event) {
            document.getElementById("latitude").value = event.latLng.lat();
            document.getElementById("longitude").value = event.latLng.lng();
        });
        google.maps.event.addListener(marker, 'click', function (event) {
            geocodeLatLng(geocoder, map, event.latLng.lat(), event.latLng.lng());
        });
    });

    google.maps.event.addListener(marker, 'dragend', function (event) {
        document.getElementById("latitude").value = this.getPosition().lat();
        document.getElementById("longitude").value = this.getPosition().lng();
    });
    google.maps.event.addListener(marker, 'dragend', function (event) {
        geocodeLatLng(geocoder, map, this.getPosition().lat(), this.getPosition().lng());
    });
    google.maps.event.addListener(map, 'click', function (event) {
        document.getElementById("latitude").value = event.latLng.lat();
        document.getElementById("longitude").value = event.latLng.lng();
    });
    google.maps.event.addListener(map, 'click', function (event) {
        geocodeLatLng(geocoder, map, event.latLng.lat(), event.latLng.lng());
    });

});
$('.bCreate_cancel').click(function () {
    document.getElementById("name").value = "";
    document.getElementById("zip").value = "";
    document.getElementById("website").value = "";
    document.getElementById("longitude").value = "";
    document.getElementById("latitude").value = "";
    document.getElementById("city").value = "";
    document.getElementById("state").value = "";
    document.getElementById("address").value = "";
    document.getElementById("phone").value = "";
    $('.create-page').animate({height: "toggle", opacity: "toggle"}, "slow");
    $('.bCreate').show();
    $('.bCreate_cancel').hide();
    initMap();
});
$('.bCreateEvent').click(function () {
    $('.create-page').animate({height: "toggle", opacity: "toggle"}, "slow");
    $('.bCreateEvent').hide();
    $('.bCreate_cancelEvent').show();
    map = new google.maps.Map(document.getElementById('mapa'), {
        center: {lat: 20.693382, lng: -103.421126},
        zoom: 15
    });

    marker = new google.maps.Marker({
        map: map,
        draggable: true,
        animation: google.maps.Animation.DROP,
        position: {lat: 20.693051, lng: -103.418873}
    });
    var geocoder = new google.maps.Geocoder;
    google.maps.event.addListener(map, 'click', function (event) {
        marker.setMap(null)
        // alert(event.latLng.lat() + ", " + event.latLng.lng())
        marker = new google.maps.Marker({
            map: map,
            draggable: true,
            animation: google.maps.Animation.DROP,
            position: {lat: event.latLng.lat(), lng: event.latLng.lng()}
        });
        google.maps.event.addListener(marker, 'dragend', function (event) {
            document.getElementById("latitude").value = this.getPosition().lat();
            document.getElementById("longitude").value = this.getPosition().lng();
        });
        google.maps.event.addListener(marker, 'dragend', function (event) {
            geocodeLatLng(geocoder, map, this.getPosition().lat(), this.getPosition().lng());
        });
        google.maps.event.addListener(marker, 'click', function (event) {
            document.getElementById("latitude").value = event.latLng.lat();
            document.getElementById("longitude").value = event.latLng.lng();
        });
        google.maps.event.addListener(marker, 'click', function (event) {
            geocodeLatLng(geocoder, map, event.latLng.lat(), event.latLng.lng());
        });
    });

    google.maps.event.addListener(marker, 'dragend', function (event) {
        document.getElementById("latitude").value = this.getPosition().lat();
        document.getElementById("longitude").value = this.getPosition().lng();
    });
    google.maps.event.addListener(marker, 'dragend', function (event) {
        geocodeLatLng(geocoder, map, this.getPosition().lat(), this.getPosition().lng());
    });
    google.maps.event.addListener(map, 'click', function (event) {
        document.getElementById("latitude").value = event.latLng.lat();
        document.getElementById("longitude").value = event.latLng.lng();
    });
    google.maps.event.addListener(map, 'click', function (event) {
        geocodeLatLng(geocoder, map, event.latLng.lat(), event.latLng.lng());
    });

});
$('.bCreate_cancelEvent').on("click",function () {
    document.getElementById("name").value = "";
    document.getElementById("description").value = "";
    document.getElementById("place").value = "";
    document.getElementById("longitude").value = "";
    document.getElementById("latitude").value = "";
    document.getElementById("fechaInicio").value = "";
    document.getElementById("fechaTermino").value = "";
    document.getElementById("horaInicio").value = "";
    document.getElementById("horaTermino").value = "";
    $('.create-page').animate({height: "toggle", opacity: "toggle"}, "slow");
    $('.bCreateEvent').show();
    $('.bCreate_cancelEvent').hide();
    initMap();
});


function initMap() {
    map = new google.maps.Map(document.getElementById('mapa'), {
        center: {lat: 20.693382, lng: -103.421126},
        zoom: 15
    });
    for (var i = 0; i < document.getElementsByClassName("latitud").length; i++) {
        latitudes[i] = parseFloat(document.getElementsByClassName("latitud")[i].innerHTML);
        longitudes[i] = parseFloat(document.getElementsByClassName("longitud")[i].innerHTML);
//        locations[i]=
//            [parseInt(document.getElementsByClassName("latitud")[i].innerHTML),parseInt(document.getElementsByClassName("longitud")[i].innerHTML)];


    }

    generateMarkers();
}



function generateMarkers() {
    var li;   // Get the first <h1> element in the document
    var lats = document.getElementsByClassName("latitud");
    var elements = document.getElementsByClassName('result');
    var colorToCompare = "rgb(229, 229, 229)"; //e5e5e5
    var colorToCompare2 = "rgb(242,242,242)";//f2f2f2
    var contenido = [];


    //Genera los marcadores personalizados al cargar el mapa por primera vez
    for (var i = 0; i < lats.length; i++) {
        infowindow = new google.maps.InfoWindow({
            content: contenido[i]
        });
        markers = new google.maps.Marker({
            position: new google.maps.LatLng(latitudes[i], longitudes[i]),
            map: map,
            id: i,
        });


        //Obtiene el id marker seleccionado
        (function (markers, i) {
            // add click event
            google.maps.event.addListener(markers, 'click', function () {
                infowindow.setContent(document.getElementsByClassName("title")[i].innerHTML);
                if (elements[i].style.backgroundColor === colorToCompare) {

                    elements[i].style.backgroundColor = "#f2f2f2";



                } else {
                    for (var x = 0; x < elements.length; x++) {
                        if (elements[x].style.backgroundColor === colorToCompare) {
                            elements[x].style.backgroundColor = "#f2f2f2";

                        }
                    }

                    elements[i].style.backgroundColor = "#e5e5e5";


                }
                infowindow.open(map, markers);
            });
        })(markers, i);

        (function (markers, i) {
            // add click event
            google.maps.event.addListener(markers, 'mouseover', function () {

                infowindow.setContent(document.getElementsByClassName("title")[i].innerHTML);
                elements[i].style.backgroundColor = "#e5e5e5";
                infowindow.open(map, markers);
            });
        })(markers, i);

        (function (markers, i) {
            // add click event
            google.maps.event.addListener(markers, 'mouseout', function () {
                infowindow.close();
                elements[i].style.backgroundColor = "#f2f2f2";
            });
        })(markers, i);
        var results = document.getElementsByClassName("result");
        (function (markers, i) {
            // add click event

            google.maps.event.addDomListener(results[i], "click", function () {
                google.maps.event.trigger(markers, "click");
            });
        })(markers, i);

        (function (markers, i) {
            // add click event

            google.maps.event.addDomListener(results[i], "mouseover", function () {
                google.maps.event.trigger(markers, "mouseover");
            });
        })(markers, i);

        (function (markers, i) {
            // add click event

            google.maps.event.addDomListener(results[i], "mouseout", function () {
                google.maps.event.trigger(markers, "mouseout");
            });
        })(markers, i);


    }
}
function toggleBounce() {
    if (marker.getAnimation() !== null) {
        marker.setAnimation(null);
    } else {
        marker.setAnimation(google.maps.Animation.BOUNCE);
    }
}

function geocodeLatLng(geocoder, map, latitude, longitude) {
    //var input = document.getElementById('latlng').value;
    var latlng = {lat: parseFloat(latitude), lng: parseFloat(longitude)};
    geocoder.geocode({'location': latlng}, function (results, status) {
        if (status === google.maps.GeocoderStatus.OK) {
            if (results[1]) {

            } else {
                window.alert('No results found');
            }
            var info;
            console.log(results);
            if (results.length == 8) {
                document.getElementById("city").value = results[4].address_components[0].long_name;
                document.getElementById("state").value = results[6].address_components[0].long_name;
                document.getElementById("zip").value = results[3].address_components[0].long_name;
                document.getElementById("suburb").value = results[1].address_components[0].long_name;
                if (isNaN(results[0].address_components[0].long_name) == false) {
                    document.getElementById("address").value = results[0].address_components[1].long_name + " " + results[0].address_components[0].long_name;
                } else {
                    document.getElementById("address").value = results[0].address_components[0].long_name;
                }
            }
            if (results.length == 7) {
                document.getElementById("city").value = results[3].address_components[0].long_name;
                document.getElementById("state").value = results[5].address_components[0].long_name;
                document.getElementById("zip").value = results[2].address_components[0].long_name;
                document.getElementById("suburb").value = results[0].address_components[1].long_name;
                if (isNaN(results[0].address_components[0].long_name) == false) {
                    document.getElementById("address").value = results[0].address_components[1].long_name + " " + results[0].address_components[0].long_name;
                } else {
                    document.getElementById("address").value = results[0].address_components[0].long_name;
                }
            }


        } else {
            window.alert('Geocoder failed due to: ' + status);
        }
    });
}

function initMapUpdate(latitud, longitud) {
    map = new google.maps.Map(document.getElementById('mapa'), {
        center: {lat: latitud, lng: longitud},
        zoom: 15
    });

    marker = new google.maps.Marker({
        map: map,
        draggable: true,
        animation: google.maps.Animation.DROP,
        position: {lat: latitud, lng: longitud}
    });
    var geocoder = new google.maps.Geocoder;
    google.maps.event.addListener(map, 'click', function (event) {
        marker.setMap(null)
        // alert(event.latLng.lat() + ", " + event.latLng.lng())
        marker = new google.maps.Marker({
            map: map,
            draggable: true,
            animation: google.maps.Animation.DROP,
            position: {lat: event.latLng.lat(), lng: event.latLng.lng()}
        });
        google.maps.event.addListener(marker, 'dragend', function (event) {
            document.getElementById("latitude").value = this.getPosition().lat();
            document.getElementById("longitude").value = this.getPosition().lng();
        });
        google.maps.event.addListener(marker, 'dragend', function (event) {
            geocodeLatLng(geocoder, map, this.getPosition().lat(), this.getPosition().lng());
        });
        google.maps.event.addListener(marker, 'click', function (event) {
            document.getElementById("latitude").value = event.latLng.lat();
            document.getElementById("longitude").value = event.latLng.lng();
        });
        google.maps.event.addListener(marker, 'click', function (event) {
            geocodeLatLng(geocoder, map, event.latLng.lat(), event.latLng.lng());
        });
    });

    google.maps.event.addListener(marker, 'dragend', function (event) {
        document.getElementById("latitude").value = this.getPosition().lat();
        document.getElementById("longitude").value = this.getPosition().lng();
    });
    google.maps.event.addListener(marker, 'dragend', function (event) {
        geocodeLatLng(geocoder, map, this.getPosition().lat(), this.getPosition().lng());
    });
    google.maps.event.addListener(map, 'click', function (event) {
        document.getElementById("latitude").value = event.latLng.lat();
        document.getElementById("longitude").value = event.latLng.lng();
    });
    google.maps.event.addListener(map, 'click', function (event) {
        geocodeLatLng(geocoder, map, event.latLng.lat(), event.latLng.lng());
    });

}

function initMapInformation(latitud, longitud) {
    
    map = new google.maps.Map(document.getElementById('mapaInformation'), {
        center: {lat: parseFloat(latitud), lng: parseFloat(longitud)},
        zoom: 15
    });

    marker = new google.maps.Marker({
        map: map,
        draggable: false,
        animation: google.maps.Animation.DROP,
        position: {lat:parseFloat(latitud), lng: parseFloat(longitud)}
    });
    

}

$('#bFotos').click(function () {
    $('#mapa').hide();
    $('#galleryPlaces').show();
    $('#bAgregarFoto').show();
    
});

$('#bFotosInformation').click(function () {
    $('#mapaInformation').hide();
    $('#galleryPlaces').show();
    $('#listInviteFriends').hide();
    
});
$('#bMapaInformation').click(function () {
    $('#mapaInformation').show();
    $('#galleryPlaces').hide();
    $('#listInviteFriends').hide();
});

var slideIndex = 1;
showDivs(slideIndex);

function plusDivs(n) {
  showDivs(slideIndex += n);
}

function showDivs(n) {
  var i;
  var x = document.getElementsByClassName("mySlides");
  if (n > x.length) {slideIndex = 1}
  if (n < 1) {slideIndex = x.length}
  for (i = 0; i < x.length; i++) {
     x[i].style.display = "none";
  }
  x[slideIndex-1].style.display = "block";
}

function initMapDefault(){
    map = new google.maps.Map(document.getElementById('mapa'), {
        center: {lat: 20.693382, lng: -103.421126},
        zoom: 15
    });

    marker = new google.maps.Marker({
        map: map,
        draggable: true,
        animation: google.maps.Animation.DROP,
        position: {lat: 20.693051, lng: -103.418873}
    });
}

function goBack() {
    window.history.back();
}

function comprobarContraseña(){
    var texto = document.getElementById('password').value;
    var comprueba = document.getElementById('password2').value;
    var rModify = document.getElementById('rModify');
    if(texto == comprueba){
        $('#ifMatch').show();
        $('#ifNotMatch').hide();
        rModify.disabled = false;
    }else{
        $('#ifNotMatch').show();
        $('#ifMatch').hide();
        rModify.disabled = true;
    }
}