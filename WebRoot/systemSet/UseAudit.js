Ext.namespace('DM.monitor.useAudit');

/**
 * 主面板
 */
DM.monitor.useAudit.MainPanel = function() {
	var mainPanel = this;
	mainPanel.gridPanel = new DM.monitor.useAudit.GridPanel();
	mainPanel.searchPanel = new DM.monitor.useAudit.SearchPanel(mainPanel.gridPanel);
	/**
	 * 标签面板的构造函数
	 */
	DM.monitor.useAudit.MainPanel.superclass.constructor.call(this, {
				title : '业务审计',
				layout : 'border',
				baseCls : "x-plain",
				closable : true,
				items : [mainPanel.searchPanel, mainPanel.gridPanel]
			});
	mainPanel.on('afterrender', function() {
				// 加载表格数据
				mainPanel.gridPanel.loadData();
			});

};
Ext.extend(DM.monitor.useAudit.MainPanel, Ext.Panel, {});

DM.monitor.useAudit.GridPanel = function() {
	var gridPanel = this;
	gridPanel.searchConditions = {
		operatorName : "",
		operateSubject : "",
		operateContent : "",
		startTime : '',
		endTime : '',
		ipAdrr : ''
	};
	// 勾选框
	gridPanel.sm = new Ext.grid.CheckboxSelectionModel();
	// 每页显示条数下拉选择框
	gridPanel.pagesize_combo = new dm.object.pagesize_combo();
	// 每页显示的条数
	gridPanel.pageSize = parseInt(gridPanel.pagesize_combo.getValue());
	gridPanel.store = new Ext.data.Store({
				proxy : new Ext.data.HttpProxy({
							url : 'resource/useAudit/search',
							method : 'post'
						}),
				reader : new Ext.data.JsonReader({
							totalProperty : 'total',
							root : 'auditLogs'
						}, [{
									name : 'id'
								}, {
									name : 'operatorName'
								}, {
									name : 'operateTime',
									convert : function(v) {
										return new Date(v)
												.format("yyyy-MM-dd hh:mm:ss");
									}
								}, {
									name : 'operateSubject'
								}, {
									name : 'operateContent'
								}, {
									name : 'ipAdrr'
								}]),
				baseParams : gridPanel.searchConditions
			});
	gridPanel.cm = new Ext.grid.ColumnModel([new Ext.grid.RowNumber({
				width : 30,
				renderer : function(value, metadata, record, rowIndex) {
					// 分页自增长序列
					return gridPanel.store.lastOptions.params.start + 1
							+ rowIndex;
				}
			}), gridPanel.sm, {
		// 操作人
		header : '操作人',
		dataIndex : "operatorName",
		width : 40,
		renderer : function(value) {
			return dm.comm.comm_getTip(value);
		}
	}, {
		// 操作ip
		header : '操作IP',
		dataIndex : "ipAdrr",
		width : 40,
		renderer : function(value) {
			return dm.comm.comm_getTip(value);
		}
	}, {
		// 操作时间
		header : '操作时间',
		dataIndex : "operateTime",
		width : 60,
		renderer : function(value) {
			return dm.comm.comm_getTip(value);
		}
	}, {
		// 操作主题
		header : '操作主题',
		dataIndex : "operateSubject",
		width : 45,
		renderer : function(value) {
			return dm.comm.comm_getTip(value);
		}
	}, {
		// 操作内容
		header : '操作内容',
		dataIndex : "operateContent",
		renderer : function renderContent(value) {
			if (value == null) {
				return "";
			}
			// 最新的版本效果
			var showTip = "<div>" + value + "</div>";
			var valueInTip = "<div style='word-break:break-all;white-space:pre-wrap;'>"
					+ showTip + "</div>";// 这里设置为pre-wrap，不会处理多余的空白符，也不会把换行符变成一个空格。
			return '<div style="padding:3px;text-overflow:ellipsis;overflow:hidden;"><span ext:qdismissDelay=0 ext:qtip="'
					+ valueInTip + '">' + value + '</span></div>';
		}
	}]);
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
	DM.monitor.useAudit.GridPanel.superclass.constructor.call(this, {
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
					forceFit : true
				},
				tbar : [{
							text : '导出',
							iconCls : 'icon-system-log-export',
							handler : function() {
								gridPanel.exportData();
							}
						}, {
							xtype : 'tbspacer',
							width : 5
						}, {
							text : '查看',
							iconCls : 'icon-info',
							handler : function() {
								gridPanel.infoData();
							}
						}, {
							xtype : 'tbspacer',
							width : 5
						}, {
							text : '刷新',
							iconCls : 'icon-flush',
							handler : function() {
								gridPanel.refreshData();
							}
						}]
			});
	gridPanel.store.on('beforeload', function() {
				gridPanel.store.baseParams = gridPanel.searchConditions;
			});
};
Ext.extend(DM.monitor.useAudit.GridPanel, Ext.grid.GridPanel, {
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
		var grid = this;
		grid.clearSearchCondition();
		grid.searchConditions.operatorName = '';
		grid.searchConditions.operateSubject = '';
		grid.searchConditions.operateContent = '';
		grid.searchConditions.startTime = null;
		grid.searchConditions.endTime = null;
		grid.searchConditions.ipAdrr = '';
		grid.loadData();
	},
	clearSearchCondition : function() {
		Ext.getCmp("useAuditOperator").setValue('');
		Ext.getCmp("useAuditOperateSubject").setValue('');
		Ext.getCmp("useAuditOperateContent").setValue('');
		Ext.getCmp("useAuditOperateStartTime").setValue('');
		Ext.getCmp("useAuditOperateEndTime").setValue('');
		Ext.getCmp("useAuditOperateStartTime").setMaxValue(null);
		Ext.getCmp("useAuditOperateEndTime").setMinValue(null);
		Ext.getCmp("useAuditOperateIpAdrr").setValue('');
	},
	infoData : function() {
		var win = new DM.monitor.useAudit.Window("查看");
		var line = this.getSelectionModel().getSelections();
		if (line.length == 0) {
			Ext.Msg.alert('提示', '请选择记录');
		} else if (line.length >= 2) {
			Ext.Msg.alert('提示', '请选择一条记录');
		} else {
			var id = this.getSelectionModel().getSelections()[0].data.id;
			win.subElement.getForm().load({
						url : 'resource/useAudit/infoAuditOperation',
						params : {
							id : id
						},
						method : 'post',
						success : function(response) {
							try {
								win.show();
							} catch (e) {
								DM.comm.ShowInternetError();
								win.destroy();
							}
						}
					});
		}
	},
	exportData : function() {
		var win = new DM.monitor.useAudit.Window('导出', this);
		win.show();
	}
});

DM.monitor.useAudit.exportForm = function(win, grid) {
	var exportForm = this;
	exportForm.win = win;
	exportForm.grid = grid;
	exportForm.exportOptions = 1;
	DM.monitor.useAudit.exportForm.superclass.constructor.call(this, {
				labelAlign : 'left',
				frame : false,
				labelSeparator : '：',
				buttonAlign : "center",
				// 表单元素和表单面板的边距
				bodyStyle : 'padding:5px 0px 0px',
				region : "center",
				defaultType : 'textfield',
				items : [{
							xtype : 'radio',
							boxLabel : '导出所有',
							inputValue : '1',
							checked : true,
							name : 'exportOptions',
							listeners : {
								'check' : function() {
									if (this.checked) {
										exportForm.exportOptions = 1;
									}
								}
							}
						}, {
							xtype : 'radio',
							boxLabel : '导出当前查询到的',
							inputValue : '2',
							name : 'exportOptions',
							listeners : {
								'check' : function() {
									if (this.checked) {
										exportForm.exportOptions = 2;
									}
								}
							}
						}],
				buttons : [{
							text : '导出',
							iconCls : 'icon-system-log-export',
							handler : function() {
								exportForm.exportAuditOperation();
							}
						}, {
							text : '关闭',
							iconCls : 'icon-shutdown',
							handler : function() {
								exportForm.win.close();
							}
						}]
			});
}
Ext.extend(DM.monitor.useAudit.exportForm, Ext.form.FormPanel, {
			setNullDate : function(date) {
				if (date != null) {
					if (Ext.isIE) {
						if (date.toString().indexOf('NaN') >= 0) {
							return true;
						}
					} else {
						if (date.toString() == 'Invalid Date') {
							return true;
						}
					}
					return false;
				}
			},
			exportAuditOperation : function() {
				var formPanel = this;
				var downloadIframe = document.createElement('iframe');
				var url = 'resource/useAudit/exportData';
				downloadIframe.style.display = "none";
				document.body.appendChild(downloadIframe);
				var searchConditions = formPanel.grid.searchConditions;
				if (formPanel.setNullDate(searchConditions.startTime)) {
					searchConditions.startTime = null;
				}
				if (formPanel.setNullDate(searchConditions.endTime)) {
					searchConditions.endTime = null;
				}
				dm.comm.IframePost.doPost({
							Url : url,
							Target : downloadIframe,
							PostParams : {
								exportConditions : Ext.util.JSON
										.encode(searchConditions),
								options : formPanel.exportOptions
							}
						});
				formPanel.win.close();
			}
		});
/**
 * 搜索面板
 */
DM.monitor.useAudit.SearchPanel = function(grid) {
	var searchPanel = this;
	searchPanel.grid = grid;
	DM.monitor.useAudit.SearchPanel.superclass.constructor.call(this, {
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
							id : 'useAuditOperator',
							xtype : 'textfield',
							// labelWidth : 20,
							fieldLabel : '操作人',// '操作人',
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
						}]
			}, {
				columnWidth : .3,
				layout : "form",
				items : [{
							id : 'useAuditOperateSubject',
							xtype : 'textfield',
							// labelWidth : 20,
							fieldLabel : '功能',
							width : 150,
							maxLength : 40,
							maxLengthText : '长度不能超过40',// "长度不能超过40",
							listeners : {
								specialkey : function(field, e) {
									if (e.getKey() == Ext.EventObject.ENTER) {
										searchPanel.searchData();
									}
								}
							}
						}]
			}, {
				columnWidth : .3,
				layout : "form",
				items : [{
							id : 'useAuditOperateIpAdrr',
							xtype : 'textfield',
							// labelWidth : 20,
							fieldLabel : '操作IP',
							width : 150,
							regexText : '请输入1-3位的数字',
							maxLength : 40,
							maxLengthText : '长度不能超过40',// "长度不能超过40",
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
						}]
			}, {
				columnWidth : .3,
				layout : "form",
				items : [new Ext.ux.ResetDateField({
							fieldLabel : '操作时间起',// '操作时间起',
							// //
							// 创建时间起
							width : 150,
							emptyText : '请选择',
							allowBlank : true,
							editable : false,
							format : 'Y-m-d',// 日期格式
							endDateField : 'useAuditOperateEndTime',
							name : '',
							id : 'useAuditOperateStartTime',
							VTypes : 'daterange',
							listeners : {
								'click' : function() {
									this.onTriggerClick();// 调用日期下拉
								},
								'blur' : function() {
									Ext
											.getCmp("useAuditOperateEndTime")
											.setMinValue(Ext
													.getCmp("useAuditOperateStartTime")
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
				items : [
				// 日期控件
				new Ext.ux.ResetDateField({
							fieldLabel : '操作时间止',// '操作时间止',
							// 创建时间起
							width : 150,
							emptyText : '请选择',
							allowBlank : true,
							editable : false,
							format : 'Y-m-d',// 日期格式
							startDateField : 'useAuditOperateStartTime',
							name : '',
							id : 'useAuditOperateEndTime',
							VTypes : 'daterange',
							listeners : {
								'click' : function() {
									this.onTriggerClick();// 调用日期下拉
								},
								'blur' : function() {
									Ext
											.getCmp("useAuditOperateStartTime")
											.setMaxValue(Ext
													.getCmp("useAuditOperateEndTime")
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
							id : 'useAuditOperateContent',
							xtype : 'textfield',
							width : 150,
							fieldLabel : '操作内容',
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
}
Ext.extend(DM.monitor.useAudit.SearchPanel, Ext.form.FormPanel, {
			searchData : function() {
				var searchPanel = this;
				if (!searchPanel.getForm().isValid()) {
					Ext.Msg.alert('提示', "查询语句输入有误！");
					return;

				}
				var grid = searchPanel.grid;
				grid.searchConditions.operatorName = Ext
						.getCmp("useAuditOperator").getValue().trim();
				grid.searchConditions.operateSubject = Ext
						.getCmp("useAuditOperateSubject").getValue().trim();
				grid.searchConditions.operateContent = Ext
						.getCmp("useAuditOperateContent").getValue().trim();
				grid.searchConditions.startTime = new Date(Ext
						.getCmp("useAuditOperateStartTime").getValue());
				grid.searchConditions.endTime = new Date(Ext
						.getCmp("useAuditOperateEndTime").getValue());
				grid.searchConditions.ipAdrr = Ext
						.getCmp("useAuditOperateIpAdrr").getValue();
				grid.loadData();
			}
		});

DM.monitor.useAudit.infoFormPanel = function(win) {
	var infoFormPanel = this;
	infoFormPanel.win = win;
	// 从后台加载数据的格式
	infoFormPanel.reader = new Ext.data.JsonReader({
				successProperty : 'success',
				root : 'auditOperation'
			}, [{
						name : 'id'
					}, {
						name : 'operatorName'
					}, {
						name : 'ipAdrr'
					}, {
						name : 'operateTime',
						convert : function(v) {
							return new Date(v).format("yyyy-MM-dd hh:mm:ss");
						}
					}, {
						name : 'operateSubject'
					}, {
						name : 'operateContent'
					}]);
	// 初始化
	DM.monitor.useAudit.infoFormPanel.superclass.constructor.call(this, {
				reader : infoFormPanel.reader,
				labelAlign : 'right',
				frame : false,
				labelSeparator : '：',
				buttonAlign : "center",
				// 表单元素和表单面板的边距
				bodyStyle : 'padding:5px 0px 0px',
				region : "center",
				defaultType : 'textfield',
				items : [{
							fieldLabel : '操作人',
							name : 'operatorName',
							width : 250,
							style : "background:#f1f1f1",
							readOnly : true
						}, {
							fieldLabel : '操作ip',
							name : 'ipAdrr',
							width : 250,
							style : "background:#f1f1f1",
							readOnly : true
						}, {
							fieldLabel : '操作时间',
							name : 'operateTime',
							width : 250,
							style : "background:#f1f1f1",
							readOnly : true
						}, {
							fieldLabel : '功能',
							name : 'operateSubject',
							width : 250,
							style : "background:#f1f1f1",
							readOnly : true
						}, {
							xtype : 'textarea',
							fieldLabel : '操作内容',
							name : 'operateContent',
							width : 250,
							style : "background:#f1f1f1",
							readOnly : true
						}],
				buttons : [{
							text :'关闭',
							iconCls : 'icon-shutdown',
							handler : function() {
								infoFormPanel.win.close();
							}
						}]
			});

}
Ext.extend(DM.monitor.useAudit.infoFormPanel, Ext.form.FormPanel, {});

DM.monitor.useAudit.Window = function(title, grid) {
	var userWin = this;
	userWin.title = title;
	userWin.width = 400;
	userWin.height = 300;
	switch (title) {
		case '导出' :
			userWin.subElement = new DM.monitor.useAudit.exportForm(this, grid);
			userWin.width = 300;
			userWin.height = 150;
			break;
		case '查看' :
			userWin.subElement = new DM.monitor.useAudit.infoFormPanel(this);
			break;
	}
	DM.monitor.useAudit.Window.superclass.constructor.call(this, {
				layout : 'border',
				resizable : false,
				bodyStyle : 'padding:5px;',
				maximizable : false,
				modal : true,
				constrain : true,
				border : false,
				closeAction : 'destroy',
				closable : true,
				collapsible : false,
				plain : true,
				title : userWin.title,
				items : [userWin.subElement]
			});
}
Ext.extend(DM.monitor.useAudit.Window, Ext.Window, {
			getForm : function() {
				var userWin = this;
				return userWin.subElement;
			}
		});