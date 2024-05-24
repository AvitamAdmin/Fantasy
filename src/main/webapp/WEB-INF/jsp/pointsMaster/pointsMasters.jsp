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
                      <th class="th-sm">Runs</th>
                      <th class="th-sm">Wickets</th>
                      <th class="th-sm">Half-Century</th>
                      <th class="th-sm">Century</th>
                      <th class="th-sm">30 Plus</th>
                      <th class="th-sm">3 Wickets</th>
                      <th class="th-sm">5 Wickets</th>
                      <th class="th-sm">3 Fours</th>
                      <th class="th-sm">3 Sixes</th>
                      <th class="th-sm">Economy</th>
                      <th class="th-sm">MatchType Id</th>

                    </tr>
              </thead>
              <tbody>
                  <c:forEach items="${models}" var="model">
                    <tr id="${model.id}">
                        <td class="td-sm">${model.id}</td>
                        <td class="td-sm">${model.runs}</td>
                        <td class="td-sm">${model.wickets}</td>
                        <td class="td-sm">${model.halfCentury}</td>
                        <td class="td-sm">${model.century}</td>
                        <td class="td-sm">${model.thirtyPlus}</td>
                        <td class="td-sm">${model.threeWickets}</td>
                        <td class="td-sm">${model.fiveWickets}</td>
                        <td class="td-sm">${model.hatrickWickets}</td>
                        <td class="td-sm">${model.hatrickSixes}</td>
                        <td class="td-sm">${model.economy}</td>
                        <td class="td-sm">${model.matchTypeId}</td>
                     </tr>
                 </c:forEach>
             </tbody>
         </table>
    </div>
  </div>
</div>
<%@ include file="../tableActions.jsp" %>