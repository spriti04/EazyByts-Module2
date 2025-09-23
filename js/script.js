// GLOBAL VARIABLES
// ----------------

let currentUser = null; // Stores logged in user's info
const BASE_URL = "http://localhost:8080" // Backend URL

// SHOW REGISTRATION FORM
// ----------------------

function showRegister() {
    document.getElementById("login-section").classList.add("hidden");
    document.getElementById("register-section").classList.remove("hidden");
}

// SHOW LOGIN FORM
// ---------------

function showLogin() {
    document.getElementById("register-section").classList.add("hidden");
    document.getElementById("login-section").classList.remove("hidden");
}

// LOGIN FUNCTION
// --------------

function login(){
    
    const username = document.getElementById("username").value;
    const password = document.getElementById("password").value;

    // Send POST request to SB Security
    fetch(BASE_URL + "/api/login",{
        method: "POST",
        headers: { 
            "Content-Type": "application/json"
        },
        body: JSON.stringify({
            username: username,
            password: password
        }),
        credentials: "include"
    })
    .then(res => {
        if(res.ok) return res.json();
        throw new Error("Invalid login!");
    })
    .then(user => {
        currentUser = user;

        // Show success
        document.getElementById("login-msg").innerText = 
                `${user.username} logged in successfully!`;

        // Show Dashboard
        document.getElementById("login-section").classList.add("hidden");
        document.getElementById("register-section").classList.add("hidden");
        document.getElementById("dashboard").classList.remove("hidden");

        // Set username
        document.getElementById("user-name").innerText = user.username;

        // Load Balance
        fetchBalance();
    })
    .catch(err => {
        document.getElementById("login-msg").innerText = err.message;
    });

}

// REGISTER FUNCTION
// -----------------

function register(){
    const username = document.getElementById("reg-username").value;
    const email = document.getElementById("reg-email").value;
    const password = document.getElementById("reg-password").value;
    const role = document.getElementById("reg-role").value;

    fetch(BASE_URL + "/api/save", {
        method: "POST",
        headers:{"Content-Type": "application/json"},
        body: JSON.stringify({username, email, password, role}),
        credentials: "include"
    })
    .then(res => {
        if(res.ok) return res.json();
        throw new Error("Registration failed!");
    })
    .then(user => {
        document.getElementById("register-msg").innerText = `User ${user.username} registered successfully!`;
        showLogin();
    })
    .catch(err => {
        document.getElementById("register-msg").innerText = err.message;
    });

}

// FETCH BALANCE
// -------------

function fetchBalance() {
    if(!currentUser) return;

    fetch(`${BASE_URL}/api/${currentUser.id}/balance`, {
        method: "GET",
        credentials: "include"
    })
    .then(res => {
        if(!res.ok) throw new Error("Balance fetch failed");
        return res.json();
    })
    .then(data => {
        document.getElementById("balance").innerText = data;
    })
    .catch(err => console.error(err));
}

// BUY STOCK
// ---------

function buyStock(symbol, qty) {
    if(!currentUser) return;

    fetch(BASE_URL + "/trade/buy", {
       method: "POST",
       credentials: "include",
       headers: {
        "Content-Type": "application/json"
       },
       body: JSON.stringify({
        personId: currentUser.id,
        symbol,
        quantity: qty
       })
    })
    .then(res => {
        if (!res.ok) throw new Error("Buy stock failed");
        return res.json();
    })
    .then(data => {
        alert(data.message);
        fetchBalance();
    })
    .catch(err => console.error(err));   
}

// SELL STOCK
// ----------

function sellStock(symbol, qty) {
    if (!currentUser) return;

    fetch(BASE_URL + "/trade/sell", {
        method: "POST",
        credentials: "include",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({
            personId: currentUser.id,
            symbol,
            quantity: qty
        })
    })
    .then(res => {
        if (!res.ok) throw new Error("Sell stock failed");
        return res.json();
    })
    .then(data => {
        alert(data.message);
        fetchBalance(); // refresh balance
    })
    .catch(err => console.error(err));
}

// VIEW PORTFOLIO
// --------------

function showPortfolio(){
    fetch(`${BASE_URL}/portfolio/${currentUser.id}`, {
        method: "GET",
        credentials: "include"
    })
    .then(res => res.json())
    .then(data => {
        let html = "<h3>Your Portfolio</h3><ul>";
        data.forEach(p => {
            html += `<li>${p.symbol} - ${p.quantity} shares</li>`;   
        });
        html += "</ul>";
        document.getElementById("results").innerHTML = html;
    })
    .catch(err => console.error(err));
}

// VIEW TRANSACTIONS
// -----------------

function showTransactions() {
    fetch(BASE_URL + `/transaction/${currentUser.id}`, {
        method: "GET",
        credentials: "include"
    })
    .then(res => res.json())
    .then(data => {
        let html = "<h3>Transactions</h3><ul>";
        data.forEach(tx => {
            html += `<li>${tx.type} ${tx.quantity} ${tx.symbol} @ ${tx.price}</li>`;   
        });
        html += "</ul>";
        document.getElementById("results").innerHTML = html;
    })
    .catch(err => console.error(err));
}

// VIEW TRADES
// -----------

function showTrades(){
    fetch(BASE_URL + `/trade/getTrades/${currentUser.id}`,{
        method: "GET",
        credentials: "include"
    })
    .then(res => res.json())
    .then(data => {
        console.log("Trades:", data);
        let html = "<h3>Your Trades</h3><ul>";
        data.forEach(tr => {
            html += `<li>${tr.type} ${tr.quantity} ${tr.symbol} @ ${tr.price}</li>`;
        });
        html += "</ul>";

        document.getElementById("results").innerHTML = html;
    })
    .catch(err => console.error(err));
}

// DEPOSIT
// -------

function deposit(amount) {
    fetch(BASE_URL + `/api/${currentUser.id}/deposit?amount=${amount}`, {
        method: "POST",
        credentials: "include"
    })
    .then(res => res.json())
    .then(data => {
        alert(data.message);
        fetchBalance();
    })
    .catch(err => console.error(err));
}

// WITHDRAW
// --------

function withdraw(amount) {
    fetch(BASE_URL + `/api/${currentUser.id}/withdraw?amount=${amount}`, {
        method: "POST",
        credentials: "include"
    })
    .then(res => res.json())
    .then(data => {
        alert(data.message);
        fetchBalance();
    })
    .catch(err => console.error(err));
}

// ADMIN FUNCTIONS
// ---------------

function viewAllUsers() {
    fetch(BASE_URL + `/admin/users`, {
        method: "GET",
        credentials: "include"
    })
    .then(res => res.json())
    .then(data => {
        console.log("All Users:", data);
        
    let html = "<h3>All Users (Admin)</h3><ul>";
    data.forEach( u => {
        html += `<li>${u.username} (${u.role})</li>`;
    });
    html += "</ul>";

    document.getElementById("results").innerHTML = html;
    })
    .catch(err => console.error(err));
}
