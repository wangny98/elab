Ext.namespace('dm.functionCode');

dm.functionCode.win = function(roleId){
	var win = this;
	win.tree = new dm.functionCode.functionTree(roleId);
	dm.functionCode.win.superclass.constructor.call(this, {
		title:'分配功能',
		items:[win.tree],
		width:264,
		height:465,
		buttonAlign : 'center',
		buttons : [{
			text : '保存',
			handler : function() {
				var checked = win.tree.getChecked();
				var code = "";
				for (i = 0; i < checked.length; i++) {
					//if (checked[i].attributes.leaf == true) {
						if(i==checked.length-1){
							code = code + checked[i].attributes.idfex;
						}else{
							code = code + checked[i].attributes.idfex + ",";
						}
					//}
				}
				
				Ext.Ajax.request({
					url:'resource/functionManage/modiftyFunc',
					method : 'post',
					params:{
						roleId:roleId,
						codes:code
					},
					success : function(response) {
						var json = Ext.util.JSON.decode(response.responseText);
							var flag = json.success;
							if (flag) {
								dm.comm.comm_alert({
										msg : '分配成功'
									});
								win.close();
							}else{
								dm.comm.comm_alert({
										msg : '分配失败'
									});
							}
					},
					failure : function(response) {
						
					}
				});
			}
		}/*,{
			text : '重置',
			handler : function(){
				functionTree.cancelSelected();
			}
		}*/,{
			text : '关闭',
			handler : function(){
				win.close();
			}
		}]
	
	});
};
Ext.extend(dm.functionCode.win,Ext.Window);

dm.functionCode.functionTree = function(roleId){
	var functionTree = this;
	functionTree.rootnode = new Ext.tree.AsyncTreeNode({
		id : '0',
		text : '功能树',
		expanded : true
	});
	dm.functionCode.functionTree.superclass.constructor.call(this, {
		root : functionTree.rootnode,
		width:250,
		height:400,
		useArrows : false,
		rootVisible : false, // 显示树节点
		border : false, // 边框
		animate : true, // 动画效果
		autoScroll : true, // 自动滚动
		enableDD : false,
		dataUrl : 'resource/functionManage/loadCheckTree?roleId='+roleId
	});
	
	functionTree.on('checkchange', function(node, checked) {

		var parentNode = node.parentNode;
		if (parentNode !== null) {
			functionTree.parentCheck(parentNode, checked);
		}
		node.expand();
		node.attributes.checked = checked;
		node.eachChild(function(child) {

					child.ui.toggleCheck(checked);
					child.attributes.checked = checked;
					child.fireEvent('checkchange', child, checked);
				});
	}, functionTree);
};

Ext.extend(dm.functionCode.functionTree,Ext.tree.TreePanel,{
	
	
	cancelSelected : function() {
		selected = this.getChecked();
		Ext.each(selected, function(node) {
					node.getUI().toggleCheck(false);
					node.attributes.checked = false;
				});
	},
	parentCheck : function(node, checked) {
		treePanel = this;
		var checkbox = node.getUI().checkbox;
		if (typeof checkbox == 'undefined')
			return false;
		if (!(checked ^ checkbox.checked))
			return false;
		if (!checked && treePanel.childHasChecked(node))
			return false;
		checkbox.checked = checked;
		node.attributes.checked = checked;
		node.getOwnerTree().fireEvent('check', node, checked);

		var parentNode = node.parentNode;
		if (parentNode !== null) {
			treePanel.parentCheck(parentNode, checked);
		}

	},
	/**
	 * 判断是否有子结点被选中
	 */
	childHasChecked : function(node) {
		var childNodes = node.childNodes;

		if (childNodes || childNodes.length > 0) {

			for (var i = 0; i < childNodes.length; i++) {

				if (childNodes[i].getUI().checkbox.checked)

					return true;

			}
		}
		return false;
	}
});