/*!
 * 工具集
 * by lt
 * Copyright 2015 Youlove, Inc.
 */
(function ($) {

    "use strict";

    /**
     * 命名空间
     * @type {{}}
     */
    var kirin = {};

    /**
     * 业务参数
     * @type {{}}
     */
    var propertyMap = {};

    /**
     * 页面缓存
     * @type {{}}
     */
    top.window.cacheMap = top.window.cacheMap || {};

    /**
     * 获取指定名称的cookie的值
     * @param objName
     * @returns {*}
     */
    kirin.getCookie = function (objName) {
        var arrStr = document.cookie.split("; ");
        for (var i = 0; i < arrStr.length; i++) {
            var temp = arrStr[i].split("=");
            if (temp[0] == objName) return decodeURI(temp[1]);
        }
    };

    /**
     * 弹出窗口
     * @type {{open: Function, done: Function, notify: Function, fail: Function}}
     */
    kirin.popup = {
        open: function (options) {
            // 默认参数
            var defaults = {
                type: 2,
                title: false,
                shadeClose: false,
                shade: [0.5, '#000'],
                maxmin: true,
                scrollbar: false
            };

            // 覆盖插件默认参数
            var opts = $.extend({}, defaults, options);

            // 设置宽高，为空则用默认
            var width = (opts.width || 800), height = (opts.height || ($(window).height() - 50));
            opts.area = [width + 'px', height + 'px'];

            // 使用promise的方式
            var defer = $.Deferred();

            // 弹出layer
            var i = top.layer.open(opts);

            top.window.cacheMap['kirin.popup.open.' + i] = defer;

            //子页面的共享数据
            top.window.cacheMap['kirin.popup.data.' + i] = opts.shareData;

            return defer.promise();
        },
        shareData: function () {
            var i = top.layer.getFrameIndex(window.name);
            var key = 'kirin.popup.data.' + i;
            return top.window.cacheMap[key];
        },
        success: function (data) {
            var i = top.layer.getFrameIndex(window.name);
            var key = 'kirin.popup.open.' + i;
            var defer = top.window.cacheMap[key];
            defer.resolve(data);
            top.window.layer.close(i);
        },
        notify: function (data) {
            var i = top.layer.getFrameIndex(window.name);
            var key = 'kirin.popup.open.' + i;
            var defer = top.window.cacheMap[key];
            defer.notify(data);
        },
        close: function (data) {
            var i = top.layer.getFrameIndex(window.name);
            var key = 'kirin.popup.open.' + i;
            var defer = top.window.cacheMap[key];

            delete top.window.cacheMap[key];
            delete top.window.cacheMap['kirin.popup.data.' + i];

            defer.reject(data);
            top.window.layer.close(i);
        }

    };
    /**
     * JQuery的封装版本(判断返回参数http与meta的值如果不为200则提示错误信息)
     * serialize 参数用来序列化页面输入等。如需要以set方式把选择的加起来 可以在元素上加 data-set="true"
     * @param options
     * @returns {*}
     */
    kirin.ajax = function (options) {

        // 默认参数
        var defaults = {
            traditional: true,
            dataType: 'json',
            type: 'GET',
            serialize: null,
            debug: false,
            async: true
        };

        // 覆盖插件默认参数
        var opts = $.extend({}, defaults, options);

        var debug = opts.debug;

        //序列化
        if (opts.serialize) {
            var data = {},
                serialize = $(opts.serialize);

            // 直接提取
            $(serialize).filter('input[type!=checkbox],select,textarea').filter('input[type!=radio],select,textarea').each(function () {
                var that = $(this),
                    name = that.attr('name'),
                    nowValues = that.val();

                name || alert('序列化未设置元素[name]属性：' + that.html());

                if ($.isArray(nowValues)) {
                    $.each(nowValues, function (idx, value) {
                        nowValues[idx] = $.trim(value);
                    });
                } else {
                    nowValues = $.trim(nowValues);
                }

                if (!nowValues) {
                    return;
                }

                var oldValues = data[name];
                if (oldValues) {
                    data[name] = [].concat(oldValues, nowValues);
                } else {
                    data[name] = nowValues;
                }
            });

            //checkbox 如果加了data-set 则使值相加
            $(serialize).filter('input[type=checkbox]:checked').each(function () {
                var that = $(this),
                    name = that.attr('name'),
                    bitSet = (that.data('set') == true),
                    nowValues = $.trim(that.val());

                if (!nowValues) {
                    return;
                }

                name || alert('序列化未设置元素[name]属性：' + that.html());

                //Set位运算
                if (bitSet) {
                    isNaN(nowValues) && alert('序列化元素[value]属性不为数字：' + that.html());

                    var oldValues = data[name];
                    if (oldValues) {
                        data[name] = oldValues + parseInt(nowValues);
                    } else {
                        data[name] = parseInt(nowValues);
                    }
                } else {
                    var oldValues = data[name];
                    if (oldValues) {
                        data[name] = [].concat(oldValues, nowValues);
                    } else {
                        data[name] = nowValues;
                    }
                }
            });

            //radio
            $(serialize).filter('input[type=radio]:checked').each(function () {
                var that = $(this),
                    name = that.attr('name'),
                    nowValues = $.trim(that.val());

                if (!nowValues) {
                    return;
                }

                name || alert('序列化未设置元素[name]属性：' + that.html());

                var oldValues = data[name];
                if (oldValues) {
                    data[name] = [].concat(oldValues, nowValues);
                } else {
                    data[name] = nowValues;
                }
            });

            opts.data = $.extend({}, opts.data, data);

            debug && console.log(opts.data);
        }

        //服务端不支持 DELETE、PUT等操作
        var type = opts.type.toUpperCase();
        if ($.inArray(opts.type.toUpperCase(), ['GET', 'POST']) === -1) {
            opts.type = 'POST';
            opts.data = opts.data || {};
            opts.data[type] = true;
        }

        // 使用promise的方式
        var defer = $.Deferred();

        $.ajax(opts).done(function (data) {

            var meta = data.meta, response = data.response;
            console.log(meta);
            // 服务器成功返回
            if (meta.code === 200) {
                defer.resolve(response);
            } else {
                switch (meta.code) {
                    case 400:
                    case 401:
                    case 403:
                        layer.alert(meta.message, {icon: 0});
                        break;
                    case 500:
                        layer.alert(meta.message, {icon: 5});
                        break;
                    default:
                        layer.alert(meta.message, {icon: 5});
                }
                defer.reject(meta);
            }

        }).fail(function (data) {
            switch (data.status) {
                case 400:
                    layer.alert("请求参数存在错误！", {icon: 0});
                    break;
                case 401:
                    layer.alert("未登录或此帐号在别处被登录！", {icon: 0});
                    break;
                case 403:
                    layer.alert("没有操作权限！", {icon: 0});
                    break;
                case 404:
                    layer.alert("请求的地址不存在！", {icon: 5});
                    break;
                case 405:
                    layer.alert("请求提交方式错误！", {icon: 0});
                    break;
                case 500:
                default:
                    layer.alert("服务器开小差了，请稍后重试！", {icon: 5});
            }
            defer.reject(data);
        });

        return defer.promise();
    };

    /**
     * 根据模版绑定分页数据
     * @param options tpl:模版ID | view：html填充选择器 | pageSize：每页条数 | transform：服务端返回的数据是否需要转换一下 | complete：渲染完成后的回调
     * @param pageing 获取服务端分页数据Ajax（请直接返回：promise）
     */
    kirin.pageing = function (options, pageing) {

        /**
         * 构造器
         * @param opts 参数
         * @param pageing 获取服务端分页数据Ajax（请直接返回：promise）
         * @constructor
         */
        var PageingConstructor = function (options, pageing) {

            // 默认参数
            var defaults = {
                pageInfo: '#page-info',
                pageBar: '#page-bar',
                pageNum: 1,
                pageSize: 10,
                transform: null,
                noDataText: '暂无数据'
            };

            // 覆盖插件默认参数
            this._opts = $.extend({}, defaults, options);

            // 获取数据的回调
            this._pageing = pageing;

            //初始化
            this.jumpPageing();

        };

        /**
         * 给原型对象填充方法
         * @type {{init: Function, reload: Function, jumpPageing: Function}}
         */
        PageingConstructor.prototype = {

            /**
             * 初始化到第一页
             */
            init: function () {
                this._opts.pageNum = 1;
                this.jumpPageing();
            },

            /**
             * 跳转到指定页
             */
            go: function (pageNum) {
                this._opts.pageNum = pageNum;
                this.jumpPageing();
            },

            /**
             * 刷新当前页
             */
            reload: function () {
                this.jumpPageing();
            },

            /**
             * 获取数据并填充分页
             */
            jumpPageing: function () {

                var that = this, opts = this._opts, pageing = this._pageing;

                pageing(opts).done(function (data) {
                    //是否存在转换程序
                    opts.transform && (data = opts.transform(data));

                    if (!data.list || data.list.length === 0) {

                        //如果数据为空的情况下，暂无数据
                        var count = $('thead tr th', opts.view).length;
                        var html = kirin.format('<tr class="u-tr-no-data"><th colspan="{0}">{1}</th></tr>', count, opts.noDataText);
                        $('tbody', opts.view).html(html);
                    } else {

                        //渲染模版
                        laytpl($(opts.tpl).html()).render(data.list, function (html) {
                            $('tbody', opts.view).html(html);
                            opts.complete && opts.complete(data);
                        });
                    }

                    //分页
                    laypage({
                        cont: $(opts.pageBar),
                        pages: data.pages,
                        curr: data.pageNum,
                        jump: function (e, first) {
                            if (!first) {
                                opts.pageNum = e.curr;
                                opts.pageSize = data.pageSize;
                                that.jumpPageing(opts);
                            }
                            //渲染分页导航条
                            var pageInfo = $(opts.pageInfo);
                            pageInfo.html('第' + e.curr + '页 &nbsp; 共' + e.pages + '页 &nbsp; &nbsp; 每页<select class="page-size" style="width: 65px;"><option value="10">10</option><option value="20">20</option><option value="50">50</option><option value="100">100</option></select>条 &nbsp;共' + data.total + '条记录');
                            var pageSize = $('.page-size', pageInfo);
                            pageSize.val(data.pageSize);
                            pageSize.on('change', function () {
                                opts.pageNum = 1;
                                opts.pageSize = $('.page-size', pageInfo).val();
                                that.jumpPageing(opts);
                            });
                        }
                    });
                });
            }
        };

        return new PageingConstructor(options, pageing);
    };

    /**
     * 时间格式化
     * @param timestamp
     * @param format
     * @returns {*}
     */
    kirin.dataFormat = function (timestamp, format) {
        return moment(timestamp).format(format);
    };

    /**
     * 必填项验证(这个样式是可以跳动的)
     * @param element
     */
    kirin.checkError = function (select, message) {
        var element = $(select);
        var val = element.val();
        var message = message || element.attr('placeholder');
        element.removeClass('animated shake');
        if (val == '') {
            layer.tips(message, select);
            element.addClass('has-error animated shake');
            element.focus();
            return false;
        } else {
            element.removeClass('has-error animated shake');
        }
        return true;
    };

    /**
     * 创建选择框项
     */
    kirin.buildSelectOptions = function (select, data, defaultItem) {
        var value = $(select).data('value');
        $(select).empty();
        $(select).append("<option value=''>" + defaultItem + "</option>");
        $.each(data, function (key, value) {
            var selected = (key == value ? 'selected' : '');
            $(select).append("<option value='" + value.id + "' " + selected + " >" + value.name + "</option>");
        });
    };

    /**
     * 根据父节点获取业务参数列表json格式
     *   {id:id,parentId:parentId,name:name}
     * @param parentId
     */
    kirin.getPropJsonByParentId = function (parentId) {
        $.ajax({
            url: '/system/property-get-sub.json',
            data: {parentId: parentId},
            cache: false,
            async: false,
            type: "GET",
            dataType: 'json',
            success: function (data) {
                var meta = data.meta;
                if (meta && meta.code === 200) {
                    propertyMap = data.response;
                } else {
                    alert(meta.message);
                }
            }, error: function () {
                alert("获取业务参数失败");
            }
        });
        return propertyMap;
    };

    //公开
    window.kirin = kirin;

})(window.jQuery);