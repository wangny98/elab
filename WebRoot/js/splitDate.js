Ext.namespace('LTMS.test');

LTMS.test = function(){
	var form = this;
	LTMS.test.superclass.constructor.call(this, {
				title : 'Test',
				closable : true,
				items : [{
							layout : "column",
							baseCls : "x-plain",
							labelAlign : "right",
							items : [{
										baseCls : "x-plain",
										bodyStyle : "padding:5px 0px 0px",
										columnWidth : 0.8,
										items : [{
													layout: "form",
													baseCls : "x-plain",
													labelAlign : "right",
													bodyStyle : "padding:5px 0px 0px",
													height : 350,
													items : [{
																xtype : 'numberfield',
																name:'id',
																fieldLabel:'id'
															},{
																xtype : 'textfield',
																name:'userName',
																fieldLabel:'userName'
															},{
																xtype : 'numberfield',
																name:'age',
																fieldLabel:'age'
															}]
												}]
									}, {
										baseCls : "x-plain",
										bodyStyle : "padding:5px 0px 0px",
										columnWidth : 0.2,
										items : [{
													baseCls : "x-plain",
													labelAlign : "right",
													bodyStyle : "padding:5px 0px 0px",
													height : 35,
													items : [{
																xtype : 'button',
																width:40,
																handler:function(){
																	//var params = form.getValues();
																	Ext.Ajax.request({
																		url : 'resource/testClass/testFunc',
																		params:form.form.getValues(),
																		method : 'post',
																		success : function(resp, options) {
																			var respText = Ext.util.JSON.decode(resp.responseText);
																			if (respText.success) {
																				alert('测试成功');
																			}
																		}, 
																		failure:function(form,action){
																			alert('测试失败');
																		}
																	})
																}
															},{
																xtype:'button',
																fieldLabel:'登入',
																handler:function(){
																	Ext.Ajax.request({
																		url : 'resource/security/validate',
																		params:form.form.getValues(),
																		method : 'post',
																		success : function(resp, options) {
																			var respText = Ext.util.JSON.decode(resp.responseText);
																			if (respText.success) {
																				alert('测试成功');
																			}
																		}, 
																		failure:function(form,action){
																			alert('测试失败');
																		}
																	});
																}
															}]
												}]
									}]
						}]
					});
};

Ext.extend(LTMS.test,Ext.form.FormPanel);