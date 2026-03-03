document.addEventListener("DOMContentLoaded", () => {
    loadProducts();
});

async function loadProducts() {
    try {
        const response = await fetch("http://localhost:8080/products");
        const products = await response.json();

        const container = document.getElementById("product-container");
        container.innerHTML = "";

        products.forEach(product => {
            const card = document.createElement("div");
            card.classList.add("product-card");

            card.innerHTML = `
                <img src="http://localhost:8080/images/${product.image}" 
                     alt="${product.name}">
                <h3>${product.name}</h3>
                <p>$${product.price}</p>
                <span class="tag">${product.category}</span>
            `;

            container.appendChild(card);
        });

    } catch (error) {
        console.error("Error loading products:", error);
    }
}