Ext.namespace('dm.pileEquip');

dm.pileEquip.GridPanel = function() {
	var gridPanel = this;
	var grid = gridPanel;
	gridPanel.searchConditions = {
	};
	// 每页显示条数下拉选择框
	gridPanel.pagesize_combo = new dm.object.pagesize_combo();
	// 每页显示的条数
	gridPanel.pageSize = parseInt(gridPanel.pagesize_combo.getValue());
	gridPanel.store = new Ext.data.Store({
				proxy : new Ext.data.HttpProxy({
							url : 'resource/PileEquipService/search',
							method : 'post'
						}),
				reader : new Ext.data.JsonReader({
							totalProperty : 'total',
							root : 'list'
						}, [{
									name : 'equipmentId'
								},{
									name : 'equipmentName'
								}, {
									name : 'equipmentCode'
								}, {
									name : 'equipmentType'
								}, {
									name : 'equipmentStatus'
								}, {
									name : 'projectStatus'
								}, {
									name : 'supervisionStatus'
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
		dataIndex : "pileEquipId",
		width : 40,
		renderer : function(value) {
			return dm.comm.comm_getTip(value);
		}
	},*/ {
		header : '设备名称',
		dataIndex : "equipmentName",
		width : 40,
		renderer : function(value) {
			return dm.comm.comm_getTip(value);
		}
	}, {
		header : '设备编码',
		dataIndex : "equipmentCode",
		width : 60,
		renderer : function(value) {
			return dm.comm.comm_getTip(value);
		}
	}, {
		header : '设备类型',
		dataIndex : "equipmentType",
		width : 45,
		renderer : function(value) {
			return dm.comm.comm_getTip(value);
		}
	}, {
		header : '设备状态',
		dataIndex : "equipmentStatus",
		width : 45,
		renderer : function(value) {
			return dm.comm.comm_getTip(value);
		}
	}, {
		header : '项目状态',
		dataIndex : "projectStatus",
		width : 45,
		renderer : function(value) {
			return dm.comm.comm_getTip(value);
		}
	}, {
		header : '监控状态',
		dataIndex : "supervisionStatus",
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
	dm.pileEquip.GridPanel.superclass.constructor.call(this, {
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
				tbar:[ 
					{text : '新增',iconCls : 'icon-add',funcCode : '',handler : function() {grid.addWin();}},
						{xtype:"tbseparator"},
					{text : '修改',iconCls : 'icon-modify',funcCode : '',handler : function() {grid.updWin();}},
						{xtype:"tbseparator"},
					{text : '查看',iconCls : 'icon-info',funcCode : '',handler : function() {grid.infoWin();}},
						{xtype:"tbseparator"},
					{text : '删除',iconCls : 'icon-delete',funcCode : '',handler : function() {grid.del();}}
				],
				// 不产横向生滚动条, 各列自动扩展自动压缩, 适用于列数比较少的情况
				viewConfig : {
					forceFit : true,
					getRowClass : function(record,rowIndex,rowParams,store){
		                   //数据显示红色
		                   if(record.data.equipmentStatus){
		                	   switch(record.data.equipmentStatus){
		                	   	case '在线':return 'x-grid-record-red';
		                	   }
		                	   
		                       /*return 'x-grid-record-red';
		                       return '';*/
		                   }    
		              }
				}
			});
	gridPanel.store.on('beforeload', function() {
				gridPanel.store.baseParams = gridPanel.searchConditions;
			});
	gridPanel.loadData();
};
Ext.extend(dm.pileEquip.GridPanel, Ext.grid.GridPanel, {
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
	refreshData : function() {
	},
	clearSearchCondition : function() {
	},
	
	addWin : function() {
		var gird = this;
		var win = new dm.pileEquip.addWin("add", null,gird);
		win.show();
	},
	updWin : function() {
		var gird = this;
		var len = this.getSelectionModel().getSelections().length;
		if (len == 0) {
			Ext.Msg.alert('提示', '请选择一条记录');
			return;
		}
		var id = this.getSelectionModel().getSelected().data.equipmentId;
		var win = new dm.pileEquip.addWin("upd", id,gird);
		win.show();
	},
	infoWin : function() {
		var userGird = this;
		var len = this.getSelectionModel().getSelections().length;
		if (len == 0) {
			Ext.Msg.alert('提示', '请选择一条记录');
			return;
		}
		var id = this.getSelectionModel().getSelected().data.equipmentId;
		var win = new dm.pileEquip.addWin("info", id);
		win.show();
	},
	del : function() {
		var gird = this;
		var len = this.getSelectionModel().getSelections().length;
		if (len == 0) {
			Ext.Msg.alert('提示', '请选择一条记录');
			return;
		}
		var id = this.getSelectionModel().getSelected().data.equipmentId;
		Ext.Ajax.request({
			url : 'resource/PileEquipService/del',
			method : 'POST',
			params : {
				'id':id
			},
			success : function(resp) {
				var json = Ext.util.JSON.decode(resp.responseText);
				var flag = json.success;
				if (flag) {
					Ext.Msg.alert('提示', '删除成功');
					gird.loadData();
				}else{
					Ext.Msg.alert('提示', '删除失败');
				}
			}
		});
	}
});

dm.pileEquip.addWin = function(actionType, id,gridPanel) {
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
			root : 'bean',
			idProperty:'equipmentId'
		}, [{name : 'equipmentId'},{name : 'equipmentName'},{name : 'equipmentCode'},{name : 'sectionId'}]),
		items : [ 
			{xtype : "textfield",fieldLabel : "设备名称",name : "equipmentName",allowBlank:false,width : 200}, 
			{xtype : "textfield",fieldLabel : "设备编码",name : "equipmentCode",allowBlank:false,width : 200},
			new Ext.form.ComboBox({
				fieldLabel:'所属标段',
		        store: new Ext.data.Store({   
		        	autoLoad: true,
		            proxy: new Ext.data.HttpProxy({ url: "resource/SectionService/searchAll",method:'post'}),   
		            reader: new Ext.data.JsonReader({ 
		            	totalProperty: "totalProperty", 
		            	root: "bean", 
		            	fields: [{ name: 'sectionId' }, { name: 'sectionName'}]
		            })
		        }),
		        name : "sectionId",
		        hiddenName : "sectionId",
		        loadingText: 'loading...',   
		        emptyText: "--请选择--",   
		        triggerAction: "all",
		        mode: "remote",   
		        valueField: "sectionId",   
		        displayField: "sectionName",   
		        selectOnFocus: true
		    })
			/*{xtype : "textfield",fieldLabel : "所属标段",name : "sectionId",allowBlank:false,width : 200}*/
		],
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
							
							console.log(win.form.getForm().getValues());
							win.form.getForm().doAction('submit', {
								url : "resource/PileEquipService/add",
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

								}
							});

						} else {
							return;
						}
					} else if (actionType == "upd") {
						// 修改
						if (win.form.getForm().isValid()) {
							win.form.getForm().doAction('submit', {
								url : "resource/PileEquipService/modify",
								method : 'post',
								params : {
									id:id
								},
								waitMsg : '正在加载',
								timeout : 2000,
								success : function(response) {
									dm.comm.comm_alert({
										msg : '修改成功'
									});
									win.close();
									gridPanel.loadData();
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
			},
			url : "resource/PileEquipService/load"
		});
	} else if (actionType == "info") {
		actionTitle = '查看';
		win.form.getForm().load({
			params : {
				id : id
			},
			success:function(form,response){
				var data = response.result.data;
			},
			url : "resource/PileEquipService/load"
		});
	}

	dm.pileEquip.addWin.superclass.constructor.call(this, {
		title : actionTitle,
		resizable : false,
		modal : true,
		width:385,
		height:207,
		closeAction : 'destroy',
		bodyStyle : 'padding:5px;',
		closable : true,
		items : [ win.form ]
	});
};

Ext.extend(dm.pileEquip.addWin, Ext.Window);
