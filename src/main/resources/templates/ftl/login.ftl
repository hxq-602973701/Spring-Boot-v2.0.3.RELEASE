<#--
 所属模块：基础模块
 页面名称：图书列表页面
 创建时间：2018/05/29
 创建人员：lt
 -->

<#-- 主体部分 -->
<@override name="main">
<div class="main">
    <div class="title">
        <span>密码登录</span>
    </div>

    <div class="title-msg">
        <span>请输入登录账户和密码</span>
    </div>

    <form class="login-form" method="post" novalidate>
        <!--输入框-->
        <div class="input-content">
            <!--autoFocus-->
            <div>
                <input type="text" autocomplete="off"
                       placeholder="用户名" name="username" required/>
            </div>

            <div style="margin-top: 16px">
                <input type="password"
                       autocomplete="off" placeholder="登录密码" name="password" required maxlength="32"/>
            </div>
        </div>

        <!--登入按钮-->
        <div style="text-align: center">
            <button type="submit" class="enter-btn">登录</button>
        </div>

        <div class="foor">
            <div class="left"><span>忘记密码 ?</span></div>

            <div class="right"><span>注册账户</span></div>
        </div>
    </form>

</div>
</@override>

<#--脚本部分-->
<@override name="script">
<script>
    $(function () {

        //保存
        $(".enter-btn").on("click", function () {
            var username = $("input[name='username']").val();
            var password = $("input[name='password']").val();
            var rememberMe = false;
            $.ajax({
                type: "post",
                url: "login",
                data: {
                    "username": username,
                    "rememberMe": rememberMe,
                    "password": password

                },
                success: function (r) {
                    console.log(r);
                    if (r.code == 0) {
                        console.log("哈");
                    } else {
                        console.log("llll");
                    }
                }
            });
        });


    })
</script>
</@override>
<@extends name="/common/layout/book-base.ftl"/>



