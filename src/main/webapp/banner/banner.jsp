<%@page pageEncoding="UTF-8" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="path" value="${pageContext.request.contextPath}" />
<script>
    $(function(){
        $("#bntable").jqGrid({
            url : "${path}/banner/selectAllBanners",
            editurl:"${path}/banner/edit",
            datatype : "json",
            rowNum : 5,
            autowidth:true,
            height:"auto",
            styleUI:"Bootstrap",
            rowList : [ 5, 10, 15 ],
            pager : '#bnpager',
            viewrecords : true,  //是否展示总条数
            colNames : [ 'Id', '名称', '图片', '描述', '状态','上传时间' ],
            colModel : [
                {name : 'id',width : 55,hidden:true},
                {name : 'title',editable:true,width : 90},
                {name : 'img_path',width : 100,editable:true,edittype:"file",align : "center",
                    formatter:function(cellvalue){
                        return "<img src='${path}/upload/photo/"+cellvalue+"' style='width:180px;height:80px' />";
                    },// 返回图片。
                },
                {name : 'description',editable:true,width : 80,align : "center"},
                {name : 'status',width : 80,align : "center",
                    formatter:function(cellvalue,option,row){
                        if(cellvalue==1){
                            return "<button class='btn btn-danger' onclick='change(\""+row.id+"\",\""+cellvalue+"\")'>不展示</button>";
                        }else {
                            return "<button class='btn btn-success' onclick='change(\""+row.id+"\",\""+cellvalue+"\")'>展示</button>";

                        }
                    }},
                {name : 'up_date',width : 80,align : "center"}
            ]

        });

        /*增删改查操作*/
        $("#bntable").jqGrid('navGrid', '#bnpager',
            {edit : true,add : true,del : true,edit: true,search:false,addtext:"添加",edittext:"编辑"},
            {
                closeAfterEdit:true,
                beforeShowForm:function(data){
                    data.find("#img_path"). attr("disabled",true)
                }
            },
            {
                afterSubmit:function(data){
                    alert(data);
                    //文件上传
                    $.ajaxFileUpload({
                        url:"${path}/banner/uploadBanner",
                        type:"post",
                        dataType:"JSON",
                        fileElementId:"img_path",
                        data:{id:data.responseText},
                        success:function(){
                            //刷新表单
                            $("#bntable").trigger("reloadGrid");
                        }
                    })
                    //一定要有返回值，返回什么都行
                    return "hello";
                },
                closeAfterAdd:true
            },
            {}
        );
    });
    function change(id,value) {
        if(value ==1){
            $.ajax({
                url:"${path}/banner/updateStatus",
                type:"post",
                dataType:"JSON",
                data:{"id":id,"status":"2"},
                success:function(data){
                  $("#bntable").trigger("reloadGrid");

                  $("#showMsg").html(data.message);
                  $("#show").show();
                  setTimeout(function(){
                      $("#show").hide();
                  }, 3000);
                }
            });
        }else {
            $.ajax({
                url:"${path}/banner/updateStatus",
                type:"post",
                dataType:"JSON",
                data:{"id":id,"status":"1"},
                success:function(data){
                    $("#bntable").trigger("reloadGrid");

                    $("#showMsg").html(data.message);
                    $("#show").show();
                    setTimeout(function(){
                        $("#show").hide();
                    }, 3000);
                }
            });
        }

    }
    $.ajax({
        url:"${path}/banner/delete",
        type:"post",
        dataType:"JSON",
        data:{"id":id},
        success:function(data) {
            //页面的刷新
            $("#bntable").trigger("reloadGrid");
        }
    })


    $.ajax({
        url:"${path}/banner/updateAll",
        type:"post",
        dataType:"JSON",
        data:{"id":id,"title":title,"description":description,"img_path":img_path},
        success:function(data){
            $("#bntable").trigger("reloadGrid");
            $("#showMsg").html(data.message);
            $("#show").show();
            setTimeout(function(){
                $("#show").hide();
            }, 3000);
        }
    });


</script>

<%--初始化面板--%>
<div class="panel panel-info">

    <div class="panel panel-heading">
        <h2>轮播图信息</h2>
    </div>
    <ul class="nav nav-tabs">
        <li class="active"><a href="#">轮播图信息</a></li>
    </ul>

    <div class="panel panel-body">
        <button type="button" class="btn btn-info" >添加轮播图</button>
        <button type="button" class="btn btn-success" >修改轮播图</button>
    </div>

    <div id="show" class="alert alert-warning alert-dismissible" role="alert"  style="width:200px;display: none">
        <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <strong id="showMsg"></strong>
    </div>
    <%--初始化表单--%>
    <table id="bntable" />

    <%--定义分页工具栏--%>
    <div id="bnpager"></div>

</div>



