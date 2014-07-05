$(function(){
	LoadCombo = function(cfg){
		$.ajax({
			url : APP_CONTEXT+'/Admin/LoadFilm',
			method : 'POST',
			success : function(response) {
				data = JSON.parse(response);
				if(data["success"])
					new Esgi.module.Admin.RemoveFilm(cfg,data["select"]);
				else
				{
					var message = $("#errorMessage");
					message.css("visibility","visible");
					message.css("-webkit-animation","notificationError 1s");
					message.css("animation","notificationError 1s");
					$("#errorMessage").html("<h2> Aucun film n'est à l'affiche pour le moment.</h2>");
				}
			}	
		});
	};
	
	Esgi.module = Esgi.module || {}
	Esgi.module.Admin = Esgi.module.Admin || {}

	Esgi.module.Admin.RemoveFilm = function (cfg,contentHtml) {
		new Esgi.html.Form({
			url : APP_CONTEXT+'/Admin/RemoveFilm',
			renderTo : cfg.id,
			inputs : [
			          {
			        	  type : "innerHtml",
			        	  name : 'selectFilm',
			        	  label : "selectFilm",
			        	  content : contentHtml
			          }			          
			          ],
			    action: AjaxComplete,
			    ajaxReplace : launchAjax
		});
		
	}
});

function AjaxComplete(data){
	var message = $("#errorMessage");
	message.css("visibility","visible");
	message.css("-webkit-animation","notificationError 1s");
	message.css("animation","notificationError 1s");
	
	if(data["success"]){
		$("#filmAffiche option:selected").remove();
		$("#errorMessage").html("<h2> Le film a bien été retiré de l'affiche.</h2>");
	}
	else
		$("#errorMessage").html(data["message"]);
};

function launchAjax(nurl){
	if($("#filmAffiche").val() == ''){
		var message = $("#errorMessage");
		message.css("visibility","visible");
		message.css("-webkit-animation","notificationError 1s");
		message.css("animation","notificationError 1s");
		$("#errorMessage").html("<h2>Veuillez choisir un film</h2>");
		return false;
	}

	$.ajax({
		url : nurl,
		method : 'POST',
		data : { idFilm : $("#filmAffiche").val()}	,
		success : function(response) {
			obj = JSON.parse(response);
			// Appel de l'action
			AjaxComplete(obj);
		}
	
	});
}