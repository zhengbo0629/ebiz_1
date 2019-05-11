<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; GBK" />
<title></title>
<link rel="stylesheet" href="styles.css?v=<%= System.currentTimeMillis()%>" type="text/css" charset="GBK"/>
</head>

<body>
	<div id="wrapper">
		<div id="sitename">
		
			<div style="height: 53px; margin: 0 auto;">
			
				<div style="float: left; padding-top: 0px;">
				<h1><a href="index.jsp">EastEbiz</a></h1>
				</div>
				<div style="float: right; padding-top: 12px;">
				<div style=" font-size:16px;">
					<a href="/Member/MyIndex.aspx" style="">gonewlife(Micro)</a>,<a
						href="/UserLogin.aspx" style="">退出</a> | <a href="/UserReg.aspx">用户注册</a>
					| <a href="/GetRandomAddress.aspx">账户设置</a>
					</div>
					<div style="font-size:14px;color:#666666;padding-top: 3px;">
					
						Next Payment Day:
						
					</div>
				</div>
			</div>




		</div>
		
		<div class="clear"></div>
		<div id="nav">
			<ul class="clear">
			
				<!-- MENU -->
				<li><a href="index.html"><span>Home</span></a></li>
				<li class="current"><a href="examples.html"><span>Examples</span></a></li>
				<li><a href="#"><span>Widgets</span></a></li>
				<li><a href="#"><span>Portfolio</span></a></li>
				<li><a href="#"><span>Solutions</span></a></li>
				<li><a href="#"><span>Contact</span></a></li>
				<!-- END MENU -->
				
			</ul>
		</div>
		
		
		
		
		<div id="body" class="clear">
			<div id="sidebar" class="column-right">
				<ul>
					<li>
						<h4>Links</h4>
						<ul>
							<li><a href="http://www.spyka.net"
								title="spyka Webmaster resources">spyka webmaster</a></li>

							<li><a href="http://www.justfreetemplates.com"
								title="free web templates">Free web templates</a></li>
							<li><a href="http://www.spyka.net/forums"
								title="webmaster forums">Webmaster forums</a></li>
							<li><a href="http://www.awesomestyles.com/mybb-themes"
								title="mybb themes">MyBB themes</a></li>
							<li><a href="http://www.awesomestyles.com"
								title="free phpbb3 themes">phpBB3 styles</a></li>

						</ul>
					</li>


				</ul>
			</div>
			<div id="content" class="column-left">
				<h3>Table</h3>
				<table>
					<tr>
						<th>ID</th>
						<th>Name</th>
						<th>Age</th>
					</tr>
					<tr>
						<td>1</td>
						<td>John Smith</td>
						<td>28</td>
					</tr>
					<tr>
						<td>2</td>
						<td>Fred James</td>
						<td>49</td>
					</tr>
					<tr>
						<td>3</td>
						<td>Rachel Johnson</td>
						<td>19</td>
					</tr>

				</table>

			</div>
		</div>

	</div>
</body>
</html>
