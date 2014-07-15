<%@ page import="com.survey.mvc.dto.AnswersDto" %>
<%@ page import="java.util.List" %>
<%@ page import="com.survey.mvc.dto.QuestionDto" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<html>
<head>
    <title>问卷调查系统</title>
    <link href="<%=basePath%>bootstrap/css/bootstrap.css" rel="stylesheet" type="text/css">
    <link href="<%=basePath%>css/questionnaire.css" rel="stylesheet" type="text/css">
    <link href="<%=basePath%>css/minimal/_all.css" rel="stylesheet" type="text/css">
</head>
<body>
<div id="paper">
	<div style="width: 100%;text-align: right;margin-bottom: 10px;margin-right: 30px;">
		<img id="back" title="返回" style="width: 50px;height: 50px;cursor:pointer" src="<%=basePath%>images/back.png" /></div>
    <div id="question-person-info">
        <table>
    	<tr><td class="toRight">
    	<label for="updateCname">客户名称：</label></td>
    	<td>${customer.cname }</td>
    	<td class="toRight"><label for="updateTaxCode">税号：</label></td>
    	<td>${customer.taxCode }</td></tr>
    	<tr>
        <td class="toRight"><label for="updatePerson">接受回访人：</label></td>
        <td><input type="text"  style="border-style:none" name="updatePerson"  id="updatePerson" value="${customer.person}"></td>
        <td class="toRight"><label for="updatePhone">办税人电话：</label></td>
        <td>${customer.phone}&nbsp;&nbsp;&nbsp;&nbsp;<c:if test="${customer.phone != null}"><input type='checkbox' name='finalPhone'  value='${customer.phone}' /></c:if></td></tr>
        <tr><td class="toRight"><label for="updatePhone1">财务负责人电话：</label></td>
        <td>${customer.phone1}&nbsp;&nbsp;&nbsp;&nbsp;<c:if test="${customer.phone1 != null}"><input type='checkbox' name='finalPhone' value='${customer.phone1}' /></c:if></td>
        <td class="toRight"><label for="updatePhone2">法人电话：</label></td>
        <td>${customer.phone2}&nbsp;&nbsp;&nbsp;&nbsp;<c:if test="${customer.phone2 != null }"><input type='checkbox' name='finalPhone' value='${customer.phone2}' /></c:if></td>
        </tr>
        </table>
    </div>
    <div>
        <div style="height: 20px;"></div>
        <input id="customerId" name="customerId" type="hidden" value="${customer.cid}"/>
        <%
            List<AnswersDto> answersDtos = (List<AnswersDto>) request.getAttribute("questionnaire");
            List<QuestionDto> questions = (List<QuestionDto>) request.getAttribute("questions");
            for (int i = 0; i<questions.size(); i++) {
                QuestionDto q = questions.get(i);
                AnswersDto a = null;
                for (AnswersDto answersDto : answersDtos) {
                    if (q.getQid() == answersDto.getQid()) {
                        a=answersDto;
                    }
                }

        %>
            <div class="question">
                <span><%=q.getContent()%></span>
                <input id="questionId<%=i%>" name="questionId" type="hidden" value="<%=q.getQid()%>"/>
            </div>
            <div class="answer">
                <label>
                    <input type="radio" name="answer<%=i%>" value="1" <% if (a!=null && a.getAnswer().equals("非常满意")) {out.print("checked");}%>/>
                    <span>非常满意</span>
                </label>
                <label>
                    <input type="radio" name="answer<%=i%>" value="2" <% if (a!=null && a.getAnswer().equals("满意")) {out.print("checked");}%>/>
                    <span>满意</span>
                </label>
                <label>
                    <input type="radio" name="answer<%=i%>" value="3" <% if (a!=null && a.getAnswer().equals("基本满意")) {out.print("checked");}%>/>
                    <span>基本满意</span>
                </label>
                <label>
                    <input type="radio" name="answer<%=i%>" value="4" <% if (a!=null && a.getAnswer().equals("不满意")) {out.print("checked");}%>/>
                    <span>不满意</span>
                </label>
                <label>
                    <input type="radio" name="answer<%=i%>" value="5" <% if (a!=null && a.getAnswer().equals("不了解")) {out.print("checked");}%>/>
                    <span>不了解</span>
                </label>
                <button onclick="feedback(<%=i%>)" class="btn btn-primary">意见反馈</button>
                <div id="feedback<%=i%>" class="hidden feedback-input">
                    <input type="text" class="form-control" id="feedbackText<%=i%>" value="<% if(a!=null) { out.print(a.getFeedback());} else {out.print("无");} %>"/>
                </div>
            </div>
        <%
            }
        %>
    </div>
    <div id="submit">
    	<input type="hidden" value="${customer.status }" id="curStatus" />
        <select id="status">
            <option value="1">未回访</option>
            <option value="2" selected>回访成功</option>
            <option value="3">无效回访</option>
            <option value="4">需要再次回访</option>
        </select>
        <select id="reason" class="hidden"></select>
        <button onclick="submitQuestionnaireUpdate()" class="btn btn-danger ie-button">提交</button>
    </div>
</div>
</body>
<script src="<%=basePath%>js/jquery-1.8.0.min.js" type="text/javascript"></script>
<script src="<%=basePath%>js/icheck.min.js" type="text/javascript"></script>
<script src="<%=basePath%>js/questionnaire.js" type="text/javascript"></script>
<script>
    var path = "<%=basePath%>";
    $(document).ready(function(){
    	var nowPhone = "${customer.finalPhone}";
    	var objs = $("input[name='finalPhone']");
    	for(i = 0;i<objs.length;i++){
    		if($(objs[i]).val()==nowPhone){
    			$(objs[i]).attr("checked",true);
				break;
    		}
    	}
    	
    });
</script>
</html>
