<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
 

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
 
    
    <title>一路滴滴</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script type="text/javascript" src="js/jquery2.1.js"></script>
   
    <script type="text/javascript">
    window.onload=function(){
       $("#img").show();
       $.ajax( {
			type : "post",
			url : "queryDataList",
			data : {},
			async : true,
			success : function(result) {
				 result=eval("("+result+")");
 
				 var html = "<tr><td>序号</td><td>手机</td><td>司机号码</td><td>点赞时间</td><td>结果</td><td>短信</td></tr>";
				 for(var i=0;i<result.list.length;i++){
				 var ss = result.list[i].result;
				     if(ss=="success"){
				        ss="合肥电信号码";
				      }else if(ss=="success2"){
				         ss="移动/联通";
				      }else{
				         ss="合肥电信号码(补券)";
				      }
				      html=html+"<tr><td>"+(i+1)+"</td>"
				      +"<td>"+result.list[i].phone+"</td>"
				      +"<td>"+result.list[i].sphone+"</td>"
				      +"<td>"+result.list[i].dtime+"</td>"
				      +"<td>"+ss+"</td>"  
				      +"<td>"+result.list[i].msg+"</td>"     
				        
				       +"</tr>"
				 }
				   $("#tt").html(html);
			       $("#img").hide();   
			}
		})
    
    
    }
    function exportExcel(){
      window.open("export");
    }
    </script>
 
  </head>
  
  <body>
  <input title='导出excel' type='button' value = '导出EXCEL' onclick="exportExcel()" />
    <div style='position:fixed;left:50%;top:50%;margin-left:width/2;margin-top:height/2;' ><img id='img' alt="" src="images/wait.gif" style='display:none'></div>
  <table style='width:100%' id='tt'>
   </table>
  </body>
</html>
