$(function(){
	Esgi.module = Esgi.module || {}
	Esgi.module.Admin = Esgi.module.Admin || {}

	Esgi.module.Admin.AddFilm = function (cfg) {
		new Esgi.html.Form({
			url : APP_CONTEXT+'/Admin/AddFilm',
			renderTo : cfg.id,
			inputs : [
			          {
			        	  type : "Text",
			        	  name : 'titre',
			        	  label : "Intitulé du film",
			        	  emptyText : "", 
			        	  required : true
			          },
			          {
			        	  type : "Text",
			        	  name : 'realisateur',
			        	  label : "Réalisateur",
			        	  emptyText : "", 
			        	  required : true
			          },
			          {
			        	  type : "Text",
			        	  name : 'genre',
			        	  label : "Genre",
			        	  emptyText : "", 
			        	  required : true
			          },
			          {
			        	  type : "File",
			        	  name : 'imgFilm',
			        	  label : "imgFilm",
			        	  emptyText : "",
			        	  required : true
			          },
			          {
			        	  type : "File",
			        	  name : 'imgFond',
			        	  label : "imgFond",
			        	  emptyText : "",
			        	  required : true
			          },
			          {
			        	  type : "Textarea", 
			        	  name : 'description',
			        	  label : "Synopsis du film",
			        	  emptyText : "",
			        	  rows : "15",
			        	  cols : "35",
			        	  required : true
			          }
			          ],
			    ajaxReplace : launchAjax
		});
		
	}
	
	function AjaxComplete(data){
			var message = $("#errorMessage");
			message.css("visibility","visible");
			message.css("-webkit-animation","notificationError 1s");
			message.css("animation","notificationError 1s");
		if(data["success"]){
			$("input").val('');
			$("#errorMessage").html("<h2> Le film a bien été mis à l'affiche.</h2>");
		}
		else
			$("#errorMessage").html(data["message"]);
	};
	
	function launchAjax(nurl,data){
		if($("#imgFilmFile_imgFilm").val().indexOf("jpg") == -1 && $("#imgFilmFile_imgFilm").val().indexOf("jpeg") == -1 
				&& $("#imgFilmFile_imgFond").val().indexOf("jpg") == -1 && $("#imgFilmFile_imgFond").val().indexOf("jpeg") == -1 ){
			var message = $("#errorMessage");
			message.css("visibility","visible");
			message.css("-webkit-animation","notificationError 1s");
			message.css("animation","notificationError 1s");
			$("#errorMessage").html("<h2>Veuillez choisir une image à l'extension jpg</h2>");
			return false;
		}
		
		var form = new FormData();    
		form.append('file', $("#imgFilmFile_imgFilm")[0].files[0] );
		form.append('fileFond', $("#imgFilmFile_imgFond")[0].files[0] );
		
		$.each(data,function(key,item){
			form.append(key,item);
		});
		
		
		console.log(form);
		
		$.ajax({
			url : nurl,
			method : 'POST',
			data : form,
			contentType: false,
			processData : false,
			success : function(response) {
				obj = JSON.parse(response);
				// Appel de l'action
				AjaxComplete(obj);
			}

		});
	}

});