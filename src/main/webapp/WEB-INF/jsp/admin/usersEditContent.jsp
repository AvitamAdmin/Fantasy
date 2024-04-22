<%@ include file="../include.jsp" %>
<div class="main-content">
    <div class="row">
        <div class="col-sm-12">
            <p class="navigation" id="navBreadcrumb">Breadcrumb</p>
        </div>
    </div>
    <form:form method="POST" id="editForm" enctype="multipart/form-data" action="/admin/user/edit" class="handle-upload" modelAttribute="editForm" >
        <form:input path="id" type="hidden" />
        <div class="row">
            <div class="col-sm-12">
                <div class="dt-buttons">
                    <button class="btn btn-primary btn-icon btn-icon-small" tabindex="2" onclick="javascript:fire_ajax_submit('/admin/user')" aria-controls="tableData" title="Cancel" type="button">Cancel</button>
                    <button class="btn btn-primary btn-icon btn-icon-small" tabindex="2" onclick="submitFormById('#editForm');" aria-controls="tableData" title="Save" type="button">Save</button>
                </div>
            </div>
        </div>
        <%@ include file="../commonFields.jsp" %>
        <div class="row">
            <div class="col-sm-3">
            <form:input path="username" class="inputbox-cheil-small" placeholder="Email" />
            <span>Email</span>
            <form:errors path="username" class="text-danger"></form:errors>
            </div>
            <div class="col-sm-3">
                <form:input path="email" class="inputbox-cheil" placeholder="User Name" />
                <span>User Name</span>
                <form:errors path="email" class="text-danger"></form:errors>
            </div>
            <div class="col-sm-3">
                 <select name="locale"  class="3col active cheil-select">
                    <option value="">Select locale</option>
                    <c:forEach items="${countries}" var="child">
                        <c:choose>
                            <c:when test="${editForm.locale eq child.locale }">
                              <option value="${child.locale}" selected>${child.locale}</option>
                            </c:when>
                            <c:otherwise>
                               <option value="${child.locale}" >${child.locale}</option>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                </select>
            </div>

        </div>
        <br/>
        <br/>

        <div class="row">
        <c:if test="${isAdmin}">
            <div class="col-sm-3 d-flex justify-content-center mt-100">
                <select class="cheil-select" name="roles[]" placeholder="Select Roles" multiple id="selectpicker">
                <span>Select Roles</span>
                    <c:forEach items="${roles}" var="role">
                        <c:choose>
                            <c:when test="${fn:contains( editForm.roles, role ) }">
                              <option value="${role.id}" selected>${role.name}</option>
                            </c:when>
                            <c:otherwise>
                               <option value="${role.id}" >${role.name}</option>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                </select>
            </div>
            </c:if>
            <div class="col-sm-3">
                <form:input type="password" path="password" class="inputbox-cheil-small" placeholder="Enter Password" />
                <span>Password</span>

                </div>
            <div class="col-sm-3">
                <form:input type="password" path="passwordConfirm" class="inputbox-cheil" placeholder="Confirm Password" />
             <span>Confirm Password</span>
            </div>
        </div>

        <script type="text/javascript">
            $(document).ready(function() {
                var multipleCancelButton = new Choices('#selectpicker', {
                        removeItemButton: true,
                        maxItemCount:10,
                        searchResultLimit:10,
                        renderChoiceLimit:10
                      });
                var multipleCancelButton = new Choices('#selectpicker2', {
                        removeItemButton: true,
                        maxItemCount:10,
                        searchResultLimit:10,
                        renderChoiceLimit:10
                      });
            });
        </script>

       </form:form>
       <c:if test="${not empty message}">
           <div class="alert alert-danger" role="alert" id="errorMessage">
               <spring:message code="${message}" />
           </div>
       </c:if>
    </div>
  </div>
</div>