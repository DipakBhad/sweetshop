const form = document.getElementById("addSweetForm");

form.addEventListener("submit", async (e) => {
    e.preventDefault();

    const sweet = {
        name: document.getElementById("name").value,
        category: document.getElementById("category").value,
        description: document.getElementById("description").value,
        price: parseFloat(document.getElementById("price").value),
        quantity: parseInt(document.getElementById("quantity").value),
    };

    try {
        const response = await fetch("/api/sweets", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(sweet)
        });

        if (response.ok) {
            alert("Sweet added successfully!");
            form.reset();
        } else {
            alert("Failed to add sweet.");
        }
    } catch (err) {
        console.error(err);
        alert("Error connecting to server.");
    }
});

// Logout
const logoutBtn = document.getElementById("logoutBtn");
logoutBtn.addEventListener("click", () => {
    localStorage.removeItem("token");
});
