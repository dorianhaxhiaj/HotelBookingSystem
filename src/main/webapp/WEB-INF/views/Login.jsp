<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<t:page pageTitle="Login" isLoginActive="true">
	<jsp:attribute name="scripts">
		<script>
			$(document).ready(function() {
			    Materialize.updateTextFields();
		  	});
		</script>
	</jsp:attribute>
	<jsp:body>
		<div class="container">
		
			<c:choose>
				<c:when test="${not empty param.redirect}">
					<div class="col s12 m12 l12 first-container">
						<div class="card">
		           			<div class="card-content">
								<div class="alertMessage">${param.redirect}</div>
							</div>
						</div>
					</div>
				</c:when>
		        <c:when test="${not empty param.error}">
					<div class="col s12 m12 l12 first-container">
						<div class="card">
		           			<div class="card-content">
								<div class="errorMessage">${param.error}</div>
							</div>
						</div>
					</div>
		        </c:when>
		        <c:when test="${not empty param.logout}">
		        	<div class="col s12 m12 l12 first-container">
						<div class="card">
		           			<div class="card-content">
								<div class="successMessage">${param.logout}</div>
							</div>
						</div>
					</div>
		        </c:when>
	       </c:choose>
	       
			<div class="row" style="margin-top: 16px;">
				<div class="col s12 m12 l12">
					<div class="card">
	            		<div class="card-content">
	            		
							<form action="<c:url value="/login" />" method="post">
								<div class="row">
							        
									<div class="row">
										<div class="input-field col s6">
											<input id="username" name="username" type="text" class="validate">
											<label for="username">Username</label>
										</div>
										<div class="input-field col s6">
											<input id="password" name="password" type="password" class="validate">
											<label for="password">Password</label>
										</div>
									</div>
							        
						            <button type="submit" class="btn">Login</button>
							        
									<p style="margin-top: 16px"><a href="<c:url value="/register" />">You do not have an account yet? Click here to register.</a></p>
							        
									<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
										
								</div>
							</form>
							
						</div>
					</div>
				</div>
			</div>
			
		</div>
	</jsp:body>
</t:page>
