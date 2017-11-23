Ext.namespace('dm.document');

Ext.namespace("dm.document");

dm.document.panel = function(){
	var panel = this;
	panel.tree = new dm.document.tree();
	panel.grid = new dm.document.grid({tree:panel.tree});
	panel.tree.grid = panel.grid;
	
	panel.tree.region = 'west';
	panel.grid.region = "center";
	
	dm.document.panel.superclass.constructor.call(this, {
		title:'文档管理',
		layout:'border',
		closable:true,
		items:[panel.tree,panel.grid]
	});
};

Ext.extend(dm.document.panel,Ext.Panel,{
});

dm.document.grid = function(config){
	var grid = this;
	Ext.apply(this,config);
	grid.store = new Ext.data.Store({
			proxy : new Ext.data.HttpProxy({
							url : 'resource/fileManager/search',
							method : 'POST'
						}),
			reader : new Ext.data.JsonReader({
							totalProperty : 'total',
							root : 'fileList'
						}, [{
									name : 'id'
								}, {
									name : 'type'
								}, {
									name : 'name'
								},{
									name : 'creatorId'
								},{
									name : 'creatorName'
								},{
									name : 'creatorTime'/*,
									dateFormat : 'yyyy-MM-dd',
									convert : function(v) {
										return dm.comm.comm_ConvertStringToDate(v);
									}*/
								}])
		});
	grid.cm = new Ext.grid.ColumnModel([new Ext.grid.RowNumberer({
				renderer : function(value, metadata, record, rowIndex) {
					return grid.store.lastOptions.params.start + 1
							+ rowIndex;
				}
			}),{
				header : '论文名称',
				dataIndex:'name',
				renderer : function(value) {
					return dm.comm.comm_getTip(value);

				}
			},{
				header : '创建人',
				dataIndex:'creatorName',
				renderer : function(value) {
					return dm.comm.comm_getTip(value);

				}
			}, {
				header : '创建时间',
				dataIndex : 'creatorTime',
				sortable : true,
				renderer : function(value) {
					return dm.comm.comm_ConvertStringToDate(value);

				}
			}]);		

	grid.pagesize_combo = new dm.object.pagesize_combo();
	grid.pageSize = parseInt(grid.pagesize_combo.getValue());
	grid.ProgressBar=new dm.object.ProgressBarPager();
	
	grid.mybbar = new dm.object.PagingToolbar(grid.pageSize,
			grid.ProgressBar, grid.store, grid.pagesize_combo);
	grid.pagesize_combo.on('select',function(combo){
				grid.mybbar.pageSize = parseInt(combo
						.getValue());

				grid.store.load({
							params : {
								start : 0,
								limit : grid.mybbar.pageSize
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
			
	dm.document.grid.superclass.constructor.call(this, {
			store : grid.store,
			columns : grid.cm,
			viewConfig : {
					forceFit : true
			},
			loadMask : {
				msg : '正在加载...'
			},
			listeners:{
				'rowclick':function(grid, rowIndex, columnIndex, e){
					var id=grid.getSelectionModel().getSelected().data.id;
					
				}
			},
			// 分页
		    bbar : grid.mybbar,
			tbar:[{
				text:'添加文档',
				handler:function(){
					grid.importWin();
				}
			},{xtype:"tbseparator"},{
				text:'下载',
				handler:function(){
					var id=grid.getSelectionModel().getSelected().data.id;
					if(id){
						grid.exportFile(id);
					}else{
						Ext.Msg.alert('提示', '请选择单个文档进行重命名');
					}
				}
			},{xtype:"tbseparator"},{
				text:'预览', // 删除
				iconCls : 'icon-remove',
				handler : function() {
					grid.preView();
				}
			},{xtype:"tbseparator"},{
				text : '删除',
				handler : function() {
					grid.deleteDoc();
				}
			}]
	});
	
	grid.store.on('beforeload',function(){
		/*if(grid.tree&&grid.tree.getSelectionModel().getSelectedNode()!=null){
			grid.treeId=grid.tree.getSelectionModel().getSelectedNode().attributes.id;
		}*/
		grid.getStore().baseParams.nodeId = grid.treeId;
		grid.getStore().baseParams.types="2,3";
	});
	grid.getStore().load({
							params : {
								start : 0,
								limit : grid.mybbar.pageSize
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
};

Ext.extend(dm.document.grid,Ext.grid.GridPanel,{
	preView:function(){
		var grid = this;
		var length = grid.getSelectionModel().getSelections().length;
		if (length != 1) {
			Ext.Msg.alert("提示", "请选择一条记录");
			return;
		}
		var name = grid.getSelectionModel().getSelected().data.name;
		var dx = name.substring(name.lastIndexOf(".")+1);
		if(dx && dx!=''){
			dx = dx.toLowerCase();
			if(dx != 'doc' && dx != 'docx' && dx != 'ppt'
               && dx != 'pptx' && dx != 'xls' && dx != 'xlsx'
               && dx != 'txt'){
               		Ext.Msg.alert("提示", "预览只支持Office文档!");
               		return;
               }
		}
		var obj = {
			id:grid.getSelectionModel().getSelected().data.id,
			name:grid.getSelectionModel().getSelected().data.name
		};
		var previewWin = new DM.doc.flexPreview.previewWindow("id", "name",
					obj, grid);
		return;
	},
	deleteDoc:function(){
		var grid = this;
		var length = grid.getSelectionModel().getSelections().length;
		if (length != 1) {
			Ext.Msg.alert("提示", "请选择一条记录");
			return;
		}
		var id = grid.getSelectionModel().getSelected().data.id;
		Ext.Ajax.request({
			url : 'resource/fileManager/deleteDoc',
			method : 'POST',
			params : {
				'id':id
			},
			success : function(resp) {
				var json = Ext.util.JSON.decode(resp.responseText);
				var flag = json.success;
				if (flag) {
					Ext.Msg.alert('提示', '删除成功');
					grid.loadData();
				}else{
					Ext.Msg.alert('提示', '删除失败');
				}
			}
		});
	},
	loadData:function(){
		var grid = this;
		grid.getStore().load({
			params : {
				start : 0,
				limit : grid.mybbar.pageSize
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
	exportFile:function(id){
		var src = 'resource/fileManager/exportFile?fileId='+id;
		var exportEXLIframe = document.createElement('iframe');
		exportEXLIframe.src = src;
		exportEXLIframe.style.display = "none";
		document.body.appendChild(exportEXLIframe);
	},
	importWin:function(){
		var grid = this;
		
		if(grid.tree.getSelectionModel().getSelectedNode()==null){
			Ext.Msg.alert('提示', '请选择文件夹进行上传!');
			return;
		}
		var id=grid.tree.getSelectionModel().getSelectedNode().attributes.id;
		
		if(id.length==1){
			Ext.Msg.alert('提示', '根目录下无法上传文档!');
			return;
		}
		var submitEl=Ext.create({
								xtype : 'uploadPanel',
								title:'添加文档',
								border : false,
								fileSize : 1024 * 550,// 限制文件大小
								uploadUrl : 'resource/fileManager/uploadFiles',
								flashUrl : 'component/flashUpload/uploader/swfupload.swf',
								filePostName : 'file', // 后台接收参数
								fileTypes : '*.*',// 可上传文件类型
								postParams : {
									id:id,
									global_id:GLOBAL_uid
								}// 上传文件存放目录
							});
		submitEl.on('uploadcompleteEvent', function(store, fileName, index,
					filestatus) {
			//console.info("filestatus",filestatus);
			if(filestatus==-4){
				grid.getStore().load({
					params : {
						start : 0,
						limit : grid.mybbar.pageSize
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
		});
		
		var win = new Ext.Window({
							width : 670,
							title : '添加文档',
							height : 300,
							modal:true,
							layout : 'fit',
							items : [submitEl]
						});
		win.show();  
	}
});

dm.document.tree = function(){
	var tree = this;
	
	tree.treeLoad = new Ext.tree.TreeLoader({
		url : 'resource/contentManager/loadTree',
		method : 'POST'
	});
	
	tree.treeLoad.on("beforeload", function(treeLoader, node) {
        treeLoader.baseParams.typeIds = "2,3";
    }, this);

	
	tree.node = new Ext.tree.AsyncTreeNode({
		id:'0',
		text:'根节点',
		typeId:'0',
		expanded:true,
		rootVisible:false
	});
	
	dm.document.tree.superclass.constructor.call(this, {
				useArrows : false,
				autoScroll: true,
				width:200,
				root:tree.node,
				rootVisible:false,
				loader:tree.treeLoad,
				listeners:{
					'click':function(node,e){
						tree.grid.treeId = node.id;
						tree.grid.loadData();
					}
				},
				tbar:[{
					text : '新增', // 新增
					//iconCls : 'icon-add',
					handler : function() {
						tree.addWin();
					}
				},{
					text : '重命名', // 重命名
					//iconCls : 'icon-rename',
					handler : function() {
						tree.renameWin();
					}
				},{
					text : '删除', // 删除
					//iconCls : 'icon-remove',
					handler : function() {
						tree.delWin();
					}
				}]
	});
};
Ext.extend(dm.document.tree,Ext.tree.TreePanel,{
	typeId:"2,3",//1科研论文
	addWin:function(){
		var tree = this;
		var parentId;
		var typeId ;
		if(tree.getSelectionModel().getSelectedNode()==null){
			parentId=0;
		}else{
			parentId=tree.getSelectionModel().getSelectedNode().attributes.id;
			typeId = tree.getSelectionModel().getSelectedNode().attributes.typeId;
		}
		var form = new Ext.FormPanel({
							labelAlign : 'reight',
							baseCls : 'x-plain',
							bodyStyle : 'padding:5 0 0',
							labelAlign : "right",
							items : [{
								xtype:'textfield',
								fieldLabel:'名称',
								maxLength:20,
								maxText:'最大长度为20',
								allowBlank:false,
								width:200,
								name:'name'
							},{
								xtype:'textarea',
								fieldLabel:'备注',
								maxLength:200,
								maxText:'最大长度为200',
								width:200,
								name:'remark'
							}]
						});
		var win = new Ext.Window({
			title:'新增',
			height:200,
			width:400,
			items:[form],
			modal : true,
			buttonAlign : "center",
			buttons : [{
				text : '保存',
				iconCls : 'icon-save',
				handler:function(){
					if(!form.getForm().isValid()){
						return;
					}
					form.getForm().doAction('submit',{
						url:'resource/contentManager/addNode',
						method:'post',
						waitMsg:'正在保存...',
						params : {
									parentId : parentId,
									typeId : typeId
								 },
						timeout : 2000,
						success : function(resp){
							dm.comm.comm_alert({
									msg : '新增成功'
								});
							win.close();
							tree.getRootNode().reload();
						},
						failure:function(resp){
							
						}
					});
				}
			},{
				text:'关闭',
				iconCls : 'icon-shutdown',
				listeners : {
					'click' : function() {
						win.close();
					}
				}
			}]
		});
		win.show();
	},
	renameWin:function(){
		var tree = this;
		var id;
		if(tree.getSelectionModel().getSelectedNode()==null){
			Ext.Msg.alert('提示', '请选择单个文件夹进行重命名');
			return;
		}else{
			id=tree.getSelectionModel().getSelectedNode().attributes.id;
		}
		if(id.length==1){
			Ext.Msg.alert('提示', '根目录无法进行重命名!');
			return;
		}
		var form = new Ext.FormPanel({
							labelAlign : 'reight',
							baseCls : 'x-plain',
							bodyStyle : 'padding:5 0 0',
							labelAlign : "right",
							reader : new Ext.data.JsonReader({
								successProperty : 'success',
								root : 'bean'
							}, [{
										name : 'name'
									},{
										name : 'remark'
									}]),
							items : [{
								xtype:'textfield',
								fieldLabel:'名称',
								allowBlank:false,
								maxLength:20,
								maxText:'最大长度为20',
								width:200,
								name:'name'
							},{
								xtype:'textarea',
								fieldLabel:'备注',
								maxLength:200,
								maxText:'最大长度为200',
								width:200,
								name:'remark'
							}]
						});
		var win = new Ext.Window({
			title:'重命名',
			height:200,
			width:400,
			items:[form],
			modal : true,
			buttonAlign : "center",
			buttons : [{
				text : '保存',
				iconCls : 'icon-save',
				handler:function(){
					if(!form.getForm().isValid()){
						return;
					}
					form.getForm().doAction('submit',{
						url:'resource/contentManager/modifyNode',
						method:'post',
						waitMsg:'正在保存...',
						params : {
									id : id
								 },
						timeout : 2000,
						success : function(resp){
							dm.comm.comm_alert({
									msg : '重命名成功!'
								});
							win.close();
							tree.getRootNode().reload();
						},
						failure:function(resp){
							
						}
					});
				}
			},{
				text:'关闭',
				iconCls : 'icon-shutdown',
				listeners : {
					'click' : function() {
						win.close();
					}
				}
			}]
		});
		form.getForm().load({
					params : {
						id : id
					},
					url:'resource/contentManager/loadNode'
				});
		win.show();
	},
	delWin:function(){
		var tree = this;
		var id;
		if(tree.getSelectionModel().getSelectedNode()==null){
			Ext.Msg.alert('提示', '请选择记录进行删除!');
			return;
		}else{
			id=tree.getSelectionModel().getSelectedNode().attributes.id;
		}
		if(id.length==1){
			Ext.Msg.alert('提示', '根目录无法进行删除!');
			return;
		}
			Ext.Msg.confirm('提示','确定删除此条记录', function(
							btn) {
						if (btn == 'yes') {
							Ext.Ajax.request({
								url:'resource/contentManager/deleteNode',
								params:{id:id},
								method:'POST',
								success : function(resp) {
									try{
										dm.comm.comm_alert({
													msg : '删除成功...'
												});
										tree.getRootNode().reload();
										tree.grid.loadData();
									} catch (e) {
										// 无错误代码的错误提示信息
										dm.comm.ShowInternetError();
									}
								},
								failure : function(resp) {
									Ext.Msg.alert('提示','删除记录失败');
								}
							});
						} else {
							return;
						}
			});
		//}
	}
});
