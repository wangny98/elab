Ext.namespace('dm.slurryProjectAmount.charts');

dm.slurryProjectAmount.charts.mainPanel= function(){
	
	var mainPanel = this;
	/**
	 * 标签面板的构造函数
	 */
	dm.slurryProjectAmount.charts.mainPanel.superclass.constructor.call(this, {
				fitToFrame: true,                   
		        html: '<iframe  src="business/equip/SlurryProjectAmount.html" frameborder="0" width="100%" height="100%"></iframe>',
				closable : true
			});
	/*mainPanel.on('afterrender', function() {
				// 加载表格数据
				mainPanel.framePanel.loadData();
			});*/

};

Ext.extend(dm.slurryProjectAmount.charts.mainPanel,Ext.Panel, {});