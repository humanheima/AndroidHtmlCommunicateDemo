# 原生调用H5的方式有哪些？

在 Android 开发中，原生代码（Native）调用 H5（WebView 中运行的 HTML5 页面）的方法主要包括以下几种方式：

### 1. **通过 `evaluateJavascript` 调用 JavaScript 方法**
- **原理**：使用 `WebView` 的 `evaluateJavascript` 方法直接执行 JavaScript 代码，调用 H5 页面中定义的函数。
- **实现步骤**：
    1. 在 Android 端调用：
       ```java
       webView.evaluateJavascript("javascript:someJsFunction('Hello from Native')", new ValueCallback<String>() {
           @Override
           public void onReceiveValue(String result) {
               // 获取 H5 返回的结果（如果有）
               Log.d("WebView", "JS Result: " + result);
           }
       });
       ```
    2. 在 H5 页面定义 JavaScript 函数：
       ```html
       <script>
           function someJsFunction(message) {
               console.log("Received: " + message);
               return "Response from H5"; // 可选返回值
           }
       </script>
       ```
- **适用版本**：Android 4.4（API 19）及以上。
- **优点**：
    - 执行效率高，支持异步获取返回值。
    - 代码简洁，适合大多数场景。
- **缺点**：
    - 低版本 Android 不支持（4.4 以下需用 `loadUrl` 替代）。
    - 需要确保 H5 页面已加载完成。

---

### 2. **通过 `loadUrl` 执行 JavaScript 代码**
- **原理**：通过 `WebView` 的 `loadUrl` 方法加载 `javascript:` 协议的 URL，执行 H5 页面中的 JavaScript 代码。
- **实现步骤**：
    1. 在 Android 端调用：
       ```java
       webView.loadUrl("javascript:someJsFunction('Hello from Native')");
       ```
    2. 在 H5 页面定义 JavaScript 函数：
       ```html
       <script>
           function someJsFunction(message) {
               console.log("Received: " + message);
           }
       </script>
       ```
- **优点**：
    - 兼容性好，适用于所有 Android 版本。
    - 实现简单。
- **缺点**：
    - 无法直接获取 JavaScript 执行结果。
    - 性能略低于 `evaluateJavascript`。
    - 需要确保页面加载完成，否则可能失效。

---

### 3. **通过 `postMessage` 发送消息**
- **原理**：利用 HTML5 的 `postMessage` API，原生通过注入 JavaScript 代码向 H5 页面发送消息，H5 通过监听 `message` 事件处理。
- **实现步骤**：
    1. 在 Android 端发送消息：
       ```java
       webView.evaluateJavascript("javascript:window.postMessage('Hello from Native', '*')", null);
       ```
    2. 在 H5 页面监听消息：
       ```html
       <script>
           window.addEventListener('message', function(event) {
               console.log("Received: " + event.data);
               // 处理消息
           });
       </script>
       ```
- **优点**：
    - 支持复杂数据传递（如 JSON 对象）。
    - 灵活，适合双向通信场景。
- **缺点**：
    - 需要 H5 页面提前实现监听逻辑。
    - 需手动管理消息格式。

---

### 4. **通过 WebViewJavascriptBridge 调用**
- **原理**：使用第三方库（如 WebViewJavascriptBridge）提供的桥接 API，原生端通过桥接对象调用 H5 注册的函数，支持回调。
- **实现步骤**：
    1. 在 Android 端初始化桥接并调用：
       ```java
       WebViewJavascriptBridge bridge = WebViewJavascriptBridge.init(webView);
       bridge.callHandler("jsHandler", "Hello from Native", new WVJBResponseCallback() {
           @Override
           public void onResult(Object data) {
               Log.d("Bridge", "JS Response: " + data);
           }
       });
       ```
    2. 在 H5 页面注册处理函数：
       ```html
       <script src="WebViewJavascriptBridge.js"></script>
       <script>
           setupWebViewJavascriptBridge(function(bridge) {
               bridge.registerHandler('jsHandler', function(data, responseCallback) {
                   console.log("Received: ", data);
                   responseCallback("Response from H5");
               });
           });
       </script>
       ```
- **优点**：
    - 支持双向通信和回调，功能强大。
    - 跨平台兼容（iOS 和 Android 通用）。
- **缺点**：
    - 需要引入额外库，增加项目复杂度。
    - 集成和调试稍复杂。

---


### 总结与建议
- **简单调用**：优先使用 `evaluateJavascript`（Android 4.4+）或 `loadUrl`（低版本兼容）。
- **复杂数据交互**：使用 `postMessage` 或 WebViewJavascriptBridge，灵活且支持回调。


# H5 调用原生

在 Android 开发中，H5（WebView 中运行的 HTML5 页面）调用原生（Native）代码的方式主要有以下几种：

### 1. **通过 JavaScriptInterface**
- **原理**：Android 通过 `WebView` 的 `addJavascriptInterface` 方法注入一个 Java/Kotlin 对象，H5 页面通过 JavaScript 调用该对象的公开方法。
- **实现步骤**：
    1. 在 Android 端定义接口类：
       ```java
       public class JsInterface {
           @JavascriptInterface
           public void callNative(String data) {
               // 处理 H5 传递的数据
               Log.d("JsInterface", "Received: " + data);
           }
       }
       ```
    2. 注入到 WebView：
       ```java
       webView.addJavascriptInterface(new JsInterface(), "Android");
       ```
    3. 在 H5 页面调用：
       ```html
       <button onclick="callAndroid()">Call Native</button>
       <script>
           function callAndroid() {
               window.Android.callNative("Hello from H5");
           }
       </script>
       ```
- **优点**：简单直接，适合轻量级交互。
- **缺点**：
    - 安全性风险：需在 Android 4.2+ 使用 `@JavascriptInterface` 注解，防止恶意 JavaScript 攻击。
    - 不支持直接返回值，复杂交互需额外封装。
- **适用场景**：简单数据传递或触发原生操作。

---

### 2. **通过 URL 拦截**
- **原理**：H5 通过加载自定义协议的 URL（如 `myapp://`），Android 重写 `WebViewClient` 的 `shouldOverrideUrlLoading` 方法拦截并解析 URL，执行原生逻辑。
- **实现步骤**：
    1. 在 Android 端设置 WebViewClient：
       ```java
       webView.setWebViewClient(new WebViewClient() {
           @Override
           public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
               String url = request.getUrl().toString();
               if (url.startsWith("myapp://")) {
                   // 解析 URL 参数，执行原生逻辑
                   Log.d("WebView", "Intercepted: " + url);
                   return true; // 拦截，不让 WebView 加载
               }
               return super.shouldOverrideUrlLoading(view, request);
           }
       });
       ```
    2. 在 H5 页面触发：
       ```html
       <a href="myapp://action?data=hello">Call Native</a>
       <!-- 或者通过 JavaScript 触发 -->
       <script>
           window.location.href = "myapp://action?data=hello";
       </script>
       ```
- **优点**：实现简单，兼容性好。
- **缺点**：
    - URL 长度有限，不适合传递复杂数据。
    - 不支持直接返回值。
- **适用场景**：简单的单向调用，如触发原生功能。

---

### 3. **通过 `window.prompt` 或 `alert` 拦截**
- **原理**：H5 使用 `window.prompt`、`alert` 或 `confirm` 方法发送数据，Android 通过重写 `WebChromeClient` 的 `onJsPrompt`、`onJsAlert` 或 `onJsConfirm` 方法拦截并处理。
- **实现步骤**：
    1. 在 Android 端设置 WebChromeClient：
       ```java
       webView.setWebChromeClient(new WebChromeClient() {
           @Override
           public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {
               // 处理 message
               Log.d("WebView", "Prompt: " + message);
               result.confirm("Native response"); // 返回结果给 H5
               return true; // 拦截处理
           }
       });
       ```
    2. 在 H5 页面调用：
       ```html
       <button onclick="callNative()">Call Native</button>
       <script>
           function callNative() {
               var result = window.prompt("myapp://data=hello");
               console.log("Native response: " + result);
           }
       </script>
       ```
- **优点**：
    - 支持同步返回值，适合需要立即响应的场景。
    - 兼容性较好。
- **缺点**：
    - 可能触发浏览器对话框，影响用户体验。
    - 不适合频繁调用或复杂交互。
- **适用场景**：需要简单返回值的小规模交互。

---

### 4. **通过 WebViewJavascriptBridge**
- **原理**：使用第三方库（如 WebViewJavascriptBridge）提供的桥接 API，H5 通过桥接对象调用原生注册的处理函数，支持回调。
- **实现步骤**：
    1. 在 Android 端初始化桥接并注册处理函数：
       ```java
       WebViewJavascriptBridge bridge = WebViewJavascriptBridge.init(webView);
       bridge.registerHandler("callNative", new WVJBHandler() {
           @Override
           public void handle(Object data, WVJBResponseCallback callback) {
               Log.d("Bridge", "Received: " + data);
               callback.onResult("Native response");
           }
       });
       ```
    2. 在 H5 页面初始化并调用：
       ```html
       <script src="WebViewJavascriptBridge.js"></script>
       <button onclick="callNative()">Call Native</button>
       <script>
           function setupWebViewJavascriptBridge(callback) {
               if (window.WebViewJavascriptBridge) { return callback(WebViewJavascriptBridge); }
               document.addEventListener('WebViewJavascriptBridgeReady', function() {
                   callback(WebViewJavascriptBridge);
               }, false);
           }
           setupWebViewJavascriptBridge(function(bridge) {
               bridge.callHandler('callNative', {'data': 'Hello'}, function(response) {
                   console.log('Native response: ', response);
               });
           });
       </script>
       ```
- **优点**：
    - 支持双向通信和回调，功能强大。
    - 跨平台兼容（iOS 和 Android 通用）。
    - 适合复杂交互。
- **缺点**：
    - 需要引入额外库，增加项目复杂度。
    - 集成和调试稍复杂。
- **适用场景**：需要复杂交互或回调的场景。

---


### 总结与建议
- **简单调用**：优先使用 `JavaScriptInterface` 或 `URL 拦截`，实现成本低，适合单向触发。
- **需要返回值**：使用 `window.prompt` 或 WebViewJavascriptBridge，`prompt` 适合简单场景，Bridge 适合复杂交互。
- **复杂场景**：推荐 WebViewJavascriptBridge，功能全面且稳定。
- **高实时性**：考虑 WebSocket 或 HTTP，但需权衡网络依赖。
- **安全性注意**：
    - 对 H5 传入的数据进行严格校验，防止注入攻击。
    - 使用 `JavaScriptInterface` 时，确保 Android 4.2+ 并添加 `@JavascriptInterface` 注解。
    - 自定义协议（如 `myapp://`）需规范格式，避免误解析。

根据你的项目需求（如复杂度、返回值需求、兼容性），选择合适的方式。如果需要某方式的详细代码示例或优化建议，请告诉我！