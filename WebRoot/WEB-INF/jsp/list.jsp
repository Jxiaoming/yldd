<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0,user-scalable=no" />
<meta http-equiv="Cache-Control" content="max-age=0" />
<meta name="apple-touch-fullscreen" content="yes" />
<meta name="apple-mobile-web-app-capable" content="yes" />
<meta name="apple-mobile-web-app-status-bar-style" content="black" />
<meta name="format-detection" content="telephone=no">
<title>排行榜</title>
<link href="css/list.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript" src="js/index.js"></script>
<script type="text/javascript">
var pageNo=1;
var totalRecords="";//总记录数
var totalPages ="";//总页数 
var pageSize =10;
window.onload=function(){
    fenye();
}
function fenye(){
   $("#container").html("");
   $("#container").attr("display","block");
    param = "pageNo=" + pageNo+"&pageSize="+pageSize;
   $.ajax( {
			type : "post",
			url : "queryList",
			data : param,
			async : true,
			success : function(result) {
			 result = eval("("+result+")");
			 totalRecords=result.map.counts;
			 var a =parseInt(totalRecords)+parseInt(pageSize);
			 a=a-1;
			 totalPages=parseInt(a/pageSize);	 
			 console.debug(totalRecords+","+totalPages);
			 var html="";
			 for(var i=0;i<result.map.list.length;i++){
			     html=html+"<div class=\"pm\"><a class=\"number\">"+result.map.list[i].rn+"</a><a>"+result.map.list[i].sphone+"</a><a>"+result.map.list[i].ct+"</a></div>";
			 }
			 $("#container").html(html); 
			}
		})
}
function checkPage(flag)
	{
		//上一页
		if(flag=="previous")
		{
			if(pageNo<=1){
				pageNo=1;
				 $(".info_tc_box").html("<p>已是第一页</p>");
			     $(".info_tc_box").show(300).delay(2500).hide(300);
				//alert("已是第一页");
				return;
			}
			pageNo=pageNo-1;
		}
		//下一页
		if(flag=="next")
		{
		   if(pageNo>=totalPages)
			   {
			     pageNo=totalPages==0?1:totalPages;
			     $(".info_tc_box").html("<p>已是最后一页</p>");
			     $(".info_tc_box").show(300).delay(2500).hide(300);
			    // alert("已是最后一页");
			     return;
			   }
		   pageNo=pageNo+1;
		}
		 
		fenye();
	}
</script>
</head>

<body>
<div class="list">
     <img src="images/bg_1.jpg" />
	 <div class="form">
	      <div class="nav">
		       <a class="mc">名次</a>
			   <a>上榜手机号码</a>
			   <a>已收到的赞</a>		  
		  </div>
		  <div class="container" id="container">
 
		  </div>
		  <div class="nav">
		      <div class="page">
		       <a href="javascript:void(0)" class="last" onclick='checkPage("previous")'>上一页</a>
			   <a href="javascript:void(0)" class="next" onclick='checkPage("next")'>下一页</a>
			  </div>
		  </div>
	 </div>
	 <img src="images/bg_2.jpg" />
	 <img src="images/bg_3.jpg" />
	 <img src="images/bg_4.jpg" />
	 <img src="images/bg_5.jpg" />
	 <img src="images/bg_6.jpg" />


</div>
<div class="erwm">
     <span><b></b>好司机精美礼品：活动期间，被乘客好评点赞前100名司机（10个点赞起可参与评选），可获赠50-1000元实物礼品（千元天翼4G智能手机、家庭无线路由器、翼支付购物卡等），活动结束后关注滴滴播报通知领奖时间、地点，服务电话0551-65110045（周一至周五，9:00-18:00）</span>
</div>
<div class="info_tc_box">
	<p></p>
</div>
</body>
</html>