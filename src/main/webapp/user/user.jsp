<%@page pageEncoding="UTF-8" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>
<script>

    $(function () {
        $("#ustable").jqGrid({
            url: "${path}/user/showAllUsers",
            datatype: "json",
            rowNum: 5,
            autowidth: true,
            height: "auto",
            styleUI: "Bootstrap",
            rowList: [5, 10, 15],
            pager: '#uspager',
            viewrecords: true,  //是否展示总条数
            colNames: ['Id', '头像', '名字', '昵称', '密码', '性别', '状态', '手机号', '注册时间'],
            colModel: [
                {name: 'id', width: 55},
                {
                    name: 'pic_img', width: 100, editable: true, edittype: "file", align: "center",
                    formatter: function (cellvalue) {
                        return "<img src='${path}/upload/photo/" + cellvalue + "' style='width:180px;height:80px' />";
                    },// 返回图片。
                },
                {name: 'name', width: 90},
                {name: 'ahama', width: 90},
                {name: 'password', width: 80, align: "center"},
                {name: 'sex', width: 90},
                {
                    name: 'status', width: 80, align: "center",
                    formatter: function (cellvalue, option, row) {
                        if (cellvalue == 1) {
                            return "<button class='btn btn-danger' onclick='change(\"" + row.id + "\",\"" + cellvalue + "\")'>冻结</button>";
                        } else {
                            return "<button class='btn btn-success' onclick='change(\"" + row.id + "\",\"" + cellvalue + "\")'>正常</button>";

                        }
                    }
                },
                {name: 'phone', width: 80, align: "center"},
                {name: 'reg_date', width: 80, align: "center"}
            ]

        })

        /*增删改查操作*/
        $("#ustable").jqGrid('navGrid', '#uspager',

            {
                closeAfterEdit: true,
                beforeShowForm: function (data) {
                    data.find("#pic_img").attr("disabled", false)
                }
            },
            {
                afterSubmit: function (data) {
                    alert(data);
                    //文件上传
                    $.ajaxFileUpload({
                        url: "${path}/user/uploadUser",
                        type: "post",

                        dataType: "JSON",

                        fileElementId: "pic_img",
                        data: {id: data.responseText},
                        success: function () {
                            //刷新表单
                            $("#ustable").trigger("reloadGrid");
                        }
                    })
                    //一定要有返回值，返回什么都行
                    return "hello";
                },
                closeAfterAdd: true
            },
            {}
        );
    });

    function change(id, value) {
        if (value == 1) {
            $.ajax({
                url: "${path}/user/updateStatus",
                type: "post",
                dataType: "JSON",
                data: {"id": id, "status": "2"},
                success: function (data) {
                    $("#ustable").trigger("reloadGrid");

                    $("#showMsg").html(data.message);
                    $("#show").show();
                    setTimeout(function () {
                        $("#show").hide();
                    }, 3000);
                }
            });
        } else {
            $.ajax({
                url: "${path}/user/updateStatus",
                type: "post",
                dataType: "JSON",
                data: {"id": id, "status": "1"},
                success: function (data) {
                    $("#ustable").trigger("reloadGrid");

                    $("#showMsg").html(data.message);
                    $("#show").show();
                    setTimeout(function () {
                        $("#show").hide();
                    }, 3000);
                }
            });
        }

    }

    function user() {

        $.ajax({
            url: "${path}/user/export",
            type: "post",
            dataType: "JSON",
            success: function (data) {
                $("#showMsg").html(data.message);
                $("#show").show();
                setTimeout(function () {
                    $("#show").hide();
                }, 3000);
            }
        });
    }

</script>

<%--初始化面板--%>
<div class="panel panel-info">

    <div class="panel panel-heading">
        <h2>用户管理</h2>
    </div>
    <ul class="nav nav-tabs">
        <li class="active"><a href="#">用户信息</a></li>
    </ul>

    <div class="panel panel-body">
        <button type="button" onclick="user()" class="btn btn-info">用户信息导出</button>
    </div>

    <div id="show" class="alert alert-warning alert-dismissible" role="alert" style="width:200px;display: none">
        <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span
                aria-hidden="true">&times;</span></button>
        <strong id="showMsg"></strong>
    </div>
    <%--初始化表单--%>
    <table id="ustable"/>

    <%--定义分页工具栏--%>
    <div id="uspager"></div>

</div>
