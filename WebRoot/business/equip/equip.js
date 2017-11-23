Ext.namespace('dm.pile.deep');


dm.pile.deep.GridPanel = function(param) {
	var gridPanel = this;
	var pileNumberParam = '32-2-240';
	if(param!=undefined &&param!="undefined" && param!=null && param!=''){
		pileNumberParam = param
	}
	gridPanel.searchConditions = {
			 pileDriverNumber : "",
			 /*startTime : "",
			 endTime : "",*/
			 pileNumber : pileNumberParam,
			 sectionNumber : ''
		};
	
	// 勾选框
	gridPanel.sm = new Ext.grid.CheckboxSelectionModel();
	// 每页显示条数下拉选择框
	gridPanel.pagesize_combo = new dm.object.pagesize_combo50();
	// 每页显示的条数
	gridPanel.pageSize = parseInt(gridPanel.pagesize_combo.getValue());
	gridPanel.store = new Ext.data.Store({
				proxy : new Ext.data.HttpProxy({
							url : '../../resource/pileDeepManager/queryAll',
							method : 'post'
						}),
				reader : new Ext.data.JsonReader({
							totalProperty : 'total',
							root : 'pile'
						}, [{
									name : 'id'
								},{
									name : 'pileDeep'
								}, {
									name : 'Liquid_Cement_Weight'
								}, {
									name : 'cementWeight'
								}, {
									name : 'powerInside'
								}, {
									name : 'powerOutside'
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
			}), /*gridPanel.sm, 
		{
			header : 'ID',
			dataIndex : "id",
			width : 40,
			renderer : function(value) {
				return dm.comm.comm_getTip(value);
			}
		}, */{
			header : '深度',
			dataIndex : "pileDeep",
			width : 40,
			renderer : function(value) {
				return dm.comm.comm_getTip(value);
			}
		}, /*{
			header : '浆量',
			dataIndex : "Liquid_Cement_Weight",
			width : 60,
			renderer : function(value) {
				return dm.comm.comm_getTip(value);
			}
		}, */{
			header : '灰量',
			dataIndex : "cementWeight",
			width : 45,
			renderer : function(value) {
				return dm.comm.comm_getTip(value);
			}
		},{
			header : '内电流',
			dataIndex : "powerInside",
			width : 45,
			renderer : function(value) {
				return dm.comm.comm_getTip(value);
			}
		},{
			header : '外电流',
			dataIndex : "powerOutside",
			width : 45,
			renderer : function(value) {
				return dm.comm.comm_getTip(value);
			}
		}
	]);
	// 分页工具栏
	//gridPanel.ProgressBar = new dm.object.ProgressBarPager();
	gridPanel.mybbar = new dm.object.PagingToolbarOther(gridPanel.pageSize,
			/*gridPanel.ProgressBar,*/ gridPanel.store, gridPanel.pagesize_combo);
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
									//DM.comm.ShowInternetError();
								}
							}
						});
			});
	dm.pile.deep.GridPanel.superclass.constructor.call(this, {
				renderTo:"grid1",
				autoWidth : true,
				height : 800,
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
				// autoExpandColumn : 6,
				// 分页
				bbar : gridPanel.mybbar,
				// 不产横向生滚动条, 各列自动扩展自动压缩, 适用于列数比较少的情况
				viewConfig : {
					forceFit : true
				}
			});
	gridPanel.store.on('beforeload', function() {
				gridPanel.store.baseParams = gridPanel.searchConditions;
			});
};



Ext.extend(dm.pile.deep.GridPanel, Ext.grid.GridPanel, {
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
							}else{
							}
						} catch (e) {
							//console.info("e: ",e);
							//dm.comm.ShowInternetError();
						}
					}
				});
		
		$.ajax({
	        url:'../../resource/pileDeepManager/queryChartData',
	        async:false,
	        data:{
	        	pileNumber:gridPanel.searchConditions.pileNumber
	        },
	        type:'post',
	        contentType: "application/x-www-form-urlencoded; charset=utf-8",
	        cache:false,
	        dataType:'json',
	        success:function(data) {
	        	console.info("data : ",data);
	        	var down={deep:[],cementWeight:[],powerInside:[],powerOutside:[],speed:[]};
				var up={deep:[],cementWeight:[],powerInside:[],powerOutside:[],speed:[]};
				//var all={deep:[],cementWeight:[],powerInside:[],powerOutside:[]};
				var abs={deep:[],cementWeight:[],powerInside:[],powerOutside:[],speed:[]};
				

				for(var b=0;b<data.up.length;b++){
					up.deep.push(data.up[b].pileDeep);
					up.cementWeight.push(data.up[b].cementWeight);
					up.powerInside.push(data.up[b].powerInside);
					up.powerOutside.push(data.up[b].powerOutside);
					up.speed.push(Math.abs(data.up[b].speed));
				}
				buildCharts("west",up.cementWeight,up.deep,up.powerInside,up.powerOutside,up.speed);
				
				for(var a=0;a<data.down.length;a++){
					down.deep.push(data.down[a].pileDeep);
					down.cementWeight.push(data.down[a].cementWeight);
					down.powerInside.push(data.down[a].powerInside);
					down.powerOutside.push(data.down[a].powerOutside);
					down.speed.push(Math.abs(data.down[a].speed));
				}
				buildCharts("middle",down.cementWeight,down.deep,down.powerInside,down.powerOutside,up.speed);
				
				
				for(var c=0;c<data.absSum.length;c++){
					abs.deep.push(data.absSum[c].pileDeep);
					abs.cementWeight.push(data.absSum[c].cementWeight);
					abs.powerInside.push(data.absSum[c].powerInside);
					abs.powerOutside.push(data.absSum[c].powerOutside);
					abs.speed.push(Math.abs(data.absSum[c].speed));
				}
				//console.info("abs.deep : ",abs.deep);
				buildCharts("east",abs.cementWeight,abs.deep,abs.powerInside,abs.powerOutside,up.speed);

				try{
					writeInfo("project","项目  : "+data.machineInfo.PROJECT_NAME);
					writeInfo("section","标段  : "+data.machineInfo.SECTION_NAME);
					writeInfo("equipment","设备  : "+data.machineInfo.EQUIPMENT_NAME+"("+data.machineInfo.EQUIPMENT_CODE+")");
					writeInfo("pilenumber","桩编号  : "+data.machineInfo.PILE_NUMBER);
				}catch(err){
					
				}
	        },
	        error : function() {
	        }
	    });
	},
	refreshData : function() {
	},
	clearSearchCondition : function() {
	}
});

var params = Ext.urlDecode(location.search.substring(1))
console.info("params ",params);
var pile_deep_grid = new dm.pile.deep.GridPanel(params.pileNumberParam);
pile_deep_grid.loadData();

