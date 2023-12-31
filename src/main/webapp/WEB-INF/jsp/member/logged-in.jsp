<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <title>Logged In Page</title>
</head>
<body>
    <c:if test="${not empty error}">
        <script>
            alert("${error}");
        </script>
    </c:if>

    <!-- 登入後的內容 -->
    <h1>Welcome, ${account}!</h1>
    <p>Your Access Token: ${accessToken}</p>
    
    <!-- 登出按鈕 -->
    <button id="logoutButton">Logout</button>

    <script>
        document.getElementById("logoutButton").addEventListener("click", function() {
            // 使用 JavaScript 跳轉到登出路徑
            window.location.href = '/demo/logout?account=' + '${account}';
        });
    </script>
</body>
</html>
