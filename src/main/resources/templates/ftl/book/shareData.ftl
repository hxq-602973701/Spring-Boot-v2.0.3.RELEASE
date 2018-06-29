<#--
 所属模块：基础模块
 页面名称：图书列表页面
 创建时间：2018/05/29
 创建人员：lt
 -->

<#-- 主体部分 -->
<@override name="main">
<input type="text" value="" id="shareData">
</@override>


<#--脚本部分-->
<@override name="script">
<script>
    /**
     * 子页面获取父页面数据
     */
    $(function () {
        var parent = kirin.popup.shareData();
        $("#shareData").val(parent);
    })
</script>
</@override>

<@extends name="/common/layout/book-base.ftl"/>