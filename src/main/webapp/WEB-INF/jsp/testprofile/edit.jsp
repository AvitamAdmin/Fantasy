<%@ include file="../include.jsp" %>
<div class="main-content">
    <div class="row">
        <div class="col-sm-12">
            <p class="navigation" id="navBreadcrumb">Breadcrumb</p>
        </div>
    </div>
    <form:form method="POST" id="editForm" enctype="multipart/form-data" action="/admin/testprofile/edit" class="handle-upload" modelAttribute="editForm" >
        <div class="row">
            <div class="col-sm-12">
                <div class="dt-buttons">
                    <button class="btn btn-primary btn-icon btn-icon-small" tabindex="2" onclick="javascript:fire_ajax_submit('/admin/testprofile')" aria-controls="tableData" title="Cancel" type="button">Cancel</button>
                    <button class="btn btn-primary btn-icon btn-icon-small" tabindex="2" onclick="ajaxformSubmit('editForm');" aria-controls="tableData" type="submit" title="Save">Save</button>
                </div>
            </div>
        </div>
        <%@ include file="../commonFields.jsp" %>
        <div class="row">
            <div class="col-sm-3">
                <form:input path="identifier" class="inputbox-cheil-small" placeholder="Enter Id" required="required" />
                <span>Enter Id</span>
                <form:errors path="identifier" class="text-danger"></form:errors>
            </div>
            <div class="col-sm-3">
                <form:input path="shortDescription" class="inputbox-cheil-long" placeholder="Enter Description" />
                <span>Enter Description</span>
                <form:errors path="shortDescription" class="text-danger"></form:errors>
            </div>
            <div class="col-sm-3">
                 <select name="testPlan" class="3col active cheil-select">
                    <option value="">Select a test plan</option>
                    <c:forEach items="${testPlans}" var="child">
                        <c:choose>
                            <c:when test="${editForm.testPlan == child.id}">
                              <option value="${child.id}" selected>${child.identifier}</option>
                            </c:when>
                            <c:otherwise>
                               <option value="${child.id}" >${child.identifier}</option>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                </select>
            </div>
        </div>
        <br/><br/>
        <div class="row">
            <div class="col-sm-6">
                 <form:textarea path="skus" id="skus" rows="6" cols="30" class="inputbox-cheil-textarea" placeholder="please enter SKU's"/>
                 <span class="searchtext">Please enter SKU's</span>
            </div>
        </div>
        <br/><br/>
        <c:forEach items="${editForm.paramInput}" var="child" varStatus="loop">
                <c:if test="${child!=''}">
                <div class="row"  id="inputParamRow${loop.index}">
                                        <div class="col-sm-4 control-group input-group">
                                        <input readonly="true" name="paramInput[${loop.index}].paramKey" value="${editForm.paramInput[loop.index].paramKey}" class="inputbox-cheil-small" placeholder="Key" />
                                        </div>
                                        <div class="col-sm-4 control-group input-group">
                                        <input readonly="true" name="paramInput[${loop.index}].paramValue" value="${editForm.paramInput[loop.index].paramValue}" class="inputbox-cheil-small" placeholder="Value" />
                                        </div>
                                         <div class="col-sm-1"> <img style="width:32px;height:32px;" onclick="javascript:removeRow(${loop.index})" src="${contextPath}/images/remove.png" /></div>

                                    </div>
                                               </br>
                                    </c:if>
                                    </c:forEach>
                            <div class="input-group control-group after-add-input-more">
                                <button class="btn btn-primary add-input-more" type="button"><i class="glyphicon glyphicon-add"></i>Add an input field</button>
                             </div>
                             <input id="existingParamsCount" name="existingParamsCount" value="${existingParamsCount}" style="display:none;">

       </form:form>
       <c:if test="${not empty message}">
           <div class="alert alert-danger" role="alert" id="errorMessage">
               <spring:message code="${message}" />
           </div>
       </c:if>
    </div>
  </div>
</div>
<script type="text/javascript">

    $(document).ready(function() {
       var count = $('#existingParamsCount').val();
      $(".add-more").click(function(){
          var html = $(".copy").html();
          $(".after-add-more").before(html);
      });

      $(".add-input-more").click(function(){
            count++;
                var html = '<div class="row" id="inputParamRow' + count + '"><div class="col-sm-3 cheil-select"><select name="paramInput['+count+'].paramKey" onchange="javascript:inputFormatChange(this.value, '+count+')" class="3col active"><option value="" selected>Select a test data type</option><c:forEach items="${dataMap}" var="dataMapVar"><option value="${dataMapVar.key}">${dataMapVar.key}</option></c:forEach></select></div><div class="col-sm-3 cheil-select" id="valueInputDiv'+count+'"><select name="paramInput['+count+'].paramValue" id="valueInput'+count+'"  class="3col active"></select></div><div class="col-sm-1"> <img style="width:32px;height:32px;" onclick="javascript:removeRow('+count+')" src="${contextPath}/images/remove.png" /></div></div></br>'
                $(".after-add-input-more").before(html);
            });

      $("body").on("click",".remove",function(){
          $(this).parents(".control-group").remove();
      });

      $(document).on("click","#inputParamRow",function(){
                $(this).remove();
            });

    });

    function inputFormatChange(value, countVal) {

    if(value!=''){


             $.ajax({
                 type: 'GET',
                 url: "/admin/testprofile/getValuesByKey/" + value,
                 datatype: "json",
                 success: function(data){
                 var toAppend='<option value="">Select the test data</option>';
                   for(var i=0; i<data.length; i++){
                          toAppend+='<option value="'+data[i].identifier+'">'+ data[i].identifier +'</option>'
                   }
                   $('#valueInput'+countVal).empty().append(toAppend);
                 },
                 error:function(e){
                     console.log(e.statusText);
                 }
             });
             }
        }



  function removeRow(countVal) {
    $('#inputParamRow'+countVal).remove();
  }

</script>