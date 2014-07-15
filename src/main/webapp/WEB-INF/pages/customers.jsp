<%@ page language="java" import="java.util.*" pageEncoding="utf-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <base href="<%=basePath%>">

    <title>客户信息</title>

    <link href="<%=basePath%>bootstrap/css/bootstrap.css" rel="stylesheet" type="text/css">
    <link href="<%=basePath%>css/customer.css" rel="stylesheet" type="text/css">
    <script type="text/javascript" src="<%=basePath%>js/jquery-1.8.0.min.js"></script>
    <script type="text/javascript" src="<%=basePath%>bootstrap/js/bootstrap.min.js"></script>
    <script type="text/javascript">
        var area = "";
        var status = "";
        var customerType = "";
        var strCurPage = "";
        var curPage = 1;
        var count = 0;
        var city = "";
        $(document).ready(function () {
            var userType = "${user.type}";
            if (userType != "1") {
                $("#repBut").hide();
                $("#pwd").hide();
            }
            area = "${area}";
        	status = "${status}";
        	strCurPage = "${curPage}";
        	customerType = "${customerType}";
        	city = "${city}";
            if(strCurPage != ""){
            	curPage = Number(strCurPage);
            }
            $("#status").val(status);
            $("#customerType").val(customerType);
            
            $("#place").children('option').each(function(){
    			if($(this).text()==city){
    				$(this).attr("selected",true);
    			}
    		});
    		
        	getArea(1);
    		
    		
            getData(curPage, 20, area, status,customerType);

            $("#select").click(function () {
                area = $("#area").val();
                status = $("#status").val();
                customerType = $("#customerType").val();
                if (area == null) {
                    area = "";
                }
                if (status == "0") {
                    status = "";
                }
                if (customerType == "0") {
                    customerType = "";
                }
                curPage = 1;
                getData(curPage, 20, area, status,customerType);
            });

            $(".as").click(function () {
                if ($(this).text() == "首页") {
                    curPage = 1;
                    getData(curPage, 20, area, status,customerType);
                } else if ($(this).text() == "上一页") {
                    if (curPage > 1) {
                        curPage = curPage - 1;
                    }
                    getData(curPage, 20, area, status,customerType);
                } else if ($(this).text() == "下一页") {
                    if (curPage < count) {
                        curPage = curPage + 1;
                    }
                    getData(curPage, 20, area, status,customerType);
                } else if ($(this).text() == "尾页") {
                    curPage = count;
                    getData(curPage, 20, area, status,customerType);
                }
            });
            
            $("#place").change(function(){ 
	        	getArea(0);
			});
        });

		function getArea(tag){
			var key = $("#place").children('option:selected').val();
        	city = $("#place").children('option:selected').text();
        	if(key==0){
        		$("#area option:not(:first)").remove();
        		return;
        	}
        	$.post("<%=basePath%>reports/area",{
        		pid : key
        	},function(result) {
        		$("#area option:not(:first)").remove();
        		$.each(result,function(n,value){
        			$("#area").append("<option>"+value.pname+"</option>");
        		});
        		if(tag==1){
        			$("#area").children('option').each(function(){
	    				if($(this).text()==area){
	    					$(this).attr("selected",true);
	    				}
    				});
        		}
        	});
		}

        function getData(page, pageSize, area, status,customerType) {
            $("#customerList tr:not(:first)").remove();
            city = $("#place").val()=="0"?"0":$("#place").children('option:selected').text();
            $.post("<%=basePath%>customer", {
                page: page,
                pageSize: pageSize,
                area: area,
                status: status,
                customerType : customerType,
                city : city
            }, function (result) {
                var trs = "";
                if (result.length == 0) {
                    $("#customerList").append("<tr><td style='text-align: center;color: red;' colspan='6'>无数据</td></tr>");
                    $("#pageDiv").hide();
                    return;
                } else {
                    $("#pageDiv").show();
                }
                $.each(result, function (n, value) {
                    if (value.cid != null) {
                        var btcontent = "<input onclick='questionnaire_update(" + value.cid + ")'  type='button' class='btn' value='修改' />";
                        if (value.status == "1") {
                            btcontent = "<input onclick='questionnaire(" + value.cid + ")'  type='button' class='btn' value='回访' />";
                        }
                        trs += "<tr><td style='vertical-align:middle;'>" + value.cname + "</td> <td style='vertical-align:middle;'>" + value.area + "</td> <td style='vertical-align:middle;'>"
                                + value.taxCode + "</td><td style='vertical-align:middle;'>" + value.customerType + "</td> <td style='vertical-align:middle;'>" + value.strStatus + "</td> <td style='vertical-align:middle;'>" + btcontent + "</td></tr>";
                    } else {
                        $("#page").text(value.page);
                        count = value.count;
                        $("#count").text(count);
                    }
                });

                $("#customerList").append(trs);
                if ($("#customerList").height() >= $("#main").height() - 100) {
                    $("#main").height($("#customerList").height() + 100);
                }
            });
        }

        function questionnaire(cid) {
            window.location.href = "questionnaire/" + cid +"?area="+area+"&status="+status+"&curPage="+curPage+"&customerType="+customerType+"&city="+city;
        }
        function questionnaire_update(cid) {
            window.location.href = "questionnaire/update/" + cid + "?area="+area+"&status="+status+"&curPage="+curPage+"&customerType="+customerType+"&city="+city;
        }
        function reports() {
            window.location.href = "reportsPage";
        }
        function set() {
            window.location.href = "question/set";
        }
        function loginOut() {
        	if(confirm("您确定退出登录吗？")){
        		window.location.href = "loginOut";
        	}
            
        }
        
        function changePwd(){
        	window.location.href = "manage";
        }


    </script>

</head>

<body>
<div id="top-bar">
    <div class="top">
        <div style="float: left">
            <span style="font-size: 37px;">调查问卷系统</span>
        </div>
        <div style="float: right">
            <span>回访人：</span><span style="color: green; ">${user.username }</span><br/>
            <input id="repBut" type="button" onclick="reports()" class="btn btn-primary" value="统计"/>
            <!-- <input type="button" onclick="set()" class="btn btn-primary" value="问题设置"/> -->
            <input id="pwd" type="button" onclick="changePwd()" class="btn btn-primary" value="设置"/>
            <input type="button" onclick="loginOut()" class="btn btn-primary" value="退出"/>
        </div>
        <div style="clear: both"></div>
    </div>
</div>
<div id="main">
    <div class="selectBar">
        <label>
				市：
			</label>
			<select class="form_customer" name="place" id="place" style="width: 100px;">
        	<option selected="selected" value="0">
                --全部--
            </option>
        	<c:forEach items="${placeMap}" var="map" varStatus="index">
        		<c:if test="${map.key==0 }">
        			<c:forEach items="${map.value}" var="place" varStatus="index">
        				<option value="${place.pid }">${place.pname}</option>
        			</c:forEach>
        		</c:if>
        	</c:forEach>
        </select>
       
		<label>
			区域：
		</label>
			<select class="form_customer" name="area" id="area" style="width: 100px;">
        	<option selected="selected" value="">
                --全部--
            </option>
        			
        	</select>
        <label>
            回访状态：
        </label>
        <select class="form-customer" name="status" id="status" style="width: 120px;">
            <option selected="selected" value="0">
                ----全部----
            </option>
            <option value="1">
                未回访
            </option>
            <option value="2">
                回访成功
            </option>
            <option value="3">
                无效回访
            </option>
            <option value="4">
                需再次回访
            </option>
        </select>&nbsp;&nbsp;
         <label>
            客户类型：
        </label>
        <select class="form-customer" name="customerType" id="customerType" style="width: 120px;">
            <option selected="selected" value="0">
                ----全部----
            </option>
            <option value="1">
                一般纳税人
            </option>
            <option value="2">
                小规模
            </option>
            <option value="3">
               个体户
            </option>
        </select>
        &nbsp;&nbsp;&nbsp;&nbsp;<input id="select" type="button" class="btn btn-primary" value="查询"/>
    </div>
    <div>
        <table style="width:900px;" id="customerList" class="table table-bordered">
            <caption class="tabtitle">客户列表</caption>
            <tr>
                <th>客户名</th>
                <th>区域</th>
                <th>税号</th>
                <th>客户类型</th>
                <th>回访状态</th>
                <th>操作</th>
            </tr>


        </table>
    </div>
    <div id="pageDiv"><a class="as" href="javascript:void(0);">首页</a>&nbsp;&nbsp;&nbsp;&nbsp;
        <a class="as" href="javascript:void(0);">上一页</a>&nbsp;&nbsp;&nbsp;&nbsp;
        <a class="as" href="javascript:void(0);">下一页</a>&nbsp;&nbsp;&nbsp;&nbsp;
        <a class="as" href="javascript:void(0);">尾页</a>&nbsp;&nbsp;&nbsp;&nbsp;
        当前第<span id="page" style="color: green; "></span>页&nbsp;&nbsp;&nbsp;&nbsp;
        共<span id="count" style="color: green; "></span>页
    </div>

</div>
</body>
</html>
