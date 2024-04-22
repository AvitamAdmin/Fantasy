<%@ include file="../include.jsp" %>
<div class="main-content">
   <div class="row">
      <div class="col-sm-5">
         <p class="navigation" id="navBreadcrumb">Breadcrumb</p>
      </div>
      <div class="col-sm-7"></div>
   </div>
   <div class="row" id="cheil-row">
      <div class="col-sm-5">
         <div class="dt-buttons">
            <button class="btn btn-primary btn-icon btn-icon-small" title="Shortcuts" id="import" tabindex="0" aria-controls="tableData" type="button">Shortcuts</button>
            <button class="btn btn-primary btn-icon btn-icon-small" title="Refresh" tabindex="0" aria-controls="tableData" type="button">Refresh</button>
         </div>
      </div>
      <div class="col-sm-7"></div>
   </div>
   <div id="myModal" class="modal fade" tabindex="-1">
      <div class="modal-dialog">
         <div class="modal-content">
            <div class="modal-header">
                Run Test
               <button type="button" class="close" data-dismiss="modal">&times;</button>
            </div>
            <div class="modal-body">
               <form:form method="POST" id="editForm" enctype="multipart/form-data" action="/qa/testrun" class="handle-upload" modelAttribute="editForm" >
                  <div class="row">
                       <div class="col-sm-12">
                      <select class="cheil-select" name="testPlan" id="selectPlan" placeholder="Select Test Plan" required="required">
                      <span>Select Test Plan</span>
                      <option value="" >Select Test Plan</option>
                         <c:forEach items="${testPlans}" var="child">
                            <option value="${child.id}" >${child.identifier}</option>
                         </c:forEach>
                      </select>
                       </div>
                    </div>
                    <br/>
                    <div class="row">
                       <div class="col-sm-12">
                      <select class="cheil-select" name="testProfile" id="selectProfile" placeholder="Select Test Profile" required="required">
                      <span>Select Test Profile</span>
                        <option value="" >Select Test Profile</option>
                      </select>
                       </div>
                    </div>
                    <br/>
                  </br>
                    Select from the default Products list
                    <div class="row">
                    <div class="col-sm-6">
                    <label class="switch">
                      <input type="checkbox" name="defaultSku" id="defaultSku">
                      <span class="slider round"></span>
                    </label>
                    </div>
                    <div class="col-sm-6"></div>
                    </div>
                    </br>
                    <div class="row" id="enterSku">
                         <div class="col-sm-7">
                            <textarea id="skus" class="inputbox-cheil-textarea-report" name="skus" rows="10" cols="53" placeholder="Please enter Products"></textarea>
                            <span class="searchtext">Please enter SKU's</span>
                            <form:errors path="skus" class="text-danger"></form:errors>
                         </div>
                         <div class="col-sm-5"></div>
                      </div>
                  <div class="row" id="productsDiv" style="display:none;">
                     <div class="col-sm-12">
                        <select name="skus" class="expanded" placeholder="Select Products" multiple id="productsVal">
                         <c:forEach items="${products}" var="child">
                            <option value="${child.code}" >${child.code}</option>
                         </c:forEach>
                        </select>
                     </div>
                  </div>
                  <br/>
                  <br/>
                     <div class="row">
                        <div style="display:none;" id="submitButton" class="col-sm-12">
                            <button class="btn btn-primary add-more" style="float:right;"  aria-controls="tableData" onclick="submitFormById('#editForm');"  type="button"><i class="glyphicon glyphicon-add"></i>Submit</button>
                        </div>
                  </div>
               </form:form>
         </div>
      </div>
   </div>
</div>
<script type="text/javascript">
   $("#defaultSku").on( "click", function() {
     // for a specific one, but you can add a foreach cycle if you need more
     if($('#defaultSku').is(":checked")){
         $("#productsDiv").show();
         $("#enterSku").hide();
     }else {
         $("#productsDiv").hide();
         $("#enterSku").show();
     }
   });

   $("#import").click(function(){
   $("#myModal").modal('show');
   });

   $("#selectProfile").change(function(){
   var profileId = $(this).val();
   if(profileId!=''){
    $('#submitButton').show();
   }
   });

    $("#selectPlan").change(function(){
                var planId = $(this).val();
                var slctSubcat = $('#selectProfile'), options="<option value='' >Select Test Profile</option>";
                slctSubcat.html(options);
                $('#submitButton').hide();
                if(planId!=''){
                $.ajax({
                    type: 'GET',
                    url: "/qa/getTestProfiles/" + planId,
                    datatype: "json",
                    success: function(data){

                    slctSubcat.empty();
                      for(var i=0; i<data.length; i++){
                          options = options + "<option value='"+data[i].id + "'>"+data[i].identifier + "</option>";
                      }
                     slctSubcat.append(options);
                    },
                    error:function(e){
                        console.log(e.statusText);
                    }
                });
                }
            });
</script>
</div>