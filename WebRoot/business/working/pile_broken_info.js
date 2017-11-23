Ext.namespace('dm.equip.pilebroken');

/**
 * 主面板
 */
dm.equip.pilebroken.MainPanel = function() {
	var mainPanel = this;
	mainPanel.gridPanel = new dm.equip.pilebroken.GridPanel();
	mainPanel.gridPanel.parent = mainPanel;
	mainPanel.searchPanel = new dm.equip.pilebroken.SearchPanel(mainPanel.gridPanel);
	/**
	 * 标签面板的构造函数
	 */
	dm.equip.pilebroken.MainPanel.superclass.constructor.call(this, {
				title : '不合格成桩列表',
				layout : 'border',
				baseCls : "x-plain",
				closable : true,
				items : [mainPanel.searchPanel, mainPanel.gridPanel, new dm.equip.pilebroken.bottom()]
			});
	mainPanel.on('afterrender', function() {
				// 加载表格数据
				mainPanel.gridPanel.loadData();
			});

};
Ext.extend(dm.equip.pilebroken.MainPanel, Ext.Panel, {});

dm.equip.pilebroken.GridPanel = function() {
	var gridPanel = this;
	gridPanel.searchConditions = {
		 pileDriverNumber : "",
		 /*startTime : "",
		 endTime : "",*/
		 pileNumber : '',
		 sectionNumber : ''
	};
	// 勾选框
	gridPanel.sm = new Ext.grid.CheckboxSelectionModel();
	// 每页显示条数下拉选择框
	gridPanel.pagesize_combo = new dm.object.pagesize_combo();
	// 每页显示的条数
	gridPanel.pageSize = parseInt(gridPanel.pagesize_combo.getValue());
	gridPanel.store = new Ext.data.Store({
				proxy : new Ext.data.HttpProxy({
							url : 'resource/pileManager/searchBroken',
							method : 'post'
						}),
				reader : new Ext.data.JsonReader({
							totalProperty : 'total',
							root : 'pile'
						}, [{
									name : 'id'
								}, {
									name : 'pileNumber'
								}, {
									name : 'startTime',
									convert : function(v) {
										return new Date(v)
												.format("yyyy-MM-dd hh:mm:ss");
									}
								},  {
									name : 'endTime',
									convert : function(v) {
										return new Date(v)
												.format("yyyy-MM-dd hh:mm:ss");
									}
								},{
									name : 'pileLength'
								}, {
									name : 'gunitingSecond'
								}, {
									name : 'totalLiquid'
								}, {
									name : 'totalCement'
								}, {
									name : 'maxUpSpeed'
								}, {
									name : 'maxDownSpeed'
								}, {
									name : 'maxInsidePower'
								}, {
									name : 'maxOutsidePower'
								}, {
									name : 'maxLean'
								}, {
									name : 'pileDriverNumber'
								}, {
									name : 'createTime',
									convert : function(v) {
										return new Date(v)
												.format("yyyy-MM-dd hh:mm:ss");
									}
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
			}), gridPanel.sm, 
	{
		header : 'ID',
		dataIndex : "id",
		width : 40,
		renderer : function(value) {
			return dm.comm.comm_getTip(value);
		}
	}, {
		header : '桩编号',
		dataIndex : "pileNumber",
		width : 40,
		renderer : function(value) {
			return dm.comm.comm_getTip(value);
		}
	}, {
		header : '开始时间',
		dataIndex : "startTime",
		width : 60,
		renderer : function(value) {
			return dm.comm.comm_getTip(value);
		}
	}, {
		header : '结束时间',
		dataIndex : "endTime",
		width : 45,
		renderer : function(value) {
			return dm.comm.comm_getTip(value);
		}
	},{
		header : '实际桩长(m)',
		dataIndex : "pileLength",
		width : 45,
		renderer : function(value) {
			return dm.comm.comm_getTip(value);
		}
	},{
		header : '喷浆时间(s)',
		dataIndex : "gunitingSecond",
		width : 45,
		renderer : function(value) {
			return dm.comm.comm_getTip(value);
		}
	},{
		header : '总浆量(L)',
		dataIndex : "totalLiquid",
		width : 45,
		renderer : function(value) {
			return dm.comm.comm_getTip(value);
		}
	},{
		header : '总灰量(Kg)',
		dataIndex : "totalCement",
		width : 45,
		renderer : function(value) {
			return dm.comm.comm_getTip(value);
		}
	},{
		header : '最大提钻速度(cm/min)',
		dataIndex : "maxUpSpeed",
		width : 45,
		renderer : function(value) {
			return dm.comm.comm_getTip(value);
		}
	},{
		header : '下钻速度(cm/min)',
		dataIndex : "maxDownSpeed",
		width : 45,
		renderer : function(value) {
			return dm.comm.comm_getTip(value);
		}
	},{
		header : '内电流(A)',
		dataIndex : "maxInsidePower",
		width : 45,
		renderer : function(value) {
			return dm.comm.comm_getTip(value);
		}
	},{
		header : '最大外电流(A)',
		dataIndex : "maxOutsidePower",
		width : 45,
		renderer : function(value) {
			return dm.comm.comm_getTip(value);
		}
	},{
		header : '垂直度(%)',
		dataIndex : "maxLean",
		width : 45,
		renderer : function(value) {
			return dm.comm.comm_getTip(value);
		}
	},{
		header : '机号',
		dataIndex : "pileDriverNumber",
		width : 45,
		renderer : function(value) {
			return dm.comm.comm_getTip(value);
		}
	},{
		header : '上传时间',
		dataIndex : "createTime",
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
	dm.equip.pilebroken.GridPanel.superclass.constructor.call(this, {
				autoWidth : true,
				height : 520,
				region : 'center',
				loadMask : {
					msg : '正在加载...'
				},
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
					forceFit : true,
					getRowClass : function(record,rowIndex,rowParams,store){
		                   //数据显示红色
	                   if(record.data.pileStatus){
	                	   switch(record.data.pileStatus){
	                	   	case 1:return 'x-grid-record-red';
	                	   	case 2:return 'x-grid-record-orange';
	                	   	case 3:return 'x-grid-record-yellow';
	                	   	case 4:return 'x-grid-record-blue';
	                	   }
	                   }    
	               }
				},
				listeners:{  
					rowdblclick : function(grid,row,e){ 
						var selectionModel = grid.getSelectionModel();
						var record = selectionModel.getSelected();
						//console.info("record : ",record.data.pileNumber);
						
						require(["business/working/pile_perdeep_info.js",'systemSet/clearDateField.js'
					          ,'js/ux/comboxWithClass.js','js/ux/comboxWithOrg.js'], function() {
							
							var cmp = mainTab.getComponent("ext-iframe-252");
							if (cmp){
								mainTab.remove(cmp);
							}
							var tmp = new dm.equip.pile_perdeep.MainPanel(record.data.pileNumber);
							tmp.setTitle("不合格单桩分析");
							
							tmp.id = "ext-iframe-252";
							tmp.iconCls = "icon-module_252";
							gridPanel.parent.frameParent.add(tmp);
							gridPanel.parent.frameParent.loadTab(tmp);
							
						});
						
					}
				}
			});
	gridPanel.store.on('beforeload', function() {
				gridPanel.store.baseParams = gridPanel.searchConditions;
			});
};
Ext.extend(dm.equip.pilebroken.GridPanel, Ext.grid.GridPanel, {
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

		Ext.getCmp("pileNumber1").setValue('');
		Ext.getCmp("sectionNumber1").setValue('');
		Ext.getCmp("pileDriverNumber1").setValue('');
		
		Ext.getCmp("startTime1").setMaxValue(null);
		Ext.getCmp("endTime1").setMinValue(null);
	}
});

/**
 * 搜索面板
 */
dm.equip.pilebroken.SearchPanel = function(grid) {
	var searchPanel = this;
	searchPanel.grid = grid;
	dm.equip.pilebroken.SearchPanel.superclass.constructor.call(this, {
		height : 70,
		region : 'north',
		hideLabels : false,
		frame : true,
		layout : "form",
		labelAlign : "right",
		bodyStyle : 'padding:5 5 0',
		items : [{
			layout : "column",
			defaults : {
				layout : "form",
				baseCls : "x-plain"
			},
			items : [{
				columnWidth : .3,
				items : [{
							id : 'sectionNumber1',
							xtype : 'textfield',
							// labelWidth : 20,
							fieldLabel : '标段',// '操作人',
							maxLength : 40,
							width : 150,
							maxLengthText : '长度不能超过40',// "长度不能超过40",
							listeners : {
								specialkey : function(field, e) {
									if (e.getKey() == Ext.EventObject.ENTER) {
										searchPanel.searchData();
									}
								}
							}
						}, new Ext.ux.ResetDateField({
							fieldLabel : '开始时间',// '操作时间起',
							// //
							// 创建时间起
							width : 150,
							emptyText : '请选择',
							allowBlank : true,
							editable : false,
							format : 'Y-m-d',// 日期格式
							endDateField : 'endTime1',
							name : '',
							id : 'startTime1',
							VTypes : 'daterange',
							listeners : {
								'click' : function() {
									this.onTriggerClick();// 调用日期下拉
								},
								'blur' : function() {
									Ext
											.getCmp("endTime1")
											.setMinValue(Ext
													.getCmp("startTime1")
													.getValue());
								},
								specialkey : function(field, e) {
									if (e.getKey() == Ext.EventObject.ENTER) {
										searchPanel.searchData();
									}
								}
							}
						})]
			}, {
				columnWidth : .3,
				layout : "form",
				items : [{
					xtype: 'panel',
					border: 0,
					height: 28
				}, // 日期控件
				new Ext.ux.ResetDateField({
					fieldLabel : '结束时间',// '操作时间止',
					// 创建时间起
					width : 150,
					emptyText : '请选择',
					allowBlank : true,
					editable : false,
					format : 'Y-m-d',// 日期格式
					startDateField : 'startTime1',
					name : '',
					id : 'endTime1',
					VTypes : 'daterange',
					listeners : {
						'click' : function() {
							this.onTriggerClick();// 调用日期下拉
						},
						'blur' : function() {
							Ext
									.getCmp("startTime1")
									.setMaxValue(Ext
											.getCmp("endTime1")
											.getValue());
						},
						specialkey : function(field, e) {
							if (e.getKey() == Ext.EventObject.ENTER) {
								searchPanel.searchData();
							}
						}
					}
				})]
			}, {
				columnWidth : .3,
				layout : "form",
				items : [{
							id : 'pileDriverNumber1',
							xtype : 'textfield',
							// labelWidth : 20,
							fieldLabel : '桩机编号',
							width : 150,
							maxLength : 40,
							maxLengthText : '长度不能超过40'
						}, {
							id : 'pileNumber1',
							xtype : 'textfield',
							width : 150,
							fieldLabel : '桩编号',
							maxLength : 200,
							maxLengthText : "长度不得超过200个字符",
							listeners : {
								specialkey : function(field, e) {
									if (e.getKey() == Ext.EventObject.ENTER) {
										searchPanel.searchData();
									}
								}
							}
						}]
			}, {
				columnWidth : .1,
				layout : "form",
				items : [{
							iconCls : 'icon-search',
							text : '查询',// 查询
							xtype : 'button',
							handler : function() {
								searchPanel.searchData();
							}
						},{
							iconCls : 'icon-reset',
							text : '重置',// '重置',
							xtype : 'button',
							// funcCode :
							// DM.FUNCCODE.SYSTEMMONITOR_USECONDITION_RESET,
							handler : function() {
								searchPanel.grid.clearSearchCondition();
							}
						}]
			}]
		}]
	});
};

Ext.extend(dm.equip.pilebroken.SearchPanel, Ext.form.FormPanel, {
			searchData : function() {
				var searchPanel = this;
				if (!searchPanel.getForm().isValid()) {
					Ext.Msg.alert('提示', "查询语句输入有误！");
					return;

				}
				
				
				var grid = searchPanel.grid;
				
				grid.searchConditions.pileNumber = Ext.getCmp("pileNumber1").getValue();
				if(grid.searchConditions.pileNumber!=undefined) 
					grid.searchConditions.pileNumber = grid.searchConditions.pileNumber.trim();
				
				grid.searchConditions.sectionNumber = Ext.getCmp("sectionNumber1").getValue().trim();
				if(grid.searchConditions.sectionNumber!=undefined) 
					grid.searchConditions.sectionNumber = grid.searchConditions.sectionNumber.trim();
				
				grid.searchConditions.pileDriverNumber = Ext.getCmp("pileDriverNumber1").getValue().trim();
				if(grid.searchConditions.pileDriverNumber!=undefined) 
					grid.searchConditions.pileDriverNumber = grid.searchConditions.pileDriverNumber.trim();
				
				grid.searchConditions.startTime = dm.comm.comm_ConvertStringToDate(Ext.getCmp("startTime1").getValue());
				grid.searchConditions.endTime = dm.comm.comm_ConvertStringToDate(Ext.getCmp("endTime1").getValue());
				
				grid.loadData();
			}
		});
dm.equip.pilebroken.bottom= function(){
	
	var mainPanel = this;
	/**
	 * 标签面板的构造函数
	 */
	dm.equip.pilebroken.bottom.superclass.constructor.call(this, {
				fitToFrame: true, 
				height : 100,
				region : 'south',
		        html: '<iframe  src="business/working/pileBottom.html" frameborder="0" width="100%" height="100%"></iframe>',
				closable : true
			});
};

Ext.extend(dm.equip.pilebroken.bottom,Ext.Panel, {});