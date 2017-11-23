Ext.namespace('dm.slurrySectionPerday.charts');

dm.slurrySectionPerday.charts.mainPanel= function(){
	
	var mainPanel = this;
	/**
	 * 标签面板的构造函数
	 */
	dm.slurrySectionPerday.charts.mainPanel.superclass.constructor.call(this, {
				fitToFrame: true,                   
		        html: '<iframe  src="business/equip/SlurrySectionPerday.html" frameborder="0" width="100%" height="100%"></iframe>',
				closable : true
			});
	/*mainPanel.on('afterrender', function() {
				// 加载表格数据
				mainPanel.framePanel.loadData();
			});*/

};

Ext.extend(dm.slurrySectionPerday.charts.mainPanel,Ext.Panel, {});