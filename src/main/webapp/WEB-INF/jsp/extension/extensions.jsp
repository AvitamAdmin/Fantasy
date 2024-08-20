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
                      <th class="th-sm">Image</th>
                      <th class="th-sm">Name</th>
                      <th class="th-sm">Status</th>

                    </tr>
              </thead>
              <tbody>
                  <c:forEach items="${models}" var="model">
                    <tr id="${model.id}">
                        <td class="td-sm">${model.id}</td>
                        <td class="td-sm"><img style="width:50px;" src="data:image/jpeg;base64,${model.pic}"</td>
                        <td class="td-sm">${model.name}</td>
                        <td class="td-sm">${model.extendStatus}</td>
                     </tr>
                 </c:forEach>
             </tbody>
         </table>
    </div>
  </div>
</div>
<%@ include file="../tableActions.jsp" %>