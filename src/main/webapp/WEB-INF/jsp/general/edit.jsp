<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
    <head>
         <link rel="stylesheet" type="text/css" href="<spring:theme code='backgroundStyle'/>">
         <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css" />
         <meta name="viewport" content="width=device-width, initial-scale=1">
         <style>
            body {
               padding: 25px;
               background-color: white;
               color: black;
               font-size: 25px;
            }
            .header {
               padding: 15px;
               background-color: white;
               color: black;
            }
            .dark-mode {
               background-color: black;
               color: white;
            }
         </style>
    </head>
   <body>
      <div class="row">
         <div  class="col-sm-12" style="text-align: right;">
            <button onclick="myFunction()"><i class="fas fa-moon"></i></button>
         </div>
      </div>

      <form:form method="POST" id="editForm" enctype="multipart/form-data" action="${pageContext.request.contextPath}/settings/general/edit" modelAttribute="editForm" accept-charset="UTF-8" class="forms-sample">
         <div class="card-body">
            <div class="row">
               <div class="col-sm-4">
                  <div class="form-group">
                     <label>Site Title</label>
                     <form:input path="siteTitle" placeholder="Title" class="form-control" type="text" />
                  </div>
               </div>
               <div class="col-sm-4">
                  <div class="form-group">
                     <label>Base Currency</label>
                     <form:input path="currency" placeholder="Currency" class="form-control" type="text" value="Rupees" />
                  </div>
               </div>
               <div class="col-sm-4">
                  <div class="form-group">
                     <label>TimeZone</label>
                        <select path="timeZone" class="cheil-select">
                        <option value="India" label="India" />
                        <option value="SriLanka" label="SriLanka" />
                        <option value="Singapore" label="Singapore" />
                     </select>
                  </div>
               </div>
               <div class="col-sm-4">
                  <div class="form-group">
                     <label>Copy Right Text</label>
                     <form:input path="copyRight"  class="form-control" value="All Rights Reserved" />
                  </div>
               </div>
               <div class="col-sm-4">
                  <div class="form-group">
                     <label>Base Color</label>
                     <input type="color" class="form-control" id="bgColor" name="bgColor" value="#000fff" onchange="changeBackgroundColor(this.value)">
                     <input type="hidden" id="selectedColor" name="selectedColor">
                  </div>
               </div>
               <div class="col-sm-4"></div>

               <div class="col-lg-6">
                  <div class="form-group">
                     <label>Comments</label>
                     <textarea path="comments" placeholder="Title" class="form-control" cols="50" rows="10"></textarea>
                  </div>
               </div>
               <div class="col-lg-6">
                  <div class="form-group">
                     <label>Footer Text</label>
                     <textarea path="footerText" placeholder="Title" class="form-control" cols="50" rows="10"></textarea>
                  </div>
               </div>
            </div>
            <div class="text-center">
               <div class="row">
                  <div class="col-sm-12">
                     <div class="dt-buttons">
                        <button class="btn btn-primary btn-icon btn-icon-small" type="button" onclick="location.href='${pageContext.request.contextPath}/settings/general'">Cancel</button>
                        <button class="btn btn-primary btn-icon btn-icon-small" type="submit">Save</button>
                     </div>
                  </div>
               </div>
            </div>
         </div>
      </form:form>
   </body>
   <script>
      function myFunction() {
         var element = document.body;
         element.classList.toggle("dark-mode");
         if (element.classList.contains("dark-mode")) {
            localStorage.setItem("theme", "dark");
         } else {
            localStorage.setItem("theme", "light");
         }
      }

      window.onload = function() {
         if (localStorage.getItem("theme") === "dark") {
            document.body.classList.add("dark-mode");
         }
      }
   </script>
     <script>
            function changeBackgroundColor(color) {
               document.body.style.backgroundColor = color;
               document.getElementById('selectedColor').value = color;
            }
         </script>
</html>
