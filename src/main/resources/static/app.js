
var stompClient = null;
// var ws = null;
var username = null;

$(function () {
    username = $("#user").text().trim();
    connect();


})

function getUserOnlineBefore() {

    $("input[name='check']").each(function (data) {
        users.push($(this).attr("title"));
    });
    if (user.length==0||user[0] == "" || user[0] == null) {
        stompClient.send("/app/public/onlineuser",{},"") ;
    }

}


function getMessage(data,type) {
    var body = JSON.parse(data.body);

    if (type==0) {
        $("#record").append("<tr class='text-warning'><td>" + new Date(body.time).toLocaleTimeString() + "</td><td>" + body.from + "</td><td>" + body.content+ "</td></tr>");
    }else if (type == 1) {
        $("#record").append("<tr class='text-primary'><td>" + new Date(body.time).toLocaleTimeString() + "</td><td>" + body.from + "</td><td>" + body.content + "</td></tr>");
    }


}

function sendAllMsg() {
    var msg = $("#msg").val();
    $("#warning").html("");
    if (checkMsg(msg)) stompClient.send("/app/group/chat",{},JSON.stringify({"content":msg,"from":username})) ;

    
}

function checkMsg(msg) {
    if (msg != null&&msg!="") {
       return true;

    }else {
        $("#warning").html('系统提示:请填写要发送的消息');
        return false;
    }
}

function sendMyMsg() {
    var users = [];
    $("#warning").html("");
    $("input[name='check']:checked").each(function (data) {
       users.push($(this).attr("title"));
    });
    console.log(users);
    if (users.length > 0) {
        var msg = $("#msg").val();
        if (checkMsg(msg)) {
            for (var user in users){
                stompClient.send("/app/private/chat",{},JSON.stringify({"content":msg,"to":users[user],"from":username}))
            }
        }




    }else {
        console.log("未选中发送人");
        $("#warning").html( '系统提示:请选择要发送的用户');
    }


}

function connect() {
    // if ('WebSocket' in window) {
    //     ws = new WebSocket("ws://localhost:8080/endpoint-websocket/");
    // }else if ('MozWebSocket' in window) {
    //     ws = new MozWebSocket("ws://localhost:8080/endpoint-websocket/");
    // } else {
    //     alert("该浏览器不支持websocket");
    // }
    var socket = new SockJS("/endpoint-websocket");
    stompClient = Stomp.over(socket);


    stompClient.connect({}, function (frame) {



        //订阅群聊消息
        stompClient.subscribe("/topic/chat", function (result) {
            var type=0;
            getMessage(result,type);
        });


        //订阅在线用户消息
        stompClient.subscribe("/topic/onlineuser", function (result) {
            var body = JSON.parse(result.body);
            var maps = JSON.parse(body.content);
            var users = [];
            $("input[name='check']:checked").each(function (data) {
                users.push($(this).attr("title"));
            });
            $("#online").html("");
            for (var key in maps) {
                var enable='';
                if ($.inArray(maps[key].username, users)!=-1) enable="checked='checked'";
                $("#online").append("<tr><td>"+maps[key].username+"</td><td>"+new Date(body.time).toLocaleTimeString()+"</td><td><input type='checkbox' name='check'  title='"+maps[key].username+"' "+enable+" /></td></tr>");
            }

        });
        //订阅当前账号消息
        stompClient.subscribe("/chat/single/" + username, function (result) {
            var type=1;
            getMessage(result,type);
        });

        getUserOnlineBefore();
    });


}