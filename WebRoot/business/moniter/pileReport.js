GWB Ext.namespace('dm.moniter.pileReport');

dm.moniter.pileReport.mainPanel= function(){
	
	var mainPanel = this;
	/**
	 * 标签面板的构造函数
	 */
	dm.moniter.pileReport.mainPanel.superclass.constructor.call(this, {
				fitToFrame: true,                   
		        html: '<iframe  src="business/moniter/pileReport.html" frameborder="0" width="100%" height="100%"></iframe>',
				closable : true
			});
};

Ext.extend(dm.moniter.pileReport.mainPanel,Ext.Panel, {});