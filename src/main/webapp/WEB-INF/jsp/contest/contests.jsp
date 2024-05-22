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
                      <th class="th-sm">Name</th>
                      <th class="th-sm">TotalPrice</th>
                      <th class="th-sm">Entry Fees</th>
                      <th class="th-sm">Members</th>
                      <th class="th-sm">Rank Price</th>
                      <th class="th-sm">Win %</th>
                      <th class="th-sm">Max Teams</th>
                      <th class="th-sm">Last Modified</th>
                    </tr>
              </thead>
              <tbody>
                  <c:forEach items="${contests}" var="contest">
                    <tr id="${contest.id}">
                        <td class="td-sm">${contest.name}</td>
                        <td class="td-sm">Rs.${contest.totalPrice}</td>
                        <td class="td-sm">Rs.${contest.entryFee}</td>
                        <td class="td-sm">${contest.noOfMembers}</td>
                        <td class="td-sm">${contest.rankPrice}</td>
                        <td class="td-sm">${contest.winPercent}%</td>
                        <td class="td-sm">${contest.maxTeams}</td>
                        <td class="td-sm">${contest.lastModified}</td>
                     </tr>
                 </c:forEach>
             </tbody>
         </table>
    </div>
  </div>
</div>
<%@ include file="../tableActions.jsp" %>