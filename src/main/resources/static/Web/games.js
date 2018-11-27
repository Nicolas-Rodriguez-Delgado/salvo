
(function () {

    fetch('http://localhost:8080/api/scores')
        .then(function (response) {
            return response.json()
    }).then(data => {
        createScores(data);
    }).catch(err => {
        console.log("error" + err)
    });

    fetch('http://localhost:8080/api/games', {credentials: 'include'})
        .then(function (response) {
            return response.json()
        }).then(data => {

        currentUserName(data);
    });


})();


function createScores(myData) {

    var tbl = document.getElementById("scoresTable");


    for (var i = 0; i < myData.length; i++) {

        var newRow = document.createElement('tr');
        newRow.insertCell().innerHTML = `${ myData[i].player }`;
        $(tbl).append(newRow);

        var total = 0;
        var wins = 0;
        var ties = 0;
        var loss = 0;

        for (var k=0; k< myData[i].scores.length; k++) {

            var score = myData[i].scores[k].score;

            if (score == 1.0) {
                wins++;
                total += 1.0;
            } else if (score == 0.5) {
                ties++
                total += 0.5;
            } else {
                loss++;
            }
            if(k == (myData[i].scores.length -1)) {
                newRow.insertCell().innerHTML = total;
                newRow.insertCell().innerHTML = wins;
                newRow.insertCell().innerHTML = loss;
                newRow.insertCell().innerHTML = ties;
            }


        }

    }

}



function login () {
    let email = document.getElementById("email").value.toLowerCase();
    let password = document.getElementById("password").value;

    log(email, password);
}

function log(email, password) {

    var currentName = document.getElementById("current");
    $.post("/api/login", {username: email, password: password})
        .done(resp => {

            currentName.innerText = email;

            alert("You are now logged in!");
        })
        .fail(err => console.log("Ocurrio un error: " + JSON.stringify(err)));
}



function logout() {

    var current = document.getElementById("current");
    var emailout= document.getElementById("email");
    current.innerHTML= "Please Log In";
    emailout.value = "";
    $.post("/api/logout").done(function() { console.log("logged out"); })

}
function signUp() {
    let emailRegistro = document.getElementById("emailsp").value.toLowerCase();
    let passwordRegistro = document.getElementById("passwordsp").value;

    $.post("/api/players", { username: emailRegistro, password: passwordRegistro })
        .then(success => {
            log(emailRegistro,passwordRegistro), console.log(success)
        }
        ).fail(err => {console.log("Ocurrio un: " + JSON.stringify(err))})
}

function currentUserName(name){



    var currentName = document.getElementById("current");


    if(name[0].loggedPlayer != null) {

        currentName.append(name[0].loggedPlayer.email);

    }else {
        currentName.append("Please Log In");
    }

}



function createGames() {

    document.addEventListener("click", createGames);

    $.post("/api/games")
        .done(resp => {
            // let currentUser = document.querySelector(".title").value;
            // console.log(currentUser);
            // let gamesTable = document.getElementById("gamesTable");
            // let newRo = document.createElement("tr")
            // newRo.insertCell().innerHTML = currentUser;
            // gamesTable.append(newRo);

            alert("New game created!");
        })
        .fail(err => console.log("error: " + JSON.stringify(err)));

}


