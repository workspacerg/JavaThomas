$( "#clickSeance" ).click(function() {
		if($("#seanceDiv").is(":visible"))
			$("#seanceDiv").slideUp( "slow");
		else
			$("#seanceDiv").slideDown( "slow");
	});