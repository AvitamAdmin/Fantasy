<%@ include file="../include.jsp" %>
<div class="main-content">
    <div class="row">
        <div class="col-sm-12">
            <p class="navigation" id="navBreadcrumb">Breadcrumb</p>
        </div>
    </div>
    <form:form method="POST" id="editForm" enctype="multipart/form-data" action="/admin/pointsUpdate/edit" class="handle-upload" modelAttribute="editForm" >
        <div class="row">
            <div class="col-sm-12">
                <div class="dt-buttons">
                    <button class="btn btn-primary btn-icon btn-icon-small" tabindex="2" onclick="javascript:fire_ajax_submit('/admin/pointsUpdate')" aria-controls="tableData" title="Cancel" type="button">Cancel</button>
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
                   <select class="cheil-select" name="playerId">
                      <option value="">Select Player</option>
                      <c:forEach items="${players}" var="child">
                          <c:choose>
                              <c:when test="${fn:contains( editForm.playerId, child ) }">

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
                <form:input path="battingPoints" class="inputbox-cheil-small" />
                <span>BattingPoints</span>
                <form:errors path="battingPoints" class="text-danger"></form:errors>
            </div>
            <div class="col-sm-3">
                <form:input path="bowlingPoints" class="inputbox-cheil-small" />
                <span>BowlingPoints</span>
                <form:errors path="bowlingPoints" class="text-danger"></form:errors>
            </div>
            <div class="col-sm-3">
                <form:input path="fieldingPoints" class="inputbox-cheil-small" />
                <span>FieldingPoints</span>
                <form:errors path="fieldingPoints" class="text-danger"></form:errors>
            </div>
            <div class="col-sm-3">
                <form:input path="totalPoints" class="inputbox-cheil-small" />
                <span>TotalPoints</span>
                <form:errors path="totalPoints" class="text-danger"></form:errors>
            </div>
            <div class="col-sm-3">
                <form:input path="economyPoints" class="inputbox-cheil-small" />
                <span>EconomyPoints</span>
                <form:errors path="economyPoints" class="text-danger"></form:errors>
            </div>
            <div class="col-sm-3">
                <form:input path="maidenPoints" class="inputbox-cheil-small" />
                <span>MaidenPoints</span>
                <form:errors path="maidenPoints" class="text-danger"></form:errors>
            </div>
            <div class="col-sm-3">
                <form:input path="boundaryPoints" class="inputbox-cheil-small" />
                <span>BoundaryPoints</span>
                <form:errors path="boundaryPoints" class="text-danger"></form:errors>
            </div>
            <div class="col-sm-3">
                  <form:input path="strikeRatePoints" class="inputbox-cheil-small" />
                  <span>StrikeRate Points</span>
                  <form:errors path="strikeRatePoints" class="text-danger"></form:errors>
            </div>
            <div class="col-sm-3">
                <form:input path="centuryPoints" class="inputbox-cheil-small" />
                <span>CenturyPoints</span>
                <form:errors path="centuryPoints" class="text-danger"></form:errors>
            </div>
            <div class="col-sm-3">
                  <form:input path="batsmanScore" class="inputbox-cheil-small" />
                  <span>BatsmanScore</span>
                  <form:errors path="batsmanScore" class="text-danger"></form:errors>
            </div>
            <div class="col-sm-3">
                  <form:input path="bowlerWickets" class="inputbox-cheil-small" />
                  <span>BowlerWickets</span>
                  <form:errors path="bowlerWickets" class="text-danger"></form:errors>
            </div>
            <div class="col-sm-3">
                  <form:input path="catches" class="inputbox-cheil-small" />
                  <span>Catches</span>
                  <form:errors path="catches" class="text-danger"></form:errors>
            </div>
            <div class="col-sm-3">
                  <form:input path="starting11" class="inputbox-cheil-small" />
                  <span>Starting11</span>
                  <form:errors path="starting11" class="text-danger"></form:errors>
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