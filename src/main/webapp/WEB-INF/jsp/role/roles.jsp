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
                      <th class="th-sm">Id</th>
                      <th class="th-sm">Name</th>
                      <th class="th-sm">Role  Id</th>
                      <th class="th-sm">Creator</th>
                      <th class="th-sm">Creation Time</th>
                      <th class="th-sm">Last Modified</th>
                    </tr>
              </thead>
              <tbody>
                  <c:forEach items="${roles}" var="role">
                    <tr id="${role.id}">
                        <td class="td-sm">${role.recordId}</td>
                        <td class="td-sm">${role.name}</td>
                        <td class="td-sm">${role.roleId}</td>
                        <td class="td-sm">${role.creator}</td>
                        <td class="td-sm">${role.creationTime}</td>
                        <td class="td-sm">${role.lastModified}</td>
                     </tr>
                 </c:forEach>
             </tbody>
         </table>
    </div>
  </div>
</div>
<%@ include file="../tableActions.jsp" %>
