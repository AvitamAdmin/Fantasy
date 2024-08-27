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
                      <th class="th-sm">Player Name</th>
                      <th class="th-sm">DOB</th>
                      <th class="th-sm">Nationality</th>
                      <th class="th-sm">Team ID</th>
                      <th class="th-sm">Player Role ID</th>
                      <th class="th-sm">Player Image</th>
                    </tr>
              </thead>
              <tbody>
                  <c:forEach items="${models}" var="model">
                    <tr id="${model.id}">
                        <td class="td-sm">${model.recordId}</td>
                        <td class="td-sm">${model.name}</td>
                        <td class="td-sm">${model.dob}</td>
                        <td class="td-sm">${model.nationality}</td>
                        <td class="td-sm">${model.teamId}</td>
                        <td class="td-sm">${model.playerRoleId}</td>
                        <td class="td-sm"><img style="width:50px;" src="data:image/jpeg;base64,${model.pic}"</td>
                     </tr>
                 </c:forEach>
             </tbody>
         </table>
    </div>
  </div>
</div>
<%@ include file="../tableActions.jsp" %>