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
       $.ajax( {
			type : "post",
			url : "queryClicks",
			data : {},
			async : true,
			success : function(result) {
				 result=eval("("+result+")");
	
				 var html = "<tr><td>日期</td><td>点击量</td></tr>";
				 for(var i=0;i<result.list.length;i++){
				      html=html+"<tr><td>"+result.list[i].time+"</td>"+"<td>"+result.list[i].ct+"</td></tr>";
				 
				 }
				   $("#tt").html(html);
			          
			}
		})
    
    
    }
    
    </script>
 
  </head>
  
  <body>
   <table style='width:100%' id='tt'>
     
     </table>
  </body>
</html>
