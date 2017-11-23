var login={
	loginData:[{
		login:"xnlogin",name:"校内邮箱",action:"http://mail.nuist.edu.cn/user/?q=login.do",params:{__VIEWSTATE:"dDwtNTQ5MTEyMzE0Ozs+YMNKk8Ar0KOCSdAb4Pjwhh6qP9U=",referer:"/",go:"/?q=base",domain_name:"nuist.edu.cn",user:"_username_",password:"_password_"
		}
	}
	,{
		login:"jx",name:"教学管理系统",action:"http://wlkt.nuist.edu.cn/default.aspx",params:{__VIEWSTATE:"/wEPDwUKMTM5MjUxOTk4Nw9kFgJmD2QWHgICDxAPFgIeB1Zpc2libGVoZGQWAWZkAgMPEA8WAh8AaGRkZGQCBA8QDxYCHwBoZGRkZAIFDxAPFgIeBFRleHQFCeaVmeWKoeWkhGRkZGQCBg8QDxYCHwBoZGRkZAIHDxAPFgIfAQUG5a2m6ZmiZGRkZAIIDxAPFgIfAQUG5a2m5YqeZGRkZAIJDxAPFgIfAGhkZGRkAgoPEA8WAh8BBQbmlZnluIhkZGRkAgsPEA8WAh8BBQblrabnlJ9kZGRkAgwPEA8WAh8AaGRkZGQCDQ8QDxYCHwBoZGRkZAIODxAPFgIfAGhkZGRkAg8PEA8WAh8AaGRkZGQCEA8QDxYCHwBoZGRkZBgBBR5fX0NvbnRyb2xzUmVxdWlyZVBvc3RCYWNrS2V5X18WCQUMUmFkaW9CdXR0b240BQxSYWRpb0J1dHRvbjQFDFJhZGlvQnV0dG9uMgUMUmFkaW9CdXR0b24yBQxSYWRpb0J1dHRvbjUFDFJhZGlvQnV0dG9uNQUMUmFkaW9CdXR0b24xBQxSYWRpb0J1dHRvbjEFDFJhZGlvQnV0dG9uM7IdA1aEZ+eBuhtY/s33y6MkLyZY",TextBox1:"_username_",TextBox2:"_password_",Button1:"登陆"
		}
	}
	,{
		login:"xs",name:"后台管理系统",action:"login.html",params:{__VIEWSTATE:"dDwtNTQ5MTEyMzE0Ozs+YMNKk8Ar0KOCSdAb4Pjwhh6qP9U=",xzbz:"1",userbh:"_username_",pass:"_password_"
		}
	}
	],init:function(){
		var $loginSelect=$('#loginSelect');
		var $loginUserName=$('#loginUserName');
		var $loginPassword=$('#loginPassword');
		var $loginParas=$('#loginParas');
		var md=login.loginData;
		var p=[];
		$loginSelect.change(function(){
			var loginName=$(this).val();
			var m=login.find(loginName,md);
			if(m){
				p=[];
				for(var key in m.params){
					p.push('<input type="hidden" name="'+key+'" value="'+m.params[key].replace(/_username_/,$loginUserName.val()).replace(/_password_/,$loginPassword.val())+'" />')
				};
				$loginParas.empty().html(p.join(''));
				$('#loginForm').attr('action',m.action);
			}
		}).change();
		$('#loginForm').bind('submit',function(){
			return login.check()
		})
	}
	,check:function(){
		var $loginSelect=$('#loginSelect');
		var $loginUserName=$('#loginUserName');
		var $loginPassword=$('#loginPassword');
		if($loginUserName.val()==''||$loginUserName.val()=='用户名'){
			alert('请输入用户名！');
			return false
		}
		else if($loginPassword.val()==''||$loginPassword.val()=='请输入密码'){
			alert('请输入密码');
			return false
		}
		else{
			$loginSelect.change();
			$loginPassword.val('');
			outWin=window.open('','','scrollbars=yes,menubar=yes,toolbar=yes,location=yes,status=yes,resizable=yes');
			doc=outWin.document;
			doc.open('text/html');
			doc.write('<html><head><meta http-equiv="Content-Type" content="text/html; charset=uft-8"><title>正在登录，请稍候...</title></head><body onload="document.tmpForm.submit()">');
			doc.write('<div style="width:100%; height:60px; line-height:60px;font-size:16pt; color: #444; text-indent:1em;">连接中......</div><form name="tmpForm" action="'+$('#loginForm').attr('action')+'" method="post">'+$('#loginParas').html()+'</form></body></html>');
			doc.close();
			return false
		}
	}
	,find:function(login,md){
		for(var i=0;i<md.length;i++){
			if(md[i].login==login)return md[i]
		}
	}
}