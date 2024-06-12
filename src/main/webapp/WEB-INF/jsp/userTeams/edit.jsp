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
            <div class="col-sm-2">
                <form:input path="userId" class="inputbox-cheil-small" placeholder="Enter UserId" />
                <span>User Id</span>
                <form:errors path="userId" class="text-danger"></form:errors>
            </div>
            <div class="col-sm-2">
                <form:input path="name" class="inputbox-cheil-small" placeholder="UserTeamName" />
                <span>UserTeam Name</span>
                <form:errors path="name" class="text-danger"></form:errors>
            </div>
           <div class="col-sm-2">
               <select id="matchId" class="cheil-select" name="matchId">
                   <option value="">Select Match</option>
                   <c:forEach items="${matches}" var="child">
                       <c:choose>
                           <c:when test="${fn:contains( editForm.matchId, child ) }">
                              <input type="hidden" id="matchId" <option value="${child.id}" selected>${child.name}</option>>
                           </c:when>
                           <c:otherwise>
                               <option value="${child.id}" >${child.name}</option>
                           </c:otherwise>
                       </c:choose>
                   </c:forEach>
               </select>
               </div>

            <div class="col-sm-3">
               <select class="cheil-select" name="team1Players">
                     <option value="">Select Team 1 Players</option>
                        <c:forEach items="${allPlayers.team1Players}" var="child">
                             <c:choose>
                                 <c:when test="${fn:contains( editForm.players, child ) }">
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
               <select class="cheil-select" name="team2Players">
                <option value="">Select Team 2 Players</option>
                      <c:forEach items="${allPlayers.team2Players}" var="child">
                             <c:choose>
                                 <c:when test="${fn:contains( editForm.players, child ) }">
                                       <option value="${child.id}" selected>${child.name}</option>
                                 </c:when>
                                 <c:otherwise>
                                     <option value="${child.id}" >${child.name}</option>
                                 </c:otherwise>
                             </c:choose>
                      </c:forEach>
               </select>
            </div>
       <input type="hidden" id="matchID" name="matchId" >
       </form:form>
       <c:if test="${not empty message}">
           <div class="alert alert-danger" role="alert" id="errorMessage">
               <spring:message code="${message}" />
           </div>
       </c:if>
    </div>
</div>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>

<script>
     $(document).ready(function() {
       $('#myButton').click(function() {
         var matchId = ("#matchId").val(); // Replace with the actual match ID

         $.ajax({
           url: '/getMatchDetails?matchId=' + matchId,
           type: 'GET',
           success: function(response) {
             // Handle the successful response
             console.log(response);
           },
           error: function(xhr, status, error) {
             // Handle the error response
             console.error(error);
           }
         });
       });
     });
</script>
