<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<!-- <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1"> -->
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="Content-Script-Type" content="text/javascript">
<meta http-equiv="Content-Style-Type" content="text/css">
<meta content="Latest Bike" name="DESCRIPTION">
<meta content="Latest Bike" name="KEYWORDS">
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
<title>Latest Bike</title>

<script type="text/javascript" src="js/jQuery/jquery-1.11.3.min.js"></script>

<script type="text/javascript">
	//<![CDATA[

	var gpmsServicePath = "REST/";
	var gpmsRootPath = "http://localhost:8181/BikeStore/";

	//]]>
</script>

<script type="text/javascript" src="js/jquery-browser.js"></script>
<script type="text/javascript" src="js/jquery.uniform.js"></script>

<script type="text/javascript" src="js/MessageBox/jquery.easing.1.3.js"></script>
<script type="text/javascript" src="js/MessageBox/alertbox.js"></script>

<script type="text/javascript" src="js/Slider/jssor.slider-21.1.mini.js"></script>

<script type="text/javascript" src="js/modules/Latest.js"></script>

<link type="text/css" rel="stylesheet" href="css/MessageBox/style.css" />

<link type="text/css" rel="stylesheet" href="css/Templates/slider.css" />

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

		<div id="jssor_1"
			style="position: relative; margin: 0 auto; top: 0px; left: 0px; width: 600px; height: 300px; overflow: hidden; visibility: hidden;">
			<!-- Loading Screen -->
			<div data-u="loading"
				style="position: absolute; top: 0px; left: 0px;">
				<div
					style="filter: alpha(opacity = 70); opacity: 0.7; position: absolute; display: block; top: 0px; left: 0px; width: 100%; height: 100%;"></div>
				<div
					style="position: absolute; display: block; background: url('images/loading.gif') no-repeat center center; top: 0px; left: 0px; width: 100%; height: 100%;"></div>
			</div>
			<div id="sliderContainer" data-u="slides"
				style="cursor: default; position: relative; top: 0px; left: 0px; width: 600px; height: 300px; overflow: hidden;">
			</div>
			<!-- Bullet Navigator -->
			<div id="bulletNav" data-u="navigator" class="jssorb01"
				style="bottom: 16px; right: 16px;">
				<div data-u="prototype" style="width: 12px; height: 12px;"></div>
			</div>
			<!-- Arrow Navigator -->
			<span id="arrowNav" style="display: none;" data-u="arrowleft"
				class="jssora02l"
				style="top: 0px; left: 8px; width: 55px; height: 55px;"
				data-autocenter="2"></span> <span data-u="arrowright"
				class="jssora02r"
				style="top: 0px; right: 8px; width: 55px; height: 55px;"
				data-autocenter="2"></span>
		</div>
	</form>
</body>
</html>