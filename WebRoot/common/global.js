Ext.namespace('LTMS.GLOBAL');

LTMS.GLOBAL.MODULE = [
	{
		id : '11',
		name : '用户管理',
		module : ' dm.userManager.grid',
		jspath : ['systemSet/userManager.js','js/ux/comboxWithOrg.js']
	},{
		id : '13',
		name : '角色管理',
		module : ' dm.roleManager.grid',
		jspath : ['systemSet/roleManager.js','systemSet/functionCodeOther.js']
	},{
		id : '14',
		name : '数据字典',
		module : 'dm.base.EventDictionaryPanel',
		jspath : ['systemSet/dictionaryPanel.js','ext/ux/grid/RowExpander.js']
	},{
		id : '12',
		name : '组织机构管理',
		module : 'dm.org.main',
		jspath : ['systemSet/orgManager.js']
	},{
		id : '15',
		name : '资产分类管理',
		module : 'dm.classificationManager.main',
		jspath : ['systemSet/classificationManager.js','js/ux/comboxWithClass.js']
	},{
		id : '16',
		name : '标段成桩报告',
		module : 'dm.equip.pile.MainPanel',
		jspath : ['business/working/pile_info.js','systemSet/clearDateField.js']
	},{
		id : '211',
		name : '项目报告',
		module : 'dm.slurryProjectAmount.charts.mainPanel',
		jspath : ['business/equip/slurryProjectAmount.js']
	},{
		id : '212',
		name : '标段报告',
		module : 'dm.slurrySectionAmount.charts.mainPanel',
		jspath : ['business/equip/slurrySectionAmount.js']
	},{
		id : '213',
		name : '标段日报',
		module : 'dm.slurrySectionPerday.charts.mainPanel ',
		jspath : ['business/equip/slurrySectionPerday.js','business/working/pile_info.js','systemSet/clearDateField.js']
	},{
		id : '221',
		name : '单桩评分标准',
		module : 'dm.evaluation.main.mainPanel',
		jspath : ['business/evaluation/evaluation.js']
	},{
		id : '252',
		name : '单桩分析',
		module : 'dm.equip.pile_perdeep.MainPanel',
		jspath : ["business/working/pile_perdeep_info.js",'systemSet/clearDateField.js']
	},{
		id : '253',
		name : '不合格成桩列表',
		module : 'dm.equip.pilebroken.MainPanel',
		jspath : ["business/working/pile_broken_info.js",'systemSet/clearDateField.js']
	},{
		id : '254',
		name : '不合格单桩分析',
		module : 'dm.equip.pile_perdeep.MainPanel',
		jspath : ["business/working/pile_perdeep_info.js",'systemSet/clearDateField.js']
	},{
		id : '28',
		name : '设置项目',
		module : 'dm.project.GridPanel',
		jspath : ["business/project/projectService.js"]
	},{
		id : '29',
		name : '标段信息',
		module : 'dm.section.GridPanel',
		jspath : ["business/project/sectionService.js"]
	},{
		id : '311',
		name : '搅拌站设备一览',
		module : 'dm.mixEquip.GridPanel',
		jspath : ["business/equip/mixEquipService.js"]
	},{
		id : '312',
		name : '打桩机设备一览',
		module : 'dm.pileEquip.GridPanel',
		jspath : ["business/equip/pileEquipService.js"]
	},{
		id : '313',
		name : '设备监控',
		module : 'dm.moniter.pile.mainPanel',
		jspath : ["business/moniter/pile.js"]
	},{
		id : '271',
		name : '水泥桩使用报告',
		module : 'dm.moniter.pileReport.mainPanel',
		jspath : ["business/moniter/pileReport.js"]
	}
	
];