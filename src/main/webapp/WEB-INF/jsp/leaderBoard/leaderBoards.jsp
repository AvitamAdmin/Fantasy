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
                      <th class="th-sm">UserId</th>
                      <th class="th-sm">TournamentId</th>
                      <th class="th-sm">MatchPlayed</th>
                      <th class="th-sm">Rank</th>
                      <th class="th-sm">BonusAmount</th>
                    </tr>
              </thead>
              <tbody>
                  <c:forEach items="${models}" var="model">
                    <tr id="${model.id}">
                        <td class="td-sm">${model.recordId}</td>
                        <td class="th-sm">${model.userId}</td>
                        <td class="th-sm">${model.tournamentId}</td>
                        <td class="th-sm">${model.matchesPlayed}</td>
                        <td class="th-sm">${model.rank}</td>
                        <td class="th-sm">Rs.${model.bonusAmount}</td>
                     </tr>
                 </c:forEach>
             </tbody>
         </table>
    </div>
  </div>
</div>
<%@ include file="../tableActions.jsp" %>