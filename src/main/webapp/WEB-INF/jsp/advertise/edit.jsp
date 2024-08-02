<%@ include file="../include.jsp" %>
<div class="main-content">
    <div class="row">
        <div class="col-sm-12">
            <p class="navigation" id="navBreadcrumb">Breadcrumb</p>
        </div>
    </div>
    <form:form method="POST" id="editForm" enctype="multipart/form-data" action="/advertise/banner/edit" class="handle-upload" modelAttribute="editForm" >
        <div class="row">
            <div class="col-sm-12">
                <div class="dt-buttons">
                    <button class="btn btn-primary btn-icon btn-icon-small" tabindex="2" onclick="javascript:fire_ajax_submit('/advertise/banner')" aria-controls="tableData" title="Cancel" type="button">Cancel</button>
                    <button class="btn btn-primary btn-icon btn-icon-small" tabindex="2" onclick="ajaxformSubmit('editForm');" aria-controls="tableData" title="Save" type="submit">Save</button>
                </div>
            </div>
        </div>
        <%@ include file="../commonFields.jsp" %>
        <div class="row">
             <div class="col-sm-4" >
               Banner Image<form:input type="file" name="image" path="image" accept="image/png"/>
             </div>
             <div class="col-sm-4">
                  <select class="cheil-select" name="size">
                     <option value="">Banner Size</option>
                     <c:forEach items="${sizes}" var="size">
                         <c:choose>
                             <c:when test="${fn:contains( editForm.size, size.size ) }">
                               <option value="${size.size}" selected>${size.size}</option>
                             </c:when>
                             <c:otherwise>
                                <option value="${size.size}" >${size.size}</option>
                             </c:otherwise>
                         </c:choose>
                     </c:forEach>
                 </select>
             </div>
            <div class="col-sm-4">
                <form:input path="url" class="inputbox-cheil-small" placeholder="Redirect URL" />
                <span>Redirect URL</span>
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