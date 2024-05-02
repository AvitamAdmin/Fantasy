<div class="main-content">
  <div class="row">
    <form:form method="POST" modelAttribute="userForm" class="handle-uploadn">
      <br/>
      <div class="row">
              <div class="col-sm-3"></div>
              <div style="text-align:center;" class="col-sm-6">
                <img style="width:150px;" src="${contextPath}/images/logo.png"/>
              </div>
              <div  class="col-sm-3"></div>
      </div>
      <br/><br/>
        <spring:bind path="name">
        <div class="row form-group ${status.error ? 'has-error' : ''}">
            <div class="col-sm-3"></div>
            <div class="col-sm-6">
                <form:input type="email" path="name" class="inputbox-cheil" placeholder="Username"
                            autofocus="true" required="required"></form:input>
                            <span>Username</span>
                <form:errors path="name" class="text-danger"></form:errors>
            </div>
            <div class="col-sm-3"></div>
        </div>
        </spring:bind>

        <spring:bind path="password">
            <div class="row form-group ${status.error ? 'has-error' : ''}">
                <div class="col-sm-3"></div>
                <div class="col-sm-6">
                    <form:input type="password" path="password" class="inputbox-cheil-small" placeholder="Password"></form:input>
                    <span>Password</span>
                    <form:errors path="password" class="text-danger"></form:errors>
                </div>
                <div class="col-sm-3"></div>
            </div>
        </spring:bind>

        <spring:bind path="passwordConfirm">
            <div class="row form-group ${status.error ? 'has-error' : ''}">
                <div class="col-sm-3"></div>
                <div class="col-sm-6">
                    <form:input type="password" path="passwordConfirm" class="inputbox-cheil-long"
                            placeholder="Confirm Password" required="required"></form:input>
                            <span>Confirm Password</span>
                    <form:errors path="passwordConfirm" class="text-danger"></form:errors>
                </div>
                <div class="col-sm-3"></div>
            </div>
        </spring:bind>
         <br/>
        <spring:bind path="referredBy">
                <div class="row form-group ${status.error ? 'has-error' : ''}">
                    <div class="col-sm-3"></div>
                    <div class="col-sm-6">
                        <form:input type="text" path="referredBy" class="inputbox-cheil" placeholder="Referred By" required="required"></form:input>
                                    <span>Referred By</span>
                        <form:errors path="referredBy" class="text-danger"></form:errors>
                    </div>
                    <div class="col-sm-3"></div>
                </div>
                </spring:bind>
                <br/>
        <div class="row">
        <div class="col-sm-3"></div>
            <div class="col-sm-6" style="font-size:12px;">Select the role you would like to enroll</div>
        </div>

        <spring:bind path="role">
                <div class="row form-group ${status.error ? 'has-error' : ''}">
                    <div class="col-sm-3"></div>
                    <div class="col-sm-6">
                        <form:input type="text" path="role" class="inputbox-cheil" placeholder="Role" required="required"></form:input>
                                    <span>Role</span>
                        <form:errors path="role" class="text-danger"></form:errors>
                    </div>
                    <div class="col-sm-3"></div>
                </div>
                </spring:bind>
        <br/>
        <spring:bind path="mobileNumber">
        <div class="row form-group ${status.error ? 'has-error' : ''}">
            <div class="col-sm-3"></div>
            <div class="col-sm-6">
                <form:input type="text" path="mobileNumber" class="inputbox-cheil" placeholder="Mobile Number" required="required"></form:input>
                            <span>Mobile Number</span>
                <form:errors path="mobileNumber" class="text-danger"></form:errors>
            </div>
            <div class="col-sm-3"></div>
        </div>
        </spring:bind>

        <spring:bind path="dateOfBirth">
        <div class="row form-group ${status.error ? 'has-error' : ''}">
            <div class="col-sm-3"></div>
            <div class="col-sm-6">
                <form:input type="text" path="dateOfBirth" class="inputbox-cheil" placeholder="Date Of Birth" required="required"></form:input>
                            <span>Date Of Birth</span>
                <form:errors path="dateOfBirth" class="text-danger"></form:errors>
            </div>
            <div class="col-sm-3"></div>
        </div>
        </spring:bind>

        <spring:bind path="gender">
        <div class="row form-group ${status.error ? 'has-error' : ''}">
            <div class="col-sm-3"></div>
            <div class="col-sm-6">
                <form:input type="text" path="gender" class="inputbox-cheil" placeholder="Gender" required="required"></form:input>
                            <span>Gender</span>
                <form:errors path="gender" class="text-danger"></form:errors>
            </div>
            <div class="col-sm-3"></div>
        </div>
        </spring:bind>

        <spring:bind path="language">
        <div class="row form-group ${status.error ? 'has-error' : ''}">
            <div class="col-sm-3"></div>
            <div class="col-sm-6">
                <form:input type="text" path="language" class="inputbox-cheil" placeholder="Language" required="required"></form:input>
                            <span>Language</span>
                <form:errors path="language" class="text-danger"></form:errors>
            </div>
            <div class="col-sm-3"></div>
        </div>
        </spring:bind>

        <br/>
        <br/>
        <div class="row">
        <div class="col-sm-4"></div>
                            <div class="col-sm-4" style="font-size:13px; text-align:center;">By clicking Submit, I agree to the <a href='#'>Terms and Conditions</a> Platform usage and the Privacy Statement.</div>
                        </div>
                        <br/>
        <div class="row">
            <div class="col-sm-3"></div>
            <div class="col-sm-6" style="display:flex;justify-content: center;">
                <button class="btn btn-lg btn-primary btn-block btn-cheil" type="submit">Submit</button>
            </div>
            <div class="col-sm-3"></div>
        </div>
      </form:form>
      <c:if test="${not empty message}">
         <div class="alert alert-danger" role="alert" id="errorMessage">
             <spring:message code="${message}" />
         </div>
     </c:if>
    </div>
  </div>