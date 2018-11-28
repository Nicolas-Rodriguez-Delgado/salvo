
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
        createGamesTable(data);
        currentUserName(data);
        console.log(data);
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

function createGamesTable(games){

        console.log(games);
        var table = $("#tbgames");

        for (let k=0; k<games.length; k++){

            if (games[k].gameplayers.length == 2) {

                var row = document.createElement("tr");
                row.insertCell().innerHTML = games[k].gameplayers[0].player.username;
                row.insertCell().innerHTML = games[k].gameplayers[1].player.username;
                table.append(row);
            }else if(games[k].gameplayers.length == 1){

                var row = document.createElement("tr");
                row.insertCell().innerHTML = games[k].gameplayers[0].player.username;
                row.insertCell().innerHTML = "No Player";
                let button = document.createElement("button");
                button.innerText = "Join Game";
                row.insertCell().append(button);
                table.append(row);

            } else {
                var row = document.createElement("tr");
                row.insertCell().innerHTML = "No Player";
                row.insertCell().innerHTML = "No Player";
                let button = document.createElement("button");
                button.innerText = "Join Game";
                row.insertCell().append(button);
                table.append(row);
            }
        }
}



function createGames() {


    $.post("/api/games")
        .done(resp => {

            var table = $(".gamesTable");
            var row = document.createElement("tr");
            row.insertCell().innerHTML = $(".title").innerHTML;
            table.append(row);
            alert("New game created!");
            console.log(resp);
            window.location.href= "game.html?gp="+resp;
        })
        .catch(err => alert("You must be logged in"));

}


