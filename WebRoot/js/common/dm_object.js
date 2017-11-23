Ext.namespace("dm.object");

/**
 * 时间区域
 * 
 * @class DateField
 * @extends Ext.form.DateField
 */
dm.object.DateField = Ext.extend(Ext.form.DateField, {
			width : 300,
			labelSeparator : "：",
			allowBlank : false,
			selectOnFocus : true,
			format : 'Y-m-d',
			setValue : function(date) {
				if (Ext.isEmpty(date)) {
				} else if (Ext.isEmpty(date.time)) {
					date = new Date(Date.parse(date.toString().replace(/-/g,
							"/")));
					
				} else {
					date = new Date(date.time);
				}
				Ext.form.DateField.superclass.setValue.call(this, this
								.formatDate(this.parseDate(date)));
			},
			// blankText : '该字段不能为空！',
			initComponent : function() {
				dm.object.DateField.superclass.initComponent.call(this);

			},
			onRender : function() {
				dm.object.DateField.superclass.onRender
						.apply(this, arguments);
			}

		});

Ext.reg('datefield', dm.object.DateField);
/**
 * 重写Ext.form.TextField实现前后去空操作
 * 
 * @class TextField
 * @extends Ext.form.TextField
 */
Ext.override(Ext.form.TextField, {
			labelSeparator : "：",
			onBlur : function() {
				if (!Ext.isEmpty(this.el.dom.value)
						&& this.autoEl.type != "file") {
					this.el.dom.value = this.el.dom.value.trim();
				}
				this.beforeBlur();
				if (this.focusClass) {
					this.el.removeClass(this.focusClass);
				}
				this.hasFocus = false;
				if (this.validationEvent !== false
						&& (this.validateOnBlur || this.validationEvent == 'blur')) {
					this.validate();
				}
				var v = this.getValue();
				if (String(v) !== String(this.startValue)) {
					this.fireEvent('change', this, v, this.startValue);
				}
				this.fireEvent('blur', this);
				this.postBlur();
			}
		});

/**
 * 重写Ext.form.TextArea实现前后去空操作
 * 
 * @class TextArea
 * @extends Ext.form.TextArea
 */
Ext.override(Ext.form.TextArea, {
			labelSeparator : "：",
			onBlur : function() {
				if (!Ext.isEmpty(this.el.dom.value)) {
					this.el.dom.value = this.el.dom.value.trim();
				}
				this.beforeBlur();
				if (this.focusClass) {
					this.el.removeClass(this.focusClass);
				}
				this.hasFocus = false;
				if (this.validationEvent !== false
						&& (this.validateOnBlur || this.validationEvent == 'blur')) {
					this.validate();
				}
				var v = this.getValue();
				if (String(v) !== String(this.startValue)) {
					this.fireEvent('change', this, v, this.startValue);
				}
				this.fireEvent('blur', this);
				this.postBlur();
			}

		});

/**
 * 分页工具栏
 * 
 * @class PagingToolbar
 * @extends Ext.PagingToolbar
 */
dm.object.pagesize_combo = Ext.extend(Ext.form.ComboBox, {
			name : 'pagesize',
			triggerAction : 'all',
			mode : 'local',
			store : new Ext.data.ArrayStore({
						fields : ['value', 'text'],
						data : [[10, '10条/页'], [20, '20条/页'], [50, '50条/页'],
								[100, '100条/页']]
					}),
			valueField : 'value',
			displayField : 'text',
			value : '20',
			editable : false,
			width : 85

		});

dm.object.pagesize_combo50 = Ext.extend(Ext.form.ComboBox, {
	name : 'pagesize',
	triggerAction : 'all',
	mode : 'local',
	store : new Ext.data.ArrayStore({
				fields : ['value', 'text'],
				data : [[10, '10条/页'], [20, '20条/页'], [50, '50条/页'],
						[100, '100条/页']]
			}),
	valueField : 'value',
	displayField : 'text',
	value : '50',
	editable : false,
	width : 85

});

dm.object.PagingToolbar = function(pageSize, ProgressBar, store, combo) {
	dm.object.PagingToolbar.superclass.constructor.call(this, {
				pageSize : pageSize,
				store : store,
				autoWidth : true,
				autoShow : true,
				items : ['-', '&nbsp;', combo],
				displayInfo : true,
				displayMsg : '显示第 {0} 条到 {1} 条记录,一共 {2} 条',
				emptyMsg : '没有符合条件的记录',
				plugins : ProgressBar
			});
};

Ext.extend(dm.object.PagingToolbar, Ext.PagingToolbar, {});

dm.object.PagingToolbarOther = function(pageSize, store, combo) {
	dm.object.PagingToolbarOther.superclass.constructor.call(this, {
				pageSize : pageSize,
				store : store,
				autoWidth : true,
				autoShow : true,
				items : ['-', '&nbsp;', combo],
				displayInfo : true,
				emptyMsg : '没有符合条件的记录'
			});
};

Ext.extend(dm.object.PagingToolbarOther, Ext.PagingToolbar, {});

dm.object.SimplePagingToolbar = function(pageSize, ProgressBar, store) {
	dm.object.SimplePagingToolbar.superclass.constructor.call(this, {
				pageSize : pageSize,
				store : store,
				autoWidth : true,
				autoShow : true,
				// items:['-', '&],
				displayInfo : true,
				displayMsg : '显示第 {0} 条到 {1} 条记录,一共 {2} 条',
				emptyMsg : '没有符合条件的记录',
				plugins : ProgressBar
			});
};

Ext.extend(dm.object.SimplePagingToolbar, Ext.PagingToolbar, {});



/*Ext.override(Ext.Component, {
			initComponent : Ext.Component.prototype.initComponent
					.createInterceptor(function() {
								if (this.allowBlank === false
										&& this.fieldLabel) {
									this.fieldLabel = '<span style="color:blue;">'
											+ this.fieldLabel + '</span>';
								}
								if (this.funcCode
										&& GLOBAL_FUNCCODE.indexOf(this.funcCode) < 0) {
									this.hidden = true;
								}
							})
		});*/
