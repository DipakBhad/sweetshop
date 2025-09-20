// Login form submission
document.addEventListener("DOMContentLoaded", () => {
    const loginForm = document.getElementById("loginForm");

    if (loginForm) {
        loginForm.addEventListener("submit", async (e) => {
            e.preventDefault();

            const email = document.getElementById("email").value;
            const password = document.getElementById("password").value;

            try {
                const response = await fetch("/api/auth/login", {
                    method: "POST",
                    headers: {
                        "Content-Type": "application/json"
                    },
                    body: JSON.stringify({ email, password })
                });

                if (response.ok) {
                    const data = await response.json();
                    localStorage.setItem("token", data.token);  // store JWT token
                    alert("Login successful!");
                    window.location.href = "dashboard.html";
                } else {
                    alert("Login failed! Check your credentials.");
                }
            } catch (err) {
                console.error(err);
                alert("Error connecting to server.");
            }
        });
    }
});

// Register form submission
const registerForm = document.getElementById("registerForm");

if (registerForm) {
    registerForm.addEventListener("submit", async (e) => {
        e.preventDefault();

        const name = document.getElementById("name").value;
        const email = document.getElementById("email").value;
        const password = document.getElementById("password").value;

        try {
            const response = await fetch("/api/auth/register", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify({ name, email, password, isAdmin: false })
            });

            if (response.ok) {
                alert("Registration successful! Please login.");
                window.location.href = "login.html";
            } else {
                const errData = await response.json();
                alert("Registration failed: " + (errData.message || "Check your data"));
            }
        } catch (err) {
            console.error(err);
            alert("Error connecting to server.");
        }
    });
}


// Load all sweets
async function loadSweets(name = "", category = "") {
    let url = `/api/sweets/search?name=${name}&category=${category}`;

    try {
        const response = await fetch(url);
        const sweets = await response.json();

        const sweetList = document.getElementById("sweetList");
        sweetList.innerHTML = "";

        sweets.forEach(sweet => {
            const card = document.createElement("div");
            card.className = "col-md-3 mb-3";
            card.innerHTML = `
                <div class="card h-100 shadow-sm">
                    <div class="card-body">
                        <h5 class="card-title">${sweet.name}</h5>
                        <p class="card-text">Category: ${sweet.category}</p>
                        <p class="card-text">Price: $${sweet.price}</p>
                        <p class="card-text">Quantity: ${sweet.quantity}</p>
                        <button class="btn btn-success w-100" ${sweet.quantity === 0 ? "disabled" : ""}
                            onclick="purchaseSweet(${sweet.id})">Purchase</button>
                    </div>
                </div>
            `;
            sweetList.appendChild(card);
        });

    } catch (err) {
        console.error(err);
        alert("Failed to load sweets.");
    }
}

// Purchase sweet
async function purchaseSweet(id) {
    const quantity = prompt("Enter quantity to purchase:");
    if (!quantity) return;

    try {
        const response = await fetch(`/api/sweets/${id}/purchase?quantity=${quantity}`, { method: "POST" });
        if (response.ok) {
            alert("Purchase successful!");
            loadSweets(); // reload the list
        } else {
            alert("Purchase failed.");
        }
    } catch (err) {
        console.error(err);
        alert("Error connecting to server.");
    }
}

// Search functionality
const searchBtn = document.getElementById("searchBtn");
searchBtn.addEventListener("click", () => {
    const name = document.getElementById("searchName").value;
    const category = document.getElementById("searchCategory").value;
    loadSweets(name, category);
});

// Logout
const logoutBtn = document.getElementById("logoutBtn");
logoutBtn.addEventListener("click", () => {
    localStorage.removeItem("token");
});

// Load all sweets on page load
loadSweets();
