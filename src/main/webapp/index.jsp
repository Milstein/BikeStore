<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<!-- <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1"> -->
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="Content-Script-Type" content="text/javascript">
<meta http-equiv="Content-Style-Type" content="text/css">
<meta content="Bike Display" name="DESCRIPTION">
<meta content="Bike Display" name="KEYWORDS">
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
<title>Bike Display</title>

<script type="text/javascript" src="js/jQuery/jquery-1.11.3.min.js"></script>

<script type="text/javascript">
	//<![CDATA[

	var gpmsServicePath = "REST/";
	var gpmsRootPath = "http://localhost:8181/BikeStore/";

	//]]>
</script>

<script type="text/javascript" src="js/jQuery/jquery-ui.js"></script>

<script type="text/javascript" src="js/core/encoder.js"></script>

<script type="text/javascript" src="js/core/json2.js"></script>

<script type="text/javascript" src="js/jquery-browser.js"></script>
<script type="text/javascript" src="js/jquery.uniform.js"></script>

<script type="text/javascript" src="js/MessageBox/jquery.easing.1.3.js"></script>
<script type="text/javascript" src="js/MessageBox/alertbox.js"></script>

<script type="text/javascript" src="js/StarRating/jquery.MetaData.js"></script>

<script type="text/javascript" src="js/StarRating/jquery.rating.js"></script>

<script type="text/javascript" src="js/modules/index.js"></script>

<link type="text/css" rel="stylesheet"
	href="css/Templates/jquery-ui.css" />

<link type="text/css" rel="stylesheet" href="css/MessageBox/style.css" />

<link type="text/css" rel="stylesheet" href="css/Templates/main.css" />

<link type="text/css" rel="stylesheet"
	href="css/Templates/jquery.rating.css" />
<body>
	<form enctype="multipart/form-data" action="index.jsp" method="post"
		name="form1" id="form1">
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

		<div class="cssClassCommonWrapper" id="itemDetails">
			<div class="cssClassProductInformation clearfix">
				<div class="cssClassProductPictureDetails">
					<div class="cssClassLeftSide">
						<h1>
							<span id="spanItemName"></span>
						</h1>
						<div class="cssClassItemRating">
							<div class="cssRatingwrapper">
								<div class="cssClassItemRatingBox cssClassToolTip">
									<div class="cssClassRatingStar clearfix">
										<asp:Literal ID="ltrRatings" runat="server"
											EnableViewState="false"></asp:Literal>
									</div>
								</div>
							</div>
						</div>
						<div class="cssItemCategories clearfix" style="display: none">
							<div class="cssClassItemCategoriesHeading">
								<strong class="sfLocale">In Categories : </strong>
							</div>
							<div class="cssClassCategoriesName"></div>
						</div>
					</div>
					<div class="cssPriceDetailwrap clearfix">
						<div class="clearfix">
							<div class="cssClassProductRealPrice">
								<asp:Label ID="lblPrice" runat="server" Text=""></asp:Label>
								<span id="spanPrice" class="cssClassFormatCurrency"></span> <br />
							</div>
						</div>
					</div>
					<div class="detailButtonWrapper clearfix">
						<div class="cssQtywrapper">
							<label class="cssClssQTY"> <asp:Label ID="lblQty"
									runat="server" Text="Qty: " meta:resourcekey="lblQtyResource1"></asp:Label>
								<input type="text" id="txtQty" />
						</div>
						<div class="sfButtonwrapper clearfix">
							<label class="i-cart cssClassCartLabel">
								<button class="addtoCart" id="btnAddToMyCart" type="button"
									onclick="ItemDetail.AddToMyCart();">
									<span class="sfLocale ">Cart+</span>
								</button>
							</label> <input type="hidden" name="itemDetailWish" id="addWishListThis" />
							<label class="i-wishlist cssWishListLabel cssClassDarkBtn">
								<button type="button" id="btnAddToWishList"
									onclick="ItemDetail.AddWishItemFromDetailPage($(this))">
									<span>WishList +</span>
								</button>
							</label> <input type="hidden" name="itemDetailCompare"
								id="addCompareListThis" />
						</div>
						<div class="cssDlinks clearfix">
							<ul>
								<li><a href="#" id="lnkContinueShopping" class="sfAnchor">
										<span class="sfLocale">Continue Shopping </span>
								</a></li>
							</ul>
						</div>
					</div>
					<div id="divItemShortDesc" class="cssClassTMar10"></div>
				</div>
			</div>
		</div>
		</div>
	</form>
</body>
</html>