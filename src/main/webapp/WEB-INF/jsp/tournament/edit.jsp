<%@ include file="../include.jsp" %>
<div class="main-content">
    <div class="row">
        <div class="col-sm-12">
            <p class="navigation" id="navBreadcrumb">Breadcrumb</p>
        </div>
    </div>
    <form:form method="POST" id="editForm" enctype="multipart/form-data" action="/admin/tournament/edit"  class="handle-upload" modelAttribute="editForm" >
        <div class="row">
            <div class="col-sm-12">
                <div class="dt-buttons">
                    <button class="btn btn-primary btn-icon btn-icon-small" tabindex="2" onclick="javascript:fire_ajax_submit('/admin/tournament')" aria-controls="tableData" title="Cancel" type="button">Cancel</button>
                    <button class="btn btn-primary btn-icon btn-icon-small" tabindex="2" onclick="submitFormById('#editForm');" aria-controls="tableData" type="button" title="Save">Save</button>
                </div>
            </div>
        </div>
        <%@ include file="../commonFields.jsp" %>
        <div class="row">
            <div class="col-sm-3">
                <form:input path="tournamentName" class="inputbox-cheil-small" placeholder="Enter Tournament Name" />
                <span>Name</span>``
                <form:errors path="tournamentName" class="text-danger"></form:errors>
            </div>
            <div class="col-sm-3">
               <select class="cheil-select" name="sportId">
                    <option value="">Select SportType</option>
                        <c:forEach items="${sportTypes}" var="sportType">
                            <c:choose>
                                <c:when test="${fn:contains( editForm.sportId, sportType.id ) }">
                                      <option value="${sportType.id}" selected>${sportType.sportName}</option>
                                </c:when>
                                <c:otherwise>
                                    <option value="${sportType.id}" >${sportType.sportName}</option>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>-
               </select>
            </div>
            <div class="col-sm-6">
                <form:input type="datetime-local" path="dateAndTime" class="inputbox-cheil-small" placeholder="Date And Time" />
                <span>City</span>
                <form:errors path="dateAndTime" class="text-danger"></form:errors>
            </div>
       </form:form>
       <c:if test="${not empty message}">
           <div class="alert alert-danger" role="alert" id="errorMessage">
               <spring:message code="${message}" />
           </div>
       </c:if>
    </div>
  </div>
</div>