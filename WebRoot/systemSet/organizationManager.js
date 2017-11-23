Ext.namespace('dm.organizationManager');

dm.organizationManager.main = function(){
	var main = this;
	var left = new dm.organizationManager.treePanel();
	var right = new dm.organizationManager.grid(left);
	dm.organizationManager.main.superclass.constructor.call(this, {
		closable:true,
		title:'组织机构管理',
		layout:'border',
		items:[left,right]
	});
};

Ext.extend(dm.organizationManager.main,Ext.form.FormPanel,{});

dm.organizationManager.treePanel = function() {
	var orgTree = this;
	dm.organizationManager.treePanel.superclass.constructor.call(this, {
		region:'west',
		root : new Ext.tree.AsyncTreeNode({
			id : '0',
			text : '组织机构',
			expanded : true,
			iconCls : 'icon-systemSet-enterprise'

		}),
		loader : new Ext.tree.TreeLoader({
			url : 'resource/organizationManager/orgTree?id=0',
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

Ext.extend(dm.organizationManager.treePanel,Ext.tree.TreePanel,{});

dm.organizationManager.grid = function(tree) {
	var grid = this;
	grid.orgTree = tree;
	grid.pagesize_combo = new dm.object.pagesize_combo();
	grid.pageSize = parseInt(grid.pagesize_combo.getValue());

	grid.store = new Ext.data.Store({
		proxy : new Ext.data.HttpProxy({
			url : 'resource/organizationManager/search',
			method : 'post'
		}),
		reader : new Ext.data.JsonReader({
			totalProperty : 'total',
			root : 'org'
		}, [ {
			name : 'id'
		}, {
			name : 'org_code'
		},{
			name : 'org_name'
		}, {
			name : 'org_short'
		},{
			name : 'telephone'
		}, {
			name : 'org_level'
		},{
			name : 'org_addr'
		},{
			name : 'fax'
		}, {
			name : 'tele_addr'
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
		dataIndex : "org_name", // 名称
		sortable : true,
		renderer : function(value) {
			return dm.comm.comm_getTip(value);
		}
	},{
		header : '编码',
		dataIndex : "org_code", // 编码
		sortable : true,
		renderer : function(value) {
			return dm.comm.comm_getTip(value);
		}
	},  {
		header : '简称',
		dataIndex : "org_short", // 类型
		renderer : function(value) {
			return dm.comm.comm_getTip(value);
		}
	}, {
		header : '机构地址',
		dataIndex : "org_addr", // 机构地址
		renderer : function(value) {
			return dm.comm.comm_getTip(value);
		}
	}, {
		header : '机构电话',
		dataIndex : "tele_addr", // 机构电话
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

	grid.searchTxt = Ext.create({
		xtype : 'textfield',
		width : 150,
		emptyText : '请输入组织机构名后按回车',
		enableKeyEvents : true,
		listeners : {
			specialkey : function(field, e) {
				if (e.getKey() == Ext.EventObject.ENTER) {
					grid.loadData();
				}
			}
		}
	} );
	
	dm.organizationManager.grid.superclass.constructor.call(this, {
		region:'center',
		closable : true,
		id:'dm_organizationManagerr_grid_1',
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

		}, '->', grid.searchTxt]
	});
	grid.store.on('beforeload', function() {
		this.baseParams = {
			searchName : grid.searchTxt.getValue()
		};
	});
	grid.loadData();
};

Ext.extend(dm.organizationManager.grid, Ext.grid.GridPanel, {
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

		var win = new dm.organizationManager.addWin("add", null);
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
		var win = new dm.organizationManager.addWin("upd", id);
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
		var win = new dm.organizationManager.addWin("info", id);
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
			url : 'resource/organizationManager/deleteOrganization',
			method : 'POST',
			params : {
				'id':id
			},
			success : function(resp) {
				var json = Ext.util.JSON.decode(resp.responseText);
				var flag = json.success;
				if (flag) {
					Ext.Msg.alert('提示', '删除成功');
					Ext.getCmp("dm_organizationManagerr_grid_1").loadData();
					Ext.getCmp("dm_organizationManagerr_grid_1").orgTree.getRootNode().reload();
				}else{
					Ext.Msg.alert('提示', '删除失败');
				}
			}
		});
	}

});

dm.organizationManager.addWin = function(actionType, id) {
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
					root : 'org'
				}, [ {
					name : 'org_code'
				},{
					name : 'org_name'
				}, {
					name : 'org_short'
				},{
					name : 'telephone'
				}, {
					name : 'org_level'
				},{
					name : 'org_addr'
				},{
					name : 'fax'
				}, {
					name : 'tele_addr'
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
			fieldLabel : "机构编码",
			name : "org_code",
			allowBlank:false,
			readOnly:isReadOnly,
			width : 200
		}, {
			xtype : "textfield",
			fieldLabel : "机构名称",
			name : "org_name",
			readOnly:isReadOnly,
			width : 200
		},{
			xtype : "textfield",
			fieldLabel : "机构简称",
			name : "org_short",
			readOnly:isReadOnly,
			width : 200
		}, {
			xtype:'comboxTreeOrg',
			fieldLabel : "上级组织机构树",
			name : "parent",
			hiddenName:'parent',
			readOnly:isParentReadOnly,
			hidden:isParentReadOnly,
			width : 200
		}, {
			xtype : "textfield",
			fieldLabel : "机构电话",
			name : "telephone",
			readOnly:isReadOnly,
			width : 200
		}, {
			xtype : "textfield",
			fieldLabel : "机构等级",
			name : "org_level",
			readOnly:isReadOnly,
			width : 200
		}, {
			xtype : "textfield",
			fieldLabel : "机构地址",
			name : "org_addr",
			readOnly:isReadOnly,
			width : 200
		}, {
			xtype : "textfield",
			fieldLabel : "传真",
			readOnly:isReadOnly,
			name : "fax",
			width : 200
		}, {
			xtype : "textfield",
			fieldLabel : "邮编",
			name : "tele_addr",
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
			iconCls : 'icon-delete',
			listeners : {
				'click' : function() {
					if (actionType == "add") {
						// 新增
						if (win.form.getForm().isValid()) {
							win.form.getForm().doAction('submit', {
								url : "resource/organizationManager/addOrganization",
								method : 'post',
								waitMsg : '正在加载',
								timeout : 2000,
								success : function(response) {
									dm.comm.comm_alert({
										msg : '添加组织机构成功'
									});
									win.close();
									Ext.getCmp("dm_organizationManagerr_grid_1").loadData();
									Ext.getCmp("dm_organizationManagerr_grid_1").orgTree.getRootNode().reload();
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
								url : "resource/organizationManager/modifyOrganization",
								method : 'post',
								waitMsg : '正在加载',
								timeout : 2000,
								success : function(response) {
									dm.comm.comm_alert({
										msg : '修改组织机构成功'
									});
									win.close();
									Ext.getCmp("dm_organizationManagerr_grid_1").loadData();
									Ext.getCmp("dm_organizationManagerr_grid_1").orgTree.getRootNode().reload();
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
			url : "resource/organizationManager/loadOrganization"
		});
	} else if (actionType == "info") {
		actionTitle = '查看';
		win.form.getForm().load({
			params : {
				id : id
			},
			url : "resource/organizationManager/loadOrganization"
		});
	}

	dm.organizationManager.addWin.superclass.constructor.call(this, {
		title : actionTitle,
		resizable : false,
		width:388,
		height:382,
		modal : true,
		closeAction : 'destroy',
		bodyStyle : 'padding:5px;',
		closable : true,
		items : [ win.form ]
	});
};

Ext.extend(dm.organizationManager.addWin, Ext.Window);
