/**
 * 
 * 数据字典
 * 
 * @author 
 * @version [版本号, 2013-8-13]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
Ext.namespace('dm.base');
// 初始化信息提示功能
var editAble = "";
// 属性
var dicAttributeFromDto = "";
// 属性值
var dicAttributeValueFromDto = "attributeValue";
// 描述
var dicDescription = "description";
// 选择的
var selectedExpendId = [];
//
var preExpandRowIndex = null;
// 字典大类的名称
var attributeNameDic;
// 字典大类的属性
var attributeDic;
//
var updateId;
// 是否该提交
var flag = false;
//
Ext.QuickTips.init();
/**
 * 显示所有岗位grid事件
 */

// /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
dm.base.EventDetailDictionaryPanel = function() {

	var eventDetailDictionaryPanel = this;
	// 勾选框
	eventDetailDictionaryPanel.gridSm = new Ext.grid.CheckboxSelectionModel({
				listeners : {
					// 当复选框选择变化事件
					'selectionchange' : function() {
						selectedExpendId = eventDetailDictionaryPanel.gridSm
								.getSelections();
					}
				}
			});
	/**
	 * 数据字典小类
	 */
	dm.base.EventDetailDictionaryPanel.superclass.constructor.call(this, {
		// 加载数据中请稍后
		loadMask : {
			msg : '加载数据中请稍后'
		},
		// 自适应宽度
		autoWidth : true,
		sm : eventDetailDictionaryPanel.gridSm,
		// 自适应高度
		autoHeight : true,
		region : 'center',
		enableHdMenu : false,
		// 与后台交互
		store : new Ext.data.Store({
					proxy : new Ext.data.HttpProxy({
								url : 'resource/dictionaryManage/listDetailDictionary',
								method : 'post'

							}),

					reader : new Ext.data.JsonReader({
								totalProperty : 'totalCount',
								root : 'dictionaryDetailElementList'
							}, [{
										name : 'id'
									}, {
										name : 'attribute'
									}, {
										name : 'attr_key'
									}, {
										name : 'attr_value'
									}, {
										name : 'description'
									}, {
										name : 'seq'
									}, {
										name : 'remark'
									}, {
										name : 'state'
									}, {
										name : 'editMode'
									}])

				}),
		// 列表
		cm : new Ext.grid.ColumnModel([eventDetailDictionaryPanel.gridSm, {
					header : '属性值',
					dataIndex : "attr_key",
					width : 25,
					// 属性值
					renderer : function(value) {
						return dm.comm.comm_getTip(" " + value + " ");
					}

				}, {
					header : '属性名称',
					dataIndex : "attr_value",
					width : 25,
					// 属性值
					renderer : function(value) {
						return dm.comm.comm_getTip(" " + value + " ");
					}

				},{
					header : '可编辑',
					dataIndex : "editMode",
					width : 25,
					// 编辑模式
					renderer : function(value) {
						if ('2' == value) {
							return dm.comm.comm_getTip(" "
									+ '可编辑');
						}
						return dm.comm.comm_getTip(" "
								+ '只读');
					}
				}, {
					header : '排序',
					dataIndex : "seq",
					width : 25,
					// 排序
					renderer : function(value) {
						return dm.comm.comm_getTip(" " + value + " ");
					}
				}, {
					header : '状态',
					dataIndex : "state",
					width : 25,
					// 状态
					renderer : function(value) {
						if ('2' == value) {
							return dm.comm.comm_getTip(" "
									+ '禁用');
						}
						return dm.comm.comm_getTip(" "
								+ '启用');
					}
				}, {
					header : '描述',
					dataIndex : "description",
					// 描述
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
					// renderer : function(value) {
					// return dm.comm.comm_getTip(" " + value);
					// }

				}, {
					header : '备注',
					dataIndex : "remark",
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
				}]),

		// 不产横向生滚动条, 各列自动扩展自动压缩, 适用于列数比较少的情况
		viewConfig : {
			forceFit : true

		}
	});
	// 停止级联选择

	eventDetailDictionaryPanel.on({
				'mouseover' : function(e) {
					e.stopPropagation();
				},
				'mouseout' : function(e) {
					e.stopPropagation();
				}
			});

	eventDetailDictionaryPanel.afterMethod("processEvent", function(n, e) {
				e.stopPropagation();
			});

	eventDetailDictionaryPanel.getStore().load({
				params : {
					attributeDic : attributeDic,
					attributeNameDic : attributeNameDic
				}
			});
};
/**
 * 
 * 
 * @class
 * @extends
 */
Ext.extend(dm.base.EventDetailDictionaryPanel, Ext.grid.GridPanel, {});
// 大类
dm.base.EventDictionaryPanel = function() {

	var eventDictionaryPanel = this;
	// grid row expander
	eventDictionaryPanel.expander = new Ext.ux.grid.RowExpander({
		tpl : new Ext.XTemplate('<div style=width:900px><div class="detailData "></div></div>')
	});
	// expand事件
	eventDictionaryPanel.expander.on("beforeexpand", function() {
				if (preExpandRowIndex != null) {
					eventDictionaryPanel.expander
							.collapseRow(preExpandRowIndex);
				}
			});
	// expand事件
	eventDictionaryPanel.expander.on("beforecollapse", function() {
				selectedExpendId = [];
			});

	eventDictionaryPanel.expander.on("expand", function(expander, record, body,
			rowIndex) {
		attributeDic = record.json.attr_name;
		attributeNameDic = record.json.attribute_name;
		//attributeDic = record.json.attribute;
		//attributeNameDic = record.json.attribute_name;

		preExpandRowIndex = rowIndex;

		new Ext.Panel({
					layout : 'fit',
					items : new dm.base.EventDetailDictionaryPanel(),
					bodyStyle : 'padding-left:50px',
					// border : true,
					// autoWidth : true,
					baseCls : 'x-plain',
					renderTo : Ext.DomQuery.select("div.detailData", body)[0]
				});

			// 判断选项

		}, this);

	// 勾选框
	// eventDictionaryPanel.sm = new Ext.grid.CheckboxSelectionModel();
	// 每页显示条数下拉选择框
	eventDictionaryPanel.pagesize_combo = new dm.object.pagesize_combo();
	// 每页显示的条数
	eventDictionaryPanel.pageSize = parseInt(eventDictionaryPanel.pagesize_combo
			.getValue());
	// 和后台交互的store
	eventDictionaryPanel.store = new Ext.data.Store({
				proxy : new Ext.data.HttpProxy({
							url : 'resource/dictionaryManage/listAllDictionary',
							method : 'POST'
						}),
				reader : new Ext.data.JsonReader({
							totalProperty : 'totalCount',
							root : 'dictionaryList'
						}, [{
									name : 'attr_name'
								}])

			});

	// 父表列表
	eventDictionaryPanel.cm = new Ext.grid.ColumnModel([
			new Ext.grid.RowNumberer({
						// 列表
						renderer : function(value, metadata, record, rowIndex) {
							return eventDictionaryPanel.store.lastOptions.params.start
									+ 1 + rowIndex;
						}
					}), eventDictionaryPanel.expander, {
				// 属性
				header : '属性',
				dataIndex : "attr_name",
				renderer : function(value) {
					return dm.comm.comm_getTip(value);
				}
			}]);

	// 分页工具栏
	eventDictionaryPanel.ProgressBar = new dm.object.ProgressBarPager();

	eventDictionaryPanel.mybbar = new dm.object.PagingToolbar(
			eventDictionaryPanel.pageSize, eventDictionaryPanel.ProgressBar,
			eventDictionaryPanel.store, eventDictionaryPanel.pagesize_combo);
	// 改变每页显示条数reload数据
	eventDictionaryPanel.pagesize_combo.on("select", function(comboBox) {
				eventDictionaryPanel.mybbar.pageSize = parseInt(comboBox
						.getValue());
				eventDictionaryPanel.store.load({
							params : {
								start : 0,
								limit : eventDictionaryPanel.mybbar.pageSize
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

			});

	/**
	 * 构造数据字典岗位列表panel
	 */
	dm.base.EventDictionaryPanel.superclass.constructor.call(this, {
		id : 'id111',
		// 数据字典
		title : '数据字典',
		region : 'center',
		closable : true,
		// 加载数据中请稍后
		loadMask : {
			msg : '加载数据中请稍后'
		},
		tbar : [{
					// 增加
					text : '增加',
					iconCls : 'icon-add',
					
					handler : function() {
						eventDictionaryPanel.addWin();
					}
				},{xtype:"tbseparator"}, {
					// 修改
					text : '修改',
					iconCls : 'icon-modify',
					
					handler : function() {
						eventDictionaryPanel.editWin();
					}
				},{xtype:"tbseparator"},{
					// 删除
					text : '删除',
					iconCls : 'icon-delete',
					
					handler : function() {
						eventDictionaryPanel.deleteDictionary();
					}

				},{xtype:"tbseparator"},{
					// 查看
					text : '查看',
					iconCls : 'icon-info',
					
					handler : function() {
						eventDictionaryPanel.infoDictionary();
					}
				}, '->', {
					// 查询输入框
					xtype : 'textfield',
					width : 150,
					emptyText : '请输入属性项',
					id : 'findDictionaryByName',
					enableKeyEvents : true,
					// enter事件
					listeners : {
						specialkey : function(field, e) {
							searchDictionary = Ext
									.getCmp('findDictionaryByName').getValue();
							if (e.getKey() == Ext.EventObject.ENTER) {
								eventDictionaryPanel
										.searchTheDictionary(searchDictionary
												.trim());
							}
						}
					}
				}, {
					// 查询
					text : '查询',
					iconCls : 'icon-search',
					
					handler : function() {
						var searchDictionary = Ext
								.getCmp('findDictionaryByName').getValue();
						eventDictionaryPanel
								.searchTheDictionary(searchDictionary.trim());
					}
				}, {
					xtype : 'tbspacer',
					width : 5
				}, {
					// 刷新
					text : '刷新',
					iconCls : 'icon-flush',
					
					listeners : {
						'click' : function() {
							Ext.getCmp('findDictionaryByName').setValue("");
							eventDictionaryPanel.store.load({
								params : {
									start : 0,
									limit : eventDictionaryPanel.mybbar.pageSize
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
						}
					}
				}],
		// 与后台交互
		store : eventDictionaryPanel.store,
		// 钩选框
		/*
		 * sm : new Ext.grid.RowSelectionModel({ selestRow : function(index,
		 * keepExisting, preventViewNotify) {
		 * eventDictionaryPanel.expander.toggleRow(index) } }),
		 */
		plugins : eventDictionaryPanel.expander,
		// 列表
		disableSelection : true,
		cm : eventDictionaryPanel.cm,
		// 第5列自适应
		// autoExpandColumn : 9,
		// 分页
		bbar : eventDictionaryPanel.mybbar,
		// 不产横向生滚动条, 各列自动扩展自动压缩, 适用于列数比较少的情况
		viewConfig : {
			forceFit : true
		}
	});

	eventDictionaryPanel.store.on('beforeload', function() {
				preExpandRowIndex = null;
				this.baseParams = {
					searchDictionary : Ext.getCmp('findDictionaryByName')
							.getValue()
				};
			});

	/** ******在gridpanle渲染后进行load会有ladmask信息显示，解决第一次laod显示加载信息的bug******** */
	eventDictionaryPanel.on("afterrender", function() {
				// 加载用户组信息
				eventDictionaryPanel.getStore().load({
							params : {
								start : 0,
								limit : eventDictionaryPanel.mybbar.pageSize
							}
						});
			});

	eventDictionaryPanel.loadData();
};
/**
 * 岗位管理总的表格中所用的函数
 * 
 * @class dm.base.EventDictionaryPanel
 * @extends Ext.grid.GridPanel
 */
Ext.extend(dm.base.EventDictionaryPanel, Ext.grid.GridPanel, {

	// 加载数据
	loadData : function() {
		dictionaryPanel = this;
		this.getStore().load({
					params : {
						start : 0,
						limit : dictionaryPanel.mybbar.pageSize
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

	// 查询
	searchTheDictionary : function(searchDictionary) {
		dictionaryPanel = this;

		this.getStore().load({
					params : {
						searchDictionary : searchDictionary,
						start : 0,
						limit : dictionaryPanel.mybbar.pageSize
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

	// 增加
	addWin : function() {
		var win = new dm.base.DictionaryAddAndUpdateWin('增加',
				this);
		win.show();
	},

	// 修改
	editWin : function() {
		var win = new dm.base.DictionaryAddAndUpdateWin(
				'修改', this);

		// 判断选择的条数
		var len = selectedExpendId.length;
		if (len > 1) {
			Ext.Msg
					.alert('提示',
							'只能选择一条记录进行修改！');
			// this.getSelectionModel().clearSelections();
			return;
		} else if (len == 0) {
			Ext.Msg.alert('提示',
					'请选择要修改的记录');
			return;
		}
		// 判断选项
		var id = selectedExpendId[0].data.id;
		var editAble = selectedExpendId[0].data.editMode;
		// 是否可编辑
		if (editAble == 1) {
			win.getForm().form.load({
				url : 'resource/dictionaryManage/infoDictionary?displayDictionaryId='
						+ id,
				params:{
					displayDictionaryId:id
				},
				success : function(form, action) {
					dicAttributeFromDto = Ext.getCmp("attribute").value;
					dicAttributeValueFromDto = Ext.getCmp("attributeValue").value;
					dicDescription = Ext.getCmp("description").value;
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
			win.show();

		} else {

			win.getForm().form.load({
				url : 'resource/dictionaryManage/infoDictionary?displayDictionaryId='
						+ id,
				params:{
					displayDictionaryId:id
				},
				success : function(form, action) {
					dicAttributeFromDto = Ext.getCmp("attribute").value;
					dicAttributeValueFromDto = Ext.getCmp("attributeValue").value;
					dicDescription = Ext.getCmp("description").value;
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

			Ext.getCmp("attribute").setReadOnly(true);
			////Ext.getCmp("attributeName").setReadOnly(true);
			//Ext.getCmp("attributeValue").setReadOnly(true);
			//Ext.getCmp("state").setReadOnly(true);
			//Ext.getCmp("description").setReadOnly(true);
			win.show();
		}
	},
	// //////////////////////////////////////////////////////////////////////////////////////////

	/*// 内存同步
	synDictionary : function() {
		dictionaryPanel = this;

		Ext.MessageBox.confirm('提示', ERMS_COMM_SURESYNDICTIONARY,
				function(btn) {
					if (btn == 'yes') {
						Ext.Ajax.request({
							url : 'resource/dictionaryManage/memSync',
							method : 'GET',
							async : false,
							success : function(resp) {
								try {
									dm.comm.comm_alert({
												msg : ERMS_COMM_SYNDICTIONARYSUCCERMSS
											});

								} catch (e) {
									// 无错误代码的错误提示信息
									dm.comm.ShowInternetError();
								}
							},

							failure : function(resp) {
								try {
									var json = Ext.util.JSON
											.decode(response.responseText);
									dm.comm.ErrMsg(json.retcode);
								} catch (e) {
									dm.comm.ShowInternetError();
								}
							}
						});

						Ext.getCmp('findDictionaryByName').setValue("");
						dictionaryPanel.store.load({
									params : {
										start : 0,
										limit : dictionaryPanel.mybbar.pageSize
									},
									callback : function(records, options,
											success) {
										try {
											if (!success) {
												dm.comm.showGridError();
											}
										} catch (e) {
											dm.comm.ShowInternetError();
										}
									}
								});
					}
				});
	},*/

	// //////////////////////////////////////////////////////////////////////////////////////////
	// 查看
	infoDictionary : function() {
		var win = new dm.base.EventInfoDictionaryWin('查看',
				this);

		// 判断选择几条记录
		var len = selectedExpendId.length;
		if (len > 1) {
			Ext.Msg.alert('提示',
					'请选择要查看的记录！');
			this.getSelectionModel().clearSelections();
			return;
		} else if (len == 0) {
			Ext.Msg.alert('提示',
					'请选择要查看的记录');
			return;
		}

		// 取得id的值
		var id = selectedExpendId[0].data.id;
		win.getForm().form.load({
			url : 'resource/dictionaryManage/infoDictionary?displayDictionaryId='
					+ id,
			params:{
				displayDictionaryId:id
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

		win.show();
	},

	/**
	 * 点击删除按钮的处理函数
	 * 
	 * 如果没有选择则提示请选择记录 如果选择两条或两条以上记录则提示只能选择一条记录
	 * 
	 * 删除成功后刷新列表
	 */
	deleteDictionary : function() {
		dictionaryPanel = this;
		var rows = selectedExpendId.length;

		if (0 == rows) {
			Ext.Msg.alert('提示',
					'请选择要删除的记录');
		} else {
			for (i = 0; i < rows; i++) {
				var editAble = selectedExpendId[i].data.editMode;
				// 判断是否可删除
				if (editAble == 2) {
					Ext.Msg.alert('提示',
							'所选记录含有只读的编辑模式，不可删除！');
					return;
				}

			}

			Ext.MessageBox.confirm('提示', '是否删除此条数据',
					function(btn) {
						if (btn == 'yes') {
							ids = dictionaryPanel
									.getDeletedIds(selectedExpendId);
							Ext.Ajax.request({
								url : "resource/dictionaryManage/deleteDictionary?deleteIds="
										+ ids,
								method : 'get',
								success : function(resp) {
									try {
										dm.comm.comm_alert({
													msg : '删除成功'
												});
										Ext.getCmp('findDictionaryByName')
												.setValue('');

										selectedExpendId = [];

										dictionaryPanel.getStore().load({
											params : {
												start : 0,
												limit : dictionaryPanel.mybbar.pageSize
											},

											callback : function(records,
													options, success) {
												try {
													if (!success) {
														dm.comm
																.showGridError();
													}
												} catch (e) {
													dm.comm
															.ShowInternetError();
												}
											}
										});

									} catch (e) {
										// 无错误代码的错误提示信息
										dm.comm.ShowInternetError();
									}

								},

								failure : function(resp) {
									try {
										var json = Ext.util.JSON
												.decode(response.responseText);
										dm.comm.ErrMsg(json.retcode);
									} catch (e) {
										dm.comm.ShowInternetError();
									}
								}
							});
						}
					});
		}
	},

	/**
	 * 批量删除时得到每列的id，将其用“,”隔开
	 * 
	 * @param {}
	 *            rows 每一列的信息
	 * @return {}
	 */
	getDeletedIds : function(rows) {
		var toDelete = "";
		for (i = 0; i < rows.length; i++) {
			id = rows[i].get('id');
			if (i == 0)
				toDelete = toDelete + id;
			else
				toDelete = toDelete + "," + id;
		}
		return toDelete;
	}
});

// ///////////////////////   新增  和  修改  ////////////////////////////////////////////
/**
 * 新增和修改所用formpanel
 */
dm.base.AddDictionaryForm = function(title, gird, win) {

	var addDictionaryForm = this;
	// 主页的表格
	addDictionaryForm.grid = gird;
	// 当前的窗口
	addDictionaryForm.win = win;
	// 窗口的属性
	dm.base.AddDictionaryForm.superclass.constructor.call(this, {
		labelWidth : 65,
		layout : 'form',
		labelAlign : 'right',
		buttonAlign : 'center',
		bodyStyle : 'padding:5px 0px 0px', // 表单元素和表单面板的边距
		frame : false,
		labelSeparator : '：',
		reader : new Ext.data.JsonReader({
					root : 'dictionary'
				}, [{
							name : 'id'
						}, {
							name : 'attr_name'
						}, {
							name : 'attr_key'
						}, {
							name : 'attr_value'
						}, {
							name : 'description'
						}, {
							name : 'seq'
						}, {
							name : 'remark'
						}, {
							name : 'state'
						}, {
							name : 'editMode'
						}]),
		items : [{
					// id
					xtype : 'textfield',
					name : 'id',
					id : 'dictionaryId',
					hidden : true,
					hiddenLabel : true,
					value : '0'
				}, {
					// id
					xtype : 'textfield',
					name : 'editMode',
					id : 'editMode',
					hidden : true,
					hiddenLabel : true,
					value : '1'
				}, {

					xtype : 'combo',
					selectOnFocus : true,
					hideTrigger : true,
					store : new Ext.data.Store({
						proxy : new Ext.data.HttpProxy({
							url : 'resource/dictionaryManage/listDictionaryAttribute',
							method : 'POST'
						}),
						autoLoad : true,

						reader : new Ext.data.JsonReader({
									totalProperty : 'totalCount',
									root : 'dictionaryAttributeList'
								}, [{
											name : 'dicAttribute'
										}])

					}),

					triggerAction : "all",
					// 不加该语句选中某项后
					mode : "local",
					// 动态需要
					displayField : "dicAttribute",
					valueField : "dicAttribute",
					fieldLabel : '<span style="color:blue;">'
							+ '属性' + '</span>',
					anchor : "97%",
					baseCls : 'x-plain',
					hiddenName : "attr_name",
					id : 'attribute',
					// 校验角色是否重名
					validationEvent : 'blur',
					validator : function(thisText) {

						Ext.Ajax.request({
							url : 'resource/dictionaryManage/queryAttributeName',
							method : 'post',
							params : {
								attribute : thisText
							},
							success : function(resp) {
								var respText = Ext.util.JSON
										.decode(resp.responseText);

								if (null == thisText || "" == thisText.trim()) {
									Ext
											.getCmp('attribute')
											.markInvalid('属性不能为空');
									flag = false;
									return flag;
								}

								if (thisText.length > 40) {
									Ext
											.getCmp('attribute')
											.markInvalid('长度在40之内');
									flag = false;
									return flag;
								}

								if (respText.success == true) {
									var dicAttributeNameFromAttribute = respText.queryAttributeNameElementList[0].attributeName;
									if (dicAttributeNameFromAttribute != null
											&& dicAttributeNameFromAttribute != "") {
										if (dicAttributeFromDto == "") {
											Ext
													.getCmp('attributeName')
													.setValue(dicAttributeNameFromAttribute);
										}
									}
									flag = true;
									return flag;
								}
							},

							failure : function(form, action) {
							}
						});
						/*
						 * } else { flag = true; return flag; }
						 */
						flag = true;
						return flag;
					}

				}, {
					// 
					xtype : 'textfield',
					baseCls : 'x-plain',
					fieldLabel : '<span style="color:blue;">'
							+ '属性名' + '</span>',
					allowBlank : false,
					blankText : '属性名不能为空',
					maxLength : 40,
					maxLengthText : '长度在40之内',
					anchor : '97%',
					name : "attr_value",
					id : "attributeName"
				}, {
					// 
					xtype : 'textfield',
					baseCls : 'x-plain',
					fieldLabel : '<span style="color:blue;">'
							+ '属性值' + '</span>',
					allowBlank : false,
					blankText : '属性值不能为空',
					maxLength : 2,
					maxLengthText : '输入0-99之间数字',

					regex : /^[1-9]+[0-9]*$/,
					regexText : '输入1-99之间数字',
					anchor : '97%',
					name : "attr_key",
					id : 'attributeValue'
				}, {
					// 
					xtype : 'textfield',
					baseCls : 'x-plain',
					fieldLabel : '<span style="color:blue;">'
							+ '排序' + '</span>',
					allowBlank : false,
					blankText : '排序排序不能为空',
					maxLength : 2,
					maxLengthText : '输入0-99之间数字',

					regex : /^[0-9]*$/,
					regexText : '输入0-99之间数字',
					anchor : '97%',
					name : "seq"
				}, {

					fieldLabel : '<span style="color:blue;">'
							+ '状态' + '</span>',
					allowBlank : false,
					blankText : '状态不可为空',
					id : 'state',
					anchor : '97%',
					hiddenName : "state",
					xtype : 'combo',
					value : '1',
					store : new Ext.data.ArrayStore({
								fields : ['value', 'text'],
								data : [['1', '启用'],
										['2', "禁用"]]
							}),
					displayField : 'text',
					valueField : 'value',
					mode : 'local',
					triggerAction : 'all',
					selectOnFocus : true,
					forceSelection : true,
					editable : false

				}, {
					// 
					xtype : 'textarea',
					fieldLabel : '描述',
					fieldLabel : '<span style="color:blue;">'
							+ '描述' + '</span>',
					//allowBlank : false,
					//blankText : '描述不能为空',
					maxLength : 40,
					maxLengthText : '长度在40之内',
					anchor : '97%',
					height : 50,
					name : "description",
					id : "description"
				}, {
					// 备注
					xtype : 'textarea',
					checkboxToggle : true,
					fieldLabel : "备注",
					maxLength : 600,
					maxLengthText : "长度在600之内",
					anchor : '97%',
					height : 50,
					name : "remark"
				}],
		showLock : false,
		buttons : [{
			// 保存
			text : '保存',
			iconCls : 'icon-save',
			listeners : {
				'click' : function() {
					addDictionaryForm.addTheDictionary(title,
							addDictionaryForm.grid);
				}
			}
		}, {
			// 重置
			text : '重置',
			iconCls : 'icon-reset',

			handler : function() {
				if (title == '修改') {
					var id = selectedExpendId[0].data.id;
					addDictionaryForm.form.load({
						url : 'resource/dictionaryManage/infoDictionary?displayDictionaryId='
								+ id,
						params:{
							displayDictionaryId:id
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
				} else {

					addDictionaryForm.getForm().getEl().dom.reset();
					Ext.getCmp('dictionaryId').setValue(0);
					Ext.getCmp('editMode').setValue(1);
				}
			}

		}, {
			// 关闭
			text : '关闭',
			iconCls : 'icon-shutdown',
			listeners : {
				'click' : function() {
					addDictionaryForm.win.close();
				}
			}
		}]
	});

	if (title == '修改') {
		//Ext.getCmp("attribute").setReadOnly(true);
		//Ext.getCmp("attributeName").setReadOnly(true);
	}
};
/**
 * 继承
 * 
 * @class ERMS.role.AddRoleForm
 * @extends Ext.form.FormPanel
 */
Ext.extend(dm.base.AddDictionaryForm, Ext.form.FormPanel, {
			// 增加
			addTheDictionary : function(title, grid) {
				var addvar = this;
				var url = null;
				if (!addvar.getForm().isValid() || flag == false) {
					return;
				}
				// 根据标题选择url
				if ('增加' == title) {
					url = "resource/dictionaryManage/addDictionary";

				} else if ('修改' == title) {
					updateId = selectedExpendId[0].data.id;
					Ext.getCmp('dictionaryId').setValue(updateId);
					url = "resource/dictionaryManage/editDictionary?id="
							+ updateId;

				}
				// 提交表单
				this.getForm().submit({
					url : url,
					params : {
						dicAtt : dicAttributeFromDto,
						dicAttValue : dicAttributeValueFromDto,
						dicDescription : dicDescription
					},
					method : 'post',
					waitMsg : '正在保存',
					timeout : 2000,
					success : function(response, action) {
						// 标准提示信息写法
						try {
							var respText = action.result.retcode;
							// 106为后台返回码，
							if (respText == 106) {
								Ext.Msg.alert('提示',
										'属性和属性值同时重复，不可保存！');
								return;
							}

							if (respText == 107) {
								Ext.Msg.alert('提示',
										'属性和描述同时重复，不可保存！');
								return;
							}
							// 提示保存成功信息
							dm.comm.comm_alert({
										msg : '保存成功'
									});
							Ext.getCmp('findDictionaryByName').setValue("");
							selectedExpendId = [];
							addvar.grid.getStore().load({
										params : {
											start : 0,
											limit : addvar.grid.mybbar.pageSize
										},
										callback : function(records, options,
												success) {
											try {
												if (!success) {
													dm.comm.showGridError();
												}
											} catch (e) {
												dm.comm.ShowInternetError();
											}
										}
									});

							addvar.getForm().getEl().dom.reset();
							addvar.win.close();
						} catch (e) {
							// 无错误代码的错误提示信息
							dm.comm.ShowInternetError();
						}
					},
					failure : function(response, action) {

						// 错误代码提示信息
						dm.comm.showErrMsg('失败',
								'保存失败');

					}
				});
			}
		});


/**
 * 新增和修改所用window
 */
dm.base.DictionaryAddAndUpdateWin = function(title, grid) {
	var addAndUpdateWin = this;
	// 主页的表格
	addAndUpdateWin.grid = grid;
	addAndUpdateWin.title = title;

	addAndUpdateWin.form = new dm.base.AddDictionaryForm(title, grid,
			addAndUpdateWin);
	// 窗口的属性
	dm.base.DictionaryAddAndUpdateWin.superclass.constructor.call(this, {

				layout : 'fit',
				width : 400,
				height : 350,
				resizable : false,
				bodyStyle : 'padding:5px;',
				border : false, // 边框线设置
				maximizable : false,
				constrain : true,
				closable : true,
				collapsible : false,
				plain : true,
				modal : true,
				title : addAndUpdateWin.title,
				items : [addAndUpdateWin.form]
			});
};
/**
 * 继承
 * 
 * @class 
 * @extends Ext.Window
 */
Ext.extend(dm.base.DictionaryAddAndUpdateWin, Ext.Window, {
			getForm : function() {
				return this.form;
			}
		});

/**
 * **************************************************查看**************************************************
 */
/**
 * 查看所用formpanel
 */
dm.base.DictionaryInfoForm = function(grid, win) {

	var dictionaryInfo = this;
	// 主页表格
	dictionaryInfo.grid = grid;
	// 当前窗口
	dictionaryInfo.win = win;
	// 窗口属性
	
	dm.base.DictionaryInfoForm.superclass.constructor.call(this, {
		labelAlign : 'right',
		labelWidth : 80,
		buttonAlign : 'center',
		bodyStyle : 'padding:5px 0px 0px',
		frame : false,
		//layout : 'form',
		defaultType : 'textfield',
		labelSeparator : '：',
		reader : new Ext.data.JsonReader({
					successProperty : 'success',
					root : 'dictionary'
				}, [{
							name : 'id'
						}, {
							name : 'attr_name'
						}, {
							name : 'attr_key'
						}, {
							name : 'attr_value'
						}, {
							name : 'description'
						}, {
							name : 'seq'
						}, {
							name : 'remark'
						}, {
							name : 'creator_id'
						}, /*{
							name : 'create_time',
							convert : function(v) {
								return new Date(v).format("yyyy-MM-dd hh:mm:ss");
							}
						}, */{
							name : 'creator_name'
						}, {
							name : 'editMode'
						}, {
							name : 'state'
						}]),
		items : [{
					// id
					name : 'id',
					hidden : true,
					hiddenLabel : true
				}, {
					// 角色名称
					fieldLabel : '属性',
					anchor : '97%',
					name : "attr_name",
					style : "background:#f1f1f1",
					readOnly : true

				}, {
					// 职能
					fieldLabel : '属性名',
					anchor : '97%',
					name : "attr_value",
					style : "background:#f1f1f1",
					readOnly : true
				}, {
					// 类型
					fieldLabel : '属性值',
					anchor : '97%',
					name : "attr_key",
					style : "background:#f1f1f1",
					readOnly : true
				}, {
					// 父类名称
					fieldLabel : '排序',
					anchor : '97%',
					name : "seq",
					style : "background:#f1f1f1",
					readOnly : true
				}, {
					// 状态
					fieldLabel : '状态',
					anchor : '97%',
					style : "background:#f1f1f1",
					readOnly : true,
					xtype : 'combo',
					width : 150,
					hiddenName : "state",
					store : new Ext.data.ArrayStore({
								fields : ['value', 'text'],
								data : [['1', '启用'],
										['2', "禁用"]]
							}),
					displayField : 'text',
					valueField : 'value',
					mode : 'local'

				}, {
					// 创建人名称
					fieldLabel : '编辑模式',
					anchor : '97%',
					style : "background:#f1f1f1",
					readOnly : true,
					xtype : 'combo',
					width : 150,
					hiddenName : "editMode",

					store : new Ext.data.ArrayStore({
								fields : ['value', 'text'],
								data : [
										['1', '可编辑'],
										['2', '只读']]
							}),
					displayField : 'text',
					valueField : 'value',
					mode : 'local'
				}, {
					// 创建人名称
					fieldLabel : '创建人名称',
					anchor : '97%',
					name : "creator_name",
					style : "background:#f1f1f1",
					readOnly : true
				}, {
					// 创建时间
					fieldLabel : '创建时间',
					anchor : '97%',
					name : "create_time",
					style : "background:#f1f1f1",
					readOnly : true
				}, {
					// 
					fieldLabel : '描述',
					xtype : 'textarea',
					anchor : '97%',
					height : 80,
					name : "description",
					style : "background:#f1f1f1",
					readOnly : true
				}, {
					// 
					fieldLabel : "备注",
					xtype : 'textarea',
					anchor : '97%',
					height : 80,
					name : "remark",
					style : "background:#f1f1f1",
					readOnly : true
				}],
		buttons : [{
					// 关闭
					text : '关闭',
					iconCls : 'icon-shutdown',
					listeners : {
						'click' : function() {
							dictionaryInfo.win.close();
						}
					}
				}]
	});
};
/**
 * 继承
 * 
 * @class 
 * @extends Ext.form.FormPanel
 */
Ext.extend(dm.base.DictionaryInfoForm, Ext.form.FormPanel, {});

/**
 * 查看的窗口
 */
dm.base.EventInfoDictionaryWin = function(title, grid) {
	var eventInfoDictionaryWin = this;
	eventInfoDictionaryWin.grid = grid;
	eventInfoDictionaryWin.title = title;
	// 查询

	//if ('查询' == title) {
		eventInfoDictionaryWin.form = new dm.base.DictionaryInfoForm(grid,eventInfoDictionaryWin);
	//}
	// 查询窗口的属性
	//console.info("form : ",eventInfoDictionaryWin.form);
	dm.base.EventInfoDictionaryWin.superclass.constructor.call(this, {

				layout : 'fit',
				width : 450,
				height : 460,
				resizable : false,
				border : false, // 边框线设置
				maximizable : false,
				constrain : true,
				// closeAction : 'hide',
				closable : true,
				collapsible : false,
				plain : true,
				modal : true,
				buttonAlign : 'center',
				title : eventInfoDictionaryWin.title,
				items : [eventInfoDictionaryWin.form]
			});
};
/**
 * 新窗口的继承
 * 
 * @class EventInfoJobWin
 * @extends Ext.Window
 */
Ext.extend(dm.base.EventInfoDictionaryWin, Ext.Window, {
			getForm : function() {
				return this.form;
			}
		});
