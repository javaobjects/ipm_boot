/**
 * Created by yanb on 2017/12/6.
 */
;$(function(){
    //<!-- Older IE Message -->
    //'	<!--[if lt IE 9]>'+
    var IeBlock = '<div class="ie-block">'+
        '<h1 class="Ops">抱歉！</h1>'+
    '<p>您正在使用一个过时的Internet Explorer版本，升级到以下任何Web浏览器，以便访问该网站的最大功能。 </p>'+
    '<ul class="browsers">'+
        '<li>'+
        '<a href="http://www.google.cn/intl/zh-CN/chrome/browser/desktop/index.html">'+
        '<img src="img/browsers/chrome.png" alt="">'+
        '<div>Google Chrome</div>'+
    '</a>'+
    '</li>'+
    '<li>'+
    '<a href="http://www.firefox.com.cn/">'+
       '<img src="img/browsers/firefox.png" alt="">'+
        '<div>Mozilla Firefox</div>'+
    '</a>'+
    '</li>'+
    '<li>'+
    '<a href="http://www.oupeng.com/download">'+
        '<img src="img/browsers/opera.png" alt="">'+
        '<div>Opera</div>'+
        '</a>'+
        '</li>'+
        '<li>'+
        '<a href="https://www.apple.com/cn/safari/">'+
        '<img src="img/browsers/safari.png" alt="">'+
        '<div>Safari</div>'+
        '</a>'+
        '</li>'+
        '<li>'+
        '<a href="https://support.microsoft.com/zh-cn/help/17621/internet-explorer-downloads">'+
        '<img src="img/browsers/ie.png" alt="">'+
        '<div>Internet Explorer(New)</div>'+
    '</a>'+
    '</li>'+
    '</ul>'+
    '<p>升级你的浏览器更安全和更快的网络体验。 <br/>感谢你的支持...</p>'+
    '</div>';
    //+
    //    '<![endif]-->'
    $("#main").append($(IeBlock));
});