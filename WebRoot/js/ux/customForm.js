Ext.namespace('dm.privateForm');

dm.privateForm.BasicForm=Ext.extend(Ext.form.BasicForm,{
	setValues : function(values){
		//console.info("setValues : ");
        if(Ext.isArray(values)){
            for(var i = 0, len = values.length; i < len; i++){
                var v = values[i];
                var f = this.findField(v.id);
                if(f){
                    f.setValue(v.value);
                    if(this.trackResetOnLoad){
                        f .originalValue = f.getValue();
                    }
                }
            }
        }else{
            var field, id;
            for(id in values){
                if(!Ext.isFunction(values[id]) && (field = this.findField(id))){
                	if(field.xtype=='combo'&&field.mode=='remote'){
                		var temp=Ext.getCmp(field.id);
                		var value=values[id];
                		temp.getStore().selectV=value;
                		temp.getStore().on('load', function(comp) {
                			temp.expand();
						    temp.setValue(comp.selectV);
						});
						temp.getStore().load();
                	}else{
                    	field.setValue(values[id]);
                	}
                    if(this.trackResetOnLoad){
                        field.originalValue = field.getValue();
                    }
                }
            }
        }
        return this;
    }
});


dm.privateForm.FormPanel=Ext.extend(Ext.FormPanel,{
			createForm : function(){
		        var config = Ext.applyIf({listeners: {}}, this.initialConfig);
		        
		        var basic=new dm.privateForm.BasicForm(null, config);
		        return basic;
		    }
});

Ext.reg('privateFromPanel',dm.privateForm.FormPanel);