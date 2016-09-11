<html>
<head>
<title>MyWebBank - Spring MVC</title>
<link rel=stylesheet type="text/css" href="css/myWebBank.css">
</head>
<body>
	</br></br>
<h1 align="center">Welcome to Spring MVC Bank</h1>
	
	<div class="login-page">
	
		<div class="form">
			<form class="register-form" action="welcome.html">
				<input type="text" placeholder="First Name" /> <input type="text"
					placeholder="last Name" /> <input type="text"
					placeholder="email address" />
				<button>create</button>
				<p class="message">
					Already registered? <a href="#">Sign In</a>
				</p>
			</form>
			<form class="login-form" action="welcome.html">
				<input type="text" placeholder="First Name" name="userName" /> <input
					type="text" placeholder="last Name" name="password" />
				<button>login</button>
				<p class="message">
					Not registered? <a href="index.jsp">Create an account</a>
				</p>
			</form>
		</div>
	</div>


</body>
</html>