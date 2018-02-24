### google 能力
* 下 keyword 精準度
* 相關 ecosystem 詞彙熟悉度
* 必要時簡體資源

### 需要的額外技術
* 學習 http 協定來溝通
* java 部署
* aws ec2
  * 操作 linux
* github 與 git
  * 操作 sourcetree
* 資料庫
  * Dbevar

### java part (stock-java)
* 伺服器 (Http Server)
* Java 邏輯撰寫 (Servlet)
* 取得網頁內容 (Http Client)
* 讀取檔案 (BufferedReader)
* 定時執行 (Scheduled)
* 連結資料庫 (JDBC)
* 資料庫 (SQLite)

### andriod part (stock-android)
* 使用者介面與互動邏輯
* 取得數據 (Http Client)
* 連結資料庫 (SQLite)
* APP上架 Google Play

### 資料輸入
* 主題: 取得股票期貨數據
  * (難) 爬蟲, 使用 Http Client 發送請求並分析網頁回應內容 (jdk, apache, spring) 
  * (中) API, 使用 Http Client 發送請求並分析資料回應內容 (jdk, apache, spring)  
  * (易) 檔案, 先手動取得資料再用 java 讀取 (java)

### 資料整理
* 將取得的資料整理之後物件化
```
Class Stock {
  Long id;
  String name;
  Float price;
  Float quantity;
  ...
}
Class Future {
  Long id;
  String name;
  Float price;
  Float quantity;
  ...
}
```

### 資料儲存
* 將整理好的物件使用jdbc儲存進資料庫(SQLite, MySQL, H2, HSQLDB)
* 在資料庫工具操作SQL語法

### 資料輸出
* 向後端伺服器發 Http 請求取得回應資料並呈現
* 使用者做操作互動