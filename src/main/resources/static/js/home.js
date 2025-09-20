const sweetsContainer = document.getElementById("sweetsContainer");
const searchBtn = document.getElementById("searchBtn");

async function fetchSweets(name = "", category = "") {
    let url = `/api/sweets/search?name=${name}&category=${category}`;
    try {
        const res = await fetch(url);
        const sweets = await res.json();
        renderSweets(sweets);
    } catch (err) {
        console.error(err);
        sweetsContainer.innerHTML = "<p class='text-danger'>Failed to load sweets.</p>";
    }
}

function renderSweets(sweets) {
    sweetsContainer.innerHTML = "";
    if (sweets.length === 0) {
        sweetsContainer.innerHTML = "<p>No sweets found.</p>";
        return;
    }

    sweets.forEach(sweet => {
        const sweetCard = document.createElement("div");
        sweetCard.className = "col-md-3 mb-4";
        sweetCard.innerHTML = `
            <div class="card h-100">
                <div class="card-body">
                    <h5 class="card-title">${sweet.name}</h5>
                    <p class="card-text">${sweet.description}</p>
                    <p>Category: ${sweet.category}</p>
                    <p>Price: $${sweet.price.toFixed(2)}</p>
                    <p>Available: ${sweet.quantity}</p>
                    <button class="btn btn-success w-100" ${sweet.quantity === 0 ? "disabled" : ""} onclick="purchaseSweet(${sweet.id})">Purchase</button>
                </div>
            </div>
        `;
        sweetsContainer.appendChild(sweetCard);
    });
}

async function purchaseSweet(id) {
    const quantity = prompt("Enter quantity to purchase:");
    if (!quantity) return;

    try {
        const res = await fetch(`/api/sweets/${id}/purchase?quantity=${quantity}`, { method: "POST" });
        if (res.ok) {
            alert("Purchase successful!");
            fetchSweets(); // refresh list
        } else {
            alert("Purchase failed!");
        }
    } catch (err) {
        console.error(err);
        alert("Error connecting to server.");
    }
}

// Search button click
searchBtn.addEventListener("click", () => {
    const name = document.getElementById("searchName").value;
    const category = document.getElementById("searchCategory").value;
    fetchSweets(name, category);
});

// Load all sweets on page load
fetchSweets();
