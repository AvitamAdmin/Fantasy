<%@ include file="../include.jsp" %>
<div class="main-content">
<div class="row">
    <div class="col-sm-12">
        <p class="navigation" id="navBreadcrumb">Breadcrumb</p>
    </div>
</div>
  <div class="row">
    <div class="col-sm-24">
         <table id="tableData" class="table table-bordered table-sm"  width="50%" data-tableName="Stock Data">
              <input type="hidden" id="rowSelectorId" name="rowSelectorId" value="">
              <thead>
                    <tr>
                      <th class="th-sm">PK</th>
                      <th class="th-sm">MatchId</th>
                      <th class="th-sm">PlayerId</th>
                      <th class="th-sm">BattingPoints</th>
                      <th class="th-sm">BowlingPoints</th>
                      <th class="th-sm">FieldingPoints</th>
                      <th class="th-sm">TotalPoints</th>
                      <th class="th-sm">Economy</th>
                      <th class="th-sm">MaidenPoints</th>
                      <th class="th-sm">Boundaries</th>
                      <th class="th-sm">StrikeRate</th>
                      <th class="th-sm">Century</th>
                      <th class="th-sm">BatsmanScore</th>
                      <th class="th-sm">BowlerWickets</th>
                      <th class="th-sm">Catches</th>
                      <th class="th-sm">starting11</th>


                    </tr>
              </thead>
              <tbody>
                  <c:forEach items="${models}" var="model">
                    <tr id="${model.id}">
                        <td class="td-sm">${model.recordId}</td>
                        <td class="td-sm">${model.matchId}</td>
                        <td class="td-sm">${model.playerId}</td>
                        <td class="td-sm">${model.battingPoints}</td>
                        <td class="td-sm">${model.bowlingPoints}</td>
                        <td class="td-sm">${model.fieldingPoints}</td>
                        <td class="td-sm">${model.totalPoints}</td>
                        <td class="td-sm">${model.economyPoints}</td>
                        <td class="td-sm">${model.maidenPoints}</td>
                        <td class="td-sm">${model.boundaryPoints}</td>
                        <td class="td-sm">${model.strikeRatePoints}</td>
                        <td class="td-sm">${model.centuryPoints}</td>
                        <td class="td-sm">${model.batsmanScore}</td>
                        <td class="td-sm">${model.bowlerWickets}</td>
                        <td class="td-sm">${model.catches}</td>
                        <td class="td-sm">${model.starting11}</td>

                     </tr>
                 </c:forEach>
             </tbody>
         </table>
    </div>
  </div>
</div>
<%@ include file="../tableActions.jsp" %>