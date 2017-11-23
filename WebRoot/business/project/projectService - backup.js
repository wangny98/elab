Ext.namespace('dm.project');

dm.project.GridPanel = function() {
	var gridPanel = this;
	gridPanel.searchConditions = {
	};
	// 每页显示条数下拉选择框
	gridPanel.pagesize_combo = new dm.object.pagesize_combo();
	// 每页显示的条数
	gridPanel.pageSize = parseInt(gridPanel.pagesize_combo.getValue());
	gridPanel.store = new Ext.data.Store({
				proxy : new Ext.data.HttpProxy({
							url : 'resource/ProjectService/search',
							method : 'post'
						}),
				reader : new Ext.data.JsonReader({
							totalProperty : 'total',
							root : 'project'
						}, [{
									name : 'projectId'
								}, {
									name : 'projectName'
								}, {
									name : 'projectLeader'
								},  {
									name : 'contractNumber'
								}]),
				baseParams : gridPanel.searchConditions
			});
	gridPanel.cm = new Ext.grid.ColumnModel([new Ext.grid.RowNumberer({
				width : 30,
				renderer : function(value, metadata, record, rowIndex) {
					// 分页自增长序列
					return gridPanel.store.lastOptions.params.start + 1
							+ rowIndex;
				}
			}), 
	/*{
		header : 'ID',
		dataIndex : "projectId",
		width : 40,
		renderer : function(value) {
			return dm.comm.comm_getTip(value);
		}
	},*/ {
		header : '项目名称',
		dataIndex : "projectName",
		width : 40,
		renderer : function(value) {
			return dm.comm.comm_getTip(value);
		}
	}, {
		header : '项目管理员',
		dataIndex : "projectLeader",
		width : 60,
		renderer : function(value) {
			return dm.comm.comm_getTip(value);
		}
	}, {
		header : '合同',
		dataIndex : "contractNumber",
		width : 45,
		renderer : function(value) {
			return dm.comm.comm_getTip(value);
		}
	}
	]);
	// 分页工具栏
	gridPanel.ProgressBar = new dm.object.ProgressBarPager();
	gridPanel.mybbar = new dm.object.PagingToolbar(gridPanel.pageSize,
			gridPanel.ProgressBar, gridPanel.store, gridPanel.pagesize_combo);
	// 改变每页显示条数reload数据
	gridPanel.pagesize_combo.on("select", function(comboBox) {
				gridPanel.mybbar.pageSize = parseInt(comboBox.getValue());
				gridPanel.store.load({
							params : {
								start : 0,
								limit : gridPanel.mybbar.pageSize
							},
							callback : function(records, options, success) {
								try {
									if (!success) {
										DM.comm.showGridError();
									}
								} catch (e) {
									DM.comm.ShowInternetError();
								}
							}
						});
			});
	dm.project.GridPanel.superclass.constructor.call(this, {
				autoWidth : true,
				region : 'center',
				loadMask : {
					msg : '正在加载...'
				},
				closable : true,
				// 与后台交互的数据
				store : gridPanel.store,
				// 列表
				sm : gridPanel.sm,
				// 勾选框
				columns : gridPanel.cm,
				// autoExpandColumn : 6,
				// 分页
				bbar : gridPanel.mybbar,
				// 不产横向生滚动条, 各列自动扩展自动压缩, 适用于列数比较少的情况
				viewConfig : {
					forceFit : true
				},
				tbar : [ {
					text : '新增', // 新增
					iconCls : 'icon-add',
					funcCode : '',
					handler : function() {
						gridPanel.openWin("add");
					}

				},{
					text : '修改', // 新增
					iconCls : 'icon-modify',
					funcCode : '',
					handler : function() {
						var len = gridPanel.getSelectionModel().getSelections().length;
						if (len == 0) {
							Ext.Msg.alert('提示', '请选择一条记录');
							return;
						}
						var id = gridPanel.getSelectionModel().getSelected().data.projectId;
						gridPanel.openWin("upd",id);
					}

				}, {xtype:"tbseparator"},{
					text : '删除', // 删除
					iconCls : 'icon-delete',
					funcCode : '',
					handler : function() {
						gridPanel.del();
					}
				}]
			});
	gridPanel.store.on('beforeload', function() {
				gridPanel.store.baseParams = gridPanel.searchConditions;
			});
	gridPanel.loadData();
};
Ext.extend(dm.project.GridPanel, Ext.grid.GridPanel, {
	loadData : function() {
		var gridPanel = this;
		gridPanel.store.load({
					params : {
						start : 0,
						limit : gridPanel.mybbar.pageSize
					},
					callback : function(records, options, success) {
						try {
							if (!success) {
								dm.comm.showGridError();
							}
						} catch (e) {
							dm.comm.ShowInternetError();
						}
					}
				});
	},
	openWin : function(actionType,id){
		var gridPanel = this;
		var win = new dm.project.addWin(actionType, id,gridPanel);
		win.show();
	},
	del : function() {
		var grid = this;
		var len = this.getSelectionModel().getSelections().length;
		if (len == 0) {
			Ext.Msg.alert('提示', '请选择一条记录');
			return;
		}
		var id = this.getSelectionModel().getSelected().data.projectId;
		Ext.Ajax.request({
			url : 'resource/ProjectService/remove',
			method : 'POST',
			params : {
				'id':id
			},
			success : function(resp) {
				var json = Ext.util.JSON.decode(resp.responseText);
				var flag = json.success;
				if (flag) {
					Ext.Msg.alert('提示', '删除成功');
					grid.loadData();
				}else{
					Ext.Msg.alert('提示', '删除失败');
				}
			}
		});
	},
	refreshData : function() {
	},
	clearSearchCondition : function() {
	}
});
/////////////////////    新增         ////////////////////
dm.project.addWin = function(actionType, id,gridPanel) {
	var win = this;
	var actionTitle=null;
	var hidden=true;
	if (actionType == "add") {
		actionTitle = '新增';
		hidden=false;
	} else if (actionType == "upd") {
		actionTitle = '修改';
	}
	
	win.form = new Ext.form.FormPanel({
		buttonAlign : 'center',
		baseCls : "x-plain",
		labelAlign : 'right',
		width : 360,
		reader : new Ext.data.JsonReader({
			successProperty : 'success',
			root : 'projectBean',
			idProperty:'projectId'
		}, [{
				name : 'projectId'
			}, {
				name : 'projectName'
			}, {
				name : 'projectLeader'
			}, {
				name : 'contractNumber'
			}]),
		items : [/* {
			xtype : "hidden",
			fieldLabel : "projectId",
			name : "projectId",
			allowBlank:hidden,
			hidden:hidden,
			width : 200
		},*/{
			xtype : "textfield",
			fieldLabel : "项目名称",
			name : "projectName",
			width : 200
		},  {
			xtype : "textfield",
			fieldLabel : "项目管理员",
			name : "projectLeader",
			width : 200
		},{
			xtype : "textfield",
			fieldLabel : "合同",
			name : "contractNumber",
			width : 200
		}],
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
								url : "resource/ProjectService/addProject",
								method : 'post',
								waitMsg : '正在加载',
								timeout : 2000,
								success : function(response) {
									dm.comm.comm_alert({
										msg : '添加成功'
									});
									win.close();
									gridPanel.loadData();
								},
								failure : function(response, action) {
									dm.comm.comm_alert({
										msg : '添加失败'
									});
								}
							});

						} else {
							return;
						}
					} else if (actionType == "upd") {
						// 修改
						if (win.form.getForm().isValid()) {
							win.form.getForm().doAction('submit', {
								url : "resource/ProjectService/modify",
								method : 'post',
								params : {
									projectId:id
								},
								waitMsg : '正在加载',
								timeout : 2000,
								success : function(response) {
									dm.comm.comm_alert({
										msg : '修改项目信息成功'
									});
									win.close();
									gridPanel.loadData();
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
	} else if(actionType == "upd")  {
		actionTitle = '修改';
		win.form.getForm().load({
			params : {
				id : id
			},
			success:function(form,response){
				var data = response.result.data;
				console.info("data : ",data);
			},
			failure: function(form, action) {
				console.info("Load failed", action.result.errorMessage);
		    },
			url : "resource/ProjectService/loadProject"
		});
	} 

	dm.project.addWin.superclass.constructor.call(this, {
		title : actionTitle,
		resizable : false,
		modal : true,
		width:385,
		height:157,
		closeAction : 'destroy',
		bodyStyle : 'padding:5px;',
		closable : true,
		items : [ win.form ]
	});
};

Ext.extend(dm.project.addWin, Ext.Window);