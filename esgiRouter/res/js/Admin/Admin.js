$(function(){
	
	Esgi.Admin = Esgi.Admin || {}
	Esgi.Admin.Menu = Esgi.Admin.Menu || {}
	
	Esgi.Admin.Menu = function(cfg,btncfg) {
		var me = this;
		me.cfg = cfg;
		me.btnUrl = btncfg;
		me.initButtonsMenu();
		me.initFilmMenu();
	};

	Esgi.Admin.Menu.prototype = {
			initButtonsMenu : function(){
				me = this;
				var addFilm = $('<div id="addFilm"/>');
				var removeFilm = $('<div id="removeFilm"/>');
				var addAdmin = $('<div id="addAdmin"/>');
				buttons = [addFilm,removeFilm,addAdmin];
				
				// Titres
				addFilm.append('<h2> Ajouter un film à l\'affiche </h2>');
				removeFilm.append('<h2> Retirer un film à l\'affiche');
				addAdmin.append('<h2> Ajouter un administrateur </h2>');
				
				addFilm.on('click',function(){
					if(!$(me.cfg).is(":visible")){
						new Esgi.module.Admin.AddFilm({ id : $(me.cfg)})
						$(me.cfg).slideDown("slow");
					}
					else{
						$(me.cfg).slideUp("fast");
						new Esgi.module.Admin.AddFilm({ id : $(me.cfg)})
						$(me.cfg).slideDown("fast");
					}
				});
				
				removeFilm.on('click',function(){
					if(!$(me.cfg).is(":visible")){
						LoadCombo({ id : $(me.cfg)});
						$(me.cfg).slideDown("slow");
					}
					else{
						$(me.cfg).slideUp("fast");
						LoadCombo({ id : $(me.cfg)});
						$(me.cfg).slideDown("fast");
					}
				});
				
				addAdmin.on('click',function(){
					if(!$(me.cfg).is(":visible")){
						new Esgi.module.Admin.AddAdmin({ id : $(me.cfg)})
						$(me.cfg).slideDown("slow");
					}
					else{
						$(me.cfg).slideUp("fast");
						new Esgi.module.Admin.AddAdmin({ id : $(me.cfg)})
						$(me.cfg).slideDown("fast");
					}
				});
				
				$.each(buttons,function(i,elem){
					$(elem).addClass('buttonGreen');
				});
				
				$(me.btnUrl).append(buttons);
			},
			initFilmMenu : function(){
				me = this;
				var mainDiv = $('<div id="addFilmMenu"/>')
				
				$(me.cfg).append(mainDiv);
				
			}
			
	};
		
});