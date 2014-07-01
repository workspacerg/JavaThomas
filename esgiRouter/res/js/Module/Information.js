$(function(){
	
	$("#changePassword").click(function(){
		if($("#container").is(":visible"))
			$("#container").slideUp( "slow");
		else
			$("#container").slideDown( "slow");
	});
	
	Esgi.module = Esgi.module || {}
	Esgi.module.Information = Esgi.module.Information || {}

	Esgi.module.Information.Form = function (cfg) {
		new Esgi.html.Form({
			url : APP_CONTEXT+'/Profil/SaveInformation',
			renderTo : cfg.id,
			inputs : [
				          {
				        	  type : "Password",
				        	  name : 'newPassword',
				        	  label : "Nouveau mot de passe",
				        	  emptyText : "",
				        	  required : true
				          },
				          {
				        	  type : "Password",
				        	  name : 'confirmPassword',
				        	  label : "Confirmer le mot de passe",
				        	  emptyText : "",
				        	  required : true
				        		
				          }
			          ],
			     action : AjaxComplete
		});
		
	}
	
	function AjaxComplete(data){
		if(data["success"]){
			$("#container").append("<h2> Mise à jour du mot de passe effectuée.</h2>");
			$("#container").slideUp( "slow");
		}
		else
			$("#errorMessage").html(data["message"]);
		
	}
});