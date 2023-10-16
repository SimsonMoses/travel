// Assuming the ID is passed as a query parameter, for example, accommodationDetails.html?id=1
const params = new URLSearchParams(window.location.search);
const id = params.get("id");

fetch(`/getDetails?id=${id}`)
    .then(response => response.json())
    .then(data => {
        const detailsDiv = document.getElementById("details");
        detailsDiv.innerHTML = `
            <p>Name: ${data.name}</p>
            <p>Location: ${data.location}</p>
            <p>Price: ${data.price}</p>
            <p>Amenities: ${data.amenities}</p>
            <p>Rating: ${data.rating}</p>
        `;
    })
    .catch(error => {
        console.error('There was an error fetching details:', error);
    });
