// API calls

let url = "http://localhost:8080"

function searchLocations(data){
    let xhr = new XMLHttpRequest(); // this object sends requests and receives responses
    xhr.open("POST", url + "/location");
    xhr.send(data); 
    xhr.onreadystatechange = function () {
        if (this.readyState === XMLHttpRequest.DONE) {
            console.log(JSON.stringify(xhr.responseText));
            return JSON.parse(xhr.responseText);
        }
    }
}

function locButton() {
    // var data = new FormData();
    // data.append('zipCode', '24522');
    locs = searchLocations({"zipCode": "24522"});
    renderLocations(locs);
}

function renderLocations(locations) {
    locations.forEach((location) => {
        let element = document.createElement("p");
        element.textContent = location.locationId;
        locationList.appendChild(element);
    });
}