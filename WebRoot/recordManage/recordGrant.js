Ext.namespace('dm.recordGrant');

dm.recordGrant.grid = function(){
	var grid = this;
	
	grid.pagesize_combo = new dm.object.pagesize_combo();
	grid.pageSize = parseInt(grid.pagesize_combo.getValue());
	
	grid.store = new Ext.data.Store({
		proxy : new Ext.data.HttpProxy({
			url : 'resource/recordGrant/search',
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
		}]);
	
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
	
	dm.recordGrant.grid.superclass.constructor.call(this, {
		title : '文件发布',
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

		}, '->', grid.searchTxt]
	});
	
	grid.store.on('beforeload', function() {
		this.baseParams = {
			searchName : grid.searchTxt.getValue()
		};
	});
	grid.loadData();
};

Ext.extend(dm.recordGrant.grid,Ext.grid.GridPanel,{
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
	addWin : function(){
		var gird = this;

		var win = new dm.recordGrant.addWin("add", null,gird);
		win.show();
	},
	updWin : function(){
		var gird = this;
		var len = this.getSelectionModel().getSelections().length;
		if (len == 0) {
			Ext.Msg.alert('提示', '请选择一条记录');
			return;
		}
		var id = this.getSelectionModel().getSelected().data.id;
		var win = new dm.recordGrant.addWin("upd", id,gird);
		win.show();
	},
	infoWin : function(){
		var gird = this;
		var len = this.getSelectionModel().getSelections().length;
		if (len == 0) {
			Ext.Msg.alert('提示', '请选择一条记录');
			return;
		}
		var id = this.getSelectionModel().getSelected().data.id;
		var win = new dm.recordGrant.addWin("info", id,gird);
		win.show();
	},
	del : function(){
		var gird = this;
		var len = this.getSelectionModel().getSelections().length;
		if (len == 0) {
			Ext.Msg.alert('提示', '请选择一条记录');
			return;
		}
		var id = this.getSelectionModel().getSelected().data.id;
		Ext.Ajax.request({
			url : 'resource/recordGrant/delete',
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
	}
});

dm.recordGrant.addWin = function(actionType, id,grid){
	var win = this;
	
	win.form = new Ext.form.FormPanel({ 
		buttonAlign : 'center',
		baseCls : 'x-plain',
		bodyStyle : 'padding:5px 0px 0px',
		labelAlign : 'right',
		width : 640,
		reader : new Ext.data.JsonReader({
			successProperty : 'success',
			root : 'bean'
		}, [ {
			name : 'id'
		}, {
			name : 'record_type'
		}, { 
			name : 'record_name'
		}, { 
			name: 'record_code'
		}, { 
			name: 'record_use_scope'
		}, { 
			name: 'record_use_date'
		}, { 
			name: 'record_release_scope'
		}, { 
			name: 'record_examine_date'
		}, { 
			name: 'examine_user'
		}, { 
			name: 'record_release_date'
		}, { 
			name: 'release_user'
		}, { 
			name: 'record_implement_date'
		}, { 
			name: 'remark'
		}, { 
			name: 'establish_user'
		}, { 
			name: 'status'
		}]),
		items : [{
			layout : 'column',
			items : [{
						columnWidth : 0.5,
						baseCls : 'x-plain',
						bodyStyle : 'padding:5px 0px 0px',
						items : [{
									layout : 'form',
									baseCls : 'x-plain',
									items : [{
												xtype : 'textfield',
												fieldLabel : '文件类型',
												name : 'record_type'
											}, {
												xtype : 'textfield',
												fieldLabel : '文件名称',
												name : 'record_name'
											}, {
												xtype : 'textfield',
												fieldLabel : '文件编码',
												name : 'record_code'
											}, {
												xtype : 'textfield',
												fieldLabel : '文件受控期限',
												name : 'record_use_date'
											}, {
												xtype : 'datefield',
												fieldLabel : '文件实施日期',
												format : 'Y-m-d',
												width : 140,
												name : 'record_implement_date'
											}]
								}]

					}, {
						columnWidth : 0.5,
						baseCls : 'x-plain',
						bodyStyle : 'padding:5px 0px 0px',
						items : [{
									layout : 'form',
									baseCls : 'x-plain',
									items : [{
												xtype : 'datefield',
												fieldLabel : '文件批准日期',
												format : 'Y-m-d',
												width : 140,
												name : 'record_examine_date'
											}, {
												xtype : 'textfield',
												fieldLabel : '批准人',
												name : 'examine_user'
											}, {
												xtype : 'datefield',
												fieldLabel : '文件发布日期',
												width : 140,
												format : 'Y-m-d',
												name : 'record_release_date'
											}, {
												xtype : 'textfield',
												fieldLabel : '发布人',
												name : 'release_user'
											}, {
												xtype : 'textfield',
												fieldLabel : '编制人',
												name : 'establish_user'
											}]
								}]
					}]
		},{xtype : 'textarea',
		   fieldLabel : '文件适用范围',
		   name : 'record_use_scope'
		},{xtype : 'textarea',
		   fieldLabel : '备    注',
		   name : 'remark'
		}],
		buttons : [{
			text : '确定',
			listeners : {
				'click' : function(){
					if (actionType == "add") {
						// 新增
						
						if (win.form.getForm().isValid()) {
							win.form.getForm().doAction('submit', {
								url : "resource/recordGrant/add",
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
								url : "resource/recordGrant/modify",
								method : 'post',
								params:{id:id},
								waitMsg : '正在加载',
								timeout : 2000,
								success : function(response) {
									dm.comm.comm_alert({
										msg : '修改成功'
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
			url : "resource/recordGrant/load"
		});
	} else if (actionType == "info") {
		actionTitle = '查看';
		win.form.getForm().load({
			params : {
				id : id
			},
			url : "resource/recordGrant/load"
		});
	}
	
	dm.recordGrant.addWin.superclass.constructor.call(this, {
		title : '新增',
		resizable : false,
		bodyStyle : 'padding:5px 0px 0px',
		modal : true,
		width : 640,
		closeAction : 'destroy',
		bodyStyle : 'padding:5px;',
		closable : true,
		items : [ win.form ]
	});
};

Ext.extend(dm.recordGrant.addWin, Ext.Window);

