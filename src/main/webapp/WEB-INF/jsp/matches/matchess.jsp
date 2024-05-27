<%@ include file="../include.jsp" %>
<div class="main-content">
<div class="row">
    <div class="col-sm-12">
        <p class="navigation" id="navBreadcrumb">Breadcrumb</p>
    </div>
</div>
  <div class="row">
    <div class="col-sm-12">
         <table id="tableData" class="table table-striped table-bordered table-sm" cellspacing="0" width="100%" data-tableName="Stock Data">
              <input type="hidden" id="rowSelectorId" name="rowSelectorId" value="">
              <thead>
                    <tr>
                      <th class="th-sm">PK</th>
                      <th class="th-sm">Team1</th>
                      <th class="th-sm">Team2</th>
                      <th class="th-sm">DateAndTime</th>
                      <th class="th-sm">TournamentID</th>
                      <th class="th-sm">SportID</th>
                      <th class="th-sm">ContestIDs</th>
                      <th class="th-sm">Status</th>
                      <th class="th-sm">MatchTypeID</th>
                    </tr>
              </thead>
              <tbody>
                  <c:forEach items="${models}" var="model">
                    <tr id="${model.id}">
                        <td class="td-sm">${model.id}</td>
                        <td class="td-sm">${model.team1}</td>
                        <td class="td-sm">${model.team2}</td>
                        <td class="td-sm">${model.dateAndTime}</td>
                        <td class="td-sm">${model.tournamentId}</td>
                        <td class="td-sm">${model.sportsId}</td>
                        <td class="td-sm">${model.contestId}</td>
                        <td class="td-sm">${model.status}</td>
                        <td class="td-sm">${model.matchTypeId}</td>
                     </tr>
                 </c:forEach>
             </tbody>
         </table>
    </div>
  </div>
</div>
<%@ include file="../tableActions.jsp" %>