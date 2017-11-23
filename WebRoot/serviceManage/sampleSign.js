Ext.namespace('dm.sampleSign');

dm.sampleSign.grid = function() {
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
	}),  {
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
	dm.sampleSign.grid.superclass.constructor.call(this, {
		title : '实验室签发',
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
			text : '签发', 
			iconCls : 'icon-info',
			funcCode : '',
			handler : function() {
				grid.signWin();
			}
		}, {
			xtype : "tbseparator"
		}, {
			text : '打印送样单',
			iconCls : 'icon-search',
			funcCode : '',
			handler : function() {
				grid.printSampleWin();
			}
		}, {
			xtype : "tbseparator"
		}, {
			text : '打印鉴定结果',
			iconCls : 'icon-search',
			funcCode : '',
			handler : function() {
				grid.printAuthWin();
			}
		}, {
			xtype : "tbseparator"
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
		//	status: 1
		};
	});
	grid.loadData();
};

Ext.extend(dm.sampleSign.grid, Ext.grid.GridPanel, {
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
	signWin : function() {
		var sampleGrid = this;
		
		var record = sampleGrid.getSelectionModel().getSelections();
		if (record.length == 0) {
			Ext.Msg.alert('提示窗口', '请先选择您要操作的行！');
			return;
		} else {
			var status = record[0].get("sample.status");
			if(status == 6 || status == 7){
				var ids = record[0].get("sample.id");
				var win = new dm.sampleSign.signWin(ids, status, sampleGrid);
				win.show();
			}else{
				Ext.Msg.alert('提示窗口', '该小样所处状态不能签发！');
			}
		}
	},
	searchWin : function() {
		var grid = this;

		var win = new dm.sampleSign.searchWin(this);
		win.show();
	},
	printSampleWin: function(){
		var sampleGrid = this;
		var record = sampleGrid.getSelectionModel().getSelections();
		if (record.length == 0) {
			Ext.Msg.alert('提示窗口', '请先选择您要操作的行！');
			return;
		} else {
			printSample(record[0]);
		}
	},
	printAuthWin: function(){
		var sampleGrid = this;
		var record = sampleGrid.getSelectionModel().getSelections();
		if (record.length == 0) {
			Ext.Msg.alert('提示窗口', '请先选择您要操作的行！');
			return;
		} else {
			var status = record[0].get('sample.status');
			if(status == 7){
				printAuth(record[0]);
			}else{
				Ext.Msg.alert('提示窗口', '该送样单还未完成鉴定流程不能打印鉴定结果报告！');
			}
		}
	}
});

function printAuth(record){
	var demo = [];
	var demo_tmp = '';
	var count = 0;
	var pagesize = 10;
	$.ajax({
		async : false,
		type : 'post',
		data : {
			id : record.get('sample.id')
		},
		url : 'resource/sampleRegister/getDemoForAuthResult',
		success : function(returnData, status) {
			if (returnData.success == true) {
				$.each(returnData.list,function(idx, item){
					demo_tmp =demo_tmp + '<tr><td class="style7">&nbsp;'+item.name
					+'</td><td class="style8">&nbsp;'+item.authresult
					+'</td><td class="style9">&nbsp;'+item.judge
					+'</td><td class="style-border">&nbsp;'+item.authdate
					+'</td><td class="style10">&nbsp;'+item.verifyer
					+'</td><td class="style11">&nbsp;'+item.verifydate
					+'</td><td class="style-border">&nbsp;'+item.memo
					+'</td></tr>';
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
		demo_tmp += '<tr><td class="style7">&nbsp;</td><td class="style8">&nbsp;</td><td class="style9">&nbsp;</td>'+
		'<td class="style-border">&nbsp;</td><td class="style10">&nbsp;</td><td class="style11">&nbsp;</td><td class="style-border">&nbsp;</td></tr>';
	}
	demo.push(demo_tmp);
	var page = demo.length;
	
	var html1 = '<style type="text/css">'+
	'.maindiv{width: 650px;text-align: center;border-color: Black;}'+
	'.table{width: 500px;height: 100px;border-color: Black;font-size: 10px;text-align:left;}'+
	'.style2{width: 101px;border-color: Black;}'+
	'.style3{border-color: Black;width: 202px;}'+
	'.style4{width: 93px;border-color: Black;}'+
	'.table1{width: 640px;font-size: 12px; border-color: Black;}'+
	'.style5{border-color: Black;width: 206px; height: 25px;}'+
	'.style6{border-color: Black;width: 406px;}'+
	'.style7{width: 81px; height: 25px;border-color: Black;}'+
	'.style8{width: 163px;border-color: Black;}'+
	'.style9{width: 55px;border-color: Black;}'+
	'.style10{width: 57px;border-color: Black;}'+
	'.style-border{border-color: Black;}'+
	'.style11{width: 103px;border-color: Black;}</style>'+
	'<div class=maindiv>'+
	'<table cellpadding=0 cellspacing=0 border=1 class=table>'+
	'<tr><td class="style4" rowspan="3">&nbsp;</td><td class="style2">实验室名称：<br />Name Of Lab</td>'+
	'<td class="style3">&nbsp;南通检验检疫局实验室</td></tr>'+
	'<tr><td class="style2">电话/传真：<br />Tel/Fax</td><td class="style3">&nbsp;</td></tr>'+
	'<tr><td class="style2">地址：<br />Address</td><td class="style3">&nbsp;</td></tr></table>'+
	'<p style="text-align:center;line-height:20.0pt;">'+
	'<b ><span style="font-size:18.0pt;font-family:宋体;letter-spacing:2.1pt">'+
	'鉴定结果报告</span></b></p>'+
	'<table cellpadding=0 cellspacing=0 border=1 class=table1>'+
	'<tr><td class="style5">报告编号：</td><td class="style6">&nbsp;'+record.get('sample.submit_no')+'</td></tr>'+
	'<tr><td class="style5">送样部门/送样人/联系电话：</td><td class="style6">&nbsp;'+record.get('sample.submit_phone')+'</td></tr>'+
	'<tr><td class="style5">送样日期：</td><td class="style6">&nbsp;'+record.get('sample.submit_date')+'</td></tr>'+
	'<tr><td class="style5">送样标识：</td><td class="style6">&nbsp;'+record.get('sample.verify_identify')+'</td></tr>'+
	'<tr><td class="style5">样品名称/数目/重量：</td><td class="style6">&nbsp;'+record.get('sample.sample_name')+
	'/'+record.get('sample.sample_num')+'</td></tr>'+
	'<tr><td class="style5">样品来源/输入国家地区：</td><td class="style6">&nbsp;'+record.get('sample.sample_region')+
	'/'+record.get('sample.export_import')+'</td></tr>'+
	'<tr><td class="style5">鉴定物：</td><td class="style6">&nbsp;'+record.get('sample.verify_type')+'</td></tr>'+
	'<tr><td class="style5">鉴定项目：</td><td class="style6">&nbsp;'+record.get('sample.verify_projects')+'</td></tr>'+
	'<tr><td class="style5">特殊要求(备注)：</td><td class="style6">&nbsp;'+record.get('sample.memo')+'</td></tr></table>'+
	'<p style="text-align:left;line-height:20.0pt;">'+
	'<b ><span style="font-size:14.0pt;font-family:宋体;letter-spacing:2.1pt">'+
	'鉴定结果RESULTS</span></b></p>'+
	'<table cellpadding=0 cellspacing=0 border=1 class=table1>'+
	'<tr><td class="style7"><b>小样名称</b></td><td class="style8"><b>鉴定结果</b></td>'+
	'<td class="style9"><b>鉴定人</b></td><td class="style-border"><b>鉴定日期</b></td><td class="style10"><b>复核人</b></td>'+
	'<td class="style11"><b>复核日期</b></td><td class="style-border"><b>备注</b></td></tr>';
	var html2 = '</table>';
	var html3 = '</div>';
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

dm.sampleSign.searchWin = function(sampleGrid) {
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
	dm.sampleSign.searchWin.superclass.constructor.call(this, {
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
Ext.extend(dm.sampleSign.searchWin, Ext.Window);

dm.sampleSign.signWin = function(id, status, sampleGrid) {
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
		reader : new Ext.data.JsonReader({
			successProperty : 'success',
			root : 'sample'
		}, [{
			name : 'sample.signer', mapping:'signer'
		}, {
			name : 'sample.signtitle', mapping:'signtitle'
		}, {
			name : 'sample.signmemo', mapping:'signmemo'
		}, {
			name : 'sample.id', mapping:'id'
		}]),
		items : [{
			xtype : 'fieldset',
			title : '',
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
							fieldLabel : "签发人员",
							name : "sample.signer",
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
							fieldLabel : "签发人员职称",
							name : "sample.signtitle",
							allowBlank : false,
							blankText : '不能为空！',
							emptyText : '不能为空！',
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
						columnWidth : '.91'
					},
					items : [ {
						layout : 'form',
						items : {
							xtype : "textarea",
							fieldLabel : "签发备注",
							name : "sample.signmemo",
							value: '无',
							width : 603
						}
					}, {
						layout : 'form',
						labelWidth : 150,
						items : {
							xtype : "textfield",
							fieldLabel : "小样id",
							hidden: true,
							name : "sample.id",
							value: id,
							width : 200
						}
					}]
				} ]
			} ]
		}],
		buttons : [ {
			text : '签发',
			listeners : {
				'click' : function() {
					if (win.form.getForm().isValid()) {
						win.form.getForm().doAction('submit', {
							url : 'resource/sampleRegister/registerSign',
							method : 'post',
							waitMsg : '正在加载',
							timeout : 2000,
							success : function(response) {
								Ext.Msg.alert('提示窗口', '签发成功！');
								sampleGrid.loadData();
								win.close();
							},
							failure : function(response, action) {
								Ext.Msg.alert('提示窗口', '签发失败！');
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
	
	if(status == 7){
		win.form.getForm().load({
			params : {
				id : id
			},
			url : "resource/sampleRegister/loadSign"
		});
	}	
	
	dm.sampleSign.signWin.superclass.constructor.call(this, {
		title : '签发窗口',
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
Ext.extend(dm.sampleSign.signWin, Ext.Window);