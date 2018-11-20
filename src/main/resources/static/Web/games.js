
(function () {

    var apiRequest1 = fetch('http://localhost:8080/api/scores').then(function (response) {
        return response.json()
    });


    var apiRequest2 = fetch('http://localhost:8080/api/games').then(function (response) {
        return response.json()

    });
    var combinedData = {"apiRequest1":{},"apiRequest2":{}};
            Promise.all([apiRequest1,apiRequest2]).then(function(values){
                combinedData["apiRequest1"] = values[0];
                combinedData["apiRequest2"] = values[1];
                return combinedData;
    })
    apiRequest1.then(data => createScores(data));
    apiRequest2.then(data2 => createGames(data2));

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

    console.log("regist11rado: " + email + " :1: " + password)
    $.post("/api/login", {username: email, password: password})
        .done(resp => {
            currentName.append(email);
            alert("You are now logged in!");
        })
        .fail(err => console.log("Ocurrio un error: " + JSON.stringify(err)));
}



function logout() {

    var current = document.getElementById("current");
    var emailout= document.getElementById("email");
    current.innerHTML= "";
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

function createGames(myData) {

    let gamesTable = document.getElementById("gamesTable");

    for(let j = 0; j< myData.length; j++) {
        let newGame = document.createElement("tr");
        if(myData[j].gameplayers != 0) {
            newGame.insertCell().innerHTML = `${ myData[j].gameplayers[0].player.username}`;
            newGame.insertCell().innerHTML = `${ myData[j].gameplayers[1].player.username}`;
            newGame.insertCell().innerHTML = ""
            newGame.insertCell().innerHTML = '<a href="" target="_parent"><button>Create me !</button></a>'
            gamesTable.append(newGame);
        }else {
            newGame.insertCell().innerHTML = "no player";
            newGame.insertCell().innerHTML = "no player";
            gamesTable.append(newGame);
        }

    }

}


