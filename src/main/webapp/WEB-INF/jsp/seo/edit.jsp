<%@ include file="../include.jsp" %>
<div class="main-content">
    <div class="row">
        <div class="col-sm-12">
            <p class="navigation" id="navBreadcrumb">Breadcrumb</p>
        </div>
    </div>
    <form:form method="POST" id="editForm" enctype="multipart/form-data" action="/settings/seo/edit" class="handle-upload" modelAttribute="editForm" >
        <div class="row">
            <div class="col-sm-12">
                <div class="dt-buttons">
                    <button class="btn btn-primary btn-icon btn-icon-small" tabindex="2" onclick="javascript:fire_ajax_submit('/settings/seo')" aria-controls="tableData" title="Cancel" type="button">Cancel</button>
                    <button class="btn btn-primary btn-icon btn-icon-small" tabindex="2" onclick="ajaxformSubmit('editForm');" aria-controls="tableData" type="submit" title="Save">Save</button>
                </div>
            </div>
        </div>
        <%@ include file="../commonFields.jsp" %>
        <div class="row">
            <div class="col-sm-3">
              SEO Image<form:input type="file" path="image" class="inputbox-cheil-small" />
            </div>
            <div class="col-sm-3">
                Meta Title<form:input path="title" class="inputbox-cheil-small" placeholder="Title" />
            </div>
            <div class="col-sm-3">
                Meta Tag<form:input path="tag" class="inputbox-cheil-small" placeholder="Mobile Number" />
                <span>Tag</span>
                <form:errors path="tag" class="text-danger"></form:errors>
            </div>
             <div class="row-sm-4">
                    Site Description<form:input path="description" class="inputbox-cheil-small"  />
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