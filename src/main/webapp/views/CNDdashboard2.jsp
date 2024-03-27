<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding = "UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<!--
Template Name: Safety - Vuejs, HTML & Laravel Admin Dashboard Template
Author: PixInvent
Website: http://www.pixinvent.com/
Contact: hello@pixinvent.com
Follow: www.twitter.com/pixinvents
Like: www.facebook.com/pixinvents
Purchase: https://1.envato.market/vuexy_admin
Renew Support: https://1.envato.market/vuexy_admin
License: You must have a valid license purchased only from themeforest(the above link) in order to legally use the theme for your project.

-->
<html class="loading" lang="en" data-textdirection="ltr">
  <!-- BEGIN: Head--> 
  
<!-- Mirrored from pixinvent.com/demo/vuexy-html-bootstrap-admin-template/html/ltr/horizontal-menu-template/table-datatable-basic.html by HTTrack Website Copier/3.x [XR&CO'2014], Sun, 07 Aug 2022 05:37:16 GMT -->
<head>
   

 	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width,initial-scale=1.0,user-scalable=0,minimal-ui">
    <meta name="description" content="Safety admin is super flexible, powerful, clean &amp; modern responsive bootstrap 4 IRM with unlimited possibilities.">
    <meta name="keywords" content="admin template,IRM, Safety admin template, dashboard template, flat admin template, responsive admin template, web app">
    <meta name="author" content="PIXINVENT">
  <title>CND - Cumulative</title>
       <link rel="icon" type="image/png" sizes="96x96" href="/reelweighbridgess/resources/images/protect-favicon.png" >
	<script src="/reelweighbridgess/resources/js/jQuery-v.3.5.min.js"  ></script>
    <!-- BEGIN: Vendor CSS-->
      <link rel="apple-touch-icon" href="/reelweighbridgess/resources/images/ico/apple-icon-120.html">
	<link href="https://fonts.googleapis.com/css2?family=Montserrat:ital,wght@0,300;0,400;0,500;0,600;1,400;1,500;1,600" rel="stylesheet">
    <!-- BEGIN: Vendor CSS-->
    <link rel="stylesheet" type="text/css" href="/reelweighbridgess/resources/vendors/css/vendors.min.css">
    <link rel="stylesheet" type="text/css" href="/reelweighbridgess/resources/vendors/css/tables/datatable/dataTables.bootstrap5.min.css">
    <link rel="stylesheet" type="text/css" href="/reelweighbridgess/resources/vendors/css/tables/datatable/responsive.bootstrap5.min.css">
    <link rel="stylesheet" type="text/css" href="/reelweighbridgess/resources/vendors/css/tables/datatable/buttons.bootstrap5.min.css">
    <link rel="stylesheet" type="text/css" href="/reelweighbridgess/resources/vendors/css/tables/datatable/rowGroup.bootstrap5.min.css">
    <link rel="stylesheet" type="text/css" href="/reelweighbridgess/resources/vendors/css/pickers/pickadate/pickadate.css">
    <link rel="stylesheet" type="text/css" href="/reelweighbridgess/resources/vendors/css/pickers/flatpickr/flatpickr.min.css">
        <link rel="stylesheet" type="text/css" href="/reelweighbridgess/resources/vendors/css/extensions/toastr.min.css">
    <link rel="stylesheet" type="text/css" href="/reelweighbridgess/resources/css/plugins/extensions/ext-component-toastr.min.css">
    <!-- END: Vendor CSS-->
 <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.1.1/css/all.min.css">
	  <link rel="stylesheet" href="<c:url value="/resources/css/font-awesome-v.4.7.css" />">
    <!-- BEGIN: Theme CSS-->
     <link rel="stylesheet" type="text/css" href="<c:url value="/resources/vendors/css/extensions/sweetalert2.min.css"/>">
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/plugins/extensions/ext-component-sweet-alerts.min.css"/>">
    
    <link rel="stylesheet" type="text/css" href="/reelweighbridgess/resources/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="/reelweighbridgess/resources/css/bootstrap-extended.min.css">
    <link rel="stylesheet" type="text/css" href="/reelweighbridgess/resources/css/colors.min.css">
    <link rel="stylesheet" type="text/css" href="/reelweighbridgess/resources/css/components.min.css">
    <link rel="stylesheet" type="text/css" href="/reelweighbridgess/resources/css/themes/dark-layout.min.css">
    <link rel="stylesheet" type="text/css" href="/reelweighbridgess/resources/css/themes/bordered-layout.min.css">
    <link rel="stylesheet" type="text/css" href="/reelweighbridgess/resources/css/themes/semi-dark-layout.min.css">
   <link rel="stylesheet" type="text/css" href="/reelweighbridgess/resources/vendors/css/forms/select/select2.min.css">
    <!-- BEGIN: Page CSS-->
    <link rel="stylesheet" type="text/css" href="/reelweighbridgess/resources/css/core/menu/menu-types/horizontal-menu.min.css">
        <link rel="stylesheet" type="text/css" href="/reelweighbridgess/resources/css/plugins/forms/pickers/form-flat-pickr.min.css">
    <link rel="stylesheet" type="text/css" href="/reelweighbridgess/resources/css/plugins/forms/pickers/form-pickadate.min.css">
    <!-- END: Page CSS-->

    <!-- BEGIN: Custom CSS-->
    <link rel="stylesheet" type="text/css" href="/reelweighbridgess/resources/css/style.css">
    <!-- END: Custom CSS-->
<style>
.form-check {
    padding-left: 0.785rem !important;
}
.mdl-grid{
	display: flex !important;
    padding: 4px;
    justify-content: space-between;
    height: 4.5rem;
} 
.dt-table{
height: 100%;
}
.modal-content {
 width: 85% !important;
}
.modal {
    width: 100% !important;
}
.required{
	color:red;
}
.my-error-class {
 	 color:red;
}
.my-valid-class {
 	 color:green;
}
.select2-container--default .select2-selection--single .select2-selection__arrow b {
     left: -25% !important;
    margin-top: 1p% !important;
}
body {
    font-family: var(--bs-body-font-family) !important;
}
.dark-layout h1, .dark-layout h2, .dark-layout h3, .dark-layout h4, .dark-layout h5, .dark-layout h6, .dark-layout span  {
    color: #D0D2D6;
}
.select2-container--classic .select2-selection--single .select2-selection__arrow b, .select2-container--default .select2-selection--single .select2-selection__arrow b {
    background-image: url(data:image/svg+xml,%3Csvg xmlns=\'http://www.w3.org/2000/svg\' viewBox=\'0 0 24 24\' fill=\'none\' stroke=\'%23d8d6de\' stroke-width=\'2\' stroke-linecap=\'round\' stroke-linejoin=\'round\' class=\'feather feather-chevron-down\'%3E%3Cpolyline points=\'6 9 12 15 18 9\'%3E%3C/polyline%3E%3C/svg%3E);
    background-size: 18px 14px,18px 14px !important;
    background-repeat: no-repeat !important;
    height: 1rem !important;
    padding-right: 1.5rem !important;
    margin-left: 0 !important;
    margin-top: 0 !important;
    left: -8px !important;
    border-style: none !important;
}
/* td {
    border: 1px solid #000;
} */

tr td:last-child {
    width: 1%;
    white-space: nowrap;
}
.page-loader{
		    background: #332e2ec2!important;
		    position: fixed;
		    width: 100%;
		    height: 100%;
		    top: 0;
		    left: 0;
		    z-index: 1000;
		}
::placeholder { /* Chrome, Firefox, Opera, Safari 10.1+ */
  color: black;
  opacity: 1; /* Firefox */
}
</style>
  </head>
  <!-- END: Head-->

  <!-- BEGIN: Body-->
  <body class="horizontal-layout horizontal-menu  navbar-floating footer-static  " data-open="hover" data-menu="horizontal-menu" data-col="">

    <!-- BEGIN: Header-->
	<jsp:include page="../views/layout/header.jsp"></jsp:include> 

    <!-- END: Header-->
    <!-- BEGIN: Main Menu-->
    <!-- END: Main Menu-->

    <!-- BEGIN: Content-->
    <div class="app-content content ">
      <div class="content-overlay"></div> 
      <div class="header-navbar-shadow"></div>
      <div class="content-wrapper container-xxl p-0">

        <div class="content-body"><!-- Dashboard Analytics Start -->
       
      
<section id="dashboard-analytics" >

 
</section>
  	
  </div>

  	
 			

 							

           
  </div>				
 																					
   <div class="page-loader" style="display: none;">
	  <div class="preloader-wrapper big active">
	    <div class="spinner-layer spinner-blue-only">
	      <div class="circle-clipper left">
	        <div class="circle"></div>
	      </div><div class="gap-patch">
	        <div class="circle"></div>
	      </div><div class="circle-clipper right">
	        <div class="circle"></div>
	      </div>
	    </div>
	  </div>
	</div> 
 																					
 																					
<!-- Dashboard Analytics end -->

        </div>
      </div>
    </div>


    <div class="sidenav-overlay"></div>
    <div class="drag-target"></div>

    <!-- BEGIN: Footer-->
    <footer class="footer footer-static footer-light">
      <p class="clearfix mb-0"><span class="float-md-start d-block d-md-inline-block mt-25">COPYRIGHT  &copy;  <span id="currentYear"></span> , All Rights Reserved | Powered by<a class="ms-25" href="https://resustainability.com/" target="_blank">Re Sustainability Limited</a><span class="d-none d-sm-inline-block"> .</span></span></p>
    </footer>
    <button class="btn btn-danger btn-icon scroll-top" type="button"><i data-feather="arrow-up"></i></button>
    <!-- END: Footer-->

 
    <!-- BEGIN: Vendor JS-->
    <script src="/reelweighbridgess/resources/vendors/js/vendors.min.js"></script>
    <!-- BEGIN Vendor JS-->

    <!-- BEGIN: Page Vendor JS-->
    <script src="/reelweighbridgess/resources/vendors/js/ui/jquery.sticky.js"></script>
    <script src="/reelweighbridgess/resources/vendors/js/forms/select/select2.full.min.js"></script>
    <script src="/reelweighbridgess/resources/vendors/js/charts/apexcharts.min.js"></script>
    <script src="/reelweighbridgess/resources/vendors/js/extensions/toastr.min.js"></script>
    <script src="/reelweighbridgess/resources/vendors/js/extensions/moment.min.js"></script>
    <script src="/reelweighbridgess/resources/vendors/js/tables/datatable/jquery.dataTables.min.js"></script>
    <script src="/reelweighbridgess/resources/vendors/js/tables/datatable/datatables.buttons.min.js"></script>
    <script src="/reelweighbridgess/resources/vendors/js/tables/datatable/dataTables.bootstrap5.min.js"></script>
    <script src="/reelweighbridgess/resources/vendors/js/tables/datatable/dataTables.responsive.min.js"></script>
    <script src="/reelweighbridgess/resources/vendors/js/tables/datatable/responsive.bootstrap5.js"></script>
    <!-- END: Page Vendor JS-->
 <script src="/reelweighbridgess/resources/js/materialize-v.1.0.min.js "  ></script>
    <script src="/reelweighbridgess/resources/js/jquery-validation-1.19.1.min.js"  ></script>
    <script src="/reelweighbridgess/resources/js/jquery.dataTables-v.1.10.min.js"  ></script>
     <script src="/reelweighbridgess/resources/js/datetime-moment-v1.10.12.js"  ></script>
         <script src="/reelweighbridgess/resources/js/dataTables.material.min.js"  ></script>
      <script src="/reelweighbridgess/resources/js/moment-v2.8.4.min.js"  ></script>
    <!-- BEGIN: Theme JS-->
    <script src="/reelweighbridgess/resources/js/core/app-menu.min.js"></script>
    <script src="/reelweighbridgess/resources/js/core/app.min.js"></script>
    <script src="/reelweighbridgess/resources/js/scripts/customizer.min.js"></script>
     <script src="/reelweighbridgess/resources/js/scripts/forms/form-select2.min.js"></script>
    <!-- END: Theme JS-->
   <script src="/reelweighbridgess/resources/js/scripts/pages/modal-add-new-cc.min.js"></script>
    <script src="/reelweighbridgess/resources/js/scripts/pages/page-pricing.min.js"></script>
    <script src="/reelweighbridgess/resources/js/scripts/pages/modal-add-new-address.min.js"></script>
    <script src="/reelweighbridgess/resources/js/scripts/pages/modal-create-app.min.js"></script>
    <script src="/reelweighbridgess/resources/js/scripts/pages/modal-two-factor-auth.min.js"></script>
    <script src="/reelweighbridgess/resources/js/scripts/pages/modal-edit-user.min.js"></script>
    <script src="/reelweighbridgess/resources/js/scripts/pages/modal-share-project.min.js"></script>
    <!-- BEGIN: Page JS-->
     <script src="/reelweighbridgess/resources/js/scripts/pages/dashboard-analytics.min.js"></script>
    <script src="/reelweighbridgess/resources/js/scripts/pages/app-invoice-list.min.js"></script>
    
        <script src="/reelweighbridgess/resources/vendors/js/pickers/pickadate/picker.js"></script>
    <script src="/reelweighbridgess/resources/vendors/js/pickers/pickadate/picker.date.js"></script>
    <script src="/reelweighbridgess/resources/vendors/js/pickers/pickadate/picker.time.js"></script>
    <script src="/reelweighbridgess/resources/vendors/js/pickers/pickadate/legacy.js"></script>
    <script src="/reelweighbridgess/resources/vendors/js/pickers/flatpickr/flatpickr.min.js"></script>
     <script src="/reelweighbridgess/resources/js/scripts/forms/pickers/form-pickers.min.js"></script>
     <script src="/reelweighbridgess/resources/js/scripts/extensions/ext-component-blockui.js"></script>
      <script src="/reelweighbridgess/resources/js/scripts/pages/dashboard-ecommerce.min.js"></script>
        <script src="/reelweighbridgess/resources/js/scripts/components/components-popovers.min.js"></script>
    <!-- END: Page JS-->
 
    <script>
    $('#InitiateIRM').on('show.bs.modal', function (event) {
        $(document).ready(function() {
            $('.select2').select2({
                dropdownParent: $('#InitiateIRM')
            });
        }); 
    });


	    function getErrorMessage(jqXHR, exception) {
	  	    var msg = '';
	  	    if (jqXHR.incident_type === 0) {
	  	        msg = 'Not connect.\n Verify Network.';
	  	    } else if (jqXHR.incident_type == 404) {
	  	        msg = 'Requested page not found. [404]';
	  	    } else if (jqXHR.incident_type == 500) {
	  	        msg = 'Internal Server Error [500].';
	  	    } else if (exception === 'parsererror') {
	  	        msg = 'Requested JSON parse failed.';
	  	    } else if (exception === 'timeout') {
	  	        msg = 'Time out error.';
	  	    } else if (exception === 'abort') {
	  	        msg = 'Ajax request aborted.';
	  	    } else {
	  	        msg = 'Uncaught Error.\n' + jqXHR.responseText;
	  	    }
	  	    console.log(msg);
        }

	    function empFilter(project){
	    	    if ($.trim(project) != "" ) {
		        	$("#select2-employee_code_add-container option:not(:first)").remove();
		        	var myParams = {project: project};
		            $.ajax({
		                url: "<%=request.getContextPath()%>/ajax/getEmpstList",
		                data: myParams, cache: false,async: false,
		                success: function (data) {
		                    if (data.length > 0) {
		                        $.each(data, function (i, val) {
			                      $("#select2-employee_code_add-container").append('<option email ="' + val.email_id + '"  value="' + val.user_id + '">'+'['+$.trim(val.user_id) +"] - "+$.trim(val.user_name) +'</option>');
		                    	});
		                     }           
		                    },error: function (jqXHR, exception) {
		    	   			      $(".page-loader").hide();
		       	          	  getErrorMessage(jqXHR, exception);
		       	     	  }
		            });
		        }
	      }
	 
	    
	   
    </script>
  </body>
  <!-- END: Body-->

<!-- Mirrored from pixinvent.com/demo/vuexy-html-bootstrap-admin-template/html/ltr/horizontal-menu-template/dashboard-analytics.html by HTTrack Website Copier/3.x [XR&CO'2014], Sun, 07 Aug 2022 05:36:10 GMT -->
</html>