/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/*
 * établit la connection websocket avec le serveur.
 */
var serverUrl;
//var username;
var webSocket;
var serverId = location.hostname;


function onMessage(event) {
    var push = eval("(" + event.data + ")");
    display(push);
}

function onOpen(event) {
    console.log("yeah open session " + event.data);
    webSocket.send('ping');
}

function onError(event) {
    console.log('Yo we got error!' + event.data);
}

function display(push) {
    console.log('recu :' + push.expediteur + '->' + push.destinataire + ': ' + push.texte);
    alert("recu\nde :" + push.expediteur + "\nmessage :" + push.texte);
}

function webSocketStart() {
    //username = $('#userField').attr("value");
    console.log('will start web socket session');
    serverUrl = 'wss://' + serverId + ':8181/forsetiOPJ/ping';
    if (webSocket !== undefined && webSocket.readyState !== webSocket.CLOSED) {
        console.log("WebSocket is already opened.");
        return;
    }
    //serverUrl = $('#urlField').attr("value");
    webSocket = new WebSocket(serverUrl);

    webSocket.onerror = function (event) {
        onError(event);
    };

    webSocket.onopen = function (event) {
        onOpen(event);
    };

    webSocket.onmessage = function (event) {
        onMessage(event);
    };
}
