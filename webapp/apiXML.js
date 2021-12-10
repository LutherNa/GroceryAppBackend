
const apiBaseUrl = 'http://localhost:8080/api'

function locButton() {
    getLocations()
    //$("button").on("click",function(){
    //console.log("hii");
    // var data = {"zipCode":"37601"};
    // $.ajax({
    //     type: "Post",
    //     url:apiBaseUrl + "/location",
    //     data: JSON.stringify(data),
    //     headers:{
    //         "Accept":"application/json",//depends on your api
    //         "Content-type":"application/Json"//depends on your api
    //     },   
    //     success:function(response){
    //         var r = response;
    //         console.log(r)
    //         for(let location of r){
    //             var hold = document.createElement(location.tag);
    //             hold.innerHTML = "<p> Name: " + location.name + ", Address: " + location.address.addressLine1 + " " + location.address.city + "</p>";
    //             document.getElementById("locationList").appendChild(hold);
    //             console.log(location.name);
    //         }
    //     }
    // });

}

function renderLocations(locations) {
    while (locationList.firstChild) locationList.removeChild(firstChild);
    locations.forEach(location => {
        var hold = document.createElement(location.tag)
        hold.innerHTML = "<p> Name: " + location.name + ", Address: " + location.address.addressLine1 + " " + location.address.city + "</p>";
        document.getElementById("locationList").appendChild(hold)
        console.log(location.name)
    })
}

function getLocations() {
    let xhr = new XMLHttpRequest(); //Used for sending and receiving requests
    let data = { "zipCode": "37601" };

    xhr.onreadystatechange = function () {
        if (this.readyState === XMLHttpRequest.DONE) {
            console.log(xhr.responseText);
            renderLocations(JSON.parse(xhr.responseText));
        }
    }

    xhr.open("POST", apiBaseUrl + "/location")
    xhr.setRequestHeader("Content-Type", "application/json")
    xhr.send(JSON.stringify(data))

}

function buildLocations(tree, container) {
    tree.forEach(function (node) {

        var el = document.createElement(node.tag);

        if (Array.isArray(node.content)) {
            buildLocations(node.content, el);
        }
        else if (typeof (node.content) == 'object') {
            buildLocations([node.content], el);
        }
        else {
            el.innerHTML = node.content;
        }

        container.appendChild(el);
    });
}

