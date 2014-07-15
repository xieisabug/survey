<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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

		<title>问卷调查结果统计</title>
		<link href="<%=basePath%>bootstrap/css/bootstrap.css" rel="stylesheet"
			type="text/css">
		<link rel="stylesheet" href="<%=basePath%>js/skins/lhgcalendar.css" type="text/css"/>
		<link rel="stylesheet" href="<%=basePath%>css/reports.css" type="text/css">
		<link rel="stylesheet" href="css/bootstrap-ie7.css" type="text/css"></link>
		<script type="text/javascript" src="<%=basePath%>js/jquery-1.8.0.min.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/lhgcalendar.min.js"></script>
		<script type="text/javascript" src="js/respond.js"></script>
		<script type="text/javascript">
	var area = "";
	var startDate = "";
	var endDate = "";
	var status = "";
	var uid = "";
	var tag = 1;
	var curPage = 1;
    var count = 0;
    var customerType = "";
    var city = "";
    var answer = "0";
    var sAnswer = "";
    var allCount = 0;
    var flag = false;
	$(document).ready(function() {
		var userType = "${user.type}";
		if(userType != "1"){
			document.write("非法访问！");
			return;
		}
		selectForCustomer(curPage, 20);

		$("#select").click(function() {
			curPage = 1;
    		count = 0;
			if (tag == 1) {
				selectForCustomer(curPage, 20);
			} else if (tag == 2) {
				selectForAnswerReport();
			} else if (tag == 3) {
				selectForStatusReport();
			}
		});

		$("#customerReport").click(function() {
			tag = 1;
			$("#area").val("");
			$("#startDate").val("");
			$("#endDate").val("");
			$("#status").val("0");
			$("#user").val("0");
			$("#customerType").val("0");
			$("#place").val("0");
            $("answer").val("0");
			$("#area option:not(:first)").remove();
			$("#tabDiv").slideDown();
			$("#answerReportDiv").slideUp();
			$("#statusReportDiv").slideUp();
			$("#status").removeAttr("disabled");
			$("#answerTr").show();
			$("#answer").val("0");
			answer = "0";
			$("#answer").attr("disabled",true);
			selectForCustomer(curPage, 20);

		});
		$("#answerReport").click(function() {
			tag = 2;
			$("#area").val("");
			$("#startDate").val("");
			$("#endDate").val("");
			$("#user").val("0");
			$("#customerType").val("0");
            $("answer").val("0");
			selectForAnswerReport();
			$("#tabDiv").slideUp();
			$("#answerReportDiv").slideDown();
			$("#statusReportDiv").slideUp();
			status = "2";
			$("#status").val("2");
			$("#place").val("0");
			$("#area option:not(:first)").remove();
			$("#status").attr("disabled","disabled");
			$("#answerTr").hide();
		});
		$("#statusReport").click(function() {
			tag = 3;
			$("#area").val("");
			$("#startDate").val("");
			$("#endDate").val("");
			$("#status").val("0");
			$("#user").val("0");
			$("#place").val("0");
            $("answer").val("0");
			$("#area option:not(:first)").remove();
			$("#customerType").val("0");
			selectForStatusReport();
			$("#tabDiv").slideUp();
			$("#answerReportDiv").slideUp();
			$("#statusReportDiv").slideDown();
			$("#status").removeAttr("disabled");
			$("#answerTr").hide();
		});

		$("#startDate").calendar();
		$("#endDate").calendar();

		$("#statusReportPrint").click(function() {
			exportExcel("listForStatusReport");
		});
		$("#answerReportPrint").click(function() {
			exportExcel("listForAnswerReport");
		});
		$("#customerReportPrint").click(function() {
			flag = true;
			alert("导出时根据数据量的大小，可能需要一段时间，请耐心等待");
			selectForCustomer(1,allCount);
		});
		
		$("#back").click(function(){
			window.location.href="customers";
		});
		
		$("input[name='export']").attr("title","如果无法导出，请设置Internet选项，将自定义安全级别中的“对未标记为可安全执行脚本的ActiveX控件初始化并执行脚本”选择“提示”项");
	
	
		$(".as").click(function () {
                if ($(this).text() == "首页") {
                    curPage = 1;
                    selectForCustomer(curPage, 20);
                } else if ($(this).text() == "上一页") {
                    if (curPage > 1) {
                        curPage = curPage - 1;
                    }
                    selectForCustomer(curPage, 20);
                } else if ($(this).text() == "下一页") {
                    if (curPage < count) {
                        curPage = curPage + 1;
                    }
                    selectForCustomer(curPage, 20);
                } else if ($(this).text() == "尾页") {
                    curPage = count;
                    selectForCustomer(curPage, 20);
                }
            });
            
        $("#status").change(function(){
        	var key = $(this).children('option:selected').val();
        	if(key=="2"){
        		$("#answer").attr("disabled",false);
        	}else {
        		$("#answer").val("0");
        		$("#answer").attr("disabled",true);
        		sAnswer = "";
        	}
        	
        });
            
        $("#place").change(function(){ 
        	var key = $(this).children('option:selected').val();
        	//$("select[name='area']").hide();
        	//$("#area"+key).show();
        	city = $(this).children('option:selected').text();
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
        	});
		});
            
	});
	
	/*function print(arg1,arg2,callBack){
		selectForCustomer(arg1,arg2);
		callBack();
	}*/

	function selectInit() {
		area = $("#area").val();
		startDate = $("#startDate").val();
		endDate = $("#endDate").val();
		status = $("#status").val();
		uid = $("#user").val();
		city = $("#place").val()=="0"?"0":$("#place").children('option:selected').text();
        answer = $("#answer").val();
        sAnswer = $("#answer").children('option:selected').text();
		customerType = $("#customerType").val();
		if (area == null) {
			area = "";
		}
		if (startDate == null) {
			startDate = "";
		}
		if (endDate == null) {
			endDate = "";
		}
	}

	function selectForCustomer(page, pageSize) {
		$("#list tr:not(:first)").remove();
		selectInit();
		$.post("<%=basePath%>reports",{
			page: page,
            pageSize: pageSize,
			area : area,
			startDate : startDate,
			endDate : endDate,
			status : status,
			customerType : customerType,
			uid : uid,
			city : city,
            answer :answer
		},function(result) {
		$("font[name='count']").text(result.length-1);
		$.each(result,function(n, value) {
		person = value.person==null?"":value.person;
		phone = value.phone==null?"":value.phone;
		finalPhone = value.finalPhone==null?"":value.finalPhone;
		if (value.updatePerson != null ) {
			person = value.updatePerson;
		}
		if (value.updatePhone != null) {
			phone = value.updatePhone;
		}
		var i = 0;
		if (value.cid != null) {
		if (value.status != "2") {
			$("#list").append(
							"<tr><td>"+ value.cname+ "</td><td>"+ value.area+ "</td><td>"+ value.taxCode+ "</td><td>"+ value.customerType+ "</td><td>"+ person+ "</td><td>"
									+ finalPhone+ "</td><td>"+ value.surveyDate+ "</td><td colspan='3' style='color:red;text-align: center;'>"+value.strStatus+"</td></tr>");
		} else {
			
			$.each(value.answersList,function(m,list) {
			var temp = "";
			if(sAnswer!=""){
					if(list.answer==sAnswer){
						temp = "<font style='color:red;'>"+list.answer+"</font>";
					}else{
						temp = list.answer;
					}
				}
			if (i == 0) {
				
				$("#list").append("<tr><td style='vertical-align: middle;' rowspan='"+value.answersList.length+"'>"+ value.cname+ "</td>"
					+ "<td style='vertical-align: middle;' rowspan='"+value.answersList.length+"'>"+ value.area+ "</td>"
					+ "<td style='vertical-align: middle;' rowspan='"+value.answersList.length+"'>"
					+ value.taxCode+ "</td><td style='vertical-align: middle;' rowspan='"+value.answersList.length+"'>"+ value.customerType
					+ "</td><td style='vertical-align: middle;' rowspan='"+value.answersList.length+"'>"+ person
					+ "</td><td style='vertical-align: middle;' rowspan='"+value.answersList.length+"'>"+ finalPhone+ "</td>"
					+ "<td style='vertical-align: middle;' rowspan='"+value.answersList.length+"'>"+ value.surveyDate+ "</td>"
					+ "<td>"+ list.title+ "</td><td>"+ temp+ "</td><td>"+ list.feedback+ "</td></tr>");

			} else {
				$("#list").append("<tr><td>"+ list.title+ "</td><td>"+ temp+ "</td><td>"+ list.feedback+ "</td></tr>");
			}
			i++;
		});
	}}
	else {
		$("#page").text(value.page);
        count = value.count;
        allCount = value.allCount;
        $("#count").text(count);
	}
	});
	if(flag){
		exportExcel("list");
		flag = false;
	}
	});
	//var t=setTimeout(setHeight,100);
	
}
	/*function setHeight(){
		if($("#list").height()>= $("#main").height()-550){
		$("#main").height($("#list").height()+450);
	}
	}*/

	function selectForAnswerReport() {
		$("#listForAnswerReport tr:not(:first)").remove();
		selectInit();
		$.post("<%=basePath%>reports/answer", {
			area : area,
			startDate : startDate,
			endDate : endDate,
			status : "2",
			customerType : customerType,
			uid : uid,
			city : city
		}, function(result) {
			$("font[name='count']").text(result.customerCount);
			$.each(result.answerReportDto, function(m, arrays) {
				$("#listForAnswerReport").append(
						"<tr><td>" + arrays.title + "</td>" + "<td>"
								+ arrays.level1 + "</td>" + "<td>"
								+ arrays.level2 + "</td>" + "<td>"
								+ arrays.level3 + "</td>" + "<td>"
								+ arrays.level4 + "</td>" + "<td>"
								+ arrays.level5 + "</td></tr>");
			});
		});
	}

	function selectForStatusReport() {
		$("#listForStatusReport tr:not(:first)").remove();
		selectInit();
		$.post("<%=basePath%>reports/status", {
			area : area,
			startDate : startDate,
			endDate : endDate,
			status : status,
			customerType : customerType,
			uid : uid,
			city : city
		}, function(result) {
			$("font[name='count']").text(result.customerCount);
			$.each(result.list,function(m,statusReportDto){
			$("#listForStatusReport").append(
					"<tr><td>" + statusReportDto.area + "</td>"
							+ "<td>" + statusReportDto.status1 + "</td>"
							+ "<td>" + statusReportDto.status2 + "</td>"
							+ "<td>" + statusReportDto.status3 + "</td>"
							+ "<td>" + statusReportDto.status4 + "</td>"
							+ "<td>" + statusReportDto.sum
							+ "</td></tr>");
			});
		});
	}

	function exportExcel(tableid) {//读取表格中每个单元到EXCEL中  
		var curTbl = document.getElementById(tableid);
		var oXL = new ActiveXObject("Excel.Application");
		//创建AX对象excel 
		var oWB = oXL.Workbooks.Add();
		//获取workbook对象 
		var oSheet = oWB.ActiveSheet;
		//激活当前sheet 
		var Lenr = curTbl.rows.length;
		var cellsCount = curTbl.rows(0).cells.length;
		oSheet.Range(oSheet.cells(1,1),oSheet.cells(1,cellsCount)).select();
      	oXL.Selection.HorizontalAlignment = 3;
        oSheet.cells(1,1).value = curTbl.caption.innerText;
		oSheet.Cells(1,1).Font.Bold = true;//加粗
        oXL.Selection.MergeCells = true;
        var count = 0;
        $("font[name='count']").each(function(){count = $(this).text();});
        oSheet.cells(2,1).value = "客户量："+count;
        
        oSheet.cells(3,1).value = "市：";
        oSheet.cells(3,2).value = $("#place").children('option:selected').text().indexOf("全部")<0?$("#place").children('option:selected').text():"全部";
        oSheet.cells(3,5).value = "区域：";
        oSheet.cells(3,6).value = $("#area").children('option:selected').text().indexOf("全部")<0?$("#area").children('option:selected').text():"全部";
        oSheet.cells(4,1).value = "开始时间：";
        oSheet.cells(4,2).value = $("#startDate").val();
        oSheet.cells(4,5).value = "截止时间：";
        oSheet.cells(4,6).value = $("#endDate").val();
        oSheet.cells(5,1).value = "回访状态：";
        oSheet.cells(5,2).value = $("#status").children('option:selected').text().indexOf("全部")<0?$("#status").children('option:selected').text():"全部";
        oSheet.cells(5,5).value = "回访人：";
        oSheet.cells(5,6).value = $("#user").children('option:selected').text().indexOf("全部")<0?$("#user").children('option:selected').text():"全部";
        oSheet.cells(6,1).value = "客户类型：";
        oSheet.cells(6,2).value = $("#customerType").children('option:selected').text().indexOf("全部")<0?$("#customerType").children('option:selected').text():"全部";
        if(($("#answer").val()!= undefined) &&($("#answer").val()!= "0") && ($("#answer").val()!= "")){
        	oSheet.cells(6,5).value = "结果筛选：";
        	oSheet.cells(6,6).value = $("#answer").children('option:selected').text().indexOf("全部")<0?$("#answer").children('option:selected').text():"全部";
        }
        
		//取得表格行数 
		for (i = 0; i < Lenr; i++) {
			var Lenc = curTbl.rows(i).cells.length;
			//取得每行的列数 
			var rowspan = $(curTbl.rows(i).cells(1)).attr("rowspan");
			if(Lenc == 3){
				for (k = 0; k < Lenc; k++) {
					//列计算
					oSheet.Cells(i + 7, k + 8).value = curTbl.rows(i).cells(k).innerText;
				}
				continue;
			}
			for (j = 0; j < Lenc; j++) {
				if($(curTbl.rows(i).cells(j)).attr("rowspan")!=null){
					var r = i + 7 + parseInt(rowspan) -1;
					oSheet.Range(oSheet.cells(i+7,j+1),oSheet.cells(r,j+1)).select();
					//oXL.Selection.HorizontalAlignment = 3;
        			oXL.Selection.MergeCells = true;
				}
				oSheet.Cells(i + 7, j + 1).value = curTbl.rows(i).cells(j).innerText;
				//赋值 
				if(i==0){
					oSheet.Cells(i+7,j+1).Font.Bold = true;//加粗
				}
			}
			
		}
		
		if(oSheet.Cells(Lenr+6,1).value=="小计"){
			oSheet.Cells(Lenr+6,1).Font.Bold = true;
		}
		oXL.Visible = true;//设置excel可见属性 
	}
</script>
	</head>

	<body>
		<div id="main">
		<div style="width: 100%;text-align: right;margin-bottom: 10px;">
		<img id="back" title="返回" style="width: 50px;height: 50px;cursor:pointer" src="images/back.png" /></div>
		<div class="viewBar">
			<a id="customerReport" href="javascript:void(0);">客户答题信息</a>&nbsp;&nbsp;&nbsp;&nbsp;
			<a id="answerReport" href="javascript:void(0);">回访情况统计</a>&nbsp;&nbsp;&nbsp;&nbsp;
			<a id="statusReport" href="javascript:void(0);">回访状态统计</a>
		</div>
		<div class="selectBar">
		<table id="thisTab">
		<tr><td colspan="2">
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
			<!-- <input class="form-custome" type="text" name="area" id="area" /> -->
			
			</td>
			<td class="toRight"><label>
				回访日期：
			</label></td>
			<td><input class="form-custome" class="runcode" type="text" name="startDate" id="startDate" />
			<label>
				&nbsp;至&nbsp;
			</label>
			<input class="form-custome" class="runcode" type="text" name="endDate" id="endDate" /></td></tr>
			
			<tr><td>
           <label> 回访状态：</label></td>
        <td>
        <select  class="form-customer" name="status" id="status" style="width: 184px;">
            <option selected="selected" value="0">
                ----全部----
            </option>
            <option value="1">未回访 </option>
            <option value="2">回访成功</option>
            <option value="3">无效回访</option>
            <option value="4">需再次回访</option>
        </select></td><td class="toRight">
        <label>回访人：</label></td><td>
        <select class="form_customer" name="user" id="user" style="width: 120px;">
        	<option selected="selected" value="0">
                ----全部----
            </option>
        	<c:forEach items="${userList}" var="user" varStatus="index">
        		<option value="${user.uid }">${user.username}</option>
        	</c:forEach>
        </select>
        &nbsp;&nbsp;&nbsp;&nbsp;
         <label>
            客户类型：
        </label>
        <select class="form-customer" name="customerType" id="customerType" style="width: 180px;">
            <option selected="selected" value="0">
                ----全部----
            </option>
            <option value="1">一般纳税人</option>
            <option value="2">小规模</option>
            <option value="3">个体户</option>
        </select>
        </td>
			
			<td class="toRight"><input class="btn btn-primary" type="button" id="select" value="查询" /></td></tr>
            <tr id="answerTr"><td><label>结果筛选：</label></td><td colspan="4">
                <select disabled="disabled"  class="form-customer" name="answer" id="answer" style="width: 184px;">
                    <option selected="selected" value="0">
                        ----全部----
                    </option>
                    <option value="1">非常满意</option>
                    <option value="2">满意</option>
                    <option value="3">基本满意</option>
                    <option value="4">不满意</option>
                    <option value="5">不了解</option>
                </select>

            <font style="font-size: 12px;color: red;">注：该条件只能在回访状态为“回访成功”时有效</font></td></tr>
			</table>
		</div>
		<div id="tabDiv">
			<table id="list" class="table table-bordered">
				<caption class="tabtitle">客户问卷信息详细列表&nbsp;</caption>
				<caption style="text-align: right;font-size: 12px;">客户量：<font name="count" color="green"></font>&nbsp;&nbsp;</caption>
				<tr>
					<th>
						客户名
					</th>
					<th>
						区域
					</th>
					<th>
						税号
					</th>
					<th>
						客户类型
					</th>
					<th>
						被回访人
					</th>
					<th>
						回访电话
					</th>
					<th>
						回访时间
					</th>
					<th>
						问卷题目
					</th>
					<th>
						问卷结果
					</th>
					<th>
						意见反馈
					</th>
				</tr>

			</table>
			<div id="pageDiv"><a class="as" href="javascript:void(0);">首页</a>&nbsp;&nbsp;&nbsp;&nbsp;
        <a class="as" href="javascript:void(0);">上一页</a>&nbsp;&nbsp;&nbsp;&nbsp;
        <a class="as" href="javascript:void(0);">下一页</a>&nbsp;&nbsp;&nbsp;&nbsp;
        <a class="as" href="javascript:void(0);">尾页</a>&nbsp;&nbsp;&nbsp;&nbsp;
        当前第<span id="page" style="color: green; "></span>页&nbsp;&nbsp;&nbsp;&nbsp;
        共<span id="count" style="color: green; "></span>页
    </div>
			<div class="output">
				<input name="export" type="button" class="btn btn-primary" id="customerReportPrint" value="导出" />
			</div>
		</div>
		<div id="answerReportDiv" style="display: none;">
			
			<table id="listForAnswerReport" class="table table-bordered">
				<caption class="tabtitle">
					回访情况统计表
				</caption>
				<caption style="text-align: right;font-size: 12px;">客户量：<font name="count" color="green"></font></caption>
				<tr>
					<th>
						问卷题目
					</th>
					<th>
						非常满意
					</th>
					<th>
						满意
					</th>
					<th>
						基本满意
					</th>
					<th>
						不满意
					</th>
					<th>
						不了解
					</th>
				</tr>

			</table>
			<div class="output">
				<input name="export" type="button" class="btn btn-primary" id="answerReportPrint" value="导出" />
			</div>
		</div>
		<div id="statusReportDiv" style="display: none;">
			
			<table id="listForStatusReport" class="table table-bordered">
				<caption class="tabtitle">
					回访状态统计表
				</caption>
				<caption style="text-align: right;font-size: 12px;">客户量：<font name="count" color="green"></font></caption>
				<tr>
					<th>
						回访区域
					</th>
					<th>
						回访成功
					</th>
					<th>
						未回访
					</th>
					<th>
						无效回访
					</th>
					<th>
						需再次回访
					</th>
					<th>
						总数
					</th>
				</tr>
			</table>
			<div class="output">
				<input  name="export" type="button" class="btn btn-primary" id="statusReportPrint" value="导出" />
			</div>
		</div>
		</div>
	</body>
</html>
