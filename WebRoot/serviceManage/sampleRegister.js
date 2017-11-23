Ext.namespace('dm.sampleRegister');

dm.sampleRegister.grid = function() {
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
			name : 'sample.goods_typeText',
			mapping : 'goods_typeText'
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
		}, {
			name : 'sample.chargebackreason',
			mapping : 'chargebackreason'
		}, {
			name : 'sample.acceptor',
			mapping : 'acceptor'
		}, {
			name : 'sample.acceptdate',
			mapping : 'acceptdate'
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
	}, {
		header : '退回理由',
		dataIndex : "sample.chargebackreason",
		renderer : function(value) {
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
	dm.sampleRegister.grid.superclass.constructor.call(this, {
		title : '样品登记',
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
			text : '基本信息登记', 
			iconCls : 'icon-add',
			funcCode : '',
			handler : function() {
				grid.addWin();
			}
		}, {
			xtype : "tbseparator"
		}, {
			text : '小样信息登记', 
			iconCls : 'icon-add',
			funcCode : '',
			handler : function() {
				grid.addWin_small();
			}
		}, {
			xtype : "tbseparator"
		}, {
			text : '删除',
			iconCls : 'icon-delete',
			funcCode : '',
			handler : function() {
				grid.deleteWin();
			}
		}, {
			xtype : "tbseparator"
		}, {
			text : '送检',
			iconCls : 'icon-search',
			funcCode : '',
			handler : function() {
				grid.submitWin();
			}
		}, {
			xtype : "tbseparator"
		}, {
			text : '打印送样单',
			iconCls : 'icon-search',
			funcCode : '',
			handler : function() {
				grid.printWin();
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
			searchName : Ext.getCmp('n').getValue()
		};
	});
	grid.loadData();
};

Ext.extend(dm.sampleRegister.grid, Ext.grid.GridPanel, {
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
	addWin : function() {
		var sampleGrid = this;
		var record = this.getSelectionModel().getSelections();
		if(record.length != 0){
			if( record[0].get('sample.status') == 0 ||record[0].get('sample.status') == 2){
				var win = new dm.sampleRegister.addWin('送样单--基本信息登记',
						'resource/sampleRegister/registSample', record[0], '登记', sampleGrid);
				win.show();
			}else{
				Ext.Msg.alert('提示窗口', '对不起，样品已送检，不能修改送样单基本信息！');
			}
		}else{
			var win = new dm.sampleRegister.addWin('送样单--基本信息登记',
					'resource/sampleRegister/registSample', record[0], '登记', sampleGrid);
			win.show();
		}
	},
	addWin_small : function() {
		var sampleGrid = this;
		
		var record = sampleGrid.getSelectionModel().getSelections();
		if (record.length == 0) {
			Ext.Msg.alert('提示窗口', '请先选择您要操作的行！');
			return;
		} else {
			var status = record[0].get('sample.status');
			if (status != 0 && status != 2) {
				Ext.Msg.alert('提示窗口', '对不起，样品已送检，不能添加或修改小样！');
			} else {
				var win = new dm.sampleRegister.addWin_small(record[0].get('sample.id'));
				win.show();
			}
		}
	},
	searchWin : function() {
		var grid = this;

		var win = new dm.sampleRegister.searchWin(this);
		win.show();
	},
	deleteWin : function() {
		var sampleGrid = this;

		var record = sampleGrid.getSelectionModel().getSelections();
		if (record.length == 0) {
			Ext.Msg.alert('提示窗口', '请先选择您要操作的行！');
			return;
		} else {
			var status = record[0].get('sample.status');
			if (status != 0 && status != 2) {
				Ext.Msg.alert('提示窗口', '对不起，样品已送检，不能删除！');
			} else {
				var ids = "";
				var length = record.length;
				for ( var i = 0; i < length; i++) {
					ids += record[i].get("sample.id");
					if (i < record.length - 1) {
						ids = ids + ",";
					}
				}
				$.ajax({
					async : false,
					type : 'post',
					data : {
						ids : ids
					},
					url : 'resource/sampleRegister/deleteSample',
					success : function(returnData, status) {
						if (returnData.success == false) {
							Ext.Msg.alert('提示窗口', '删除失败！');
						} else {
							Ext.Msg.alert('提示窗口', '删除成功！');
							sampleGrid.loadData();
						}
						// });
					},
					failure : function() {
						Ext.Msg.alert(' 错误', '服务器出现错误请稍后再试！');
					}
				});
			}
		}
	},
	submitWin : function() {
		var sampleGrid = this;

		var record = sampleGrid.getSelectionModel().getSelections();
		if (record.length == 0) {
			Ext.Msg.alert('提示窗口', '请先选择您要操作的行！');
			return;
		} else {
			var ids = '';
			var warnings = '';
			var length = record.length;
			for ( var i = 0; i < length; i++) {
				var status = record[i].get("sample.status");
				if (status == 0 ||status == 2) {
					if(ids != ''){
						ids += ',';
					}
					ids += record[i].get("sample.id");
				}else{
					warnings += record[i].get("sample.submit_no");
					warnings += ',';
				}
			}
			$.ajax({
				async : false,
				type : 'post',
				data : {
					ids : ids
				},
				url : 'resource/sampleRegister/submitSample',
				success : function(returnData, status) {
					if (returnData.success == false) {
						Ext.Msg.alert('提示窗口', '送检失败！请检查是否至少登记了一个小样！');
					} else {
						Ext.Msg.alert('提示窗口', '送检成功！');
						sampleGrid.loadData();
					}
					// });
				},
				failure : function() {
					Ext.Msg.alert('提示窗口', '提交失败！');
				}
			});
			if(warnings.length != 0){
				Ext.Msg.alert('提示窗口',warnings+ '没有送检成功！');
			}
		}
	},
	printWin: function(){
		var sampleGrid = this;
		var record = sampleGrid.getSelectionModel().getSelections();
		if (record.length == 0) {
			Ext.Msg.alert('提示窗口', '请先选择您要操作的行！');
			return;
		} else {
			printSample(record[0]);
		}
	}
});

function printSample(record){
	var demo = [];
	var demo_tmp = '';
	var count = 0;
	var pagesize = 15;
	$.ajax({
		async : false,
		type : 'post',
		data : {
			id : record.get('sample.id')
		},
		url : 'resource/sampleRegister/getDemo',
		success : function(returnData, status) {
			if (returnData.success == true) {
				$.each(returnData.list,function(idx, item){
					demo_tmp =demo_tmp + '<tr><td class="auto-style1">&nbsp;'+item.no
					+'</td><td class="auto-style1">&nbsp;'+item.name
					+'</td><td  class="auto-style2">&nbsp;'+item.total
					+'</td><td  class="auto-style2">&nbsp;'+item.requirement
					+'</td><td class="auto-style2">&nbsp;'+item.memo+'</td></tr>';
					count++;
					if(count % pagesize == 0 ){
						demo.push(demo_tmp);
						demo_tmp = '';
					}
    			});
			}
		},
		failure : function() {
			Ext.Msg.alert('提示窗口', '！');
		}
	});
	for(var j = count % pagesize; j < pagesize; j++){
		demo_tmp += '<tr><td class="auto-style1">&nbsp;</td><td class="auto-style1">&nbsp;</td><td  class="auto-style2">&nbsp;</td><td  class="auto-style2">&nbsp;</td><td class="auto-style2">&nbsp;</td></tr>';
	}
	demo.push(demo_tmp);
	var page = demo.length;
	
	var html1 = '<style type="text/css">'+
	'.maindiv {text-align: center; width: 650px;}'+
	'.maindiv .subdiv {width: 650px;}'+
	'.maindiv .subdiv {width: 650px;}'+
	'.maindiv .subdiv {width: 650px;}'+
	'.maindiv .subdiv {width: 650px;}'+
	'.table {text-align: left; font-size:12.0pt; border-collapse:collapse; border-spacing:0;border-color:black;}'+
	'.border-style{border-color:black;}'+
	'.auto-style1 {width: 80px;height: 25px;border-color:black;}'+
	'.auto-style2 {width: 160px; border-color:black;}'+
	'.auto-style5 {height: 120px;width: 80px; border-color:black;}'+
	'.auto-style6 {height: 120px; border-color:black;}'+
	'.auto-style7 {height: 50px; border-color:black;}'+
	'.auto-style8 {height: 25px; border-color:black;}'+
	'.auto-style9 {width: 160px;height: 25px; border-color:black;}</style>'+
	'<div class ="maindiv">'+
	'<p style="text-align:center;line-height:20.0pt;">'+
	'<b ><span style="font-size:16.0pt;font-family:宋体;letter-spacing:2.1pt">'+
	'南通出入境检验检疫局有害生物检疫实验室</span></b></p>'+
	'<p style="text-align:center;line-height:18.0pt;">'+
	'<b><span style="font-size:14.0pt;font-family:宋体;letter-spacing:2.1pt">送样单</span></b></p>'+
	'<div class ="subdiv">'+
	'<p style="text-align:right;line-height:15.0pt;">编号：'+record.get('sample.submit_no')+'</p>'+
	'<table cellSpacing =0 cellPadding =0 border = 1 class ="table">'+
	'<tr><td colspan="2" class="auto-style8" >*送样单位: '+record.get('sample.submit_depart')+
	'</td><td  class="auto-style9">*送样人：'+record.get('sample.submitter')+
	'</td><td  class="auto-style9">*电话：'+record.get('sample.submit_phone')+
	'</td><td class="auto-style9" >*送样日期：'+record.get('sample.submit_date')+'</td></tr>'+
	'<tr><td colspan="2"  class="auto-style8">*样品名称：'+record.get('sample.sample_name')+
	'</td><td colspan="2" class = "border-style">*样品数/重量：'+record.get('sample.sample_num')+
	'</td><td class="auto-style2">*产地：'+record.get('sample.sample_region')+'</td></tr>'+
	'<tr><td colspan="2" class="auto-style8" >*样品描述：'+record.get('sample.sample_detail')+
	'</td><td colspan="2" class = "border-style">*输入/输出国家或地区：'+record.get('sample.export_import')+
	'</td><td class="auto-style2">*报检号：'+record.get('sample.verify_no')+'</td></tr>'+
	'<tr><td colspan="2" class="auto-style8" >*送样标识：'+record.get('sample.verify_identify')+
	'</td><td colspan="2" class = "border-style">收样人：'+record.get('sample.acceptor')+
	'</td><td class="auto-style2">收样日期：'+record.get('sample.acceptdate')+'</td></tr>'+
	'<tr><td colspan="3" class="auto-style8">*检验检疫项目(周期)：'+record.get('sample.verify_circle')+
	'</td><td colspan="2" class = "border-style">检验依据：'+record.get('sample.submit_phone')+'</td></tr>'+
	'<tr><td class="auto-style1">*小样编号</td><td class="auto-style1">*小样名称</td><td  class="auto-style2">*数/重量</td><td  class="auto-style2">*具体检验检疫要求</td><td class="auto-style2">*备注</td></tr>';
	
	var html2 = '<tr style =" text-align: left;"><td colspan="5" class="auto-style7">补充说明：</td></tr>'+
	'<tr style =" text-align: left;"><td colspan="5" class="auto-style7">实验室评审</td></tr>'+
	'<tr style =" text-align: left;"><td class="auto-style6">样品和送样单接收：</td><td class="auto-style5">样品和送样单的传递：</td>'+
	'<td class="auto-style6">送样单和样品检验检疫记录交合同评审员：</td>'+
	'<td class="auto-style6">报告签发：</td><td class="auto-style6">报告发出：</td></tr>'+
	'</table>';
	
	var html3 = '</div>'+
	'</div>';
	LODOP=getLodop(document.getElementById('LODOP'),document.getElementById('LODOP_EM'));  
	LODOP.PRINT_INIT("打印控件功能演示_Lodop功能_打印图片1");
	for(var i = 0; i < page; i++){
		var t = '<p style="text-align:right;line-height:12.0pt;">第'+i+'页，一共'+page+'页</p>';
		LODOP.ADD_PRINT_IMAGE(20,20,650,1000,
				html1 + demo[i] + html2 + t + html3);
		LODOP.NewPage();
	}
	LODOP.PREVIEW();	
}

dm.sampleRegister.searchWin = function(sampleGrid) {
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
	dm.sampleRegister.searchWin.superclass.constructor.call(this, {
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
Ext.extend(dm.sampleRegister.searchWin, Ext.Window);

dm.sampleRegister.addWin = function(title, url, record, result, sampleGrid) {
	var win = this;
	
	if(record != null){
		url = 'resource/sampleRegister/updateSample';
		result = '更新';
	}

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

	win.form = new Ext.form.FormPanel({
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
							xtype : "textfield",
							fieldLabel : "送样单位",
							name : "sample.submit_depart",
							allowBlank : false,
							blankText : '不能为空！',
							emptyText : '不能为空！',
							width : 200
						}
					}, {
						columnWidth : '.55',
						layout : 'form',
						labelWidth : 150,
						items : {
							xtype : "textfield",
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
							xtype : "textfield",
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
							xtype : "datefield",
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
							xtype : "textfield",
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
							xtype : "textfield",
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
							xtype : "textfield",
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
							xtype : "textfield",
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
							xtype : "textfield",
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
							xtype : "textfield",
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
							xtype : "textfield",
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
							xtype : "textfield",
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
							xtype:"combo",
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
				        	width:200,
							listeners:{
								'select': function(combo, record, index){ 
									if(combo.value == 1) {
										var columns = myCheckboxGroup.columns.length;  
						                var items = myCheckboxGroup.items;  
						                var length = items.getCount();  
						                for ( var i = 0; i < length; i++) {  
						                    var delItems = myCheckboxGroup.items.items[0];  
						                    delItems.destroy();  
						                    myCheckboxGroup.items.remove(delItems);  
						                }  
						                var col = myCheckboxGroup.panel.items.get(0);  
						                col = myCheckboxGroup.panel.items.get(0 % columns);  
						                var chk1 = new Ext.form.Checkbox( {boxLabel: '昆虫', name: 'kunchong', inputValue : 1});
						                col.add(chk1);  
						                myCheckboxGroup.items.add(chk1);  
						                col = myCheckboxGroup.panel.items.get(1 % columns);  
						                var chk2 =  new Ext.form.Checkbox({boxLabel: '线虫', name: 'xianchong', inputValue : 2});
						                col.add(chk2);  
						                myCheckboxGroup.items.add(chk2);
						                col = myCheckboxGroup.panel.items.get(2 % columns);  
						                var chk3 =  new Ext.form.Checkbox({boxLabel: '其他', name: 'other', inputValue : 3});
						                col.add(chk3);  
						                myCheckboxGroup.items.add(chk3);
						                myCheckboxGroup.panel.doLayout();  
									} else {
										var columns = myCheckboxGroup.columns.length;  
						                var items = myCheckboxGroup.items;  
						                var length = items.getCount();  
						                for ( var i = 0; i < length; i++) {  
						                    var delItems = myCheckboxGroup.items.items[0];  
						                    delItems.destroy();  
						                    myCheckboxGroup.items.remove(delItems);  
						                }  
						                var col = myCheckboxGroup.panel.items.get(0);  
						                col = myCheckboxGroup.panel.items.get(0 % columns);  
						                var chk1 =  new Ext.form.Checkbox({boxLabel: '杂草', name: 'zacao', inputValue : 1}); 
						                col.add(chk1);  
						                myCheckboxGroup.items.add(chk1);  
						                col = myCheckboxGroup.panel.items.get(1 % columns);  
						                var chk2 =  new Ext.form.Checkbox({boxLabel: '病害', name: 'binghai', inputValue : 2});  
						                col.add(chk2);  
						                myCheckboxGroup.items.add(chk2);
						                col = myCheckboxGroup.panel.items.get(2 % columns);  
						                var chk3 =  new Ext.form.Checkbox({boxLabel: '其他', name: 'other', inputValue : 3});
						                col.add(chk3);  
						                myCheckboxGroup.items.add(chk3);
						                myCheckboxGroup.panel.doLayout(); 
									}
								}
							}
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
							xtype : "textarea",
							fieldLabel : "补充说明",
							allowBlank : false,
							name : "sample.memo",
							width : 603
						}
					},{
						layout : 'form',
						items : {
							xtype : "textfield",
							fieldLabel : "id",
							hidden: true,
							name : "sample.id",
							width : 603
						}
					},{
						layout : 'form',
						items : {
							xtype : "textfield",
							fieldLabel : "项目",
							hidden: true,
							id: 'project_id', 
							name : "sample.verify_projects",
							width : 603
						}
					} ]
				} ]
			}]
		}],
		buttons : [ {
			text : '确定',
			listeners : {
				'click' : function() {
					var projects = [];    
                    var cbitems = myCheckboxGroup.items;    
                    for (var i = 0; i < cbitems.length; i++) {    
                        if (cbitems.itemAt(i).checked) {    
                        	projects.push(cbitems.itemAt(i).name);    
                        }    
                    }  
            //        alert(projects);
                    Ext.getCmp('project_id').setValue(projects);
                    myCheckboxGroup.setValue(projects);
					if (win.form.getForm().isValid()) {
						win.form.getForm().doAction('submit', {
							url : url,
							method : 'post',
							waitMsg : '正在加载',
							timeout : 2000,
							success : function(response) {
								Ext.Msg.alert('提示窗口', result + '成功！');
								sampleGrid.loadData();
								win.close();
							},
							failure : function(response, action) {
								Ext.Msg.alert('提示窗口', result + '失败！');
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
	if(record != null){
		win.form.getForm().loadRecord(record);
		
		var goodtype = Ext.getCmp('goodtype');
		goodtype.setValue(record.get('sample.goods_type'));
		goodtype.setRawValue(record.get('sample.goods_typeText'));
		
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
	
	dm.sampleRegister.addWin.superclass.constructor.call(this, {
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
Ext.extend(dm.sampleRegister.addWin, Ext.Window);

dm.sampleRegister.addWin_small = function(id){
	 var win = this;
	 
	 var button_text = '添加/更新';
	 var url = 'resource/sampleRegister/registDemo';
	 
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
				{ name: 'small.memo',mapping:'memo' }
			]),
			remoteSort: true
	});
		
	var cm = new Ext.grid.ColumnModel([
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
		bbar:new Ext.PagingToolbar({ //实现分页
			pageSize:10,
			store: storeconf,
			displayInfo: true,
			displayMsg:'显示第{0}条到第{1}条记录，一共{2}条',
			emptyMsg: '没有数据'
		}),
		listeners:{  
			rowdblclick : function(grid,row){ 
				var selectionModel = grid.getSelectionModel();
				var record = selectionModel.getSelected();
				url = 'resource/sampleRegister/updateDemo';
				win.form.getForm().loadRecord(record);
			}
		}
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
         title: '管理小样',
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
					 columnWidth : '.33'
				 },				
				 items : [{
					 layout : 'form',
					 items: {
						 xtype:"textfield",
					     fieldLabel:"小样编号",
					     name:"small.no",
					     allowBlank: false,
					     blankText : '小样编号不能为空！',
					     emptyText: '小样编号不能为空！',
				         width: 160
					 }
				 }, {
					 layout : 'form',
					 items: {
						 xtype:"textfield",
					     fieldLabel:"小样名称",
					     name:"small.name",
					     allowBlank: false,
					     blankText : '小样名称不能为空！',
					     emptyText: '小样名称不能为空！',
				         width: 160
					 }
				 }, {
					 layout : 'form',
					 items: {
						 xtype:"textfield",
					     fieldLabel:"数/重量",
					     name:"small.total",
					     allowBlank: false,
					     blankText : '数/重量不能为空！',
					     emptyText: '数/重量不能为空！',
				         width: 160
					 }
				 }]
			 }, {
				 layout : 'column',						
				 defaults : {
					 columnWidth : '.45'
				 },				
				 items : [{
					 layout : 'form',
					 items: {
						 xtype:"textarea",
					     fieldLabel:"具体检验检疫要求",
					     name:"small.requirement",
					     allowBlank: false,
				         width: 300
					 }
				 }, {
					 layout : 'form',
					 items: {
						 xtype:"textarea",
					     fieldLabel:"备注",
					     name:"small.memo",
					     allowBlank: false,
				         width: 300
					 }
				 }, {
					 layout : 'form',
					 items: {
						 xtype:"textfield",
					     fieldLabel:"基本信息id",
					     name:"small.basic_id",
					     hidden: true,
					     value: id,
				         width: 400
					 }
				 }, {
					 layout : 'form',
					 items: {
						 xtype:"textfield",
					     fieldLabel:"id",
					     name:"small.id",
					     hidden: true,
				         width: 400
					 }
				 }]
			 }]
		 }],
		 buttons : [{
			 text: button_text,
			 listeners:{
				 'click':function(){
					 if(win.form.getForm().isValid()){
						 win.form.getForm().doAction('submit',{
							 url: url,
							 method:'post',
							 waitMsg:'正在加载',
							 timeout : 2000,
							 success:function(response){
								 url = 'resource/sampleRegister/registDemo';
								 Ext.Msg.alert('提示窗口','添加或修改成功!');
								 storeconf.load({
								    params :{
								    	start:0,
								    	limit:10
								    }
								 });
							 },
							 failure:function(response,action){
								 url = 'resource/sampleRegister/registDemo';
								 Ext.Msg.alert('提示窗口','添加或修改失败!');
							 }
						 });
					 }else{
						 return;
					 }
				 }
			 }
		 },{
			 text:'取消',
			 handler:function(){
				 win.close();
			 }
		 },{
			 text:'删除',
			 listeners:{
				 'click':function(){
					 if(win.form.getForm().isValid()){
						 win.form.getForm().doAction('submit',{
							 url : 'resource/sampleRegister/deleteDemo',
							 method:'post',
							 waitMsg:'正在加载',
							 timeout : 2000,
							 success:function(response){
								 Ext.Msg.alert('提示窗口','删除成功!');
								 storeconf.load({
								    	params :{
								    		start:0,
								    		limit:10
								    	}
								 });
							 },
							 failure:function(response,action){
								 Ext.Msg.alert('提示窗口','删除错误!');
							 }
						 });
					 }else{
						 return;
					 }
				 }
			 }
		 },{
			 text:'重置',
			 handler:function(){
				 win.form.getForm().reset();
			 }
		 }]
	 });
	 dm.sampleRegister.addWin_small.superclass.constructor.call(this, {
		 	title:'',
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
Ext.extend(dm.sampleRegister.addWin_small,Ext.Window);