<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Login Page</title>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
</head>
<body>
    <h1>Login Page</h1>
    <form id="loginForm">
        <label for="username">Username:</label>
        <input type="text" id="username" name="username" required><br><br>
        <label for="password">Password:</label>
        <input type="password" id="password" name="password" required><br><br>
        <input type="submit" value="Login">
    </form>

    <script>
        $(document).ready(function() {
            $('#loginForm').submit(function(event) {
                event.preventDefault(); // 阻止表單提交

                var username = $('#username').val();
                var password = $('#password').val();
                // console.log('username:', username);
                // console.log('password:', password);

                var data = {
                    account: username,
                    password: password
                };

                $.ajax({
                    url: '/demo/login',
                    type: 'POST',
                    contentType: 'application/json',
                    data: JSON.stringify(data),
                    success: function(response) {
                        // 登入成功，處理回應
                        var accessToken = response;
                        alert('登入成功！');
                    },
                    error: function(xhr, status, error) {
                        //登入失敗，處理錯誤
                        var errorMessage = xhr.responseText;
                        alert('登入失敗！錯誤訊息：' + errorMessage);
                    }
                });
            });
        });
    </script>
</body>
</html>
