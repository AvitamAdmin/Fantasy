<%@ include file="../include.jsp" %>
<div class="main-content">
<div class="row">
    <div class="col-sm-10">
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
                      <th class="th-sm">UserName</th>
                      <th class="th-sm">Paying Amount</th>
                      <th class="th-sm">Gateway Name</th>
                      <th class="th-sm">Transaction Id</th>
                      <th class="th-sm">Status</th>
                    </tr>
              </thead>
              <tbody>
                  <c:forEach items="${models}" var="model">
                    <tr id="${model.id}">
                        <td class="td-sm">${model.id}</td>
                        <td class="td-sm">${model.userId}</td>
                        <td class="td-sm">${model.payingAmount}</td>
                        <td class="td-sm">${model.gatewayName}</td>
                        <td class="td-sm">${model.transactionId}</td>
                        <td class="td-sm">${model.depositStatus}</td>
                     </tr>
                 </c:forEach>
             </tbody>
         </table>
    </div>
  </div>
</div>
<%@ include file="../tableActions.jsp" %>