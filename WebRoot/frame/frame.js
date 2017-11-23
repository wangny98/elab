 Ext.namespace('dm.frame');
 
/*dm.frame.north=function(){
	dm.frame.north.superclass.constructor.call(this, {});
};
Ext.extend(dm.frame.north,Ext.Panel);*/
Ext.Ajax.on('requestexception', function(conn, response, options) {
		if (response.status == "999") {
			Ext.Msg.alert('提示', '会话超时，请重新登录!', function() {
					window.location.href="login.html";
				});
		}
});
 var trees=[];
 var GLOBAL_FUNCCODE=[];
 GLOBAL_uid=null;
 Ext.Ajax.request({
		url : 'resource/functionManage/infoFuncTree',
		async : false,
		method : 'POST',
		success : function(resp, options) {
			var respText = Ext.util.JSON.decode(resp.responseText);
			if(respText.success){
				trees = respText.module;
				GLOBAL_FUNCCODE = respText.func;
				GLOBAL_uid=respText.uId;
			}
		}
	});

dm.frame.left=function(trees){
	var leftPanel = this;
 	dm.frame.left.superclass.constructor.call(this, {
 		region : 'west',
 		border : false,
 		collapsible : true,
 		split : true,
 		width : 175,
 		layout : 'accordion',
 		layoutConfig : {
			animate : true
		},
		bbar:[{
			text:'注销',
			iconCls : 'icon_logout',
			handler:function(){
				Ext.Msg.confirm('系统提示', '您是否确认注销？', function(btn) {
					if (btn == 'yes') {
						Ext.Ajax.request({
									url : 'resource/security/logout',
									success : function(response) {
										var responseArray = Ext.util.JSON
												.decode(response.responseText);
										if (responseArray.success == true) {
											window.location.href=window.location.href.replace("index","login");
											//location.reload(true);
										}
									},
									failure : function(response) {
										Ext.Msg.alert('失败',
												response.responseText);
									}
								});
					}
				}, this);
			}
		}]
 	});
 	leftPanel.initTree(trees);
 	
};
 Ext.extend(dm.frame.left,Ext.Panel,{
 		initTree:function(trees){
 			var leftPanel = this;
 			for(var i=0;i<trees.length;i++){
		 		var treePanel = new Ext.tree.TreePanel({
		 			id : trees[i].root.id,
		 			border : trees[i].root.border,
		 			rootVisible : trees[i].root.rootVisible,
		 			root : new Ext.tree.AsyncTreeNode({
		 				text : trees[i].root.title,
		 				iconCls:trees[i].root.iconCls,
		 				rootVisible : false,
						expanded : true,
						children : trees[i].root.children
		 			})
		 		});
		 		
		 		treePanel.addEvents({'nodeClick':true});
				leftPanel.add({
					iconCls : trees[i].root.iconCls,
					title : trees[i].root.title,
					autoScroll : true,
					items : treePanel
				});
				//leftPanel.addEvents({'nodeClick':true});
				
			}
 			if(trees.length!=0){
				leftPanel.initTreeEvent();
			}
 		},
 		initTreeEvent : function(){
 			var leftPanel = this;
 			for(var i = 0;i<this.items.length;i++){
 				for (var i = 0; i < this.items.length; i++) {
 					var p = this.items.itemAt(i);
					if (p)
						var t = p.items.itemAt(0);
					if (t)
						t.on({
							'click' : function(node, event) {
								if (node) {
									event.stopEvent();
									leftPanel.fireEvent('nodeClick',node.attributes);
								}
							}
						});
 				}
 			}
 		}
 });
 
var left = new dm.frame.left(trees);

dm.frame.main=function(){
	var main = this;
	dm.frame.main.superclass.constructor.call(this, {
						region : 'center',
						enableTabScroll : true,
						autoDestroy : true,
						autoScroll : true,
						activeTab : 0,
						plugins : new Ext.ux.TabCloseMenu(),
						items:[]
	});
	
};
Ext.extend(dm.frame.main,Ext.TabPanel,{

	findPanel : function(id) {
		var ret = Ext.getCmp(id);// this.cache[i]
		return ret;
	},

	loadTab : function(node) {
		this.setActiveTab(node);

	}
});
var mainTab = new dm.frame.main();
mainTab.on('afterrender', function() {
	require(["business/project/projectService.js"], function() {
		var cmp = eval("new dm.project.GridPanel()").setTitle("选择项目");
		cmp.id = "ext-iframe-"+28;
		cmp.iconCls = "icon-module_28";
		if (!cmp) {
			Ext.Msg.alert("失败", "页面加载失败");
			return;
		}
		mainTab.add(cmp);
		mainTab.loadTab(cmp);
	})
});

left.on('nodeClick', function(nodeAttr) {
					var size = mainTab.items.getCount();
					var n = mainTab.getComponent("ext-iframe-"+nodeAttr.idfex);
					if (!n) {
						var module = LTMS.GLOBAL.MODULE;
						var cmp;
						for (var i = 0; i < module.length; i++) {
							if (module[i].id == nodeAttr.idfex) {

								var mm = module[i];
								require(mm.jspath, function() {
											console.log(nodeAttr.idfex);
											cmp = eval("new " + mm.module + "()").setTitle(mm.name);
											cmp.id = "ext-iframe-"+nodeAttr.idfex;
											cmp.iconCls = nodeAttr.iconCls;
											if (!cmp) {
												Ext.Msg.alert("失败", "页面加载失败");
												return;
											}
											mainTab.add(cmp);
											mainTab.loadTab(cmp);
											cmp.frameParent = mainTab;
										});
								break;
							}else{
								if(i == module.length-1){
									
									var panel404 = new Ext.Panel({
											//id:"ext-iframe-"+"ext-iframe-"
											title:nodeAttr.text,
											layout : 'fit',
											closable:true,              
									        html: '<iframe src="404.html" width="100%" height="100%"></iframe>'  
									});

									mainTab.add(panel404);
									mainTab.loadTab(panel404);
									/*Ext.Msg.show({
										title:'提示', 
										msg:'功能正在建设中。。。',
										icon:"icon_building"
									});*/
								}
							}
						}
					} else {
						mainTab.loadTab(n);
					}
});



dm.frame.center=function(){
	dm.frame.center.superclass.constructor.call(this, {
			region : 'center',
			layout:'border',
			items : [left,mainTab]
	});
};

Ext.extend(dm.frame.center,Ext.Panel);

/*dm.frame.south=function(){
	dm.frame.south.superclass.constructor.call(this, {});
};
Ext.extend(dm.frame.south,Ext.Panel);*/

var centerPanel = new dm.frame.center();
 Ext.onReady(function() {
 	Ext.form.Field.prototype.msgTarget = 'qtip';
 	
 	/** 屏蔽backspace键 
	Ext.EventManager.on(Ext.isIE ? document : window, 'keydown',
			function(e, i) {
				var t = e.target.tagName;
				if (e.getKey() == e.BACKSPACE && t !== "INPUT"
						&& t !== "TEXTAREA") {
					e.stopEvent();
				} else if (e.getKey() == e.BACKSPACE
						&& (i.disabled || i.readOnly)) {
					e.stopEvent();
				}
			});*/
			
	dm.frame.viewPort = function(){
		dm.frame.viewPort.superclass.constructor.call(this, {
				layout : 'border',
				items : [{
							collapsible : false,
							border : false,
							region : 'north',// 指定子面板所在区域为north
							height : 69   , //高度原来是90，暂时替换为100
							html: '<div style="background: url(style/images/logo_bk.jpg) repeat-x;">'+
							'<div style="display: inline-block;"><img src="style/images/logo1.jpg"></div>'+
							'<div style="display: inline-block;vertical-align: top;"><div style="font-size: 38px;margin-top: 8px;color:#ffffff;">路鼎水泥土搅拌桩监控分析平台</div></div>'
						},centerPanel,{
							collapsible : false,
							region : 'south',// 指定子面板所在区域为north
							border : false,
							height : 0    //高度原来是90，暂时替换为100
						}]
		});
	};
	Ext.extend(dm.frame.viewPort, Ext.Viewport, {});
	
	new dm.frame.viewPort();
	
 });
 
