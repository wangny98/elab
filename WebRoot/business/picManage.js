Ext.namespace('dm.picManage');
dm.picManage.grid = function(config){
	var grid = this;
	Ext.apply(grid,config);
	grid.pagesize_combo = new dm.object.pagesize_combo();
	grid.pageSize = parseInt(grid.pagesize_combo.getValue());
	grid.store = new Ext.data.Store({
		proxy : new Ext.data.HttpProxy({
			url : 'resource/picManage/search',
			method : 'post'
		}),
		reader : new Ext.data.JsonReader({
			totalProperty : 'total',
			root : 'list'
		}, [ {
			name : 'id'
		}, {
			name : 'pic_descript'
		}, { 
			name : 'pic_name'
		}]),
		baseParams : {
			searchName : ''
		}
	});
	
	grid.cm = new Ext.grid.ColumnModel([{
		header : '名称',
		dataIndex : "pic_name", // 编码
		sortable : true,
		renderer : function(value) {
			return dm.comm.comm_getTip(value);
		}
	},{
		header : '描述',
		dataIndex : "pic_descript", // 编码
		sortable : true,
		renderer : function(value) {
			return dm.comm.comm_getTip(value);
		}
	}]);
	
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
	
	grid.searchTxt = Ext.create({
		xtype : 'textfield',
		width : 150,
		emptyText : '请输入单号后按回车',
		enableKeyEvents : true,
		listeners : {
			specialkey : function(field, e) {
				if (e.getKey() == Ext.EventObject.ENTER) {
					grid.loadData();
				}
			}
		}
	});
	
	dm.picManage.grid.superclass.constructor.call(this, {
		closable:true,
		viewConfig : {
			forceFit : true
		// 不产横向生滚动条, 各列自动扩展自动压缩, 适用于列数比较少的情况
		},
		height : '400',
		store : grid.store,
		columns : grid.cm,
		bbar : grid.ppBar,
		tbar : [{
			text : '添加图片', // 修改
			iconCls : 'icon-add',
			funcCode : '',
			handler : function() {
				grid.addPic(grid.txt.getValue());
			}
		},{
			text : '修改描述', // 修改
			iconCls : 'icon-modify',
			funcCode : '',
			handler : function() {
				grid.updWin();
			}
		}]
	});
	
	grid.store.on('beforeload', function() {
		this.baseParams = {
			searchName : grid.searchTxt.getValue(),
			show_id : grid.show_id
		};
	});
	grid.loadData();
};

Ext.extend(dm.picManage.grid,Ext.grid.GridPanel,{
	loadData : function() {
		var grid = this;
		grid.getStore().load({
			params : {
				start : 0,
				limit : grid.pageSize
			},
			callback : function(records, options, success) {
				// dm.comm.showGridError();
			}
		});
	},
	updWin : function() {
		var gird = this;
		var len = this.getSelectionModel().getSelections().length;
		if (len == 0) {
			Ext.Msg.alert('提示', '请选择一条记录');
			return;
		}
		var id = this.getSelectionModel().getSelected().data.id;
		//console.info("id",id);
		var form = new Ext.form.FormPanel({
			width : 425,
			baseCls : "x-plain",
			labelAlign : 'right',
			reader: new Ext.data.JsonReader({
				successProperty : 'success',
				root : 'bean'
			}, [ {
				name : 'id'
			}, {
				name : 'pic_descript'
			}]),
			items : [{
				xtype:'textarea',
	        	fieldLabel:'描述',
	        	name:'descript',
	        	width:200
			}]			
		});
		var win = new Ext.Window({
			width : 425,
			height :150,
			bodyStyle : 'padding:5px;',
			items : [form],
			buttons : [{
				text : '确定',
				listeners : {
					'click' : function(){
						if (form.getForm().isValid()) {
							form.getForm().doAction('submit', {
								url : "resource/picManage/modify",
								method : 'post',
								params:{id:id},
								waitMsg : '正在加载',
								timeout : 2000,
								success : function(response) {
									dm.comm.comm_alert({
										msg : '修改成功'
									});
									win.close();
									gird.loadData();
								},
								failure : function(response, action) {

								}
							});

						} else {
							return;
						}
						
					}
				}
			},{
				text : '取消',
				listeners : {
					'click' : function(){
						win.close();
					}
				}
			}]
		});
		form.getForm().load({
			params : {
				id : id
			},
			url : "resource/picManage/load"
		});
		win.show();
	},
	addPic:function(pic_descript){
		var grid = this;
		var submitEl=Ext.create({
			xtype : 'uploadPanel',
			title:'添加图片',
			height : '400',
			border : false,
			fileSize : 1024 * 550,// 限制文件大小
			uploadUrl : 'resource/picManage/uploadFiles',
			flashUrl : 'component/flashUpload/uploader/swfupload.swf',
			filePostName : 'file', // 后台接收参数
			fileTypes : '*.*',// *.jpg;*.jpeg;*.gif;*.png;*.bmp, 可上传文件类型
			postParams : {
				show_id:grid.show_id,
				pic_descript:pic_descript
			}// 上传文件存放目录
		});
		submitEl.on('beforeUploadEvent',function(file){
			submitEl.postParams = {
					show_id:grid.show_id,
					pic_descript:pic_descript
				};
			submitEl.setting.post_params = {
					show_id:grid.show_id,
					pic_descript:pic_descript
				};
		});
		submitEl.on('uploadcompleteEvent', function(store, fileName, index,filestatus) {
			console.info("filestatus",filestatus);
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
			height : 450,
			layout : 'fit',
			modal:true,
			items : [submitEl]
		});
		win.show();
	}
	
});

dm.picManage.win = function(show_id){
	var win = this;
	
	
	win.grid = new dm.picManage.grid({show_id : show_id});
	
	win.grid.txt = new Ext.form.TextArea({
		fieldLabel:'描述',
		width : 510,
		name : 'pic_descript'
	});
	win.form = new Ext.form.FormPanel({
		height : '250',
		labelAlign : 'right',
		items:[win.grid.txt]
	});
	
	dm.picManage.win.superclass.constructor.call(this, {
		title : '图片',
		resizable : false,
		modal : true,
		width : 725,
		height : '500',
		closeAction : 'destroy',
		bodyStyle : 'padding:5px;',
		closable : true,
		items : [ win.form,win.grid],
		buttonAlign : 'center',
		buttons:[{
			text : '取消',
			listeners : {
				'click' : function(){
					win.close();
				}
			}
		}]
	});
};

Ext.extend(dm.picManage.win,Ext.Window,{
	
});