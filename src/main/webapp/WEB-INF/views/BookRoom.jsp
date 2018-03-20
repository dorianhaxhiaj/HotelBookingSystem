<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<t:page pageTitle="Databridge Project" isHomeActive="true">
	<jsp:attribute name="scripts">
		<script><!--
			function goBack() {
			    window.history.back();
			}
		//--></script>
	</jsp:attribute>
	<jsp:body>
	
		<div class="container">
			<h3>Available Rooms</h3>
			<c:url value="/" var="backUrl">
				<fmt:formatDate value="${roomReservationForm.checkin}" pattern="dd/MM/yyyy" var="checkinDate" />
				<c:param name="checkin" value="${checkinDate}}" />
				<fmt:formatDate value="${roomReservationForm.checkout}" pattern="dd/MM/yyyy" var="checkoutDate" />
				<c:param name="checkout" value="${checkoutDate}" />
			</c:url>
			<a href="${backUrl}">Go Back</a>
			<ul class="collection">
				<c:forEach items="${roomList}" var="room">
				
					<li class="collection-item avatar">
						<span class="title">Room No. <c:out value="${room.roomNumber}" /></span>
			
						<p>
							<br />
							Type: <c:out value="${room.roomType}" />
							<br />
							Floor Number: <c:out value="${room.roomFloor}" />
							<br />
							<br />
							<c:url var="url" value="/makeReservation" />
							<form:form action="${url}" method="post" modelAttribute="roomReservationForm">
						    	<form:hidden path="checkin" />
			                	<form:hidden path="checkout" />
			                	<form:hidden path="roomId"  value="${room.roomId}" />
			             		<input type="submit" class="btn" value="Book Room" />
			            	</form:form>
		           		</p>
           			</li>
				
				</c:forEach>
			</ul>
		</div>
	</jsp:body>
</t:page>