function slurry2pileInfo(equipmentCode,dayTime){
	var cmp = parent.mainTab.getComponent("ext-iframe-16");
	if (cmp){
	}else{
		var stopLoadData = true;
		cmp = new dm.equip.pile.MainPanel(stopLoadData);
		cmp.setTitle("成桩列表");
		parent.mainTab.add(cmp);
		parent.mainTab.loadTab(cmp);
	}
	
	var startTime = dayTime+" 00:00:00";
	var endTime   = dayTime+" 23:59:59";
	cmp.gridPanel.searchConditions={
		startTime:startTime,
		endTime:endTime,
		mixStationNumber:equipmentCode
	};
	cmp.gridPanel.loadData();
}


function pile2pilePerDeep(pileNumber){
	var cmp = parent.mainTab.getComponent("ext-iframe-252");
	if (cmp){
		mainTab.remove(cmp);
	}
		
		cmp = new dm.equip.pile_perdeep.MainPanel(pileNumber);
		cmp.setTitle("单桩分析");
		cmp.id = "ext-iframe-252";
		cmp.iconCls = "icon-module_252";
		
		mainTab.add(cmp);
		mainTab.loadTab(cmp);
	
	
}