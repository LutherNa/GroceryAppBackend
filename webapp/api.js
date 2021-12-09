
// API calls

const apiBaseUrl = 'localhost:8080/'

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
function verifyUser(user = {}) {
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
        body: locationSearch,
        headers: {
            'Content-Type': 'application/json'
        }
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
    });
    const myJson = await response.json(); 
    // parse object search results
}

// button 

// function locButton () {
//     locations = searchLocations({"zipCode":"25461"});
//     document.write(locations);
// }