$(function(){
	$( "#validComment" ).click(function() {
		data = {};
		data["commentPlace"] = $("#commentPlace").val();
		data["note"] = $("#note").val();
		data["idfilm"] = $("#idfilm").val();
		$.ajax({
			url : APP_CONTEXT+'/index/SendComment',
			method : 'POST',
			data : data,
			success : function(response) {
				obj = JSON.parse(response);
				if(obj["success"]){
					alert("ok");
					window.location.reload();
				}
				else
					$("#errorMessage").html(obj["message"]);
			}

		})
	});
});