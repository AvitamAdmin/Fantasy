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
                      <th class="th-sm">Banner Image</th>
                      <th class="th-sm">Banner Size</th>
                      <th class="th-sm">Redirect URL</th>

                    </tr>
              </thead>
              <tbody>
                  <c:forEach items="${banners}" var="model">
                    <tr id="${model.id}">
                        <td class="td-sm">${model.id}</td>
                        <td class="td-sm"><img style="width:50px;" src="data:image/jpeg;base64,${model.pic}"</td>
                        <td class="td-sm">${model.size}</td>
                        <td class="td-sm">${model.url}</td>
                     </tr>
                 </c:forEach>
             </tbody>
         </table>
    </div>
  </div>
</div>
<%@ include file="../tableActions.jsp" %>