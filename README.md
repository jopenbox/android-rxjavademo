# Android-RxJavaDemo

RxJava – Reactive Extensions for the JVM – a library for composing asynchronous and event-based programs using observable sequences for the Java VM.

RxJava可以浓缩为异步两个字，其核心的东西不外乎两个， Observables（被观察者） 和 Observable（观察者）。Observables可以发出一系列的 事件（例如网络请求、复杂计算、数据库操作、文件读取等），事件执行结束后交给Observable 的回调处理。

RxJava1 VS RxJava2

[http://www.jcodecraeer.com/a/anzhuokaifa/androidkaifa/2017/0118/7052.html](http://www.jcodecraeer.com/a/anzhuokaifa/androidkaifa/2017/0118/7052.html)

JSON Test Data

[https://raw.githubusercontent.com/smartbetter/android-rxjavademo/master/website/static/jsondata.json](https://raw.githubusercontent.com/smartbetter/android-rxjavademo/master/website/static/jsondata.json)

# RxJava 系列框架

| RxJava 框架 | 说明 | 开源地址 |
|:------------- |:------------- |:------------- |
| RxAndroid | 针对 Android 平台的扩展框架，方便 RxJava 用于 Android 开发，目前 RxAndroid 主要的功能是对 Android 主线程的调度 AndroidSchedulers.mainThread()。 | https://github.com/ReactiveX/RxAndroid |
| RxKotlin | Kotlin的RxJava绑定。 | https://github.com/ReactiveX/RxKotlin |
| DataBinding | DataBinding 是基于MVVM思想实现数据和UI绑定的的框架，支持双向绑定。 | DataBinding 是一个support库，最低支持到Android 2.1 |
| RxBinding | 基于 RxJava 的用于绑定 Android UI 控件的框架，它可以异步获取并处理控件的各类事件（例如点击事件、文字变化、选中状态） | https://github.com/JakeWharton/RxBinding |
| Retrofit| 网络请求框架，Retrofit 结合 RxJava 简化请求流程。 | https://github.com/square/retrofit |
| RxPermissions | 动态权限管理框架，动态权限内容可参考。 | https://github.com/tbruyelle/RxPermissions |
| RxLifecycle | 生命周期绑定，提供了基于 Activity 和 Fragment 生命周期事件的自动完成队列，用于避免不完整回调导致的内存泄漏。 | https://github.com/trello/RxLifecycle |
| RxBus | 是一种基于RxJava实现事件总线的一种思想。可以替代EventBus/Otto，因为他们都依赖于观察者模式。 | https://github.com/AndroidKnife/RxBus |

# Sample usage

A sample project which provides runnable code examples that demonstrate uses of the classes in this project is available in the rxjava1/ folder and rxjava2/ folder.
