Ext.namespace('dm.moniter.pile');

dm.moniter.pile.mainPanel= function(){
	
	var mainPanel = this;
	/**
	 * 标签面板的构造函数
	 */
	dm.moniter.pile.mainPanel.superclass.constructor.call(this, {
				fitToFrame: true,                   
		        html: '<iframe  src="business/moniter/pile.html" frameborder="0" width="100%" height="100%"></iframe>',
				closable : true
			});
	/*mainPanel.on('afterrender', function() {
				// 加载表格数据
				mainPanel.framePanel.loadData();
			});*/

};

Ext.extend(dm.moniter.pile.mainPanel,Ext.Panel, {});