Ext.namespace('dm.roleManager');

dm.roleManager.grid = function() {
	var grid = this;

	grid.pagesize_combo = new dm.object.pagesize_combo();
	grid.pageSize = parseInt(grid.pagesize_combo.getValue());

	grid.store = new Ext.data.Store({
		proxy : new Ext.data.HttpProxy({
			url : 'resource/roleManage/search',
			method : 'post'
		}),
		reader : new Ext.data.JsonReader({
			totalProperty : 'total',
			root : 'roles'
		}, [ {
			name : 'id'
		}, {
			name : 'name'
		},{
			name : 'code'
		}, {
			name : 'type'
		},{
			name : 'remark'
		},{
			name : 'creator_id'
		}, {
			name : 'creator_name'
		}, {
			name : 'creator_time'
		} ]),
		baseParams : {
			searchName : ''
		}
	});

	grid.cm = new Ext.grid.ColumnModel([ new Ext.grid.RowNumberer({
		renderer : function(value, metadata, record, rowIndex) {
			return grid.store.lastOptions.params.start + 1 + rowIndex;
		}
	}), {
		header : '名称',
		dataIndex : "name", // 名称
		sortable : true,
		renderer : function(value) {
			return dm.comm.comm_getTip(value);
		}
	},{
		header : '编码',
		dataIndex : "code", // 编码
		sortable : true,
		renderer : function(value) {
			return dm.comm.comm_getTip(value);
		}
	}, /* {
		header : '类型',
		dataIndex : "type", // 类型
		renderer : function(value) {
			return dm.comm.comm_getTip(value);
		}
	},*/ {
		header : '备注',
		dataIndex : "remark", // 备注号
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

	dm.roleManager.grid.superclass.constructor.call(this, {
		title : '角色管理',
		closable : true,
		id:'dm_roleManager_grid_1',
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

		}, {xtype:"tbseparator"}, {
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
			text : '分配功能', // 
			iconCls : 'icon-assignRole',
			funcCode : '',
			handler : function() {
				grid.assignFunc();
			}

		},'->', {
			xtype : 'textfield',
			width : 150,
			id : 'findRoleByName',
			emptyText : '请输入角色名，后按回车',
			enableKeyEvents : true,
			listeners : {
				specialkey : function(field, e) {
					if (e.getKey() == Ext.EventObject.ENTER) {
						grid.loadData();
					}
				}
			}
		} ]
	});
	grid.store.on('beforeload', function() {
		this.baseParams = {
			searchName : Ext.getCmp('findRoleByName').getValue()
		};
	});
	grid.loadData();
};

Ext.extend(dm.roleManager.grid, Ext.grid.GridPanel, {
	assignFunc:function(){
		var userGird = this;
		var len = this.getSelectionModel().getSelections().length;
		if (len == 0) {
			Ext.Msg.alert('提示', '请选择一条记录');
			return;
		}
		var id = this.getSelectionModel().getSelected().data.id;
		var win = new dm.functionCode.win(id);
		win.show();
	},
	loadData : function() {
		var roleGrid = this;
		roleGrid.getStore().load({
			params : {
				start : 0,
				limit : roleGrid.pageSize
			},
			callback : function(records, options, success) {
				// dm.comm.showGridError();
			}
		});
	},
	addWin : function() {
		var userGird = this;

		var win = new dm.roleManager.addWin("add", null);
		win.show();
	},
	updWin : function() {
		var userGird = this;
		var len = this.getSelectionModel().getSelections().length;
		if (len == 0) {
			Ext.Msg.alert('提示', '请选择一条记录');
			return;
		}
		var id = this.getSelectionModel().getSelected().data.id;
		var win = new dm.roleManager.addWin("upd", id);
		win.show();
	},
	infoWin : function() {
		var userGird = this;
		var len = this.getSelectionModel().getSelections().length;
		if (len == 0) {
			Ext.Msg.alert('提示', '请选择一条记录');
			return;
		}
		var id = this.getSelectionModel().getSelected().data.id;
		var win = new dm.roleManager.addWin("info", id);
		win.show();
	},
	del : function() {
		var userGird = this;
		var len = this.getSelectionModel().getSelections().length;
		if (len == 0) {
			Ext.Msg.alert('提示', '请选择一条记录');
			return;
		}
		var id = this.getSelectionModel().getSelected().data.id;
		Ext.Ajax.request({
			url : 'resource/roleManage/deleteRole',
			method : 'POST',
			params : {
				'id':id
			},
			success : function(resp) {
				var json = Ext.util.JSON.decode(resp.responseText);
				var flag = json.success;
				if (flag) {
					Ext.Msg.alert('提示', '删除成功');
					Ext.getCmp("dm_roleManager_grid_1").loadData();
				}else{
					Ext.Msg.alert('提示', '删除失败');
				}
			}
		});
	}

});

dm.roleManager.addWin = function(actionType, id) {
	var win = this;

	win.form = new Ext.form.FormPanel({
		buttonAlign : 'center',
		baseCls : "x-plain",
		labelAlign : 'right',
		reader : new Ext.data.JsonReader({
			successProperty : 'success',
			root : 'role'
		}, [{
					name : 'name'
				}, {
					name : 'type'
				},{
					name : 'code'
				},{
					name : 'remark'
				}, {
					name : 'creatorName'
				}, {
					name : 'createTime',
					type : 'date',
					dateFormat : 'yyyy-MM-dd'/*,
					convert : function(v) {
						return dm.comm.comm_ConvertStringToDate(v);
					}*/
				}]),
		items : [ {
			xtype : "textfield",
			fieldLabel : "名称",
			name : "name",
			allowBlank:false,
			width : 200
		}, {
			xtype : "textfield",
			fieldLabel : "编码",
			name : "code",
			allowBlank:false,
			width : 200
		}, /* {
			xtype : "textfield",
			fieldLabel : "类别",
			name : "type",
			width : 200
		}, */{
			xtype : "textarea",
			fieldLabel : "备注",
			name : "remark",
			width : 200
		} ],
		buttons : [ {
			text : '确定',
			listeners : {
				'click' : function() {
					if (actionType == "add") {
						// 新增
						if (win.form.getForm().isValid()) {
							win.form.getForm().doAction('submit', {
								url : "resource/roleManage/addRole",
								method : 'post',
								waitMsg : '正在加载',
								timeout : 2000,
								success : function(response) {
									dm.comm.comm_alert({
										msg : '添加用户成功'
									});
									win.close();
									Ext.getCmp("dm_roleManager_grid_1").loadData();
								},
								failure : function(response, action) {

								}
							});

						} else {
							return;
						}
					} else if (actionType == "upd") {
						// 修改
						if (win.form.getForm().isValid()) {
							win.form.getForm().doAction('submit', {
								url : "resource/roleManage/modifyRole",
								method : 'post',
								waitMsg : '正在加载',
								timeout : 2000,
								success : function(response) {
									dm.comm.comm_alert({
										msg : '修改用户成功'
									});
									win.close();
									Ext.getCmp("dm_roleManager_grid_1").loadData();
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
		}, {
			text : '取消',
			handler : function() {
				win.close();
			}
		} ]
	});

	if (actionType == "add") {
		actionTitle = '新增';
	} else if (actionType == "upd") {
		actionTitle = '修改';
		win.form.getForm().load({
			params : {
				id : id
			},
			url : "resource/roleManage/loadRole"
		});
	} else if (actionType == "info") {
		actionTitle = '查看';
		win.form.getForm().load({
			params : {
				id : id
			},
			url : "resource/roleManage/loadRole"
		});
	}

	dm.roleManager.addWin.superclass.constructor.call(this, {
		title : '新增',
		resizable : false,
		height:224,
		width : 385,
		modal : true,
		closeAction : 'destroy',
		bodyStyle : 'padding:5px;',
		closable : true,
		items : [ win.form ]
	});
};

Ext.extend(dm.roleManager.addWin, Ext.Window);
