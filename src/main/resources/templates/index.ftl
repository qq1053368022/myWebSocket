<!Doctype html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>
    <link type="text/css" rel="stylesheet" href="/webjars/bootstrap/css/bootstrap.min.css">
</head>
<body>
    <#if username ??>

    <div class="panel panel-primary">

        <div class="panel-heading text-center">
            您好！
            <label id="user" class="text text-center ">
                ${username}
            </label>

        </div>
        <div class="panel-body">
            <div class="row">
                <div class="navbar-form" >
                    <div class="navbar-header">
                        <span class="navbar-brand">
                            请输入消息内容：
                        </span>
                    </div>
                    <div class="form-group "  >
                        <textarea id="msg"  name="message"  class="form-control input-lg"  placeholder="请输入..."></textarea>
                    </div>
                    <button id="send" class="btn btn-lg btn-primary " onclick="sendMyMsg()">私&nbsp;&nbsp;&nbsp;&nbsp;聊</button>
                    <button id="send" class="btn btn-lg btn-primary " onclick="sendAllMsg()">群&nbsp;&nbsp;&nbsp;&nbsp;发</button>
                    <div id="warning" class="text-warning navbar-default">

                    </div>
                </div>


            </div>

            <div class="row">
                <div class="col-md-6">
                    <div class="panel panel-info">
                        <div class="panel-heading">
                            实时在线用户列表
                        </div>
                        <table id="conversation" class="table table-striped">
                            <thead class="text-center">
                            <tr>
                                <td>  用户名</td>
                                <td>更新时间</td>
                                <td>  选择 </td>
                            </tr>

                            </thead>
                            <tbody id='online' class="text-center">
                                <tr>
                                    <td>&nbsp;</td>
                                    <td>&nbsp;</td>
                                    <td>&nbsp;</td>
                                </tr>
                            </tbody>
                        </table>
                    </div>

                </div>

                <div class="col-md-6">

                    <div class="panel panel-info">
                        <div class="panel-heading">
                                聊天记录
                        </div>
                        <table id="conversation" class="table table-striped">
                            <thead class="text-center">
                            <tr>
                                <td>消息时间</td>
                                <td> 用户名 </td>
                                <td> 内容 </td>
                            </tr>
                            </thead>

                            <tbody id='record' class="text-center">
                                <tr>
                                    <td> &nbsp;</td>
                                    <td> &nbsp;</td>
                                    <td> &nbsp;</td>
                                </tr>

                            </tbody>
                        </table>

                    </div>

                </div>
            </div>
        </div>

    </div>
    <script src="/webjars/jquery/jquery.min.js"></script>
    <script src="/webjars/sockjs-client/sockjs.min.js"></script>
    <script src="/webjars/stomp-websocket/stomp.min.js"></script>
    <script src="/app.js"></script>
    <#else>
    <div id="main-content" class="container">
        <form action="" class="form-inline" method="post" >
            <div class="form-group">
                <input type="text" name="username" class="form-control" placeholder="用户名">
                <input type="submit" class="form-control" value="登陆">
            </div>
        </form>
    </div>
    </#if>

</body>
</html>