<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="screen-orientation" content="portrait"> <!--    //Android 禁止屏幕旋转-->
    <meta name="full-screen" content="yes"> <!--    //全屏显示-->
    <meta name="browsermode" content="application">
    <!--    //UC应用模式，使用了application这种应用模式后，页面讲默认全屏，禁止长按菜单，禁止收拾，标准排版，以及强制图片显示。-->
    <meta name="x5-orientation" content="portrait"> <!--    //QQ强制竖屏-->
    <meta name="x5-fullscreen" content="true"> <!--    //QQ强制全屏-->
    <meta name="x5-page-mode" content="app"> <!--    //QQ应用模式-->
    <meta name="viewport"
          content="width=device-width,initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no,minimal-ui">
    <!--    //WebApp全屏模式-->
    <meta name="format-detection" content="telphone=no, email=no"/> <!--    //忽略数字识别为电话号码和邮箱-->
    <style>

    </style>
    <link rel="icon" href="./images/favicon.ico">
    <title>秒杀抢购</title>
    <link rel="stylesheet" href="./js/layer_mobile/need/layer.css"/>
    <link rel="stylesheet" href="./css/index.css"/>
</head>

<body>
<div id="app">
    <header>
        <div class="swiper-container">
            <div class="swiper-wrapper">
                <div class="swiper-slide">
                    <img src="${sku.image}"
                         :onerror="defaultImage" width="100%"></img>
                </div>
                <div class="swiper-slide">
                    <img src="" :onerror="defaultImage" width="100%"></img>
                </div>
                <div class="swiper-slide">
                    <img src="${sku.image}"
                         :onerror="defaultImage" width="100%"></img>
                </div>
            </div>
            <!-- 如果需要分页器 -->
            <div class="swiper-pagination"></div>
        </div>
        <div class="detailsInfo">
            <div style="padding:8px 14px;position: relative;">
                <div class="cards"><span class="left"></span><span class="rit"></span></div>
                <img src="./images/detailLog.png" class="ico" width="45" alt="">
            </div>
            <div style="flex: 1; padding-top: 15px;padding-left: 30px;">
                <div><span style="font-size: 12px;">￥</span><span
                            style="font-size: 20px;font-weight: bold">${sku.seckillPrice}</span></div>
                <div style="text-decoration: line-through;font-size: 12px;"><span>￥</span><span>${sku.price}</span>
                </div>
            </div>
            <div style="padding-top: 10px;padding-right: 15px;font-size: 12px;">
                <div id="time-end" class="time">
                    <div style="margin-bottom: 6px">距离结束：</div>
                    <div class="tim"><span class="hours"></span> ：<span class="minutes"></span> ：<span
                                class="seconds"></span></div>
                </div>
                <div id="time-begin" class="time">
                    <div style="margin-bottom: 6px">距离开始：</div>
                    <div class="tim"><span class="hours"></span> ：<span class="minutes"></span> ：<span
                                class="seconds"></span></div>
                </div>
                <div id="time" class="time" style="line-height: 50px;">活动已结束</div>
            </div>
        </div>
    </header>
    <div class="skileDetail">
        <div class="itemCont infoCont">
            <div class="title">${sku.name}</div>
            <div class="desc">${sku.brandName} ${sku.category3Name}</div>
            <div class="item">
                <div class="info"><span>已选</span><span>${spec}</span></div>
                <img src="./images/right.png" width="44"
                     alt="">
            </div>
            <div class="item">
                <div class="info"><span>送至</span><span>北京市昌平区建材城西路9号, 传智播客前台</span></div>
                <img src="./images/right.png"
                     width="44" alt="">
            </div>
        </div>
        <div class="itemCont comment">
            <div class="title">
                <div class="tit">评论</div>
                <div class="rit">
                    <span>好评度 100%</span>
                    <img src="./images/right.png" width="44" alt="">
                </div>
            </div>
            <div class="cont">
                <div class="item">
                    <div class="top">
                        <div class="info">
                            <img src="./images/icon.png" width="25" height="25" alt=""/>
                            <div style="color:#666;margin-left: 10px; font-size: 14px;">张庆</div>
                        </div>
                        <div>
                            <img src="./images/shoucang.png" width="11" alt="">
                            <img src="./images/shoucang.png" width="11" alt="">
                            <img src="./images/shoucang.png" width="11" alt="">
                            <img src="./images/shoucang.png" width="11" alt="">
                            <img src="./images/shoucang.png" width="11" alt="">
                        </div>
                    </div>
                    <div class="des">
                        <div class="tit">质量不错，灵敏度高，结构巧妙，款式也好看</div>
                        <div style="margin: 10px 0">
                            <img src="./images/detail1.png" width="79" height="79" style="border-radius: 3px;"/>
                            <img src="./images/detail2.png" width="79" height="79" style="border-radius: 3px;"/>
                        </div>
                        <div class="font">
                            购买时间：2021-5-02 黑色，公开版，1件
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="content">
            <#list images! as img>
                <img src="${img}" width="100%" alt="">
            </#list>
        </div>
    </div>
    <footer>
        <div class="collection">
            <span><img src="./images/shoucang.png" width="21" alt=""></span>
            <span>收藏</span>
        </div>
        <button class="login-btn" value="立即购买">立即购买</button>
        <button class="login-btn-loading disabled" disabled="disabled">抢购中...</button>
        <button class="login-btn-disabled" value="立即购买" style="background: #92949C;" disabled="disabled">立即购买</button>
        <!-- <button v-if="isbegin!=1" value="立即购买" class="" style="background: #92949C;" disabled="disabled">立即购买</button> -->
    </footer>
</div>
<!-- 引入组件库 -->
<script type="text/javascript" src="./js/jquery-3.6.0.min.js"></script><!-- axios交互-->
<script type="text/javascript" src="./js/axios.js"></script><!-- axios交互-->
<script src="./js/layer_mobile/layer.js"></script>
<script>
    // 商品id
    var id = '${sku.id}';
    // 登录token
    var token = 'eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIwYTZmYzcwNC0yZGI3LTRjZDEtOTNkMi1iNTMyNmE3MzE5MDQiLCJpYXQiOjE2MjE5Mjg4NjAsImlzcyI6IjBhNmZjNzA0LTJkYjctNGNkMS05M2QyLWI1MzI2YTczMTkwNCIsInN1YiI6IjBhNmZjNzA0LTJkYjctNGNkMS05M2QyLWI1MzI2YTczMTkwNCIsInBob25lIjoiMTM2MTExMTIyMjIiLCJuYW1lIjpudWxsLCJ1c2VybmFtZSI6Iml0aGVpbWEiLCJleHAiOjE2MjIwMTUyNjB9.4Cgfp7NCnYlRknKVdpwUAiX0dmYS5rphw6-kazwksbo'
    // websocketUrl
    var websocketUrl = 'ws://39.103.145.66:28082/ws/itheima'
    // 下单url
    var addOrderUrl = '/lua/order/add?id=' + id
</script>
<script src="./js/index.js"></script>
</body>

</html>