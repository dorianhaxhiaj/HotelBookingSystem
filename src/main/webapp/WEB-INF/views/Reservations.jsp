<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<t:page pageTitle="Reservations" isReservationsActive="true">
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
			
			<c:choose>
	   			<c:when test="${not empty reservationPage.reservationList}">
		 			<h2>Reservation List</h2>
					<c:forEach items="${reservationPage.reservationList}" var="reservation">
						<ul class="collection">
							<li class="collection-item avatar">
								<span class="title">Hotel: <c:out value="${reservation.room.hotel.hotelName}" /></span>
								
								<p>
									<br />
									Room Number: <c:out value="${reservation.room.roomNumber}" />
									<br />
									<fmt:formatDate value="${reservation.checkin}" pattern="dd/MM/yyyy" var="checkinDate" />
									Check-in Date: <c:out value="${checkinDate}" /> 
									<br />
									<fmt:formatDate value="${reservation.checkout}" pattern="dd/MM/yyyy" var="checkoutDate" />
									Check-out Date: <c:out value="${checkoutDate}" />
								</p>
								<jsp:useBean id="now" class="java.util.Date" />
								<fmt:formatDate value="${now}" pattern="dd/MM/yyyy" var="nowDate" />
								<c:if test="${checkinDate ge nowDate}">
									<form method="post" action="<c:url value="/cancelReservation" />">
										<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
										<input type="hidden" name="reservationId" value="${reservation.reservationId}" />
										<button type="submit" class="secondary-content link-btn"><i class="material-icons">clear</i></button>
									</form>
								</c:if>
							</li>
						</ul>
					</c:forEach>
					<div class="col s12">
						<ul class="pagination">
						
							<c:choose>
								<c:when test="${reservationPage.startPage le 1}">
									<li class="disabled"><a><i class="material-icons">chevron_left</i></a></li>
								</c:when>
								<c:otherwise>
									<li class="waves-effect"><a href="<c:url value="/Reservations/Page${reservationPage.startPage - 1}" />"><i class="material-icons">chevron_left</i></a></li>
								</c:otherwise>
							</c:choose>
					
							<c:forEach begin="${reservationPage.startPage}" end="${reservationPage.endPage}" varStatus="loop">
								<c:choose>
									<c:when test="${reservationPage.currentPage eq loop.index}">
										<li class="active"><a>${loop.index}</a></li>
									</c:when>
									<c:otherwise>
										<li class="waves-effect"><a href="<c:url value="/Reservations/Page${loop.index}" />">${loop.index}</a></li>
									</c:otherwise>
								</c:choose>
							</c:forEach>
							<c:choose>
								<c:when test="${reservationPage.endPage eq reservationPage.lastPage}">
									<li class="disabled"><a><i class="material-icons">chevron_right</i></a></li>
								</c:when>
								<c:otherwise>
									<li class="waves-effect"><a href="/Reservations/Page${reservationPage.endPage + 1}"><i class="material-icons">chevron_right</i></a></li>
								</c:otherwise>
							</c:choose>
						</ul>
					</div>
		 		</c:when>
				<c:otherwise>
					<div class="col s12 m12 l12 first-container">
						<div class="card">
	            			<div class="card-content">
								<h5 style="color: orange;">You Do Not Have Any Reservation</h5>
							</div>
						</div>
					</div>
					
				</c:otherwise>
			</c:choose>
		</div>
	</jsp:body>
</t:page>