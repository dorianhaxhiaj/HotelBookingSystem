<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<t:page pageTitle="Edit Profile" isEditProfileActive="true">
	<jsp:attribute name="scripts">
		<script>
			$(document).ready(function() {
			    Materialize.updateTextFields();
		  	});
			
			$('.datepicker').pickadate({
			    selectMonths: true,
			    selectYears: 100,
			    max: new Date(),
			    format: "dd/mm/yyyy"
		  	});
			
			<fmt:formatDate value="${profileForm.birthday}" pattern="dd/MM/yyyy" var="birthdayDate" />
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

			<div class="col s12 m12 l12 first-container">
					<div class="card">
            			<div class="card-content">
				 
						<c:url var="url" value="/editProfile" />
				   		
				   		<form:form action="${url}"  method="post" modelAttribute="profileForm">
						
							<c:if test="${not empty successMessage}">
								<div class="blue-text">${successMessage}</div>
							</c:if>
			
							<h3>Edit Profile</h3>
							
							<div class="row">
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
									<input type="submit" class="btn" value="Save"/> 
								</div>
							</div>
						</form:form>
					</div>
				</div>
			</div>
		</div>
	</jsp:body>
</t:page>