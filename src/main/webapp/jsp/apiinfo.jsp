<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>The Alfresco Public API</title>
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Expires" content="-1">
<style>
section {
    padding: 10px;
}
</style>
</head>
<body>
<a href="/alfapi/">
<img src="http://www.alfresco.com/sites/www/themes/alfrescodotcom/img/logo.svg" alt="Alfresco" width="241px">
</a>
<h1>Some basic information retrieved using Alfresco Public API</h1>
<section>
Person is: ${api_con.person.firstName} ${api_con.person.lastName} <br/>
Email: ${api_con.person.email}<br/>
Avatar id is ${api_con.person.avatarId}<br/>
</section>
<section>
CMIS root folder is ${api_con.cmisSession.rootFolder.path}
</section>
<section>
Network is ${api_con.network.id}
</section>
<section>
	<a href="/alfapi/dosomething">Do something great!</a>
</section>

</body>
</html>