Ext.namespace('dm.classificationManager');

dm.classificationManager.main = function(){
	var main = this;
	var left = new dm.classificationManager.treePanel();
	var right = new dm.classificationManager.grid(left);
	dm.classificationManager.main.superclass.constructor.call(this, {
		closable:true,
		title:'资产分类管理',
		layout:'border',
		items:[left,right]
	});
};

Ext.extend(dm.classificationManager.main,Ext.form.FormPanel,{});

dm.classificationManager.treePanel = function() {
	var classTree = this;
	dm.classificationManager.treePanel.superclass.constructor.call(this, {
		region:'west',
		root : new Ext.tree.AsyncTreeNode({
			id : '0',
			text : '资产分类',
			expanded : true,
			iconCls : 'icon-systemSet-enterprise'

		}),
		loader : new Ext.tree.TreeLoader({
			url : 'resource/classificationManager/classTree?id=0',
			method : 'post'
		}),
		useArrows : false,
		width : 250,
		rootVisible : true,
		border : true,
		animate : true,
		autoScroll : true,
		enableDD : false
	});
};

Ext.extend(dm.classificationManager.treePanel,Ext.tree.TreePanel,{});

dm.classificationManager.grid = function(tree) {
	var grid = this;
	grid.classTree = tree;
	grid.pagesize_combo = new dm.object.pagesize_combo();
	grid.pageSize = parseInt(grid.pagesize_combo.getValue());

	grid.store = new Ext.data.Store({
		proxy : new Ext.data.HttpProxy({
			url : 'resource/classificationManager/search',
			method : 'post'
		}),
		reader : new Ext.data.JsonReader({
			totalProperty : 'total',
			root : 'cla'
		}, [ {
			name : 'id'
		}, {
			name : 'class_code'
		},{
			name : 'class_name'
		}, {
			name : 'class_short'
		}, {
			name : 'class_level'
		}, {
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
		dataIndex : "class_name", // 名称
		sortable : true,
		renderer : function(value) {
			return dm.comm.comm_getTip(value);
		}
	},{
		header : '编码',
		dataIndex : "class_code", // 编码
		sortable : true,
		renderer : function(value) {
			return dm.comm.comm_getTip(value);
		}
	},  {
		header : '简称',
		dataIndex : "class_short", // 类型
		renderer : function(value) {
			return dm.comm.comm_getTip(value);
		}
	}, {
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

	dm.classificationManager.grid.superclass.constructor.call(this, {
		region:'center',
		closable : true,
		id:'dm_classificationManager_grid_1',
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

		}, {
			xtype : 'tbspacer',
			width : 5
		}, {
			text : '修改', // 修改
			iconCls : 'icon-add',
			funcCode : '',
			handler : function() {
				grid.updWin();
			}

		}, {
			xtype : 'tbspacer',
			width : 5
		}, {
			text : '查看', // 查看
			iconCls : 'icon-add',
			funcCode : '',
			handler : function() {
				grid.infoWin();
			}

		}, {
			xtype : 'tbspacer',
			width : 5
		}, {
			text : '删除', // 删除
			iconCls : 'icon-add',
			funcCode : '',
			handler : function() {
				grid.del();
			}

		} ]
	});
	grid.store.on('beforeload', function() {
		this.baseParams = {
		};
	});
	grid.loadData();
};

Ext.extend(dm.classificationManager.grid, Ext.grid.GridPanel, {
	loadData : function() {
		var GridPanel = this;
		GridPanel.getStore().load({
			params : {
				start : 0,
				limit : GridPanel.pageSize
			},
			callback : function(records, options, success) {
				// dm.comm.showGridError();
			}
		});
	},
	addWin : function() {
		var userGird = this;

		var win = new dm.classificationManager.addWin("add", null);
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
		var win = new dm.classificationManager.addWin("upd", id);
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
		var win = new dm.classificationManager.addWin("info", id);
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
			url : 'resource/classificationManager/deleteClassification',
			method : 'POST',
			params : {
				'id':id
			},
			success : function(resp) {
				var json = Ext.util.JSON.decode(resp.responseText);
				var flag = json.success;
				if (flag) {
					Ext.Msg.alert('提示', '删除成功');
					Ext.getCmp("dm_classificationManager_grid_1").loadData();
					Ext.getCmp("dm_classificationManager_grid_1").classTree.getRootNode().reload();
				}else{
					Ext.Msg.alert('提示', '删除失败');
				}
			}
		});
	}

});

dm.classificationManager.addWin = function(actionType, id) {
	var win = this;
	var isReadOnly = false;
	var isParentReadOnly = false;
	if (actionType == "add") {
		actionTitle = '新增';
	} else if (actionType == "upd") {
		isParentReadOnly = true;
	}else if (actionType == "info") {
		isReadOnly = true;
		isParentReadOnly = true;
	}
	
	win.form = new Ext.form.FormPanel({
		buttonAlign : 'center',
		baseCls : "x-plain",
		labelAlign : 'right',
		width : 360,
		reader : new Ext.data.JsonReader({
					successProperty : 'success',
					root : 'cla'
				}, [ {
					name : 'class_code'
				},{
					name : 'class_name'
				}, {
					name : 'class_short'
				}, {
					name : 'class_level'
				}, {
					name : 'remark'
				},{
					name : 'creator_id'
				}, {
					name : 'creator_name'
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
			fieldLabel : "分类编码",
			name : "class_code",
			readOnly:isReadOnly,
			width : 200
		}, {
			xtype : "textfield",
			fieldLabel : "分类名称",
			name : "class_name",
			readOnly:isReadOnly,
			width : 200
		},{
			xtype : "textfield",
			fieldLabel : "分类简称",
			name : "class_short",
			readOnly:isReadOnly,
			width : 200
		}, {
			xtype:'comboxTreeClass',
			fieldLabel : "上级资产分类树",
			name : "parent",
			hiddenName:'parent',
			readOnly:isParentReadOnly,
			hidden:isParentReadOnly,
			width : 200
		}, {
			xtype : "textfield",
			fieldLabel : "分类等级",
			name : "class_level",
			readOnly:isReadOnly,
			width : 200
		}, {
			xtype : "textarea",
			fieldLabel : "备注",
			readOnly:isReadOnly,
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
								url : "resource/classificationManager/addClassification",
								method : 'post',
								waitMsg : '正在加载',
								timeout : 2000,
								success : function(response) {
									Ext.Msg.alert('提示窗口','添加用户成功！');
									win.close();
									Ext.getCmp("dm_classificationManager_grid_1").loadData();
									Ext.getCmp("dm_classificationManager_grid_1").classTree.getRootNode().reload();
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
								url : "resource/classificationManager/modifyClassification",
								method : 'post',
								waitMsg : '正在加载',
								timeout : 2000,
								success : function(response) {
									Ext.Msg.alert('提示窗口','修改用户成功！');
									win.close();
									Ext.getCmp("dm_classificationManager_grid_1").loadData();
									Ext.getCmp("dm_classificationManager_grid_1").classTree.getRootNode().reload();
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
			url : "resource/classificationManager/loadClassification"
		});
	} else if (actionType == "info") {
		actionTitle = '查看';
		win.form.getForm().load({
			params : {
				id : id
			},
			url : "resource/classificationManager/loadClassification"
		});
	}

	dm.classificationManager.addWin.superclass.constructor.call(this, {
		title : actionTitle,
		resizable : false,
		modal : true,
		width: 385,
		closeAction : 'destroy',
		bodyStyle : 'padding:5px;',
		closable : true,
		items : [ win.form ]
	});
};

Ext.extend(dm.classificationManager.addWin, Ext.Window);