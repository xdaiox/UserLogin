<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Logged out Page</title>
</head>
<body>
    <h1>登出成功</h1>
    <p>您已成功登出。</p>
    
    <button onclick="goToLoginPage()">返回登入頁面</button>
    
    <script>
        function goToLoginPage() {
            window.location.href = "/demo";
        }
    </script>
</body>
</html>
