# Android-XRouter
[[点击查看中文版]](https://www.jianshu.com/p/15d8cc6cf19b)<p>
This is a lightweight and simple routing framework that provides jump routing and method routing.<p>
[![API](https://img.shields.io/badge/API-14%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=14) [![License](https://img.shields.io/badge/license-Apache%202.0-blue.svg)](https://github.com/michaelxs/Android-XRouter/blob/master/LICENSE)<p>
## Feature
- Based on `Kotlin`
- Support free assembly of different modules to achieve differential compilation
- Provide page routing and interceptors
- Provide method routing including: synchronous and asynchronous
- Simple and convenient
- No reflection, high efficiency
- Super lightweight, less than 42 KB
## Setup
1.Add jcenter repository to root's build.gradle
```groovy
allprojects {
    repositories {
        ...
        jcenter()
    }
}
```
2.Apply kapt in app's build.gradle and module's build.gradle
```groovy
...
apply plugin: 'kotlin-kapt'
```
3.Add dependencies in app's build.gradle and module's build.gradle (please use the latest version)
```groovy
dependencies {
		...
    implementation 'com.xuyefeng:xrouter-core:1.1.3'
    kapt 'com.xuyefeng:xrouter-compiler:1.0.6'
}
```
4.Register routing in app's build.gradle and module's build.gradle
```groovy
kapt {
    arguments {
        arg("XRouterModule", project.getName())
    }
}
```
5.Register routing app in app's build.gradle
```groovy
kapt {
    arguments {
        arg("XRouterApp", project.getName() + ",modulea,moduleb")
    }
}
```
- The Routing App consists of three routing modules, app, module, and moduleb. Corresponding to the project name of the route module registered in step 4
- Here you can dynamically set XRouterApp based on a freely assembled module

6.Initialize XRouter
```kotlin
@RouterApp
class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        XRouter.init(this, BuildConfig.DEBUG)
    }
}
```
- It is recommended to initialize in Application and add annotations @RouterApp
- In debug mode, you can use XRouter as a tag to filter log information
![](https://upload-images.jianshu.io/upload_images/13146984-993f9bdae0d2821f.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
## Usage
#### Page routing
1.Annotation page (supports one page for multiple routing addresses)
```kotlin
@Router("www.baidu.com")
class MainActivity : AppCompatActivity()

@Router("www.baidu.com", "www.google.com")
class MainActivity : AppCompatActivity()
```
2.Jump page
```kotlin
// Common jump
XRouter.with(context).target("www.google.com").jump()

// Custom intentFlags
XRouter.with(context)
        .target("www.google.com")
        .intentFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
        .jump()

// Custom jump animation
XRouter.with(context)
        .target("www.google.com")
        .transition(android.R.anim.fade_in, android.R.anim.fade_out)
        .jump()

// Splicing parameters by url
XRouter.with(context)
        .target("www.google.com?name=blue&age=18")
        .jump()

// Passing parameters through the bundle
XRouter.with(context)
        .target("www.google.com")
        .data("name", "blue")
        .data("age", 18)
        .data(Bundle())
        .jump()

// StartActivityForResult
XRouter.with(context)
        .target("www.google.com")
        .requestCode(1001)
        .jump()

// Routing results, only concerned about success
XRouter.with(context)
        .target("www.google.com")
        .jump {
            // jump success to do sth
        }

// Routing results, only concerned with failure
XRouter.with(context)
        .target("www.google.com")
        .jump({
            // jump failure to do sth
        })

// Routing results, including failures and successes
XRouter.with(context)
        .target("www.google.com")
        .jump({
            // jump failure to do sth
        }, {
            // jump success to do sth
        })
```
3.Interceptor
```kotlin
@RouterInterceptor(priority = 8)
public class LoginInterceptor implements XRouterInterceptor {
    @Override
    public void onInit(@NotNull Context context) {
        // do something in application init
    }
    @Override
    public void onProcess(@NotNull XRouterInterceptorCallback callback) {
        // check login status
      	...
      	// check success
        callback.onContinue();
        // or check failure
        callback.onIntercept("check login error");
    }
}
```
- The parameter priority determines the priority of the interceptor. The default is 5. The higher the value, the higher the priority
- Multiple interceptors can be defined, executed sequentially according to priority
- The onInit method is called when the XRouter is initialized and can be used for interceptor initialization
- The onProcess method is called in the page route and can be used for page blocking. The classic scenario is that the login status needs to be detected during the page routing process. If the login status is invalid, the original route is terminated and changed to jump to the login interface
#### Method routing
1.Annotation method
```kotlin
@Router("toast")
fun toast(context: Context, routerParams: XRouterParams): XRouterResult {
    Toast.makeText(context, "toast from other module", Toast.LENGTH_SHORT).show()
    return XRouterResult.Builder().build()
}

@Router("getSum", async = true)
fun getSum(context: Context, routerParams: XRouterParams, callback: XRouterCallback?) {
  	// get object data
    val fragment = routerParams.obj as Fragment
  	// get data
    val a = routerParams.data.getInt("a")
    val b = routerParams.data.getInt("b")
  	...
    val sum = a + b
  	val result = XRouterResult.Builder().data("sum", sum).obj(fragment).build()
    callback?.onRouterSuccess(result)
  	// or
  	callback?.onRouterError(result)
}
```
- Annotated methods need to be global static methods
- The name of the annotation can be different from the method name, the name of the annotation is used to find the target method, and the method name is used to execute the method
- The default is synchronous routing. You can enable asynchronous routing through the annotation parameter async
- The parameters received by all synchronous routes are Context and XRouterParams. Please keep the same way
- The parameters received by all asynchronous routes are Context, XRouterParams, and XRouterCallback. Please keep the same syntax
- The parameters passed by the route can be obtained through XRouterParams. The parameters of the route result can be set by XRouterResult. Both pass the parameters by bundle, and obj passes the object data
2.Call method
```kotlin
// No-parameter routing
XRouter.with(context).target("toast").route()

// Passing parameters through the bundle
XRouter.with(context)
        .target("doSomething")
        .data("name", "blue")
        .data("age", 18)
        .route()

// Object passing
XRouter.with(context)
        .target("doSomething")
        .obj(Fragment())
        .route()

// Synchronous route acquisition result
val result = XRouter.with(context)
        .target("getSum")
        .data("a", 1)
        .data("b", 2)
        .route()

// Asynchronous routing gets results, only concerned about success
XRouter.with(context)
        .target("getSum")
        .data("a", 1)
        .data("b", 2)
        .route {
            // get params
            val sum = it.getData().getInt("sum")
            val fragment = it.getObj() as Fragment
            // route success to do sth
        }

// Asynchronous routing gets results, only cares about failure
XRouter.with(context)
        .target("getSum")
        .data("a", 1)
        .data("b", 2)
        .route ({
            // route failure to do sth
        })

// Asynchronous route gets the result, including failure and success
XRouter.with(context)
        .target("getSum")
        .data("a", 1)
        .data("b", 2)
        .route ({
            // route failure to do sth
        },{
            // get params
            val sum = it.getData().getInt("sum")
            val fragment = it.getObj() as Fragment
            // route success to do sth
        })
```
#### ProGuard
```
-keep class com.blue.xrouter.** {*;}
-keep interface com.blue.xrouter.** {*;}
```
## License
[Apache-2.0](https://opensource.org/licenses/Apache-2.0)
