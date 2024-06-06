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
                      <th class="th-sm">ID</th>
                      <th class="th-sm">Match Name</th>
                      <th class="th-sm">Team1 ID</th>
                      <th class="th-sm">Team2 ID</th>
                      <th class="th-sm">Date And Time</th>
                      <th class="th-sm">Tournament ID</th>
                      <th class="th-sm">Sport Type ID</th>
                      <th class="th-sm">Contest ID</th>
                      <th class="th-sm">Match Type ID</th>
                      <th class="th-sm">Match Status</th>
                    </tr>
              </thead>
              <tbody>
                  <c:forEach items="${models}" var="model">
                    <tr id="${model.id}">
                        <td class="td-sm">${model.id}</td>
                        <td class="td-sm">${model.name}</td>
                        <td class="td-sm">${model.team1Id}</td>
                        <td class="td-sm">${model.team2Id}</td>
                        <td class="td-sm">${model.dateAndTime}</td>
                        <td class="td-sm">${model.tournamentId}</td>
                        <td class="td-sm">${model.sportTypeId}</td>
                        <td class="td-sm">${model.contestId}</td>
                        <td class="td-sm">${model.matchTypeId}</td>
                        <td class="td-sm">${model.matchStatus}</td>
                     </tr>
                 </c:forEach>
             </tbody>
         </table>
    </div>
  </div>
</div>
<%@ include file="../tableActions.jsp" %>