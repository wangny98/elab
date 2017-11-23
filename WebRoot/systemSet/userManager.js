Ext.namespace('dm.userManager');

dm.userManager.grid = function() {
	var grid = this;

	grid.pagesize_combo = new dm.object.pagesize_combo();
	grid.pageSize = parseInt(grid.pagesize_combo.getValue());

	grid.store = new Ext.data.Store({
		proxy : new Ext.data.HttpProxy({
			url : 'resource/userManage/search',
			method : 'post'
		}),
		reader : new Ext.data.JsonReader({
			totalProperty : 'total',
			root : 'users'
		}, [ {
			name : 'id'
		}, {
			name : 'fullname'
		}, {
			name : 'sex'
		}, {
			name : 'account'
		}, {
			name : 'alias'
		}, {
			name : 'departmentName'
		}, {
			name : 'state'
		}, {
			name : 'remark'
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
		header : '姓名',
		dataIndex : "fullname", // 姓名
		sortable : true,
		renderer : function(value) {
			return dm.comm.comm_getTip(value);
		}
	}, /*{
		header : '性别',
		dataIndex : "sex", // 性别
		renderer : function(value) {
			if ('1' == value) {
				return dm.comm.comm_getTip("男");
			}
			return dm.comm.comm_getTip("女");
		}
	}, */{
		header : '账号',
		dataIndex : "account", // 账号
		renderer : function(value) {
			return dm.comm.comm_getTip(value);
		}
	}, {
		header : '人员代码',
		dataIndex : "alias", // 别名
		renderer : function(value) {
			return dm.comm.comm_getTip(value);
		}
	}, {
		header : '所属部门',
		dataIndex : "departmentName", // 所属部门
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

	dm.userManager.grid.superclass.constructor.call(this, {
		title : '用户管理',
		closable : true,
		id:'dm_userManager_grid_1',
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

		},{xtype:"tbseparator"},{
			text : '修改', // 修改
			iconCls : 'icon-modify',
			funcCode : '',
			handler : function() {
				grid.updWin();
			}

		},{xtype:"tbseparator"},{
			text : '查看', // 查看
			iconCls : 'icon-info',
			funcCode : '',
			handler : function() {
				grid.infoWin();
			}

		},{xtype:"tbseparator"},{
			text : '删除', // 删除
			iconCls : 'icon-delete',
			funcCode : '',
			handler : function() {
				grid.del();
			}

		},{xtype:"tbseparator"},{
			text : '分配角色', // 删除
			iconCls : 'icon-assignRole',
			funcCode : '',
			handler : function() {
				grid.assignWin();
			}

		},{xtype:"tbseparator"},{
			text : '分配权限', // 删除
			iconCls : 'icon-assignRole',
			funcCode : '',
			handler : function() {
				grid.assignFunc();
			}

		}, '->', {
			xtype : 'textfield',
			width : 150,
			id : 'findUserByName',
			emptyText : '请输入用户名后按回车',
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
			searchName : Ext.getCmp('findUserByName').getValue()
		};
	});
	grid.loadData();
};

Ext.extend(dm.userManager.grid, Ext.grid.GridPanel, {
	assignWin : function(){
		var userGird = this;
		var len = this.getSelectionModel().getSelections().length;
		if (len == 0) {
			Ext.Msg.alert('提示', '请选择一条记录');
			return;
		}
		var id = this.getSelectionModel().getSelected().data.id;
		var win = new dm.userManager.assignRoleWin(id);
		win.show();
	},
	assignFunc:function(){
		var userGird = this;
		var len = this.getSelectionModel().getSelections().length;
		if (len == 0) {
			Ext.Msg.alert('提示', '请选择一条记录');
			return;
		}
		var id = this.getSelectionModel().getSelected().data.id;
		var win = new dm.userManager.assignFuncWin(id);
		win.show();
	},
	loadData : function() {
		var userGrid = this;
		userGrid.getStore().load({
			params : {
				start : 0,
				limit : userGrid.pageSize
			},
			callback : function(records, options, success) {
				// dm.comm.showGridError();
			}
		});
	},
	addWin : function() {
		var userGird = this;

		var win = new dm.userManager.addWin("add", null);
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
		var win = new dm.userManager.addWin("upd", id);
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
		var win = new dm.userManager.addWin("info", id);
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
			url : 'resource/userManage/deleteUser',
			method : 'POST',
			params : {
				'id':id
			},
			success : function(resp) {
				var json = Ext.util.JSON.decode(resp.responseText);
				var flag = json.success;
				if (flag) {
					Ext.Msg.alert('提示', '删除成功');
					Ext.getCmp("dm_userManager_grid_1").loadData();
				}else{
					Ext.Msg.alert('提示', '删除失败');
				}
			}
		});
	}

});

dm.userManager.addWin = function(actionType, id) {
	var win = this;
	var actionTitle=null;
	var hidden=true;
	if (actionType == "add") {
		actionTitle = '新增';
		hidden=false;
	} else if (actionType == "upd") {
		actionTitle = '修改';
	} else if (actionType == "info") {
		actionTitle = '查看';
	}
	
	win.form = new Ext.form.FormPanel({
		buttonAlign : 'center',
		baseCls : "x-plain",
		labelAlign : 'right',
		width : 360,
		reader : new Ext.data.JsonReader({
			successProperty : 'success',
			root : 'user'
		}, [{
					name : 'account'
				}, {
					name : 'fullname'
				}, {
					name : 'alias'
				}, {
					name : 'sex'
				}, {
					name : 'position'
				},  {
					name : 'department_id'
				},  {
					name : 'departmentName'
				}, {
					name : 'org_id'
				},  {
					name : 'org_name'
				}, {
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
			fieldLabel : "帐号",
			name : "account",
			allowBlank:false,
			width : 200
		},  {
			xtype : "textfield",
			fieldLabel : "密码",
			name : "password",
			minLength:6,
			allowBlank:hidden,
			hidden:hidden,
			width : 200
		},{
			xtype : "textfield",
			fieldLabel : "人员姓名",
			name : "fullname",
			width : 200
		}, {
			xtype : "textfield",
			fieldLabel : "人员代码",
			name : "alias",
			width : 200
		}, {
			xtype : "comboxTreeOrg",
			fieldLabel : "所属部门",
			allowBlank:false,
			name : "department_id",
			hiddenName : "department_id",
			width : 200
		}/*, {
			xtype : "textarea",
			fieldLabel : "备注",
			name : "remark",
			width : 200
		} */],
		listeners:{
			'render':function(){
				
			}
		},
		buttons : [ {
			text : '确定',
			listeners : {
				'click' : function() {
					if (actionType == "add") {
						// 新增
						if (win.form.getForm().isValid()) {
							win.form.getForm().doAction('submit', {
								url : "resource/userManage/addUser",
								method : 'post',
								waitMsg : '正在加载',
								timeout : 2000,
								success : function(response) {
									dm.comm.comm_alert({
										msg : '添加用户成功'
									});
									win.close();
									Ext.getCmp("dm_userManager_grid_1").loadData();
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
								url : "resource/userManage/modifyUser",
								method : 'post',
								params : {
									id:id
								},
								waitMsg : '正在加载',
								timeout : 2000,
								success : function(response) {
									dm.comm.comm_alert({
										msg : '修改用户成功'
									});
									win.close();
									Ext.getCmp("dm_userManager_grid_1").loadData();
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
			success:function(form,response){
				var data = response.result.data;
				//console.info("data : ",data);
				var depart = win.form.getForm().findField('department_id');
				//console.info("depart : ",depart);
				depart.setValue({id:data.department_id,text:data.departmentName});
			},
			url : "resource/userManage/loadUser"
		});
	} else if (actionType == "info") {
		actionTitle = '查看';
		win.form.getForm().load({
			params : {
				id : id
			},
			success:function(form,response){
				var data = response.result.data;
				var depart = win.form.getForm().findField('department_id');
				depart.setValue({id:data.department_id,text:data.departmentName});
			},
			url : "resource/userManage/loadUser"
		});
	}

	dm.userManager.addWin.superclass.constructor.call(this, {
		title : actionTitle,
		resizable : false,
		modal : true,
		width:385,
		height:337,
		closeAction : 'destroy',
		bodyStyle : 'padding:5px;',
		closable : true,
		items : [ win.form ]
	});
};

Ext.extend(dm.userManager.addWin, Ext.Window);

/////////////////////    分配角色         ////////////////////
dm.userManager.assignRoleWin = function(userId){
	var assign = this;
	assign.grid = new dm.userManager.assignRoleGrid(userId);
	dm.userManager.assignRoleWin.superclass.constructor.call(this, {
		title : '分配角色',
		resizable : false,
		modal : true,
		width : 623,
		height:476,
		closeAction : 'destroy',
		bodyStyle : 'padding:5px;',
		closable : true,
		items:[assign.grid],
		buttonAlign : 'center',
		buttons:[ {
			text : '确定',
			listeners : {
				'click' : function() {
					
					var lines=assign.grid.getSelectionModel().getSelections();
						if (lines.length == 0) {
							Ext.Msg.alert('提示', '请选择角色进行分配!');
							return;
						}
						var ids = "";
						for (i = 0; i < lines.length; i++) {
							id = lines[i].get('id');
							if (i == 0)
								ids = ids + id;
							else
								ids = ids + "," + id;
						}
					Ext.Ajax.request({
						url:'resource/userManage/assignRoleUser',
						method : 'POST',
						params:{
							userId:userId,
							roleIds:ids
						},
						waitMsg : '请稍等',
						timeout : 2000,
						success : function(response) {
							var json = Ext.util.JSON.decode(response.responseText);
							var flag = json.success;
							if (flag) {
								dm.comm.comm_alert({
										msg : '分配成功'
									});
								assign.close();
							}else{
								dm.comm.comm_alert({
										msg : '分配失败'
									});
							}
						},
						failure : function(response, action) {
						}
					});
				}
			}
		}, {
			text : '取消',
			handler : function() {
				assign.close();
			}
		} ]
	});
};
Ext.extend(dm.userManager.assignRoleWin, Ext.Window);

dm.userManager.assignRoleGrid = function(userId){
	var grid = this;
	grid.store = new Ext.data.Store({
		proxy : new Ext.data.HttpProxy({
			url : 'resource/roleManage/getAllRole',
			method : 'post'
		}),
		reader : new Ext.data.JsonReader({
			root : 'roles'
		}, [ {
			name : 'checked'
		},{
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
			userId : userId
		}
	});
	grid.csm= new Ext.grid.CheckboxSelectionModel();
	grid.sm = grid.csm;
	//console.info("grid.sm : ",grid.sm);
	grid.cm = new Ext.grid.ColumnModel([ /*new Ext.grid.RowNumberer({
		renderer : function(value, metadata, record, rowIndex) {
			return grid.store.lastOptions.params.start + 1 + rowIndex;
		}
	}),*/grid.sm,{
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
	},  {
		header : '类型',
		dataIndex : "type", // 类型
		renderer : function(value) {
			return dm.comm.comm_getTip(value);
		}
	}, {
		header : '是否分配',
		dataIndex : "checked", // 备注号
		renderer : function(value) {
			var str ='<font style="color:green">'+'未分配'+'</font>';
			if(value==1){
				str ='<font style="color:red">'+'已分配'+'</font>';
			}
			return str;
		}
	}, {
		header : '备注',
		dataIndex : "remark", // 备注号
		renderer : function(value) {
			return dm.comm.comm_getTip(value);
		}
	}]);
	dm.userManager.assignRoleGrid.superclass.constructor.call(this, {
		store : grid.store,
		width:600,
		height:400,
		columns : grid.cm
	});
	grid.getStore().load({
		callback : function(o, response, success) {
			var length = grid.getStore().getCount();
			var checkRecords=[];
			
			for(var i=0;i<length;i++){
				var record =grid.getStore().getAt(i);
				if(record.data.checked==1){
					checkRecords.push(record);
				}
			}
			grid.csm.selectRecords(checkRecords);
			grid.getView().refresh();
		}
	});
	
};

Ext.extend(dm.userManager.assignRoleGrid, Ext.grid.GridPanel);

//////////////////////////////////////////////////////////////
dm.userManager.assignFuncWin = function(userId){
	var assign= this;
	assign.grid = new dm.userManager.assignFunc(userId);
	dm.userManager.assignFuncWin.superclass.constructor.call(this, {
		title : '分配权限',
		resizable : false,
		modal : true,
		width : 623,
		height:476,
		closeAction : 'destroy',
		bodyStyle : 'padding:5px;',
		closable : true,
		items:[assign.grid],
		buttonAlign : 'center',
		buttons:[ {
			text : '确定',
			listeners : {
				'click' : function() {
					
					var lines=assign.grid.getSelectionModel().getSelections();
						if (lines.length == 0) {
							Ext.Msg.alert('提示', '请选择标段进行分配!');
							return;
						}
						var ids = "";
						for (i = 0; i < lines.length; i++) {
							id = lines[i].get('sectionId');
							if (i == 0)
								ids = ids + id;
							else
								ids = ids + "," + id;
						}
					Ext.Ajax.request({
						url:'resource/SectionService/modifyUserACL',
						method : 'POST',
						params:{
							userId:userId,
							codes:ids
						},
						waitMsg : '请稍等',
						timeout : 2000,
						success : function(response) {
							var json = Ext.util.JSON.decode(response.responseText);
							var flag = json.success;
							if (flag) {
								dm.comm.comm_alert({
										msg : '分配成功'
									});
								assign.close();
							}else{
								dm.comm.comm_alert({
										msg : '分配失败'
									});
							}
						},
						failure : function(response, action) {
						}
					});
				}
			}
		}, {
			text : '取消',
			handler : function() {
				assign.close();
			}
		} ]
	});
};
Ext.extend(dm.userManager.assignFuncWin, Ext.Window);

dm.userManager.assignFunc = function(userId){
	var grid = this;
	grid.store = new Ext.data.Store({
		proxy : new Ext.data.HttpProxy({
			url : 'resource/SectionService/getUserACL',
			method : 'post'
		}),
		reader : new Ext.data.JsonReader({
			root : 'list'
		}, [ {
			name : 'checked'
		},{
			name : 'sectionId'
		}, {
			name : 'sectionName'
		},{
			name : 'sectionLeader'
		}, {
			name : 'projectName'
		},{
			name : 'projectId'
		},{
			name : 'contractNumber'
		} ]),
		baseParams : {
			userId : userId
		}
	});
	grid.csm= new Ext.grid.CheckboxSelectionModel();
	/*grid.csm.on('rowdeselect',function(csm,rowIndex,record){
		grid.csm.deselectRow(rowIndex);
	});*/
	
	grid.sm = grid.csm;
	//console.info("grid.sm : ",grid.sm);
	grid.cm = new Ext.grid.ColumnModel([ /*new Ext.grid.RowNumberer({
		renderer : function(value, metadata, record, rowIndex) {
			return grid.store.lastOptions.params.start + 1 + rowIndex;
		}
	}),*/grid.sm,{
		header : '项目名称',
		dataIndex : "projectName", // 名称
		sortable : true,
		renderer : function(value) {
			return dm.comm.comm_getTip(value);
		}
	},{
		header : '标段名称',
		dataIndex : "sectionName", // 编码
		sortable : true,
		renderer : function(value) {
			return dm.comm.comm_getTip(value);
		}
	},  {
		header : '标段负责人',
		dataIndex : "sectionLeader", // 类型
		renderer : function(value) {
			return dm.comm.comm_getTip(value);
		}
	}, {
		header : '是否分配',
		dataIndex : "checked", // 备注号
		renderer : function(value) {
			var str ='<font style="color:green">'+'未分配'+'</font>';
			if(value==1){
				str ='<font style="color:red">'+'已分配'+'</font>';
			}
			return str;
		}
	}]);
	dm.userManager.assignFunc.superclass.constructor.call(this, {
		store : grid.store,
		width:600,
		sm:grid.csm,
		height:400,
		columns : grid.cm
	});
	grid.getStore().load({
		callback : function(o, response, success) {
			var length = grid.getStore().getCount();
			var checkRecords=[];
			var checkIndex = [];
			for(var i=0;i<length;i++){
				var record =grid.getStore().getAt(i);
				if(record.data.checked==1){
					checkRecords.push(record);
					checkIndex.push(i);
				}
			}
			//grid.csm.selectRecords(checkRecords);
			grid.csm.selectRows(checkIndex);
			//grid.getView().refresh();
		}
	});
	/*grid.on('render',function(){
		console.info("grid.getStore() : ",grid.getStore());
		var length = grid.getStore().getCount();
		var checkRecords=[];
		
		for(var i=0;i<length;i++){
			var record =grid.getStore().getAt(i);
			console.info("record : ",record);
			if(record.data.checked==1){
				checkRecords.push(jsonData[i]);
			}
		}
		console.info("checkRecords : ",checkRecords);
		grid.csm.selectRecords(checkRecords);
		grid.getView().refresh();
	});*/
};

Ext.extend(dm.userManager.assignFunc, Ext.grid.GridPanel);