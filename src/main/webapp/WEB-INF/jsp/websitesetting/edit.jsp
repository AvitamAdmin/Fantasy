<%@ include file="../include.jsp" %>
<div class="main-content">
    <div class="row">
        <div class="col-sm-12">
            <p class="navigation" id="navBreadcrumb">Breadcrumb</p>
        </div>
    </div>
    <form:form method="POST" id="editForm" enctype="multipart/form-data" action="/admin/websitesetting/edit" class="handle-upload" modelAttribute="editForm" >
        <div class="row">
            <div class="col-sm-12">
                <div class="dt-buttons">
                    <button class="btn btn-primary btn-icon btn-icon-small" tabindex="2" onclick="javascript:fire_ajax_submit('/admin/websitesetting')" aria-controls="tableData" title="Cancel" type="button">Cancel</button>
                    <button class="btn btn-primary btn-icon btn-icon-small" tabindex="2" onclick="ajaxformSubmit('editForm');" aria-controls="tableData" title="Save" type="submit">Save</button>
                </div>
            </div>
        </div>
        <%@ include file="../commonFields.jsp" %>
        <div class="row">

            <div class="col-sm-4">
                <form:input path="name" class="inputbox-cheil-small" placeholder="Enter Name" />
                <span>Name</span>
                <form:errors path="name" class="text-danger"></form:errors>
            </div>
            <div class="col-sm-4" >
               Upload Logo<form:input type="file" name="logo" path="logo" accept="image/png"/>
            </div>
            <div class="col-sm-4"  >
                Upload Favicon<form:input type="file" name="favicon" path="favicon" accept="image/png"/>
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