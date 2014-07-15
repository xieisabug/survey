function deleteQuestion(index) {
    $("#question_set"+index).remove();
}

function addQuestion(){
    var questions = $(".question");
    var size = questions.length;
    var html='';
    html += '<div class="input-group question" id="question_set'+size+'">';
    html += '<input type="text" id="question'+size+'" value="" class="form-control">';
    html += '    <span class="input-group-btn">';
    html += '        <button class="btn btn-danger" type="button" onclick="deleteQuestion('+size+')">删除</button>';
    html += '    </span>';
    html += '</div>';
    $(html).insertAfter(questions[size-1]);
}

function submitQuestion() {
    var qusetions = '';
    $("input").each(function (index, item) {
        qusetions += $(item).val() + '|';
    });

    $.ajax({
        url: path+'question/commit',
        type: 'post',
        data: {
            questions:qusetions
        },
        success: function () {
            window.location.href = path + "home";
        }
    });
}

$(document).ready(function(){
	$("#back").click(function(){
		var curPath=window.document.location.href;
		var pathName=window.document.location.pathname;
	    var pos=curPath.indexOf(pathName);
		var localhostPaht=curPath.substring(0,pos);
		var projectName=pathName.substring(0,pathName.substr(1).indexOf('/')+1);
		window.location.href=localhostPaht+projectName+"/customers";
	});
});