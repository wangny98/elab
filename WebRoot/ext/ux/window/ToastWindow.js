/**
 * Ext.ux.ToastWindow
 *
 * @author  Edouard Fattal
 * @date	March 14, 2008
 *
 * @class Ext.ux.ToastWindow
 * @extends Ext.Window
 */

Ext.namespace("Ext.ux");


Ext.ux.NotificationMgr = {
	positions: []
};

Ext.ux.Notification = Ext.extend(Ext.Window, {
	initComponent: function(){
		Ext.apply(this, {
			iconCls: this.iconCls || 'x-icon-information',
			cls: 'x-notification',
			width : 220,
			height : 100,
			plain: false,
			autoDestroy : true,
			draggable: false,
			autoHide :  this.autoHide && true, //是否自动隐藏窗口
			hideDelay: this.hideDelay || 3000 ,//如果自动隐藏，n毫秒后隐藏窗口。autoHide为true，hideDelay起作用
			constrain:true,
			bodyStyle: 'text-align:left'
		});
		if(this.autoHide) {
			this.task = new Ext.util.DelayedTask(this.hideWin, this);
		}
		Ext.ux.Notification.superclass.initComponent.call(this);
	},
	hideWin:function(){
				this.hide();
				this.close();//关闭当前窗口
	},
	setMessage: function(msg){
		this.body.update(msg);
	},
	setTitle: function(title, iconCls){
		Ext.ux.Notification.superclass.setTitle.call(this, title, iconCls||this.iconCls);
	},
	onRender:function(ct, position) {
		Ext.ux.Notification.superclass.onRender.call(this, ct, position);
	},
	onDestroy: function(){
		Ext.ux.NotificationMgr.positions.remove(this.pos);
		Ext.ux.Notification.superclass.onDestroy.call(this);
	},
	cancelHiding: function(){
		this.addClass('fixed');
		if(this.autoHide) {
			this.task.cancel();
		}
	},
	afterShow: function(){
		Ext.ux.Notification.superclass.afterShow.call(this);
		Ext.fly(this.body.dom).on('click', this.cancelHiding, this);
		if(this.autoHide) {
			this.task.delay(this.hideDelay || 3000);
	   }
	},
	animShow: function(){
		this.pos = 0;
		while(Ext.ux.NotificationMgr.positions.indexOf(this.pos)>-1)
			this.pos++;
		Ext.ux.NotificationMgr.positions.push(this.pos);
		this.setSize(220,150);
		this.el.alignTo(document, "br-br", [ -20, -20-((this.getSize().height+5)*this.pos) ]);
		this.el.slideIn('b', {
			duration: 1,
			callback: this.afterShow,
			scope: this
		});
	},
	animHide: function(){
		   Ext.ux.NotificationMgr.positions.remove(this.pos);
		this.el.ghost("b", {
			duration: 1,
			remove: true
		});
	},
	
	/**
	 * 调用方法：操作成功，显示成功的信息
	 * @param {} title
	 * @param {} msg
	 */
	showSuccess:function(title,msg){
		this.iconCls=	'x-icon-information',
		this.title = title||'success';
		this.html = msg||'process successfully!';
		this.show(document);	
	},
	/**
	 * 调用方法：操作失败，显示失败的信息
	 * @param {} title
	 * @param {} msg
	 */
	showFailure:function(title,msg){
		this.iconCls = 'x-icon-error',
		this.title = title||'success';
		this.html = msg||'process successfully!';
		this.show(document);	
	},
	/**
	 * 调用方法：显示操作结果的信息
	 * @param {} title
	 * @param {} msg
	 * @param {} success 操作是否成功
	 */
	showMessage:function(title,msg,success){
		if(success){
			this.iconCls=	'x-icon-information';
			this.autoHide=true;//自动隐藏窗口
			this.task = new Ext.util.DelayedTask(this.hideWin, this);
		}
		else{
			this.iconCls=	'x-icon-error';
			}
			this.title = title;
			this.html = msg;
			this.show(document);			
	},
	focus: Ext.emptyFn 

}); 