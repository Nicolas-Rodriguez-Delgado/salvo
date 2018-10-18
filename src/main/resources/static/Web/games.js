
$(function () {
    fetch("http://localhost:8080/api/games").

      then(function (response) {

        return response.json()

       }).then(function (data) {

           onLoad(data);

           console.log(data);


    });
});


function onLoad(myDat) {


    for (let i = 0; i < myDat.length; i++) {

        var date = myDat[i].date;
        var li = document.createElement("li");
        document.getElementById("list").append(li);
        li.innerText = date;

    }
}



