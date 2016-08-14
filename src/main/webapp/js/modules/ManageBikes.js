var managebike = '';

$(function() {
	jQuery.fn.exists = function() {
		return this.length > 0;
	}

	// $.validator.unobtrusive.parse(#form1);
	$.validator.setDefaults({
		ignore : []
	});

	$.validator
			.addMethod(
					'greaterthan',
					function(value, element, params) {
						if ($(params).autoNumeric('get') != ''
								&& $(element).autoNumeric('get') != '') {
							return isNaN($(element).autoNumeric('get'))
									&& isNaN($(params).autoNumeric('get'))
									|| parseFloat($(element).autoNumeric('get')) > parseFloat($(
											params).autoNumeric('get'));
						}
						return true;
					}, 'Must be greater than Price From');

	$("#txtSearchPriceFrom").keyup(function() {
		$("#txtSearchPriceTo").val('');
		$("#txtSearchPriceTo").removeClass('error');
		$("#txtSearchPriceTo-error").remove();
	});

	var validator = $("#form1").validate({
		rules : {
			searchPriceTo : {
				greaterthan : "#txtSearchPriceFrom"
			},
			bikeTitle : {
				required : true,
				minlength : 5,
				maxlength : 150
			},
			bikeDescription : {
				required : function() {
					CKEDITOR.instances.txtDescription.updateElement();
				},
				minlength : 5
			},
			bikeQuantity : {
				required : true,
				digits : true,
				maxlength : 5
			}
		},
		errorElement : "span",
		messages : {
			searchPriceTo : {
				greaterthan : "Must be greater than From"
			},
			bikeTitle : {
				required : "Please enter bike title.",
				minlength : "Bike title must be at least 5 characters long",
				maxlength : "Bike title must be at most 150 characters long"
			},
			bikeDescription : {
				required : "Please enter bike Description.",
				minlength : "Bike description must be at least 5 digits long",
			},
			bikeQuantity : {
				required : "Please enter Quantity.",
				maxlength : "Quantity must be at most 5 characters long"
			}
		}
	});

	managebike = {
		config : {
			isPostBack : false,
			async : false,
			cache : false,
			type : 'POST',
			contentType : "application/json; charset=utf-8",
			data : '{}',
			dataType : 'json',
			rootURL : "REST/",
			baseURL : "REST/bikes/",
			method : "",
			url : "",
			ajaxCallMode : 0,
			bikeId : "0",
			uploadObj : ""
		},

		ajaxCall : function(config) {
			$.ajax({
				type : managebike.config.type,
				beforeSend : function(request) {
					// Need to send oAuth values here
				},
				contentType : managebike.config.contentType,
				cache : managebike.config.cache,
				async : managebike.config.async,
				url : managebike.config.url,
				data : managebike.config.data,
				dataType : managebike.config.dataType,
				success : managebike.ajaxSuccess,
				error : managebike.ajaxFailure
			});
		},

		SearchBikes : function() {
			var bikeTitle = $.trim($("#txtSearchTitle").val());
			var addedOnFrom = $.trim($("#txtSearchAddedOnFrom").val());
			var addedOnTo = $.trim($("#txtSearchAddedOnTo").val());
			var priceFrom = $.trim($("#txtSearchPriceFrom").autoNumeric('get'));
			var priceTo = $.trim($("#txtSearchPriceTo").autoNumeric('get'));

			var isActive = $.trim($("#ddlSearchIsActive").val()) == "" ? null
					: $.trim($("#ddlSearchIsActive").val()) == "True" ? true
							: false;

			if (bikeTitle.length < 1) {
				bikeTitle = null;
			}
			if (addedOnFrom.length < 1) {
				addedOnFrom = null;
			}
			if (addedOnTo.length < 1) {
				addedOnTo = null;
			}
			if (priceFrom.length < 1) {
				priceFrom = null;
			}
			if (priceTo.length < 1) {
				priceTo = null;
			}

			managebike.BindBikeGrid(bikeTitle, addedOnFrom, addedOnTo,
					priceFrom, priceTo, isActive);
		},

		BindBikeGrid : function(bikeTitle, addedOnFrom, addedOnTo, priceFrom,
				priceTo, isActive) {
			this.config.url = this.config.baseURL;
			this.config.method = "GetBikesList";
			var offset_ = 1;
			var current_ = 1;
			var perpage = ($("#gdvBikes_pagesize").length > 0) ? $(
					"#gdvBikes_pagesize :selected").text() : 10;

			var bikeBindObj = {
				bikeTitle : bikeTitle,
				addedOnFrom : addedOnFrom,
				addedOnTo : addedOnTo,
				priceFrom : priceFrom,
				priceTo : priceTo,
				isActive : isActive
			};

			this.config.data = {
				bikeBindObj : bikeBindObj
			};

			var data = this.config.data;

			$("#gdvBikes").sagegrid({
				url : this.config.url,
				functionMethod : this.config.method,
				colModel : [ {
					display : 'Bike ID',
					cssclass : 'cssClassHeadCheckBox',
					coltype : 'checkbox',
					align : 'center',
					checkFor : '8',
					elemClass : 'attrChkbox',
					elemDefault : false,
					controlclass : 'attribHeaderChkbox'
				}, {
					display : 'Title',
					name : 'title',
					cssclass : '',
					controlclass : '',
					coltype : 'label',
					align : 'left'
				}, {
					display : 'Description',
					name : 'description',
					cssclass : '',
					controlclass : '',
					coltype : 'label',
					align : 'left'
				}, {
					display : 'Rating',
					name : 'rating',
					cssclass : '',
					controlclass : '',
					coltype : 'label',
					align : 'left'
				}, {
					display : 'Price',
					name : 'price',
					cssclass : '',
					controlclass : '',
					type : 'currency',
					align : 'left'
				}, {
					display : 'Quantity',
					name : 'quantity',
					cssclass : '',
					controlclass : '',
					coltype : 'label',
					align : 'left'
				}, {
					display : 'Type',
					name : 'type',
					cssclass : '',
					controlclass : '',
					coltype : 'label',
					align : 'left'
				}, {
					display : 'Added On',
					name : 'addedOn',
					cssclass : '',
					controlclass : '',
					coltype : 'label',
					align : 'left',
					type : 'date',
					format : 'yyyy/MM/dd hh:mm:ss a'
				}, {
					display : 'Is Active?',
					name : 'isactive',
					cssclass : 'cssClassHeadBoolean',
					controlclass : '',
					coltype : 'label',
					align : 'left',
					type : 'boolean',
					format : 'True/False'
				}, {
					display : 'Actions',
					name : 'action',
					cssclass : 'cssClassAction',
					coltype : 'label',
					align : 'center'
				} ],

				buttons : [ {
					display : 'Edit',
					name : 'edit',
					enable : true,
					_event : 'click',
					trigger : '1',
					callMethod : 'managebike.EditBike',
					arguments : '1, 2 , 3, 4, 5, 6, 7, 8'
				}, {
					display : 'Activate',
					name : 'revoke',
					enable : true,
					_event : 'click',
					trigger : '2',
					callMethod : 'managebike.ActivateBike',
					arguments : '8'
				}, {
					display : 'Deactivate',
					name : 'changelog',
					enable : true,
					_event : 'click',
					trigger : '3',
					callMethod : 'managebike.DeactivateBike',
					arguments : '8'
				} ],
				rp : perpage,
				nomsg : 'No Records Found!',
				param : data,
				current : current_,
				pnew : offset_,
				sortcol : {
					0 : {
						sorter : false
					},
					9 : {
						sorter : false
					}
				}
			});
		},

		ExportToExcel : function(bikeTitle, addedOnFrom, addedOnTo, priceFrom,
				priceTo, isActive) {
			var bikeBindObj = {
				bikeTitle : bikeTitle,
				addedOnFrom : addedOnFrom,
				addedOnTo : addedOnTo,
				priceFrom : priceFrom,
				priceTo : priceTo,
				isActive : isActive
			};

			this.config.data = JSON2.stringify({
				bikeBindObj : bikeBindObj
			});

			this.config.url = this.config.baseURL + "BikesExportToExcel";
			this.config.ajaxCallMode = 1;
			this.ajaxCall(this.config);
			return false;
		},

		EditBike : function(tblID, argus) {
			switch (tblID) {
			case "gdvBikes":

				$('#lblFormHeading').html('Edit Bike Details for: ' + argus[1]);

				managebike.ClearForm();

				managebike.BindBikeImageDetailsByBikeId(argus[0]);

				managebike.config.bikeId = argus[0];

				$("#ddlType").val(argus[6]);

				$("#txtTitle").val(argus[1]);

				CKEDITOR.instances['txtDescription'].setData(argus[2]);
				// $("#txtDescription").val();

				$('.auto-submit-star').rating('select', argus[3].toString());

				$("#txtPrice").val(argus[4]);

				$("#txtQuantity").val(argus[5]);

				var bool_value = argus[8].toLowerCase() == 'true';

				$('input[name=chkActive]').prop('checked', bool_value);

				$("#lblBikeAddedOn").text(argus[7]);

				$("#btnReset").hide();
				$("#trAddedOn").show();

				$('#divBikeGrid').hide();
				$('#divBikeForm').show();

				break;
			default:
				break;
			}
		},

		BindBikeImageDetailsByBikeId : function(bikeId) {
			this.config.url = this.config.baseURL
					+ "GetBikeImageDetailsByBikeId";
			this.config.data = JSON2.stringify({
				bikeId : bikeId
			});
			this.config.ajaxCallMode = 6;
			this.ajaxCall(this.config);
			return false;
		},

		InitializeUploader : function(images) {
			// Uploader for Image
			var globalSettings = {
				url : managebike.config.rootURL + "files/multiupload",
				multiple : true,
				dragDrop : true,
				fileName : "myfile",
				allowDuplicates : false,
				duplicateStrict : true,
				nestedForms : false,
				fileCounterStyle : ") ",
				// autoSubmit : true,
				// sequential : true,
				// sequentialCount : 1,
				// autoSubmit : false,
				// formData : {
				// "name" : "Milson",
				// "age" : 29
				// },uploadObj
				allowedTypes : "jpg,png,gif,jpeg,bmp,png",
				// acceptFiles : "image/*",
				maxFileCount : 5,
				maxFileSize : 5 * 1024 * 1024, // 5MB
				returnType : "json",
				showDelete : true,
				confirmDelete : true,
				statusBarWidth : 600,
				dragdropWidth : 600,
				uploadQueueOrder : 'top',
				deleteCallback : function(data, pd) {
					pd.statusbar.hide(); // You choice.
				}
			}

			var settings = {
				showDownload : true,
				downloadCallback : function(filename, pd) {
					window.location.href = managebike.config.rootURL
							+ 'files/download?fileName=' + filename;
				}
			}

			managebike.config.uploadObj = $("#fileuploader").uploadFile(
					globalSettings);

			if (images != "") {
				managebike.config.uploadObj.update(settings);

				$.each(images, function(index, value) {
					managebike.config.uploadObj.createProgress(value.filename,
							value.filepath, value.filesize);
				});

				managebike.config.uploadObj.update({
					showDownload : false
				});
			}
		},

		SaveBike : function(config) {
			var bikeInfo = {
				BikeId : config.bikeId,
				Type : $("#ddlType").val(),
				Title : $("#txtTitle").val(),
				Description : CKEDITOR.instances['txtDescription'].getData()
						.replace(/<[^>]*>/gi, '').replace(/&nbsp;/gi, ' '),
				Price : $("#txtPrice").autoNumeric('get'),
				Quantity : $("#txtQuantity").val(),

				IsActive : $('input[name=chkActive]').prop('checked')
			};

			bikeInfo.Rating = "0";
			$('#divRating input').each(function() {
				if (this.checked)
					bikeInfo.Rating = this.value;
			});

			bikeInfo.ImageInfo = this.config.uploadObj.getResponses().reverse();

			managebike.AddBikeInfo(config, bikeInfo);
			return false;
		},

		AddBikeInfo : function(config, info) {
			this.config.url = this.config.baseURL + "SaveUpdateBike";
			this.config.data = JSON2.stringify({
				bikeInfo : info
			});
			this.config.ajaxCallMode = 2;
			this.ajaxCall(this.config);
			return false;
		},

		ActivateBike : function(tblID, argus) {
			switch (tblID) {
			case "gdvBikes":
				if (argus[1].toLowerCase() != "true") {
					managebike.UpdateBikeActive(argus[0], true);
				} else {
					csscody.alert('<h2>' + 'Information Alert' + '</h2><p>'
							+ 'Sorry! this bike is already active.' + '</p>');
				}
				break;
			default:
				break;
			}
		},

		DeactivateBike : function(tblID, argus) {
			switch (tblID) {
			case "gdvBikes":
				if (argus[1].toLowerCase() != "false") {
					managebike.UpdateBikeActive(argus[0], false);
				} else {
					csscody.alert('<h2>' + 'Information Alert' + '</h2><p>'
							+ 'Sorry! this bike is already deactive.' + '</p>');
				}
				break;
			default:
				break;
			}
		},

		UpdateBikeActive : function(bikeId, isActive) {
			this.config.url = this.config.baseURL
					+ "UpdateBikeIsActiveByBikeId";
			this.config.data = JSON2.stringify({
				bikeId : bikeId,
				isActive : isActive
			});
			if (isActive) {
				this.config.ajaxCallMode = 3;
			} else {
				this.config.ajaxCallMode = 4;
			}
			this.ajaxCall(this.config);
			return false;
		},

		ConfirmDeactivateMultiple : function(bike_ids, event) {
			if (event) {
				managebike.DeactivateMultipleBikes(bike_ids);
			}
		},

		DeactivateMultipleBikes : function(bikeIds) {
			// this.config.dataType = "html";
			this.config.url = this.config.baseURL
					+ "DeactivateMultipleBikesByBikeId";
			this.config.data = JSON2.stringify({
				bikeIds : bikeIds
			});
			this.config.ajaxCallMode = 5;
			this.ajaxCall(this.config);
			return false;
		},

		ClearForm : function() {
			validator.resetForm();

			managebike.config.bikeId = '0';

			if (managebike.config.uploadObj != "") {
				managebike.config.uploadObj.reset(true);
			}

			var container = $("#tblBikeDetails");
			var inputs = container.find('INPUT, SELECT, TEXTAREA');
			$.each(inputs, function(i, item) {
				if (!$(this).hasClass("auto-submit-star")) {
					$(this).val('');
					$(this).val($(this).find('option').first().val());
				}
			});

			if (!$('input[name=chkActive]').is(":checked")) {
				$('input[name=chkActive]').prop('checked', 'checked');
			}

			$('.auto-submit-star').rating('drain');
			$('.auto-submit-star').removeAttr('checked');
			$('.auto-submit-star').rating('select', -1);

			return false;
		},

		ajaxSuccess : function(msg) {
			switch (managebike.config.ajaxCallMode) {
			case 0:
				break;

			case 1: // Export to Excel Bikes
				if (msg != "No Record") {
					window.location.href = managebike.config.rootURL
							+ 'files/download?fileName=' + msg;
				} else {
					csscody.alert("<h2>" + 'Information Message' + "</h2><p>"
							+ 'No Record found!' + "</p>");
				}
				break;

			case 2: // Save / Update Bike
				managebike.BindBikeGrid(null, null, null, null, null, null);
				$('#divBikeGrid').show();

				if (managebike.config.bikeId != "0") {
					csscody.info("<h2>" + 'Successful Message' + "</h2><p>"
							+ 'Bike has been Updated successfully.' + "</p>");
				} else {
					csscody.info("<h2>" + 'Successful Message' + "</h2><p>"
							+ 'Bike has been Saved successfully.' + "</p>");
				}

				$('#divBikeForm').hide();

				managebike.config.bikeId = '0';
				break;

			case 3:
				managebike.BindBikeGrid(null, null, null, null, null, null);
				csscody.info("<h2>" + 'Successful Message' + "</h2><p>"
						+ 'Bike has been activated successfully.' + "</p>");
				break;

			case 4:
				managebike.BindBikeGrid(null, null, null, null, null, null);
				csscody.info("<h2>" + 'Successful Message' + "</h2><p>"
						+ 'Bike has been deactivated successfully.' + "</p>");
				break;

			case 5:
				managebike.BindBikeGrid(null, null, null, null, null, null);
				csscody
						.info("<h2>"
								+ 'Successful Message'
								+ "</h2><p>"
								+ 'Selected Bike(s) have been deactivated successfully.'
								+ "</p>");
				break;

			case 6:// For Bike Edit Action
				// Initialize Images content and Uploader
				managebike.InitializeUploader(msg.images);
				break;

			}
		},

		ajaxFailure : function(msg) {
			switch (managebike.config.ajaxCallMode) {
			case 0:
				break;
			case 1:
				csscody.error("<h2>" + 'Error Message' + "</h2><p>"
						+ 'Cannot create and download Excel report!' + "</p>");
				break;

			case 2:
				if (managebike.config.bikeId != "0") {
					csscody.error("<h2>" + 'Error Message' + "</h2><p>"
							+ 'Failed to update bike!' + "</p>");
				} else {
					csscody.error("<h2>" + 'Error Message' + "</h2><p>"
							+ 'Failed to save bike!' + "</p>");
				}
				break;

			case 3:
				csscody.error("<h2>" + 'Error Message' + "</h2><p>"
						+ 'Bike cannot be activated.' + "</p>");
				break;

			case 4:
				csscody.error("<h2>" + 'Error Message' + "</h2><p>"
						+ 'Bike cannot be deactivated.' + "</p>");
				break;

			case 5:
				csscody.error("<h2>" + 'Error Message' + "</h2><p>"
						+ 'Bike(s) cannot be deactivated.' + "</p>");
				break;

			case 6:
				csscody.error('<h2>' + 'Error Message' + '</h2><p>'
						+ 'Failed to load bike details.' + '</p>');

			}
		},

		init : function(config) {
			$("#txtSearchAddedOnFrom").datepicker(
					{
						dateFormat : 'yy-mm-dd',
						changeMonth : true,
						changeYear : true,
						// maxDate : 0
						onSelect : function(selectedDate) {
							$("#txtSearchAddedOnTo").datepicker("option",
									"minDate", selectedDate);
						}
					}).mask("9999-99-99", {
				placeholder : "yyyy-mm-dd"
			});
			$("#txtSearchAddedOnTo").datepicker(
					{
						dateFormat : 'yy-mm-dd',
						changeMonth : true,
						changeYear : true,
						onSelect : function(selectedDate) {
							$("#txtSearchAddedOnFrom").datepicker("option",
									"maxDate", selectedDate);
						}
					}).mask("9999-99-99", {
				placeholder : "yyyy-mm-dd"
			});

			$("#txtPrice").autoNumeric('init', {
				aSep : ',',
				dGroup : '3',
				aDec : '.',
				aSign : '$',
				pSign : 'p',
				aPad : true
			});

			$("#txtSearchPriceFrom").autoNumeric('init', {
				aSep : ',',
				dGroup : '3',
				aDec : '.',
				aSign : '$',
				pSign : 'p',
				aPad : true
			// vMin : "1.00"
			});
			$("#txtSearchPriceTo").autoNumeric('init', {
				aSep : ',',
				dGroup : '3',
				aDec : '.',
				aSign : '$',
				pSign : 'p',
				aPad : true
			// vMin : "1.00"
			});

			managebike.BindBikeGrid(null, null, null, null, null, null);
			$('#divBikeForm').hide();
			$('#divBikeGrid').show();

			$('#btnDeactivateSelected')
					.click(
							function() {
								var bike_ids = '';
								bike_ids = SageData.Get("gdvBikes").Arr
										.join(',');

								if (bike_ids.length > 0) {
									var properties = {
										onComplete : function(e) {
											managebike
													.ConfirmDeactivateMultiple(
															bike_ids, e);
										}
									};
									csscody
											.confirm(
													"<h2>"
															+ 'Delete Confirmation'
															+ "</h2><p>"
															+ 'Are you certain you want to delete selected bike(s)?'
															+ "</p>",
													properties);
								} else {
									csscody
											.alert('<h2>'
													+ 'Information Alert'
													+ '</h2><p>'
													+ 'Please select at least one bike before deleting.'
													+ '</p>');
								}
							});

			$("#btnExportToExcel")
					.on(
							"click",
							function() {
								var bikeTitle = $.trim($("#txtSearchTitle")
										.val());
								var addedOnFrom = $.trim($(
										"#txtSearchAddedOnFrom").val());
								var addedOnTo = $.trim($("#txtSearchAddedOnTo")
										.val());
								var priceFrom = $.trim($("#txtSearchPriceFrom")
										.autoNumeric('get'));
								var priceTo = $.trim($("#txtSearchPriceTo")
										.autoNumeric('get'));

								var isActive = $.trim($("#ddlSearchIsActive")
										.val()) == "" ? null
										: $.trim($("#ddlSearchIsActive").val()) == "True" ? true
												: false;

								if (bikeTitle.length < 1) {
									bikeTitle = null;
								}
								if (addedOnFrom.length < 1) {
									addedOnFrom = null;
								}
								if (addedOnTo.length < 1) {
									addedOnTo = null;
								}
								if (priceFrom.length < 1) {
									priceFrom = null;
								}
								if (priceTo.length < 1) {
									priceTo = null;
								}

								managebike.ExportToExcel(bikeTitle,
										addedOnFrom, addedOnTo, priceFrom,
										priceTo, isActive);
							});

			$('#btnBack').on("click", function() {
				$('#divBikeGrid').show();
				$('#divBikeForm').hide();
				managebike.config.bikeId = '0';
			});

			$('#btnReset')
					.on(
							"click",
							function() {
								var properties = {
									onComplete : function(e) {
										if (e) {
											if (managebike.config.bikeId == "0") {
												managebike.ClearForm();
												CKEDITOR.instances['txtDescription']
														.setData('');
											}
										}
									}
								};
								csscody
										.confirm(
												"<h2>"
														+ 'Reset Confirmation'
														+ "</h2><p>"
														+ 'Are you certain you want to reset this bike information?'
														+ "</p>", properties);
							});

			$('#btnAddNew').on("click", function() {
				if (managebike.config.bikeId == '0') {
					$('#lblFormHeading').html('New Bike Details');

					managebike.InitializeUploader("");

					$("#trAddedOn").hide();
					$("#btnReset").show();
					$("#btnSaveBike").show();

					managebike.ClearForm();
					CKEDITOR.instances['txtDescription'].setData('');

					$('#divBikeGrid').hide();
					$('#divBikeForm').show();
				}
			});

			// Save
			$('#btnSaveBike')
					.click(
							function(event) {
								if (validator.form()) {
									var properties = {
										onComplete : function(e) {
											if (e) {
												$('#btnSaveBike').disableWith(
														'Saving...');

												managebike
														.SaveBike(managebike.config);

												$('#btnSaveBike').enableAgain();
												event.preventDefault();
												return false;
											}
										}
									};
									csscody
											.confirm(
													"<h2>"
															+ 'Save Confirmation'
															+ "</h2><p>"
															+ 'Are you certain you want to save this bike?'
															+ "</p>",
													properties);
								}
							});

			$("#btnSearchBike").on("click", function() {
				// if ($("#form1").valid()) {
				managebike.SearchBikes();
				// }
				return false;
			});

			$(
					'#txtSearchTitle, #txtSearchAddedOnFrom, #txtSearchAddedOnTo, #txtSearchPriceFrom, #txtSearchPriceTo, #ddlSearchIsActive')
					.keyup(function(event) {
						if (event.keyCode == 13) {
							$("#btnSearchBike").click();
						}
					});
		}
	};

	$.metadata.setType("attr", "validate");
	$('.auto-submit-star').rating({
		cancel : 'Cancel',
		cancelValue : '0'
	});

	managebike.init();

});