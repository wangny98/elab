Ext.namespace('dm.sampleDispose');

dm.sampleDispose.grid = function() {
	var grid = this;

	grid.pagesize_combo = new dm.object.pagesize_combo();
	grid.pageSize = parseInt(grid.pagesize_combo.getValue());

	grid.store = new Ext.data.Store({
		proxy : new Ext.data.HttpProxy({
			url : 'resource/sampleRegister/searchDemo2',
			method : 'post'
		}),
		reader: new Ext.data.ArrayReader({totalProperty:'total', root:'list'}, [  //--使用数组解析器解析数组
		    { name: 'small.id',mapping:'id'},                                                                         
			{ name: 'small.no',mapping:'no'}, 
			{ name: 'small.submit_no',mapping:'submit_no'}, 
			{ name: 'small.basic_id',mapping:'basic_id'},    
			{ name: 'small.name',mapping:'name'},
			{ name: 'small.total',mapping:'total'},
			{ name: 'small.requirement',mapping:'requirement'},
			{ name: 'small.memo',mapping:'memo' },
			{ name: 'small.status',mapping:'status' },
			{ name: 'small.handletype',mapping:'handletype' }
		]),
		baseParams : {
			searchName : ''
		}
	});

	var sm = new Ext.grid.CheckboxSelectionModel();
	grid.cm = new Ext.grid.ColumnModel([ sm, new Ext.grid.RowNumberer({
		renderer : function(value, metadata, record, rowIndex) {
			return grid.store.lastOptions.params.start + 1 + rowIndex;
		}
	}), {
		header : '样品编号',
		dataIndex : "small.submit_no",
		sortable : true,
		renderer : function(value) {
			return dm.comm.comm_getTip(value);
		}
	}, {
		header : '小样编号',
		dataIndex : "small.no",
		sortable : true,
		renderer : function(value) {
			return dm.comm.comm_getTip(value);
		}
	}, {
		header : '小样名称',
		dataIndex : "small.name",
		sortable : true,
		renderer : function(value) {
			return dm.comm.comm_getTip(value);
		}
	}, {
		header : '数/重量',
		dataIndex : "small.total",
		renderer : function(value) {
			return dm.comm.comm_getTip(value);
		}
	}, {
		header : '具体检验检疫要求',
		dataIndex : "small.requirement",
		renderer : function(value) {
			return dm.comm.comm_getTip(value);
		}
	}, {
		header : '备注',
		dataIndex : "small.memo",
		renderer : function(value) {
			return dm.comm.comm_getTip(value);
		}
	}, {
		header : '状态',
		dataIndex : "small.status",
		renderer : function(value) {
			if(value == 0){
				value = '未鉴定';
			}else if(value == 1){
				value = '已鉴定';
			}else if(value == 2){
				value = '已复核';
			}else if(value == 3){
				value = '已签发';
			}
			return dm.comm.comm_getTip(value);
		}
	}, {
		header : '处理状态',
		dataIndex : "small.handletype",
		renderer : function(value) {
			if(value == 0){
				value = '未处理';
			}else if(value == 1){
				value = '销毁';
			}else if(value == 2){
				value = '制作样本';
			}else if(value == 3){
				value = '留样';
			}
			return dm.comm.comm_getTip(value);
		}
	} ]);

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
	dm.sampleDispose.grid.superclass.constructor.call(this, {
		title : '样本处理',
		closable : true,
		viewConfig : {
			forceFit : true
		// 不产横向生滚动条, 各列自动扩展自动压缩, 适用于列数比较少的情况
		},
		store : grid.store,
		sm : sm,
		columns : grid.cm,
		bbar : grid.ppBar,
		tbar : [ {
			text : '销毁', 
			iconCls : 'icon-info',
			funcCode : '',
			handler : function() {
				grid.destroyWin();
			}
		}, {
			xtype : "tbseparator"
		}, {
			text : '制成标本', 
			iconCls : 'icon-cancel',
			funcCode : '',
			handler : function() {
				grid.sampleWin();
			}
		}, {
			xtype : "tbseparator"
		}, {
			text : '留样', 
			iconCls : 'icon-cancel',
			funcCode : '',
			handler : function() {
				grid.reserveWin();
			}
		}, {
			xtype : "tbseparator"
		}, '->', {
			xtype : "textfield",
			emptyText : '小样单编号     按回车查询',
			width : 150,
			id : 'n',
			listeners : {
				specialkey : function(textfield, e) {
					if (e.getKey() == Ext.EventObject.ENTER) {
						var n = $("#n").attr("value");
						grid.store.load({
							params : {
								start : 0,
								limit : grid.pageSize,
								no : n
							}
						});
					}
				}
			}
		}, {
			xtype : "tbseparator"
		}, {
			text : '高级查询',
			iconCls : 'icon-search',
			funcCode : '',
			handler : function() {
				grid.searchWin();
			}
		} ]
	});
	grid.store.on('beforeload', function() {
		this.baseParams = {
			searchName : Ext.getCmp('n').getValue(),
			demo_status: 3
		};
	});
	grid.loadData();
};

Ext.extend(dm.sampleDispose.grid, Ext.grid.GridPanel, {
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
	destroyWin : function() {
		var sampleGrid = this;
		
		var record = sampleGrid.getSelectionModel().getSelections();
		if (record.length == 0) {
			Ext.Msg.alert('提示窗口', '请先选择您要操作的行！');
			return;
		} else {
			var ids = '';
			for(var i = 0; i < record.length; i ++){
				var type = record[i].get('small.handletype');
				if(ids != ''){
					ids += ',';
				}
				ids += record[i].get("small.id");
			}
			var win = new dm.sampleDispose.destroyWin(ids, record[0].get('small.handletype'), sampleGrid);
			win.show();
		}
	},
	sampleWin : function() {
		var sampleGrid = this;
		
		
	},
	reserveWin : function() {
		var sampleGrid = this;
		
		var record = sampleGrid.getSelectionModel().getSelections();
		if (record.length == 0) {
			Ext.Msg.alert('提示窗口', '请先选择您要操作的行！');
			return;
		} else {
			var ids = '';
			for(var i = 0; i < record.length; i ++){
				if(ids != ''){
					ids += ',';
				}
				ids += record[i].get("small.id");
			}
			var win = new dm.sampleDispose.reserveWin(ids, record[0].get('small.handletype'),sampleGrid);
			win.show();
		}
	},
	searchWin : function() {
		var grid = this;

		var win = new dm.sampleDispose.searchWin(this);
		win.show();
	}
});

dm.sampleDispose.searchWin = function(sampleGrid) {
	var win = this;

	var goods_type_store = new Ext.data.Store({
		proxy : new Ext.data.HttpProxy({
			url : 'resource/sampleRegister/getType',
			method : 'post'
		}),
		reader : new Ext.data.JsonReader({
			root : 'list'
		}, [ {
			name : 'attr_key'
		}, {
			name : 'attr_value'
		} ])
	});
	goods_type_store.load({
		params : {
			type : '货物类型'
		}
	});
	
	win.form = new Ext.form.FormPanel({
		width : 820,
		height : 250,
		collapsible : false,
		autoScroll : true,
		frame : true,
		renderTo : Ext.getBody(),
		style : 'margin-left:auto;margin-right:auto;width:500px;margin-top:8px;',
		labelAlign : 'right',
		buttonAlign : 'center',
		items : [{
			xtype : 'fieldset',
			title : '',
			autoHeight : true,
			items : [ {
				layout : 'form',
				autoHeight : true,
				xtype : 'panel',
				title : '',
				items : [ {
					layout : 'column',
					defaults : {
						columnWidth : '.33'
					},
					items : [ {
						layout : 'form',
						columnWidth : '.45',
						items : {
							xtype : "textfield",
							fieldLabel : "送样单位",
							id: 'submit_depart',
							name : "sample.submit_depart",
							width : 200
						}
					}, {
						columnWidth : '.55',
						layout : 'form',
						labelWidth : 150,
						items : {
							xtype : "textfield",
							fieldLabel : "送样人",
							id: 'submitter',
							name : "sample.submitter",
							width : 200
						}
					} ]
				} ]
			}, {
				layout : 'form',
				autoHeight : true,
				xtype : 'panel',
				title : '',
				items : [ {
					layout : 'column',
					defaults : {
						columnWidth : '.33'
					},
					items : [ {
						layout : 'form',
						columnWidth : '.45',
						items : {
							xtype : "textfield",
							fieldLabel : "报检号",
							id: 'verify_no',
							name : "sample.verify_no",
							width : 200
						}
					}, {
						columnWidth : '.55',
						layout : 'form',
						labelWidth : 150,
						items : {
							xtype : "datefield",
							format : 'Y-m-d',
							editable : false,
							fieldLabel : '送样日期',
							id: 'submit_date',
							name : "sample.submit_date",
							width : 200
						}
					} ]
				} ]
			}, {
				layout : 'form',
				autoHeight : true,
				xtype : 'panel',
				title : '',
				items : [ {
					layout : 'column',
					defaults : {
						columnWidth : '.33'
					},
					items : [ {
						layout : 'form',
						columnWidth : '.45',
						items : {
							xtype : "textfield",
							fieldLabel : "货物名称",
							id: 'goods_name',
							name : "sample.goods_name",
							width : 200
						}
					}, {
						columnWidth : '.55',
						layout : 'form',
						labelWidth : 150,
						items : {
							xtype:"combo",
				 			fieldLabel:"货物类型",
				 			id: 'goodtype',
				 			name:"sample.goods_type",
				 			hiddenName:"sample.goods_type",
				 			store : goods_type_store,
							mode: 'local',
							triggerAction:"all",
						    valueField: 'attr_key',
						    displayField: 'attr_value',
				        	width:200
						}
					} ]
				} ]
			}, {
				layout : 'form',
				autoHeight : true,
				xtype : 'panel',
				title : '',
				// labelAlign:'left',
				items : [ {
					layout : 'column',
					defaults : {
						columnWidth : '.33'
					},
					items : [ {
						layout : 'form',
						columnWidth : '.45',
						items : {
							xtype : "textfield",
							fieldLabel : "产地",
							id:'sample_region',
							name : "sample.sample_region",
							width : 200
						}
					}, {
						columnWidth : '.55',
						layout : 'form',
						labelWidth : 150,
						items : {
							xtype : "textfield",
							fieldLabel : "输出/入国家或地区",
							id:'export_import',
							name : "sample.export_import",
							width : 200
						}

					} ]
				} ]
			}, {
				layout : 'form',
				autoHeight : true,
				xtype : 'panel',
				title : '',
				// labelAlign:'left',
				items : [ {
					layout : 'column',
					defaults : {
						columnWidth : '.33'
					},
					items : [ {
						layout : 'form',
						columnWidth : '.45',
						items : {
							xtype:"combo",
				 			fieldLabel:"送检类型",
				 			id: 'verify_type',
				 			name:"sample.verify_type",
				 			hiddenName:"sample.verify_type",
				 			store : new Ext.data.SimpleStore({
				                  fields: ['attr_key', 'attr_value'],
								  data: [[1, '动检'],[2, '植检']]
							}),
							mode: 'local',
							triggerAction:"all",
						    valueField: 'attr_key',
						    displayField: 'attr_value',
				        	width:200
						}
					} , {
						columnWidth : '.55',
						layout : 'form',
						labelWidth : 150,
						items : {
							xtype:"combo",
				 			fieldLabel:"送样单状态",
				 			id:'status',
				 			name:"sample.status",
				 			hiddenName:"sample.status",
				 			store : new Ext.data.SimpleStore({
				                  fields: ['attr_key', 'attr_value'],
								  data: [[0, '未送检'],[1, '已送检'],[2, '退回'],[3, '评审通过'],
								         [4, '已鉴定'],[5, '分包'],[6, '已复核'],[6, '已签发']]
							}),
							mode: 'local',
							triggerAction:"all",
						    valueField: 'attr_key',
						    displayField: 'attr_value',
				        	width:200
						}
					}]
				} ]
			}, {
				layout : 'form',
				autoHeight : true,
				xtype : 'panel',
				title : '',
				// labelAlign:'left',
				items : [ {
					layout : 'column',
					defaults : {
						columnWidth : '.33'
					},
					items : [ {
						layout : 'form',
						columnWidth : '.45',
						items : {
							xtype : "textfield",
							fieldLabel : "小样名称",
							id:'demo_name',
							name : "sample.demo_name",
							width : 200
						}
					} , {
						columnWidth : '.55',
						layout : 'form',
						labelWidth : 150,
						items : {
							xtype:"combo",
				 			fieldLabel:"小样状态",
				 			id:'demo_status',
				 			name:"sample.demo_status",
				 			hiddenName:"sample.status",
				 			store : new Ext.data.SimpleStore({
				                  fields: ['attr_key', 'attr_value'],
								  data: [[0, '未鉴定'],[1, '已鉴定'],[2, '已复核'],[3, '已签发']]
							}),
							mode: 'local',
							triggerAction:"all",
						    valueField: 'attr_key',
						    displayField: 'attr_value',
				        	width:200
						}
					}]
				} ]
			}]
		} ],
		buttons : [{
			text : '确定',
			listeners : {
				'click' : function() {
					sampleGrid.store.reload({
						params : {
							start : 0,
							limit : sampleGrid.pageSize,
							submit_depart: Ext.getCmp('submit_depart').getValue(),
							submitter: Ext.getCmp('submitter').getValue(),
							verify_no: Ext.getCmp('verify_no').getValue(),
							submit_date: Ext.getCmp('submit_date').getValue(),
							goods_name: Ext.getCmp('goods_name').getValue(),
							goodtype: Ext.getCmp('goodtype').getValue(),
							sample_region: Ext.getCmp('sample_region').getValue(),
							export_import: Ext.getCmp('export_import').getValue(),
							verify_type: Ext.getCmp('verify_type').getValue(),
							status: Ext.getCmp('status').getValue(),
							demo_name: Ext.getCmp('demo_name').getValue(),
							demo_status: Ext.getCmp('demo_status').getValue(),
						}
					});
					win.close();
				}
			}
		}, {
			text : '取消',
			handler : function() {
				win.close();
			}
		}, {
			text : '重置',
			handler : function() {
				win.form.getForm().reset();
			}
		} ]
	});
	dm.sampleDispose.searchWin.superclass.constructor.call(this, {
		title:'高级搜索',
		resizable : false,
		modal : true,
		width : 865,
		closeAction : 'destroy',
		bodyStyle : 'padding:5px;',
		closable : true,
		items : [ win.form ]
	});
};
Ext.extend(dm.sampleDispose.searchWin, Ext.Window);

dm.sampleDispose.destroyWin = function(id, type, sampleGrid) {
	var win = this;
	
	win.form = new Ext.form.FormPanel({
		layout : 'form',
		collapsible : false,
		autoScroll : true,
		width : 420,
		frame : true,
		renderTo : Ext.getBody(),
		style : 'margin-left:auto;margin-right:auto;width:800px;margin-top:8px;',
		labelAlign : 'right',
		buttonAlign : 'center',
		reader : new Ext.data.JsonReader({
			successProperty : 'success',
			root : 'small'
		}, [{
			name : 'small.destroyer', mapping:'destroyer'
		}, {
			name : 'small.destroydate', mapping:'destroydate'
		}]),
		items : [{
			xtype : 'fieldset',
			title : '填写销毁信息',
			autoHeight : true,
			items : [ {
				layout : 'form',
				autoHeight : true,
				xtype : 'panel',
				title : '',
				// labelAlign:'left',
				items : [ {
					layout : 'column',
					items : [ {
						layout : 'form',
						items : {
							xtype : "textfield",
							fieldLabel : "销毁人",
							name : "small.destroyer",
							allowBlank : false,
							blankText : '不能为空！',
							emptyText : '不能为空！',
							width : 200
						}
					}]
				} ]
			}, {
				layout : 'form',
				autoHeight : true,
				xtype : 'panel',
				title : '',
				items : [ {
					layout : 'column',
					items : [ {
						layout : 'form',
						items : {
							xtype : "datefield",
							format : 'Y-m-d',
							editable : false,
							fieldLabel : '销毁日期',
							allowBlank : false,
							blankText : '不能为空！',
							emptyText : '不能为空！',
							name : "small.destroydate",
							width : 200
						}
					} , {
						layout : 'form',
						items : {
							xtype : "textfield",
							fieldLabel : "id",
							hidden:true,
							name : "small.id",
							value: id,
							width : 200
						}
					}]
				} ]
			}]
		}],
		buttons : [ {
			text : '确定',
			listeners : {
				'click' : function() {
					if (win.form.getForm().isValid()) {
						win.form.getForm().doAction('submit', {
							url : 'resource/sampleRegister/registerDestroy',
							method : 'post',
							waitMsg : '正在加载',
							timeout : 2000,
							success : function(response) {
								Ext.Msg.alert('提示窗口', '添加销毁信息成功！');
								sampleGrid.loadData();
								win.close();
							},
							failure : function(response, action) {
								Ext.Msg.alert('提示窗口', '添加销毁信息失败！');
							}
						});
					} else {
						return;
					}
				}
			}
		}, {
			text : '取消',
			handler : function() {
				win.close();
			}
		}, {
			text : '重置',
			handler : function() {
				win.form.getForm().reset();
			}
		} ]
	});
	if(type == 1){
		win.form.getForm().load({
			params : {
				id : id
			},
			url : "resource/sampleRegister/loadDestroy"	
		});
	}
	
	dm.sampleDispose.destroyWin.superclass.constructor.call(this, {
		title : '销毁信息',
		resizable : false,
		autoScroll : true,
		width : 465,
		height : 250,
		modal : true,
		closeAction : 'destroy',
		bodyStyle : 'padding:5px;',
		closable : true,
		items : [ win.form ]
	});
};
Ext.extend(dm.sampleDispose.destroyWin, Ext.Window);

dm.sampleDispose.reserveWin = function(id, type, sampleGrid) {
	var win = this;
	
	win.form = new Ext.form.FormPanel({
		layout : 'form',
		collapsible : false,
		autoScroll : true,
		width : 420,
		frame : true,
		renderTo : Ext.getBody(),
		style : 'margin-left:auto;margin-right:auto;width:800px;margin-top:8px;',
		labelAlign : 'right',
		buttonAlign : 'center',
		reader : new Ext.data.JsonReader({
			successProperty : 'success',
			root : 'small'
		}, [{
			name : 'small.staytodate', mapping:'staytodate'
		}]),
		items : [{
			xtype : 'fieldset',
			title : '填写留样信息',
			autoHeight : true,
			items : [{
				layout : 'form',
				autoHeight : true,
				xtype : 'panel',
				title : '',
				items : [ {
					layout : 'column',
					items : [ {
						layout : 'form',
						items : {
							xtype : "datefield",
							format : 'Y-m-d',
							editable : false,
							fieldLabel : '留样日期到',
							allowBlank : false,
							blankText : '不能为空！',
							emptyText : '不能为空！',
							name : "small.staytodate",
							width : 200
						}
					} , {
						layout : 'form',
						items : {
							xtype : "textfield",
							fieldLabel : "id",
							hidden:true,
							name : "small.id",
							value: id,
							width : 200
						}
					}]
				} ]
			}]
		}],
		buttons : [ {
			text : '确定',
			listeners : {
				'click' : function() {
					if (win.form.getForm().isValid()) {
						win.form.getForm().doAction('submit', {
							url : 'resource/sampleRegister/registerReserve',
							method : 'post',
							waitMsg : '正在加载',
							timeout : 2000,
							success : function(response) {
								Ext.Msg.alert('提示窗口', '添加销毁信息成功！');
								sampleGrid.loadData();
								win.close();
							},
							failure : function(response, action) {
								Ext.Msg.alert('提示窗口', '添加销毁信息失败！');
							}
						});
					} else {
						return;
					}
				}
			}
		}, {
			text : '取消',
			handler : function() {
				win.close();
			}
		}, {
			text : '重置',
			handler : function() {
				win.form.getForm().reset();
			}
		} ]
	});
	if(type == 3){
		win.form.getForm().load({
			params : {
				id : id
			},
			url : "resource/sampleRegister/loadReserve"	
		});
	}
	
	dm.sampleDispose.reserveWin.superclass.constructor.call(this, {
		title : '留样信息',
		resizable : false,
		autoScroll : true,
		width : 465,
		height : 250,
		modal : true,
		closeAction : 'destroy',
		bodyStyle : 'padding:5px;',
		closable : true,
		items : [ win.form ]
	});
};
Ext.extend(dm.sampleDispose.reserveWin, Ext.Window);