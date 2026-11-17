package vn.hungyen.news

import android.app.Activity
import android.os.Bundle
import android.content.Intent
import android.graphics.Color
import android.view.*
import android.webkit.*
import android.widget.*
import android.graphics.Typeface
import android.net.Uri

class MainActivity : Activity() {
 private lateinit var web: WebView
 private lateinit var address: TextView
 private val home = "https://congan.hungyen.gov.vn/"
 private val prefs by lazy { getSharedPreferences("saved", MODE_PRIVATE) }
 override fun onCreate(savedInstanceState: Bundle?) { super.onCreate(savedInstanceState)
  val root = LinearLayout(this).apply { orientation = LinearLayout.VERTICAL; setBackgroundColor(Color.WHITE) }
  val header = LinearLayout(this).apply { orientation = LinearLayout.VERTICAL; setPadding(16,16,16,10); setBackgroundColor(Color.rgb(146,25,35)) }
  header.addView(TextView(this).apply { text="TIN TỨC CÔNG AN HƯNG YÊN"; textSize=19f; setTextColor(Color.WHITE); setTypeface(null,Typeface.BOLD) })
  address=TextView(this).apply { text="Nguồn: congan.hungyen.gov.vn"; setTextColor(Color.WHITE); textSize=12f }
  header.addView(address); root.addView(header)
  val search=EditText(this).apply { hint="Tìm từ khóa trên website"; setSingleLine(true); setPadding(18,4,18,4) }
  val searchRow=LinearLayout(this); searchRow.addView(search,LinearLayout.LayoutParams(0,55,1f))
  val searchButton=Button(this).apply { text="Tìm"; setOnClickListener { val q=Uri.encode(search.text.toString().trim()); if(q.isNotEmpty()) web.loadUrl("https://congan.hungyen.gov.vn/tim-kiem.html?keyword=$q") } }
  searchRow.addView(searchButton); root.addView(searchRow)
  val categories=HorizontalScrollView(this).apply { isHorizontalScrollBarEnabled=false }
  val tabs=LinearLayout(this)
  val links=listOf("Trang chủ" to home,"Tin ANTT" to "https://congan.hungyen.gov.vn/chuyen-muc-tin-antt-trong-tinh-c211-1.html","Cảnh báo" to "https://congan.hungyen.gov.vn/chuyen-muc-canh-bao-toi-pham-c235-1.html")
  links.forEach { (name,url) -> tabs.addView(Button(this).apply { text=name; setOnClickListener { web.loadUrl(url) } }) }
  categories.addView(tabs); root.addView(categories)
  val progress=ProgressBar(this,null,android.R.attr.progressBarStyleHorizontal).apply { max=100 }
  root.addView(progress,LinearLayout.LayoutParams(-1,5))
  web=WebView(this).apply { settings.javaScriptEnabled=true; settings.domStorageEnabled=true; settings.builtInZoomControls=true; settings.displayZoomControls=false; settings.useWideViewPort=true; settings.loadWithOverviewMode=true
   webViewClient=object: WebViewClient() { override fun shouldOverrideUrlLoading(view:WebView?,request:WebResourceRequest?):Boolean { val url=request?.url ?: return false; if(url.scheme!="https" || url.host!="congan.hungyen.gov.vn") { try { startActivity(Intent(Intent.ACTION_VIEW,url)) } catch (_:Exception) {} ; return true }; return false }
    override fun onPageFinished(view:WebView?,url:String?) { address.text="Nguồn: congan.hungyen.gov.vn" } }
   webChromeClient=object: WebChromeClient() { override fun onProgressChanged(view:WebView?,newProgress:Int) { progress.progress=newProgress; progress.visibility=if(newProgress==100) View.GONE else View.VISIBLE } }
  }
  root.addView(web,LinearLayout.LayoutParams(-1,0,1f))
  val actions=LinearLayout(this).apply { gravity=Gravity.CENTER; setBackgroundColor(Color.rgb(245,245,245)) }
  fun action(label:String, callback:()->Unit) { actions.addView(Button(this).apply { text=label; textSize=12f; setOnClickListener { callback() } },LinearLayout.LayoutParams(0,55,1f)) }
  action("Lưu") { val url=web.url ?: home; prefs.edit().putString("bookmark",url).apply(); Toast.makeText(this,"Đã lưu đường dẫn",Toast.LENGTH_SHORT).show() }
  action("Đã lưu") { web.loadUrl(prefs.getString("bookmark",home) ?: home) }
  action("Làm mới") { web.reload() }
  action("Chia sẻ") { val i=Intent(Intent.ACTION_SEND).apply { type="text/plain"; putExtra(Intent.EXTRA_TEXT,web.url ?: home) }; startActivity(Intent.createChooser(i,"Chia sẻ bài viết")) }
  root.addView(actions); setContentView(root); web.loadUrl(home)
 }
 @Deprecated("Deprecated in Java") override fun onBackPressed() { if(web.canGoBack()) web.goBack() else super.onBackPressed() }
 override fun onDestroy() { web.destroy(); super.onDestroy() }
}
