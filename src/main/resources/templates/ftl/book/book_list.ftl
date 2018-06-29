<#--
 所属模块：基础模块
 页面名称：图书列表页面
 创建时间：2018/05/29
 创建人员：lt
 -->

<#-- 主体部分 -->
<@override name="main">
    <#assign user = utils.getBook()>
<button id="freeMakerUtil">${user.bookName}${username}</button>
<button id="add" type="button" class="btn btn-sm btn-danger">添加</button>
<button id="del" type="button" class="btn btn-sm btn-danger">删除</button>
<button id="export" type="button" class="btn btn-sm btn-danger">导出</button>
<button id="assign" type="button" class="btn btn-sm btn-danger">shareData</button>
<table id="tableView">
    <thead>
    <tr>
        <th style="width: 40px;">No.</th>
        <th style="width: 150px">书名</th>
        <th>作者</th>
        <th>操作</th>
    </tr>
    </thead>
    <tbody id="view">
    </tbody>
</table>
<#--分页码-->
<div style="padding-top: 10px;" class="m-b-lg">
    <div id="page-info" class="pull-left"></div>
    <div id="page-bar" class="pull-right"></div>
</div>
</@override>

<#--脚本部分-->
<@override name="script">
<script id="bookTemp" type="text/html">
    {{# for(var i = 0, len = d.length; i < len; i++){ }}
    <tr>
        <td><input type="checkbox" class="i-checks" name="checkrow" value="{{ d[i].bookId }}"></td>
        <td>{{=i+1}}</td>
        <td>{{=d[i].bookName}}</td>
        <td>{{=d[i].bookAuthor}}</td>
        <td>
            <button type="button" class="btn-look" data-book-id="{{=d[i].bookId}}">查看
            </button>
        </td>
    </tr>
    {{# } }}
</script>
<script>
    $(function () {

        $('#freeMakerUtil').on('click', function () {
            layer.msg('Hello layer', {icon: 0});
        });
        //渲染页面
        var pageing = kirin.pageing(
                {
                    view: '#tableView',
                    tpl: '#bookTemp'
                }, function (data) {
                    return $.ajax({
                        type: 'GET',
                        url: '/book_pageInfo_List.json?pageNum=' + data.pageNum + "&pageSize=" + data.pageSize,
                        traditional: true,
                        dataType: 'json',
                        cache: false
                    });
                });

        /**
         * 加载列表渲染页面原生
         */
//        getDataList();
//        function getDataList() {
//            var data = {};
//            $.getJSON('/book2.html',
//                    data,
//                    function (res) { //从第1页开始请求。返回的json格式可以任意定义
//                        console.log(res);
//                        laypage({
//                            cont: 'pager', //容器。值支持id名、原生dom对象，jquery对象。【如该容器为】：<div id="page1"></div>
//                            pages: res.total, //通过后台拿到的总页数
//                            curr: 1, //初始化当前页
//                            jump: function (e) { //触发分页后的回调
//                                curPage = e.curr;
//                                $('#pageinfo').html('第' + e.curr + '页 &nbsp; 共' + e.pages + '页 &nbsp; &nbsp; 每页<select id="selpage"><option value="10">10</option><option value="20">20</option><option value="50">50</option><option value="100">100</option></select>条记录');
//                                $('#selpage').val(pageSize);
//                                $('#selpage').change(function () {
//                                    pageSize = $('#selpage').val();
//                                    getDataList();
//                                });
//                                refreshList();
//                            }
//                        });
//                    });
//        }

        /**
         * 刷新列表
         */
//        function refreshList() {
//
//            var data = {};
//            data.pageNum = curPage;
//            data.pageSize = pageSize;
//            $.getJSON('/book2.html', data, function (resdata) {
//                //totalPages = resdata.response.pages; //重新获取总页数，一般不用写
//                //渲染
//                var gettpl = document.getElementById('bookTemp').innerHTML;
//                laytpl(gettpl).render(resdata, function (html) {
//                    document.getElementById('view').innerHTML = html;
//                });
//            });
//        }

        //编辑
        $('#tableView').on('click', '.btn-look', function () {
            var bookId = $(this).data("bookId");
            //原生获取弹出框
//            layer.open({
//                type: 2,
//                title: '查看',
//                area: ['800px', '655px'],
//                fix: false,
//                maxmin: false,
//                scrollbar: false,
//                content: "/book/show.html?bookId=" + bookId
//            });
            kirin.popup.open({
                title: '编辑图书',
                width: 600,
                height: 350,
                maxmin: false,
                content: "/book/show.html?bookId=" + bookId
            }).done(function (data) {
                if (data == 'update') {
                    pageing.reload();
                }
            });

        });

        //保存
        $("#add").on("click", function () {
            kirin.popup.open({
                title: '添加图书',
                width: 600,
                height: 350,
                maxmin: false,
                content: "/book/show.html"
            }).done(function (data) {
                if (data == 'update') {
                    pageing.reload();
                }
            });
        });

        //批量删除
        $("#del").on("click", function () {
            var checkNum = 0;
            var ids = "";
            $('input[name="checkrow"]:checked').each(function () {
                if (checkNum > 0) {
                    ids += ",";
                }
                ids += parseInt($(this).val());
                checkNum++;
            });
            if (checkNum <= 0) {
                layer.alert("请选择需要删除的数据！");
                return;
            }

            layer.confirm('确认删除？', {
                btn: ['是', '否'], //按钮
                shade: false //不显示遮罩
            }, function () {
                $.ajax({
                    type: 'GET',
                    url: '/book/book_del.html',
                    traditional: true,
                    dataType: 'json',
                    cache: false,
                    data: {
                        ids: ids
                    },
                    success: function (data) {
                        console.log(data);
                        layer.alert('删除成功。', 0);
                        pageing.init();
                    }, error: function () {
                        layer.alert("删除失败", 0);
                    }
                });
            });
        })

        //导出
        $("#export").click(function () {
            // 调用导出Excel
            $.getJSON('/book_list.html', '0,5', function (data) {
                var subhead = (new Date().toLocaleString());
                laytpl($('#tplExport').html()).render(data, function (html) {
                    $(html).tableExport({type: 'excel', escape: 'false', fileName: '图书列表', subhead: subhead});
                });
            });
        });

        //shareData
        $('#assign').on('click', function () {
            var a = "你好，我是父页面的值";
            kirin.popup.open({
                title: '测试shareData',
                width: 600,
                height: 350,
                maxmin: false,
                shareData: a,
                content: '/book/shareData.html',
            }).done(function (data) {
                kirin.popup.success("update")
            });
        });

        /**
         * 刷新列表使用定时器
         */
        var curPage = 1;
        refresh();

        function refresh() {
            pageing.init(curPage);
            console.log("定时刷新..");
            setTimeout(function () {
                refresh()
            }, 5000);
        }
    })
</script>
<!-- 导出Excel模版 -->
<script id="tplExport" type="text/html">
    <table>
        <thead>
        <tr>
            <th style="width: 40px;">No.</th>
            <th style="width: 150px">书名</th>
            <th>作者</th>
        </tr>
        </thead>
        <tbody>
        {{# for(var i = 0, len = d.length; i < len; i++){ }}
        <tr>
            <td>{{=i+1}}</td>
            <td>{{=d[i].bookName}}</td>
            <td>{{=d[i].bookAuthor}}</td>
        </tr>
        {{# } }}
        </tbody>
    </table>
</script>
</@override>

<@extends name="/common/layout/book-base.ftl"/>



