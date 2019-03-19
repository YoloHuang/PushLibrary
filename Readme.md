## 一个集合所有主流厂商的推送框架


### 前言
由于项目需要，开发了一个整合了所有主流厂商的推送框架。本框架集合了小米，华为，OPPO，VIVO，极光等五家推送。其中我们可以在初始化时选择是否支持这四家厂商推送。先贴下项目路径：[PushLibrary](https://github.com/YoloHuang/PushLibrary)   

目前的推送逻辑是，如果初始化时都支持，会根据用户手机型号来自动判断使用哪家的推送。如果用户手机不属于当前四大厂商，则使用极光推送。考虑到四大厂商推送也可能不支持早期版本的手机，会有在初始化出错之后选择极光推送的容错处理。（后续可能会继续加入个推，友盟推送等平台推送，但我们日常开发中只需要添加一个就行了）

目前我们在使用各大推送平台时，在用户点击跳转这个选项上，都是设置的自定义行为。由服务器传入额外信息后APP根据信息自行处理，例如跳转到不同界面，显示不同信息。如果APP不做任何处理，点击通知不会产生任何效果，也不会自动打开APP。

鄙人不才，可能很多需求没考虑清楚。大家如果发现BUG或者有更好的解决方案，欢迎提issue。觉得有用或者出于鼓励，也可以star一下，在此拜谢。

### demo预览
|小米推送|vivo推送|极光推送|
|:-------:|:-------:|:-------:|
|![](https://github.com/YoloHuang/picture/blob/master/pushlibrary/demo_xiaomi.gif)&nbsp;&nbsp;&nbsp;|![](https://github.com/YoloHuang/picture/blob/master/pushlibrary/demo_vivo.gif)&nbsp;&nbsp;&nbsp;|![](https://github.com/YoloHuang/picture/blob/master/pushlibrary/demo_jiguang.gif)&nbsp;&nbsp;&nbsp;|
 

华为手机无法录屏，所以只有截图  
<img src="https://github.com/YoloHuang/picture/blob/master/pushlibrary/huawei_01.jpg" width=256 height=512 />

OPPO推送无法注册个人账户，暂时没有预览


### 快速集成

#### 1.添加依赖

在项目的`build.gradle`中，需要在`allprojects`最后添加`jitpack`


```
allprojects {
    repositories {
       ...
       
        maven { url 'https://jitpack.io' }
    }
}
```
在APP的`build.gradle`中添加如下依赖，版本号以最新为准

```
    implementation 'com.github.YoloHuang:PushLibrary:v1.0'
```

#### 2.在`AndroidManifest.xml`的`application`标签下添加厂商推送的ID和key等相关资源（如果只支持部分厂商，只需要添加支持的厂商信息就行）

```
        <!-- vivo 推送的ID 和 key -->

        <meta-data
            android:name="com.vivo.push.api_key"
            android:value="b4bcea82-9dbf-45aa-82d8-5555bc65257e"/>
        <meta-data
            android:name="com.vivo.push.app_id"
            android:value="10937"/>

        <!-- oppo 推送暂不支持个人开发者，所以无法在demo中演示 -->

        <meta-data
            android:name="OPPO_APP_KEY"
            android:value="" />

        <meta-data
            android:name="OPPO_APP_SECRET"
            android:value="" />

        <!-- 华为推送 ID-->

        <meta-data
            android:name="com.huawei.hms.client.appid"
            android:value="appid=100612743" />

        <!--//小米推送的AppKey ,APPID ****请务必在数值中间添加一个空格，否则会发生数值变化**** -->

        <meta-data
            android:name="XMPUSH_APPKEY"
            android:value="5851794   217581" />

        <meta-data
            android:name="XMPUSH_APPID"
            android:value="288230376   1517942581" />

        <!--//小米推送的AppKey ,APPID ****请务必在数值中间添加一个空格，否则会发生数值变化**** -->


        <!-- JPUSH_CHANNEL 是为了方便开发者统计APK分发渠道。-->
        <meta-data
            android:name="JPUSH_CHANNEL"
            android:value="default" />
        <!-- Required. AppKey copied from Portal -->
        <meta-data
            android:name="JPUSH_APPKEY"
            android:value="4bc48df35351d4ccf480561f" />
```

#### 3.初始化pushlibrary
初始化仅需要在application的onCreate中增加如下代码。在这其中，需要注意：设置debug需要在init之前，这样可以确保log打印完整。设置debug模式仅仅是针对log是否打印。debug默认为false，如果不需要打印log不设置即可。

```
    override fun onCreate() {
        super.onCreate()

        PushTargetManager.getInstance().setDebug(true)
        PushTargetManager.getInstance().init(this)
    }
```

#### 4.登录，设置别名，登出等接口，其中登录需要在activity中执行（是由于华为推送登录需要传入activity对象）。框架中，将登录和接受通知、登出和不接受通知放在一起处理，后面会拆分开来，满足不同需求。

```
/**
* 登录
*/
PushTargetManager.getInstance().loginIn(this)

/**
* 设置别名
*/
PushTargetManager.getInstance().setAlias(alias)

/**
* 登出
*/
PushTargetManager.getInstance().loginOut()

```


#### 5.在`AndroidManifest.xml`的`application`标签下注册静态广播。

```
        <receiver android:name="com.yolo.pushlibrary.TestPushReceiver">
            <intent-filter>
                <action android:name="com.kidosc.pushlibrary.ACTION_RECEIVE_NOTIFICATION"/>
                <action android:name="com.kidosc.pushlibrary.ACTION_RECEIVE_NOTIFICATION_CLICK"/>
                <action android:name="com.kidosc.pushlibrary.ACTION_RECEIVE_MESSAGE"/>
                <action android:name="com.kidosc.pushlibrary.ACTION_RECEIVE_TOKEN_SET"/>
                <action android:name="com.kidosc.pushlibrary.ACTION_RECEIVE_INIT_RESULT"/>
                <action android:name="com.kidosc.pushlibrary.ACTION_RECEIVE_LOGIN_OUT"/>
                <action android:name="com.kidosc.pushlibrary.ACTION_RECEIVE_SET_ALIAS"/>
                <category android:name="${applicationId}" />
            </intent-filter>
        </receiver>
```

新建一个receiver继承框架中的BasePushReceiver，重写onReceiveNotificationClick等方法，推送的相关处理都是在这里处理。

```
class TestPushReceiver : BasePushReceiver() {

    companion object {
        val ACTION_BROADCAST:String = "com.yolo.pushlibrary.ACTION_PUSH"
        val PUSH_LOG = "push_log"
    }




    override fun onReceiveNotification(context: Context, info: ReceiverInfo) {
        sendBroadCast(context,info)
    }

    override fun onReceiveNotificationClick(context: Context, info: ReceiverInfo) {
        sendBroadCast(context,info)
    }

    override fun onReceiveMessage(context: Context, info: ReceiverInfo) {
        sendBroadCast(context,info)
    }

    override fun onTokenSet(context: Context, info: ReceiverInfo) {
        sendBroadCast(context,info)
    }

    override fun onInitResult(context: Context, info: ReceiverInfo) {
        sendBroadCast(context,info)
    }

    override fun onLoginOut(context: Context, info: ReceiverInfo) {
        sendBroadCast(context,info)
    }

    override fun onSetAlias(context: Context, info: ReceiverInfo) {
        sendBroadCast(context,info)
    }



    fun sendBroadCast(context: Context,info: ReceiverInfo){
        val intent= Intent(ACTION_BROADCAST)
        intent.putExtra(PUSH_LOG,info)
        intent.`package` = context.packageName
        context.sendBroadcast(intent)

    }



}
```


这一步中，可能会存在Android8.0无法接收动态注册广播问题。但是我在8.0测试机上没发现，后续如果出现此问题，会想办法解决。

以上，就完成了整个框架的快速集成。

### 关于集成各家推送中遇到的问题

#### 极光推送，小米推送，OPPO推送
极光是一个成熟的推送平台，整个集成流程行云流水，文档也十分完整，基本没什么问题。小米推送也是如此。OPPO推送由于集成完后无法注册个人账号测试，所以效果未知。各位大佬如果可以弄到测试账号在下感激不尽。

#### vivo推送,华为推送
vivo推送中遇到的主要问题是，在服务器端，按照vivo接口文档，我们在clientCustomMap中放入我们所需要额外信息。同时设置skipType为3自定义。这样我们可以直接根据通知中的额外信息让APP自行处理，而不需要在服务器端设置。但是在skipContent这个参数上有一些疑问，不能为空，我们又不知道该传入什么。最后找到他们的官方FAE QQ交流，得知这个参数随便填一下就行，APP也不用做处理。   
<img src="https://github.com/YoloHuang/picture/blob/master/pushlibrary/vivo_2.jpg" width=256 height=300 />  

华为推送，讲道理，有点坑。集成起来最复杂不说，遇到的问题还贼多。华为推送在点击通知这块还是选择自定义由APP处理。但是我们需要在`AndroidManifest.xml`中添加intent-filter过滤器，并且在服务器端使用同种过滤器。而这点，在文档中藏的很深。[华为推送服务端文档中关于这个问题的描述](https://developer.huawei.com/consumer/cn/service/hms/catalog/huaweipush_agent.html?page=hmssdk_huaweipush_api_reference_agent_s2)


```
        <activity
            android:name=".rom.huawei.HuaweiLoadActivity"
            android:theme="@style/LoadTheme">
            <intent-filter>
                <action android:name="android.intent.action.VIEW" />
                <category android:name="android.intent.category.DEFAULT" />
                <data
                    android:host="com.kidosc.pushlibrary"
                    android:path="/notification"
                    android:scheme="pushlibrary" />
            </intent-filter>
        </activity>
```
还遇到的一个问题就是，签名之后注册华为推送失败。这个是我自己这边没有进行混淆处理导致的。当时我的混淆处理加在pushlibrary中，却一直不生效。后面将处理加在APP中，问题解决。目前推送框架已经解决了这个问题。


### 后续计划

写了个框架就相当于开了个坑，开坑不填那肯定是不行的。后续的计划有：  
1.增加标签功能  
2.增加友盟，个推平台    
3.优化代码，减小包的大小    
4.将各个推送分开做库，再使用统一库做统一处理。这样可以满足不同需求。

### 相关API介绍

<h6 align = "left">pushlibrary详细api</h6>

|方法名称|描述及解释|
|---------|:-------:|
|init(Application application )|初始化OnePush，建议在Application中onCreate()方法|
|init(Application application,boolean enableHWPush,boolean enableOppoPush,boolean enableVivoPush,boolean enableXIAOMIPush )|初始化OnePush，建议在Application中onCreate()方法,可以设置是否支持该厂商推送|
|loginIn(Activity activity)|注册消息推送,需要在activity中调用|
|loginOut()|取消注册消息推送|
|setAlias(String alias)|绑定别名|
|setDebug(boolean)|设置是否为debug模式|

</br>
<h6 align = "left">BasePushReceiver详细api</h6>

|方法名称|描述及解释|
|---------|:-------:|
|onReceiveNotification(Context context ,ReceiverInfo info)|转发通知|
|onReceiveMessage(Context context ,ReceiverInfo info)|转发透传消息|
|onTokenSet(Context context ,ReceiverInfo info)|转发华为token|
|onInitResult(Context context ,ReceiverInfo info)|转发初始化成功消息，info中包含推送注册平台信息|
|onSetAlias(Context context ,ReceiverInfo info)|转发设置别名成功的消息，info中包含别名|
|onLoginOut(Context context ,ReceiverInfo info)|转发登出成功消息|


### 感谢
在做这个框架时，参考了[OnePush](https://github.com/pengyuantao/OnePush)和 [Push](https://github.com/Luomingbear/Push)，在此感谢各位同行大佬。
