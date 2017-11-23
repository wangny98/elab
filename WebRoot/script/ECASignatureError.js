var JSCATopDocument=parent.document.getElementById("topFrame");
var JSCAClientDocument=JSCATopDocument.contentWindow.document;

var signatureerr=['处理成功','枚举设备失败','连接设备失败','获取设备信息失败','生成随机数失败','设置明文对称密钥失败','加密初始化失败','单组数据加密失败',
'设备认证失败','创建应用失败','验证用户PIN失败','创建容器失败','生成ECC签名密钥对失败','密码杂凑初始化失败','多组数据密码杂凑失败','结束密码杂凑失败',
'ECC签名失败','ECC验签失败','导入ECC加密密钥对失败','导入数字证书失败','导出数字证书失败','导出公钥失败','删除证书和密钥对失败','导入加密密钥对失败',
'计算杂凑值失败','签名公钥验证失败','获取证书内容失败','从证书中获取签名公钥失败','分配内存空间失败','获取可用设备失败','枚举应用失败',
'打开应用失败','枚举容器失败','打开容器失败','参数错误','没有匹配的介质设备','PIN口令校验失败'];

function getECASignErrorInfo(index){
  	return signatureerr[index];
}