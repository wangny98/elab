/**
 * @update 
 * @version JPK System 2.0 beta
 * @author songyinghao
 * @sourceCode utf-8
 * @license LGPL,All Rights Reserved.
 */
/**
 * @class JpkFrame.plugins.ContainerMask
 */
/**
  为container(如panel,tabpanel等)打造的一款LoadMask插件
  参数含义如下:
  msg:显示的信息
  msgClass:msg的css
  maskClass:mask的css
  bodyMask：遮盖body
  masked:render的时候就开始遮盖
  使用方法如下：
  在container的属性加上
  plugins: [new JpkFrame.plugins.ContainerMask({
 bodyMask:true//optional
 })],
 在handler中
 this.showMask();或
 this.showMask({
 msg:'菜单加载中..',
 msgClass:'menu-loading'
 });
 this.hideMask();
 */
Ext.namespace('JpkFrame.plugins');

JpkFrame.plugins.ContainerMask = function(opt){
    var CSS = Ext.util.CSS, rules = [];
    opt = Ext.apply({
        bodyMask: true,
        msg: '内容加载中',
        msgClass: 'content-loading div'
    }, opt ||{});
    return {
        init: function(c){
            Ext.applyIf(c, {
                showMask: function(showOpt){
                    showOpt = showOpt ||{};
                    var el;
                    if (this.rendered && (el = opt.bodyMask ? c.body : (this[opt.el] || Ext.get(opt.el) || this.getEl ? this.getEl() : null))) {
                        el.mask.call(el, showOpt.msg || opt.msg, showOpt.msgClass || opt.msgClass,showOpt.maskClass || opt.maskClass);
                    }
                },
                hideMask: function(){
                    var el;
                    if (this.rendered && (el = opt.bodyMask ? c.body : (this[opt.el] || Ext.get(opt.el) || this.getEl ? this.getEl() : null))) {
                        el.unmask.call(el);
                    }
                }
            });
            if (opt.masked) {
                c.on('render', c.showMask.createDelegate(c, [null]), c, {
                    delay: 10,
                    single: true
                });
            }
            CSS.getRule('.content-loading div') || rules.push(".content-loading div{font-size:13px;padding:15px 10px 5px 10px;background: #fbfbfb url( '/JingpinkeSystemApp/jslib/images/menu/loading-balls.gif' ) no-repeat 5px 5px;background-position:top;line-height: 16px;}");
            if (!!rules.length) {
                CSS.createStyleSheet(rules.join(''));
            }
        }
    };
};
