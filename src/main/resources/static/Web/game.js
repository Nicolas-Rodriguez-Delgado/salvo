
var url = new URLSearchParams((window.location.search));
var id = url.get("gp");

$(function () {
    fetch(`http://localhost:8080/api/game_view/${ id }`).

    then(function (response) {

        return response.json()

    }).then(function (data) {


        createGrid("ships");
        createGrid("salvoes");
        putShips(data);
        putSalvoes(data);
    });
});

    function createGrid(oneTable){

        oneTable = document.getElementById("firstTable");

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
            $(oneTable).append(newRow)
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

    function putSalvoes() {

        var st = document.getElementById("secondTable");
    }


