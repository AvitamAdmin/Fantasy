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
                <form:input path="userId" class="inputbox-cheil-small" placeholder="Enter UserId" />
                <span>User Id</span>
                <form:errors path="userId" class="text-danger"></form:errors>
            </div>
             <div class="col-sm-3">
                <select class="cheil-select" name="matchId">
                     <option value="">Select Match</option>
                         <c:forEach items="${matches}" var="child">
                             <c:choose>
                                 <c:when test="${fn:contains( editForm.matchId, child ) }">
                                       <option value="${child.id}" selected>${child.id}</option>
                                 </c:when>
                                 <c:otherwise>
                                     <option value="${child.id}" >${child.id}</option>
                                 </c:otherwise>
                             </c:choose>
                         </c:forEach>
                </select>
             </div>

            <div class="col-sm-3">
               <select class="cheil-select" name="team1Players">
                    <option value="">Select Team 1 Players</option>
               </select>
            </div>

             <div class="col-sm-3">
               <select class="cheil-select" name="team2Players">
                    <option value="">Select Team 2 Players</option>
               </select>
            </div>

       </form:form>
       <c:if test="${not empty message}">
           <div class="alert alert-danger" role="alert" id="errorMessage">
               <spring:message code="${message}" />
           </div>
       </c:if>
    </div>
</div>

<script>
document.addEventListener('DOMContentLoaded', function () {
    const matchSelect = document.querySelector('[name="matchId"]');
    const team1PlayersSelect = document.querySelector('[name="team1Players"]');
    const team2PlayersSelect = document.querySelector('[name="team2Players"]');

    matchSelect.addEventListener('change', function () {
        const selectedMatchId = this.value;
        if (selectedMatchId) {
            fetch(`web/controllers/admin/userTeams/getTeamsByMatchId?matchId=${selectedMatchId}`)
                .then(response => response.json())
                .then(data => {
                    const team1Players = data.team1Players;
                    const team2Players = data.team2Players;

                    // Clear previous options
                    team1PlayersSelect.innerHTML = '';
                    team2PlayersSelect.innerHTML = '';

                    // Populate team 1 players dropdown
                    team1Players.forEach(player => {
                        const option = new Option(player.playerName, player.id);
                        team1PlayersSelect.add(option);
                    });

                    // Populate team 2 players dropdown
                    team2Players.forEach(player => {
                        const option = new Option(player.playerName, player.id);
                        team2PlayersSelect.add(option);
                    });
                })
                .catch(error => console.error('Error fetching players:', error));
        } else {
            // Clear players dropdowns if no match is selected
            team1PlayersSelect.innerHTML = '';
            team2PlayersSelect.innerHTML = '';
        }
    });
});
</script>
