
	$(function () {
		$(".btn-comment").click(function () {
			var parentId = $("#questionId").val();
			var content = $(".text-comment").val();
			
			if(content.match(/^[ ]*$/)){
				alert("回复内容不能为空~");
				return;
			}
			$.ajax({
				type: "POST",
				url: "/comment",
				contentType: "application/json",  //发送信息至服务器时内容编码类型。
				dataType: "json",  // 预期服务器返回的数据类型。如果不指定，jQuery 将自动根据 HTTP 包 MIME 信息来智能判断，比如XML MIME类型就被识别为XML。
				data: JSON.stringify({ "parentId": parentId, "content": content, "type": 1 }),
				success: function (data) {
					if (data.code == 200) {
						window.location.reload();
						$(".text-comment").val("");
					} else if (data.code == 2003) {
						confirm(data.message);
						window.open("https://github.com/login/oauth/authorize?client_id=74a455a0bddfc1639361&redirect_uri=http://localhost:8082/callback&scope=user&state=1");
						window.localStorage.setItem("closable",true);

					}else{
						alert(data.message);

					}
				}
			});

		})


	})