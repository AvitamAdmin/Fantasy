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
                      <th class="th-sm">Line_1</th>
                      <th class="th-sm">Line_2</th>
                      <th class="th-sm">City</th>
                      <th class="th-sm">State</th>
                      <th class="th-sm">PinCode</th>
                    </tr>
              </thead>
              <tbody>
                  <c:forEach items="${models}" var="model">
                    <tr id="${model.id}">
                        <td class="td-sm">${model.id}</td>
                        <td class="td-sm">${model.line_1}</td>
                        <td class="td-sm">${model.line_2}</td>
                        <td class="td-sm">${model.city}</td>
                        <td class="td-sm">${model.state}</td>
                        <td class="td-sm">${model.pinCode}</td>
                     </tr>
                 </c:forEach>
             </tbody>
         </table>
    </div>
  </div>
</div>
<%@ include file="../tableActions.jsp" %>