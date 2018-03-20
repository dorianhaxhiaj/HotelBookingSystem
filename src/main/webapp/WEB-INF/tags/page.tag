<%@ tag description="Overall Page template" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ attribute name="scripts" fragment="true" %>
<%@ attribute name="pageTitle" required="true" %>
<%@ attribute name="isHomeActive" %>
<%@ attribute name="isReservationsActive" %>
<%@ attribute name="isManageHotelsActive" %>
<%@ attribute name="isManageUsersActive" %>
<%@ attribute name="isManageReservationsActive" %>
<%@ attribute name="isRegisterActive" %>
<%@ attribute name="isEditProfileActive" %>
<%@ attribute name="isLoginActive" %>

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
	<sec:authorize access="hasRole('ROLE_ADMIN')">
		<ul id="administrationDropdown" class="dropdown-content">
			<li<c:if test="${isManageHotelsActive}"> class="active"</c:if>><a href="<c:url value="/admin/manageHotels" />">Manage Hotels</a></li>
		  	<li class="divider"></li>
			<li<c:if test="${isManageUsersActive}"> class="active"</c:if>><a href="<c:url value="/admin/manageUsers" />">Manage Users</a></li>
			<li class="divider"></li>
			<li<c:if test="${isManageReservationsActive}"> class="active"</c:if>><a href="<c:url value="/admin/manageReservations" />">Manage Reservations</a></li>
		</ul>
	</sec:authorize>
	<sec:authorize access="isAuthenticated()">
		<ul id="profileDropdown" class="dropdown-content">
			<li<c:if test="${isEditProfileActive}"> class="active"</c:if>><a href="<c:url value="/editProfile" />">Edit Profile</a></li>
			<li class="divider"></li>
			<li>
				<form action="<c:url value="/logout" />" method="post">
					<input type="submit" value="Logout" class="link-dropdown-btn" />
					<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
				</form>
			</li>
		</ul>
	</sec:authorize>
	<nav class="top-navbar">
		<div class="container">
			<div class="nav-wrapper">
				<a href="<c:url value="/" />" class="brand-logo">DatabridgeProject</a>
				<ul id="nav-mobile" class="right hide-on-med-and-down">
					<li<c:if test="${isHomeActive}"> class="active"</c:if>><a href="<c:url value="/" />">Home</a></li>
					<li<c:if test="${isReservationsActive}"> class="active"</c:if>><a href="<c:url value="/Reservations" />">Reservations</a></li>
					<sec:authorize access="hasRole('ROLE_ADMIN')">
						<li<c:if test="${isManageHotelsActive || isManageUsersActive || isManageReservationsActive}"> class="active"</c:if>><a class="dropdown-button" data-activates="administrationDropdown">Administration<i class="material-icons right">arrow_drop_down</i></a></li>
					</sec:authorize>
					<sec:authorize access="isAuthenticated()">
						<li<c:if test="${isEditProfileActive}"> class="active"</c:if>><a class="dropdown-button" data-activates="profileDropdown"><sec:authentication property="principal.username" /><i class="material-icons left">account_circle</i><i class="material-icons right">arrow_drop_down</i></a></li>
					</sec:authorize>
					<sec:authorize access="isAnonymous()">
						<li<c:if test="${isRegisterActive}"> class="active"</c:if>><a href="<c:url value="/register" />">Register</a></li>
						<li<c:if test="${isLoginActive}"> class="active"</c:if>><a href="<c:url value="/login" />">Login</a></li>
					</sec:authorize>
				</ul>
			</div>
		</div>
	</nav>
	<jsp:doBody />
	<script type="text/javascript" src="<c:url value="/resources/js/jquery-2.1.1.min.js" />"></script>
	<script type="text/javascript" src="<c:url value="/resources/js/materialize.min.js" />"></script>
	<jsp:invoke fragment="scripts" />
</body>
</html>