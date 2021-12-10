
const apiBaseUrl = 'http://localhost:8081/api'

$(window, document, undefined).ready(function() {
    $('.input').blur(function() {
      var $this = $(this);
      if ($this.val())
        $this.addClass('used');
      else
        $this.removeClass('used');
    });
});
  
  
$('#tab1').on('click' , function(){
    $('#tab1').addClass('login-shadow');
    $('#tab2').removeClass('signup-shadow');
});
  
$('#tab2').on('click' , function(){
    $('#tab2').addClass('signup-shadow');
    $('#tab1').removeClass('login-shadow');
});





function locButton () {
    getLocations()
}

let User = class {
    constructor(username, password){
        this.username = username,
        this.password = password
    };

    setId(id) {
        this.id = id;
    }
}

var user = new User(undefined,undefined);

function hide (elements) {
    elements = elements.length ? elements : [elements];
    for (var index = 0; index < elements.length; index++) {
      elements[index].style.display = 'none';
    }
}

function show (elements) {
    elements = elements.length ? elements : [elements];
    for (var index = 0; index < elements.length; index++) {
      elements[index].style.display = 'block';
    }
}

document.addEventListener("click", myFunction);
function myFunction() {
    if(user.username != undefined && user.password != undefined){
        show(document.getElementById("navBar"));
    } else {
        hide(document.getElementById("navBar"));
    }
}

function renderLocations(locations){
    while(locationList.firstChild) locationList.removeChild(firstChild);
    locations.forEach(location => {
        var hold = document.createElement(location.tag)
        hold.innerHTML = "<p> Name: " + location.name +
                         ", Address: " + location.address.addressLine1 + " " + location.address.city + "</p>";
        document.getElementById("locationList").appendChild(hold)
        console.log(location.name)
    })
}

function getLocations(){
    let xhr = new XMLHttpRequest(); //Used for sending and receiving requests
    let data = {"zipCode":"37601"};

    xhr.onreadystatechange = function(){
        if(this.readyState === XMLHttpRequest.DONE){
            console.log(xhr.responseText);
            renderLocations(JSON.parse(xhr.responseText));
        }
    }
    
    xhr.open("POST", apiBaseUrl + "/location")
    xhr.setRequestHeader("Content-Type","application/json")
    xhr.send(JSON.stringify(data))

}
 