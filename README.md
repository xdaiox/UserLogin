# UserLoginDemo
需要先在MySQL上建立名為t2的資料庫
再啟動專案，專案啟動後會自動建置資料表account
後再至資料表account中insert帳號及密碼


用Spring Framework &amp; MyBatis &amp; MySQL &amp; RabbitMQ &amp; Redis 實作以下功能  
1. 實作一個登入API，當帳號密碼與DB的一致時，Access Token，不一致時，回傳錯誤訊息，
2. 將Access Token存進Redis，多重登入時使用後踢前的策略
3. 使用Pub/Sub方式，將用戶登入的結果記錄在DB或Log File
4. 實作一個個人資訊 REST API，當Access Token可用時，回傳用戶的顯示名稱，不可用時，回傳錯誤訊息
5. 實作一個登出 API，讓Access Token失效
