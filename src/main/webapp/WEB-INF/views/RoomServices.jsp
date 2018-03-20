<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<t:page pageTitle="Room Reservation Management" isManageHotelsActive="true">
	<jsp:attribute name="scripts">
		<script>
			$(document).ready(function() {
			    Materialize.updateTextFields();
		  	});
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
            			<a href="<c:url value="/admin/manageHotels" />" class="waves-effect waves-light flat-btn"><i class="material-icons left">arrow_back</i>Back</a>
						<c:url var="url" value="/admin/saveRoomService" />
						<form:form action="${url}"  method="post"  modelAttribute="roomServiceForm">
						
							<c:if test="${not empty successMessage}">
								<div class="successMessage">${successMessage}</div>
							</c:if>
							<form:hidden path="roomServiceId" />
							<div class="row">
								<div class="input-field col s12" style="margin-bottom: 16px;">
									<form:input path="name" />
									<label for="name">Room Service Name</label>
									<form:errors path="name" />
								</div>
							</div>
							<input type="submit" class="btn" value="Save" /> 	
			
						</form:form>
					</div>
				</div>
			</div>
			
			<br />
			
			<h2>Room Service List</h2>
		
			<table class="striped centered responsive-table">
				<thead>
					<tr>
						<th>Service ID</th>
						<th>Service Name</th>
						<th>Actions</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${roomServiceList}" var="roomService">
				
						<tr>
							<td><c:out value="${roomService.roomServiceId}" /></td>
							<td><c:out value="${roomService.name}" /></td>
							
						    <td>
					    		<a href="<c:url value='/admin/editRoomService/${roomService.roomServiceId}' />" >Edit</a> &nbsp; &nbsp; | &nbsp; &nbsp;
					    		<form method="post" action="<c:url value='/admin/removeRoomService' />" style="display: inline;">
						    		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
						    		<input type="hidden" name="roomServiceId" value="${roomService.roomServiceId}" />
						    		<input type="submit" value="Delete" class="link-button" />
					    		</form>
					    	</td>
						</tr>
						
					</c:forEach>
				</tbody>
			</table>
		</div>
	</jsp:body>
</t:page>