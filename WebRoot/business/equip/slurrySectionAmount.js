Ext.namespace('dm.slurrySectionAmount.charts');

dm.slurrySectionAmount.charts.mainPanel= function(){
	
	var mainPanel = this;
	/**
	 * 标签面板的构造函数
	 */
	dm.slurrySectionAmount.charts.mainPanel.superclass.constructor.call(this, {
				fitToFrame: true,                   
		        html: '<iframe  src="business/equip/SlurrySectionAmount.html" frameborder="0" width="100%" height="100%"></iframe>',
				closable : true
			});
	/*mainPanel.on('afterrender', function() {
				// 加载表格数据
				mainPanel.framePanel.loadData();
			});*/

};

Ext.extend(dm.slurrySectionAmount.charts.mainPanel,Ext.Panel, {});