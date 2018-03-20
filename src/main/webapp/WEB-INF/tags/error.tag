<%@ tag description="Error Page template" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ attribute name="pageTitle" required="true" %>
<%@ attribute name="imageFile" required="true" %>
<%@ attribute name="error" required="true" %>


<!DOCTYPE html>
<html lang="en">
<head>
	<title>${pageTitle}</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<meta name="viewport" content="width=device-width, initial-scale=1.0" />
	<link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet" />
	<link type="text/css" rel="stylesheet" href="<c:url value="/resources/css/materialize.min.css" />"  media="screen,projection" />
	<link type="text/css" rel="stylesheet" href="<c:url value="/resources/css/style.css" />" />
</head>
<body>
	<div class="container" style="width: inherit!important;">
		<div class="col s12 m12 l12 first-container exception-error-card">
			<div class="card">
				 <div class="card-image">
					<img src="<c:url value="/resources/images/errors/${imageFile}" />">
					<span class="card-title">${pageTitle}</span>
				</div>
	    		<div class="card-content">
	    			<p>${error}</p>
	    		</div>
	    		<div class="card-action">
	              <a href="<c:url value="/" />">Take Me To Home Page</a>
	            </div>
	   		</div>
		</div>
	</div>
	<script type="text/javascript" src="<c:url value="/resources/js/jquery-2.1.1.min.js" />"></script>
	<script type="text/javascript" src="<c:url value="/resources/js/materialize.min.js" />"></script>
</body>
</html>