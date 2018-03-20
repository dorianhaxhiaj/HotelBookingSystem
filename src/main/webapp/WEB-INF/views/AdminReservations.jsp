<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<t:page pageTitle="Manage Reservations" isManageReservationsActive="true">
	<jsp:body>
		<div class="container">
				
			<h3>Reservations</h3>
			<table class="striped centered responsive-table">
				<thead>
					<tr>
						<th>Reservation ID</th>
						<th>Hotel ID</th>
						<th>Hotel Name</th>
						<th>Room ID</th>
						<th>Room Number</th>
						<th>Username</th>
						<th>Check-in Date</th>
						<th>Check-out Date</th>
						<th>Actions</th>
					</tr>
				</thead>
				<tbody>
					<c:if test="${empty reservationPage.reservationList}">
						<td colspan="9">There are no reservations currently in the system.</td>
					</c:if>
					<c:forEach items="${reservationPage.reservationList}" var="reservation">
						<tr>
							<td><c:out value="${reservation.reservationId}" /></td>
							<td><c:out value="${reservation.room.hotel.hotelId}" /></td>
							<td><c:out value="${reservation.room.hotel.hotelName}" /></td>
							<td><c:out value="${reservation.room.roomId}" /></td>
							<td><c:out value="${reservation.room.roomNumber}" /></td>
							<td><c:out value="${reservation.user.username}" /></td>
							<td>
								<fmt:formatDate value="${reservation.checkin}" pattern="dd/MM/yyyy" var="checkinDate" />
								<c:out value="${checkinDate}" />
							</td>
							<td>
								<fmt:formatDate value="${reservation.checkout}" pattern="dd/MM/yyyy" var="checkoutDate" />
								<c:out value="${checkoutDate}" />
							</td>
							<td>
								<form method="post" action="<c:url value='/admin/removeReservation' />" style="display: inline;">
						    		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
						    		<input type="hidden" name="reservationId" value="${reservation.reservationId}" />
						    		<input type="submit" value="Delete" class="link-btn">
						    	</form>
			            	</td>
						</tr>
					</c:forEach>
					<c:if test="${not empty reservationPage.reservationList}">
						<div class="col s12">
							<ul class="pagination">
							
								<c:choose>
									<c:when test="${reservationPage.startPage le 1}">
										<li class="disabled"><a><i class="material-icons">chevron_left</i></a></li>
									</c:when>
									<c:otherwise>
										<li class="waves-effect"><a href="<c:url value="/admin/Reservations/Page${reservationPage.startPage - 1}" />"><i class="material-icons">chevron_left</i></a></li>
									</c:otherwise>
								</c:choose>
						
								<c:forEach begin="${reservationPage.startPage}" end="${reservationPage.endPage}" varStatus="loop">
									<c:choose>
										<c:when test="${reservationPage.currentPage eq loop.index}">
											<li class="active"><a>${loop.index}</a></li>
										</c:when>
										<c:otherwise>
											<li class="waves-effect"><a href="<c:url value="/admin/manageReservations/Page${loop.index}" />">${loop.index}</a></li>
										</c:otherwise>
									</c:choose>
								</c:forEach>
								<c:choose>
									<c:when test="${reservationPage.endPage eq reservationPage.lastPage}">
										<li class="disabled"><a><i class="material-icons">chevron_right</i></a></li>
									</c:when>
									<c:otherwise>
										<li class="waves-effect"><a href="/admin/Reservations/Page${reservationPage.endPage + 1}"><i class="material-icons">chevron_right</i></a></li>
									</c:otherwise>
								</c:choose>
							</ul>
						</div>
					</c:if>
				</tbody>
			</table>
		
		</div>
	</jsp:body>
</t:page>