Ext.namespace('dm.equip.pile_perdeep');

/**
 * 主面板
 */
dm.equip.pile_perdeep.MainPanel = function(param) {
	var mainPanel = this;
	mainPanel.framePanel = new dm.equip.pile_perdeep.framePanel(param);
	mainPanel.searchPanel = new dm.equip.pile_perdeep.SearchPanel(mainPanel.framePanel);
	/**
	 * 标签面板的构造函数
	 */
	dm.equip.pile_perdeep.MainPanel.superclass.constructor.call(this, {
				title : '成桩列表',
				layout : 'fit',//'border',
				baseCls : "x-plain",
				closable : true,
				items : [/*mainPanel.searchPanel,*/ mainPanel.framePanel]
			});
	mainPanel.on('afterrender', function() {
				// 加载表格数据
				mainPanel.framePanel.loadData();
			});

};
Ext.extend(dm.equip.pile_perdeep.MainPanel,Ext.Panel, {});

dm.equip.pile_perdeep.framePanel = function(param) {
	var framePanel = this;
	framePanel.searchConditions = {
		 pileDriverNumber : "",
		 pileNumber : '',
		 sectionNumber : ''
	};
	dm.equip.pile_perdeep.framePanel.superclass.constructor.call(this, {
		layout:'border',
		closable:true,
		fitToFrame: true,                   
        html: '<iframe id="equipFrame" name="equipFrame" src="business/equip/equip.html?pileNumberParam='+param+'" frameborder="0" width="100%" height="100%"></iframe>'  
			});
};
Ext.extend(dm.equip.pile_perdeep.framePanel, Ext.Panel, {
	loadData : function() {
		/*var gridPanel = this;
		gridPanel.store.load({
					params : {
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
				});*/
	},
	refreshData : function() {
	},
	clearSearchCondition : function() {
	}
});

/**
 * 搜索面板
 */
dm.equip.pile_perdeep.SearchPanel = function(grid) {
	var searchPanel = this;
	searchPanel.grid = grid;
	dm.equip.pile_perdeep.SearchPanel.superclass.constructor.call(this, {
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
							id : 'sectionNumber',
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
							endDateField : 'endTime',
							name : '',
							id : 'startTime',
							VTypes : 'daterange',
							listeners : {
								'click' : function() {
									this.onTriggerClick();// 调用日期下拉
								},
								'blur' : function() {
									Ext
											.getCmp("endTime")
											.setMinValue(Ext
													.getCmp("startTime")
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
					startDateField : 'startTime',
					name : '',
					id : 'endTime',
					VTypes : 'daterange',
					listeners : {
						'click' : function() {
							this.onTriggerClick();// 调用日期下拉
						},
						'blur' : function() {
							Ext
									.getCmp("startTime")
									.setMaxValue(Ext
											.getCmp("endTime")
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
							id : 'pileDriverNumber',
							xtype : 'textfield',
							// labelWidth : 20,
							fieldLabel : '桩机编号',
							width : 150,
							maxLength : 40,
							maxLengthText : '长度不能超过40'
						}, {
							id : 'pileNumber',
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

Ext.extend(dm.equip.pile_perdeep.SearchPanel, Ext.form.FormPanel, {
			searchData : function() {
				var searchPanel = this;
				if (!searchPanel.getForm().isValid()) {
					Ext.Msg.alert('提示', "查询语句输入有误！");
					return;

				}
				var grid = searchPanel.grid;
				grid.searchConditions.pileNumber = Ext
						.getCmp("pileNumber").getValue().trim();
				grid.searchConditions.sectionNumber = Ext
						.getCmp("sectionNumber").getValue().trim();
				grid.searchConditions.pileDriverNumber = Ext
						.getCmp("pileDriverNumber").getValue().trim();
				grid.searchConditions.startTime = new Date(Ext
						.getCmp("startTime").getValue());
				grid.searchConditions.endTime = new Date(Ext
						.getCmp("endTime").getValue());
				grid.loadData();
			}
		});
