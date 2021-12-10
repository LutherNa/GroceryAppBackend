
// API calls

const apiBaseUrl = 'http://localhost:8081/api'


// UserController
    //API returns User object
async function createUser(newUser = {}) {
    const response = await fetch(apiBaseUrl + '/users', {
        method: 'PUT',
        body: newUser,
        headers: {
            'Content-Type': 'application/json'
        }
    });
    const myJson = await response.json(); //extract JSON from the http response
    // do something with myJson
  }

    //API returns boolean 
async function verifyUser(user = {}) {
    const response = await fetch(apiBaseUrl + '/users', {
        method: 'POST',
        body: user,
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
    const response = await fetch(apiBaseUrl + '/location', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(locationSearch)
    }).then(function(response){
        console.log(response.status);
        if(!response.ok){
            throw new Error("HTTP status "+ response.status);
        }
        console.log(response.json);
    });
    const myJson = await response.json(); 
    return myJson
    // parse location search results
}
// Product Controller
    //API returns list of Product objects or null
async function searchProducts(productSearch = {}) {
    const response = await fetch(apiBaseUrl + '/products', {
        method: 'POST',
        body: productSearch,
        headers: {
            'Content-Type': 'application/json'
        }
    }).then(function(response){
        console.log(response.status);
        if(!response.ok){
            throw new Error("HTTP status "+ response.status);
        }
        return response.json();
    });
    const myJson = await response.json(); 
    // parse object search results
}

// button 

function locButton () {
    locations = searchLocations({"zipCode":"37601"});
    document.write(locations);
}