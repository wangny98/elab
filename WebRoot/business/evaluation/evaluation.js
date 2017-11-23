Ext.namespace('dm.evaluation.main');

dm.evaluation.main.mainPanel= function(){
	
	var mainPanel = this;
	/**
	 * 标签面板的构造函数
	 */
	dm.evaluation.main.mainPanel.superclass.constructor.call(this, {
				fitToFrame: true,                   
		        html: '<iframe  src="business/evaluation/evaluation.html" frameborder="0" width="100%" height="100%"></iframe>',
				closable : true
			});
};

Ext.extend(dm.evaluation.main.mainPanel,Ext.Panel, {});