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
                      <th class="th-sm">OTP
                    </tr>
              </thead>
              <tbody>
                  <c:forEach items="${tokens}" var="token">
                    <tr id="${token.id}">
                        <td class="td-sm">${token.id}</td>
                        <td class="td-sm">${token.userId}</td>
                        <td class="td-sm">${token.otp}</td>
                     </tr>
                 </c:forEach>
             </tbody>
         </table>
    </div>
  </div>
</div>
<%@ include file="../tableActions.jsp" %>