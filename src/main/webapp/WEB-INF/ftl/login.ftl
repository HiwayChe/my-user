<html>
	<head>
		<meta charset="utf-8"/>
		<script src="https://cdn.bootcss.com/jquery/3.2.1/jquery.min.js"></script>
	</head>
	
	<body>
		<form action="/user/dologin" method="post">
			<label>用户名</label><input type="text" name="id"/>
			<label>密码</label><input type="password" name="password"/>
			<input type="submit" value="登录"/>
		</form>
	</body>
</html>