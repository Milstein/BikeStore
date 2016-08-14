<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<!-- <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1"> -->
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="Content-Script-Type" content="text/javascript">
<meta http-equiv="Content-Style-Type" content="text/css">
<meta content="Bike Management" name="DESCRIPTION">
<meta content="Bike Management" name="KEYWORDS">
<meta content="@GPMS" name="COPYRIGHT">
<meta content="GENERATOR" name="GENERATOR">
<meta content="Author" name="AUTHOR">
<meta content="DOCUMENT" name="RESOURCE-TYPE">
<meta content="GLOBAL" name="DISTRIBUTION">
<meta content="INDEX, FOLLOW" name="ROBOTS">
<meta content="1 DAYS" name="REVISIT-AFTER">
<meta content="GENERAL" name="RATING">
<meta http-equiv="X-UA-Compatible" content="IE=9; IE=8; IE=7; IE=EDGE">
<!-- Mimic Internet Explorer 7 -->
<meta content="IE=EmulateIE7" http-equiv="X-UA-Compatible">
<meta content="RevealTrans(Duration=0,Transition=1)"
	http-equiv="PAGE-ENTER">
<link type="icon shortcut" media="icon" href="favicon.ico">
<title>Bike Management</title>

<script src="js/ckeditor/ckeditor.js"></script>
<script type="text/javascript" src="js/jQuery/jquery-1.11.3.min.js"></script>

<script type="text/javascript">
	//<![CDATA[

	var gpmsServicePath = "REST/";
	var gpmsRootPath = "http://localhost:8181/BikeStore/";

	//]]>
</script>

<script type="text/javascript" src="js/jQuery/jquery-ui.js"></script>

<script type="text/javascript" src="js/core/encoder.js"></script>

<script type="text/javascript" src="js/core/jquery.disable_with.js"></script>

<script type="text/javascript"
	src="js/FormValidation/jquery.validate.js"></script>
<script type="text/javascript"
	src="js/FormValidation/jquery.ui.datepicker.validation.js"></script>
<script type="text/javascript"
	src="js/FormValidation/jquery.maskedinput.js"></script>
<script type="text/javascript" src="js/FormValidation/autoNumeric.js"></script>

<script type="text/javascript" src="js/core/json2.js"></script>

<script type="text/javascript" src="js/jquery-browser.js"></script>
<script type="text/javascript" src="js/jquery.uniform.js"></script>

<script type="text/javascript" src="js/GridView/jquery.tablesorter.js"></script>
<script type="text/javascript" src="js/GridView/jquery.grid.js"></script>
<script type="text/javascript" src="js/GridView/SagePaging.js"></script>
<script type="text/javascript" src="js/GridView/jquery.global.js"></script>
<script type="text/javascript" src="js/GridView/jquery-dateFormat.js"></script>

<script type="text/javascript" src="js/MessageBox/jquery.easing.1.3.js"></script>
<script type="text/javascript" src="js/MessageBox/alertbox.js"></script>

<script type="text/javascript" src="js/Uploader/jquery.uploadfile.js"></script>

<script type="text/javascript" src="js/StarRating/jquery.MetaData.js"></script>

<script type="text/javascript" src="js/StarRating/jquery.rating.js"></script>

<script type="text/javascript" src="js/modules/ManageBikes.js"></script>

<link type="text/css" rel="stylesheet"
	href="css/Templates/jquery-ui.css" />

<link type="text/css" rel="stylesheet"
	href="css/Templates/uploadfile.css">

<link type="text/css" rel="stylesheet" href="css/MessageBox/style.css" />

<link type="text/css" rel="stylesheet" href="css/GridView/tablesort.css" />
<link type="text/css" rel="stylesheet" href="css/GridView/grid.css" />
<link type="text/css" rel="stylesheet"
	href="css/Templates/buttonicon.css" />
<link type="text/css" rel="stylesheet" href="css/Templates/admin.css" />

<link type="text/css" rel="stylesheet"
	href="css/Templates/jquery.rating.css" />

</head>
<body>
	<form enctype="multipart/form-data" action="ManageBikes.jsp"
		method="post" name="form1" id="form1">
		<div style="display: none;" id="UpdateProgress1">
			<div class="sfLoadingbg">&nbsp;</div>
			<div class="sfLoadingdiv">
				<img id="imgProgress" src="./images/ajax-loader.gif"
					style="border-width: 0px;" alt="Loading..." title="Loading..." />
				<br> <span id="lblPrgress">Please wait...</span>
			</div>
		</div>
		<noscript>
			<span>This page requires java-script to be enabled. Please
				adjust your browser-settings.</span>
		</noscript>
		<div id="sfOuterwrapper">
			<div class="sfSagewrapper">
				<!-- Body Content -->
				<div class="sfContentwrapper clearfix">
					<div id="divCenterContent">
						<div class="sfMaincontent">
							<div style="display: block" class="sfCpanel sfInnerwrapper"
								id="divBottompanel">
								<div class="sfModulecontent clearfix">
									<!-- Grid -->
									<div id="divBikeGrid">
										<div class="cssClassCommonBox Curve">
											<div class="cssClassHeader">
												<h1>
													<span>Manage Bikes</span>
												</h1>
												<div class="cssClassHeaderRight">
													<div class="sfButtonwrapper">
														<p>
															<button title="Add New Bike" type="button" id="btnAddNew"
																class="sfBtn">
																<span class="icon-addnew">Add New Bike</span>
															</button>
														</p>
														<p>
															<button title="Deactivate All Selected" type="button"
																id="btnDeactivateSelected" class="sfBtn">
																<span class="icon-delete">Deactivate All Selected</span>
															</button>
														</p>
														<p>
															<button title="Export to Excel" type="button"
																id="btnExportToExcel" class="sfBtn">
																<span class="icon-excel">Export to Excel</span>
															</button>
														</p>
														<div class="cssClassClear"></div>
													</div>
													<div class="cssClassClear"></div>
												</div>
												<div class="cssClassClear"></div>
											</div>
											<div class="sfGridwrapper">
												<div class="sfGridWrapperContent">
													<div class="sfFormwrapper sfTableOption">
														<table width="100%" cellspacing="0" cellpadding="0"
															border="0">
															<tbody>
																<tr>
																	<td><label class="cssClassLabel">Bike
																			Title:</label> <input title="Bike Title" type="text"
																		class="sfTextBox" id="txtSearchTitle"
																		placeholder="Bike Title" /></td>

																	<td style="width: 180px; float: left;"><label
																		class="cssClassLabel">Added On:</label>
																		<div>
																			<span class="cssClassLabel">From:</span> <input
																				type="text" title="Added On From"
																				id="txtSearchAddedOnFrom" class="sfTextBoxFix"
																				placeholder="From">
																		</div>
																		<div>
																			<span class="cssClassLabel">To:</span> <input
																				type="text" title="Added On To"
																				id="txtSearchAddedOnTo" class="sfTextBoxFix"
																				placeholder="To">
																		</div></td>

																	<td style="width: 180px;"><label
																		class="cssClassLabel">Price Range:</label>
																		<div>
																			<span class="cssClassLabel">From:</span> <input
																				type="text" title="Price From"
																				id="txtSearchPriceFrom" name="searchPriceFrom"
																				class="sfTextBoxFix" placeholder="From">
																		</div>
																		<div>
																			<span class="cssClassLabel">To:</span> <input
																				type="text" title="Price To" id="txtSearchPriceTo"
																				name="searchPriceTo" class="sfTextBoxFix"
																				placeholder="To">
																		</div></td>

																	<td><label class="cssClassLabel">Active:</label> <select
																		title="Is Active?" id="ddlSearchIsActive"
																		class="sfListmenu" style="width: 100px;">
																			<option value="">--All--</option>
																			<option value="True">True</option>
																			<option value="False">False</option>
																	</select></td>

																	<td><label class="cssClassLabel">&nbsp;</label>
																		<button title="Search Bike" class="sfBtn"
																			id="btnSearchBike" type="button">
																			<span class="icon-search">Search</span>
																		</button></td>
																</tr>
															</tbody>
														</table>
													</div>
													<div class="loading">
														<img id="ajaxLoader" src="./images/ajax-loader.gif"
															alt="Loading..." title="Loading..." />
													</div>
													<div class="log"></div>
													<table id="gdvBikes" cellspacing="0" cellpadding="0"
														border="0" width="100%"></table>
												</div>
											</div>
										</div>
									</div>
									<!-- End of Grid -->
									<!-- form -->
									<div id="divBikeForm" style="display: none;">
										<div class="cssClassCommonBox Curve">
											<div class="cssClassHeader">
												<h1>
													<span id="lblFormHeading">New Bike Details</span>
												</h1>
												<div>
													<span class="cssClassRequired">*</span> <span
														class="cssClassLabelTitle">indicates required
														fields</span>
												</div>
											</div>
											<div class="sfFormwrapper">
												<table cellspacing="0" cellpadding="0" border="0"
													id="tblBikeDetails">
													<tbody>
														<tr>
															<td><span class="cssClassLabel" id="lblType">Type:</span>
																<span class="cssClassRequired">*</span></td>
															<td><select title="Choose Bike Type"
																class="sfListmenu" id="ddlType" name="ddlType"
																required="true">
																	<option value="Road Bike">Road Bike</option>
																	<option value="Mountain Bike">Mountain Bike</option>
																	<option value="Kid's Bike">Kid's Bike</option>
															</select></td>
														</tr>
														<tr>
															<td><span class="cssClassLabel" id="lblTitle">Title:</span>
																<span class="cssClassRequired">*</span></td>
															<td><input title="Bike Title" type="text"
																id="txtTitle" class="sfInputbox" name="bikeTitle"
																placeholder="Bike Title" /></td>
														</tr>
														<tr>
															<td><span class="cssClassLabel" id="lblDescription">Description:</span>
																<span class="cssClassRequired">*</span></td>
															<td><textarea class="cssClassTextArea"
																	title="Bike Description" id="txtDescription"
																	name="bikeDescription" cols="26" rows="2"
																	placeholder="Bike Description"></textarea></td>
														</tr>
														<tr>
															<td><span class="cssClassLabel" id="lblPrice">Price:</span>
																<span class="cssClassRequired">*</span></td>
															<td><input title="Bike Price" type="text"
																id="txtPrice" class="sfInputbox" name="bikePrice"
																placeholder="Bike Price" /></td>
														</tr>
														<tr>
															<td><span class="cssClassLabel" id="lblQuantity">Quantity:</span>
																<span class="cssClassRequired">*</span></td>
															<td><input title="Quantity" type="text"
																id="txtQuantity" class="sfInputbox" name="bikeQuantity"
																placeholder="Quantity" /></td>
														</tr>
														<tr>
															<td><span class="cssClassLabel" id="lblRating">Rating:</span></td>
															<td>
																<div class="Clear" id="divRating">
																	<input class="auto-submit-star" type="radio"
																		name="star-rating" value="1" /> <input
																		class="auto-submit-star" type="radio"
																		name="star-rating" value="2" /> <input
																		class="auto-submit-star" type="radio"
																		name="star-rating" value="3" /> <input
																		class="auto-submit-star" type="radio"
																		name="star-rating" value="4" /> <input
																		class="auto-submit-star" type="radio"
																		name="star-rating" value="5" />
																</div>
															</td>
														</tr>
														<tr>
															<td><span class="cssClassLabel" id="lblImages">Images:</span></td>
															<td><div id="fileuploader">Upload</div></td>
														</tr>
														<tr>
															<td><span class="cssClassLabel" id="lblActive">Active:</span></td>
															<td><input title="Active" type="checkbox" value=""
																name="chkActive" class="cssClassCheckBox" checked="true"></td>
														</tr>
														<tr id="trAddedOn">
															<td><span class="cssClassLabel">Added On:</span></td>
															<td class="cssClassTableRightCol"><span
																id="lblBikeAddedOn"></span></td>
														</tr>
													</tbody>
												</table>
											</div>

											<div class="sfButtonwrapper">
												<p>
													<button class="sfBtn" id="btnBack" type="button"
														title="Go Back">
														<span class="icon-arrow-slim-w">Back</span>
													</button>
												</p>
												<p>
													<button class="sfBtn" id="btnReset" type="button"
														title="Reset">
														<span class="icon-refresh">Reset</span>
													</button>
												</p>
												<p>
													<button class="sfBtn" id="btnSaveBike" type="button"
														title="Save Delegation">
														<span class="icon-edit">Save</span>
													</button>
												</p>
											</div>
										</div>
									</div>
									<!-- End form -->
								</div>

							</div>
						</div>
						<!-- END sfMaincontent -->
					</div>
				</div>
				<!-- END Body Content sfContentwrapper -->
			</div>
		</div>
	</form>
</body>
<script>
	var editor = CKEDITOR.replace('txtDescription');
</script>
</html>