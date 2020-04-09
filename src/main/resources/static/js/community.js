$(function () {
	moment.locale("zh-cn");
	var QUESTION_TYPE = 1;
	var COMMENT_TYPE = 2;
	dateFormat($(document));
	
	
	/**
	 * 提交一级回复
	 */
	$(".btn-comment-submit").on("click",function () {
		var parentId = $("#questionId").val();
		var content = $(".text-comment").val();
		comment(parentId, QUESTION_TYPE, content);
	});
	/**
	 * 提交二级回复
	 * 由于提交按钮是动态生成的，使用on方法绑定事件
	 */

	$(document).on("click",".btn-sub-comment-submit",function () {
		var parentId = $(this).data("id");
		var content = $(".text-sub-comment-"+parentId).val();
		comment(parentId, COMMENT_TYPE, content);
	});

	/**
	 * 点击展开二级评论
	 */
	$(".operate-comment").on("click", function () {
		var subCommentParentId = $(this).parent().data("id");
		// 如果没有二级评论展示，就展示
		if ($("#cmt-" + subCommentParentId + " .well").children().length == 0) {
			$.getJSON("/comment/" + subCommentParentId,
				function (result) {
					$.each(result.data, function (index, comment) {
						buildSubComments(comment, subCommentParentId);
					})
					buildSubCommentInput(subCommentParentId);

				}
			);

		}
		// 已经有展示了，就删除掉
		else{
			$("#cmt-" + subCommentParentId + " .well").children().remove();
		}
	});
	
	
	/**
	 * 点击标签输入框弹出标签库
	 */
	$("#tag").on("click", function() {
		$("#select-tag").show();
	});
	
	$(".question-tag").on("click",function () {
	    var value = this.getAttribute("data-tag");
	    var previous = $("#tag").val();
	    if (previous.split(",").indexOf(value) == -1) {
	        if (previous) {
	            $("#tag").val(previous + ',' + value);
	        } else {
	            $("#tag").val(value);
	        }
	    }
	});
})



/**
 * 构建二级评论
 * @param {*} comment 
 * @param {*} commentParentId 
 */
function buildSubComments(comment, commentParentId) {
	var commentItem = $("<div>", { "class": "media comment-item" });
	var avatar = $("<a>",
		{
			"class": "pull-left",
			"html": "<img class='media-object img-rounded' src='" + comment.user.avatarUrl + "'></img>"
		}
	);

	var commentBody = $("<div>").addClass("media-body comment-body");
	var userName = $("<h5 class=media-heading><a>" + comment.user.name + "</a></h5>");
	var content = $("<span>" + comment.content + "</span>");
	//格式化时间
	var time = moment(comment.gmtCreate);
	var now = moment(new Date());
	var textTime = time;
	if (now.diff(time) < 7 * 24 * 60 * 60 * 1000) {
		textTime=time.fromNow();
	}
	var commentOperate = $("<div class=comment-operate></dev>")
		.append($("<span>" + textTime + "</span>").addClass("pull-right time"));

	var commentBodyNode = commentBody.append(userName).append(content).append(commentOperate);
	$("#cmt-" + commentParentId + " .well")
		.append(commentItem.append(avatar).append(commentBodyNode));
}

/**
 * post请求提交一级/二级评论
 * @param {*} targetId 
 * @param {*} type 
 * @param {*} content 
 */
function comment(targetId, type, content) {

	if (content.match(/^[ ]*$/)) {
		alert("回复内容不能为空~");
		return;
	}
	$.ajax({
		type: "POST",
		url: "/comment",
		contentType: "application/json",  //发送信息至服务器时内容编码类型。
		dataType: "json",  // 预期服务器返回的数据类型。如果不指定，jQuery 将自动根据 HTTP 包 MIME 信息来智能判断，比如XML MIME类型就被识别为XML。
		data: JSON.stringify({ "parentId": targetId, "content": content, "type": type }),
		success: function (data) {
			if (data.code == 200) {
				window.location.reload();
				//$(".text-comment").val("");
			} else if (data.code == 2003) {
				confirm(data.message);
				window.open("https://github.com/login/oauth/authorize?client_id=74a455a0bddfc1639361&redirect_uri=http://localhost:8082/callback&scope=user&state=1");
				window.localStorage.setItem("closable", true);

			} else {
				alert(data.message);

			}
		}
	});
}
/**
 * parentSelector下所有.time元素时间格式化
 * 如果是7天前就显示日期，否则显示相对日期
 */
function dateFormat(parentSelector) {
	$.each(parentSelector.find($(".time")), function (index, object) {
		var time = moment($(object).text(), "YYYYMMDD,H:mm");
		var now = moment(new Date());
		if (now.diff(time) < 7 * 24 * 60 * 60 * 1000) {
			$(object).text(time.fromNow());
		}
	})
}
/**
 * 构建二级评论下面的回复二级评论输入框
 * @param {} commentParentId 
 */
function buildSubCommentInput(commentParentId) {
	var submit = $("<div>")
		.append($("<textarea rows=1 placeholder=评论一下...></textarea>").addClass("form-control text-sub-comment-" + commentParentId))
		.append($("<button>提交</button>").addClass("pull-right btn btn-success btn-sm btn-sub-comment-submit").attr("data-id", commentParentId));
	$("#cmt-" + commentParentId + " .well").append(submit);
}
