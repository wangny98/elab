Ext.namespace('dm.sampleReview');

dm.sampleReview.grid = function() {
	var grid = this;

	grid.pagesize_combo = new dm.object.pagesize_combo();
	grid.pageSize = parseInt(grid.pagesize_combo.getValue());

	grid.store = new Ext.data.Store({
		proxy : new Ext.data.HttpProxy({
			url : 'resource/sampleRegister/search',
			method : 'post'
		}),
		reader : new Ext.data.JsonReader({
			totalProperty : 'total',
			root : 'sample'
		}, [ {
			name : 'sample.id',
			mapping : 'id'
		}, {
			name : 'sample.submit_no',
			mapping : 'submit_no'
		}, {
			name : 'sample.submit_depart',
			mapping : 'submit_depart'
		}, {
			name : 'sample.submitter',
			mapping : 'submitter'
		}, {
			name : 'sample.submit_phone',
			mapping : 'submit_phone'
		}, {
			name : 'sample.submit_date',
			mapping : 'submit_date'
		}, {
			name : 'sample.goods_name',
			mapping : 'goods_name'
		}, {
			name : 'sample.goods_type',
			mapping : 'goods_type'
		}, {
			name : 'sample.sample_name',
			mapping : 'sample_name'
		}, {
			name : 'sample.sample_num',
			mapping : 'sample_num'
		}, {
			name : 'sample.sample_region',
			mapping : 'sample_region'
		}, {
			name : 'sample.export_import',
			mapping : 'export_import'
		}, {
			name : 'sample.sample_detail',
			mapping : 'sample_detail'
		}, {
			name : 'sample.verify_no',
			mapping : 'verify_no'
		}, {
			name : 'sample.verify_identify',
			mapping : 'verify_identify'
		}, {
			name : 'sample.verify_circle',
			mapping : 'verify_circle'
		}, {
			name : 'sample.verify_type',
			mapping : 'verify_type'
		}, {
			name : 'sample.verify_projects',
			mapping : 'verify_projects'
		}, {
			name : 'sample.memo',
			mapping : 'memo'
		}, {
			name : 'sample.status',
			mapping : 'status'
		} ]),
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
		dataIndex : "sample.submit_no",
		sortable : true,
		renderer : function(value) {
			return dm.comm.comm_getTip(value);
		}
	}, {
		header : '送样单位',
		dataIndex : "sample.submit_depart",
		sortable : true,
		renderer : function(value) {
			return dm.comm.comm_getTip(value);
		}
	}, {
		header : '实验室编号',
		dataIndex : "sample.submit_no",
		sortable : true,
		renderer : function(value) {
			return dm.comm.comm_getTip(value);
		}
	}, {
		header : '报检号',
		dataIndex : "sample.verify_no",
		renderer : function(value) {
			return dm.comm.comm_getTip(value);
		}
	}, {
		header : '样品名',
		dataIndex : "sample.sample_name",
		renderer : function(value) {
			return dm.comm.comm_getTip(value);
		}
	}, {
		header : '产地',
		dataIndex : "sample.sample_region",
		renderer : function(value) {
			return dm.comm.comm_getTip(value);
		}
	}, {
		header : '送检类型',
		dataIndex : "sample.verify_type",
		renderer : function(value) {
			return dm.comm.comm_getTip(value);
		}
	}, {
		header : '送检日期',
		dataIndex : "sample.submit_date",
		renderer : function(value) {
			return dm.comm.comm_getTip(value);
		}
	}, {
		header : '状态',
		dataIndex : "sample.status",
		renderer : function(value) {
			if(value == 0){
				value = '未送检';
			}else if(value == 1){
				value = '已送检 ';
			}else if(value == 2){
				value = '退回';
			}else if(value == 3){
				value = '评审通过';
			}else if(value == 4){
				value = '已鉴定';
			}else if(value == 5){
				value = '分包';
			}else if(value == 6){
				value = '已复核';
			}else if(value == 7){
				value = '已签发';
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
	dm.sampleReview.grid.superclass.constructor.call(this, {
		title : '实验室评审',
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
			text : '查看', 
			iconCls : 'icon-info',
			funcCode : '',
			handler : function() {
				grid.infoWin();
			}
		}, {
			xtype : "tbseparator"
		}, {
			text : '退单', 
			iconCls : 'icon-cancel',
			funcCode : '',
			handler : function() {
				grid.rejectWin();
			}
		}, {
			xtype : "tbseparator"
		}, {
			text : '受理', 
			iconCls : 'icon-assignRole',
			funcCode : '',
			handler : function() {
				grid.acceptWin();
			}
		}, {
			xtype : 'tbspacer',
			width : 5
		}, '->', {
			xtype : "textfield",
			emptyText : '样单编号     按回车查询',
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
								submit_no : n
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
//			status: 1
		};
	});
	grid.loadData();
};

Ext.extend(dm.sampleReview.grid, Ext.grid.GridPanel, {
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
	searchWin : function() {
		var grid = this;

		var win = new dm.sampleReview.searchWin(this);
		win.show();
	},
	infoWin : function() {
		var sampleGrid = this;
		
		var record = sampleGrid.getSelectionModel().getSelections();
		if (record.length == 0) {
			Ext.Msg.alert('提示窗口', '请先选择您要操作的行！');
			return;
		} else {
			var win = new dm.sampleReview.infoWin('送样单--基本信息登记',
					'resource/sampleRegister/registSample', record[0], '登记', sampleGrid);
			win.show();
		}

	},
	acceptWin : function() {
		var sampleGrid = this;
		
		var record = sampleGrid.getSelectionModel().getSelections();
		if (record.length == 0) {
			Ext.Msg.alert('提示窗口', '请先选择您要操作的行！');
			return;
		} else {
			if(record[0].get('sample.status') == 1){
				var id = record[0].get('sample.id');
				var win = new dm.sampleReview.acceptWin(id, sampleGrid);
				win.show();
			}else{
				Ext.Msg.alert('提示窗口', '改送样单所处状态不能采取受理操作！');
			}
		}
	},
	rejectWin : function() {
		var sampleGrid = this;

		var record = sampleGrid.getSelectionModel().getSelections();
		if (record.length == 0) {
			Ext.Msg.alert('提示窗口', '请先选择您要操作的行！');
			return;
		} else {
			if(record[0].get('sample.status') == 1){
				var ids = [];
				for(var i = 0; i < record.length; i++){
					ids.push(record[i].get('sample.id'));
				}
				var win = new dm.sampleReview.rejectWin(ids, sampleGrid);
				win.show();
			}else{
				Ext.Msg.alert('提示窗口', '改送样单所处状态不能采取退回操作！');
			}
		}
	}
});

dm.sampleReview.searchWin = function(sampleGrid) {
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
				 			fieldLabel:"状态",
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
							status: Ext.getCmp('status').getValue()
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
	dm.sampleReview.searchWin.superclass.constructor.call(this, {
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
Ext.extend(dm.sampleReview.searchWin, Ext.Window);

dm.sampleReview.infoWin = function(title, url, record, result, sampleGrid) {
	var win = this;
	
	var myCheckboxItems = [];    
	
	if(record != null){
		if(record.get('sample.verify_type') == 2){
			var chk1 =  new Ext.form.Checkbox({boxLabel: '杂草', name: 'zacao', inputValue: 1}); 
            var chk2 =  new Ext.form.Checkbox({boxLabel: '病害', name: 'binghai', inputValue: 2});  
            var chk3 =  new Ext.form.Checkbox({boxLabel: '其他', name: 'other', inputValue: 3});
			myCheckboxItems.push(chk1);
			myCheckboxItems.push(chk2);
			myCheckboxItems.push(chk3);
		}else{
			var chk1 = new Ext.form.Checkbox( {boxLabel: '昆虫', name: 'kunchong', inputValue: 1});
		    var chk2 =  new Ext.form.Checkbox({boxLabel: '线虫', name: 'xianchong', inputValue: 2});
		    var chk3 =  new Ext.form.Checkbox({boxLabel: '其他', name: 'other', inputValue: 3});
			myCheckboxItems.push(chk1);
			myCheckboxItems.push(chk2);
			myCheckboxItems.push(chk3);
		}
	}else{
		var chk1 = new Ext.form.Checkbox( {boxLabel: '昆虫', name: 'kunchong', inputValue: 1});
	    var chk2 =  new Ext.form.Checkbox({boxLabel: '线虫', name: 'xianchong', inputValue: 2});
	    var chk3 =  new Ext.form.Checkbox({boxLabel: '其他', name: 'other', inputValue: 3});
		myCheckboxItems.push(chk1);
		myCheckboxItems.push(chk2);
		myCheckboxItems.push(chk3);
	}
	
    var myCheckboxGroup = new Ext.form.CheckboxGroup({    
        xtype : 'checkboxgroup',  
        fieldLabel: '送检项目', 
	    id: 'projectBox',
        itemCls : 'x-check-group-alt',    
        columns : 3,    
        items : myCheckboxItems,
        width: 200
    });    

	win.form = new Ext.form.FormPanel(
			{
				layout : 'form',
				collapsible : false,
				autoScroll : true,
				width : 820,
				frame : true,
				renderTo : Ext.getBody(),
				// title: '<center style="curor:hand"
				// onclick="window.location.reload();">表单控件</center>',
				style : 'margin-left:auto;margin-right:auto;width:800px;margin-top:8px;',
				labelAlign : 'right',
				buttonAlign : 'center',
				items : [{
							xtype : 'fieldset',
							title : '样本基本信息',
							autoHeight : true,
							items : [ {
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
											xtype : "displayfield",
											fieldLabel : "送样单位",
											name : "sample.submit_depart",
											blankText : '不能为空！',
											emptyText : '不能为空！',
											width : 200
										}
									}, {
										columnWidth : '.55',
										layout : 'form',
										labelWidth : 150,
										items : {
											xtype : "displayfield",
											fieldLabel : "送样人",
											allowBlank : false,
											blankText : '不能为空！',
											emptyText : '不能为空！',
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
											xtype : "displayfield",
											fieldLabel : "电话",
											name : "sample.submit_phone",
											blankText : '不能为空！',
											emptyText : '不能为空！',
											width : 200
										}
									}, {
										columnWidth : '.55',
										layout : 'form',
										labelWidth : 150,
										items : {
											xtype : "displayfield",
											format : 'Y-m-d',
											editable : false,
											fieldLabel : '送样日期',
											allowBlank : false,
											blankText : '不能为空！',
											emptyText : '不能为空！',
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
											xtype : "displayfield",
											fieldLabel : "货物名称",
											name : "sample.goods_name",
											blankText : '不能为空！',
											emptyText : '不能为空！',
											width : 200
										}
									}, {
										columnWidth : '.55',
										layout : 'form',
										labelWidth : 150,
										items : {
											xtype:"displayfield",
								 			fieldLabel:"货物类型",
								 			name:"sample.goods_type",
								 			hiddenName:"sample.goods_type",
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
											xtype : "displayfield",
											fieldLabel : "样品名称",
											name : "sample.sample_name",
											blankText : '不能为空！',
											emptyText : '不能为空！',
											width : 200
										}
									}, {
										columnWidth : '.55',
										layout : 'form',
										labelWidth : 150,
										items : {
											xtype : "displayfield",
											fieldLabel : "样品数/重量",
											allowBlank : false,
											blankText : '不能为空！',
											emptyText : '不能为空！',
											name : "sample.sample_num",
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
											xtype : "displayfield",
											fieldLabel : "产地",
											name : "sample.sample_region",
											blankText : '不能为空！',
											emptyText : '不能为空！',
											width : 200
										}
									}, {
										columnWidth : '.55',
										layout : 'form',
										labelWidth : 150,
										items : {
											xtype : "displayfield",
											fieldLabel : "输出/入国家或地区",
											allowBlank : false,
											blankText : '不能为空！',
											emptyText : '不能为空！',
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
											xtype : "displayfield",
											fieldLabel : "样品描述",
											name : "sample.sample_detail",
											blankText : '不能为空！',
											emptyText : '不能为空！',
											width : 200
										}
									}, {
										columnWidth : '.55',
										layout : 'form',
										labelWidth : 150,
										items : {
											xtype : "displayfield",
											fieldLabel : "报检号",
											allowBlank : false,
											blankText : '不能为空！',
											emptyText : '不能为空！',
											name : "sample.verify_no",
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
										columnWidth : '.43'
									},
									items : [ {
										layout : 'form',
										columnWidth : '.45',
										items : {
											xtype : "displayfield",
											fieldLabel : "送样标识",
											name : "sample.verify_identify",
											blankText : '不能为空！',
											emptyText : '不能为空！',
											width : 200
										}
									}, {
										columnWidth : '.55',
										layout : 'form',
										labelWidth : 150,
										items : {
											xtype : "displayfield",
											fieldLabel : "检验检疫项目（周期）",
											allowBlank : false,
											blankText : '不能为空！',
											emptyText : '不能为空！',
											name : "sample.verify_circle",
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
											xtype:"displayfield",
								 			fieldLabel:"送检类型",
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
										items : myCheckboxGroup
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
										columnWidth : '.91'
									},
									items : [ {
										layout : 'form',
										items : {
											xtype : "displayfield",
											fieldLabel : "补充说明",
											allowBlank : false,
											name : "sample.memo",
											width : 603
										}
									}]
								} ]
							}]
						}],
				buttons : [{
					text : '关闭窗口',
					handler : function() {
						win.close();
					}
				} ]
			});
	if(record != null){
		win.form.getForm().loadRecord(record);
		var pros = record.get('sample.verify_projects').split(",");
		var items = myCheckboxGroup.items;  
		myCheckboxGroup.reset();
        for(var i = 0; i < pros.length;i++){ 
        	for(var j = 0; j < items.length; j++){
        		if(items.items[j].name == pros[i].replace(/(^\s*)|(\s*$)/g, "") ){
        			items.items[j].setValue(true);  
        		}
        	}
        } 
	}
	
	dm.sampleReview.infoWin.superclass.constructor.call(this, {
		title : title,
		resizable : false,
		autoScroll : true,
		width : 865,
		height : 450,
		modal : true,
		closeAction : 'destroy',
		bodyStyle : 'padding:5px;',
		closable : true,
		items : [ win.form ]
	});
};
Ext.extend(dm.sampleReview.infoWin, Ext.Window);

dm.sampleReview.acceptWin = function(id, sampleGrid){
	 var win = this;
	 
	 var storeconf = new Ext.data.Store({
			proxy: new Ext.data.HttpProxy({
				url:'resource/sampleRegister/searchDemo',
				method : 'post'
			}),
			reader: new Ext.data.ArrayReader({totalProperty:'total', root:'list'}, [  //--使用数组解析器解析数组
			    { name: 'small.id',mapping:'id'},                                                                         
				{ name: 'small.no',mapping:'no'}, 
				{ name: 'small.basic_id',mapping:'basic_id'},    
				{ name: 'small.name',mapping:'name'},
				{ name: 'small.total',mapping:'total'},
				{ name: 'small.requirement',mapping:'requirement'},
				{ name: 'small.memo',mapping:'memo' },
				{ name: 'small.judge',mapping:'judge' }
			]),
			remoteSort: true
	});
	var sm = new Ext.grid.CheckboxSelectionModel();	
	var cm = new Ext.grid.ColumnModel([
	    sm,
	    new Ext.grid.RowNumberer({
	    	renderer : function(value, metadata, record, rowIndex) {
	    		return storeconf.lastOptions.params.start + 1 + rowIndex;
	    	}
	    }),{
			header : '小样编号',
			dataIndex : "small.no", // 账号
			renderer : function(value) {
				return dm.comm.comm_getTip(value);
			},
			width: 80
		},{
			header : '小样名称',
			dataIndex : "small.name", // 账号
			renderer : function(value) {
				return dm.comm.comm_getTip(value);
			},
			width: 100
		},{
			header : '数/重量',
			dataIndex : "small.total", // 账号
			renderer : function(value) {
				return dm.comm.comm_getTip(value);
			},
			width: 50
		},{
			header : '具体检验检疫要求',
			dataIndex : "small.requirement", // 账号
			renderer : function(value) {
				return dm.comm.comm_getTip(value);
			},
			width: 160
		},{
			header : '备注',
			dataIndex : "small.memo", // 账号
			renderer : function(value) {
				return dm.comm.comm_getTip(value);
			},
			width: 80
		},{
			header : '鉴定人员',
			dataIndex : "small.judge", // 账号
			renderer : function(value) {
				return dm.comm.comm_getTip(value);
			},
			width: 80
		}]);	
	 
	 win.grid = new Ext.grid.GridPanel({
		 id: 'grid',
		 frame: true,
		 title: '',
		width: 800,
		height:300,
		stripeRows:true,
		store: storeconf,
		viewConfig:{  
				forceFit:true  
			},
		cm: cm,
		sm : sm,
		bbar:new Ext.PagingToolbar({ //实现分页
			pageSize:10,
			store: storeconf,
			displayInfo: true,
			displayMsg:'显示第{0}条到第{1}条记录，一共{2}条',
			emptyMsg: '没有数据'
		})
	 });
	 storeconf.on('beforeload',function(){
		 this.baseParams = {
			id: id
		 };
	 });
	 storeconf.load({
	    	params :{
	    		start:0,
	    		limit:10
	    	}
	 });
	 win.form = win.form = new Ext.form.FormPanel({
		 layout: 'form',  
		 width: 800,
		 height: 210,
        collapsible: false,  
        title: '样品分配',
        autoScroll: true,
        frame: true,  
        renderTo: Ext.getBody(),  
        style: 'margin-left:auto;margin-right:auto;width:500px;margin-top:8px;',  
        labelAlign: 'right',  
        buttonAlign:'center',
		 items: [{
			 layout : 'form',
			 autoHeight : true,
			 xtype : 'panel',
		  	 title : '',
		  	 labelWidth: 80,
			 items : [{
				 layout : 'column',						
				 defaults : {
					 columnWidth : '.45'
				 },				
				 items : [{
					 layout : 'form',
					 items: {
						 xtype:"combo",
				 		 fieldLabel:"鉴定人员",
				 		 name:"small.judge",
				 		 hiddenName:"small.judge",
				 		 store : new Ext.data.SimpleStore({
				            fields: ['attr_key', 'attr_value'],
							data: [[1, '张三'],[2, '李四']]
						 }),
						 mode: 'local',
						 triggerAction:"all",
						 valueField: 'attr_key',
						 displayField: 'attr_value',
				         width:200
					 }
				 },{
					 layout : 'form',
					 items: {
						 xtype : "textfield",
						 fieldLabel : "demo_id",
						 name : "small.id",
						 hidden: true,
						 id: 'demo_id',
						 width : 160
					 }
				 }]
			 }]
		 }],
		 buttons : [{
			 text: '分配小样',
			 listeners:{
				 'click':function(){
					 var record = win.grid.getSelectionModel().getSelections();
					 var ids = [];
					 var length = record.length;
					 for ( var i = 0; i < length; i++) {
							ids.push(record[i].get("small.id"));
 					 }
					 Ext.getCmp('demo_id').setValue(ids);
					 if(win.form.getForm().isValid()){
						 win.form.getForm().doAction('submit',{
							 url: 'resource/sampleRegister/assignDemo',
							 method:'post',
							 waitMsg:'正在加载',
							 timeout : 2000,
							 success:function(response){
								 storeconf.load({
								    params :{
								    	start:0,
								    	limit:10
								    }
								 });
							 },
							 failure:function(response,action){
								 Ext.Msg.alert('提示窗口','添加或修改失败!');
							 }
						 });
					 }else{
						 return;
					 }
				 }
			 }
		 },{
			 text:'受理',
			 listeners:{
				 'click':function(){
					 var flag = false;
					 var length = storeconf.data.length;
					 for ( var i = 0; i < length; i++) {
						 if(storeconf.data.items[i].get("small.judge") == null){
							 flag = true;
						 }
 					 }
					 if(flag){
						 Ext.Msg.alert('提示窗口','请分配完所有小样之后再受理送样单!');
					 }else if(win.form.getForm().isValid()){
						 $.ajax({
							 async: false,
							 type: 'post',
							 data: {
							 	 id: id
							 },
						 	 url: 'resource/sampleRegister/acceptSample',
							 success: function(returnData, status) {
								 Ext.Msg.alert('提示窗口','受理成功!');
								 sampleGrid.loadData();
								 win.close();
							 },
							 failure : function() {
								 Ext.Msg.alert('提示窗口','受理失败!');
							 }
						 });
					 }else{
						 return;
					 }
				 }
			 }
		 },{
			 text:'关闭窗口',
			 handler:function(){
				win.close();
			 }
		 }]
	 });
	 dm.sampleReview.acceptWin.superclass.constructor.call(this, {
		 	title:'受理窗口',
		 	resizable : false, 
	//	 	height: 570,
			width: 825,
		 	modal : true,
		 	closeAction : 'destroy',
		 	bodyStyle : 'padding:5px;',
			closable : true,
		 	items:[win.grid,win.form]
	 });
};
Ext.extend(dm.sampleReview.acceptWin,Ext.Window);

dm.sampleReview.rejectWin = function(ids, sampleGrid) {
	var win = this;

	win.form = new Ext.form.FormPanel({
		layout : 'form',
		collapsible : false,
		autoScroll : true,
		width : 820,
		frame : true,
		renderTo : Ext.getBody(),
		style : 'margin-left:auto;margin-right:auto;width:800px;margin-top:8px;',
		labelAlign : 'right',
		buttonAlign : 'center',
		items : [{
			xtype : 'fieldset',
			title : '',
			autoHeight : true,
			items : [{
				layout : 'form',
				autoHeight : true,
				xtype : 'panel',
				title : '',
				// labelAlign:'left',
				items : [ {
					layout : 'column',
					defaults : {
						columnWidth : '.91'
					},
					items : [ {
						layout : 'form',
						items : {
							xtype : "textarea",
							fieldLabel : "送样单退回原因",
							allowBlank : false,
							name : "sample.chargebackreason",
							width : 603
						}

					},{
						layout : 'form',
						items : {
							xtype : "textarea",
							fieldLabel : "送样单退回原因",
							hidden:true,
							value: ids,
							name : "sample.id",
							width : 603
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
							url : 'resource/sampleRegister/rejectSample',
							method : 'post',
							waitMsg : '正在加载',
							timeout : 2000,
							success : function(response) {
								Ext.Msg.alert('提示窗口', '退单成功！');
								sampleGrid.loadData();
								win.close();
							},
							failure : function(response, action) {
								Ext.Msg.alert('提示窗口', '退单失败！');
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

	dm.sampleReview.rejectWin.superclass.constructor.call(this, {
		title : '退单窗口',
		resizable : false,
		autoScroll : true,
		width : 865,
		height : 250,
		modal : true,
		closeAction : 'destroy',
		bodyStyle : 'padding:5px;',
		closable : true,
		items : [ win.form ]
	});
};
Ext.extend(dm.sampleReview.rejectWin, Ext.Window);