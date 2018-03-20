<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<t:page pageTitle="Register" isRegisterActive="true">
	<jsp:attribute name="scripts">
		<script>
			$(document).ready(function() {
			    Materialize.updateTextFields();
		  	});
		
			$('.datepicker').pickadate({
			    selectMonths: true,
			    selectYears: 30,
			    max: new Date(),
			    format: "dd/mm/yyyy"
			});
			
			<fmt:formatDate value="${userForm.birthday}" pattern="dd/MM/yyyy" var="birthdayDate" />
			<c:choose>
				<c:when test="${empty birthdayDate}">
					$('#birthday').pickadate().pickadate('picker').clear();
				</c:when>
				<c:otherwise>
					$('#birthday').pickadate().pickadate('picker').set('select', '${birthdayDate}', { format: 'dd/mm/yyyy' });
				</c:otherwise>
			</c:choose>
		</script>
	</jsp:attribute>
	<jsp:body>
		<div class="container">
		
			<c:if test="${not empty successMessage}">
				<div class="col s12 m12 l12 first-container">
					<div class="card">
            			<div class="card-content">
							<div class="successMessage">${successMessage}</div>
						</div>
					</div>
				</div>
			</c:if>
					
			<c:if test="${not empty errorMesssage}">
				<div class="col s12 m12 l12 first-container">
					<div class="card">
            			<div class="card-content">
							<div class="errorMessage">${errorMessage}</div>
						</div>
					</div>
				</div>
			</c:if>
			
			<div class="col s12 m12 l12" style="margin-top: 32px">
				<div class="card">
            		<div class="card-content">
            		
						<c:url var="url" value="/register" />
						<form:form action="${url}"  method="post" modelAttribute="userForm">
			
							<h3>Registration</h3>
							<div class="row">
								<div class="input-field col s12">
									<form:input path="username" cssClass="validate" />
									<form:label path="username">Username</form:label>
									<form:errors path="username" cssClass="input-error" />
								</div>
								<div class="input-field col s12">
									<form:input path="password" type="password" cssClass="validate" />
									<form:label path="password">Password</form:label>
									<form:errors path="password" cssClass="input-error" />
								</div>
								<div class="input-field col s12">
									<form:input path="confirmPassword" type="password" cssClass="validate" />
									<form:label path="confirmPassword">Confirm Password</form:label>
									<form:errors path="confirmPassword" cssClass="input-error" />
								</div>
								<div class="input-field col s12">
									<form:input path="firstName" cssClass="validate" />
									<form:label path="firstName">First Name</form:label>
									<form:errors path="firstName" cssClass="input-error" />
								</div>
								<div class="input-field col s12">
									<form:input path="lastName" cssClass="validate" />
									<form:label path="lastName">Last Name</form:label>
									<form:errors path="lastName" cssClass="input-error" />
								</div>
								<div class="input-field col s12">
									<form:input  id="birthday" path="birthday" type="date" cssClass="datepicker" />
									<form:label path="birthday">Birthday</form:label>
									<form:errors path="birthday" cssClass="input-error" />
								</div>
								<div class="input-field col s12">
									<form:input path="email" type="email" cssClass="validate" />
									<form:label path="email">Email</form:label>
									<form:errors path="email" cssClass="input-error" />
								</div>
								<div class="input-field col s12">
									<input type="submit" class="btn" value="Register" /> 
								</div>
							</div>
						</form:form>
						
					</div>
				</div>
			</div>
		</div>
	</jsp:body>
</t:page>