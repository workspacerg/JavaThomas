var global = this;
$(function(){
	global.Esgi.html.Table = function(cfg) {
		var me = this;
		me.cfg = cfg;
		me.initComponent();
	}

	global.Esgi.html.Table.prototype = {
			initComponent : function(){
				var me = this,
				tr = $('<tr/>')
				me.table = $('<table/>');
				$(me.cfg.renderTo).append(me.table);
				me.table.append(tr);
			},
			setItems : function(items){
				var me = this;
				$.each(items, function(idx, item) {
					me.addItem(item, idx);
				})
			},
			addItem : function(item,index){
				var me = this,
				tr = $('<tr id="row_'+index+'"/>');
				me.table.append(tr);
				$.each(me.cfg.columns, function(idx, label){
					var td = $('<td/>');
					tr.append(td);
					if ('actions' != label) {
						td.text(item[label]);
					} else {
						$.each(me.cfg.itemActions, function(idx, action){
							var a = $('<'+action.type+'/>');
							a.on('click', function(){
								action.callback(item,index);
							});
							a.text(action.label);
							td.append(a);
						})
					}
				});
			}
	}

});