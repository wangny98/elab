
Ext.ux.ComboBoxTree = function() {
	this.treeId = Ext.id() + '-tree';
	this.maxHeight = arguments[0].maxHeight || arguments[0].height
			|| this.maxHeight;
	this.tpl = new Ext.Template('<tpl for="."><div style="height:'
			+ this.maxHeight + 'px"><div id="' + this.treeId
			+ '"></div></div></tpl>');
	this.store = new Ext.data.SimpleStore({
				fields : [],
				data : [[]]
			});
	this.selectedClass = '';
	this.mode = 'local';
	this.triggerAction = 'all';
	this.onSelect = Ext.emptyFn;
	this.editable = false;
	this.beforeBlur = Ext.emptyFn;

	// all:所有结点都可选中
	// exceptRoot：除根结点，其它结点都可选（默认）
	// folder:只有目录（非叶子和非根结点）可选
	// leaf：只有叶子结点可选
	this.selectNodeModel = arguments[0].selectNodeModel || 'exceptRoot';
	
	this.isSelectAction =  arguments[0].isSelectAction || 'exceptRoot';

	this.addEvents('afterchange');

	Ext.ux.ComboBoxTree.superclass.constructor.apply(this, arguments);

}

Ext.extend(Ext.ux.ComboBoxTree, Ext.form.ComboBox, {

	expand : function() {
		Ext.ux.ComboBoxTree.superclass.expand.call(this);
		if (this.tree.rendered) {
			return;
		}

		Ext.apply(this.tree, {
					height : this.maxHeight,
					width : (this.listWidth || this.width - (Ext.isIE ? 3 : 0))
							- 2,
					border : false,
					autoScroll : true
				});
		if (this.tree.xtype) {
			this.tree = Ext.ComponentMgr.create(this.tree, this.tree.xtype);
		}
		this.tree.render(this.treeId);

		var root = this.tree.getRootNode();
		if (!root.isLoaded())
			root.reload();

		this.tree.on('click', function(node) {
//					var selModel = this.selectNodeModel;
//					var isLeaf = node.isLeaf();
//
//					if ((node == root) && selModel != 'all') {
//						return;
//					} else if (selModel == 'folder' && isLeaf) {
//						return;
//					} else if (selModel == 'leaf' && !isLeaf) {
//						return;
//					}
			
					var isa = this.isSelectAction;
					if(isa == false){
						return;
					}
					var oldNode = this.getNode();
					if (this.fireEvent('beforeselect', this, node, oldNode) !== false) {
						this.setValue(node.text);
						this.collapse();

						this.fireEvent('select', this, node, oldNode);
						(oldNode !== node) ? this.fireEvent('afterchange',
								this, node, oldNode) : '';
					}
				}, this);
	},

	/*setValue : function(text) {
		//this.node = node;
		//var text = node.text;
		this.lastSelectionText = text;
		if (this.hiddenField) {
			this.hiddenField.value = node.id;
		}
		Ext.form.ComboBox.superclass.setValue.call(this, text);
		this.value = node.id;
	},*/

	getValue : function() {
		return typeof this.value != 'undefined' ? this.value : '';
	},

	getNode : function() {
		return this.node;
	},

	clearValue : function() {
		Ext.ux.ComboBoxTree.superclass.clearValue.call(this);
		this.node = null;
	},
	// 重写onViewClick，使展开树结点是不关闭下拉框
	onViewClick : function(doFocus) {
		var index = this.view.getSelectedIndexes()[0], s = this.store, r = s
				.getAt(index);
		if (r) {
			this.onSelect(r, index);
		}
		if (doFocus !== false) {
			this.el.focus();
		}
	},
	// private
	destroy : function() {
		Ext.ux.ComboBoxTree.superclass.destroy.call(this);
		Ext.destroy([this.node, this.tree]);
		delete this.node;
	}
});

Ext.reg('combotree', Ext.ux.ComboBoxTree);