<%@ include file="../include.jsp" %>
<div class="main-content">
<div class="row">
        <div class="col-sm-12">
            <p class="navigation" id="navBreadcrumb">Breadcrumb</p>
        </div>
    </div>
    <form:form method="POST" id="roleForm" enctype="multipart/form-data" action="/admin/role/edit" class="handle-upload" modelAttribute="roleForm" >
        <div class="row">
            <div class="col-sm-12">
                <div class="dt-buttons">
                    <button class="btn btn-primary btn-icon btn-icon-small" tabindex="2" onclick="javascript:fire_ajax_submit('/admin/role')" aria-controls="tableData" title="Cancel" type="button">Cancel</button>
                    <button class="btn btn-primary btn-icon btn-icon-small" tabindex="2" onclick="ajaxformSubmit('roleForm');" aria-controls="tableData" title="Save" type="submit">Save</button>
                </div>
            </div>
        </div>
        <%@ include file="../commonFields.jsp" %>
        <div class="row">
            <div class="col-sm-3">
                <form:input path="name" class="inputbox-cheil-small" placeholder="Name" required="required"/>
                <span>Name</span>
                <form:errors path="name" class="text-danger"></form:errors>
            </div>
            <div class="col-sm-6"></div>
        </div>
        <br/></br>
<c:set var="count" value="2" scope="page" />
                                     <c:forEach items="${nodes}" var="child">
                                     <c:if test="${child.parentNode == null}">

                                     <c:if test="${count % 2 == 0}">
                                     <div class="row">
                                                 <div class="col-sm-6">
                                             </c:if>
                                             <c:if test="${count %2 != 0}">
                                                 <div class="col-sm-6">
                                             </c:if>

                                     <select name="permissions[]" placeholder="Select permissions" multiple id="selectpickerperm${count}">
                                     <optgroup label="${child.name}">
                                       <c:forEach items="${child.childNodes}" var="childNode">
                                       <c:set var="displayValue">${fn:replace(childNode.name,"%","")}</c:set>
                                       <c:set var="displayValue">${fn:replace(displayValue,"(","")}</c:set>
                                       <c:set var="displayValue">${fn:replace(displayValue,")","")}</c:set>
                                       <c:choose>
                                             <c:when test="${fn:contains( roleForm.permissions, childNode )}">
                                               <option value="${childNode.id}" selected>${displayValue}</option>
                                             </c:when>
                                             <c:otherwise>
                                                <option value="${childNode.id}" >${displayValue}</option>
                                             </c:otherwise>
                                         </c:choose>

                                       </c:forEach>
                                        </optgroup>
                                        </select>
                                        <c:if test="${count % 2 == 0}">
                                         </div>

                                         </c:if>
                                         <c:if test="${count %2 != 0}">
                                             </div>
                                             </div>
                                             </br>
                                              </br>
                                         </c:if>
                                        <c:set var="count" value="${count + 1}" scope="page"/>
                                        </c:if>
                                        </c:forEach>


       </form:form>
       <c:if test="${not empty message}">
           <div class="alert alert-danger" role="alert" id="errorMessage">
               <spring:message code="${message}" />
           </div>
       </c:if>
  </div>
</div>
<script type="text/javascript">
    $(document).ready(function() {
         $('[id*="selectpicker"]').multiselect({
                    enableClickableOptGroups: true,
                    enableCollapsibleOptGroups: true,
                    collapseOptGroupsByDefault:true,
                    enableFiltering: true,
                    includeSelectAllOption: true,
                    buttonWidth: '100%',
                    includeResetOption: true,
                    includeResetDivider: true,
                    enableFiltering: true,
                    resetText: "Reset all",
                    dropUp: true,
                    maxHeight: 300,
                    enableHTML: true,
                    buttonText: function(options, select) {
                            var $select = $(select);
                            var $optgroups = $('optgroup', $select);
                                    if (options.length === 0) {
                                        return 'Select permissions';
                                    }
                                     else {
                                        var text='';
                                         $optgroups.each(function() {
                                              var $selectedOptions = $('option:selected', this);
                                              var $options = $('option', this);
                                              if ($selectedOptions.length == $options.length) {
                                                  text += '<span class="boot-choices">' + $(this).attr('label') + '|' + 'All Nodes ' + '</span>';
                                              }
                                              else if($selectedOptions.length > 0) {
                                                  text += '<span class="boot-choices">' + $(this).attr('label') + '|';
                                                  $selectedOptions.each(function() {
                                                      text += $(this).text() + ',';
                                                  });
                                                  text = text.substr(0, text.length - 1);
                                                  text += '</span>';
                                              }
                                          });
                                          return text;
                                     }
                                }
                });

    });

</script>
