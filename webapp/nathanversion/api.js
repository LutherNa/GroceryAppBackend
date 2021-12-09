
// API calls

let apiBaseUrl = 'http://localhost:8080'

// UserController
    //API returns User object
async function createUser(newUser = {}) {
    let response = await fetch(apiBaseUrl + '/users', {
        method: 'PUT',
        body: JSON.stringify(newUser),
        headers: {
            'Content-Type': 'application/json'
        }
    });
    const myJson = await response.json(); //extract JSON from the http response
    // do something with myJson
  }

    //API returns boolean 
async function verifyUser(user = {}) {
    let response = await fetch(apiBaseUrl + '/users', {
        method: 'POST',
        body: JSON.stringify(user),
        headers: {
            'Content-Type': 'application/json'
        }
    });
    const myJson = await response.json(); //extract JSON from the http response
    // do something with myJson
}

// Location Controller
    //API returns list of Location objects or null
async function searchLocations(locationSearch = {}) {
    let response = await fetch(apiBaseUrl + '/location', {
        method: 'POST',
        body: JSON.stringify(locationSearch),
        headers: {
            'Content-Type': 'application/json'
            // "Access-Control-Allow-Origin": "http://localhost:8080",
	        // "Access-Control-Allow-Methods": "GET, OPTIONS, POST, PUT"
        }
    })
    return response.json();
    // response.json().then ((data) => {
    //     console.log(data);
    //     return data;
    // });
}
// Product Controller
    //API returns list of Product objects or null
async function searchProducts(productSearch = {}) {
    let response = await fetch(apiBaseUrl + '/products', {
        method: 'POST',
        body: JSON.stringify(productSearch),
        headers: {
            'Content-Type': 'application/json'
        }
    }).then((response) => {
        return response;
    });
}

// button 

async function locButton () {
    let zipCode = document.getElementById("textLocation").value;
    let searchTerm = {"zipCode": zipCode};
    let locations = await Promise.resolve(searchLocations(searchTerm));
    let locationList = document.getElementById("locationList");
    let listEntry = document.createElement("locationsElement");
    listEntry.textContent = JSON.stringify(locations[0]);
    locationList.appendChild(listEntry);
    console.log(locations[0]);
    console.log("end of function");
}