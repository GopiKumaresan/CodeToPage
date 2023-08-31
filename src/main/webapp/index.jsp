<html>
<head>
    <title>File Upload and Validation</title>
    <link rel="stylesheet" type="text/css" href="styles.css">
    <script type="text/javascript" src="script.js"></script>
</head>
<body>
<form action = "upload" method = "POST" enctype="multipart/form-data">
	HTML : <input type="file" name="htmlFile" accept =".html" required> <br> <br>
    CSS : <input type="file" name="cssFile"  accept =".css" required> <br> <br>
    JS : <input type="file" name="jsFile"  accept =".js" required> <br> <br>
	<button type = "submit">Upload</button>

</form>


</body>
</html>
