(function (){
    fetch('http://localhost:8080/api/scores').
    then(response => {
        return response.json()
    }).then( data => {
        createScores(data);
    })
})()

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

            console.log(loss);

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

    var email = document.getElementById("email").value.toLowerCase();
    var password = document.getElementById("password").value;
    var currentName = document.getElementById("current");

    $.post("/api/login", {username: email, password: password})
        .done(success => {
            console.log("Logged in"),
            console.log(success)
            console.log(email);
            currentName = email;
            })
        .fail(err => console.log(err));
    $.post("/api/logout", {})


    // document.location.reload(true)

}