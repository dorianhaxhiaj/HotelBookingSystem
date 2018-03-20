<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<t:page pageTitle="Hotel Service Management" isManageHotelsActive="true">
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
			
			<div class="col s12 m12 l12 first-container">
					<div class="card">
            			<div class="card-content">
            				<a href="<c:url value="/admin/manageHotels" />" class="waves-effect waves-light flat-btn"><i class="material-icons left">arrow_back</i>Back</a>
							<c:url var="url" value="/admin/saveHotelService" />
							<form:form action="${url}"  method="post"  modelAttribute="hotelServiceForm">
						
								
					        	<form:hidden path="hotelServiceId" />
					        	
					        	<div class="row">
					        		<div class="input-field col s12">
					        			<form:input path="name" />
							        	<label for="name">Hotel Service Name</label>
							    		<form:errors path="name" cssClass="input-error" />
					        		</div>
					        		<div class="input-field col s12">
					        			<input type="submit" class="btn" value="Save" />
					        		</div>
					        	</div>
					        	
							</form:form>
					</div>
				</div>
			</div>
			<br />
       
			<h2>Hotel Service List</h2>
		
			<table class="striped centered responsive-table">
				<thead>
					<tr>
						<th>Service ID</th>
						<th>Service Name</th>
						<th>Actions</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${hotelServiceList}" var="hotelService">
					
						<tr>
							<td><c:out value="${hotelService.hotelServiceId}" /></td>
							<td><c:out value="${hotelService.name}" /></td>
							
						    <td>
						    	<a href="<c:url value='/admin/editHotelService/${hotelService.hotelServiceId}' />" >Edit</a> &nbsp; &nbsp; | &nbsp; &nbsp;
						    	<form method="post" action="<c:url value='/admin/removeHotelService' />" style="display: inline;">
						    		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
						    		<input type="hidden" name="hotelServiceId" value="${hotelService.hotelServiceId}" />
						    		<input type="submit" value="Delete" class="link-btn">
						    	</form>
					    	</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>

		</div>
	</jsp:body>
</t:page>