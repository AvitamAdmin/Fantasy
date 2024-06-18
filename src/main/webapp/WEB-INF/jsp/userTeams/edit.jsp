<%@ include file="../include.jsp" %>
<div class="main-content">
    <div class="row">
        <div class="col-sm-12">
            <p class="navigation" id="navBreadcrumb">Breadcrumb</p>
        </div>
    </div>
    <form:form method="POST" id="editForm" enctype="multipart/form-data" action="/admin/userTeams/edit"  class="handle-upload" modelAttribute="editForm" >
        <div class="row">
            <div class="col-sm-12">
                <div class="dt-buttons">
                    <button class="btn btn-primary btn-icon btn-icon-small" tabindex="2" onclick="javascript:fire_ajax_submit('/admin/userTeams')" aria-controls="tableData" title="Cancel" type="button">Cancel</button>
                    <button class="btn btn-primary btn-icon btn-icon-small" tabindex="2" onclick="submitFormById('#editForm');" aria-controls="tableData" type="button" title="Save">Save</button>
                </div>
            </div>
        </div>
        <%@ include file="../commonFields.jsp" %>
        <div class="row">
            <div class="col-sm-3">
                <form:input path="userId" class="inputbox-cheil-small" placeholder="Enter Id" />
                <span>User-Id</span>
                <form:errors path="userId" class="text-danger"></form:errors>
            </div>
            <div class="col-sm-3">
                 <form:input path="name" class="inputbox-cheil-small" placeholder="Name" />
                 <span>User-Team Name</span>
                 <form:errors path="name" class="text-danger"></form:errors>
            </div>
            <div class="col-sm-3">
                 <select class="cheil-select" name="matchId">
                    <option value="">Select Match</option>
                    <c:forEach items="${matches}" var="match">
                        <c:choose>
                            <c:when test="${fn:contains( editForm.matchId, match.id ) }">
                              <option value="${match.id}" selected>${match.name}</option>
                            </c:when>
                            <c:otherwise>
                               <option value="${match.id}" >${match.name}</option>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                </select>
            </div>
            <div class="col-sm-3">
                 <select class="cheil-select" name="players" multiple="true">
                    <option value="">Select Team1 Players</option>
                    <c:forEach items="${allPlayers}" var="player">
                        <c:choose>
                            <c:when test="${fn:contains( editForm.players, player.id ) }">
                              <option value="${player.id}" >${player.name}</option>
                            </c:when>
                            <c:otherwise>
                               <option value="${player.id}" >${player.name}</option>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                </select>
            </div>
            <div class="col-sm-3">
                 <select class="cheil-select" name="players" multiple="true">
                    <option value="">Select Team2Players</option>
                    <c:forEach items="${allPlayers}" var="player">
                        <c:choose>
                            <c:when test="${fn:contains( editForm.players, player.id ) }">
                              <option value="${player.id}" selected-multiple>${player.name}</option>
                            </c:when>
                            <c:otherwise>
                               <option value="${player.id}" >${player.name}</option>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                </select>
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