/**
 * Title: 自定义界面窗口
 * 
 * Description: 用户自定义界面模板生成
 * 
 * 
 * Copyright: Copyright (c) 2012
 * 
 * Company: 新模式
 * 
 * @author 周晶
 * @version V1.0 2012-09-20
 * @since
 */
Ext.namespace('ERMS.customEntityList');


ERMS.customEntityList.customGrid = Ext.extend(Ext.grid.GridPanel,{
	viewValue:null,
	
	entityId:null,
	entityName:null,
	showProgressBar:true,
	cmArray:[],
	
	closable : true,
	title : '自定义列表',
	region : 'center',
	loadMask : {
			msg : ERMS_COMM_LOADMASK
	},
	height : 280,
	loadData:function(){
		this.getStore().load({
			params:{
				start : 0,
				limit : this.getPagesize()
			},
			callback : function(records, options, success) {
								try {
									if (!success) {
										ERMS.comm.showGridError();
									}
								} catch (e) {
									ERMS.comm.ShowInternetError();
								}
							}
		});
	},
	constructor:function(config){ 
		var grid=this;
		Ext.apply(this, config);
		
		if(this.entityId==null){
			Ext.Ajax.request({
						url : 'resource/Component/getEntityRegisterId',
						method : 'POST',
						async:false,
						params:{
								entityName : this.entityName
								},
						success : function(resp){
								var json=Ext.util.JSON.decode(resp.responseText);
						  		var flag=json.success;
						  		if(flag){
						  			//console.info("json.result : ",json.result);
						  			grid.entityId=json.result;
						  		}else{
						  			
						  		}
						}
					});
		}
		if(this.cmArray.length==0){
			//console.info("根据实体名称     "+this.entityName+"  获取得到的ID为   "+this.entityId);
			Ext.Ajax.request({
							url : 'resource/Component/listEntityCM',
							method : 'POST',
							async:false,
							params:{
									entityId:this.entityId
									},
							success : function(resp){
									var json=Ext.util.JSON.decode(resp.responseText);
							  		var flag=json.success;
							  		if(flag){
							  			//console.info("json.root : ",json.root);
							  			grid.cmArray=json.root;
							  			//console.info("this.cmArray  ",grid.cmArray);
							  		}else{
							  			
							  		}
							}
						});
			this.colModel=new Ext.grid.ColumnModel(grid.cmArray);
		}
		this.store = new Ext.data.Store({
			proxy:new Ext.data.HttpProxy({
						url:'resource/Component/infoEntityByPage',
						method:'POST'
				}),
			reader:new Ext.data.JsonReader(),
			baseParams : {
							entityName : this.entityName,
							entityId : this.entityId
						}
		});
		grid.pagesize_combo = new ERMS.object.pagesize_combo();
		grid.getPagesize=function(){return parseInt(grid.pagesize_combo.getValue())};
		
		if(grid.showProgressBar){
			grid.ProgressBar=new ERMS.object.ProgressBarPager();
			grid.bbar=new ERMS.object.PagingToolbar(grid.getPagesize(),
				grid.ProgressBar,grid.store,grid.pagesize_combo);
		}else{
			grid.bbar=new Ext.PagingToolbar({
				pageSize : 20,
				store : grid.store,
				displayInfo : true,
				displayMsg : ERMS_PAGINGBAR_INFO,
				emptyMsg : ERMS_COMM_EMPTYMSG
			})
		}
		grid.pagesize_combo.on('select',function(comboBox){
			grid.bbar.pageSize=grid.getPagesize();
			grid.getStore().load({
				params:{
						start : 0,
						limit : grid.getPagesize()
					},
				callback : function(records, options, success) {
									try {
										if (!success) {
											ERMS.comm.showGridError();
										}
									} catch (e) {
										ERMS.comm.ShowInternetError();
									}
					}
			});
		});		
				
		ERMS.customEntityList.customGrid.superclass.constructor.call(this);
		//grid.loadData();
	},
	init:function(combox){
		this.combox=combox;
	},
	listeners : {
				rowdblclick : function() {
					var record = this.getSelectionModel().getSelected();
					
					var node={id:record.get('ID'),text:record.get(this.viewValue)};
					//console.info("双击事件。。。。。。。。。。。",node);
					if(this.combox){
						this.combox.setValue(node);
						this.combox.collapse();
					}
				},
				beforerender:function(){
					this.loadData();
				}
	}

})

Ext.reg('customGrid',ERMS.customEntityList.customGrid);



ERMS.customEntityList.comboxWithGrid = Ext.extend(Ext.form.ComboBox,{
	selectGrid	:	{},
	
	store : new Ext.data.ArrayStore({
				fields : ['value', 'text'],
				data : [[]]
			}),
	editable : false,
	//allowBlank : false,
	selectOnFocus : true,
	forceSelection : true,
	width : 177,
	mode : 'local',
	triggerAction : 'all',
	maxHeight : 300,
	gridId:null,
	
	onSelect : Ext.emptyFn,
	onViewClick : function(doFocus) {
					var index = this.view.getSelectedIndexes()[0], 
					s = this.store, r = s.getAt(index);
					 if (r) {
							this.onSelect(r, index);
							}
					if (doFocus !== false) {
							this.el.focus();
						}
	},
	listeners : {
				 'expand':function(comboBox){
					 this.list.setWidth('400px');
					 this.innerList.setWidth('auto');
					 this.selectGrid.render(comboBox.gridId);
				 }/*,
				 'render':function(){
				 	this.selectGrid.store.load();
				 }*/
	},
	setValue : function(node) {
		if (typeof node == "object") {
			// 当node为object对象时 node和tree里面的对应
			this.lastSelectionText = node.text;
			this.setRawValue(node.text);
			if (this.hiddenField) {
				this.hiddenField.value = node.id;
			}
			this.value = node.id;
			return this;
		} else {
			// 当node为文本时 这段代码是从combo的源码中拷贝过来的 具体作用不细说了
			var text = node;
			if (this.valueField) {
				var r = this.findRecord(this.valueField, node);
				if (r) {
					text = r.data[this.displayField];
				} else if (Ext.isDefined(this.valueNotFoundText)) {
					text = this.valueNotFoundText;
				}
			}
			this.lastSelectionText = text;
			if (this.hiddenField) {
				this.hiddenField.value = node;
			}
			ERMS.comboxWithTree.classic.superclass.setValue.call(this, text);
			this.value = node;
			return this;
		}
	},
						
	constructor	: function(config){
			Ext.apply(this, config);
			this.gridId=Ext.id()+'-grid';
			this.tpl = "<tpl for='.'><div style='height:100px'><div id='"+this.gridId+"'></div></div></tpl>",
			this.selectGrid=Ext.create(this.selectGrid);
			
			ERMS.customEntityList.comboxWithGrid.superclass.constructor.call(this,{
				plugins:this.selectGrid
			});
	}
	
});

Ext.reg('comboxWithGrid',ERMS.customEntityList.comboxWithGrid);