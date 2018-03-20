<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<t:page pageTitle="Manage Users" isManageUsersActive="true">
	<jsp:attribute name="scripts">
		<script>
			$(document).ready(function() {
			    Materialize.updateTextFields();
		  	});
			
			$('.datepicker').pickadate({
			    selectMonths: true,
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
			
			<div class="col s12 m12 l12 first-container">
				<div class="card">
            		<div class="card-content">
            		
						<c:url var="url" value="/admin/saveAdminUser" />
						<form:form action="${url}"  method="post"  modelAttribute="userForm">
						
						    
							<h4>Admin User Form</h4>
							
							<br />
							
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
									<form:errors path="" cssClass="input-error" />
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
									<input type="submit" class="btn" value="Register"/> 
								</div>
							</div>
						</form:form>
					</div>
				</div>
			</div>
						
			<br />
			
			<c:choose>
				<c:when test="${not empty userPage.userList}">
					
					<h3>User List</h3>
					<table class="striped centered responsive-table">
						<thead>
							<tr>
								<th>Username</th>
								<th>Authority</th>
								<th>Name</th>
								<th>Surname</th>
								<th>Birthday</th>
								<th>Email</th>
								<th>Actions</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${userPage.userList}" var="user">
								<tr>
									<td><c:out value="${user.username}" /></td>
									<td><c:out value="${user.authority.authority}" /></td>
									<td><c:out value="${user.userProfile.forename}" /></td>
									<td><c:out value="${user.userProfile.surname}" /></td>
									<td>
										<fmt:formatDate value="${user.userProfile.birthday}" pattern="dd/MM/yyyy" var="birthdayDate" />
										<c:out value="${birthdayDate}" />
									</td>
									<td><c:out value="${user.userProfile.email}" /></td>
									<td>
										<form method="post" action="<c:url value='/admin/removeUser' />" style="display: inline;">
								    		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
								    		<input type="hidden" name="username" value="${user.username}" />
								    		<input type="submit" value="Delete" class="link-btn">
								    	</form>
					            	</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
					
					<div class="col s12">
						<ul class="pagination">
						
							<c:choose>
								<c:when test="${userPage.currentPage le 1}">
									<li class="disabled"><a><i class="material-icons">chevron_left</i></a></li>
								</c:when>
								<c:otherwise>
									<li class="waves-effect"><a href="<c:url value="/admin/manageUsers/Page${userPage.startPage - 1}" />"><i class="material-icons">chevron_left</i></a></li>
								</c:otherwise>
							</c:choose>
					
							<c:forEach begin="${userPage.startPage}" end="${userPage.endPage}" varStatus="loop">
								<c:choose>
									<c:when test="${userPage.currentPage eq loop.index}">
										<li class="active"><a>${loop.index}</a></li>
									</c:when>
									<c:otherwise>
										<li class="waves-effect"><a href="<c:url value="/admin/manageUsers/Page${loop.index}" />">${loop.index}</a></li>
									</c:otherwise>
								</c:choose>
							</c:forEach>
							<c:choose>
								<c:when test="${userPage.endPage eq userPage.lastPage}">
									<li class="disabled"><a><i class="material-icons">chevron_right</i></a></li>
								</c:when>
								<c:otherwise>
									<li class="waves-effect"><a href="/admin/manageUsers/Page${userPage.endPage + 1}"><i class="material-icons">chevron_right</i></a></li>
								</c:otherwise>
							</c:choose>
						</ul>
					</div>
				</c:when>
				<c:otherwise>
					<div class="col s12 m12 l12 first-container">
						<div class="card">
	            			<div class="card-content">
								<h5 style="color: orange;">Currently there are no users in the system.</h5>
							</div>
						</div>
					</div>
				</c:otherwise>
			</c:choose>
		
		</div>
	</jsp:body>
</t:page>