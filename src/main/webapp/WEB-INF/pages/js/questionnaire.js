var finalPhone = "";
function feedback(index) {
    $("#feedback"+index).removeClass("hidden");
}

function submitQuestionnaire(){
    var i;
    var cid = $("#customerId").val();
    var qid = "";
    var size = $(".question").length;
    var feedback = "";
    var answer = "";
    var status = $("#status").val();
    var reason = $("#reason").val();
    var updatePerson = $("#updatePerson").val();
    var updatePhone = $("#updatePhone").val()==undefined?"":$("#updatePhone").val();
    for(i = 0; i<size; i++) {
        answer += $("input[name=answer"+i+"]:checked").val()+"|";
        qid += $("#questionId"+i).val()+"|";
        feedback +=  $("#feedbackText"+i).val()+"|";
    }
    if(status == 2) {
        for(i = 0; i<size; i++) {
            if( $("input[name=answer"+i+"]:checked").val() == undefined) {
                alert("您有未填写的答案。");
                return;
            }
        }
        if(finalPhone==""){
        	alert("请勾选您回访的电话号码！");
        	return;
        }
        $.ajax({
            url:path+'questionnaire/commit',
            type:'post',
            data:{
                cid:cid,
                qid:qid,
                feedback:feedback,
                answer:answer,
                status:status,
                updatePerson:updatePerson,
                updatePhone:updatePhone,
                reason:reason,
                finalPhone:finalPhone
            },
            success:function(){
            	alert("提交成功！");
                window.location.href = path + "home";
            }
        });
    } else {
        $.ajax({
            url:path+'questionnaire/commitFail',
            type:'post',
            data:{
                cid:cid,
                qid:qid,
                feedback:feedback,
                answer:answer,
                status:status,
                updatePerson:updatePerson,
                updatePhone:updatePhone,
                reason:reason,
                finalPhone:finalPhone
            },
            success:function(){
            	alert("提交成功！");
                window.location.href = path + "home";
            }
        });
    }
}

function submitQuestionnaireUpdate(){
    var i;
    var cid = $("#customerId").val();
    var qid = "";
    var size = $(".question").length;
    var feedback = "";
    var answer = "";
    var status = $("#status").val();
    var reason = $("#reason").val();
    var updatePerson = $("#updatePerson").val();
    var updatePhone = $("#updatePhone").val()==undefined?"":$("#updatePhone").val();
    for(i = 0; i<size; i++) {
        answer += $("input[name=answer"+i+"]:checked").val()+"|";
        qid += $("#questionId"+i).val()+"|";
        feedback +=  $("#feedbackText"+i).val()+"|";
    }
    if(status == 2) {
        for(i = 0; i<size; i++) {
            if( $("input[name=answer"+i+"]:checked").val() == undefined) {
                alert("您有未填写的答案。");
                return;
            }
        }
        $("input[name='finalPhone']").each(function(){
    		if($(this).attr("checked")=="checked"){
    			finalPhone = $(this).val();
    		}
    	});
        if(finalPhone==""){
        	alert("请勾选您回访的电话号码！");
        	return;
        }
        $.ajax({
            url:path+'questionnaire/updateCommit',
            type:'post',
            data:{
                cid:cid,
                qid:qid,
                feedback:feedback,
                answer:answer,
                status:status,
                updatePerson:updatePerson,
                updatePhone:updatePhone,
                reason:reason,
                finalPhone:finalPhone
            },
            success:function(){
            	alert("提交成功！");
                window.location.href = path + "home";
            }
        });
    } else {
        $.ajax({
            url:path+'questionnaire/commitFail',
            type:'post',
            data:{
                cid:cid,
                qid:qid,
                feedback:feedback,
                answer:answer,
                status:status,
                updatePerson:updatePerson,
                updatePhone:updatePhone,
                reason:reason,
                finalPhone:finalPhone
            },
            success:function(){
            	alert("提交成功！");
                window.location.href = path + "home";
            }
        });
    }
}

$(document).ready(function(){
	var status = $("#curStatus").val();
	$("#status").val(status);
    if(!$.browser.msie || ($.browser.msie && $.browser.version>6)) {
        $('.answer input').iCheck({
            radioClass: 'iradio_minimal-blue'
        });
    }

    var status = $("#status");
    var reason = $("#reason");
    var html1 = "";
    html1 += '<option value="非办税人员">非办税人员</option>';
    html1 += '<option value="号码无效">号码无效</option>';
    html1 += '<option value="拒绝回访">拒绝回访</option>';
    html1 += '<option value="一直占线">一直占线</option>';
    html1 += '<option value="一直无人接听">一直无人接听</option>';
    var html2 = "";
    html2 += '<option value="手机关机">手机关机</option>';
    html2 += '<option value="手机占线">手机占线</option>';
    html2 += '<option value="无人接听">无人接听</option>';
    html2 += '<option value="无法接通">无法接通</option>';

    status.change(function(){
        var index = status.val();
        if(index == 3) {
            reason.html(html1);
            reason.removeClass("hidden");
        } else if(index == 4) {
            reason.html(html2);
            reason.removeClass("hidden");
        } else {
            reason.html("");
            reason.addClass("hidden");
        }
    });
    
    $("#back").click(function(){
		var curPath=window.document.location.href;
		var pathName=window.document.location.pathname;
	    var pos=curPath.indexOf(pathName);
		var localhostPaht=curPath.substring(0,pos);
		var projectName=pathName.substring(0,pathName.substr(1).indexOf('/')+1);
		window.location.href=localhostPaht+projectName+"/customers";
	});
    
    $("input[type='checkbox']").click(function(){
    	var obj = this;
    	$("input[name='finalPhone']").each(function(){
    		if(obj != this){
    			$(this).attr("checked",false);
    		}
    	}); 
    	finalPhone = $(this).attr("checked")=="checked"?$(this).val():"";
    });
    
    
    
});