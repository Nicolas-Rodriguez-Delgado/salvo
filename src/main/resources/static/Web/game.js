var url = new URLSearchParams((window.location.search));
var id = url.get("gp");


onload = (function () {


    fetch(`http://localhost:8080/api/game_view/${ id }`).then(response => {

        return response.json()

    }).then( data => {
        createGrid("firstTable");
        createGrid("secondTable");
        putShips(data);
        putSalvoes(data);
        // putHits(data);
        document.getElementById("fire").addEventListener("click",function () {

            fireSalvo(data);
        })


    }).catch(err => console.log(err));


});






function createGrid(oneTable) {

    var tbl = document.getElementById(oneTable);

    var rows = ["", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10"];
    var cols = ["", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J"];

    for (var i = 0; i < rows.length; i++) {

        var newRow = document.createElement('tr')
        newRow.insertCell().innerHTML = `${ rows[i] }`

        for (var k = 1; k < cols.length; k++) {
            if (i == 0) {
                newRow.insertCell().innerHTML = `${ cols[k] }`
            } else {
                newRow.insertCell().setAttribute('class', `${ cols[k] }${ rows[i] }`);
            }
        }

        $(tbl).append(newRow)
    }


};


function putShips(myData) {


    allData = myData;
    var ud = myData.gameplayer[0].player.email;
    document.getElementById("you").append(ud);
    if (myData.gameplayer[1] != null) {
        var op = myData.gameplayer[1].player.email;
        document.getElementById("op").append(op);
    }

    for (let i = 0; i < myData.ships.length; i++) {

        var ls = myData.ships[i].locations;

        ls.forEach(el => {

            var sc = document.getElementById("firstTable").querySelector(`.${el}`);
            sc.classList.add("ship");
        });
    }
}

function putSalvoes(myData2) {

    var shipLocations = [];
    var opSalvo = [];
    var hits = [];

    for (let k = 0; k < myData2.salvoes.length; k++) {

        var locSalvo = myData2.salvoes[k].locations;

        locSalvo.forEach(loc => {
            var square = document.getElementById("secondTable").querySelector(`.${loc}`);
            square.style.backgroundColor = "green";
            // square.classList.add("ship")
        })

    }

    for (let j = 0; j < myData2.ships.length; j++) {

        myData2.ships[j].locations.forEach(el => shipLocations.push(el));
    }

    if (myData2.OpponentSalvoes != null) {

        for (let l = 0; l < myData2.OpponentSalvoes.length; l++) {

            myData2.OpponentSalvoes[l].locations.forEach(el => opSalvo.push(el));

        }


        opSalvo.forEach((loca) => shipLocations.forEach((loca2) => {
            var square = document.getElementById("firstTable").querySelector(`.${loca}`);

            if (loca == loca2) {
                square.classList.add("hit");
                console.log(loca);
            } else {
                square.classList.add("noHit");
            }

        }))
        console.log(hits);
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


function addClass(oneCell) {

    if (oneCell != null) {
        oneCell.classList.add("high");
    } else if (oneCell == null) {
        $("#tb").onmouseenter(
            $('.high').removeClass('high'));

    } else {
        oneCell.classList.remove("high");
    }

}

var size;
var type;

function chooseShip(_this) {

    Array.from($('div')).forEach(el => el.classList.remove('activeShip'))

    _this.classList.add('activeShip')

    var arr = [];


    $("#tb td").on("mouseenter", function (e) {

        e.stopPropagation();

        var data = $(this).attr('class');


        size = $(".activeShip")[0].dataset.lenght
        type = $(".activeShip")[0].dataset.type

        for (let i = 0; i < size; i++) {

            e.stopPropagation();
            if (document.getElementById("vert").checked) {

                let num = data.match(/\d+/g).map(Number)[0]
                let clase = data.charAt(0).concat(i + num).toString();
                let cell = document.querySelector(`.${clase}`);

                addClass(cell);
                arr.push(clase);

            } else if (document.getElementById("hori").checked) {

                let clase2 = String.fromCharCode(data.charCodeAt(0) + i).concat(data.match(/\d+/g).map(Number)[0]);
                let cell = document.querySelector(`.${clase2}`);

                addClass(cell);
                arr.push(clase2);
            }
        }
    });

    $("#tb td").on("mouseleave", function (e) {

        e.stopPropagation()

        for (let j = 0; j < arr.length; j++) {

            let cellOut = document.querySelector(`.${arr[j]}`);
            if (cellOut != null) {
                cellOut.classList.remove("high");
            }
        }
    });

}

var allLoc = [];
var placedType = [];

var shipsList = [
    {
        "type": "airCarrier",
        "location": []
    }, {
        "type": "battleship",
        "location": []
    }, {
        "type": "submarine",
        "location": []
    }, {
        "type": "destroyer",
        "location": []
    }, {
        "type": "patrol",
        "location": []
    }
]


function placeShip() {

    var loc = [];


    var highCell = Array.from(document.getElementsByClassName("high"));

    var cellClass = highCell.classList


    highCell.forEach(ele => {
        console.log()
    });


    highCell.forEach(e => {


        if (e.classList.contains("ship")) {

            return;

        } else {
            if (!allLoc.includes(e)) {

                loc.push(e.classList[0])
            }

        }
    })

    allLoc.forEach(el => {
        document.getElementById('tb').querySelector(`.${el}`).classList.remove("ship");
    })
    allLoc = []
    shipsList.forEach(ship => {
        if (ship.type == type) {
            if (loc.length == size) {
                if (!placedType.includes(type)) {


                    ship.location = loc;


                }
            }
        }
        allLoc = allLoc.concat(ship.location);
    })

    allLoc.forEach(el => {

        document.getElementById('tb').querySelector(`.${el}`).classList.add("ship");

    })
    console.log(shipsList);
}

function checkShips() {

    for (let i = 0; i < 5; i++) {

        if (shipsList[i].location.length != 0) {

            return true;

        } else {
            return false
        }
    }
}

function postShips() {


    var nn = id;
    console.log(checkShips());


    if (checkShips()) {

        $.post({

            url: `/api/games/players/${id}/ships`,
            data: JSON.stringify(this.shipsList),
            dataType: "text",
            contentType: "application/json"

        })
            .done(resp => {

                alert("game started")
                console.log(resp);
                window.location.href = "game.html?gp=" + nn


            }).catch(err => console.log(err))
    } else {
        alert("put all the ships")
    }

}

var listSalvoes = [];

function chooseSalvoes() {

    document.getElementById("tb2").addEventListener("click", function handler(e) {

        e.stopPropagation();

        var cl = event.target.classList[0]

        if (cl != listSalvoes[0] && cl != listSalvoes[0] && cl != listSalvoes[0] && listSalvoes.length < 3) {

            listSalvoes.push(cl);
            document.getElementById("tb2").querySelector(`.${cl}`).classList.add("salvo");

        } else {
            alert("Salvos ready to fire!")
        }

        this.removeEventListener("click", arguments.callee);

    })
console.log(listSalvoes);
}

function fireSalvo(allData) {


    let turn = allData.salvoes.length + 1;
    let locations = listSalvoes;
    console.log(locations);

    if (locations.length == 3) {
        $.post({
            url: `/api/games/players/${id}/salvoes`,
            data: JSON.stringify({turn: turn, locations: locations}),
            dataType: "text",
            contentType: "application/json"

        }).done(res => {

            alert("Salvos fired!!")
            // window.location.href = "game.html?gp=" + id


        })
            .catch(err => console.log(err))
    }
}










