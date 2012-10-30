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
Person is: ${user_cache.person.firstName} ${user_cache.person.lastName} <br/>
Email: ${user_cache.person.email}<br/>
</section>
<section>
CMIS root folder is ${user_cache.cmisSession.rootFolder.path}
</section>
<section>
Network is ${user_cache.network.id}
</section>
<section>
	<a href="/alfapi/dosomething">Do something great!</a>
</section>
<section>
	<a href="/alfapi/rapid/dosomething.groovy">Do something groovy</a>
</section>
</body>
</html>