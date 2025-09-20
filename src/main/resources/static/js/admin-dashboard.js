const sweetList = document.getElementById("sweetList");

// Fetch all sweets
async function loadSweets() {
    try {
        const response = await fetch("/api/sweets");
        const sweets = await response.json();
        sweetList.innerHTML = "";

        sweets.forEach(sweet => {
            const col = document.createElement("div");
            col.className = "col-md-4 mb-3";
            col.innerHTML = `
                <div class="card">
                    <div class="card-body">
                        <h5 class="card-title">${sweet.name}</h5>
                        <p class="card-text">${sweet.description}</p>
                        <p class="card-text">Category: ${sweet.category}</p>
                        <p class="card-text">Price: $${sweet.price}</p>
                        <p class="card-text">Quantity: ${sweet.quantity}</p>
                        <button class="btn btn-primary me-2" onclick="editSweet(${sweet.id})">Edit</button>
                        <button class="btn btn-danger" onclick="deleteSweet(${sweet.id})">Delete</button>
                    </div>
                </div>
            `;
            sweetList.appendChild(col);
        });
    } catch (err) {
        console.error(err);
        alert("Error fetching sweets.");
    }
}

// Edit Sweet
function editSweet(id) {
    window.location.href = `edit-sweet.html?id=${id}`;
}

// Delete Sweet
async function deleteSweet(id) {
    if (!confirm("Are you sure you want to delete this sweet?")) return;

    try {
        const response = await fetch(`/api/sweets/${id}`, {
            method: "DELETE"
        });

        if (response.status === 204) {
            alert("Sweet deleted successfully!");
            loadSweets();
        } else {
            alert("Failed to delete sweet.");
        }
    } catch (err) {
        console.error(err);
        alert("Error connecting to server.");
    }
}

// Logout
const logoutBtn = document.getElementById("logoutBtn");
logoutBtn.addEventListener("click", () => localStorage.removeItem("token"));

// Load sweets on page load
loadSweets();
