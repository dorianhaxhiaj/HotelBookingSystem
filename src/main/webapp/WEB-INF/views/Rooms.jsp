<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<t:page pageTitle="Room Management" isManageHotelsActive="true">
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
					<h4>Room Form</h4>
					
					
					<c:url var="url" value="/admin/saveRoom" />
					<form:form action="${url}"  method="post"  modelAttribute="roomForm">
							<form:hidden  path="roomId" />
							<form:hidden path="hotelId" />
							<div class="row">
								<div class="input-field col s12">
									<form:input path="roomNumber" style="margin-bottom: 0px;" />
									<label for="roomNumber">Room Number</label>
					                <form:errors path="roomNumber" cssClass="input-error" />
					                <form:errors path="" cssClass="input-error" />
				                </div>
			                </div>
							<div class="row">
								<div class="input-field col s12">
									<form:input path="roomFloor" style="margin-bottom: 0px;" />
									<label for="roomFloor">Room Floor</label>
					                <form:errors path="roomFloor" cssClass="input-error" />
				                </div>
			                </div>
			                <p>  
								Room Type:
								&nbsp; &nbsp; &nbsp; &nbsp;
								<form:radiobutton id="room_type_single" path="roomType" value="SINGLE" />
								<label for="room_type_single">Single</label>
								&nbsp; &nbsp; &nbsp; &nbsp;
								<form:radiobutton id="room_type_double" path="roomType" value="DOUBLE" />
								<label for="room_type_double">Double</label>
								&nbsp; &nbsp; &nbsp; &nbsp;
								<form:radiobutton id="room_type_triple" path="roomType" value="TRIPLE" />
								<label for="room_type_triple">Triple</label>
								&nbsp; &nbsp; &nbsp; &nbsp;
								<form:radiobutton id="room_type_quadruple" path="roomType" value="QUADRUPLE"/>
								<label for="room_type_quadruple">Quadruple</label>
								&nbsp; &nbsp; &nbsp; &nbsp;
								<form:errors path="roomType" cssClass="input-error" />
							</p>
							<p>
								<br />
								Room Services:
								
								<c:set var="checked" value="${false}" />
								<c:forEach items="${roomServiceList}" var="roomService">
			               			
			               			<c:forEach items="${roomForm.roomServiceIdList}" var="serviceId">
										<c:if test="${serviceId eq roomService.roomServiceId}"><c:set var="checked" value="${true}" /></c:if>
				               		</c:forEach>
				               		
				               		&nbsp; &nbsp; &nbsp; &nbsp;
				               		<input id="${roomService.roomServiceId}" type="checkbox" name="roomServiceIdList"  value="${roomService.roomServiceId}" <c:if test="${checked}">checked</c:if>/>
				               		<label for="${roomService.roomServiceId}"><c:out value="${roomService.name}" /></label>
				               		
				               		<c:set var="checked" value="${false}" />
				               		
			              		</c:forEach>
		              		</p>
		              		<p>
								<a href="<c:url value='/admin/manageRoomServices' />">Manage Room Service</a>
							</p>
							<div class="row">
								<div class="input-field col s12">
									<input class="btn" type="submit" value="Register Room" />
								</div>		
							</div>
						</form:form>
					</div>
				</div>
			</div>	
			<br />
			<h2>Room List</h2>
			<table class="striped centered responsive-table">
				<thead>
					<tr>
						<th>Room ID</th>
						<th>Room Number</th>
						<th>Type</th>
						<th>Floor Number</th>
						<th>Room Services</th>
						<th>Actions</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${roomList}" var="room">
						<tr>
							<td><c:out value="${room.roomId}" /></td>
							<td><c:out value="${room.roomNumber}" /></td>
							<td><c:out value="${room.roomType}" /></td>
							<td><c:out value="${room.roomFloor}" /></td>
							<td>
								<ul>
									<c:forEach items="${room.roomServices}" var="roomService">
										<li><c:out value="${roomService.name}" /></li>
									</c:forEach>
								</ul>
							</td>
						    <td>
						    	<a href="<c:url value='/admin/editRoom/${room.roomId}' />">Edit</a> &nbsp; &nbsp; | &nbsp; &nbsp;
						    	<form method="post" action="<c:url value='/admin/removeRoom' />" style="display: inline;">
						    		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
						    		<input type="hidden" name="roomId" value="${room.roomId}" />
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