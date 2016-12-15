<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page import="com.hlkj.util.Signature"%>
<%@page import="com.hlkj.util.Common"%>
<%
     //微信分享
	Common ls = new Common();
	long times = new Date().getTime();
	String timeStr = times + "";
	timeStr = timeStr.substring(0, timeStr.length() - 3);
	String nonceStr = ls.randomStr();
	String url = "";
 
		url = "http://ah252.10006.info/yldd/index.htm";    
	 
	//
	String from=request.getParameter("from");
	String isappinstalled=request.getParameter("isappinstalled");
    if(null != from && null != isappinstalled){
 		url = url+"?from="+from+"&isappinstalled="+isappinstalled;
 	}

	String signature = ls.getSignature(nonceStr, timeStr, url);
	System.out.print("url-----"+url); 
	
	String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta charset="utf-8">
<meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<meta name="format-detection" content="telephone=no">
<title>合肥滴滴三周年 给司机好评获免费流量等超值好礼</title>
<link href="css/index.css" rel="stylesheet" type="text/css">
<link href="css/tc_detail.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="js/jquery.js"></script>
<!-- <script type="text/javascript" src="js/index.js"></script>-->
<script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
<script type="text/javascript">
    	wx.config({
	    debug: false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
	    appId: 'wx140002bd54e78ef9', // 必填，公众号的唯一标识
	    timestamp: '<%=timeStr%>', // 必填，生成签名的时间戳
	    nonceStr: '<%=nonceStr%>', // 必填，生成签名的随机串
	    signature: '<%=signature%>',// 必填，签名，见附录1
	    jsApiList: [
	        'onMenuShareTimeline',
	        'onMenuShareAppMessage'
	    ] // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
	});
	var linkUrl="http://ah252.10006.info/yldd/index.htm";  
	var imgpac="<%=basePath%>" + "images/fx_img.png"; 
	wx.ready(function(){
	 // 2.1 监听“分享到朋友圈”按钮点击、自定义分享内容及分享结果接口
		wx.onMenuShareTimeline({ 
		      title: '合肥滴滴三周年 感恩送好礼',
		      desc: '合肥滴滴三周年 给司机好评获免费流量等超值好礼',
		      link: linkUrl, 
		      imgUrl: imgpac, 
		      success: function (res) {
		        $(".wx_fx_bg").hide();	
		      },
		      fail: function (res) {
		       // alert(JSON.stringify(res));
		      }
		  });
		  
	
		  
		 // 2.1 监听“分享给朋友”，按钮点击、自定义分享内容及分享结果接口
		    wx.onMenuShareAppMessage({
	    	  title: '合肥滴滴三周年 感恩送好礼',
		      desc: '合肥滴滴三周年 给司机好评获免费流量等超值好礼', 
		      link: linkUrl,
		      imgUrl: imgpac,
		      success: function (res) {
		         $(".wx_fx_bg").hide();	
		      },
		      fail: function (res) {
		       // alert(JSON.stringify(res));
		      }
		    });
		    
	
		    //分享QQ好友
	    wx.onMenuShareQQ({
             title : '合肥滴滴三周年 感恩送好礼', // 分享标题
             desc : '合肥滴滴三周年 给司机好评获免费流量等超值好礼',//分享描述
             link : linkUrl, // 分享链接
             imgUrl: imgpac,// 分享图标
             success: function () { 
		       // 用户确认分享后执行的回调函数
		     },
		     cancel: function () { 
		       // 用户取消分享后执行的回调函数
		     }
        });
		  
        //分享到QQ空间
        wx.onMenuShareQZone({
            title : '合肥滴滴三周年 感恩送好礼', // 分享标题
             desc : '合肥滴滴三周年 给司机好评获免费流量等超值好礼',//分享描述
             link : linkUrl, // 分享链接
             imgUrl: imgpac,// 分享图标 
             success: function () { 
		       // 用户确认分享后执行的回调函数
		    },
		    cancel: function () { 
		       // 用户取消分享后执行的回调函数
		    }
        });
	});

window.shareData = {
        "imgUrl": imgpac,  
        "timeLineLink": "http://ah252.10006.info/yldd/index.htm" ,
        "sendFriendLink": "http://ah252.10006.info/yldd/index.htm", 
        "tTitle": "合肥滴滴三周年 感恩送好礼",
        "tContent": "让爱随行 让每一次付出喝彩",
        "fTitle": "合肥滴滴三周年 感恩送好礼",
        "fContent": "让爱随行 让每一次付出喝彩",
};
</script>
<script type="text/javascript">
window.onload=function (){
     //  var isp = "${isp}";
     //  console.debug(isp);
      // if(isp.indexOf("电信")!=-1){
        /* $.ajax({
					url:"http://ah231.10006.info/tool/queryPhoneJsonP.do", 
					type:"get",
					dataType:'jsonp',   
					async:false,
					data:{},
					jsonp:'cbFn',
				    jsonpCallback:'staPostCb',
					success:function (data,status) {
					     var ph=0;
						 if(data.result!='用户手机号获取失败'){
						   $("#ph").val(data.result);
						   ph=data.result;
						 }
                         
					}
				});*/
		click(ph);
        allCounts();		
      // }
	   
      
}
function click(flag){
 var param = "ph="+flag
	   $.ajax( {
		type : "post",
		url : "clicks",
		data : param,
		async : true,
		success : function(result) {
		}
	})

}

function allCounts(){
   $.ajax( {
		type : "post",
		url : "getAllCounts",
		 data : {},
		async : true,
		success : function(result) {
		  $("#span").html(result);
		  }
		})
}
function sendsms(t){
    var ph=$("#ph").val();
        if(ph.length!=11){
		    $("#ts").html("请输入正确的手机号码");
		    return;
		  }
        param = "phone=" + $("#ph").val();
        $("#btn").attr("disabled","true");
		for(i=1;i<=t;i++) {
			window.setTimeout("update_p(" + i + ","+t+")", i * 1000);
		}
		$.ajax( {
			type : "post",
			url : "send",
			data : param,
			async : true,
			success : function(result) {
				 $("#ts").html(result);
			}
		})
       
}
function update_p(num,t) {
	if(num == t) {
		$("#btn").val(" 获取 ");
		$("#btn").removeAttr('disabled');
	}
	else {
		printnr = t-num;
		$("#btn").val(" " + printnr +"");
	}
}
function zan(){
  alert("活动已结束，谢谢参与！");
  return;
  $("#zan").attr("disabled","true");
  $("#img").attr("src","images/btn_zan_gray.png");
  var ph=$("#ph").val();
  var code=$("#code").val();
  var sph=$("#sph").val();
  if(ph.length!=11){
    $("#ts").html("请输入正确的手机号码");
    $("#zan").removeAttr("disabled"); 
    $("#img").attr("src","images/btn_zan.png");
    return;
  }
  if(sph.length!=11){
    $("#ts").html("请输入正确的司机手机号码");
    $("#zan").removeAttr("disabled"); 
    $("#img").attr("src","images/btn_zan.png");
    return;
  }
  var param="ph="+ph+"&code="+code+"&sph="+sph;
  $.ajax( {
			type : "post",
			url : "sumbit",
			data : param,
			async : true,
			success : function(result) {
				  if(result=='success'){
				     $(".main_pic").attr("src","images/zan_success.png");
				     $("#p1").html("亲，点赞成功!滴滴出租车司机感谢您，么么哒！<br /><span style=\"color:#f64904\">100M</span>电信省内通用流量正飞奔而来~");
				     $("#p2").html("<a href=\"javascript:void(0)\" onclick='share()'>奔走相告，好事速分享！</a>");
				     $("#p3").html("返回首页");
				     $(".tc_bg").show();
				  }else if(result=='success2'){//非电信用户直接提示领奖
				     $(".main_pic").attr("src","images/zan_success.png");
				     $("#p1").html("感谢您对司机的好评，请关注微信公众号[合小滴]抽取礼品<img src='images/hxd.png'/>");
				     $("#p2").html("<a href=\"javascript:void(0)\" onclick='share()'>奔走相告，好事速分享！</a>");
				     $("#p3").hide();
				     $("#p4").show();
				     $("#p4").html("<a  href='http://ah.189.cn/netup/hd1414.html' >点进惊喜</a>");
				     $(".tc_bg").show();
				  
				  }else if(result.indexOf(",")!=-1){
				         console.debug("等待补券");
				         $(".main_pic").attr("src","images/zan_success.png");
					     $("#p1").html("亲，点赞成功!滴滴出租车司机感谢您，么么哒！<br /><span style=\"color:#f64904\">100M</span>电信省内通用流量正飞奔而来~");
					     $("#p2").html("<a href=\"javascript:void(0)\" onclick='share()'>奔走相告，好事速分享！</a>");
					     $("#p3").html("返回首页");
					     $(".tc_bg").show();
					    var param="ss="+result.split(",")[1]+"&ph="+ph;
					     $.ajax( {
							type : "post",
							url : "gengxin",
							data : param,
							async : true,
							success : function(result) {
								 
							}
						})
				     
				  
				  }
				  else{
				  	 $(".main_pic").attr("src","images/zan_fail.png");
				     $("#p1").html(result+"<img src='images/hxd.png'/>");
				     $("#p2").html("<a  href='http://ah.189.cn/netup/hd1414.html' >没有电信号码怎么办</a>");
				     $("#p3").html("返回首页");
				     $(".tc_bg").show();  
				  }
				   $("#img").attr("src","images/btn_zan.png");
				   $("#zan").removeAttr("disabled"); 
			}
		})

}
$(function(){
                $(".rule").click(function(){
                    $(".more").toggle();
                });
				$(".next").click(function(){
                    $(".container").hide();
					$(".container2").show();
                });
				$(".last").click(function(){
                    $(".container2").hide();
					$(".container").show();
                });
            });
            
  function closediv(){
      $("#p4").hide();
      $("#p3").show();
      $(".tc_bg").hide();
  }
</script>
</head>

<body>
<div class="top">
   <img src="images/indexbg_1.png" />
   <img src="images/indexbg_2.png" />
   <img src="images/indexbg_3.png" />
   <img src="images/indexbg_4.png" />
   <div class="user" style=" background-image:url(images/indexbg_5.png); background-size:100%; padding-top:7%; background-repeat:no-repeat;">
        <p class="box_t_1">
	        		<input class="login_t_1" type="text" id='ph' placeholder="请输入您的手机号码"/>
	    </p>
	    <p class="box_t_2">
	        		<input class="login_t_2" id='code' type="text" placeholder="验证码"/>
	        		<input class="login_t_3"  onClick="sendsms(60)" type="button" id="btn" name="phone" value="获取验证码"/>
	    </p>
	    <span id='ts' style='color:red'></span>
   </div>
   <div class="driver" style=" background-image:url(images/indexbg_6.png); background-size:100%; padding-top:8%;">
        <p class="box_t_3">
					<input class="login_t_4" type="text" id='sph' placeholder="请输入司机手机号为他点赞"/>
	        	</p>
   </div>
   <button id="zan" onclick='zan()'><img id='img' src="images/btn_zan.png" /></button>
   <p class="fenshu">已收到<span id='span'></span>个点赞</p>
</div>
<div class="bottom">
   <img src="images/indexbg_7.png" />
   <a href="javascript:void(0)" class="rule">点我查看活动细则<img src="images/pic_click.png" /></a>
   <p class="more" style="display:none;"><b></b>乘客搭乘滴滴出租车后，扫描车上二维码或通过链接进入活动页面，<br />
<b></b>乘客输入司机手机号码、乘客号码为司机服务点赞，同时行程结束后为司机评价五星，<br />
<b></b>点赞后，合肥电信手机用户可获得100M安徽省内免费流量（每号300M/月封顶），流量自动充值且限当月使用（部分电信号码由于套餐问题未能充值成功，则通过流量券方式2个工作日内短信发送，可转赠使用电信号亲友），还可关注微信公众号［合小滴］抽取更多礼品，
<br/>
<b></b>非电信号码（移动／联通）用户根据页面提示关注微信公众号［合小滴］抽取礼品，<br/>
<b></b>本活动以主办方解释口径为准，服务电话0551-65110045（周一至周五，9:00-18:00）。
</p>
   <a href="list.htm">点赞司机荣誉榜<img src="images/pic_jp.png" /></a>
    <img src="images/indexbg_8.png" />
</div>

<!--点赞成功弹窗-->
<div class="tc_bg" style="display:none;">
     <div class="tc_box">
	      <img class="main_pic"  />
		  <img class="close" src="images/close.png" onclick='closediv()'/> 
		  
		  <div class="tc_container">
		       <p id='p1'>
               
			   </p>
			   <a href="javascript:void(0)" id='p2'>奔走相告，好事速分享！</a>
			   <a href="javascript:void(0)" id ='p3' onclick="closediv()">返回首页</a>
			   <a href="javascript:void(0)" id ='p4' style='display:none'>返回首页</a>
		  </div>
	 </div>
</div>

<!--点赞失败弹窗
<div class="tc_bg" style="display:none;">
     <div class="tc_box">
	      <img class="main_pic" src="images/zan_fail.png" />
		  <img class="close" onclick="closediv()" src="images/close.png" />
		  
		  <div class="tc_container">
		       <p>本活动仅限合肥电信手机用户参与哦~<br />
                  给您支招，点进有惊喜！
			   </p>
			   <a href="javascript:void(0)">没有电信号码怎么办</a>
			   <a href="javascript:void(0)">返回首页</a>
		  </div>
	 </div>
</div>
 -->
 <!--微信分享-->
<div class="wx_fx_bg">
	<img class="fx_pic" src="images/wx.png"/>
</div>
<script>
	 function share(){
	    $(".wx_fx_bg").show();
	 }
		
	 
	$(".fx_pic").click(function(){
		$(".wx_fx_bg").hide();	
	})
</script>
</body>
</html>