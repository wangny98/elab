Ext.namespace('dm.showCase');

dm.showCase.grid = function(){
	var grid = this;
	
	grid.pagesize_combo = new dm.object.pagesize_combo();
	grid.pageSize = parseInt(grid.pagesize_combo.getValue());
	
	grid.store = new Ext.data.Store({
		proxy : new Ext.data.HttpProxy({
			url : 'resource/caseShow/search',
			method : 'post'
		}),
		reader : new Ext.data.JsonReader({
			totalProperty : 'total',
			root : 'list'
		}, [ {
			name : 'id'
		}, {
			name : 'name'
		}, { 
			name : 'note'
		}, { 
			name: 'creator_name'
		}, { 
			name: 'create_time'
		}, { 
			name: 'public_org'
		}, { 
			name: 'public_time'
		}]),
		baseParams : {
			searchName : ''
		}
	});
	
	grid.cm = new Ext.grid.ColumnModel([{
			header : '名称',
			dataIndex : "name", // 编码
			sortable : true,
			renderer : function(value) {
				return dm.comm.comm_getTip(value);
			}
		},{
			header : '描述',
			dataIndex : "note", // 编码
			sortable : true,
			renderer : function(value) {
				return dm.comm.comm_getTip(value);
			}
		}/*,{
			header : '发布组织',
			dataIndex : "public_org", // 编码
			sortable : true,
			renderer : function(value) {
				return dm.comm.comm_getTip(value);
			}
		},{
			header : '发布时间',
			dataIndex : "public_time", // 编码
			sortable : true,
			renderer : function(value) {
				return dm.comm.comm_getTip(value);
			}
		}*/]);
	
	grid.pagesize_combo.on("select", function(comboBox) {
		grid.store.reload({
			params : {
				start : 0,
				limit : parseInt(comboBox.getValue())
			}
		});
	});

	grid.ProgressBar = new dm.object.ProgressBarPager();
	grid.ppBar = new dm.object.PagingToolbar(grid.pageSize, grid.ProgressBar,
			grid.store, grid.pagesize_combo);
	
	grid.searchTxt = Ext.create({
		xtype : 'textfield',
		width : 150,
		emptyText : '请输入单号后按回车',
		enableKeyEvents : true,
		listeners : {
			specialkey : function(field, e) {
				if (e.getKey() == Ext.EventObject.ENTER) {
					grid.loadData();
				}
			}
		}
	});
	
	dm.showCase.grid.superclass.constructor.call(this, {
		title : '案例展示',
		closable:true,
		viewConfig : {
			forceFit : true
		// 不产横向生滚动条, 各列自动扩展自动压缩, 适用于列数比较少的情况
		},
		store : grid.store,
		columns : grid.cm,
		bbar : grid.ppBar,
		tbar : [ {
			text : '新增', // 新增
			iconCls : 'icon-add',
			funcCode : '',
			handler : function() {
				grid.addWin();
			}

		}, {xtype:"tbseparator"},{
			text : '修改', // 修改
			iconCls : 'icon-modify',
			funcCode : '',
			handler : function() {
				grid.updWin();
			}

		}, {xtype:"tbseparator"},{
			text : '查看', // 查看
			iconCls : 'icon-info',
			funcCode : '',
			handler : function() {
				grid.infoWin();
			}

		}, {xtype:"tbseparator"},{
			text : '删除', // 删除
			iconCls : 'icon-delete',
			funcCode : '',
			handler : function() {
				grid.del();
			}

		}, {xtype:"tbseparator"},{
			text : '图片', // 删除
			iconCls : 'icon-add',
			funcCode : '',
			handler : function() {
				grid.uploadPic();
			}

		}, '->', grid.searchTxt]
	});
	
	grid.store.on('beforeload', function() {
		this.baseParams = {
			searchName : grid.searchTxt.getValue(),
			stateStr : 'transfer'
		};
	});
	grid.loadData();
};

Ext.extend(dm.showCase.grid,Ext.grid.GridPanel,{
	loadData : function() {
		var grid = this;
		grid.getStore().load({
			params : {
				start : 0,
				limit : grid.pageSize
			},
			callback : function(records, options, success) {
				// dm.comm.showGridError();
			}
		});
	},
	addWin: function() {
		var gird = this;

		var win = new dm.showCase.addWin("add", null,gird);
		win.show();
	},
	updWin : function() {
		var gird = this;
		var len = this.getSelectionModel().getSelections().length;
		if (len == 0) {
			Ext.Msg.alert('提示', '请选择一条记录');
			return;
		}
		var id = this.getSelectionModel().getSelected().data.id;
		var win = new dm.showCase.addWin("upd", id,gird);
		win.show();
	},
	infoWin : function() {
		var gird = this;
		var len = this.getSelectionModel().getSelections().length;
		if (len == 0) {
			Ext.Msg.alert('提示', '请选择一条记录');
			return;
		}
		var id = this.getSelectionModel().getSelected().data.id;
		var win = new dm.showCase.addWin("info", id,gird);
		win.show();
	},
	del : function() {
		var gird = this;
		var len = this.getSelectionModel().getSelections().length;
		if (len == 0) {
			Ext.Msg.alert('提示', '请选择一条记录');
			return;
		}
		var id = this.getSelectionModel().getSelected().data.id;
		Ext.Ajax.request({
			url : 'resource/caseShow/delete',
			method : 'POST',
			params : {
				id:id
			},
			success : function(resp) {
				var json = Ext.util.JSON.decode(resp.responseText);
				var flag = json.success;
				if (flag) {
					Ext.Msg.alert('提示', '删除成功');
					gird.getStore().load({
						params : {
							start : 0,
							limit : gird.pageSize
						}});
				}else{
					Ext.Msg.alert('提示', '删除失败');
					
				}
			}
		});
	},
	uploadPic : function(){
		var gird = this;
		var len = this.getSelectionModel().getSelections().length;
		if (len == 0) {
			Ext.Msg.alert('提示', '请选择一条记录');
			return;
		}
		var id = this.getSelectionModel().getSelected().data.id;
		var win = new dm.picManage.win(id);
		win.show();
	}
});

dm.showCase.addWin = function(actionType, id,grid){
	var win = this;
	
	var state;
	
	win.form = new Ext.form.FormPanel({ 
		buttonAlign : 'center',
		baseCls : "x-plain",
		labelAlign : 'right',
		width : 400,
		reader : new Ext.data.JsonReader({
			successProperty : 'success',
			root : 'bean'
		}, [ {
			name : 'id'
		}, {
			name : 'name'
		}, { 
			name : 'note'
		}, { 
			name: 'creator_name'
		}, { 
			name: 'create_time'
		}, { 
			name: 'public_org'
		}, { 
			name: 'public_time'
		}]),
		items : [{
			xtype:'textfield',
        	fieldLabel:'名称',
        	allowBlank:false,
        	name:'name',
        	width:200
		},{
			xtype:'textfield',
        	fieldLabel:'描述',
        	name:'note',
        	width:200
		}],
		buttons : [{
			text : '确定',
			listeners : {
				'click' : function(){
					if (actionType == "add") {
						// 新增
						
						if (win.form.getForm().isValid()) {
							win.form.getForm().doAction('submit', {
								url : "resource/caseShow/add",
								method : 'post',
								waitMsg : '正在加载',
								timeout : 2000,
								success : function(response) {
									dm.comm.comm_alert({
										msg : '新增成功!'
									});
									win.close();
									grid.loadData();
								},
								failure : function(response, action) {

								}
							});
						}else {
							return;
						}
					}else if (actionType == "upd") {
						if (win.form.getForm().isValid()) {
							win.form.getForm().doAction('submit', {
								url : "resource/caseShow/modify",
								method : 'post',
								params:{id:id},
								waitMsg : '正在加载',
								timeout : 2000,
								success : function(response) {
									dm.comm.comm_alert({
										msg : '修改用户成功'
									});
									win.close();
									grid.loadData();
								},
								failure : function(response, action) {

								}
							});

						} else {
							return;
						}
					}
				}
			}
		},{
			text : '取消',
			listeners : {
				'click' : function(){
					win.close();
				}
			}
		}]
	});
	
	if (actionType == "add") {
		actionTitle = '新增';
	} else if (actionType == "upd") {
		actionTitle = '修改';
		win.form.getForm().load({
			params : {
				id : id
			},
			url : "resource/caseShow/load"
		});
	} else if (actionType == "info") {
		actionTitle = '查看';
		win.form.getForm().load({
			params : {
				id : id
			},
			url : "resource/caseShow/load"
		});
	}
	
	dm.showCase.addWin.superclass.constructor.call(this, {
		title : '新增',
		resizable : false,
		modal : true,
		width : 425,
		closeAction : 'destroy',
		bodyStyle : 'padding:5px;',
		closable : true,
		items : [ win.form ]
	});
};

Ext.extend(dm.showCase.addWin, Ext.Window);