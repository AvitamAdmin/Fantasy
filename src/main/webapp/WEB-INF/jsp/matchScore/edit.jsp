<%@ include file="../include.jsp" %>
<div class="main-content">
    <div class="row">
        <div class="col-sm-12">
            <p class="navigation" id="navBreadcrumb">Breadcrumb</p>
        </div>
    </div>
    <form:form method="POST" id="editForm" enctype="multipart/form-data" action="/admin/matchScore/edit" class="handle-upload" modelAttribute="editForm" >
        <div class="row">
            <div class="col-sm-12">
                <div class="dt-buttons">
                    <button class="btn btn-primary btn-icon btn-icon-small" tabindex="2" onclick="javascript:fire_ajax_submit('/admin/matchScore')" aria-controls="tableData" title="Cancel" type="button">Cancel</button>
                    <button class="btn btn-primary btn-icon btn-icon-small" tabindex="2" onclick="submitFormById('#editForm');" aria-controls="tableData" type="button" title="Save">Save</button>
                </div>
            </div>
        </div>
        <%@ include file="../commonFields.jsp" %>
        <div class="row">
           <div class="col-sm-3">
                <select class="cheil-select" name="matchId">
                   <option value="">Select Match</option>
                   <c:forEach items="${matches}" var="child">
                       <c:choose>
                           <c:when test="${fn:contains( editForm.matchId, child ) }">

                             <option value="${child.id}" selected>${child.name}</option>
                           </c:when>
                           <c:otherwise>
                                    <option value="${child.id}" >${child.name}</option>
                           </c:otherwise>
                       </c:choose>
                   </c:forEach>
               </select>
           </div>
            <div class="col-sm-3">
                <form:input path="team1Score" class="inputbox-cheil-small" placeholder="Team1 Score" />
                <span>Team1 Score</span>
                <form:errors path="team1Score" class="text-danger"></form:errors>
            </div>
            <div class="col-sm-3">
                <form:input path="team2Score" class="inputbox-cheil-small" placeholder="Team2Score" />
                <span>Team2 Score</span>
                <form:errors path="team2Score" class="text-danger"></form:errors>
            </div>
            <div class="col-sm-3">
                <form:input path="overs" class="inputbox-cheil-small" placeholder="Overs" />
                <span>Overs</span>
                <form:errors path="overs" class="text-danger"></form:errors>
            </div>
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