$(function(){	
	$( "#validComment" ).click(function() {
		if($("#commentPlace").val() == ''){
			var message = $("#errorMessage");
			message.css("visibility","visible");
			message.css("-webkit-animation","notificationError 1s");
			message.css("animation","notificationError 1s");
			$("#errorMessage").html("<h2>Veuillez entrer un commentaire</h2>");
			e.preventDefault();
			return false;
		}
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
					$("#commentPlace").val("");
					$("#note").val(0);
					window.location.reload();
				}
				else{
					var message = $("#errorMessage");
					message.css("visibility","visible");
					message.css("-webkit-animation","notificationError 1s");
					message.css("animation","notificationError 1s");
					$("#errorMessage").html(obj["message"]);
				}
			}

		})
	});
});