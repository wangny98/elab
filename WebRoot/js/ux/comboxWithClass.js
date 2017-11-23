Ext.namespace('dm.comboxWithTree');

dm.comboxWithTree.classes=Ext.extend(Ext.form.ComboBox,{
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
		var classComboWithTree=this;
		Ext.apply(this, config);
		this.divId=Ext.id()+'-tree';
		this.tpl="<tpl for='.'><div style='height:200px'><div id='"+this.divId+"'></div></div></tpl>";
		dm.comboxWithTree.classes.superclass.constructor.call(this);
		var tree=new Ext.tree.TreePanel({
						root : new Ext.tree.AsyncTreeNode({
							id : '0',
							text : '资产分类',
							expanded : false,
							iconCls : 'icon-systemSet-enterprise'
			
						}),
						loader : new Ext.tree.TreeLoader({
							url : 'resource/classificationManager/classTree?id=0',
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
			/*if (orgComboWithTree.unselectOrgType.indexOf(node.attributes.orgType) >0 || node.id == 0) {
				Ext.Msg.alert('提示', '');
				orgComboWithTree.isSelectAction = false;
				orgComboWithTree.collapse();
				return;
			}*/
			classComboWithTree.setValue(node);
			classComboWithTree.collapse();
			classComboWithTree.isSelectAction = false;
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
			dm.comboxWithTree.classes.superclass.setValue.call(this, text);
			this.value = node;
			return this;
		}
	}
});
Ext.reg('comboxTreeClass',dm.comboxWithTree.classes);