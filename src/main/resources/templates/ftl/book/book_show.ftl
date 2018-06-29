<#--
 所属模块：基础模块
 页面名称：图书列表页面
 创建时间：2018/05/29
 创建人员：lt
 -->

<#-- 主体部分 -->
<@override name="main">
<form>
    <input type="hidden" id="bookId" name="bookId" value="${book.bookId!}">
    <table>
        <tr>
            <th class="required-input">书名</th>
            <td>
                <input placeholder="请输入书名" type="text" class="" id="bookName" name="bookName" value="${book.bookName!}">
            </td>
        </tr>
        <tr>
            <th>作者</th>
            <td>
                <input placeholder="请输入作者" type="text" class="" id="bookAuthor" name="bookAuthor"
                       value="${book.bookAuthor!}">
            </td>
        </tr>
        <a type="button" id="save" class="">保存</a>
    </table>
</form>
</@override>

<#--脚本部分-->
<@override name="script">
<script>
    $(function () {

        var thisindex = parent.layer.getFrameIndex(window.name); //获取窗口索引
        /**
         * 保存
         */
        $("#save").on("click", function () {
            var bookBase = {};
            $("input[name],select[name]").each(function () {
                if ($(this).val() != "") {
                    bookBase[$(this).attr("name")] = $(this).val();
                }
            });
            var bookBaseStr = JSON.stringify(bookBase);
            console.log(bookBaseStr);
            layer.confirm("确定要保存么？", {
                btn: ['确定', '取消']
            }, function (index) {
                $.ajax({
                    url: '/book/saveOrUpdate.html',
                    type: "post",
                    dataType: "json",
                    async: true,
                    timeout: 5000,
                    data: {bookBaseStr: bookBaseStr},
                    success: function (data, textStatus, jqXHR) {
                        layer.alert("保存成功!", function (index2) {
                            kirin.popup.success("update")
                            // layer.close(index2);
                            //  refreshParent();
                            //  parent.layer.close(thisindex);
                        })
                    },
                    error: function (XMLHttpRequest, textStatus, errorThrown, data) {
                        console.log(XMLHttpRequest.responseText);
                        console.log(XMLHttpRequest.status);
                        console.log(XMLHttpRequest.readyState);
                        console.log(textStatus); // parser error;
                        console.log(data)
                    }
                })
            });
        });

        //刷新父页面
        function refreshParent() {
            parent.location.reload();
            window.close();
        }
    })
</script>
</@override>

<@extends name="/common/layout/book-base.ftl"/>
