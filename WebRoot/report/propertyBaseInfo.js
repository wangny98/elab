Ext.namespace('dm.report');
dm.report.propertyBaseInfo = function(){
	var grid = this;
	grid.cmArray = [ {
		header : '项目',
		dataIndex : 'key',
		width:300
	},{
		header : '年初数',
		dataIndex : 'start',
		width:300
	},{
		header : '本年增加数',
		dataIndex : 'add',
		width:300
	},{
		header : '本年减少数',
		dataIndex : 'del',
		width:300
	},{
		header : '年末数',
		dataIndex : 'end',
		width:300
	} ];
	this.colModel=new Ext.grid.ColumnModel(grid.cmArray);
	this.store = new Ext.data.Store({
		proxy:new Ext.data.HttpProxy({
					url:'resource/propertyReport/baseInfo',
					method:'POST'
			}),
		reader:new Ext.data.JsonReader(),
		baseParams : {
						entityName : this.entityName,
						entityId : this.entityId
					}
	});
	dm.report.propertyBaseInfo.superclass.constructor.call(this,{
		title:'固定资产基础情况表',
		closable:true,
		loadMask : {
				msg : '请稍等'
		},
		tbar : [  {
			text : '打印', // 审批
			iconCls : 'icon-printer',
			funcCode : '',
			handler : function() {
				grid.printReport();
			}

		},{xtype:"tbseparator"},{
			text : '导出Excel', // 审批
			iconCls : 'icon-export_excel',
			funcCode : '',
			handler : function() {
				grid.exportExl();
			}

		}]
	});
	grid.getStore().load();
};

Ext.extend(dm.report.propertyBaseInfo,Ext.grid.GridPanel,{
	exportExl:function(){
		var grid = this;
		var src = 'resource/propertyReport/exportExl1';
		
		var exportEXLIframe = document.createElement('iframe');
		exportEXLIframe.src = src;
		exportEXLIframe.style.display = "none";
		document.body.appendChild(exportEXLIframe);
		
	},
	printReport : function(){
		
		var grid = this;

		LODOP = getLodop(document.getElementById('LODOP_OB'), document
				.getElementById('LODOP_EM'));
		LODOP.PRINT_INIT("打印控件功能演示_Lodop功能_按网址打印");
		LODOP.ADD_PRINT_URL(30, 20, 746, "100%",
				"/resource/propertyReport/reportBaseReport");
		LODOP.SET_PRINT_STYLEA(0, "HOrient", 3);
		LODOP.SET_PRINT_STYLEA(0, "VOrient", 3);
		LODOP.SET_SHOW_MODE("MESSAGE_GETING_URL", ""); // 该语句隐藏进度条或修改提示信息
		LODOP.SET_SHOW_MODE("MESSAGE_PARSING_URL", "");// 该语句隐藏进度条或修改提示信息
		LODOP.PREVIEW();
	}
});