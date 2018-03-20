<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<t:page pageTitle="Databridge Project" isHomeActive="true">
	<jsp:attribute name="scripts">
		<script async>
			$(document).ready(function() {
			    Materialize.updateTextFields();
			    $('#infoModal').modal();
		  	});
			
			
			$('#checkin').pickadate({
			    selectMonths: true,
			    selectYears: 15,
			    min: new Date(),
			    format: "dd/mm/yyyy"
		  	});
		  	
			$('#checkout').pickadate({
			    selectMonths: true,
			    selectYears: 15,
			    min: new Date((new Date()).getTime() + 24 * 60 * 60 * 1000),
			    format: "dd/mm/yyyy"
		  	});
			
			<fmt:formatDate value="${searchForm.checkin}" pattern="dd/MM/yyyy" var="checkinDate" />
			<fmt:formatDate value="${searchForm.checkout}" pattern="dd/MM/yyyy" var="checkoutDate" />
			<c:choose>
				<c:when test="${empty checkinDate}">
					$('#checkin').pickadate().pickadate('picker').clear();
				</c:when>
				<c:otherwise>
					$('#checkin').pickadate().pickadate('picker').set('select', '${checkinDate}', { format: 'dd/mm/yyyy' });
				</c:otherwise>
			</c:choose>
			<c:choose>
				<c:when test="${empty checkoutDate}">
					$('#checkout').pickadate().pickadate('picker').clear();
				</c:when>
				<c:otherwise>
					$('#checkout').pickadate().pickadate('picker').set('select', '${checkoutDate}', { format: 'dd/mm/yyyy' });
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
		
			<div class="row">
				<div id="infoModal" class="modal top-sheet">
					<div class="modal-content">
						<h5>Please Search First</h5>
						<p>Select a Check-in and Check-out Date and press Search, to filter the relevant results only.</p>
					</div>
				</div>
				<div class="col s12 m12 l12 first-container">
					<div class="filter-container">
						<div class="card">
		            		<div class="card-content">
								<c:url var="searchUrl" value="/Page${currentPage}" />
								<form:form action="${searchUrl}"  method="get"  modelAttribute="searchForm">
									<div class="row">
										<div class="row">
											<div class="input-field col s12 m6 l6">
												<form:input  id="checkin" path="checkin" type="date" cssClass="datepicker" />
												<form:label path="checkin">Check-in</form:label>
												<form:errors path="checkin" cssClass="input-error" />
												<form:errors path="" cssClass="input-error" />
											</div>
											<div class="input-field col s12 m6 l6">
												<form:input  id="checkout" path="checkout" type="date" cssClass="datepicker" />
												<form:label path="checkout" data-error="test">Check-out</form:label>
												<form:errors path="checkout" cssClass="input-error" />
											</div>
										</div>
										<input class="waves-effect waves-teal btn" type="submit" value="Search" /> 	
									</div>
								</form:form>
							</div>
						</div>
					</div>
				</div>
			
				<c:choose>        
					<c:when test="${not empty hotelPage.hotelList}">
						
						<div class="col s12 m12 l12">
						 
							<h3>Available Hotels</h3>
							
							<c:set var="counter" value="0" />
							<c:forEach items="${hotelPage.hotelList}" var="hotel">
								<c:if test="${counter % 3 == 0}">
									<div class="row"></div>
								</c:if>
								<c:set var="counter" value="${counter + 1}" />
								<div class="col s12 m4">
									<div class="card">
										<div class="card-content">
											<span class="card-title">${hotel.hotelName}</span>
											<p>Rating: ${hotel.hotelStars}</p>
											<c:if test="${not empty hotel.hotelServices}">
												<br />
												<h6><strong>Hotel Services</strong></h6>
												<ul>
													<c:forEach items="${hotel.hotelServices}" var="hotelService">
												 		<li class="classic-bullet">${hotelService.name}</li>
													</c:forEach>
												</ul>
											</c:if>
										</div>
										<div class="card-action">
											<c:url value="/Hotel${hotel.hotelId}/AvailableRooms" var="viewRoomsUrl">
												<fmt:formatDate value="${searchForm.checkin}" pattern="dd/MM/yyyy" var="checkinDate" />
												<c:param name="checkin" value="${checkinDate}" />
												<fmt:formatDate value="${searchForm.checkout}" pattern="dd/MM/yyyy" var="checkoutDate" />
												<c:param name="checkout" value="${checkoutDate}" />
											</c:url>
											<c:choose>
												<c:when test="${isSearchComplete}">
													<a href="${viewRoomsUrl}" class="blue-text medium">See Available Rooms</a>
												</c:when>
												<c:otherwise>
													<sec:authorize access="isAuthenticated()">
														<a href="#infoModal">See Available Rooms</a>
													</sec:authorize>
													<sec:authorize access="isAnonymous()">
														<a href="<c:url value="/login"><c:param name="redirect" value="You need to login first!" /></c:url>">See Available Rooms</a>
													</sec:authorize>
												</c:otherwise>
											</c:choose>
											
										</div>
									</div>
								</div>
							</c:forEach>
						</div>
						
						<div class="col s12">
							<ul class="pagination">
							
								<c:choose>
									<c:when test="${hotelPage.startPage le 1}">
										<li class="disabled"><a><i class="material-icons">chevron_left</i></a></li>
									</c:when>
									<c:otherwise>
										<li class="waves-effect"><a href="<c:url value="/Page${hotelPage.startPage - 1}"><c:param name="checkin" value="${param.checkin}" /><c:param name="checkout" value="${param.checkout}" /></c:url>"><i class="material-icons">chevron_left</i></a></li>
									</c:otherwise>
								</c:choose>
						
								<c:forEach begin="${hotelPage.startPage}" end="${hotelPage.endPage}" varStatus="loop">
									<c:choose>
										<c:when test="${hotelPage.currentPage eq loop.index}">
											<li class="active"><a>${loop.index}</a></li>
										</c:when>
										<c:otherwise>
											<li class="waves-effect"><a href="<c:url value="/Page${loop.index}"><c:param name="checkin" value="${param.checkin}" /><c:param name="checkout" value="${param.checkout}" /></c:url>">${loop.index}</a></li>
										</c:otherwise>
									</c:choose>
								</c:forEach>
								<c:choose>
									<c:when test="${hotelPage.endPage eq hotelPage.lastPage}">
										<li class="disabled"><a><i class="material-icons">chevron_right</i></a></li>
									</c:when>
									<c:otherwise>
										<li class="waves-effect"><a href="<c:url value="/Page${hotelPage.endPage + 1}"><c:param name="checkin" value="${param.checkin}" /><c:param name="checkout" value="${param.checkout}" /></c:url>"><i class="material-icons">chevron_right</i></a></li>
									</c:otherwise>
								</c:choose>
							</ul>
						</div>
					</c:when>
					<c:otherwise>
						<div class="col s12 m12 l12 first-container">
							<div class="card">
		            			<div class="card-content">
									<h5 style="color: orange;">Currently there are no free hotels for such dates.</h5>
								</div>
							</div>
						</div>
					</c:otherwise>
				</c:choose>
			</div>
		</div>
	</jsp:body>
</t:page>