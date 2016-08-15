var index = '';

$(function() {
	jQuery.fn.exists = function() {
		return this.length > 0;
	}

	index = {
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
			ajaxCallMode : 0
		},

		ajaxCall : function(config) {
			$.ajax({
				type : index.config.type,
				beforeSend : function(request) {
					// Need to send oAuth values here
				},
				contentType : index.config.contentType,
				cache : index.config.cache,
				async : index.config.async,
				url : index.config.url,
				data : index.config.data,
				dataType : index.config.dataType,
				success : index.ajaxSuccess,
				error : index.ajaxFailure
			});
		},

		FindFirstBikeFromDB : function() {
			this.config.url = this.config.baseURL + "GetFirstBikeFromDB";
			this.config.ajaxCallMode = 1;
			this.ajaxCall(this.config);
			return false;
		},

		FillDetails : function(bike) {

		},

		ajaxSuccess : function(msg) {
			switch (index.config.ajaxCallMode) {
			case 0:
				break;

			case 1:
				index.FillDetails(msg);
				break;

			}
		},

		ajaxFailure : function(msg) {
			switch (index.config.ajaxCallMode) {
			case 0:
				break;
			case 1:
				csscody.error("<h2>" + 'Error Message' + "</h2><p>"
						+ 'Cannot load any Bike from the Store!' + "</p>");
				break;

			}
		},

		init : function(config) {
			$("#btnAddToMyCart")
					.on(
							"click",
							function() {
								csscody
										.info("<h2>"
												+ 'Successful Message'
												+ "</h2><p>"
												+ 'Bike has been Added to Your Cart successfully.'
												+ "</p>");
								return false;
							});

			index.FindFirstBikeFromDB();
		}
	};
	$.metadata.setType("attr", "validate");
	$('.auto-submit-star').rating();

	index.init();

});