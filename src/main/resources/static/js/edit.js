const form = document.getElementById("editSweetForm");

// Get sweet ID from URL
const urlParams = new URLSearchParams(window.location.search);
const sweetId = urlParams.get("id");

// Fetch sweet details
async function loadSweet() {
    try {
        const response = await fetch(`/api/sweets/${sweetId}`);
        const sweet = await response.json();

        document.getElementById("name").value = sweet.name;
        document.getElementById("category").value = sweet.category;
        document.getElementById("description").value = sweet.description;
        document.getElementById("price").value = sweet.price;
        document.getElementById("quantity").value = sweet.quantity;
    } catch (err) {
        console.error(err);
        alert("Failed to load sweet details.");
    }
}

// Handle form submit
form.addEventListener("submit", async (e) => {
    e.preventDefault();

    const updatedSweet = {
        name: document.getElementById("name").value,
        category: document.getElementById("category").value,
        description: document.getElementById("description").value,
        price: parseFloat(document.getElementById("price").value),
        quantity: parseInt(document.getElementById("quantity").value)
    };

    try {
        const response = await fetch(`/api/sweets/${sweetId}`, {
            method: "PUT",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(updatedSweet)
        });

        if (response.ok) {
            alert("Sweet updated successfully!");
            window.location.href = "dashboard.html";
        } else {
            alert("Failed to update sweet.");
        }
    } catch (err) {
        console.error(err);
        alert("Error connecting to server.");
    }
});

// Load sweet on page load
loadSweet();
