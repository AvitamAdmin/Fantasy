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
                      <th class="th-sm">Email</th>
                      <th class="th-sm">Status</th>

                      <th class="th-sm">Role</th>

                    </tr>
              </thead>
              <tbody>
                  <c:forEach items="${userList}" var="model">
                    <tr id="${model.id}">
                        <td class="td-sm">${model.id}</td>
                        <td class="td-sm">${model.username}</td>
                        <c:choose>
                            <c:when test="${model.status}">
                                 <c:set var="varChecked" value="Active"></c:set>
                             </c:when>
                             <c:otherwise>
                                 <c:set var="varChecked" value="Inactive"></c:set>
                             </c:otherwise>
                        </c:choose>
                        <td class="td-sm">${varChecked}</td>

                        <td class="td-sm">
                            <c:forEach items="${model.roles}" var="child">
                                 ${child.name},
                            </c:forEach>
                        </td>
                     </tr>
                 </c:forEach>
             </tbody>
         </table>
    </div>
  </div>
</div>
<%@ include file="../tableActions.jsp" %>