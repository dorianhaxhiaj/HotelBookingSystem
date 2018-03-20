<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<t:page pageTitle="Hotel Management" isManageHotelsActive="true">
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
            		
						<c:url var="url" value="/admin/saveHotel" />
						<form:form action="${url}"  method="post"  modelAttribute="hotelForm">
							
							<h4>Hotel Form</h4><br />
							<form:hidden  path="hotelId" />
							<div class="row">
								<div class="input-field col s12">
									<form:input id="hotelName" path="hotelName" cssClass="validate" style="margin-bottom: 0px;"/>
									<label for="hotelName">Hotel Name</label>
									<form:errors path="hotelName" cssClass="input-error" />
								</div>
								<div class="row" style="margin-left: 12px;">
								<p class="rating">
									<span style="line-height: 2.5em;">Hotel Rating:</span>
									<form:errors path="hotelStars" cssClass="input-error" style="float: right; line-height: 2.5em; text-indent: 16px;"/>
				                	<form:errors path="" cssClass="input-error" style="float: right; line-height: 2.5em; text-indent: 16px;" />
									<form:radiobutton id="5_star_rating" path="hotelStars" value="5" /> 
					                <label for="5_star_rating">5 Stars</label>
				                	<form:radiobutton id="4_star_rating" path="hotelStars" value="4" />
					                <label for="4_star_rating">4 Stars</label>
					                <form:radiobutton id="3_star_rating" path="hotelStars" value="3" />
					                <label for="3_star_rating">3 Stars</label>
					                <form:radiobutton id="2_star_rating" path="hotelStars" value="2" />
					                <label for="2_star_rating">2 Stars</label>
					                <form:radiobutton id="1_star_rating" path="hotelStars" value="1" />
					                <label for="1_star_rating">1 Star</label>
				                	
			                	</p>
								</div>
								<div class="row" style="margin-left: 12px;">
								<p>
									Hotel Services:
	
				             		<c:set var="checked" value="${false}" />
				             		<c:forEach items="${hotelServiceList}" var="hotelService">
				             		
				               			<c:forEach items="${hotelForm.hotelServiceIdList}" var="activeServiceId">
					               			<c:if test="${activeServiceId eq hotelService.hotelServiceId}">
					               				<c:set var="checked" value="${true}" />
					               			</c:if>
				               			</c:forEach>
				               			
				               			&nbsp; &nbsp; &nbsp; &nbsp;
				               			<input id="${hotelService.hotelServiceId}" type="checkbox" name="hotelServiceIdList"  value="${hotelService.hotelServiceId}" <c:if test="${checked}">checked</c:if>/>
				               			<label for="${hotelService.hotelServiceId}"><c:out value="${hotelService.name}" /></label>
				               			
				               			<c:set var="checked" value="${false}" />
				               			
				              		</c:forEach>
			              		</p>
			              		</div>
			              		<p style="margin-left: 12px;"><a href="<c:url value='/admin/manageHotelServices' />">Manage Hotel Services</a></p>
								<p style="margin-left: 10px;"><br /><input type="submit" class="btn" value="Register Hotel" /></p> 	
							</div>
						</form:form>
						
					</div>
	    		</div>
	    	</div>
	    	
	    	<br />
	    	
	    	<c:choose>        
				<c:when test="${not empty hotelPage.hotelList}">
					
			    	<h2>Hotel List</h2>
				
					<table class="striped centered responsive-table">
						<thead>
							<tr>
								<th>Hotel ID</th>
								<th>Hotel Name</th>
								<th>Hotel Rating</th>
								<th>Hotel Services</th>
								<th>Actions</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${hotelPage.hotelList}" var="hotel">
								<tr>
									<td><c:out value="${hotel.hotelId}" /></td>
									<td><c:out value="${hotel.hotelName}" /></td>
									<td><c:out value="${hotel.hotelStars}" /></td>
									<td>
										<ul>
											<c:forEach items="${hotel.hotelServices}" var="hotelService">
												<li><c:out value="${hotelService.name}" /></li>
											</c:forEach>
										</ul>
									</td>
							    	<td>
								    	<a href="<c:url value='/admin/editHotel/${hotel.hotelId}' />">Edit</a> &nbsp; &nbsp; | &nbsp; &nbsp;
								    	<form method="post" action="<c:url value='/admin/removeHotel' />" style="display: inline;">
								    		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
								    		<input type="hidden" name="hotelId" value="${hotel.hotelId}" />
								    		<input type="submit" value="Delete" class="link-btn">
								    	</form> &nbsp; &nbsp; | &nbsp; &nbsp;
						             	<a href="<c:url value="/admin/Hotel${hotel.hotelId}/manageRooms" />">Manage Rooms</a>
					             	</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
					<div class="col s12">
						<ul class="pagination">
						
							<c:choose>
								<c:when test="${hotelPage.startPage le 1}">
									<li class="disabled"><a><i class="material-icons">chevron_left</i></a></li>
								</c:when>
								<c:otherwise>
									<li class="waves-effect"><a href="<c:url value="/admin/manageHotels/Page${hotelPage.startPage - 1}" />"><i class="material-icons">chevron_left</i></a></li>
								</c:otherwise>
							</c:choose>
					
							<c:forEach begin="${hotelPage.startPage}" end="${hotelPage.endPage}" varStatus="loop">
								<c:choose>
									<c:when test="${hotelPage.currentPage eq loop.index}">
										<li class="active"><a>${loop.index}</a></li>
									</c:when>
									<c:otherwise>
										<li class="waves-effect"><a href="<c:url value="/admin/manageHotels/Page${loop.index}" />">${loop.index}</a></li>
									</c:otherwise>
								</c:choose>
							</c:forEach>
							<c:choose>
								<c:when test="${hotelPage.endPage eq hotelPage.lastPage}">
									<li class="disabled"><a><i class="material-icons">chevron_right</i></a></li>
								</c:when>
								<c:otherwise>
									<li class="waves-effect"><a href="/admin/manageHotels/Page${hotelPage.endPage + 1}"><i class="material-icons">chevron_right</i></a></li>
								</c:otherwise>
							</c:choose>
						</ul>
					</div>
				</c:when>
				<c:otherwise>
					<div class="col s12 m12 l12 first-container">
						<div class="card">
	            			<div class="card-content">
								<h5 style="color: orange;">Currently there are no hotels in the system.</h5>
							</div>
						</div>
					</div>
				</c:otherwise>
			</c:choose>
		</div>
	</jsp:body>
</t:page>