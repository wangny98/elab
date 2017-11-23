Ext.namespace('dm.org');

dm.org.main = function(){
	var main = this;
	main.left = new dm.org.treePanel();
	main.left.main = main;

	main.right = new Ext.Panel({
		layout : 'fit',
		id : 'dm_org_main',
		region : 'center',
		tbar : [{
			text : '新增单位',
			handler : function(){
				//console.info("node ； ",main.left.getSelectionModel().getSelectedNode());
				if(main.left.getSelectionModel().getSelectedNode()==null || main.left.getSelectionModel().getSelectedNode().attributes.type==2){
					Ext.Msg.alert('提示', '请选择上级单位!');
					return;
				}
				
				var parentId = main.left.getSelectionModel().getSelectedNode().attributes.id;
				//console.info("parentId : ",parentId);
				var form = new dm.org.unitFormPanel();
				var win = new Ext.Window({
					height:600,
					width:580,
					layout:'fit',
					items:[form],
					buttonAlign : 'center',
					buttons : [{
						text : '确定',
						listeners : {
							'click' : function() {
								console.info("form : ",form);
								//console.info("valid : ",form.getForm().isValid());
								//if (form.getForm().isValid()) {
									form.getForm().doAction('submit', {
										url : "resource/OrgService/addUnit",
										method : 'post',
										waitMsg : '正在加载',
										params : {
											parentId:parentId
										},
										timeout : 2000,
										success : function(response) {
											dm.comm.comm_alert({
												msg : '添加单位成功'
											});
											win.close();
											main.left.getRootNode().reload();
										},
										failure : function(response, action) {

										}
									});
								/*} else {
									return;
								}*/
							}
						}
					},{
						text : '关闭',
						listeners : {
							'click' : function() {
								win.close();
							}
						}
						
					}]
				});
				win.show();
			}
		},{
			text : '新增部门',
			handler : function(){
				if(main.left.getSelectionModel().getSelectedNode()==null){
					Ext.Msg.alert('提示', '请选择上级单位!');
					return;
				}
				
				var parentId = main.left.getSelectionModel().getSelectedNode().attributes.id;
				var form = new dm.org.departFormPanel();
				var win = new Ext.Window({
					height:500,
					width:400,
					layout:'fit',
					items:[form],
					buttonAlign : 'center',
					buttons : [{
						text : '确定',
						listeners : {
							'click' : function() {
								console.info("form : ",form);
								//console.info("valid : ",form.getForm().isValid());
								//if (form.getForm().isValid()) {
									form.getForm().doAction('submit', {
										url : "resource/OrgService/addDepart",
										method : 'post',
										waitMsg : '正在加载',
										params : {
											parentId:parentId
										},
										timeout : 2000,
										success : function(response) {
											dm.comm.comm_alert({
												msg : '新增部门成功'
											});
											win.close();
											main.left.getRootNode().reload();
										},
										failure : function(response, action) {

										}
									});
								/*} else {
									return;
								}*/
							}
						}
					},{
						text : '关闭',
						listeners : {
							'click' : function() {
								win.close();
							}
						}
						
					}]
				});
				win.show();
			}
		},{
			text : '删除',
			handler : function(){
				var node = main.left.getSelectionModel().getSelectedNode();
				if(node==null){
					Ext.Msg.alert('提示', '请选择单条记录进行删除!');
					return;
				}
				if(!node.attributes.leaf){
					Ext.Msg.alert('提示', '请先删除该条记录下的子记录!');
					return;
				}
				
				Ext.Ajax.request({
					url : 'resource/OrgService/deleteOrg',
					method : 'POST',
					params : {
						'orgId':node.id,
						'orgType':node.attributes.type
					},
					success : function(resp) {
						var json = Ext.util.JSON.decode(resp.responseText);
						var flag = json.success;
						if (flag) {
							Ext.Msg.alert('提示', '删除成功');
							main.left.getRootNode().reload();
							main.right.removeAll();
							main.right.doLayout();
						}else{
							Ext.Msg.alert('提示', '删除失败');
						}
					}
				});
			}
		}]
	});
	main.right.main = main;
	dm.org.main.superclass.constructor.call(this, {
		closable:true,
		title:'组织机构管理',
		layout:'border',
		items:[main.left,main.right]
	});
};
Ext.extend(dm.org.main,Ext.Panel);

dm.org.treePanel = function() {
	var orgTree = this;
	dm.org.treePanel.superclass.constructor.call(this, {
		region:'west',
		root : new Ext.tree.AsyncTreeNode({
			id : '0',
			text : '单位',
			expanded : true,
			iconCls : 'icon-systemSet-enterprise',
			type : 1
		}),
		loader : new Ext.tree.TreeLoader({
			url : 'resource/OrgService/getTree?id=0',
			method : 'post'
		}),
		useArrows : false,
		width : 250,
		rootVisible : true,
		border : true,
		animate : true,
		autoScroll : true,
		enableDD : false,
		listeners:{
			click:function(node){
				var form = null;
				if(node.id=='0'){
					return;
				}
				if(node.attributes.type==1){
					form = new dm.org.unitFormPanel(false);
				}else{
					form = new dm.org.departFormPanel(false);
				}
				form.getForm().load({
					url : 'resource/OrgService/getOrg',
					params : {
						orgId : node.id,
						orgType : node.attributes.type
					}
				});
				form.orgId = node.id;
				orgTree.main.right.removeAll();
				orgTree.main.right.add(form);
				orgTree.main.right.doLayout();
			}
		}
		
	});
};

Ext.extend(dm.org.treePanel,Ext.tree.TreePanel,{});

dm.org.unitFormPanel = function(hiddenSave){
	var unit = this;
	if(hiddenSave){
		
	}else{
		hiddenSave = false;
	}
	dm.org.unitFormPanel.superclass.constructor.call(this, {
		buttonAlign : 'center',
		baseCls : "x-plain",
		bodyStyle : 'padding:15px;',
		labelAlign : 'right',
		reader : new Ext.data.JsonReader({
			root : 'org'
		}, [ {
			name : 'id'
		},{
			name : 'unit_name'
		}, {
			name : 'unit_charge_name'
		},{
			name : 'property_charge_name'
		}, {
			name : 'write_name'
		},{
			name : 'telephone'
		},{
			name : 'unit_addr'
		}, {
			name : 'unit_code'
		}, {
			name : 'finance_code'
		},{
			name : 'post_code'
		},{
			name : 'unit_local'
		},{
			name : 'unit_character'
		},{
			name : 'unit_account'
		},{
			name : 'unit_type'
		},{
			name : 'last_year_code'
		},{
			name : 'depart_classic_type'
		},{
			name : 'report_reason'
		},{
			name : 'report_type'
		},{
			name : 'remark_code'
		},{
			name : 'plait_date',
			type : 'date',
			dateFormat : 'yyyy-MM-dd'/*,
			convert : function(v) {
				return dm.comm.comm_ConvertStringToDate(v);
			}*/
		}]),
		items:[{
	        	xtype:'textfield',
	        	width:400,//400
	        	fieldLabel:'单位名称',
	        	name : 'unit_name'
        	},{
	        	xtype:'textfield',
	        	width:200,
	        	fieldLabel:'单位负责人',
	        	name : 'unit_charge_name'
        	},{
	        	xtype:'textfield',
	        	width:200,
	        	fieldLabel:'资产管理负责人',
	        	name : 'property_charge_name'
        	},{
	        	xtype:'textfield',
	        	width:200,
	        	fieldLabel:'填表人',
	        	name : 'write_name'
        	},{
	        	xtype:'textfield',
	        	width:200,
	        	fieldLabel:'电话号码',
	        	name : 'telephone'
        	},{
	        	xtype:'textfield',
	        	width:400,//400
	        	fieldLabel:'单位地址',
	        	name : 'unit_addr'
        	},{
	        	xtype:'textfield',
	        	width:200,
	        	fieldLabel:'邮政编码',
	        	name : 'post_code'
        	},{
	        	xtype:'textfield',
	        	width:200,
	        	fieldLabel:'组织机构代码',
	        	name : 'unit_code'
        	},{
	        	xtype:'textfield',
	        	width:200,
	        	fieldLabel:'财政预算代码',
	        	name : 'finance_code'
        	},{
	        	xtype:'combo',
	        	width:400,//400
	        	fieldLabel:'单位所在地区',
	        	hiddenName : 'unit_local',
	        	name : 'unit_local',
	        	forceSelection	: true,
	        	editable : false,
				triggerAction : 'all',
				mode:'remote',
				store : dm.comm.getDictionaryStore('unit_local'),
				displayField:'attr_value',
				valueField:'attr_key',
        	},{
	        	xtype:'combo',
	        	width:400,//400
	        	fieldLabel:'单位基本性质',
	        	editable : false,
				triggerAction : 'all',
				mode:'remote',
				store : dm.comm.getDictionaryStore('unit_character'),
				displayField:'attr_value',
				valueField:'attr_key',
	        	hiddenName : 'unit_character'
        	},{
	        	xtype:'combo',
	        	width:400,//400
	        	fieldLabel:'单位执行会计制度',
	        	hiddenName : 'unit_account',
	        	editable : false,
				triggerAction : 'all',
				mode:'remote',
				store : dm.comm.getDictionaryStore('unit_account'),
				displayField:'attr_value',
				valueField:'attr_key',
        	},{
	        	xtype:'combo',
	        	width:400,//400
	        	fieldLabel:'行业类型',
	        	hiddenName : 'unit_type',
	        	editable : false,
				triggerAction : 'all',
				mode:'remote',
				store : dm.comm.getDictionaryStore('unit_type'),
				displayField:'attr_value',
				valueField:'attr_key',
        	
        	},{
	        	xtype:'combo',
	        	width:200,
	        	fieldLabel:'部门级次分类类型',
	        	hiddenName : 'depart_classic_type',
	        	editable : false,
				triggerAction : 'all',
				mode:'remote',
				store : dm.comm.getDictionaryStore('depart_classic_type'),
				displayField:'attr_value',
				valueField:'attr_key'
        	},{
	        	xtype:'textfield',
	        	width:200,
	        	fieldLabel:'上年代码',
	        	name:'last_year_code'
        	},{
	        	xtype:'combo',
	        	width:400,//400
	        	fieldLabel:'新报因素',
	        	hiddenName : 'report_reason',
	        	editable : false,
				triggerAction : 'all',
				mode:'remote',
				store : dm.comm.getDictionaryStore('report_reason'),
				displayField:'attr_value',
				valueField:'attr_key'
        	},{
	        	xtype:'combo',
	        	width:400,//400
	        	fieldLabel:'报表类型',
	        	hiddenName : 'report_type',
	        	editable : false,
				triggerAction : 'all',
				mode:'remote',
				store : dm.comm.getDictionaryStore('report_type'),
				displayField:'attr_value',
				valueField:'attr_key'
        	},{
	        	xtype:'textfield',
	        	width:200,
	        	fieldLabel:'备用码',
	        	name : 'remark_code'
        	}/*,{
	        	xtype:'datefield',
	        	width:200,
	        	fieldLabel:'编制日期',
	        	name : 'plait_date',
	        	editable : false,
	        	format: 'Y-m-d H:m:s'
        	}*//*{
		    layout:"column",
		    baseCls : "x-plain",
		    items:[{
		    	layout : 'form',
				baseCls : "x-plain",
				labelAlign:'right',
				bodyStyle : 'padding:5px 0px 0px',
		        items:[]
		    },{
		    	layout : 'form',
				baseCls : "x-plain",
				labelAlign:'right',
				bodyStyle : 'padding:5px 0px 0px',
		        items:[{
		        	
		        }]
		    }]
		}*/],
		buttonAlign : 'center',
		buttons:[{
			text : '保存',
			hidden:hiddenSave,
			handler : function(){
				var node = unit.ownerCt.main.left.getSelectionModel().getSelectedNode();
				
				var nodeId = null;
				if(unit.SelectNodeId){
					nodeId = unit.SelectNodeId;
				}else{
					nodeId = node.id;
				}
				unit.getForm().doAction('submit', {
					url : "resource/OrgService/modifyUnit",
					method : 'post',
					waitMsg : '正在加载',
					params : {
						orgId:nodeId
					},
					timeout : 2000,
					success : function(response) {
						dm.comm.comm_alert({
							msg : '修改单位成功'
						});
						unit.ownerCt.main.left.getRootNode().reload({
							success : function(){
							}
						});
						
						if(unit.SelectNodeId){
							
						}else{
							unit.SelectNodeId = node.id;
						}
						/*unit.getForm().load({
							url : 'resource/OrgService/getOrg',
							params : {
								orgId : node.id,
								orgType : node.attributes.type
							}
						});*/
					},
					failure : function(response, action) {

					}
				});
			}
		}]
	});
	
};

Ext.extend(dm.org.unitFormPanel,dm.privateForm.FormPanel,{	  
});

dm.org.departFormPanel = function(hiddenSave){
	var depart = this;
	//console.info("sss : ",hiddenSave);
	if(hiddenSave){
		
	}else{
		hiddenSave = false;
	}
	dm.org.departFormPanel.superclass.constructor.call(this, {
		baseCls : "x-plain",
		bodyStyle : 'padding:15px;',
		labelAlign : 'right',
		reader : new Ext.data.JsonReader({
			root : 'org'
		}, [ {
			name : 'id'
		},{
			name : 'depart_code'
		}, {
			name : 'depart_name'
		}]),
		items:[{
			xtype:'textfield',
			fieldLabel : '部门代码',
        	name : 'depart_code'
		},{
			xtype:'textfield',
			fieldLabel : '部门名称',
        	name : 'depart_name'
		}],
		buttonAlign : 'center',
		buttons:[{
			text : '保存',
			//hidden:hiddenSave,
			handler : function(){
				
				var node= depart.ownerCt.main.left.getSelectionModel().getSelectedNode();
				
				var nodeId = null;
				if(depart.SelectNodeId){
					nodeId = depart.SelectNodeId;
				}else{
					nodeId = node.id;
				}
				depart.getForm().doAction('submit', {
					url : "resource/OrgService/modifyDepart",
					method : 'post',
					waitMsg : '正在加载',
					params : {
						orgId:nodeId
					},
					timeout : 2000,
					success : function(response) {
						dm.comm.comm_alert({
							msg : '修改部门成功'
						});
						depart.ownerCt.main.left.getRootNode().reload();
						if(depart.SelectNodeId){
							
						}else{
							depart.SelectNodeId = node.id;
						}
					},
					failure : function(response, action) {

					}
				});
			}
		}]
	});
};

Ext.extend(dm.org.departFormPanel,Ext.form.FormPanel);