Ext.namespace('dm.report');
dm.report.propertyChangeInfo = function(){
	var grid = this;
	grid.cmArray = [ {
		header : '项目',
		dataIndex : 'key',
		width:300
	},{
		header : '合计',
		dataIndex : 'c1'
	},{
		header : '土地面积',
		dataIndex : 'c2'
	},{
		header : '土地原值',
		dataIndex : 'c3'
	},{
		header : '房屋面积',
		dataIndex : 'c4'
	},{
		header : '房屋原值',
		dataIndex : 'c5'
	} ,{
		header : '交通数量',
		dataIndex : 'c6'
	} ,{
		header : '交通原值',
		dataIndex : 'c7'
	} ,{
		header : '通用办公设备数量',
		dataIndex : 'c8'
	} ,{
		header : '通用办公设备原值',
		dataIndex : 'c9'
	} ,{
		header : '办公家具数量',
		dataIndex : 'c10'
	} ,{
		header : '办公家具原值',
		dataIndex : 'c11'
	} ,{
		header : '其他数量',
		dataIndex : 'c12'
	} ,{
		header : '其他原值',
		dataIndex : 'c13'
	}  ];
	this.colModel=new Ext.grid.ColumnModel(grid.cmArray);
	this.store = new Ext.data.Store({
		proxy:new Ext.data.HttpProxy({
					url:'resource/propertyReport/changeInfo',
					method:'POST'
			}),
		reader:new Ext.data.JsonReader(),
		baseParams : {
						entityName : this.entityName,
						entityId : this.entityId
					}
	});
	dm.report.propertyChangeInfo.superclass.constructor.call(this,{
		title:'固定资产变更情况表',
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

Ext.extend(dm.report.propertyChangeInfo,Ext.grid.GridPanel,{
	exportExl:function(){
		var grid = this;
		var src = 'resource/propertyReport/exportExl2';
		
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
				"/resource/propertyReport/pringChangeInfo");
		LODOP.SET_PRINT_STYLEA(0, "HOrient", 3);
		LODOP.SET_PRINT_STYLEA(0, "VOrient", 3);
		LODOP.SET_SHOW_MODE("MESSAGE_GETING_URL", ""); // 该语句隐藏进度条或修改提示信息
		LODOP.SET_SHOW_MODE("MESSAGE_PARSING_URL", "");// 该语句隐藏进度条或修改提示信息
		LODOP.PREVIEW();
	}
});