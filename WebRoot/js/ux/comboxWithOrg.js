Ext.namespace('dm.comboxWithTree');

dm.comboxWithTree.org=Ext.extend(Ext.form.ComboBox,{
	selectTree : null,

	store : new Ext.data.ArrayStore({
				fields : ['value', 'text'],
				data : [[]]
			}),
	editable : false,
	blankText : null,
	emptyText : null,
	selectOnFocus : true,
	forceSelection : true,
	width : 177,
	mode : 'local',
	triggerAction : 'all',
	maxHeight : 300,
	divId:null,
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
	listeners : {
		'expand' : function(comboxBox) {
			comboxBox.list.setWidth('250px'); // auto
			comboxBox.innerList.setWidth('auto');
			this.selectTree.render(comboxBox.divId);
		}
	},
	unselectOrgType:[],
	unselectOrgTypeVtext:null,
	constructor : function(config) {
		var orgComboWithTree=this;
		Ext.apply(this, config);
		this.divId=Ext.id()+'-tree';
		this.tpl="<tpl for='.'><div style='height:200px'><div id='"+this.divId+"'></div></div></tpl>";
		dm.comboxWithTree.org.superclass.constructor.call(this);
		var tree=new Ext.tree.TreePanel({
						root : new Ext.tree.AsyncTreeNode({
							id : '0',
							text : '组织机构',
							expanded : false,
							iconCls : 'icon-systemSet-enterprise'
			
						}),
						loader : new Ext.tree.TreeLoader({
							url : 'resource/OrgService/getTree?id=0',
							method : 'post'
						}),
						useArrows : false,
						width : 250,
						rootVisible : true,
						border : true,
						animate : true,
						autoScroll : true,
						enableDD : false
		});
		tree.on('click',function(node){
			if(node.attributes.type == 1){
				Ext.Msg.alert('提示', '请选择部门!');
				return;
			}
			orgComboWithTree.setValue(node);
			orgComboWithTree.collapse();
			orgComboWithTree.isSelectAction = false;
			return;
		});
		tree.on('afterrender', function(tree) {
							tree.expandAll();
						});
		this.selectTree=tree;
	},
	
	setValue : function(node) {
		
		if(node==null){
			return this;
		}
		if (typeof node == "object") {
			// 当node为object对象时 node和tree里面的对应
			this.lastSelectionText = node.text;
			this.setRawValue(node.text);
			if (this.hiddenField) {
				this.hiddenField.value = node.id;
			}
			this.value = node.id;
			return this;
		} else {
			// 当node为文本时 这段代码是从combo的源码中拷贝过来的 
			var text = node;
			if (this.valueField) {
				var r = this.findRecord(this.valueField, node);
				if (r) {
					text = r.data[this.displayField];
				} else if (Ext.isDefined(this.valueNotFoundText)) {
					text = this.valueNotFoundText;
				}
			}
			this.lastSelectionText = text;
			if (this.hiddenField) {
				this.hiddenField.value = node;
			}
			dm.comboxWithTree.org.superclass.setValue.call(this, text);
			this.value = node;
			return this;
		}
	}
});
Ext.reg('comboxTreeOrg',dm.comboxWithTree.org);