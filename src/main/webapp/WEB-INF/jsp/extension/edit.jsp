<%@ include file="../include.jsp" %>
<div class="main-content">
        <div class="row">
            <div class="col-sm-12">
                <p class="navigation" id="navBreadcrumb">Breadcrumb</p>
            </div>
        </div>
    <form:form method="POST" id="editForm" enctype="multipart/form-data" action="/settings/extension/edit" class="handle-upload" modelAttribute="editForm" >
            <div class="row">
                <div class="col-sm-12">
                    <div class="dt-buttons">
                        <button class="btn btn-primary btn-icon btn-icon-small" tabindex="2" onclick="javascript:fire_ajax_submit('/settings/extension')" aria-controls="tableData" title="Cancel" type="button">Cancel</button>
                        <button class="btn btn-primary btn-icon btn-icon-small" tabindex="2" onclick="ajaxformSubmit('editForm');" aria-controls="tableData" type="submit" title="Save">Save</button>
                    </div>
                </div>
            </div>
            <%@ include file="../commonFields.jsp" %>
            <div class="row">
                 <div class="col-sm-4">
                    <form:input path="name" class="inputbox-cheil-small" placeholder="Player Name"/>
                    <span>Name</span>
                    <form:errors path="name" class="text-danger"></form:errors>
                 </div>

                 <div class="col-sm-4">
                      <form:input path="extendStatus" class="inputbox-cheil-small" placeholder="Status" />
                      <form:errors path="extendStatus" class="text-danger"></form:errors>
                 </div>

                 <div class="col-sm-4">
                  <form:input type="file" path="Image" class="inputbox-cheil-small" />
                 </div>
            </div>
    </form:form>
           <c:if test="${not empty message}">
               <div class="alert alert-danger" role="alert" id="errorMessage">
                   <spring:message code="${message}" />
               </div>
           </c:if>
    </div>
  </div>
</div>