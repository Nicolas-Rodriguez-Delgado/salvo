
var url = new URLSearchParams((window.location.search));
var id = url.get("gp");

$(function () {
    fetch(`http://localhost:8080/api/game_view/${ id }`).

    then(function (response) {

        return response.json()

    }).then(function (data) {
        createGrid("firstTable");
        createGrid("secondTable");
        putShips(data);
        putSalvoes(data);
        putHits(data);
    });
});

function createGrid(oneTable){

    var tbl = document.getElementById(oneTable);

    var rows = ["","1", "2", "3", "4", "5", "6", "7", "8", "9", "10"];
    var cols = ["", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J"];

    for(var i = 0; i< rows.length; i++) {

        var newRow = document.createElement('tr')
        newRow.insertCell().innerHTML = `${ rows[i] }`

        for (var k=1; k<cols.length; k++){
            if(i==0){
                newRow.insertCell().innerHTML = `${ cols[k] }`
            } else {
                newRow.insertCell().setAttribute('class', `${ cols[k] }${ rows[i] }`)
            }
        }
        $(tbl).append(newRow)
    }
};

function putShips(myData) {


    var ud =  myData.gameplayer[0].player.email;
    document.getElementById("you").append(ud);
    var op = myData.gameplayer[1].player.email;
    document.getElementById("op").append(op);


    for (let i = 0; i<myData.ships.length; i++) {

        var ls = myData.ships[i].locations;

        ls.forEach(el => {

            var sc = document.getElementById("firstTable").querySelector(`.${el}`);
            sc.style.backgroundColor = "gray";
        });
    }
}

function putSalvoes(myData2) {

    for (let k =0; k < myData2.salvoes.length; k++){

        var locSalvo = myData2.salvoes[k].locations;

        locSalvo.forEach(loc => {
            var square = document.getElementById("secondTable").querySelector(`.${loc}`);
            square.style.backgroundColor = "green"; })

    }

    for (let j = 0; j < myData2.ships.length; j++){

        var locShips = myData2.ships[j].locations;

        for (let l = 0; l < myData2.OpponentSalvoes.length; l++){

            var locOpSalvo = myData2.OpponentSalvoes[l].locations;

        locOpSalvo.forEach(loc => {
            var square = document.getElementById("firstTable").querySelector(`.${loc}`);

            if (loc == locShips ){
                square.style.backgroundColor = "red";

            }else {
                square.style.backgroundColor = "green";
            }
        })
    }
}

function putHits(myData2) {

    for (let k = 0; k < myData2.salvoHits.length; k++) {

        var locHits = myData2.salvoHits;

        console.log(locHits);


        locHits.forEach(loc => {
            var square = document.getElementById("secondTable").querySelector(`.${loc}`);
            square.style.backgroundColor = "red";
        })

    }
}