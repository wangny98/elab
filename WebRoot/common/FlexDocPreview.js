Ext.namespace("DM.doc.flexPreview");

/**
 * 文档预览窗口
 * 
 * @param {}
 *            docId 文档主键
 * @param {}
 *            docName 文档名称
 */
DM.doc.flexPreview.previewWindow = function(id, name, record, grid) {
	var previewWindow = this;
	previewWindow.currentIndex = grid.getStore().indexOf(record);
	// 上一次预览的文档主键
	previewWindow.lastDocId = null;
	DM.doc.flexPreview.previewWindow.superclass.constructor.call(this, {
		closeAction : 'close',
		resizable : false,
		height:500,
		width:720,
		plain : false,
		layout : 'fit',
		//autoWidth : true,
		id : 'flexPreview.previewWindow',
		//autoHeight : true,
		autoScroll : true,
		// draggable : false,
		// maximizable : true,
		modal : true,
		title : record.name,
		listeners : {
			
			// 移动元素时，重新加载数据
			'statesave' : function(win, state) {
				var current = grid.getStore().getAt(previewWindow.currentIndex);
				if (current.data.baseType == 'link') {
					previewWindow.loadData(current.get("fkId"), current
									.get(name));
				} else {
					previewWindow.loadData(current.get(id), current.get(name));
				}
			}
		},
		html : '<div id="maxViewerPlaceHolder" style="display:none">',
		items : [{
			xtype : 'panel',
			buttonAlign : "center",
			id : 'viewerPlaceHolder',
			width : 720,
			height : 500/*,
			buttons : [{
				text : '上一个',
				iconCls : 'icon-lastItem',
				handler : function() {
					previewWindow.currentIndex = previewWindow.getLastIndex(
							grid, grid.getStore()
									.getAt(previewWindow.currentIndex));
					var currentRecord = grid.getStore()
							.getAt(previewWindow.currentIndex);
					previewWindow.setTitle(currentRecord.get(name));
					if (currentRecord.data.baseType == 'link') {
						previewWindow.loadData(currentRecord.get("fkId"),
								currentRecord.get(name));
					} else {
						previewWindow.loadData(currentRecord.get(id),
								currentRecord.get(name));
					}
					grid.getSelectionModel().selectRecords(([currentRecord]));
					grid.getView().refresh();
				}
			}, {
				text : '下一个',
				iconCls : 'icon-nextItem',
				handler : function() {
					previewWindow.currentIndex = previewWindow.getNextIndex(
							grid, grid.getStore()
									.getAt(previewWindow.currentIndex));
					var currentRecord = grid.getStore()
							.getAt(previewWindow.currentIndex);
					previewWindow.setTitle(currentRecord.get(name));
					if (currentRecord.data.baseType == 'link') {
						previewWindow.loadData(currentRecord.get("fkId"),
								currentRecord.get(name));
					} else {
						previewWindow.loadData(currentRecord.get(id),
								currentRecord.get(name));
					}
					grid.getSelectionModel().selectRecords(([currentRecord]));
					grid.getView().refresh();
				}
			}]*/
		}]
	});
	// 展示预览窗口
	previewWindow.show();
	// 加载预览内容
	previewWindow.loadData(record.id, record.name);
};
Ext.extend(DM.doc.flexPreview.previewWindow, Ext.Window, {
	getLastIndex : function(grid, record) {
		var store = grid.getStore();
		var index = store.indexOf(record);
		if (index <= 0) {
			return index;
		}
		var current;
		for (var i = index - 1; i >= 0; i--) {
			current = store.getAt(i);
			if (current.data.baseType == 'doc' || current.data.type == 'link'
					|| current.data.baseType == 'link') {
				return i;
			}
		}
		if (i < 0) {
			return index;
		}
	},
	getNextIndex : function(grid, record) {
		var store = grid.getStore();
		var index = store.indexOf(record);
		var current;
		if (index >= store.getCount() - 1) {
			return index;
		}
		for (var i = index + 1; i < store.getCount(); i++) {
			current = store.getAt(i);
			if (current.data.baseType == 'doc' || current.data.type == 'link'
					|| current.data.baseType == 'link') {
				return i;
			}
		}
		if (i >= store.getCount()) {
			return index;
		}
	},
	maxPreviewImg : function(path) {
		$(document).ready(function() {
					$.layer({
								type : 1,
								title : false,
								bgcolor : '',
								offset : ['5px', ''],
								area : ['1280px', '768px'],
								page : {
									dom : '#maxViewerPlaceHolder'// 执行的标签段
								},
								isConfirm : true
							});
				});
		document.getElementById('maxViewerPlaceHolder').innerHTML = "<img src='"
				+ path + "'  width=" + 1280 + " height=" + 768 + "/>";
	},
	loadData : function(docId, docName) {
		var previewWindow = this;
		var viewerPlaceHolder = Ext.getCmp('viewerPlaceHolder');
		if (previewWindow.isContainsDwg(docName)) {
			alert('等待调用dwg预览插件');
			viewerPlaceHolder.body.update('等待调用dwg预览插件');
			return;
		}
		// 删除上次预览的电子文档主键
		if (null != previewWindow.lastDocId) {
			previewWindow.deleteTmpFile(previewWindow.lastDocId);
		}
		previewWindow.lastDocId = docId;
		viewerPlaceHolder.body
				.update("<table><tr><td height='200px'></td></tr></table>"
						+ "<span style='position:relative;margin:40% 30%;'>"
						+ "<img src='style/images/login/loading3.gif'/>"
						+ "资源加载中，稍等...</span>");
		var localhost = window.location.host;
		var url = "http://" + localhost
				+ "/elab/resource/fileManager/docPreview";
		Ext.Ajax.request({
			url : url,
			params : {
				docId : docId,
				loginId : GLOBAL_uid
			},
			disableCaching : true,// 禁止缓存
			method : 'post',
			// 设置超时时间为50分钟
			timeout : 1000 * 60 * 50,
			success : function(resp, options) {
				try {
					var data = Ext.decode(resp.responseText);
					if (data.success) {
						if (data.isImage) {
							var path = data.path.trim();
							viewerPlaceHolder.body
									.update("<table><tr><td><img src='"
											+ path
											+ "'  width=700px height=500px/></td><td valign='top'>"
											+ "<img src='style/images/frame/maxPreview.png' "
											+ "title='最大化预览' "
											+ "style='cursor: pointer;' onclick=\"javascript:Ext.getCmp('flexPreview.previewWindow').maxPreviewImg('"
											+ path + "');\"" + "/></td></tr>");
						} else if (data.isOffice) {
							var path = data.path.trim();
							//console.info("isOffice : ",path);
							viewerPlaceHolder.body
									.update('<div id="viewSwfPanelContainer" style="position:relative;width:720px;height:500px"></div>');
							setTimeout(function() {
										previewWindow.renderFlexPaper(path);
									}, 500);
						} else if (data.isMedia) {
							var flashurl = "http://"
									+ localhost
									+ "/DM/resource/docManage/docPreview?docId="
									+ docId + "&loginId=" + DM.GLOBAL.STAFFID
									+ "&currentTime="
									+ (new Date() + Math.random());;
							flashurl = encodeURI(flashurl);
							var flashvars = {
								file : flashurl,
								provider : 'http',
								'http.dvr' : 'true'
							};
							viewerPlaceHolder.body
									.update('<div id="viewVideoPanelContainer" style="position:relative;width:720px;height:500px"></div>');
							swfobject.embedSWF(
									'component/viewVideoPanel/player.swf',
									'viewVideoPanelContainer', '720px',
									'450px', '9.0.115', 'true', flashvars, {
										allowfullscreen : 'true',
										allowscriptaccess : 'always'
									}, {
										id : 'jwplayer',
										name : 'jwplayer'
									});
						}
					} else {
						viewerPlaceHolder.body
								.update("<table><tr><td height='200px'></td></tr></table>"
										+ "<span style='position:relative;margin:30% 30%;'>"
										+ "<img src='style/images/frame/sorry.gif' style='background:transparent;'/>"
										+ "正在准备可预览资源，稍等...</span>")
					}

				} catch (e) {
					alert('failure');
				}
			}
		});
	},
	renderFlexPaper : function(path) {
		var previewWindow = this;
		var flexPaper = new FlexPaperViewer(
				'component/viewOnline/FlexPaperViewer',
				'viewSwfPanelContainer', {
					config : {
						SwfFile : escape(path),// 要浏览的swf文件
						Scale : 0.6,
						ZoomTransition : 'easeOut',
						ZoomTime : 0.5,
						ZoomInterval : 0.2,
						FitPageOnLoad : true,
						FitWidthOnLoad : true,
						PrintEnabled : true,
						FullScreenAsMaxWindow : false,
						ProgressiveLoading : false,
						MinZoomSize : 0.2,
						MaxZoomSize : 5,
						SearchMatchAll : false,
						InitViewMode : 'Portrait',

						ViewModeToolsVisible : true,
						ZoomToolsVisible : true,
						NavToolsVisible : true,
						CursorToolsVisible : true,
						SearchToolsVisible : true,
						localeChain : 'zh_CN'// 设置地区（语言）,
					}
				});
		flexPaper.onDocumentLoaded = function(totalPages) {
			// alert("pages:"+totalPages);
			// previewWindow.deleteTmpFile(docId);
		}
	},

	/**
	 * 检测文档是否是pdf文件
	 * 
	 * @param {}
	 *            docName
	 * @return {Boolean}
	 */
	isContainsPdf : function(docName) {
		docName = docName.trim().toLowerCase();
		var index = docName.lastIndexOf('.') + 1;
		docName = docName.substring(index);
		if (docName == "pdf") {
			return true;
		} else {
			return false;
		}
	},
	/**
	 * 检测文档是否是dwg文件
	 * 
	 * @param {}
	 *            docName
	 * @return {Boolean}
	 */
	isContainsDwg : function(docName) {
		docName = docName.trim().toLowerCase();
		var index = docName.lastIndexOf('.') + 1;
		docName = docName.substring(index);
		if (docName == "dwg") {
			return true;
		} else {
			return false;
		}
	}
});