Ext.namespace('dm.equip.pile');

/**
 * 主面板
 */
var mainPanel;
dm.equip.pile.MainPanel = function(projectRecord) {
	mainPanel = this;
	mainPanel.autoScroll = true;
	mainPanel.gridPanel = new dm.equip.pile.GridPanel();
	mainPanel.gridPanel.parent = mainPanel;
	mainPanel.searchPanel = new dm.equip.pile.SearchPanel(mainPanel.gridPanel, projectRecord);
	/**
	 * 标签面板的构造函数
	 */
	dm.equip.pile.MainPanel.superclass.constructor.call(this, {
				title : '成桩列表',
				layout : 'border',
				baseCls : "x-plain",
				closable : true,
				items : [mainPanel.searchPanel, mainPanel.gridPanel]
			});
	mainPanel.on('afterrender', function() {
				// 加载表格数据
				if(projectRecord){
					mainPanel.gridPanel.searchConditions.projectId = projectRecord.data.projectId;
					mainPanel.gridPanel.loadData(projectRecord);
				}else{
					mainPanel.gridPanel.loadData();
				}
				
			});

};
Ext.extend(dm.equip.pile.MainPanel, Ext.Panel, {});

/**
 * 搜索面板
 */
var searchPanel;
dm.equip.pile.SearchPanel = function(grid,projectRecord) {
	searchPanel = this;
	searchPanel.grid = grid;
	searchPanel.projectRecord=projectRecord;
	//alert(projectRecord.data.projectId);
	searchPanel.searchConditions = {
    		 projectId : projectRecord.data.projectId
    	};

	dm.equip.pile.SearchPanel.superclass.constructor.call(this, {
		height : 100,
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
			items : [
			 {
				columnWidth : .3,
				items : [
				{
                    xtype: "label",
                    height: 30,
                    forId: "projectLabel",
                    text: "项目名称:    ",
                    },
                {
                    xtype: "label",
                    height: 30,
                    forId: "projectName",
                    text: projectRecord.data.projectName,
                    },
				{
					id : 'sectionId',
					xtype : 'combo',
					fieldLabel : '标段',// '操作人',
					width : 200,
					displayField : 'sectionName',
					valueField : 'sectionId',
					mode : 'remote',
					triggerAction : 'all',
					selectOnFocus : true,
					forceSelection : true,
					editable : false,
					store :  new Ext.data.Store({
                            				proxy : new Ext.data.HttpProxy({
                            							url : 'resource/SectionService/searchByProject',
                            							method : 'post'
                            						}),
                            				reader : new Ext.data.JsonReader({
                            							totalProperty : 'total',
                            							root : 'sections'
                            						}, [{
                            									name : 'sectionId'
                            								}, {
                            									name : 'sectionName'
                            								},{
                            									name : 'sectionLeader'
                            								}
                            								]),
                                            baseParams : searchPanel.searchConditions
                            			}),
					listeners : {
					//alert("sectionId="+Ext.getCmp("sectionId").getValue().trim());
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
							maxLength : 50,
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
				items : [
                         {
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
						},{
							iconCls : 'icon-print',
							text : '打印',// '打印',
							xtype : 'button',
							// funcCode :
							// DM.FUNCCODE.SYSTEMMONITOR_USECONDITION_RESET,
							handler : function() {
								var projectName = projectRecord.data.projectName;
								var projectBuilder= projectRecord.data.projectBuilder;
								var projectSupervisor=projectRecord.data.projectSupervisor;
								var contractNumber=projectRecord.data.contractNumber;
								//var pileDriverNumber = Ext.getCmp("pileDriverNumber").getValue().trim();
								var pileNumber = Ext.getCmp("pileNumber").getValue().trim();
								var sectionName = Ext.getCmp("sectionId").getRawValue();
								var startTime = dm.comm.comm_ConvertStringToDate(Ext.getCmp("startTime").getValue());
								if(startTime == null){
									startTime = '';
								}
						        var endTime = dm.comm.comm_ConvertStringToDate(Ext.getCmp("endTime").getValue());
						        if(endTime == null){
						        	endTime = '';
								}
								printGrid(projectName, projectBuilder, projectSupervisor, contractNumber, sectionName, pileNumber, startTime, endTime);
							}
						}]
			}]
		}]
	});
};

Ext.extend(dm.equip.pile.SearchPanel, Ext.form.FormPanel, {
			searchData : function() {
				var searchPanel = this;
				if (!searchPanel.getForm().isValid()) {
					Ext.Msg.alert('提示', "查询语句输入有误！");
					return;

				}
				//alert( Ext.getCmp("sectionId").getValue());
				var grid = searchPanel.grid;
				if(getCmp("pileNumber")!=null)
				  grid.searchConditions.pileNumber = Ext.getCmp("pileNumber").getValue().trim();
				grid.searchConditions.sectionNumber = Ext
						.getCmp("sectionId").getValue();
				//alert( grid.searchConditions.sectionNumber );
				grid.searchConditions.pileDriverNumber = Ext
						.getCmp("pileDriverNumber").getValue().trim();
				grid.searchConditions.startTime = dm.comm.comm_ConvertStringToDate(Ext
						.getCmp("startTime").getValue());
				grid.searchConditions.endTime = dm.comm.comm_ConvertStringToDate(Ext
						.getCmp("endTime").getValue());
				//alert(grid.searchConditions.endTime);
				grid.loadData();
			}
		});

var gridPanel;

function printGrid(projectName, projectBuilder,projectSupervisor,contractNumber,sectionName, pileNumber, startTime, endTime){
	
	var myWindow = window.open('', '', 'width=1200,height=auto');
	myWindow.document.write('<html><head>');
	//myWindow.document.write('<title>' + 'Title' + '</title>');
	//myWindow.document.write('<link rel="Stylesheet" type="text/css" href="component/tablestyle/table-style.css" />');
	myWindow.document.write('<link rel="Stylesheet" type="text/css" href="ext/resources/css/ext-all.css" />');
	myWindow.document.write('<script type="text/javascript" src="component/bootstrap/bootstrap.js"></script>'); 
	myWindow.document.write('</head><body>'); 
	var searchConditions = '<div><b><span style="font-size:25px;display:block;width:100%;text-align:center;">钉形水泥土双向搅拌桩现场记录表</span></b> ';
	    searchConditions += '<br>';

	    searchConditions += '<span style="display:block;width:100%;font-size:18px;text-align:center;">项目名称: ' + projectName + '</span> ';
		//searchConditions += ' &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; ';
		searchConditions += '<br>';
		searchConditions += '<span>承包单位: ' + projectBuilder + '</span> ';
        searchConditions += ' &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; ';
		searchConditions += '<span>监理单位: ' + projectSupervisor + '</span> ';
        searchConditions += ' &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; ';
        searchConditions += '<span>合同编号: ' + contractNumber + '</span> ';
        searchConditions += ' &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; ';

		searchConditions += '<br>';
		searchConditions += '<span>标段: ' + sectionName + '</span> ';
        searchConditions += ' &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; ';
		searchConditions += '<span>桩机编号: ' + pileDriverNumber + '</span> ';
		searchConditions += ' &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; ';
		searchConditions += '<span>开始时间: ' + startTime + '</span> ';
		searchConditions += ' &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; ';
		searchConditions += '<span>结束时间: ' + endTime + '</span></div>';
		searchConditions += '<div> &nbsp;&nbsp; </div>';
	
	myWindow.document.write(searchConditions);
	myWindow.document.write(gridPanel.body.dom.innerHTML.replace(/border=\"0\"/g, 'border=\"1\"').replace('cellspacing=\"0\"', 'cellspacing=\"2\"'));
	//myWindow.document.write(gridPanel.body.dom.innerHTML);
	var bottomSection='<span>记录: '+'_______________'+ '</span> ';
	    bottomSection+=' &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; ';
	    bottomSection+='<span>机长: '+'_______________'+ '</span> ';
        bottomSection+=' &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; ';
        bottomSection+='<span>施工技术员: '+'______________'+ '</span> ';
        bottomSection+=' &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; ';
        bottomSection+='<span>场旁站: '+'______________'+ '</span> ';
        bottomSection+=' &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; ';
        bottomSection+='<span>监理: '+'______________'+ '</span> ';
        bottomSection+=' &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; ';
    myWindow.document.write(bottomSection);
	myWindow.document.write('</body></html>');
	setTimeout(function() {myWindow.print();},1000);
	//myWindow.print();
	
}
/**
 * 列表面板
 */
dm.equip.pile.GridPanel = function() {
	gridPanel = this;
	gridPanel.searchConditions = {
		 pileDriverNumber : "",
		 /*startTime : "",
		 endTime : "",*/
		 pileNumber : '',
		 sectionNumber : ''
	};
	gridPanel.autoHeight = true;
	gridPanel.autoWidth = true;
	gridPanel.border=true;
	gridPanel.columnLines=true;

	// 勾选框
	gridPanel.sm = new Ext.grid.CheckboxSelectionModel();
	// 每页显示条数下拉选择框
	gridPanel.pagesize_combo = new dm.object.pagesize_combo();
	// 每页显示的条数
	gridPanel.pageSize = parseInt(gridPanel.pagesize_combo.getValue());
	gridPanel.store = new Ext.data.Store({
				proxy : new Ext.data.HttpProxy({
							url : 'resource/pileManager/search',
							method : 'post'
						}),
				reader : new Ext.data.JsonReader({
							totalProperty : 'total',
							root : 'piles'
						}, [{
									name : 'id'
								}, {
									name : 'score'
								},{
									name : 'scoreMark'
								},{
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
									name : 'pileStatus'
								},{
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
	/*{
		header : 'ID',
		dataIndex : "id",
		width : 48,
		renderer : function(value) {
			return dm.comm.comm_getTip(value);
		}
	},*/ {
		header : '桩编号',
		dataIndex : "pileNumber",
		width : 42,
		renderer : function(value) {
			return dm.comm.comm_getTip(value);
		}
	}, {
		header : '开始时间',
		dataIndex : "startTime",
		width : 70,
		renderer : function(value) {
			return dm.comm.comm_getTip(value);
		}
	},{
		header : '结束时间',
		dataIndex : "endTime",
		width : 70,
		renderer : function(value) {
			return dm.comm.comm_getTip(value);
		}
	},{
		header : '实际桩长(m)',
		dataIndex : "pileLength",
		width : 48,
		renderer : function(value) {
			return dm.comm.comm_getTip(value);
		}
	}/*,{
		header : '喷浆时间(s)',
		dataIndex : "gunitingSecond",
		width : 50,
		renderer : function(value) {
			return dm.comm.comm_getTip(value);
		}
	}*/,{
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
		header : '提钻速度(cm/min)',
		dataIndex : "maxUpSpeed",
		width : 60,
		renderer : function(value) {
			return dm.comm.comm_getTip(value);
		}
	},{
		header : '下钻速度(cm/min)',
		dataIndex : "maxDownSpeed",
		width : 60,
		renderer : function(value) {
			return dm.comm.comm_getTip(value);
		}
	},{
		header : '最大内电流(A)',
		dataIndex : "maxInsidePower",
		width : 55,
		renderer : function(value) {
			return dm.comm.comm_getTip(value);
		}
	},{
		header : '最大外电流(A)',
		dataIndex : "maxOutsidePower",
		width : 55,
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
	},/*{
		header : '机号',
		dataIndex : "pileDriverNumber",
		width : 45,
		renderer : function(value) {
			return dm.comm.comm_getTip(value);
		}
	},*/ {
     		header : '评分',
     		dataIndex : "score",
     		width : 35,
     		renderer : function(value) {
     			return dm.comm.comm_getTip(value);
     		}
     	}/*, {
     		header : '评分结果',
     		dataIndex : "scoreMark",
     		width : 60,
     		renderer : function(value) {
     			return dm.comm.comm_getTip(value);
     		}
     	}*//*,{
		header : '上传时间',
		dataIndex : "createTime",
		width : 100,
		renderer : function(value) {
			return dm.comm.comm_getTip(value);
		}
	}*/
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
	dm.equip.pile.GridPanel.superclass.constructor.call(this, {
				autoWidth : true,
				border:1,
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
				tbar : [ {
					xtype : 'combo',
					value : '1',
					id:'evalId',
					store : new Ext.data.ArrayStore({
								fields : ['value', 'text'],
								data : [['1', '普通桩（固定桩长）'],['2', "普通桩（不定桩长）"],
										['3', "钉型桩（固定桩长）"],['4', "钉型桩（不定桩长）"]]
							}),
					displayField : 'text',
					valueField : 'value',
					mode : 'local',
					triggerAction : 'all',
					selectOnFocus : true,
					forceSelection : true,
					editable : false
				},{
					text : '评分', // 删除
					iconCls : 'icon-assignRole',
					funcCode : '',
					handler : function() {
						gridPanel.evaluation();
					}
				}],
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
	                	   
	                       /*return 'x-grid-record-red';
	                       return '';*/
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
							
							
							pile2pilePerDeep(record.data.pileNumber);
							/*var cmp = mainTab.getComponent("ext-iframe-252");
							if (cmp){
								mainTab.remove(cmp);
							}
							var tmp = new dm.equip.pile_perdeep.MainPanel(record.data.pileNumber);
							tmp.setTitle("单桩分析");
							
							tmp.id = "ext-iframe-252";
							tmp.iconCls = "icon-module_252";
							gridPanel.parent.frameParent.add(tmp);
							gridPanel.parent.frameParent.loadTab(tmp);*/
							
						});
						
					}
				}
			});
	gridPanel.store.on('beforeload', function() {
				gridPanel.store.baseParams = gridPanel.searchConditions;
			});
};
Ext.extend(dm.equip.pile.GridPanel, Ext.grid.GridPanel, {
	evaluation:function(){
		var gird = this;
		var len = this.getSelectionModel().getSelections().length;
		if (len == 0) {
			Ext.Msg.alert('提示', '请选择一条记录');
			return;
		}
		var lines = this.getSelectionModel().getSelections();
		var ids='';
		for (i = 0; i < lines.length; i++) {
			id = lines[i].get('id');
			if (i == 0)
				ids = ids + id;
			else
				ids = ids + "," + id;
		}
		var evalId = Ext.getCmp("evalId").getValue();
		console.log(ids);
		console.log(evalId);
		Ext.Ajax.request({
			url:'resource/EvaluationService/evaluation',
			method : 'POST',
			params:{
				ids:ids,
				evalId:evalId
			},
			waitMsg : '请稍等',
			timeout : 2000,
			success : function(response) {
				var json = Ext.util.JSON.decode(response.responseText);
				var flag = json.success;
				if (flag) {
					gird.store.reload();
					dm.comm.comm_alert({
							msg : '评分成功'
						});
				}else{
					dm.comm.comm_alert({
							msg : '评分失败'
						});
				}
			},
			failure : function(response, action) {
			}
		});
	},
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
	}
});

/*dm.equip.pile.evalPanel = function(){
	
};
Ext.extend();*/




dm.equip.pile.bottom= function(){
	
	var mainPanel = this;
	/**
	 * 标签面板的构造函数
	 */
	dm.equip.pile.bottom.superclass.constructor.call(this, {
				fitToFrame: true, 
				height : 100,
				region : 'south',
		        html: '<iframe  src="business/working/pileBottom.html" frameborder="0" width="100%" height="100%"></iframe>',
				closable : true
			});
};

Ext.extend(dm.equip.pile.bottom,Ext.Panel, {});