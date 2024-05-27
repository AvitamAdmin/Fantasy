<%@ include file="../include.jsp" %>
<div class="main-content">
    <div class="row">
        <div class="col-sm-12">
            <p class="navigation" id="navBreadcrumb">Breadcrumb</p>
        </div>
    </div>
    <form:form method="POST" id="editForm" enctype="multipart/form-data" action="/admin/matches/edit" class="handle-upload" modelAttribute="editForm" >
        <div class="row">
            <div class="col-sm-12">
                <div class="dt-buttons">
                    <button class="btn btn-primary btn-icon btn-icon-small" tabindex="2" onclick="javascript:fire_ajax_submit('/admin/matches')" aria-controls="tableData" title="Cancel" type="button">Cancel</button>
                    <button class="btn btn-primary btn-icon btn-icon-small" tabindex="2" onclick="submitFormById('#editForm');" aria-controls="tableData" type="button" title="Save">Save</button>
                </div>
            </div>
        </div>
        <%@ include file="../commonFields.jsp" %>
        <div class="row">

            <div class="col-sm-3">
                 <select class="cheil-select" name="teamId1">
                    <option value="">Select Team1</option>
                    <c:forEach items="${teams}" var="team">
                        <c:choose>
                            <c:when test="${fn:contains( editForm.teamId1, team.id ) }">
                              <option value="${team.id}" selected>${team.teamName}</option>
                            </c:when>
                            <c:otherwise>
                               <option value="${team.id}" >${team.teamName}</option>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                </select>
            </div>

            <div class="col-sm-3">
                 <select class="cheil-select" name="teamId2">
                    <option value="">Select Team2</option>
                    <c:forEach items="${teams}" var="team">
                        <c:choose>
                            <c:when test="${fn:contains( editForm.teamId2, team.id ) }">
                              <option value="${team.id}" selected>${team.teamName}</option>
                            </c:when>
                            <c:otherwise>
                               <option value="${team.id}" >${team.teamName}</option>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                </select>
            </div>

            <div class="col-sm-3">
                 <select class="cheil-select" name="tournamentId">
                    <option value="">Select Tournament</option>
                    <c:forEach items="${tournaments}" var="tournament">
                        <c:choose>
                            <c:when test="${fn:contains( editForm.tournamentId, tournament.id ) }">
                              <option value="${tournament.id}" selected>${tournament.tournamentName}</option>
                            </c:when>
                            <c:otherwise>
                               <option value="${tournament.id}" >${tournament.tournamentName}</option>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                </select>
            </div>

            <div class="col-sm-3">
                 <select class="cheil-select" name="sportTypeId">
                    <option value="">Select Sport Type</option>
                    <c:forEach items="${sportTypes}" var="sportType">
                        <c:choose>
                            <c:when test="${fn:contains( editForm.sportTypeId, sportType.id ) }">
                              <option value="${sportType.id}" selected>${sportType.sportName}</option>
                            </c:when>
                            <c:otherwise>
                               <option value="${sportType.id}" >${sportType.sportName}</option>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                </select>
            </div>

            <div class="col-sm-3">
                 <select class="cheil-select" name="contestId">
                    <option value="">Select Contest</option>
                    <c:forEach items="${contests}" var="contest">
                        <c:choose>
                            <c:when test="${fn:contains( editForm.contestId, contest.id ) }">
                              <option value="${contest.id}" selected>${contest.contestName}</option>
                            </c:when>
                            <c:otherwise>
                               <option value="${contest.id}" >${contest.contestName}</option>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                </select>
            </div>

            <div class="col-sm-3">
                 <select class="cheil-select" name="matchTypeId">
                    <option value="">Select Match Type</option>
                    <c:forEach items="${matchTypes}" var="matchType">
                        <c:choose>
                            <c:when test="${fn:contains( editForm.matchTypeId, matchType.id ) }">
                              <option value="${matchType.id}" selected>${matchType.matchType}</option>
                            </c:when>
                            <c:otherwise>
                               <option value="${matchType.id}" >${matchType.matchType}</option>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                </select>
            </div>
            <div class="col-sm-6">
                <form:input type="datetime-local" path="dateAndTime" class="inputbox-cheil-small" placeholder="Date And Time" />
                <span>City</span>
                <form:errors path="dateAndTime" class="text-danger"></form:errors>
            </div>
            <div class="col-sm-3">
                <c:choose>
                    <c:when test="${editForm.matchStatus}">
                         <c:set var="varChecked" value="checked"></c:set>
                     </c:when>
                     <c:otherwise>
                         <c:set var="varUnchecked" value="checked"></c:set>
                     </c:otherwise>
                </c:choose>
                <input type="radio" name="matchStatus"  value="true" ${varChecked}> Live
                <input type="radio" name="matchStatus" value="false" ${varUnchecked}> No Live Action
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